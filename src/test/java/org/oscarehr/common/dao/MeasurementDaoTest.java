package org.oscarehr.common.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.oscarehr.common.dao.MeasurementDao;
import org.oscarehr.common.model.Measurement;
import org.oscarehr.common.util.EntityModelUtils;
import org.oscarehr.e2e.constant.Constants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MeasurementDaoTest {
	private static ApplicationContext context;
	private static MeasurementDao measurementDao;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		measurementDao = context.getBean(MeasurementDao.class);
	}

	@Test
	public void measurementTest() {
		EntityModelUtils.invokeMethodsForModelClass(new Measurement());
		Measurement entity = (Measurement) EntityModelUtils.generateTestDataForModelClass(new Measurement());
		measurementDao.persist(entity);
		assertNotNull(entity.getId());
	}

	@Test
	public void findByDemographicNoTest() {
		List<Measurement> measurements = measurementDao.findByDemographicNo(Constants.Runtime.VALID_DEMOGRAPHIC);
		assertNotNull(measurements);
		assertEquals(10, measurements.size());
	}
}
