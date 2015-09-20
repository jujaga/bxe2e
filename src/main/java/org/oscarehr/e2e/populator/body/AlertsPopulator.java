package org.oscarehr.e2e.populator.body;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.marc.everest.datatypes.BL;
import org.marc.everest.datatypes.II;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntry;
import org.oscarehr.casemgmt.model.CaseManagementNote;
import org.oscarehr.e2e.constant.BodyConstants.Alerts;
import org.oscarehr.e2e.extension.ObservationWithConfidentialityCode;
import org.oscarehr.e2e.model.PatientExport;
import org.oscarehr.e2e.model.export.body.AlertsModel;

public class AlertsPopulator extends AbstractBodyPopulator<CaseManagementNote> {
	private List<CaseManagementNote> alerts = null;

	AlertsPopulator(PatientExport patientExport) {
		bodyConstants = Alerts.getConstants();
		if(patientExport.isLoaded()) {
			alerts = patientExport.getAlerts();
		}
	}

	@Override
	public void populate() {
		if(alerts != null) {
			for(CaseManagementNote alert : alerts) {
				Entry entry = new Entry(x_ActRelationshipEntry.DRIV, new BL(true));
				entry.setTemplateId(Arrays.asList(new II(bodyConstants.ENTRY_TEMPLATE_ID)));
				entry.setClinicalStatement(populateClinicalStatement(Arrays.asList(alert)));
				entries.add(entry);
			}
		}

		super.populate();
	}

	@Override
	public ClinicalStatement populateClinicalStatement(List<CaseManagementNote> list) {
		AlertsModel alertsModel = new AlertsModel(list.get(0));
		ObservationWithConfidentialityCode observation = new ObservationWithConfidentialityCode(x_ActMoodDocumentObservation.Eventoccurrence);

		observation.setId(alertsModel.getIds());
		observation.setCode(alertsModel.getCode());
		observation.setStatusCode(alertsModel.getStatusCode());
		observation.setText(alertsModel.getText());
		observation.setEffectiveTime(alertsModel.getEffectiveTime());
		observation.setConfidentialityCode(alertsModel.getConfidentiality());

		return observation;
	}

	@Override
	public ClinicalStatement populateNullFlavorClinicalStatement() {
		AlertsModel alertsModel = new AlertsModel(null);
		ObservationWithConfidentialityCode observation = new ObservationWithConfidentialityCode(x_ActMoodDocumentObservation.Eventoccurrence);

		observation.setId(alertsModel.getIds());
		observation.setCode(alertsModel.getCode());
		observation.setStatusCode(alertsModel.getStatusCode());
		observation.setText(alertsModel.getText());
		observation.setEffectiveTime(alertsModel.getEffectiveTime());
		observation.setConfidentialityCode(alertsModel.getConfidentiality());

		return observation;
	}

	@Override
	public List<String> populateText() {
		List<String> list = new ArrayList<String>();
		if(alerts != null) {
			for(CaseManagementNote alert : alerts) {
				AlertsModel alertsModel = new AlertsModel(alert);
				list.add(alertsModel.getTextSummary());
			}
		}

		return list;
	}
}
