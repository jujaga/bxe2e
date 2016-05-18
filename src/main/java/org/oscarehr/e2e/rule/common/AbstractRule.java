package org.oscarehr.e2e.rule.common;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.oscarehr.e2e.lens.common.AbstractLens;

public abstract class AbstractRule<S, T> implements IRule<S, T> {
	protected final Logger log = Logger.getLogger(this.getClass().getSimpleName());
	private static final String EXECUTE_WARNING = "Rule Not Executed: results may not be in valid transformed state";
	protected static Original original = Original.SOURCE;
	protected String ruleName = this.getClass().getSimpleName();
	private Boolean executed = false;

	protected Pair<S, T> pair = null;
	private AbstractLens<Pair<S, T>, Pair<S, T>> lens = null;

	protected AbstractRule(S source, T target) {
		this.pair = new ImmutablePair<S, T>(source, target);

		try {
			lens = defineLens();
		} catch (NullPointerException e) {
			log.error("Rule Error: Lens definition failed", e);
		}
	}

	/**
	 * Define the lens.
	 *
	 * @return the lens
	 */
	protected abstract AbstractLens<Pair<S, T>, Pair<S, T>> defineLens();

	/**
	 * Gets the lens.
	 *
	 * @return the lens
	 */
	public final AbstractLens<Pair<S, T>, Pair<S, T>> getLens() {
		return lens;
	}

	/**
	 * Sets the original transformation direction.
	 *
	 * @param original the original source
	 */
	public static void setOriginal(Original original) {
		AbstractRule.original = original;
	}

	@Override
	public String getName() {
		return ruleName;
	}

	@Override
	public IRule<S, T> execute() {
		if(!executed && lens != null) {
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

		return this;
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
