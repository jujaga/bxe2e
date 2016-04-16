package org.oscarehr.e2e.lens.header.recordtarget;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Patient;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.PatientRole;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.e2e.lens.common.AbstractLens;

public class RecordTargetLens extends AbstractLens<Pair<Demographic, RecordTarget>, Pair<Demographic, RecordTarget>> {
	public RecordTargetLens() {
		get = source -> {
			RecordTarget recordTarget = source.getRight();

			if(recordTarget.getPatientRole() == null) {
				PatientRole patientRole = new PatientRole();
				patientRole.setPatient(new Patient());

				recordTarget.setContextControlCode(ContextControl.OverridingPropagating);
				recordTarget.setPatientRole(patientRole);
			}

			return new ImmutablePair<>(source.getLeft(), recordTarget);
		};

		put = (source, target) -> {
			return new ImmutablePair<>(target.getLeft(), target.getRight());
		};
	}
}
