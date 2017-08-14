package ru.projects.joke.orm.core;

public interface JokeConfiguration {

	String getDriverClassname();
	
	String getUsername();
	
	boolean autocommitEnabled();
	
	boolean buildSchemaEnabled();
	
	String getUrl();
	
	String getPassword();
}
