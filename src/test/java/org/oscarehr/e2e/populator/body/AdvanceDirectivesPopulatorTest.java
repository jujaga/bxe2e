package org.oscarehr.e2e.populator.body;

import org.junit.BeforeClass;
import org.junit.Test;
import org.oscarehr.e2e.constant.BodyConstants.AdvanceDirectives;

public class AdvanceDirectivesPopulatorTest extends AbstractBodyPopulatorTest {
	@BeforeClass
	public static void beforeClass() {
		setupClass(AdvanceDirectives.getConstants());
	}

	@Test
	public void advanceDirectivesComponentSectionTest() {
		componentSectionTest();
	}

	@Test
	public void advanceDirectivesEntryCountTest() {
		entryCountTest(1);
	}
}
