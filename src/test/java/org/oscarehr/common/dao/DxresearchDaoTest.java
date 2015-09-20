package org.oscarehr.common.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.oscarehr.common.dao.DxresearchDao;
import org.oscarehr.common.model.Dxresearch;
import org.oscarehr.common.util.EntityModelUtils;
import org.oscarehr.e2e.constant.Constants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DxresearchDaoTest {
	private static ApplicationContext context;
	private static DxresearchDao dxResearchDao;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		dxResearchDao = context.getBean(DxresearchDao.class);
	}

	@Test
	public void dxresearchTest() {
		EntityModelUtils.invokeMethodsForModelClass(new Dxresearch());
		Dxresearch entity = (Dxresearch) EntityModelUtils.generateTestDataForModelClass(new Dxresearch());
		dxResearchDao.persist(entity);
		assertNotNull(entity.getDxresearchNo());
	}

	@Test
	public void getDxResearchItemsByPatientTest() {
		List<Dxresearch> problems = dxResearchDao.getDxResearchItemsByPatient(Constants.Runtime.VALID_DEMOGRAPHIC);
		assertNotNull(problems);
		assertEquals(2, problems.size());
	}
}
