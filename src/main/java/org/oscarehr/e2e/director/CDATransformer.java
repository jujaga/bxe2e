package org.oscarehr.e2e.director;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.oscarehr.e2e.lens.Lens;

public abstract class CDATransformer<M> {
	protected final M model;
	protected final Lens<M, ClinicalDocument> lens;

	public CDATransformer(M model, Lens<M, ClinicalDocument> lens) {
		this.model = model;
		this.lens = lens;
	}

	public ClinicalDocument doExport() {
		return lens.get(model);
	}

	public M doImport(ClinicalDocument clinicalDocument) {
		return lens.put(clinicalDocument);
	}
}