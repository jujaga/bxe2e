package org.oscarehr.e2e.model;

public abstract class Model {
	protected Boolean loaded = false;

	public Boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(Boolean loaded) {
		this.loaded = loaded;
	}
}
