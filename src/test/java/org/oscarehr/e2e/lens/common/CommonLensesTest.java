package org.oscarehr.e2e.lens.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.ADXP;
import org.marc.everest.datatypes.AddressPartType;
import org.marc.everest.datatypes.ENXP;
import org.marc.everest.datatypes.EntityNamePartType;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.SET;
import org.oscarehr.common.model.Provider;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.util.EverestUtils;

public class CommonLensesTest {
	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
	}

	@Test
	public void addressPartLensGetTest() {
		final String test = "test";

		AddressPartLens lens = new AddressPartLens(AddressPartType.Delimiter);
		assertNotNull(lens);

		assertNull(lens.get(null));
		assertEquals(new ADXP(test, AddressPartType.Delimiter), lens.get(test));
	}

	@Test
	public void addressPartLensPutTest() {
		final String test = "test";

		AddressPartLens lens = new AddressPartLens(AddressPartType.Delimiter);
		assertNotNull(lens);

		assertNull(lens.put(null));
		assertNull(lens.put(new ADXP(test, AddressPartType.City)));
		assertEquals(test, lens.put(new ADXP(test, AddressPartType.Delimiter)));
		assertEquals(test, lens.put(test, null));
		assertEquals(test, lens.put(test, new ADXP() {{setNullFlavor(NullFlavor.NoInformation);}}));
		assertEquals(test, lens.put(test, new ADXP("wrong", null)));
		assertEquals(test, lens.put(test, new ADXP("wrong", AddressPartType.City)));
		assertEquals(test, lens.put("wrong", new ADXP(test, AddressPartType.Delimiter)));
	}

	@Test
	public void authorIdLensGetTest() {
		final String test = Constants.Runtime.VALID_PROVIDER.toString();
		final Provider provider = EverestUtils.getProviderFromString(test);
		final String originalPracNo = provider.getPractitionerNo();
		final String originalOhipNo = provider.getOhipNo();

		AuthorIdLens lens = new AuthorIdLens();
		assertNotNull(lens);

		// Null
		assertNotNull(lens.get(null));
		assertEquals(NullFlavor.NoInformation, lens.get(null).get(0).getNullFlavor().getCode());

		// Invalid
		assertNotNull(lens.get(Constants.Runtime.INVALID_VALUE.toString()));
		assertEquals(NullFlavor.NoInformation, lens.get(null).get(0).getNullFlavor().getCode());

		// PractitionerNo
		assertNotNull(lens.get(test));
		assertEquals(Constants.DocumentHeader.BC_MINISTRY_OF_HEALTH_PRACTITIONER_ID_OID, lens.get(test).get(0).getRoot());
		assertEquals(Constants.DocumentHeader.BC_MINISTRY_OF_HEALTH_PRACTITIONER_NAME, lens.get(test).get(0).getAssigningAuthorityName());
		assertEquals(originalPracNo, lens.get(test).get(0).getExtension());

		// OhipNo
		provider.setPractitionerNo(null);
		assertNotNull(lens.get(test));
		assertEquals(Constants.DocumentHeader.MEDICAL_SERVICES_PLAN_BILLING_NUMBER_OID, lens.get(test).get(0).getRoot());
		assertEquals(Constants.DocumentHeader.MEDICAL_SERVICES_PLAN_BILLING_NUMBER_NAME, lens.get(test).get(0).getAssigningAuthorityName());
		assertEquals(originalOhipNo, lens.get(test).get(0).getExtension());

		// ProviderNo
		provider.setOhipNo(null);
		assertNotNull(lens.get(test));
		assertEquals(Constants.DocumentHeader.LOCALLY_ASSIGNED_IDENTIFIER_OID, lens.get(test).get(0).getRoot());
		assertEquals(Constants.DocumentHeader.LOCALLY_ASSIGNED_IDENTIFIER_NAME, lens.get(test).get(0).getAssigningAuthorityName());
		assertEquals(test, lens.get(test).get(0).getExtension());

		// Reset provider
		provider.setPractitionerNo(originalPracNo);
		provider.setOhipNo(originalOhipNo);
	}

	@Test
	public void authorIdLensPutTest() {
		final String test = "test";

		AuthorIdLens lens = new AuthorIdLens();
		assertNotNull(lens);

		assertNull(lens.put(null));
		assertEquals(test, lens.put(test, null));
	}

	@Test
	public void authorPersonLensGetTest() {
		final String test = Constants.Runtime.VALID_PROVIDER.toString();
		final Provider provider = EverestUtils.getProviderFromString(test);
		final String originalFirstName = provider.getFirstName();
		final String originalLastName = provider.getLastName();

		AuthorPersonLens lens = new AuthorPersonLens();
		assertNotNull(lens);

		// Null
		assertNotNull(lens.get(null));
		assertEquals(NullFlavor.NoInformation, lens.get(null).getNullFlavor().getCode());

		// Invalid
		assertNotNull(lens.get(Constants.Runtime.INVALID_VALUE.toString()));
		assertEquals(NullFlavor.NoInformation, lens.get(null).getNullFlavor().getCode());

		// Normal
		assertNotNull(lens.get(test));
		assertTrue(lens.get(test).getName().get(0).getParts().stream().anyMatch(e -> e.getType().getCode() == EntityNamePartType.Given));
		assertTrue(lens.get(test).getName().get(0).getParts().stream().anyMatch(e -> e.getType().getCode() == EntityNamePartType.Family));

		// Null Names
		provider.setFirstName(null);
		provider.setLastName(null);
		assertNotNull(lens.get(test));
		assertEquals(NullFlavor.NoInformation, lens.get(test).getName().get(0).getNullFlavor().getCode());

		// Reset provider
		provider.setFirstName(originalFirstName);
		provider.setLastName(originalLastName);
	}

	@Test
	public void authorPersonLensPutTest() {
		final String test = "test";

		AuthorPersonLens lens = new AuthorPersonLens();
		assertNotNull(lens);

		assertNull(lens.put(null));
		assertEquals(test, lens.put(test, null));
	}

	@Test(expected=NullPointerException.class)
	public void entryIdLensNullTest() {
		new EntryIdLens(null);
	}

	@Test
	public void entryIdLensGetTest() {
		final Integer test = 1;

		EntryIdLens lens = new EntryIdLens(Constants.IdPrefixes.ProblemList);
		assertNotNull(lens);

		assertNotNull(lens.get(null));
		assertEquals(Constants.EMR.EMR_OID, lens.get(null).get(0).getRoot());
		assertEquals(Constants.EMR.EMR_VERSION, lens.get(null).get(0).getAssigningAuthorityName());
		assertTrue(lens.get(null).get(0).getExtension().startsWith(Constants.IdPrefixes.ProblemList.toString()));
		assertTrue(lens.get(null).get(0).getExtension().endsWith("0"));

		assertNotNull(lens.get(test));
		assertEquals(Constants.EMR.EMR_OID, lens.get(null).get(0).getRoot());
		assertEquals(Constants.EMR.EMR_VERSION, lens.get(null).get(0).getAssigningAuthorityName());
		assertTrue(lens.get(test).get(0).getExtension().startsWith(Constants.IdPrefixes.ProblemList.toString()));
		assertTrue(lens.get(test).get(0).getExtension().endsWith(test.toString()));
	}

	@Test
	public void entryIdLensPutTest() {
		final Integer zero = 0;
		final Integer test = 1;

		EntryIdLens lens = new EntryIdLens(Constants.IdPrefixes.ProblemList);
		assertNotNull(lens);

		assertEquals(zero, lens.put(null));
		assertEquals(test, lens.put(test, null));
		assertEquals(zero, lens.put(new SET<II>()));

		SET<II> iis = lens.get(test);
		assertEquals(test, lens.put(iis));

		iis.get(0).setRoot("garbage");
		assertEquals(zero, lens.put(iis));
	}

	@Test
	public void everestBugLensGetTest() {
		final String test = "xsi:nil=\"true\" delimeter";

		EverestBugLens lens = new EverestBugLens();
		assertNotNull(lens);

		assertNotNull(lens.get(test));
		assertFalse(lens.get(test).contains("xsi:nil=\"true\" "));
		assertTrue(lens.get(test).contains("delimiter"));
	}

	@Test
	public void everestBugLensPutTest() {
		final String test = "delimiter";

		EverestBugLens lens = new EverestBugLens();
		assertNotNull(lens);

		assertNotNull(lens.put(test));
		assertTrue(lens.put(test).contains("delimeter"));
	}

	@Test
	public void namePartLensGetTest() {
		final String test = "test";

		assertNotNull(new NamePartLens(null));
		NamePartLens lens = new NamePartLens(EntityNamePartType.Given);
		assertNotNull(lens);

		assertNotNull(lens.get(test));
		assertEquals(EntityNamePartType.Given, lens.get(test).getType().getCode());
		assertEquals(test, lens.get(test).getValue());
	}

	@Test
	public void namePartLensPutTest() {
		final String test = "test";

		assertNotNull(new NamePartLens(null));
		NamePartLens lens = new NamePartLens(EntityNamePartType.Given);
		assertNotNull(lens);

		assertNull(lens.put(new ENXP(test, null)));
		assertEquals(test, lens.put(test, new ENXP(null, null)));

		assertNotNull(lens.put(lens.get(test)));
	}
}
