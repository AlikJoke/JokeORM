package ru.projects.joke.orm.core;

import javax.validation.constraints.NotNull;

import ru.projects.joke.orm.core.operations.JokeOperations;

public interface JokeInstance<T> {

	@NotNull
	JokeOperations<T> getJokeOperations();
}
