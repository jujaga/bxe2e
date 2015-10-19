package org.oscarehr.e2e.lens;

@FunctionalInterface
public interface Put<S, T> {
	S put(S s, T t);
}
