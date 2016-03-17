package org.oscarehr.e2e.model;

// TODO Make model objects generic
public class Triplet {
	private Object source = null;
	private Object target = null;
	private Object correspondence = null;

	public Object getSource() {
		return source;
	}
	public void setSource(Object source) {
		this.source = source;
	}
	public Object getTarget() {
		return target;
	}
	public void setTarget(Object target) {
		this.target = target;
	}
	public Object getCorrespondence() {
		return correspondence;
	}
	public void setCorrespondence(Object correspondence) {
		this.correspondence = correspondence;
	}
}
