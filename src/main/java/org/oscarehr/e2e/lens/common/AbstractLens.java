package org.oscarehr.e2e.lens.common;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.log4j.Logger;

public abstract class AbstractLens<S, T> implements IGet<S, T>, IPut<S, T> {
	protected Logger log = Logger.getLogger(this.getClass().getSimpleName());
	protected Function<S, T> get = null;
	protected BiFunction<S, T, S> put = null;

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
}
