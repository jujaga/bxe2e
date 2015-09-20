package org.oscarehr.e2e.model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.PatientExport;

public class PatientExportTest {
	@Test
	public void patientExportTest() {
		PatientExport patientExport = new PatientExport(Constants.Runtime.VALID_DEMOGRAPHIC);
		assertTrue(patientExport.isLoaded());
	}

	@Test
	public void emptyPatientExportTest() {
		PatientExport patientExport = new PatientExport(Constants.Runtime.EMPTY_DEMOGRAPHIC);
		assertTrue(patientExport.isLoaded());
	}
}
