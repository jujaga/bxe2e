package org.oscarehr.casemgmt.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.oscarehr.casemgmt.dao.CaseManagementNoteDao;
import org.oscarehr.casemgmt.model.CaseManagementNote;
import org.oscarehr.common.util.EntityModelUtils;
import org.oscarehr.e2e.constant.Constants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CaseManagementNoteDaoTest {
	private static ApplicationContext context;
	private static CaseManagementNoteDao caseManagementNoteDao;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		caseManagementNoteDao = context.getBean(CaseManagementNoteDao.class);
	}

	@Test
	public void caseManagementNoteTest() {
		EntityModelUtils.invokeMethodsForModelClass(new CaseManagementNote());
		CaseManagementNote entity = (CaseManagementNote) EntityModelUtils.generateTestDataForModelClass(new CaseManagementNote());
		caseManagementNoteDao.persist(entity);
		assertNotNull(entity.getId());
	}

	@Test
	public void getNotesByDemographicTest() {
		List<CaseManagementNote> cmNotes = caseManagementNoteDao.getNotesByDemographic(Constants.Runtime.VALID_DEMOGRAPHIC.toString());
		assertNotNull(cmNotes);
		assertEquals(6, cmNotes.size());
	}
}
