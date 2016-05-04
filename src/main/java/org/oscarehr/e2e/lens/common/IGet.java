package org.oscarehr.e2e.lens.common;

/**
 * The Interface IGet for Get Function Transformations
 *
 * @param <S> the generic type
 * @param <T> the generic type
 */
@FunctionalInterface
public interface IGet<S, T> {

	/**
	 * Get Function.
	 *
	 * @param s the s
	 * @return the t
	 */
	T get(S s);
}
