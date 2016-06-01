package org.oscarehr.e2e.lens.body.section;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.BL;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Component2;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.StructuredBody;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActRelationshipHasComponent;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_BasicConfidentialityKind;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.CreatePatient;
import org.oscarehr.e2e.model.IModel;
import org.oscarehr.e2e.model.PatientModel;

public class SectionLensesTest {
	private Pair<IModel, ClinicalDocument> getPair;
	private Pair<IModel, ClinicalDocument> putPair;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
	}

	@Before
	public void before() {
		PatientModel patientModel = new CreatePatient(Constants.Runtime.VALID_DEMOGRAPHIC).getPatientModel();

		StructuredBody structuredBody = new StructuredBody();
		structuredBody.setConfidentialityCode(x_BasicConfidentialityKind.Normal);
		structuredBody.setLanguageCode(Constants.DocumentHeader.LANGUAGE_ENGLISH_CANADIAN);
		structuredBody.setComponent(new ArrayList<>());

		Component2 component = new Component2(ActRelationshipHasComponent.HasComponent, new BL(true), structuredBody);

		ClinicalDocument clinicalDocument = new ClinicalDocument();
		clinicalDocument.setComponent(component);

		getPair = Pair.of(patientModel, clinicalDocument);
		putPair = Pair.of(new PatientModel(), clinicalDocument);
	}

	@Test
	public void abstractSectionLensGetTest() {
		ProblemsSectionLens lens = new ProblemsSectionLens();
		assertNotNull(lens);

		Pair<IModel, ClinicalDocument> pair = lens.get(getPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());

		// Test if section exists already
		pair = lens.get(pair);
		assertEquals(1, pair.getRight().getComponent().getBodyChoiceIfStructuredBody().getComponent().size());
	}

	@Test
	public void abstractSectionLensPutTest() {
		ProblemsSectionLens lens = new ProblemsSectionLens();
		assertNotNull(lens);

		Pair<IModel, ClinicalDocument> pair = lens.put(putPair, putPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());

		// Test if section exists already
		putPair = Pair.of(putPair.getLeft(), lens.get(getPair).getRight());
		pair = lens.put(putPair, putPair);
		assertNotNull(((PatientModel) pair.getLeft()).getProblems());
	}

	@Test
	public void advanceDirectivesSectionLensGetTest() {
		AdvanceDirectivesSectionLens lens = new AdvanceDirectivesSectionLens();
		assertNotNull(lens);
		assertNull(lens.createSummaryText((PatientModel) getPair.getLeft()));
		assertFalse(lens.containsEntries((PatientModel) getPair.getLeft()));
		assertNotNull(lens.createModelList((PatientModel) getPair.getLeft()));

		Pair<IModel, ClinicalDocument> pair = lens.get(getPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
	}

	@Test
	public void advanceDirectivesSectionLensPutTest() {
		AdvanceDirectivesSectionLens lens = new AdvanceDirectivesSectionLens();
		assertNotNull(lens);
		assertNull(lens.createSummaryText((PatientModel) putPair.getLeft()));
		assertFalse(lens.containsEntries((PatientModel) putPair.getLeft()));
		assertNotNull(lens.createModelList((PatientModel) putPair.getLeft()));

		Pair<IModel, ClinicalDocument> pair = lens.put(putPair, putPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
	}

	@Test
	public void problemsSectionLensGetTest() {
		ProblemsSectionLens lens = new ProblemsSectionLens();
		assertNotNull(lens);
		assertNotNull(lens.createSummaryText((PatientModel) getPair.getLeft()));
		assertEquals(2, lens.createSummaryText((PatientModel) getPair.getLeft()).size());
		assertTrue(lens.containsEntries((PatientModel) getPair.getLeft()));
		assertNotNull(lens.createModelList((PatientModel) getPair.getLeft()));

		Pair<IModel, ClinicalDocument> pair = lens.get(getPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
	}

	@Test
	public void problemsSectionLensPutTest() {
		ProblemsSectionLens lens = new ProblemsSectionLens();
		assertNotNull(lens);
		assertNull(lens.createSummaryText((PatientModel) putPair.getLeft()));
		assertFalse(lens.containsEntries((PatientModel) putPair.getLeft()));
		assertNotNull(lens.createModelList((PatientModel) putPair.getLeft()));

		Pair<IModel, ClinicalDocument> pair = lens.put(putPair, putPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
	}
}
