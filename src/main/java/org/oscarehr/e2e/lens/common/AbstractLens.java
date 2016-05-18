package org.oscarehr.e2e.lens.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

public class AbstractLens<S, T> implements IGet<S, T>, IPut<S, T> {
	protected final Logger log = Logger.getLogger(this.getClass().getSimpleName());
	protected List<String> lensTypes = new ArrayList<>(Arrays.asList(this.getClass().getSimpleName()));
	protected Function<S, T> get = null;
	protected BiFunction<S, T, S> put = null;

	protected AbstractLens() {
		// Should only be instantiated directly when performing a lens composition
	}

	/**
	 * Performs a lens composition between the caller lens and innerLens and returns the composed lens.
	 *
	 * @param innerLens the inner lens
	 * @return the new composed lens
	 */
	public <U> AbstractLens<S, U> compose(AbstractLens<T, U> innerLens) {
		Validate.notNull(get, "Composition Error: Outer get function is null");
		Validate.notNull(put, "Composition Error: Outer put function is null");
		Validate.notNull(innerLens.get, "Composition Error: Inner get function is null");
		Validate.notNull(innerLens.put, "Composition Error: Inner put function is null");

		AbstractLens<S, U> newLens = new AbstractLens<>();
		newLens.lensTypes = this.lensTypes;
		newLens.lensTypes.add(innerLens.getClass().getSimpleName());
		newLens.get = this.get.andThen(innerLens.get);
		newLens.put = (s, u) -> this.put.apply(s, innerLens.put.apply(this.get.apply(s), u));
		return newLens;
	}

	/**
	 * Gets the lens types.
	 *
	 * @return the lens types
	 */
	public final List<String> getLensTypes() {
		return lensTypes;
	}

	// Standard Get Function
	@Override
	public T get(S s) {
		Validate.notNull(get, "Lens Error: Get function is null");
		return get.apply(s);
	}

	// Standard Put Function
	@Override
	public S put(S s, T t) {
		Validate.notNull(put, "Lens Error: Put function is null");
		return put.apply(s, t);
	}
}
