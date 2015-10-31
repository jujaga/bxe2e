package org.oscarehr.e2e.lens;

@FunctionalInterface
public interface IPut<S, T> {
	S put(S s, T t);
}
