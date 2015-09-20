package org.oscarehr.e2e.populator.header;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Test;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Patient;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.PatientRole;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.oscarehr.e2e.populator.AbstractPopulatorTest;

public class RecordTargetPopulatorTest extends AbstractPopulatorTest {
	@Test
	public void recordTargetTest() {
		ArrayList<RecordTarget> recordTargets = clinicalDocument.getRecordTarget();
		assertNotNull(recordTargets);
		assertEquals(1, recordTargets.size());

		RecordTarget recordTarget = recordTargets.get(0);
		assertNotNull(recordTarget);
	}

	@Test
	public void patientRoleTest() {
		PatientRole patientRole = clinicalDocument.getRecordTarget().get(0).getPatientRole();
		assertNotNull(patientRole);
	}

	@Test
	public void patientTest() {
		PatientRole patientRole = clinicalDocument.getRecordTarget().get(0).getPatientRole();
		Patient patient = patientRole.getPatient();
		assertNotNull(patient);
	}
}
