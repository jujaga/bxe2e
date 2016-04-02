package org.oscarehr.e2e.lens;

import java.util.GregorianCalendar;

import org.apache.commons.lang3.tuple.MutablePair;
import org.marc.everest.datatypes.TS;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.model.Model;
import org.oscarehr.e2e.model.PatientModel;

public class CDALens extends AbstractLens<MutablePair<Model, ClinicalDocument>, MutablePair<Model, ClinicalDocument>> {
	public CDALens() {
		get = source -> {
			ClinicalDocument clinicalDocument = source.getRight();

			if(clinicalDocument == null) {
				clinicalDocument = new ClinicalDocument();
			}

			clinicalDocument.setEffectiveTime(new GregorianCalendar(), TS.MINUTE);

			source.setRight(clinicalDocument);
			return source;
		};

		put = (source, target) -> {
			Model patientModel = source.getLeft();

			if(patientModel == null) {
				patientModel = new PatientModel();
			}

			source.setLeft(patientModel);
			return source;
		};
	}
}
