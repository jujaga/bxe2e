package org.oscarehr.e2e.rule.common;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.oscarehr.e2e.lens.common.AbstractLens;

public abstract class AbstractRule<S, T> implements IRule<S, T> {
	protected final Logger log = Logger.getLogger(this.getClass().getSimpleName());
	protected Pair<S, T> pair = null;
	private AbstractLens<Pair<S, T>, Pair<S, T>> lens = null;

	private Original original = null;
	private Boolean applied = false;

	protected AbstractRule(S source, T target, Original original) {
		Validate.notNull(source, "Parameter source cannot be null");
		Validate.notNull(target, "Parameter target cannot be null");
		Validate.notNull(original, "Parameter original cannot be null");

		this.pair = new ImmutablePair<S, T>(source, target);
		this.original = original;

		lens = defineLens();
		apply();
	}

	protected abstract AbstractLens<Pair<S, T>, Pair<S, T>> defineLens();

	@Override
	public void apply() {
		try {
			if(original == Original.SOURCE) {
				pair = lens.get(pair);
			} else if(original == Original.TARGET) {
				pair = lens.put(pair, pair);
			}
		} catch (NullPointerException e) {
			log.error("Rule Error: Lens transformation failed", e);
		}

		applied = true;
	}

	// TODO Determine if this should be used for aggregating rule pairs into a map
	@Override
	public Pair<S, T> getPair() {
		if(applied) {
			return pair;
		} else {
			return null;
		}
	}

	@Override
	public S getSource() {
		return pair.getLeft();
	}

	@Override
	public T getTarget() {
		return pair.getRight();
	}
}
