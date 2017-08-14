package ru.projects.joke.orm.core.utils;

public enum IndexMethod {

	GIN("gin"),

	BTREE("btree");

	private String name;

	IndexMethod(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
