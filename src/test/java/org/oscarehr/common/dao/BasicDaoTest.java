package org.oscarehr.common.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.oscarehr.common.dao.ClinicDao;
import org.oscarehr.common.dao.DemographicDao;
import org.oscarehr.common.dao.ProviderDao;
import org.oscarehr.common.model.Clinic;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.common.model.Provider;
import org.oscarehr.common.util.EntityModelUtils;
import org.oscarehr.e2e.constant.Constants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BasicDaoTest {
	private static ApplicationContext context;
	private static ClinicDao clinicDao;
	private static DemographicDao demographicDao;
	private static ProviderDao providerDao;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		clinicDao = context.getBean(ClinicDao.class);
		demographicDao = context.getBean(DemographicDao.class);
		providerDao = context.getBean(ProviderDao.class);
	}

	@Test
	public void clinicTest() {
		EntityModelUtils.invokeMethodsForModelClass(new Clinic());
		Clinic entity = (Clinic) EntityModelUtils.generateTestDataForModelClass(new Clinic());
		clinicDao.persist(entity);
		assertNotNull(entity.getId());
	}

	@Test
	public void demographicTest() {
		EntityModelUtils.invokeMethodsForModelClass(new Demographic());
		Demographic entity = (Demographic) EntityModelUtils.generateTestDataForModelClass(new Demographic());
		demographicDao.persist(entity);
		assertNotNull(entity.getDemographicNo());
	}

	@Test
	public void providerTest() {
		EntityModelUtils.invokeMethodsForModelClass(new Provider());
		Provider entity = (Provider) EntityModelUtils.generateTestDataForModelClass(new Provider());
		providerDao.persist(entity);
		assertNotNull(entity.getProviderNo());
	}
}
