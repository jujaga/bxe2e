package org.oscarehr.e2e.lens;

@FunctionalInterface
public interface IGet<S, T> {
	T get(S s);
}
