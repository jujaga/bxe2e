package org.oscarehr.e2e.lens;

public interface ZipperLens<S, T> {
	T get(S s);
	void put(S s, T t);
}
