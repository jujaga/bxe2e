package org.oscarehr.e2e.director;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.oscarehr.e2e.model.Lens;

public class E2ETransformer<M> {
	protected final M model;
	protected final Lens<M, ClinicalDocument> lens;

	E2ETransformer(M model, Lens<M, ClinicalDocument> lens) {
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
