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

	@SuppressWarnings("unchecked")
	public <U> AbstractLens<S, U> compose(AbstractLens<T, U> innerLens) {
		AbstractLens<S, U> newLens = new AbstractLens<>();

		try {
			newLens.get = this.get.andThen(innerLens.get);
		} catch (NullPointerException e) {
			log.error("Composition Error: Get subfunction(s) undefined");
		}
		try {
			// TODO Figure out if functional currying is appropriate here
			newLens.put = (s, u) -> {
				T t = null;
				if(s instanceof String && t instanceof String) {
					t = innerLens.put((T) s, u);
				} else {
					t = innerLens.put(u);
					log.warn("Composed lens types are not all of Type String - Put functions falling back to null inputs");
				}

				/*try { // Try casting s as intermediary t for bifunction apply
					T temp = (T) s;
					t = innerLens.put(temp, u);
				} catch (Exception e) {
					log.warn("Cannot cast " + s.getClass().getSimpleName() + " as " + t.getClass().getSimpleName());
					t = innerLens.put(u);
				}*/
				return this.put.apply(s, t);
			};
		} catch (NullPointerException e) {
			log.error("Composition Error: Put subfunction(s) undefined");
		}

		return newLens;
	}
}
