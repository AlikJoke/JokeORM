package ru.projects.joke.orm.core.operations;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.hibernate.validator.constraints.NotEmpty;

public interface JokeOperations<T> {

	void save(@NotNull T entity);

	void update(@NotNull T entity);

	boolean removeById(@NotNull Class<T> entityClass, @NotNull Object primaryKey);

	boolean remove(@NotNull T entity);

	@Null
	T findById(@NotNull Class<T> entityClass, @NotNull Object primaryKey);

	@NotNull
	List<T> findAll(@NotNull Class<T> entityClass);

	void executeQuery(@NotNull @NotEmpty String nativeQuery);
}
