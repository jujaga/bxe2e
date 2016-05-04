package org.oscarehr.e2e.lens.body.section;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.oscarehr.common.model.Dxresearch;
import org.oscarehr.e2e.constant.BodyConstants.Problems;
import org.oscarehr.e2e.model.PatientModel;
import org.oscarehr.e2e.util.EverestUtils;

public class ProblemsSectionLens extends AbstractSectionLens {
	public ProblemsSectionLens() {
		super(Problems.getConstants());
	}

	@Override
	List<String> createSummaryText(PatientModel patientModel) {
		List<String> summary = null;
		List<Dxresearch> problems = patientModel.getProblems();
		if(problems != null && !problems.isEmpty()) {
			summary = problems.stream()
					.filter(e -> e.getCodingSystem().equals("icd9"))
					.map(e -> {
						StringBuilder sb = new StringBuilder();
						String description = EverestUtils.getICD9Description(e.getDxresearchCode());

						if(!EverestUtils.isNullorEmptyorWhitespace(e.getDxresearchCode())) {
							sb.append("ICD9: " + e.getDxresearchCode());
						}
						if(!EverestUtils.isNullorEmptyorWhitespace(description)) {
							sb.append(" - " + description);
						}

						return sb.toString();
					})
					.collect(Collectors.toList());
		}

		return summary;
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
