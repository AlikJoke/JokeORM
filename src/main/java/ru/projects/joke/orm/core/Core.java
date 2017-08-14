package ru.projects.joke.orm.core;

import java.util.Map;
import java.util.Set;

public interface Core {

	void loadContext();
	
	Set<Class<?>> getEntityClasses();
	
	Map<String, String> getFieldsTranslator(Class<?> entityClass);
}
