package org.oscarehr.e2e.lens.body.section;

import java.util.List;

import org.oscarehr.e2e.constant.BodyConstants.AdvanceDirectives;
import org.oscarehr.e2e.model.PatientModel;

public class AdvanceDirectivesSectionLens extends AbstractSectionLens {
	public AdvanceDirectivesSectionLens() {
		super(AdvanceDirectives.getConstants());
	}

	@Override
	List<String> createSummaryText(PatientModel patientModel) {
		return null;
	}

	@Override
	Boolean containsEntries(PatientModel patientModel) {
		return false;
	}

	@Override
	PatientModel createModelList(PatientModel patientModel) {
		return patientModel;
	}
}
