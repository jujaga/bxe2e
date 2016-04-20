package org.oscarehr.e2e.rule.common;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.oscarehr.e2e.lens.common.AbstractLens;

public abstract class AbstractRule<S, T> implements IRule<S, T> {
	protected final Logger log = Logger.getLogger(this.getClass().getSimpleName());
	private static final String EXECUTE_WARNING = "Rule not yet executed - results may not be properly transformed";
	protected static Original original = Original.SOURCE;
	protected String ruleName = this.getClass().getSimpleName();
	private Boolean executed = false;

	protected Pair<S, T> pair = null;
	private AbstractLens<Pair<S, T>, Pair<S, T>> lens = null;

	protected AbstractRule(S source, T target) {
		this.pair = new ImmutablePair<S, T>(source, target);

		lens = defineLens();
	}

	protected abstract AbstractLens<Pair<S, T>, Pair<S, T>> defineLens();

	public static void setOriginal(Original original) {
		AbstractRule.original = original;
	}

	@Override
	public String getName() {
		return ruleName;
	}

	@Override
	public void execute() {
		try {
			if(original == Original.SOURCE) {
				pair = lens.get(pair);
			} else if(original == Original.TARGET) {
				pair = lens.put(pair, pair);
			}
		} catch (NullPointerException e) {
			log.error("Rule Error: Lens transformation failed", e);
		}

		executed = true;
	}

	@Override
	public Boolean executed() {
		return executed;
	}

	@Override
	public Pair<S, T> getPair() {
		if(!executed) {
			log.warn(EXECUTE_WARNING);
		}
		return pair;
	}

	@Override
	public S getSource() {
		if(!executed) {
			log.warn(EXECUTE_WARNING);
		}
		return pair.getLeft();
	}

	@Override
	public T getTarget() {
		if(!executed) {
			log.warn(EXECUTE_WARNING);
		}
		return pair.getRight();
	}
}
