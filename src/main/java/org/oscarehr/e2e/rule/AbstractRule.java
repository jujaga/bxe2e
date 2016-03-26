package org.oscarehr.e2e.rule;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.log4j.Logger;
import org.oscarehr.e2e.lens.common.AbstractLens;

public abstract class AbstractRule<S, T> {
	protected Logger log = Logger.getLogger(this.getClass().getSimpleName());
	protected MutablePair<S, T> pair = null;
	protected AbstractLens<MutablePair<S, T>, MutablePair<S, T>> lens = null;

	public AbstractRule(S source, T target) {
		pair = new MutablePair<S, T>(source, target);

		defineLens();
		apply();
	}

	protected abstract void defineLens();

	protected void apply() {
		try {
			if(pair.getRight() == null) {
				pair = lens.get(pair);
			} else if (pair.getLeft() == null){
				pair = lens.put(pair);
			} else {
				pair = lens.put(pair, pair);
			}
		} catch (NullPointerException e) {
			log.error("Rule Error: Lens undefined");
		}
	}

	public S getSource() {
		return pair.getLeft();
	}

	public T getTarget() {
		return pair.getRight();
	}

	// TODO Determine if this should be used for aggregating rule pairs into a map
	public MutablePair<S, T> getPair() {
		return pair;
	}
}
