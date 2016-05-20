package org.oscarehr.e2e.lens;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.CreatePatient;
import org.oscarehr.e2e.model.IModel;
import org.oscarehr.e2e.model.PatientModel;

public class CDALensesTest {
	private Pair<IModel, ClinicalDocument> inputPair;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
	}

	@Before
	public void before() {
		PatientModel patientModel = new CreatePatient(Constants.Runtime.VALID_DEMOGRAPHIC).getPatientModel();
		ClinicalDocument clinicalDocument = new ClinicalDocument();
		clinicalDocument.setId(UUID.randomUUID().toString().toUpperCase(), Constants.Runtime.VALID_DEMOGRAPHIC.toString());

		inputPair = Pair.of(patientModel, clinicalDocument);
	}

	@Test
	public void cdaLensGetTest() {
		CDALens lens = new CDALens();
		assertNotNull(lens);

		Pair<IModel, ClinicalDocument> pair = lens.get(inputPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
		assertNotNull(pair.getRight().getEffectiveTime());
		assertFalse(pair.getRight().getEffectiveTime().isNull());
		assertFalse(pair.getRight().getEffectiveTime().isInvalidDate());
	}

	@Test
	public void cdaLensPutTest() {
		CDALens lens = new CDALens();
		assertNotNull(lens);

		Pair<IModel, ClinicalDocument> pair = lens.put(inputPair, inputPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
	}

	// TODO
	@Test
	public void e2eConversionLensGetTest() {
		E2EConversionLens lens = new E2EConversionLens();
		assertNotNull(lens);

		Pair<IModel, ClinicalDocument> pair = lens.get(inputPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
	}

	// TODO
	@Test
	public void e2eConversionLensPutTest() {
		E2EConversionLens lens = new E2EConversionLens();
		assertNotNull(lens);

		Pair<IModel, ClinicalDocument> pair = lens.put(inputPair, inputPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
	}
}
