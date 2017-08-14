package ru.projects.joke.orm.core.utils;

public class ObjectPair<K, V> {

	private K firstValue;
	private V secondValue;

	public ObjectPair(K firstValue, V secondValue) {
		this.firstValue = firstValue;
		this.secondValue = secondValue;
	}

	public K getFirstValue() {
		return firstValue;
	}

	public V getSecondValue() {
		return secondValue;
	}
}
