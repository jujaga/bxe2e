package org.oscarehr.e2e.transformer;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.CreatePatient;
import org.oscarehr.e2e.model.PatientModel;

public class E2EConversionTransformerTest {
	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
	}

	@Test(expected=NullPointerException.class)
	public void nullOriginalTest() {
		new E2EConversionTransformer(null, null, null);
	}

	@Test
	public void modelTransformationBaseTest() {
		E2EConversionTransformer transformer = new E2EConversionTransformer(new PatientModel());
		assertNotNull(transformer.getTarget());
		assertNull(transformer.getPatientUUID());
	}

	@Test
	public void modelTransformationTest() {
		PatientModel patientModel = new CreatePatient(Constants.Runtime.VALID_DEMOGRAPHIC).getPatientModel();
		E2EConversionTransformer transformer = new E2EConversionTransformer(patientModel);
		assertNotNull(transformer.getTarget());
		assertNotNull(transformer.getPatientUUID());
	}

	@Test
	public void targetTransformationBaseTest() {
		E2EConversionTransformer transformer = new E2EConversionTransformer(new ClinicalDocument());
		assertNotNull(transformer.getModel());
		assertNull(transformer.getPatientUUID());
	}

	@Test
	public void targetTransformationTest() {
		PatientModel patientModel = new CreatePatient(Constants.Runtime.VALID_DEMOGRAPHIC).getPatientModel();
		E2EConversionTransformer setupTransformer = new E2EConversionTransformer(patientModel);

		ClinicalDocument clinicalDocument = setupTransformer.getTarget();
		E2EConversionTransformer transformer = new E2EConversionTransformer(clinicalDocument);
		assertNotNull(transformer.getModel());
		assertNotNull(transformer.getPatientUUID());
	}
}
