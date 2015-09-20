package org.oscarehr.common.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.oscarehr.common.dao.PatientLabRoutingDao;
import org.oscarehr.common.model.PatientLabRouting;
import org.oscarehr.common.util.EntityModelUtils;
import org.oscarehr.e2e.constant.Constants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PatientLabRoutingDaoTest {
	private static ApplicationContext context;
	private static PatientLabRoutingDao patientLabRoutingDao;
	private static final String HL7 = "HL7";

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		patientLabRoutingDao = context.getBean(PatientLabRoutingDao.class);
	}

	@Test
	public void patientLabRoutingTest() {
		EntityModelUtils.invokeMethodsForModelClass(new PatientLabRouting());
		PatientLabRouting entity = (PatientLabRouting) EntityModelUtils.generateTestDataForModelClass(new PatientLabRouting());
		patientLabRoutingDao.persist(entity);
		assertNotNull(entity.getId());
	}

	@Test
	public void findByDemographicAndLabTypeTest() {
		List<PatientLabRouting> patientLabRoutings = patientLabRoutingDao.findByDemographicAndLabType(Constants.Runtime.VALID_DEMOGRAPHIC, HL7);
		assertNotNull(patientLabRoutings);
		assertEquals(1, patientLabRoutings.size());
	}
}
