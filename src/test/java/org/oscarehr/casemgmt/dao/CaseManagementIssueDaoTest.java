package org.oscarehr.casemgmt.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.oscarehr.casemgmt.dao.CaseManagementIssueDao;
import org.oscarehr.casemgmt.model.CaseManagementIssue;
import org.oscarehr.common.util.EntityModelUtils;
import org.oscarehr.e2e.constant.Constants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CaseManagementIssueDaoTest {
	private static ApplicationContext context;
	private static CaseManagementIssueDao caseManagementIssueDao;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		caseManagementIssueDao = context.getBean(CaseManagementIssueDao.class);
	}

	@Test
	public void caseManagementNoteTest() {
		EntityModelUtils.invokeMethodsForModelClass(new CaseManagementIssue());
		CaseManagementIssue entity = (CaseManagementIssue) EntityModelUtils.generateTestDataForModelClass(new CaseManagementIssue());
		caseManagementIssueDao.persist(entity);
		assertNotNull(entity.getId());
	}

	@Test
	public void getIssuesByDemographicTest() {
		List<CaseManagementIssue> cmIssues = caseManagementIssueDao.getIssuesByDemographic(Constants.Runtime.VALID_DEMOGRAPHIC.toString());
		assertNotNull(cmIssues);
		assertEquals(3, cmIssues.size());
	}
}
