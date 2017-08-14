package ru.projects.joke.orm.core.operations;

import java.util.List;

public interface JokeOperations<T> {

	void save(T entity);
	
	void update(T entity);
	
	boolean removeById(Class<T> entityClass, Object primaryKey);
	
	boolean remove(T entity);
	
	T findById(Class<T> entityClass, Object primaryKey);
	
	List<T> findAll(Class<T> entityClass);
	
	void executeQuery(String nativeQuery);
}
