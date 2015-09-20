package org.oscarehr.e2e.populator.body;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.marc.everest.datatypes.BL;
import org.marc.everest.datatypes.II;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntry;
import org.oscarehr.e2e.constant.BodyConstants.Labs;
import org.oscarehr.e2e.model.PatientExport;
import org.oscarehr.e2e.model.PatientExport.Lab;
import org.oscarehr.e2e.model.export.body.LabsModel;

public class LabsPopulator extends AbstractBodyPopulator<Lab> {
	private List<Lab> labs = null;

	LabsPopulator(PatientExport patientExport) {
		bodyConstants = Labs.getConstants();
		if(patientExport.isLoaded()) {
			labs = patientExport.getLabs();
		}
	}

	@Override
	public void populate() {
		if(labs != null) {
			for(Lab lab : labs) {
				Entry entry = new Entry(x_ActRelationshipEntry.DRIV, new BL(true));
				entry.setTemplateId(Arrays.asList(new II(bodyConstants.ENTRY_TEMPLATE_ID)));
				entry.setClinicalStatement(populateClinicalStatement(Arrays.asList(lab)));
				entries.add(entry);
			}
		}

		super.populate();
	}

	@Override
	public ClinicalStatement populateClinicalStatement(List<Lab> list) {
		LabsModel labsModel = new LabsModel(list.get(0));
		Observation observation = new Observation(x_ActMoodDocumentObservation.Eventoccurrence);

		observation.setId(labsModel.getIds());
		observation.setCode(labsModel.getCode());
		observation.setText(labsModel.getText());
		observation.setAuthor(labsModel.getAuthor());
		observation.setEntryRelationship(labsModel.getResultOrganizers());

		return observation;
	}

	@Override
	public ClinicalStatement populateNullFlavorClinicalStatement() {
		LabsModel labsModel = new LabsModel(null);
		Observation observation = new Observation(x_ActMoodDocumentObservation.Eventoccurrence);

		observation.setId(labsModel.getIds());
		observation.setCode(labsModel.getCode());
		observation.setText(labsModel.getText());
		observation.setAuthor(labsModel.getAuthor());
		observation.setEntryRelationship(labsModel.getResultOrganizers());

		return observation;
	}

	@Override
	public List<String> populateText() {
		List<String> list = new ArrayList<String>();
		if(labs != null) {
			for(Lab lab : labs) {
				LabsModel labsModel = new LabsModel(lab);
				list.add(labsModel.getTextSummary());
			}
		}

		return list;
	}
}
