package org.oscarehr.e2e.lens.common;

@FunctionalInterface
public interface IGet<S, T> {
	T get(S s);
}
