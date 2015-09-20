package org.oscarehr.e2e.populator;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.oscarehr.e2e.model.PatientExport;
import org.oscarehr.e2e.populator.body.DocumentBodyPopulator;
import org.oscarehr.e2e.populator.header.HeaderPopulator;

public class EmrExportPopulator extends AbstractPopulator {
	public EmrExportPopulator(PatientExport patientExport, CE<String> code, II templateId) {
		if(patientExport.isLoaded()) {
			this.populators.add(new HeaderPopulator(patientExport, code, templateId));
			this.populators.add(new DocumentBodyPopulator(patientExport));

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
