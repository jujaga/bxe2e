package org.oscarehr.common.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.oscarehr.common.dao.AllergyDao;
import org.oscarehr.common.model.Allergy;
import org.oscarehr.common.util.EntityModelUtils;
import org.oscarehr.e2e.constant.Constants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AllergyDaoTest {
	private static ApplicationContext context;
	private static AllergyDao allergyDao;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		allergyDao = context.getBean(AllergyDao.class);
	}

	@Test
	public void drugTest() {
		EntityModelUtils.invokeMethodsForModelClass(new Allergy());
		Allergy entity = (Allergy) EntityModelUtils.generateTestDataForModelClass(new Allergy());
		allergyDao.persist(entity);
		assertNotNull(entity.getId());
	}

	@Test
	public void findAllergiesTest() {
		List<Allergy> allergies = allergyDao.findAllergies(Constants.Runtime.VALID_DEMOGRAPHIC);
		assertNotNull(allergies);
		assertEquals(1, allergies.size());
	}
}
