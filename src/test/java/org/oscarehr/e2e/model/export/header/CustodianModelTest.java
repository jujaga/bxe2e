package org.oscarehr.e2e.model.export.header;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.ENXP;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.ON;
import org.marc.everest.datatypes.generic.SET;
import org.oscarehr.common.dao.ClinicDao;
import org.oscarehr.common.model.Clinic;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.export.header.CustodianModel;
import org.oscarehr.e2e.util.EverestUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CustodianModelTest {
	private static ApplicationContext context;
	private static ClinicDao dao;
	private static Clinic clinic;
	private static CustodianModel custodianModel;

	private static Clinic nullClinic;
	private static CustodianModel nullCustodianModel;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		dao = context.getBean(ClinicDao.class);
		clinic = dao.find(Constants.Runtime.VALID_CLINIC);
		custodianModel = new CustodianModel(clinic);

		nullClinic = new Clinic();
		nullCustodianModel = new CustodianModel(nullClinic);
	}

	@Test
	public void custodianModelNullTest() {
		assertNotNull(new CustodianModel(null));
	}

	@Test
	public void idTest() {
		SET<II> ids = custodianModel.getIds();
		assertNotNull(ids);

		II id = ids.get(0);
		assertNotNull(id);
		assertEquals(Constants.EMR.EMR_OID, id.getRoot());
		assertEquals(Constants.EMR.EMR_VERSION, id.getAssigningAuthorityName());
		assertFalse(EverestUtils.isNullorEmptyorWhitespace(id.getExtension()));
		assertEquals(clinic.getId().toString(), id.getExtension());
	}

	@Test
	public void idNullTest() {
		SET<II> ids = nullCustodianModel.getIds();
		assertNotNull(ids);

		II id = ids.get(0);
		assertNotNull(id);
		assertTrue(id.isNull());
		assertEquals(NullFlavor.NoInformation, id.getNullFlavor().getCode());
	}

	@Test
	public void nameTest() {
		ON on = custodianModel.getName();
		assertNotNull(on);

		ENXP name = on.getPart(0);
		assertNotNull(name);
		assertFalse(EverestUtils.isNullorEmptyorWhitespace(clinic.getClinicName()));
		assertEquals(clinic.getClinicName(), name.getValue());
	}

	@Test
	public void nameNullTest() {
		ON on = nullCustodianModel.getName();
		assertNull(on);
	}
}
