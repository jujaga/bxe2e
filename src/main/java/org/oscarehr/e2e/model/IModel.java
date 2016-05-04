package org.oscarehr.e2e.model;

/**
 * The Interface IModel.
 */
public interface IModel {

	/**
	 * Checks if Model is loaded.
	 *
	 * @return the boolean
	 */
	Boolean isLoaded();

	/**
	 * Sets the loaded Model state.
	 *
	 * @param loaded the new boolean loaded state
	 */
	void setLoaded(Boolean loaded);
}
