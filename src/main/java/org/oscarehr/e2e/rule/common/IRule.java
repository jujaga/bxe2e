package org.oscarehr.e2e.rule.common;

public interface IRule<S, T> {
	public static enum Original {
		SOURCE, TARGET
	}

	void apply();

	T getTarget();

	S getSource();

	Boolean isApplied();
}
