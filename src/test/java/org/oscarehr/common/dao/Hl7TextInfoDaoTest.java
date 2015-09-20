package org.oscarehr.common.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.oscarehr.common.dao.Hl7TextInfoDao;
import org.oscarehr.common.model.Hl7TextInfo;
import org.oscarehr.common.util.EntityModelUtils;
import org.oscarehr.e2e.constant.Constants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Hl7TextInfoDaoTest {
	private static ApplicationContext context;
	private static Hl7TextInfoDao hl7TextInfoDao;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		hl7TextInfoDao = context.getBean(Hl7TextInfoDao.class);
	}

	@Test
	public void hl7TextInfoTest() {
		EntityModelUtils.invokeMethodsForModelClass(new Hl7TextInfo());
		Hl7TextInfo entity = (Hl7TextInfo) EntityModelUtils.generateTestDataForModelClass(new Hl7TextInfo());
		hl7TextInfoDao.persist(entity);
		assertNotNull(entity.getId());
	}

	@Test
	public void findLabIdTest() {
		Hl7TextInfo hl7TextInfo = hl7TextInfoDao.findLabId(Constants.Runtime.VALID_LAB_NO);
		assertNotNull(hl7TextInfo);

		Integer invalidLabNo = 0;
		Hl7TextInfo hl7TextInfo2 = hl7TextInfoDao.findLabId(invalidLabNo);
		assertNull(hl7TextInfo2);
	}
}
