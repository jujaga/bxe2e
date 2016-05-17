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

public class BaseLensTest {
	private AbstractLens<String, Boolean> lens = new StringIntLens().compose(new IntBooleanLens());

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
	}

	@Test
	public void abstractLensInstantiationTest() {
		assertNotNull(new AbstractLens<>());
	}

	@Test
	public void lensComposeTest() {
		assertNotNull(lens);
	}

	@Test
	public void lensComposeGetTest() {
		assertFalse(lens.get("0"));
		assertNotNull(lens.get(null));
	}

	@Test
	public void lensComposePutTest() {
		assertEquals("1", lens.put(true));
		assertNotNull(lens.put(false));
	}

	@Test
	public void stringIntGetTest() {
		StringIntLens subLens = new StringIntLens();
		assertEquals(new Integer(2), subLens.get("2"));
		assertEquals(new Integer(0), subLens.get(null));
	}

	@Test
	public void stringIntPutTest() {
		StringIntLens subLens = new StringIntLens();
		assertEquals("2", subLens.put(2));
		assertNull(subLens.put(null));
	}

	@Test
	public void intBooleanGetTest() {
		IntBooleanLens subLens = new IntBooleanLens();
		assertTrue(subLens.get(1));
		assertNull(subLens.get(null));
	}

	@Test
	public void intBooleanPutTest() {
		IntBooleanLens subLens = new IntBooleanLens();
		assertEquals(new Integer(1), subLens.put(true));
		assertEquals(new Integer(0), subLens.put(false));
	}

	private class StringIntLens extends AbstractLens<String, Integer> {
		public StringIntLens() {
			get = value -> {
				try {
					return Integer.parseInt(value);
				} catch (NumberFormatException e) {
					return 0;
				}
			};

			put = (value, number) -> {
				try {
					return Integer.toString(number);
				} catch (NullPointerException e) {
					return null;
				}
			};
		}
	}

	private class IntBooleanLens extends AbstractLens<Integer, Boolean> {
		public IntBooleanLens() {
			get = number -> {
				if(number != null) {
					return (number != 0);
				}
				return null;
			};

			put = (number, state) -> {
				return state ? 1 : 0;
			};
		}
	}
}
