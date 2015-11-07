package org.oscarehr.e2e.lens.common;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.log4j.Logger;

public class AbstractLens<S, T> implements IGet<S, T>, IPut<S, T> {
	protected Logger log = Logger.getLogger(this.getClass().getSimpleName());
	protected Function<S, T> get = null;
	protected BiFunction<S, T, S> put = null;

	protected AbstractLens() {
		// Should only be instantiated directly when performing a lens concatenation
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
	public static <A, B, C, V> AbstractLens<A, C> compose(AbstractLens<A, B> outerLens, AbstractLens<B, C> innerLens) {
		AbstractLens<A, C> newLens = new AbstractLens<>();

		try {
			Function<B, C> innerGet = innerLens.get;
			Function<A, B> outerGet = outerLens.get;
			newLens.get = outerGet.andThen(innerGet);
		} catch (NullPointerException e) {
			//log.error("Concatenation Error: Get subfunction(s) undefined");
			return null;
		}

		/*try {
			BiFunction<B, C, B> innerPut = innerLens.put;
			BiFunction<A, B, A> outerPut = (BiFunction<A, B, A>) this.put;
			newLens.put = (BiFunction<A, C, A>) innerPut.andThen((Function<? super B, ? extends V>) outerPut);
		} catch (NullPointerException e) {
			log.error("Concatenation Error: Put subfunction(s) undefined");
			return null;
		}*/

		return newLens;
	}
}
