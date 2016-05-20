package org.oscarehr.e2e.constant;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConstantsTest {
	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
	}

	@Test(expected=UnsupportedOperationException.class)
	public void bodyConstantsInstantiationTest() {
		new BodyConstants();
	}

	@Test(expected=UnsupportedOperationException.class)
	public void constantsInstantiationTest() {
		new Constants();
	}

	@Test(expected=UnsupportedOperationException.class)
	public void mappingsInstantiationTest() {
		new Mappings();
	}

	@Test
	public void mappingsFieldsNotNullTest() {
		for(Field f : Mappings.class.getFields()){
			f.setAccessible(true);
			try {
				assertNotNull(f.get(Mappings.class));
			} catch (IllegalArgumentException|IllegalAccessException e) {
				// Do Nothing
			}
		}
	}
}
