package org.oscarehr.e2e.lens;

import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class AbstractLens<S, T> implements Get<S, T>, Put<S, T> {
	protected Function<S, T> get = null;
	protected BiFunction<S, T, S> put = null;

	public T get(S s) {
		return get.apply(s);
	}

	public S put(S s, T t) {
		return put.apply(s, t);
	}
}
