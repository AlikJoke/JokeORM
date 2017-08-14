package ru.projects.joke.orm.core.exceptions;

public class JokeException extends RuntimeException {

	private static final long serialVersionUID = 5810445719437737013L;

	public JokeException(Exception e) {
		super(e);
	}
	
	public JokeException(String message) {
		super(message);
	}
	
	public JokeException(String message, Exception e) {
		super(message, e);
	}
}
