package org.oscarehr.e2e.rule.common;

import org.apache.commons.lang3.tuple.Pair;

/**
 * The Interface IRule modeling a Triple Graph Grammar.
 *
 * @param <S> the generic type
 * @param <T> the generic type
 */
public interface IRule<S, T> {

	/**
	 * The Original type. This is used to specify whether S or T is the source
	 */
	public static enum Original {
		SOURCE, TARGET
	}

	/**
	 * Sets the original transformation direction.
	 *
	 * @param original the original source
	 */
	void setOriginal(Original original);

	/**
	 * Gets the rule name.
	 *
	 * @return the name
	 */
	String getName();

	/**
	 * Executes the rule.
	 *
	 * @return the i rule
	 */
	IRule<S, T> execute();

	/**
	 * Returns whether the rule has been executed or not.
	 *
	 * @return the boolean
	 */
	Boolean executed();

	/**
	 * Gets the pair.
	 *
	 * @return the pair
	 */
	Pair<S, T> getPair();

	/**
	 * Gets the target.
	 *
	 * @return the target
	 */
	T getTarget();

	/**
	 * Gets the source.
	 *
	 * @return the source
	 */
	S getSource();
}
