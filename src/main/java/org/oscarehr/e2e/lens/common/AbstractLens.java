package org.oscarehr.e2e.lens.common;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.log4j.Logger;

public class AbstractLens<S, T> implements IGet<S, T>, IPut<S, T> {
	protected final Logger log = Logger.getLogger(this.getClass().getSimpleName());
	protected Function<S, T> get = null;
	protected BiFunction<S, T, S> put = null;

	protected AbstractLens() {
		// Should only be instantiated directly when performing a lens composition
	}

	// Standard Get Function
	public T get(S s) {
		try {
			return get.apply(s);
		} catch (NullPointerException e) {
			log.error("Transformation Error: Get function undefined");
			return null;
		}
	}

	// Standard Put Function
	public S put(S s, T t) {
		try {
			return put.apply(s, t);
		} catch (NullPointerException e) {
			log.error("Transformation Error: Put function undefined");
			return null;
		}
	}

	public <U> AbstractLens<S, U> compose(AbstractLens<T, U> innerLens) {
		AbstractLens<S, U> newLens = new AbstractLens<>();

		try {
			newLens.get = this.get.andThen(innerLens.get);
		} catch (NullPointerException e) {
			log.error("Composition Error: Get subfunction(s) undefined");
		}
		try {
			// TODO Debug order of lens operations
			newLens.put = (s, u) -> this.put.apply(s, innerLens.put.apply(this.get.apply(s), u));
		} catch (NullPointerException e) {
			log.error("Composition Error: Put subfunction(s) undefined");
		}

		return newLens;
	}
}
