package org.oscarehr.e2e.lens.common;

@FunctionalInterface
public interface IPut<S, T> {
	S put(S s, T t);

	// Put Function without Complement (aka Lens Create)
	default S put(T t) {
		return put(null, t);
	}
}
