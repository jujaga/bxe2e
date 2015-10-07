package org.oscarehr.e2e.populator;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.oscarehr.e2e.model.PatientModel;
import org.oscarehr.e2e.populator.body.DocumentBodyPopulator;
import org.oscarehr.e2e.populator.header.HeaderPopulator;

public class EmrExportPopulator extends AbstractPopulator {
	public EmrExportPopulator(PatientModel patientModel, CE<String> code, II templateId) {
		if(patientModel.isLoaded()) {
			this.populators.add(new HeaderPopulator(patientModel, code, templateId));
			this.populators.add(new DocumentBodyPopulator(patientModel));

			this.clinicalDocument = new ClinicalDocument();
			AbstractPopulator.setClinicalDocument(clinicalDocument, this.populators);
		}
		else {
			this.clinicalDocument = null;
		}
	}

	@Override
	public void populate() {
		AbstractPopulator.doPopulate(this);
	}
}
