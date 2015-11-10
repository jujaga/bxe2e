package org.oscarehr.e2e.lens.temp;

import org.oscarehr.e2e.lens.common.AbstractLens;

//TODO Move to test framework
public class Test2Lens extends AbstractLens<Integer, Boolean> {
	public Test2Lens() {
		get = number -> {
			return (number != 0);
		};

		put = (number, state) -> {
			return state ? 1 : 0;
		};
	}
}
