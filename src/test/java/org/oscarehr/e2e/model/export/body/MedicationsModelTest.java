package org.oscarehr.e2e.model.export.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Consumable;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActStatus;
import org.oscarehr.common.dao.DrugDao;
import org.oscarehr.common.model.Drug;
import org.oscarehr.e2e.constant.BodyConstants;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.export.body.MedicationsModel;
import org.oscarehr.e2e.util.EverestUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MedicationsModelTest {
	private static ApplicationContext context;
	private static DrugDao dao;
	private static Drug drug;
	private static MedicationsModel medicationsModel;

	private static Drug nullDrug;
	private static MedicationsModel nullMedicationsModel;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		dao = context.getBean(DrugDao.class);
		drug = dao.findByDemographicId(Constants.Runtime.VALID_DEMOGRAPHIC).get(0);
		medicationsModel = new MedicationsModel(drug);

		nullDrug = new Drug();
		nullMedicationsModel = new MedicationsModel(nullDrug);
	}

	@Test
	public void medicationsModelNullTest() {
		assertNotNull(new MedicationsModel(null));
	}

	@Test
	public void textSummaryTest() {
		String text = medicationsModel.getTextSummary();
		assertNotNull(text);
	}

	@Test
	public void textSummaryNullTest() {
		String text = nullMedicationsModel.getTextSummary();
		assertNotNull(text);
	}

	@Test
	public void idTest() {
		SET<II> ids = medicationsModel.getIds();
		assertNotNull(ids);

		II id = ids.get(0);
		assertNotNull(id);
		assertEquals(Constants.EMR.EMR_OID, id.getRoot());
		assertEquals(Constants.EMR.EMR_VERSION, id.getAssigningAuthorityName());
		assertFalse(EverestUtils.isNullorEmptyorWhitespace(id.getExtension()));
		assertTrue(id.getExtension().contains(Constants.IdPrefixes.Medications.toString()));
		assertTrue(id.getExtension().contains(drug.getId().toString()));
	}

	@Test
	public void idNullTest() {
		SET<II> ids = nullMedicationsModel.getIds();
		assertNotNull(ids);
	}

	@Test
	public void codeTest() {
		CD<String> code = medicationsModel.getCode();
		assertNotNull(code);

		assertEquals(Constants.SubstanceAdministrationType.DRUG.toString(), code.getCode());
		assertEquals(Constants.CodeSystems.ACT_CODE_CODESYSTEM_OID, code.getCodeSystem());
		assertEquals(Constants.CodeSystems.ACT_CODE_CODESYSTEM_NAME, code.getCodeSystemName());
		assertEquals(BodyConstants.Medications.DRUG_THERAPY_ACT_NAME, code.getDisplayName());
	}

	@Test
	public void codeNullTest() {
		CD<String> code = nullMedicationsModel.getCode();
		assertNotNull(code);

		assertEquals(Constants.SubstanceAdministrationType.DRUG.toString(), code.getCode());
		assertEquals(Constants.CodeSystems.ACT_CODE_CODESYSTEM_OID, code.getCodeSystem());
		assertEquals(Constants.CodeSystems.ACT_CODE_CODESYSTEM_NAME, code.getCodeSystemName());
		assertEquals(BodyConstants.Medications.DRUG_THERAPY_ACT_NAME, code.getDisplayName());
	}

	@Test
	public void statusCodeActiveTest() {
		ActStatus status = medicationsModel.getStatusCode();
		assertNotNull(status);
		assertEquals(ActStatus.Active, status);

		Drug drug2 = dao.findByDemographicId(Constants.Runtime.VALID_DEMOGRAPHIC).get(0);
		drug2.setLongTerm(false);
		drug2.setEndDate(new Date());
		MedicationsModel medicationsModel2 = new MedicationsModel(drug2);

		ActStatus status2 = medicationsModel2.getStatusCode();
		assertNotNull(status2);
		assertEquals(ActStatus.Active, status2);
	}

	@Test
	public void statusCodeCompleteTest() {
		Drug drug2 = dao.findByDemographicId(Constants.Runtime.VALID_DEMOGRAPHIC).get(0);
		drug2.setLongTerm(false);
		MedicationsModel medicationsModel2 = new MedicationsModel(drug2);

		ActStatus status = medicationsModel2.getStatusCode();
		assertNotNull(status);
		assertEquals(ActStatus.Completed, status);

		Drug drug3 = dao.findByDemographicId(Constants.Runtime.VALID_DEMOGRAPHIC).get(0);
		drug3.setArchived(true);
		MedicationsModel medicationsModel3 = new MedicationsModel(drug3);

		ActStatus status2 = medicationsModel3.getStatusCode();
		assertNotNull(status2);
		assertEquals(ActStatus.Completed, status2);
	}

	@Test
	public void statusCodeNullTest() {
		ActStatus status = nullMedicationsModel.getStatusCode();
		assertNotNull(status);
		assertEquals(ActStatus.Completed, status);
	}

	@Test
	public void consumableTest() {
		Consumable consumable = medicationsModel.getConsumable();
		assertNotNull(consumable);
	}

	@Test
	public void consumableNullTest() {
		Consumable consumable = nullMedicationsModel.getConsumable();
		assertNotNull(consumable);
	}

	@Test
	public void recordTypeTest() {
		EntryRelationship entryRelationship = medicationsModel.getRecordType();
		assertNotNull(entryRelationship);
	}

	@Test
	public void recordTypeNullTest() {
		EntryRelationship entryRelationship = nullMedicationsModel.getRecordType();
		assertNotNull(entryRelationship);
	}

	@Test
	public void lastReviewDateTest() {
		EntryRelationship entryRelationship = medicationsModel.getLastReviewDate();
		assertNotNull(entryRelationship);
	}

	@Test
	public void lastReviewDateNullTest() {
		EntryRelationship entryRelationship = nullMedicationsModel.getLastReviewDate();
		assertNull(entryRelationship);
	}

	@Test
	public void prescriptionInformationTest() {
		EntryRelationship entryRelationship = medicationsModel.getPrescriptionInformation();
		assertNotNull(entryRelationship);
	}

	@Test
	public void prescriptionInformationNullTest() {
		EntryRelationship entryRelationship = nullMedicationsModel.getPrescriptionInformation();
		assertNotNull(entryRelationship);
	}
}
