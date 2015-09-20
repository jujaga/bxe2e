package org.oscarehr.e2e.model.export.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.SetOperator;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.IVL;
import org.marc.everest.datatypes.interfaces.ISetComponent;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Consumable;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.SubstanceAdministration;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_DocumentSubstanceMood;
import org.oscarehr.common.dao.DrugDao;
import org.oscarehr.common.model.Drug;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.export.template.MedicationPrescriptionEventModel;
import org.oscarehr.e2e.util.EverestUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MedicationPrescriptionEventModelTest {
	private static ApplicationContext context;
	private static DrugDao dao;
	private static Drug drug;
	private static Drug nullDrug;
	private static MedicationPrescriptionEventModel mpeModel;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		dao = context.getBean(DrugDao.class);
		mpeModel = new MedicationPrescriptionEventModel();
	}

	@Before
	public void before() {
		drug = dao.findByDemographicId(Constants.Runtime.VALID_DEMOGRAPHIC).get(0);
		nullDrug = new Drug();
	}

	@Test
	public void medicationPrescriptionEventStructureTest() {
		EntryRelationship entryRelationship = mpeModel.getEntryRelationship(drug);
		assertNotNull(entryRelationship);
		assertEquals(x_ActRelationshipEntryRelationship.HasComponent, entryRelationship.getTypeCode().getCode());
		assertTrue(entryRelationship.getContextConductionInd().toBoolean());
		assertEquals(Constants.TemplateOids.MEDICATION_PRESCRIPTION_EVENT_TEMPLATE_ID, entryRelationship.getTemplateId().get(0).getRoot());

		SubstanceAdministration substanceAdministration = entryRelationship.getClinicalStatementIfSubstanceAdministration();
		assertNotNull(substanceAdministration);
		assertEquals(x_DocumentSubstanceMood.RQO, substanceAdministration.getMoodCode().getCode());

		II id = substanceAdministration.getId().get(0);
		assertNotNull(id);
		assertTrue(id.getExtension().contains(Constants.IdPrefixes.MedicationPrescriptions.toString()));
		assertTrue(id.getExtension().contains(drug.getId().toString()));

		Consumable consumable = substanceAdministration.getConsumable();
		assertNotNull(consumable);

		CD<String> code = substanceAdministration.getCode();
		assertNotNull(code);
		assertEquals(Constants.SubstanceAdministrationType.DRUG.toString(), code.getCode());
		assertEquals(Constants.CodeSystems.ACT_CODE_CODESYSTEM_OID, code.getCodeSystem());
		assertEquals(Constants.CodeSystems.ACT_CODE_CODESYSTEM_NAME, code.getCodeSystemName());
	}

	@Test
	public void medicationPrescriptionEventNullTest() {
		EntryRelationship entryRelationship = mpeModel.getEntryRelationship(null);
		assertNotNull(entryRelationship);
	}

	@Test
	public void effectiveTimeTest() {
		SubstanceAdministration substanceAdministration = substanceAdministrationHelper(drug);
		ArrayList<ISetComponent<TS>> effectiveTime = substanceAdministration.getEffectiveTime();
		assertNotNull(effectiveTime);
		assertEquals(1, effectiveTime.size());

		IVL<TS> ivl = (IVL<TS>) effectiveTime.get(0);
		assertNotNull(ivl);
		assertEquals(SetOperator.Inclusive, ivl.getOperator());
		assertEquals(EverestUtils.buildTSFromDate(drug.getRxDate()), ivl.getLow());
		assertEquals(EverestUtils.buildTSFromDate(drug.getEndDate()), ivl.getHigh());
	}

	@Test
	public void effectiveTimeNullTest() {
		nullDrug.setRxDate(null);

		SubstanceAdministration substanceAdministration = substanceAdministrationHelper(nullDrug);
		ArrayList<ISetComponent<TS>> effectiveTime = substanceAdministration.getEffectiveTime();
		assertNotNull(effectiveTime);
		assertEquals(1, effectiveTime.size());

		IVL<TS> ivl = (IVL<TS>) effectiveTime.get(0);
		assertNotNull(ivl);
		assertEquals(SetOperator.Inclusive, ivl.getOperator());
		assertTrue(ivl.getLow().isNull());
		assertEquals(NullFlavor.Unknown, ivl.getLow().getNullFlavor().getCode());
		assertNull(ivl.getHigh());
	}

	@Test
	public void consumableTest() {
		SubstanceAdministration substanceAdministration = substanceAdministrationHelper(drug);
		Consumable consumable = substanceAdministration.getConsumable();
		assertNotNull(consumable);
	}

	@Test
	public void consumableNullTest() {
		SubstanceAdministration substanceAdministration = substanceAdministrationHelper(nullDrug);
		Consumable consumable = substanceAdministration.getConsumable();
		assertNotNull(consumable);
	}

	@Test
	public void authorTest() {
		SubstanceAdministration substanceAdministration = substanceAdministrationHelper(drug);
		ArrayList<Author> authors = substanceAdministration.getAuthor();
		assertNotNull(authors);
		assertEquals(1, authors.size());
	}

	@Test
	public void authorNullTest() {
		SubstanceAdministration substanceAdministration = substanceAdministrationHelper(nullDrug);
		ArrayList<Author> authors = substanceAdministration.getAuthor();
		assertNotNull(authors);
		assertEquals(1, authors.size());
	}

	@Test
	public void doseStructureTest() {
		ArrayList<EntryRelationship> entryRelationships = substanceAdministrationHelper(drug).getEntryRelationship();
		assertNotNull(entryRelationships);

		EntryRelationship entryRelationship = entryRelationships.get(0);
		assertNotNull(entryRelationship);
		assertEquals(x_ActRelationshipEntryRelationship.HasComponent, entryRelationship.getTypeCode().getCode());
		assertTrue(entryRelationship.getContextConductionInd().toBoolean());
		assertEquals(Constants.TemplateOids.DOSE_OBSERVATION_TEMPLATE_ID, entryRelationship.getTemplateId().get(0).getRoot());
	}

	@Test
	public void doseNullTest() {
		ArrayList<EntryRelationship> entryRelationships = substanceAdministrationHelper(nullDrug).getEntryRelationship();
		assertNotNull(entryRelationships);

		EntryRelationship entryRelationship = entryRelationships.get(0);
		assertNotNull(entryRelationship);
	}

	private SubstanceAdministration substanceAdministrationHelper(Drug drug) {
		EntryRelationship entryRelationship = mpeModel.getEntryRelationship(drug);
		return entryRelationship.getClinicalStatementIfSubstanceAdministration();
	}
}
