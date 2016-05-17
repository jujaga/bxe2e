package org.oscarehr.e2e.model;

import org.junit.Test;
import org.oscarehr.common.util.EntityModelUtils;

public class PatientModelTest {
	@Test
	public void patientModelMethodsTest() {
		EntityModelUtils.invokeMethodsForModelClass(new PatientModel());
	}
}
