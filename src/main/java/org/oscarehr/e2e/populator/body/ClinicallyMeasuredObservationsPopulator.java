package org.oscarehr.e2e.populator.body;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.marc.everest.datatypes.BL;
import org.marc.everest.datatypes.II;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Organizer;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActClassDocumentEntryOrganizer;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntry;
import org.oscarehr.common.model.Measurement;
import org.oscarehr.e2e.constant.BodyConstants.ClinicallyMeasuredObservations;
import org.oscarehr.e2e.model.PatientExport;
import org.oscarehr.e2e.model.export.body.ClinicallyMeasuredObservationsModel;

public class ClinicallyMeasuredObservationsPopulator extends AbstractBodyPopulator<Measurement> {
	private List<Measurement> measurements = null;

	ClinicallyMeasuredObservationsPopulator(PatientExport patientExport) {
		bodyConstants = ClinicallyMeasuredObservations.getConstants();
		if(patientExport.isLoaded()) {
			measurements = patientExport.getMeasurements();
		}
	}

	@Override
	public void populate() {
		if(measurements != null) {
			for(Measurement measurement : measurements) {
				Entry entry = new Entry(x_ActRelationshipEntry.DRIV, new BL(true));
				entry.setTemplateId(Arrays.asList(new II(bodyConstants.ENTRY_TEMPLATE_ID)));
				entry.setClinicalStatement(populateClinicalStatement(Arrays.asList(measurement)));
				entries.add(entry);
			}
		}

		super.populate();
	}

	@Override
	public ClinicalStatement populateClinicalStatement(List<Measurement> measurement) {
		ClinicallyMeasuredObservationsModel cmoModel = new ClinicallyMeasuredObservationsModel(measurement.get(0));
		Organizer organizer = new Organizer(x_ActClassDocumentEntryOrganizer.CLUSTER);

		organizer.setId(cmoModel.getIds());
		organizer.setCode(cmoModel.getCode());
		organizer.setStatusCode(cmoModel.getStatusCode());
		organizer.setAuthor(cmoModel.getAuthor());
		organizer.setComponent(cmoModel.getComponents());

		return organizer;
	}

	@Override
	public ClinicalStatement populateNullFlavorClinicalStatement() {
		return null;
	}

	@Override
	public List<String> populateText() {
		List<String> list = new ArrayList<String>();
		if(measurements != null) {
			for(Measurement measurement : measurements) {
				ClinicallyMeasuredObservationsModel cmoModel = new ClinicallyMeasuredObservationsModel(measurement);
				list.add(cmoModel.getTextSummary());
			}
		}

		return list;
	}
}
