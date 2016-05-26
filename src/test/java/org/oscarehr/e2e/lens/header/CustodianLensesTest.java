package org.oscarehr.e2e.lens.header;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.AssignedCustodian;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Custodian;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.CustodianOrganization;
import org.oscarehr.common.dao.ClinicDao;
import org.oscarehr.common.model.Clinic;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.lens.header.custodian.CustodianIdLens;
import org.oscarehr.e2e.lens.header.custodian.CustodianLens;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CustodianLensesTest {
	private static ApplicationContext context;
	private static ClinicDao dao;
	private static Clinic clinic;

	private Pair<Clinic, Custodian> blankPair;
	private Pair<Clinic, Custodian> getPair;
	private Pair<Clinic, Custodian> putPair;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);

		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		dao = context.getBean(ClinicDao.class);
		clinic = dao.find(Constants.Runtime.VALID_CLINIC);
	}

	@Before
	public void before() {
		Custodian custodian = new Custodian();
		custodian.setAssignedCustodian(new AssignedCustodian(new CustodianOrganization()));

		blankPair = Pair.of(new Clinic(), custodian);
		getPair = Pair.of(clinic, custodian);
		putPair = Pair.of(new Clinic(), custodian);
	}

	@Test
	public void custodianLensGetTest() {
		CustodianLens lens = new CustodianLens();
		assertNotNull(lens);

		Pair<Clinic, Custodian> pair = lens.get(blankPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
		assertNotNull(pair.getRight().getAssignedCustodian());
		assertNotNull(pair.getRight().getAssignedCustodian().getRepresentedCustodianOrganization());
	}

	@Test
	public void custodianLensPutTest() {
		CustodianLens lens = new CustodianLens();
		assertNotNull(lens);

		Pair<Clinic, Custodian> pair = lens.put(blankPair, blankPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
	}

	@Test
	public void custodianIdLensNullGetTest() {
		CustodianIdLens lens = new CustodianIdLens();
		assertNotNull(lens);

		Pair<Clinic, Custodian> pair = lens.get(blankPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());

		SET<II> ids = pair.getRight().getAssignedCustodian().getRepresentedCustodianOrganization().getId();
		assertNotNull(ids);
		assertTrue(ids.get(0).isNull());
		assertEquals(NullFlavor.NoInformation, ids.get(0).getNullFlavor().getCode());
	}

	@Test
	public void custodianIdLensGetTest() {
		CustodianIdLens lens = new CustodianIdLens();
		assertNotNull(lens);

		Pair<Clinic, Custodian> pair = lens.get(getPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());

		SET<II> ids = pair.getRight().getAssignedCustodian().getRepresentedCustodianOrganization().getId();
		assertNotNull(ids);
		assertEquals(Constants.EMR.EMR_OID, ids.get(0).getRoot());
		assertEquals(Constants.EMR.EMR_VERSION, ids.get(0).getAssigningAuthorityName());
		assertEquals(clinic.getId().toString(), ids.get(0).getExtension());
	}

	@Test
	public void custodianIdLensNullPutTest() {
		CustodianIdLens lens = new CustodianIdLens();
		assertNotNull(lens);

		Pair<Clinic, Custodian> pair = lens.put(blankPair, blankPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
	}

	@Test
	public void custodianIdLensPutTest() {
		CustodianIdLens lens = new CustodianIdLens();
		assertNotNull(lens);

		SET<II> ids = new SET<>(new II(Constants.EMR.EMR_OID, clinic.getId().toString()));
		putPair.getRight().getAssignedCustodian().getRepresentedCustodianOrganization().setId(ids);

		Pair<Clinic, Custodian> pair = lens.put(putPair, putPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
		assertEquals(clinic.getId(), pair.getLeft().getId());

		ids = new SET<>(new II(Constants.EMR.EMR_OID, "garbage"));
		putPair.getRight().getAssignedCustodian().getRepresentedCustodianOrganization().setId(ids);
		putPair.getLeft().setId(null);

		pair = lens.put(putPair, putPair);
		assertNull(pair.getLeft().getId());
	}
}
