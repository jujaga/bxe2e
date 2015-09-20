package org.oscarehr.e2e.populator.body;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.marc.everest.datatypes.BL;
import org.marc.everest.datatypes.II;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Act;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActClassDocumentEntryAct;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntry;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_DocumentActMood;
import org.oscarehr.common.model.Allergy;
import org.oscarehr.e2e.constant.BodyConstants.Allergies;
import org.oscarehr.e2e.model.PatientExport;
import org.oscarehr.e2e.model.export.body.AllergiesModel;

public class AllergiesPopulator extends AbstractBodyPopulator<Allergy> {
	private List<Allergy> allergies = null;

	AllergiesPopulator(PatientExport patientExport) {
		bodyConstants = Allergies.getConstants();
		if(patientExport.isLoaded()) {
			allergies = patientExport.getAllergies();
		}
	}

	@Override
	public void populate() {
		if(allergies != null) {
			for(Allergy allergy : allergies) {
				Entry entry = new Entry(x_ActRelationshipEntry.DRIV, new BL(true));
				entry.setTemplateId(Arrays.asList(new II(bodyConstants.ENTRY_TEMPLATE_ID)));
				entry.setClinicalStatement(populateClinicalStatement(Arrays.asList(allergy)));
				entries.add(entry);
			}
		}

		super.populate();
	}

	@Override
	public ClinicalStatement populateClinicalStatement(List<Allergy> list) {
		AllergiesModel allergiesModel = new AllergiesModel(list.get(0));
		Act act = new Act(x_ActClassDocumentEntryAct.Act, x_DocumentActMood.Eventoccurrence);
		ArrayList<EntryRelationship> entryRelationships = new ArrayList<EntryRelationship>();

		act.setId(allergiesModel.getIds());
		act.setCode(allergiesModel.getCode());
		act.setStatusCode(allergiesModel.getStatusCode());
		act.setEffectiveTime(allergiesModel.getEffectiveTime());

		entryRelationships.add(allergiesModel.getAllergyObservation());

		act.setEntryRelationship(entryRelationships);
		return act;
	}

	@Override
	public ClinicalStatement populateNullFlavorClinicalStatement() {
		AllergiesModel allergiesModel = new AllergiesModel(null);
		Act act = new Act(x_ActClassDocumentEntryAct.Act, x_DocumentActMood.Eventoccurrence);
		ArrayList<EntryRelationship> entryRelationships = new ArrayList<EntryRelationship>();

		act.setId(allergiesModel.getIds());
		act.setCode(allergiesModel.getCode());
		act.setStatusCode(allergiesModel.getStatusCode());
		act.setEffectiveTime(allergiesModel.getEffectiveTime());

		entryRelationships.add(allergiesModel.getAllergyObservation());

		act.setEntryRelationship(entryRelationships);
		return act;
	}

	@Override
	public List<String> populateText() {
		List<String> list = new ArrayList<String>();
		if(allergies != null) {
			for(Allergy allergy : allergies) {
				AllergiesModel allergiesModel = new AllergiesModel(allergy);
				list.add(allergiesModel.getTextSummary());
			}
		}

		return list;
	}
}
