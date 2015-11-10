package org.oscarehr.e2e.lens.temp;

import org.oscarehr.e2e.lens.common.AbstractLens;

// TODO Move to test framework
public class Test1Lens extends AbstractLens<String, Integer> {
	public Test1Lens() {
		get = value -> {
			return Integer.parseInt(value);
		};

		put = (value, number) -> {
			return Integer.toString(number);
		};
	}
}
