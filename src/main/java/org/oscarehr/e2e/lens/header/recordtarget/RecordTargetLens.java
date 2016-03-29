package org.oscarehr.e2e.lens.header.recordtarget;

import org.apache.commons.lang3.tuple.MutablePair;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Patient;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.PatientRole;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.e2e.lens.common.AbstractLens;

public class RecordTargetLens extends AbstractLens<MutablePair<Demographic, RecordTarget>, MutablePair<Demographic, RecordTarget>> {
	public RecordTargetLens() {
		get = source -> {
			RecordTarget recordTarget = new RecordTarget();
			PatientRole patientRole = new PatientRole();
			Patient patient = new Patient();

			recordTarget.setContextControlCode(ContextControl.OverridingPropagating);
			recordTarget.setPatientRole(patientRole);
			patientRole.setPatient(patient);

			source.setRight(recordTarget);
			return source;
		};

		put = (source, target) -> {
			Demographic demographic = source.getLeft();

			if(demographic == null) {
				demographic = new Demographic();
			}

			source.setLeft(demographic);
			return source;
		};
	}
}
