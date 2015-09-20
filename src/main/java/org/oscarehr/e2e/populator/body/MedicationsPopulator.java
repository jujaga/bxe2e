package org.oscarehr.e2e.populator.body;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.marc.everest.datatypes.BL;
import org.marc.everest.datatypes.II;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.SubstanceAdministration;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntry;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_DocumentSubstanceMood;
import org.oscarehr.common.model.Drug;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.constant.BodyConstants.Medications;
import org.oscarehr.e2e.model.PatientExport;
import org.oscarehr.e2e.model.export.body.MedicationsModel;

public class MedicationsPopulator extends AbstractBodyPopulator<Drug> {
	private List<Drug> allDrugs = null;
	private Map<Integer, ArrayList<Drug>> mapDrugs = null;

	MedicationsPopulator(PatientExport patientExport) {
		bodyConstants = Medications.getConstants();
		mapDrugs = new HashMap<Integer, ArrayList<Drug>>();
		if(patientExport.isLoaded()) {
			allDrugs = patientExport.getMedications();
		}
		Collections.reverse(allDrugs); // Order recent drugs first

		if(allDrugs != null) {
			for(Drug drug : allDrugs) {
				Integer din;
				try {
					din = Integer.parseInt(drug.getRegionalIdentifier());
				} catch (NumberFormatException e) {
					din = Constants.Runtime.INVALID_VALUE;
				}

				if(mapDrugs.containsKey(din)) {
					mapDrugs.get(din).add(drug);
				} else {
					mapDrugs.put(din, new ArrayList<Drug>(Arrays.asList(drug)));
				}
			}
		}
	}

	@Override
	public void populate() {
		for(List<Drug> medication : mapDrugs.values()) {
			Entry entry = new Entry(x_ActRelationshipEntry.DRIV, new BL(true));
			entry.setTemplateId(Arrays.asList(new II(bodyConstants.ENTRY_TEMPLATE_ID)));
			entry.setClinicalStatement(populateClinicalStatement(medication));
			entries.add(entry);
		}

		super.populate();
	}

	@Override
	public ClinicalStatement populateClinicalStatement(List<Drug> list) {
		Boolean medicationEventPopulated = false;
		SubstanceAdministration substanceAdministration = new SubstanceAdministration(x_DocumentSubstanceMood.Eventoccurrence);
		ArrayList<EntryRelationship> entryRelationships = new ArrayList<EntryRelationship>();

		for(Drug drug : list) {
			MedicationsModel medicationsModel = new MedicationsModel(drug);

			if(!medicationEventPopulated) {
				substanceAdministration.setId(medicationsModel.getIds());
				substanceAdministration.setCode(medicationsModel.getCode());
				substanceAdministration.setStatusCode(medicationsModel.getStatusCode());
				substanceAdministration.setConsumable(medicationsModel.getConsumable());

				entryRelationships.add(medicationsModel.getRecordType());
				entryRelationships.add(medicationsModel.getLastReviewDate());

				medicationEventPopulated = true;
			}

			entryRelationships.add(medicationsModel.getPrescriptionInformation());
		}

		substanceAdministration.setEntryRelationship(entryRelationships);

		return substanceAdministration;
	}

	@Override
	public ClinicalStatement populateNullFlavorClinicalStatement() {
		MedicationsModel medicationsModel = new MedicationsModel(null);
		SubstanceAdministration substanceAdministration = new SubstanceAdministration(x_DocumentSubstanceMood.Eventoccurrence);
		ArrayList<EntryRelationship> entryRelationships = new ArrayList<EntryRelationship>();

		substanceAdministration.setId(medicationsModel.getIds());
		substanceAdministration.setCode(medicationsModel.getCode());
		substanceAdministration.setStatusCode(medicationsModel.getStatusCode());
		substanceAdministration.setConsumable(medicationsModel.getConsumable());

		entryRelationships.add(medicationsModel.getRecordType());
		entryRelationships.add(medicationsModel.getLastReviewDate());

		substanceAdministration.setEntryRelationship(entryRelationships);

		return substanceAdministration;
	}

	@Override
	public List<String> populateText() {
		List<String> list = new ArrayList<String>();
		for(List<Drug> medication : mapDrugs.values()) {
			MedicationsModel medicationsModel = new MedicationsModel(medication.get(0));
			list.add(medicationsModel.getTextSummary());
		}

		return list;
	}
}
