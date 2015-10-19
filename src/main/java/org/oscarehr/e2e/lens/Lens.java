package org.oscarehr.e2e.lens;

public interface Lens<S, T> extends Get<S, T>, Put<S, T> {
	/*default Lens<?,?> concat(Lens<?,?> lens) {
		return lens;
	}*/
}
