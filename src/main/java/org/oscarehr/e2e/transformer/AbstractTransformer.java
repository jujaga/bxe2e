package org.oscarehr.e2e.transformer;

import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;
import org.oscarehr.e2e.rule.common.IRule.Original;

public abstract class AbstractTransformer<IModel, T> {
	protected final Logger log = Logger.getLogger(this.getClass().getSimpleName());
	protected IModel model;
	protected T target;
	protected final Original original;

	protected AbstractTransformer(IModel model, T target, Original original) {
		Validate.notNull(original, "Parameter original cannot be null");
		this.model = model;
		this.target = target;
		this.original = original;

		transform();
	}

	protected abstract void transform();

	public IModel getModel() {
		return model;
	}

	public T getTarget() {
		return target;
	}
}
