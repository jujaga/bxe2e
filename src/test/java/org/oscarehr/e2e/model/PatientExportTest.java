package org.oscarehr.e2e.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.oscarehr.e2e.constant.Constants;

public class PatientExportTest {
	@Test
	public void patientExportTest() {
		PatientModel patientModel = new CreatePatient(Constants.Runtime.VALID_DEMOGRAPHIC).getPatientModel();
		assertTrue(patientModel.isLoaded());
	}

	@Test
	public void emptyPatientExportTest() {
		PatientModel patientModel = new CreatePatient(Constants.Runtime.EMPTY_DEMOGRAPHIC).getPatientModel();
		assertTrue(patientModel.isLoaded());
	}

	@Test
	public void nullPatientExportTest() {
		PatientModel patientModel = new CreatePatient(null).getPatientModel();
		assertFalse(patientModel.isLoaded());
	}
}
