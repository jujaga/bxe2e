package org.oscarehr.e2e.lens.header.recordtarget;

import org.apache.log4j.Logger;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Patient;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.PatientRole;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.e2e.lens.AbstractLens;
import org.oscarehr.e2e.model.export.header.RecordTargetModel;

public class RecordTargetLens extends AbstractLens<Demographic, RecordTarget> {
	public RecordTargetLens() {
		log = Logger.getLogger(RecordTargetLens.class.getName());

		get = demographic -> {
			RecordTargetModel recordTargetModel = new RecordTargetModel(demographic); // Temporary

			RecordTarget recordTarget = new RecordTarget();
			PatientRole patientRole = new PatientRole();
			Patient patient = new Patient();

			recordTarget.setContextControlCode(ContextControl.OverridingPropagating);
			recordTarget.setPatientRole(patientRole);

			patientRole.setId(new HinIdLens().get(demographic.getHin()));
			patientRole.setAddr(new AddressLens().get(demographic));
			patientRole.setTelecom(recordTargetModel.getTelecoms()); // Temporary
			patientRole.setPatient(patient);

			patient.setName(new NameLens().get(demographic));
			patient.setAdministrativeGenderCode(recordTargetModel.getGender()); // Temporary
			patient.setBirthTime(recordTargetModel.getBirthDate()); // Temporary
			patient.setLanguageCommunication(recordTargetModel.getLanguages()); // Temporary

			return recordTarget;
		};

		// TODO Put Function
	}
}
