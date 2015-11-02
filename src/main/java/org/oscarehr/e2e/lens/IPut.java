package org.oscarehr.e2e.lens;

@FunctionalInterface
public interface IPut<S, T> {
	S put(S s, T t);

	// Put Function without Complement
	default S put(T t) {
		return put(null, t);
	}
}
