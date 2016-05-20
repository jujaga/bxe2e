package org.oscarehr.e2e.lens.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.ADXP;
import org.marc.everest.datatypes.AddressPartType;
import org.marc.everest.datatypes.NullFlavor;

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
		final String test = "test";

		AuthorIdLens lens = new AuthorIdLens();
		assertNotNull(lens);

		// TODO
		assertNotNull(lens.get(null));
		assertNotNull(lens.get(test));
	}

	@Test
	public void authorIdLensPutTest() {
		final String test = "test";

		AuthorIdLens lens = new AuthorIdLens();
		assertNotNull(lens);

		assertNull(lens.put(null));
		assertEquals(test, lens.put(test, null));
	}
}
