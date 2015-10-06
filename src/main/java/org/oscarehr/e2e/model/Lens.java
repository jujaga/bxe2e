package org.oscarehr.e2e.model;

public interface Lens<T, U> {
	U get(T t);
	T put(U u);
}
