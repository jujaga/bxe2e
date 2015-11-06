package org.oscarehr.e2e.lens.common;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.log4j.Logger;

public class AbstractLens<S, T> implements IGet<S, T>, IPut<S, T> {
	protected Logger log = Logger.getLogger(this.getClass().getSimpleName());
	protected Function<S, T> get = null;
	protected BiFunction<S, T, S> put = null;

	protected AbstractLens() {
		// This abstract class shouldn't be instantiated unless it's built from other lenses
	}

	// Standard Get Function
	public T get(S s) {
		try {
			return get.apply(s);
		} catch (NullPointerException e) {
			log.error("Get function undefined");
			return null;
		}
	}

	// Standard Put Function
	public S put(S s, T t) {
		try {
			return put.apply(s, t);
		} catch (NullPointerException e) {
			log.error("Put function undefined");
			return null;
		}
	}

	// TODO Figure out Templating issue
	@SuppressWarnings("unchecked")
	public <V> AbstractLens<S, T> concat(AbstractLens<?, T> innerLens) {
		AbstractLens<S, T> newLens = new AbstractLens<>();
		Function<?, T> innerGet = innerLens.get;
		newLens.get = (Function<S, T>) innerGet.andThen((Function<? super T, ? extends V>) this.get);
		return newLens;
	}
}
