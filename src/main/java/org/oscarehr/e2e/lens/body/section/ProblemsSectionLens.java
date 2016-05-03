package org.oscarehr.e2e.lens.body.section;

import java.util.ArrayList;
import java.util.List;

import org.oscarehr.common.model.Dxresearch;
import org.oscarehr.e2e.constant.BodyConstants.Problems;
import org.oscarehr.e2e.model.PatientModel;

public class ProblemsSectionLens extends AbstractSectionLens {
	public ProblemsSectionLens() {
		super(Problems.getConstants());
	}

	@Override
	List<String> createSummaryText(PatientModel patientModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	Boolean containsEntries(PatientModel patientModel) {
		List<Dxresearch> problems = patientModel.getProblems();
		return problems != null && !problems.isEmpty();
	}

	@Override
	PatientModel createModelList(PatientModel patientModel) {
		if(patientModel.getProblems() == null) {
			patientModel.setProblems(new ArrayList<>());
		}
		return patientModel;
	}
}
