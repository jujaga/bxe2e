package org.oscarehr.e2e.transformer;

import org.apache.commons.lang3.Validate;
import org.oscarehr.e2e.model.Model;
import org.oscarehr.e2e.rule.common.AbstractRule;
import org.oscarehr.e2e.rule.common.IRule.Original;

public abstract class AbstractTransformer<M extends Model, T> {
	protected M model;
	protected T target;
	protected final Original original;

	protected AbstractTransformer(M model, T target, Original original) {
		Validate.notNull(original, "Parameter original cannot be null");
		this.model = model;
		this.target = target;
		this.original = original;
		AbstractRule.setOriginal(this.original);
	}

	protected abstract void transform();

	public M getModel() {
		return model;
	}

	public T getTarget() {
		return target;
	}
}
