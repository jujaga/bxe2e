package org.oscarehr.e2e.rule.common;

import org.apache.commons.lang3.tuple.Pair;

public interface IRule<S, T> {
	public static enum Original {
		SOURCE, TARGET
	}

	Pair<S, T> getPair();

	T getTarget();

	S getSource();

	void apply();
}
