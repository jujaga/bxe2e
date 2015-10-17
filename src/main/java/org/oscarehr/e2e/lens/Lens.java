package org.oscarehr.e2e.lens;

public interface Lens<S, T> {
	T get(S s);
	S put(S s, T t);
}
