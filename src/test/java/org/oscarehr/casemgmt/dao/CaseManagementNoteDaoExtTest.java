package org.oscarehr.casemgmt.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.oscarehr.casemgmt.dao.CaseManagementNoteExtDao;
import org.oscarehr.casemgmt.model.CaseManagementNoteExt;
import org.oscarehr.common.util.EntityModelUtils;
import org.oscarehr.e2e.constant.Constants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CaseManagementNoteDaoExtTest {
	private static ApplicationContext context;
	private static CaseManagementNoteExtDao caseManagementNoteExtDao;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		caseManagementNoteExtDao = context.getBean(CaseManagementNoteExtDao.class);
	}

	@Test
	public void caseManagementNoteTest() {
		EntityModelUtils.invokeMethodsForModelClass(new CaseManagementNoteExt());
		CaseManagementNoteExt entity = (CaseManagementNoteExt) EntityModelUtils.generateTestDataForModelClass(new CaseManagementNoteExt());
		caseManagementNoteExtDao.persist(entity);
		assertNotNull(entity.getId());
	}

	@Test
	public void getExtByNoteTest() {
		List<CaseManagementNoteExt> cmNotesExt = caseManagementNoteExtDao.getExtByNote(Constants.Runtime.VALID_FAMILY_HISTORY);
		assertNotNull(cmNotesExt);
		assertEquals(5, cmNotesExt.size());
	}
}
