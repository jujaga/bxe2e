package org.oscarehr.e2e.lens.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oscarehr.e2e.lens.Test1Lens;
import org.oscarehr.e2e.lens.Test2Lens;

// TODO Build out this class to be a template for inherited lenses
public class AbstractLensTest {
	AbstractLens<String, Boolean> test;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
	}

	@Before
	public void before() {
		test = new Test1Lens().compose(new Test2Lens());
	}

	@Test
	public void abstractLensInstantiationTest() {
		AbstractLens<String, Boolean> test = new AbstractLens<>();
		assertNotNull(test);
	}

	@Test
	public void lensGetTest() {
		assertNotNull(test.get(null));
	}

	@Test
	public void lensPutTest() {
		assertNotNull(test.put(false));
	}

	@Test
	public void lensComposeTest() {
		test = new Test1Lens().compose(new Test2Lens());
		assertFalse(test.get("0"));
		assertEquals("1", test.put(true));
	}
}
