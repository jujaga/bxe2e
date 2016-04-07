package org.oscarehr.e2e.rule.common;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.log4j.Logger;
import org.oscarehr.e2e.lens.common.AbstractLens;

public abstract class AbstractRule<S, T> implements IRule<S, T> {
	protected final Logger log = Logger.getLogger(this.getClass().getSimpleName());
	protected MutablePair<S, T> pair = null;
	private AbstractLens<MutablePair<S, T>, MutablePair<S, T>> lens = null;

	private Original original = null;
	private Boolean applied = false;

	protected AbstractRule(S source, T target, Original original) {
		if(source == null || target == null || original == null) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		this.pair = new MutablePair<S, T>(source, target);
		this.original = original;

		lens = defineLens();
		apply();
	}

	protected abstract AbstractLens<MutablePair<S, T>, MutablePair<S, T>> defineLens();

	@Override
	public void apply() {
		try {
			if(original == Original.SOURCE) {
				pair = lens.get(pair);
			} else {
				pair = lens.put(pair, pair);
			}
		} catch (NullPointerException e) {
			log.error("Rule Error: Lens undefined");
		}

		applied = true;
	}

	// TODO Determine if this should be used for aggregating rule pairs into a map
	@Override
	public MutablePair<S, T> getPair() {
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
