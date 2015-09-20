package org.oscarehr.common.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.oscarehr.common.dao.MeasurementsExtDao;
import org.oscarehr.common.model.MeasurementsExt;
import org.oscarehr.common.util.EntityModelUtils;
import org.oscarehr.e2e.constant.Constants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MeasurementsExtDaoTest {
	private static ApplicationContext context;
	private static MeasurementsExtDao measurementsExtDao;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		measurementsExtDao = context.getBean(MeasurementsExtDao.class);
	}

	@Test
	public void measurementsExtTest() {
		EntityModelUtils.invokeMethodsForModelClass(new MeasurementsExt());
		MeasurementsExt entity = (MeasurementsExt) EntityModelUtils.generateTestDataForModelClass(new MeasurementsExt());
		measurementsExtDao.persist(entity);
		assertNotNull(entity.getId());
	}

	@Test
	public void getMeasurementsExtByMeasurementIdAndKeyValTest() {
		MeasurementsExt measurementsExt = measurementsExtDao.getMeasurementsExtByMeasurementIdAndKeyVal(Constants.Runtime.VALID_LAB_MEASUREMENT, Constants.MeasurementsExtKeys.lab_no.toString());
		assertNotNull(measurementsExt);
		assertEquals(Constants.Runtime.VALID_LAB_NO.toString(), measurementsExt.getVal());
	}

	@Test
	public void getMeasurementsExtByMeasurementIdTest() {
		List<MeasurementsExt> measurementsExts = measurementsExtDao.getMeasurementsExtByMeasurementId(Constants.Runtime.VALID_LAB_MEASUREMENT);
		assertNotNull(measurementsExts);
		assertEquals(13, measurementsExts.size());
	}
}
