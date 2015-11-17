package org.oscarehr.e2e.lens;

import org.oscarehr.e2e.lens.common.AbstractLens;

public class Test1Lens extends AbstractLens<String, Integer> {
	public Test1Lens() {
		get = value -> {
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException e) {
				return 0;
			}
		};

		put = (value, number) -> {
			return Integer.toString(number);
		};
	}
}
