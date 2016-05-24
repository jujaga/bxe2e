package org.oscarehr.e2e.lens.header;

import static org.junit.Assert.assertNotNull;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Custodian;
import org.oscarehr.common.model.Clinic;
import org.oscarehr.e2e.lens.header.custodian.CustodianLens;

public class CustodianLensesTest {
	private Pair<Clinic, Custodian> inputPair;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
	}

	@Before
	public void before() {
		inputPair = Pair.of(new Clinic(), new Custodian());
	}

	@Test
	public void custodianLensGetTest() {
		CustodianLens lens = new CustodianLens();
		assertNotNull(lens);

		Pair<Clinic, Custodian> pair = lens.get(inputPair);
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

		Pair<Clinic, Custodian> pair = lens.put(inputPair, inputPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
	}
}
