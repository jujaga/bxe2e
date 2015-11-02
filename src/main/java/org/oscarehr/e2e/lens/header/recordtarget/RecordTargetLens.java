package org.oscarehr.e2e.lens.header.recordtarget;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Patient;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.PatientRole;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.e2e.lens.AbstractLens;

public class RecordTargetLens extends AbstractLens<Demographic, RecordTarget> {
	public RecordTargetLens() {
		get = demographic -> {
			RecordTarget recordTarget = new RecordTarget();
			PatientRole patientRole = new PatientRole();
			Patient patient = new Patient();

			recordTarget.setContextControlCode(ContextControl.OverridingPropagating);
			recordTarget.setPatientRole(patientRole);

			patientRole.setId(new HinIdLens().get(demographic.getHin()));
			patientRole.setAddr(new AddressLens().get(demographic));
			patientRole.setTelecom(new TelecomLens().get(demographic));
			patientRole.setPatient(patient);

			patient.setName(new NameLens().get(demographic));
			patient.setAdministrativeGenderCode(new GenderLens().get(demographic.getSex()));
			patient.setBirthTime(new BirthDateLens().get(demographic));
			patient.setLanguageCommunication(new LanguageLens().get(demographic.getOfficialLanguage()));

			return recordTarget;
		};

		// TODO Put Function
	}
}
