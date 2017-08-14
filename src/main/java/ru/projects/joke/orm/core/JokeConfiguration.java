package ru.projects.joke.orm.core;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.hibernate.validator.constraints.NotEmpty;

public interface JokeConfiguration {

	@NotNull
	@NotEmpty
	String getDriverClassname();

	@NotNull
	@NotEmpty
	String getUsername();

	boolean autocommitEnabled();

	boolean buildSchemaEnabled();

	@NotNull
	@NotEmpty
	String getUrl();

	@Null
	String getPassword();
}
