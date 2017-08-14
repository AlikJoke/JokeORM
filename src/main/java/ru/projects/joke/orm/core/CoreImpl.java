package ru.projects.joke.orm.core;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.assertj.core.util.Lists;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import ru.projects.joke.orm.annotations.JokeColumn;
import ru.projects.joke.orm.annotations.JokeEntity;
import ru.projects.joke.orm.annotations.JokeForeignKey;
import ru.projects.joke.orm.annotations.JokeIndex;
import ru.projects.joke.orm.annotations.JokePrimaryKey;
import ru.projects.joke.orm.core.exceptions.JokeException;
import ru.projects.joke.orm.core.operations.JokeOperations;
import ru.projects.joke.orm.core.utils.ObjectPair;

@Component
public class CoreImpl implements Core {

	@Autowired
	private JokeOperations<?> jokeOperations;

	private Set<Class<?>> entityClasses = Sets.newHashSet();
	private Map<Class<?>, Map<String, String>> clazz2fieldsMap = Maps.newHashMap();
	private Map<Class<?>, List<String>> primaryKeysMap = Maps.newHashMap();

	@PostConstruct
	public void loadContext() {
		Reflections reflections = new Reflections();
		entityClasses.addAll(reflections.getTypesAnnotatedWith(JokeEntity.class));
		Map<Class<?>, ObjectPair<String, Set<Class<?>>>> resultMap = Maps.newHashMap();
		Set<String> indexQueries = Sets.newHashSet();

		entityClasses.forEach(clazz -> {
			final String tableName = clazz.getAnnotation(JokeEntity.class).name();
			Set<Class<?>> references = Sets.newHashSet();
			List<String> primaryKeys = Lists.newArrayList();
			Map<String, String> column2typeMap = Maps.newHashMap();
			Map<String, String> fieldsMap = Maps.newHashMap();

			Field[] fields = clazz.getDeclaredFields();
			Stream.of(fields).filter(field -> field.isAnnotationPresent(JokeColumn.class)).forEach(field -> {
				field.setAccessible(true);
				JokeColumn columnDef = field.getAnnotation(JokeColumn.class);

				fieldsMap.put(field.getName(), columnDef.name());
				ObjectPair<String, String> foreignKeyDef = getForeignKeyDef(field, references);
				String fieldDefinition = createColumnDefinition(columnDef.name(), columnDef.length(),
						foreignKeyDef.getFirstValue(), foreignKeyDef.getSecondValue(), columnDef.nullable());
				column2typeMap.put(fieldDefinition, fieldDefinition);

				JokePrimaryKey primaryKeyDef = field.getAnnotation(JokePrimaryKey.class);
				if (primaryKeyDef != null) {
					primaryKeys.add(columnDef.name());
				}

				if (field.isAnnotationPresent(JokeIndex.class))
					indexQueries.add(createIndexQuery(field.getAnnotation(JokeIndex.class), tableName));
			});

			primaryKeysMap.put(clazz, primaryKeys);
			clazz2fieldsMap.put(clazz, fieldsMap);

			String tableDefinition = createTableQuery(tableName, primaryKeys, column2typeMap);
			resultMap.put(clazz, new ObjectPair<>(tableDefinition, references));
		});

		buildSchema(resultMap, indexQueries);
	}

	private String createIndexQuery(JokeIndex indexDef, String tableName) {
		if (indexDef.column().isEmpty() && indexDef.columns().length == 0)
			throw new JokeException("Invalid index definition: columns can't be absent!");

		StringBuilder sb = new StringBuilder();
		sb.append("CREATE INDEX IF NOT EXISTS ");
		sb.append(indexDef.name());
		sb.append(" ON ");
		sb.append(tableName);
		sb.append(" USING ");
		sb.append(indexDef.method().getName());
		sb.append(" (");
		if (indexDef.column().isEmpty())
			sb.append(StringUtils.arrayToCommaDelimitedString(indexDef.columns()));
		else
			sb.append(indexDef.column());
		sb.append(")");

		return sb.toString();
	}

	private void buildSchema(Map<Class<?>, ObjectPair<String, Set<Class<?>>>> definitionsMap,
			Set<String> indexQueries) {
		Set<Class<?>> alreadyDefined = Sets.newHashSet();
		definitionsMap.keySet().forEach(key -> buildTableSchema(alreadyDefined, key, definitionsMap));

		indexQueries.forEach(query -> jokeOperations.executeQuery(query));
	}

	private void buildTableSchema(Set<Class<?>> alreadyDefined, Class<?> targetCls,
			Map<Class<?>, ObjectPair<String, Set<Class<?>>>> definitionsMap) {
		if (alreadyDefined.contains(targetCls))
			return;

		Set<Class<?>> references = definitionsMap.get(targetCls).getSecondValue();
		if (references.isEmpty() || alreadyDefined.containsAll(references)) {
			alreadyDefined.add(targetCls);
			jokeOperations.executeQuery(definitionsMap.get(targetCls).getFirstValue());
		} else {
			references.forEach(cls -> buildTableSchema(alreadyDefined, cls, definitionsMap));
		}

	}

	private ObjectPair<String, String> getForeignKeyDef(Field field, Set<Class<?>> references) {
		if (!field.isAnnotationPresent(JokeForeignKey.class))
			return null;

		JokeForeignKey foreignKeyDef = field.getAnnotation(JokeForeignKey.class);
		String foreignKeyName = foreignKeyDef.fieldName();
		Class<?> entityClass = foreignKeyDef.entityClass();
		if (!entityClasses.contains(entityClass))
			throw new JokeException(
					"Invalid foreign key definition: related entity hasn't annotation " + JokeEntity.class);

		references.add(entityClass);
		String tableName = entityClass.getAnnotation(JokeEntity.class).name();
		return new ObjectPair<>(foreignKeyName, tableName);
	}

	private String createColumnDefinition(String type, long length, String foreignColumn, String foreignTable,
			boolean isNullable) {
		StringBuilder sb = new StringBuilder();
		sb.append(type);
		sb.append(" (").append(length).append(") ");

		if (!isNullable)
			sb.append(" NOT NULL ");

		if (StringUtils.hasLength(foreignColumn))
			sb.append(" references ").append(foreignTable).append("(").append(foreignColumn).append(")");

		return sb.toString();
	}

	private String createTableQuery(final String tableName, List<String> primaryKeyColumns,
			Map<String, String> columnName2typeMap) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE IF NOT EXISTS ");
		sb.append(tableName);
		sb.append(" (");
		if (!columnName2typeMap.isEmpty()) {
			columnName2typeMap.entrySet()
					.forEach(entry -> sb.append(entry.getKey()).append(" ").append(entry.getValue()).append(", "));
		}

		if (!primaryKeyColumns.isEmpty()) {
			sb.append(" PRIMARY KEY (");
			primaryKeyColumns.forEach(column -> sb.append(column).append(","));
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append(")");
		sb.append(")");

		return sb.toString();
	}

	@Override
	public Set<Class<?>> getEntityClasses() {
		return entityClasses;
	}

	@Override
	public Map<String, String> getFieldsTranslator(Class<?> entityClass) {
		return clazz2fieldsMap.get(entityClass);
	}

	public <T> void saveEntity(T entity) {
		Map<String, Object> param2valueMap = getParamMap(entity);
	}
	
	private Map<String, Object> getParamMap(Object entity) {
		Map<String, String> translator = getFieldsTranslator(entity.getClass());
		Map<String, Object> param2value = Maps.newHashMap();
		translator.keySet()
				.forEach(fieldName -> param2value.put(translator.get(fieldName), getFieldValue(fieldName, entity)));
		
		return param2value;
	}

	private Object getFieldValue(String fieldName, Object entity) {
		if (StringUtils.isEmpty(fieldName))
			throw new JokeException("Name of field can't be empty or null!");

		try {
			return entity.getClass().getDeclaredField(fieldName).get(entity);
		} catch (Exception e) {
			throw new JokeException(e);
		}
	}

}
