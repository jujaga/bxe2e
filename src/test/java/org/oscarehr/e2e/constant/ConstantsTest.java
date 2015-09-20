package org.oscarehr.e2e.constant;

import org.junit.Test;
import org.oscarehr.e2e.constant.BodyConstants;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.constant.Mappings;

public class ConstantsTest {
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
}
