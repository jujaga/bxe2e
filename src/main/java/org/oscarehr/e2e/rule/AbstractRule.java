package org.oscarehr.e2e.rule;

import org.apache.log4j.Logger;
import org.oscarehr.e2e.lens.common.AbstractLens;

public abstract class AbstractRule<S, T> {
	protected Logger log = Logger.getLogger(this.getClass().getSimpleName());
	protected S source = null;
	protected T target = null;
	protected AbstractLens<S, T> lens = null;

	public AbstractRule(S source, T target) {
		this.source = source;
		this.target = target;

		// May need to look into Apache Commons Pair library
		defineLens();
		apply();
	}

	protected abstract void defineLens();

	protected void apply() {
		try {
			if(target == null) {
				target = lens.get(source);
			} else if (source == null){
				source = lens.put(target);
			} else {
				source = lens.put(source, target);
			}
		} catch (NullPointerException e) {
			log.error("Rule Error: Lens undefined");
		}
	}

	public S getSource() {
		return source;
	}

	public T getTarget() {
		return target;
	}
}
