package org.oscarehr.e2e.rule.header;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Patient;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.PatientRole;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.e2e.lens.header.recordtarget.AddressLens;
import org.oscarehr.e2e.lens.header.recordtarget.BirthDateLens;
import org.oscarehr.e2e.lens.header.recordtarget.GenderLens;
import org.oscarehr.e2e.lens.header.recordtarget.HinIdLens;
import org.oscarehr.e2e.lens.header.recordtarget.LanguageLens;
import org.oscarehr.e2e.lens.header.recordtarget.NameLens;
import org.oscarehr.e2e.lens.header.recordtarget.RecordTargetLens;
import org.oscarehr.e2e.lens.header.recordtarget.TelecomLens;
import org.oscarehr.e2e.rule.AbstractRule;

public class RecordTargetRule extends AbstractRule<Demographic, RecordTarget> {
	public RecordTargetRule(Demographic source) {
		super(source, null);
	}

	public RecordTargetRule(Demographic source, RecordTarget target) {
		super(source, target);
	}

	@Override
	protected void defineLens() {
		lens = new RecordTargetLens();
	}

	@Override
	protected void apply() {
		if(pair.right == null) {
			// Do forward transformation
			RecordTarget recordTarget = new RecordTarget();
			PatientRole patientRole = new PatientRole();
			Patient patient = new Patient();

			recordTarget.setContextControlCode(ContextControl.OverridingPropagating);
			recordTarget.setPatientRole(patientRole);

			patientRole.setId(new HinIdLens().get(pair.left.getHin()));
			patientRole.setAddr(new AddressLens().get(pair.left));
			patientRole.setTelecom(new TelecomLens().get(pair.left));
			patientRole.setPatient(patient);

			patient.setName(new NameLens().get(pair.left));
			patient.setAdministrativeGenderCode(new GenderLens().get(pair.left.getSex()));
			patient.setBirthTime(new BirthDateLens().get(pair.left));
			patient.setLanguageCommunication(new LanguageLens().get(pair.left.getOfficialLanguage()));

			pair.right = recordTarget;
		}

		if(pair.left == null) {
			// Do backward transformation
		}
	}
}
