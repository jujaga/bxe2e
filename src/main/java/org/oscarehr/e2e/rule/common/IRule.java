package org.oscarehr.e2e.rule.common;

import org.apache.commons.lang3.tuple.MutablePair;

public interface IRule<S, T> {
	public static enum Original {
		SOURCE, TARGET
	}

	MutablePair<S, T> getPair();

	T getTarget();

	S getSource();
}