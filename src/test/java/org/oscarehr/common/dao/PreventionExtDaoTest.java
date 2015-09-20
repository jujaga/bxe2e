package org.oscarehr.common.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.oscarehr.common.dao.PreventionExtDao;
import org.oscarehr.common.model.PreventionExt;
import org.oscarehr.common.util.EntityModelUtils;
import org.oscarehr.e2e.constant.Constants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PreventionExtDaoTest {
	private static ApplicationContext context;
	private static PreventionExtDao preventionExtDao;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		preventionExtDao = context.getBean(PreventionExtDao.class);
	}

	@Test
	public void preventionExtTest() {
		EntityModelUtils.invokeMethodsForModelClass(new PreventionExt());
		PreventionExt entity = (PreventionExt) EntityModelUtils.generateTestDataForModelClass(new PreventionExt());
		preventionExtDao.persist(entity);
		assertNotNull(entity.getId());
	}

	@Test
	public void findByPreventionIdTest() {
		List<PreventionExt> preventionExts = preventionExtDao.findByPreventionId(Constants.Runtime.VALID_PREVENTION);
		assertNotNull(preventionExts);
		assertEquals(8, preventionExts.size());
	}
}
