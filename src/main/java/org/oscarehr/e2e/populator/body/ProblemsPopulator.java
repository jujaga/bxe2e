package org.oscarehr.e2e.populator.body;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.marc.everest.datatypes.BL;
import org.marc.everest.datatypes.II;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntry;
import org.oscarehr.common.model.Dxresearch;
import org.oscarehr.e2e.constant.BodyConstants.Problems;
import org.oscarehr.e2e.model.PatientExport;
import org.oscarehr.e2e.model.export.body.ProblemsModel;

public class ProblemsPopulator extends AbstractBodyPopulator<Dxresearch> {
	private List<Dxresearch> allProblems = null;
	private List<Dxresearch> problems = null;

	ProblemsPopulator(PatientExport patientExport) {
		bodyConstants = Problems.getConstants();
		if(patientExport.isLoaded()) {
			allProblems = patientExport.getProblems();
		}
		problems = new ArrayList<Dxresearch>();

		if(allProblems != null) {
			for(Dxresearch problem : allProblems) {
				if(problem.getStatus() != 'D' && problem.getCodingSystem().equalsIgnoreCase("icd9")) {
					this.problems.add(problem);
				}
			}
		}
	}

	@Override
	public void populate() {
		for(Dxresearch problem : problems) {
			if(problem.getCodingSystem().equals("icd9")) {
				Entry entry = new Entry(x_ActRelationshipEntry.DRIV, new BL(true));
				entry.setTemplateId(Arrays.asList(new II(bodyConstants.ENTRY_TEMPLATE_ID)));
				entry.setClinicalStatement(populateClinicalStatement(Arrays.asList(problem)));
				entries.add(entry);
			}
		}

		super.populate();
	}

	@Override
	public ClinicalStatement populateClinicalStatement(List<Dxresearch> problem) {
		ProblemsModel problemsModel = new ProblemsModel(problem.get(0));
		Observation observation = new Observation(x_ActMoodDocumentObservation.Eventoccurrence);
		ArrayList<EntryRelationship> entryRelationships = new ArrayList<EntryRelationship>();

		observation.setId(problemsModel.getIds());
		observation.setCode(problemsModel.getCode());
		observation.setText(problemsModel.getText());
		observation.setStatusCode(problemsModel.getStatusCode());
		observation.setEffectiveTime(problemsModel.getEffectiveTime());
		observation.setValue(problemsModel.getValue());
		observation.setAuthor(problemsModel.getAuthor());

		entryRelationships.add(problemsModel.getSecondaryCodeICD9());
		entryRelationships.add(problemsModel.getDiagnosisDate());

		observation.setEntryRelationship(entryRelationships);

		return observation;
	}

	@Override
	public ClinicalStatement populateNullFlavorClinicalStatement() {
		ProblemsModel problemsModel = new ProblemsModel(null);
		Observation observation = new Observation(x_ActMoodDocumentObservation.Eventoccurrence);

		observation.setId(problemsModel.getIds());
		observation.setCode(problemsModel.getCode());
		observation.setAuthor(problemsModel.getAuthor());

		return observation;
	}

	@Override
	public List<String> populateText() {
		List<String> list = new ArrayList<String>();
		for(Dxresearch problem : problems) {
			if(problem.getCodingSystem().equals("icd9")) {
				ProblemsModel problemsModel = new ProblemsModel(problem);
				list.add(problemsModel.getTextSummary());
			}
		}

		return list;
	}
}
