package org.oscarehr.e2e.transformer;

import org.oscarehr.e2e.model.Model;
import org.oscarehr.e2e.rule.common.IRule.Original;

public abstract class AbstractTransformer<M extends Model, T> {
	protected M model;
	protected T target;
	protected final Original original;

	protected AbstractTransformer(M model, T target, Original original) {
		this.model = model;
		this.target = target;
		this.original = original;
	}

	protected abstract void transform();

	public M getModel() {
		return model;
	}

	public T getTarget() {
		return target;
	}
}
