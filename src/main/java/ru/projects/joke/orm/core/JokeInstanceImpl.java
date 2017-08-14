package ru.projects.joke.orm.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import ru.projects.joke.orm.core.operations.JokeOperations;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Configurable
public class JokeInstanceImpl<T> implements JokeInstance<T> {

	@Autowired
	private JokeOperations<T> jokeOperations;
	
	@Override
	public JokeOperations<T> getJokeOperations() {
		return this.jokeOperations;
	}
}
