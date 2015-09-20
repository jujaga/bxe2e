package org.oscarehr.e2e.model.export.template.observation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.ED;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.PQ;
import org.marc.everest.datatypes.SetOperator;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.datatypes.generic.IVL;
import org.marc.everest.datatypes.generic.PIVL;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Consumable;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.SubstanceAdministration;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_DocumentSubstanceMood;
import org.oscarehr.common.dao.DrugDao;
import org.oscarehr.common.model.Drug;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.constant.Mappings;
import org.oscarehr.e2e.model.export.template.observation.DoseObservationModel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DoseObservationModelTest {
	private static ApplicationContext context;
	private static DrugDao dao;
	private static Drug drug;
	private static Drug nullDrug;
	private static DoseObservationModel doseObservationModel;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		dao = context.getBean(DrugDao.class);
		doseObservationModel = new DoseObservationModel();
	}

	@Before
	public void before() {
		drug = dao.findByDemographicId(Constants.Runtime.VALID_DEMOGRAPHIC).get(0);
		nullDrug = new Drug();
	}

	@Test
	public void doseObservationStructureTest() {
		EntryRelationship entryRelationship = doseObservationModel.getEntryRelationship(drug);
		assertNotNull(entryRelationship);
		assertEquals(x_ActRelationshipEntryRelationship.HasComponent, entryRelationship.getTypeCode().getCode());
		assertTrue(entryRelationship.getContextConductionInd().toBoolean());
		assertEquals(Constants.TemplateOids.DOSE_OBSERVATION_TEMPLATE_ID, entryRelationship.getTemplateId().get(0).getRoot());

		SubstanceAdministration substanceAdministration = entryRelationship.getClinicalStatementIfSubstanceAdministration();
		assertNotNull(substanceAdministration);
		assertEquals(x_DocumentSubstanceMood.RQO, substanceAdministration.getMoodCode().getCode());

		Consumable consumable = substanceAdministration.getConsumable();
		assertNotNull(consumable);
	}

	@Test
	public void doseObservationNullTest() {
		EntryRelationship entryRelationship = doseObservationModel.getEntryRelationship(null);
		assertNotNull(entryRelationship);

		SubstanceAdministration substanceAdministration = entryRelationship.getClinicalStatementIfSubstanceAdministration();
		assertNotNull(substanceAdministration);

		Consumable consumable = substanceAdministration.getConsumable();
		assertNotNull(consumable);
	}

	@Test
	public void doseInstructionsTest() {
		ED text = substanceAdministrationHelper(drug).getText();
		assertNotNull(text);
	}

	@Test
	public void doseInstructionsNullTest() {
		ED text = substanceAdministrationHelper(nullDrug).getText();
		assertNotNull(text);
	}

	@Test
	public void durationTest() {
		IVL<TS> ivl = (IVL<TS>) substanceAdministrationHelper(drug).getEffectiveTime().get(0);
		assertNotNull(ivl);
		assertTrue(IVL.isValidWidthFlavor(ivl));
		assertEquals(drug.getDuration(), ivl.getWidth().getValue().toString());
		assertEquals(drug.getDurUnit(), ivl.getWidth().getUnit());
	}

	@Test
	public void durationInvalidTest() {
		drug.setDuration("test");

		IVL<TS> ivl = (IVL<TS>) substanceAdministrationHelper(drug).getEffectiveTime().get(0);
		assertNotNull(ivl);
		assertTrue(ivl.isNull());
		assertEquals(NullFlavor.Unknown, ivl.getNullFlavor().getCode());
	}

	@Test
	public void durationNullTest() {
		IVL<TS> ivl = (IVL<TS>) substanceAdministrationHelper(nullDrug).getEffectiveTime().get(0);
		assertNotNull(ivl);
		assertTrue(ivl.isNull());
		assertEquals(NullFlavor.Unknown, ivl.getNullFlavor().getCode());
	}

	@Test
	public void frequencyTest() {
		PIVL<TS> pivl = (PIVL<TS>) substanceAdministrationHelper(drug).getEffectiveTime().get(1);
		assertNotNull(pivl);
		assertEquals(new PQ(BigDecimal.ONE, Constants.TimeUnit.d.toString()), pivl.getPeriod());
		assertEquals(SetOperator.Intersect, pivl.getOperator());
		assertTrue(pivl.getInstitutionSpecified());
	}

	@Test
	public void frequencyInvalidTest() {
		drug.setFreqCode("test");

		PIVL<TS> pivl = (PIVL<TS>) substanceAdministrationHelper(drug).getEffectiveTime().get(1);
		assertNotNull(pivl);
		assertTrue(pivl.isNull());
		assertEquals(NullFlavor.Other, pivl.getNullFlavor().getCode());
	}

	@Test
	public void frequencyNullTest() {
		PIVL<TS> pivl = (PIVL<TS>) substanceAdministrationHelper(nullDrug).getEffectiveTime().get(1);
		assertNotNull(pivl);
		assertTrue(pivl.isNull());
		assertEquals(NullFlavor.Unknown, pivl.getNullFlavor().getCode());
	}

	@Test
	public void routeTest() {
		CE<String> route = substanceAdministrationHelper(drug).getRouteCode();
		assertNotNull(route);
		assertEquals(drug.getRoute().toUpperCase(), route.getCode());
		assertEquals(Constants.CodeSystems.ROUTE_OF_ADMINISTRATION_OID, route.getCodeSystem());
		assertEquals(Constants.CodeSystems.ROUTE_OF_ADMINISTRATION_NAME, route.getCodeSystemName());
	}

	@Test
	public void routeNullTest() {
		CE<String> route = substanceAdministrationHelper(nullDrug).getRouteCode();
		assertNull(route);
	}

	@Test
	public void doseQuantityTest() {
		IVL<PQ> ivl = substanceAdministrationHelper(drug).getDoseQuantity();
		assertNotNull(ivl);
		assertEquals(new PQ(new BigDecimal(drug.getTakeMin().toString()), null), ivl.getLow());
		assertEquals(new PQ(new BigDecimal(drug.getTakeMax().toString()), null), ivl.getHigh());

		drug.setUnitName("te st");

		ivl = substanceAdministrationHelper(drug).getDoseQuantity();
		assertNotNull(ivl);
		assertEquals(new PQ(new BigDecimal(drug.getTakeMin().toString()), "te_st"), ivl.getLow());
		assertEquals(new PQ(new BigDecimal(drug.getTakeMax().toString()), "te_st"), ivl.getHigh());
	}

	@Test
	public void doseQuantityNullTest() {
		nullDrug.setTakeMin(null);
		nullDrug.setTakeMax(null);

		IVL<PQ> ivl = substanceAdministrationHelper(nullDrug).getDoseQuantity();
		assertNotNull(ivl);
		assertTrue(ivl.isNull());
		assertEquals(NullFlavor.NoInformation, ivl.getNullFlavor().getCode());
	}

	@Test
	public void formTest() {
		CE<String> form = substanceAdministrationHelper(drug).getAdministrationUnitCode();
		assertNotNull(form);
		assertEquals(Mappings.formCode.get(drug.getDrugForm()), form.getCode());
		assertEquals(Constants.CodeSystems.ADMINISTERABLE_DRUG_FORM_OID, form.getCodeSystem());
		assertEquals(Constants.CodeSystems.ADMINISTERABLE_DRUG_FORM_NAME, form.getCodeSystemName());
		assertEquals(drug.getDrugForm(), form.getDisplayName());
	}

	@Test
	public void formNullTest() {
		CE<String> form = substanceAdministrationHelper(nullDrug).getAdministrationUnitCode();
		assertNull(form);
	}

	private SubstanceAdministration substanceAdministrationHelper(Drug drug) {
		EntryRelationship entryRelationship = doseObservationModel.getEntryRelationship(drug);
		return entryRelationship.getClinicalStatementIfSubstanceAdministration();
	}
}
