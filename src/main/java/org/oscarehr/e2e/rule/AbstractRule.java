package org.oscarehr.e2e.rule;

public abstract class AbstractRule<S, T> {
	// Constructor should take in 2 things source and target: one of the two may be null
	// Initialization of the object should detect which part is missing and build it
	// After construction, the object is fully populated and binds the source and target
	// Standard use should be a simple no-parameter get and put to access the objects
	// The object itself should represent the entire TGG including the correspondence

	protected S source = null;
	protected T target = null;

	public AbstractRule(S s, T t) {
		this.source = s;
		this.target = t;

		apply();
	}

	public T get() {
		return target;
	}
	public S put() {
		return source;
	}

	protected abstract void apply(); // Apply rule between S and T
}
