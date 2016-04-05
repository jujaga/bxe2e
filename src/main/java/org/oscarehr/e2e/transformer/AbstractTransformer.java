package org.oscarehr.e2e.transformer;

import org.oscarehr.e2e.model.Model;

public abstract class AbstractTransformer<M extends Model, T> {
	protected M model;
	protected T target;

	protected AbstractTransformer(M model, T target) {
		this.model = model;
		this.target = target;
	}

	protected abstract void transform();

	public M getModel() {
		return model;
	}

	public T getTarget() {
		return target;
	}
}
