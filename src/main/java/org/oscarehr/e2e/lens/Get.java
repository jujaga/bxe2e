package org.oscarehr.e2e.lens;

@FunctionalInterface
public interface Get<S, T> {
	T get(S s);
}
