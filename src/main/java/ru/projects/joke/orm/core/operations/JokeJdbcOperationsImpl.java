package ru.projects.joke.orm.core.operations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.projects.joke.orm.core.Core;
import ru.projects.joke.orm.core.exceptions.JokeException;


@Component
public class JokeJdbcOperationsImpl<T> implements JokeOperations<T> {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private Core core;

	@Override
	public void save(T entity) {
		
	}

	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean removeById(Class<T> entityClass, Object primaryKey) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(T entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T findById(Class<T> entityClass, Object primaryKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findAll(Class<T> entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void executeQuery(String nativeQuery) {
		// TODO Auto-generated method stub

	}
}
