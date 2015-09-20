package org.oscarehr.e2e.populator.header;

import java.util.ArrayList;
import java.util.Arrays;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Patient;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.PatientRole;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;
import org.oscarehr.e2e.model.PatientExport;
import org.oscarehr.e2e.model.export.header.RecordTargetModel;
import org.oscarehr.e2e.populator.AbstractPopulator;

class RecordTargetPopulator extends AbstractPopulator {
	private final RecordTargetModel recordTargetModel;

	RecordTargetPopulator(PatientExport patientExport) {
		recordTargetModel = new RecordTargetModel(patientExport.getDemographic());
	}

	@Override
	public void populate() {
		RecordTarget recordTarget = new RecordTarget();
		PatientRole patientRole = new PatientRole();
		Patient patient = new Patient();

		recordTarget.setContextControlCode(ContextControl.OverridingPropagating);
		recordTarget.setPatientRole(patientRole);

		patientRole.setId(recordTargetModel.getIds());
		patientRole.setAddr(recordTargetModel.getAddresses());
		patientRole.setTelecom(recordTargetModel.getTelecoms());
		patientRole.setPatient(patient);

		patient.setName(recordTargetModel.getNames());
		patient.setAdministrativeGenderCode(recordTargetModel.getGender());
		patient.setBirthTime(recordTargetModel.getBirthDate());
		patient.setLanguageCommunication(recordTargetModel.getLanguages());

		clinicalDocument.setRecordTarget(new ArrayList<RecordTarget>(Arrays.asList(recordTarget)));
	}
}
