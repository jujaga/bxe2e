package org.oscarehr.e2e.lens;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.II;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_BasicConfidentialityKind;
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

	@Test
	public void e2eConversionLensGetTest() {
		E2EConversionLens lens = new E2EConversionLens();
		assertNotNull(lens);

		Pair<IModel, ClinicalDocument> pair = lens.get(inputPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());

		ClinicalDocument clinicalDocument = pair.getRight();
		assertNotNull(clinicalDocument.getRealmCode());
		assertNotNull(clinicalDocument.getRealmCode().get(0));
		assertNotNull(clinicalDocument.getRealmCode().get(0).getCode());
		assertEquals(Constants.DocumentHeader.E2E_DTC_CLINICAL_DOCUMENT_TYPE_REALM_CODE, clinicalDocument.getRealmCode().get(0).getCode().getCode());

		assertNotNull(clinicalDocument.getTypeId());
		assertEquals(Constants.DocumentHeader.E2E_DTC_CLINICAL_DOCUMENT_TYPE_ID, clinicalDocument.getTypeId().getRoot());
		assertEquals(Constants.DocumentHeader.E2E_DTC_CLINICAL_DOCUMENT_TYPE_ID_EXTENSION, clinicalDocument.getTypeId().getExtension());

		assertNotNull(clinicalDocument.getTemplateId());
		assertEquals(2, clinicalDocument.getTemplateId().size());
		assertTrue(clinicalDocument.getTemplateId().contains(new II(Constants.DocumentHeader.TEMPLATE_ID)));
		assertTrue(clinicalDocument.getTemplateId().contains(new II(Constants.EMRConversionDocument.TEMPLATE_ID)));

		assertNotNull(clinicalDocument.getId());
		assertNotNull(clinicalDocument.getId().getRoot());
		assertEquals(Constants.Runtime.VALID_DEMOGRAPHIC.toString(), clinicalDocument.getId().getExtension());

		assertNotNull(clinicalDocument.getCode());
		assertEquals(Constants.EMRConversionDocument.CODE, clinicalDocument.getCode());

		assertNotNull(clinicalDocument.getTitle());
		assertTrue(clinicalDocument.getTitle().getValue().contains("E2E-DTC Record of "));

		assertNotNull(clinicalDocument.getConfidentialityCode());
		assertEquals(x_BasicConfidentialityKind.Normal, clinicalDocument.getConfidentialityCode().getCode());

		assertNotNull(clinicalDocument.getLanguageCode());
		assertEquals(Constants.DocumentHeader.LANGUAGE_ENGLISH_CANADIAN, clinicalDocument.getLanguageCode().getCode());
	}

	@Test
	public void e2eConversionLensPutTest() {
		((PatientModel) inputPair.getLeft()).getDemographic().setDemographicNo(null);

		E2EConversionLens lens = new E2EConversionLens();
		assertNotNull(lens);

		Pair<IModel, ClinicalDocument> pair = lens.put(inputPair, inputPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());

		PatientModel patientModel = (PatientModel) pair.getLeft();
		assertTrue(patientModel.isLoaded());
		assertNotNull(patientModel.getDemographic());
		assertEquals(Constants.Runtime.VALID_DEMOGRAPHIC, patientModel.getDemographic().getDemographicNo());
	}
}
