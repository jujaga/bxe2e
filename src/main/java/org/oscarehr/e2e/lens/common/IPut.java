package org.oscarehr.e2e.lens.common;

/**
 * The Interface IPut for Put Function Transformations.
 *
 * @param <S> the generic type
 * @param <T> the generic type
 */
@FunctionalInterface
public interface IPut<S, T> {

	/**
	 * Put Function.
	 *
	 * @param s the s
	 * @param t the t
	 * @return the s
	 */
	S put(S s, T t);

	/**
	 * Put Function without Complement (aka Lens Create).
	 *
	 * @param t the t
	 * @return the s
	 */
	default S put(T t) {
		return put(null, t);
	}
}
