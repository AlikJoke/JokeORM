package ru.projects.joke.orm.core;

import ru.projects.joke.orm.core.operations.JokeOperations;

public interface JokeInstance<T> {

	JokeOperations<T> getJokeOperations();
}
