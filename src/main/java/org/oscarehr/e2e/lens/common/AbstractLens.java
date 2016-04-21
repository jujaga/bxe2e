package org.oscarehr.e2e.lens.common;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

public class AbstractLens<S, T> implements IGet<S, T>, IPut<S, T> {
	protected final Logger log = Logger.getLogger(this.getClass().getSimpleName());
	protected Function<S, T> get = null;
	protected BiFunction<S, T, S> put = null;

	protected AbstractLens() {
		// Should only be instantiated directly when performing a lens composition
	}

	// Standard Get Function
	@Override
	public T get(S s) {
		Validate.notNull(get, "Get function is null");
		return get.apply(s);
	}

	// Standard Put Function
	@Override
	public S put(S s, T t) {
		Validate.notNull(put, "Put function is null");
		return put.apply(s, t);
	}

	public <U> AbstractLens<S, U> compose(AbstractLens<T, U> innerLens) {
		AbstractLens<S, U> newLens = new AbstractLens<>();

		// TODO Add null sanity detection for sub-lenses
		newLens.get = this.get.andThen(innerLens.get);
		newLens.put = (s, u) -> this.put.apply(s, innerLens.put.apply(this.get.apply(s), u));

		return newLens;
	}
}
