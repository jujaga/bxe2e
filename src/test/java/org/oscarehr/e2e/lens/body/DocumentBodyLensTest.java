package org.oscarehr.e2e.lens.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Component2;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.StructuredBody;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActRelationshipHasComponent;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_BasicConfidentialityKind;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.CreatePatient;
import org.oscarehr.e2e.model.IModel;
import org.oscarehr.e2e.model.PatientModel;

public class DocumentBodyLensTest {
	private Pair<IModel, ClinicalDocument> inputPair;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
	}

	@Before
	public void before() {
		PatientModel patientModel = new CreatePatient(Constants.Runtime.VALID_DEMOGRAPHIC).getPatientModel();
		ClinicalDocument clinicalDocument = new ClinicalDocument();

		inputPair = Pair.of(patientModel, clinicalDocument);
	}

	@Test
	public void documentBodyLensGetTest() {
		DocumentBodyLens lens = new DocumentBodyLens();
		assertNotNull(lens);

		Pair<IModel, ClinicalDocument> pair = lens.get(inputPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());

		Component2 component = pair.getRight().getComponent();
		assertNotNull(component);
		assertEquals(ActRelationshipHasComponent.HasComponent, component.getTypeCode().getCode());
		assertTrue(component.getContextConductionInd().getValue());

		StructuredBody structuredBody = component.getBodyChoiceIfStructuredBody();
		assertNotNull(structuredBody);
		assertEquals(x_BasicConfidentialityKind.Normal, structuredBody.getConfidentialityCode().getCode());
		assertEquals(Constants.DocumentHeader.LANGUAGE_ENGLISH_CANADIAN, structuredBody.getLanguageCode().getCode());
		assertNotNull(structuredBody.getComponent());
	}

	@Test
	public void documentBodyLensPutTest() {
		DocumentBodyLens lens = new DocumentBodyLens();
		assertNotNull(lens);

		Pair<IModel, ClinicalDocument> pair = lens.put(inputPair, inputPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
	}
}
