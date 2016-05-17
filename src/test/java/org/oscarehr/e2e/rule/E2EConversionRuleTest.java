package org.oscarehr.e2e.rule;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.oscarehr.e2e.rule.common.AbstractRuleTest;

public class E2EConversionRuleTest extends AbstractRuleTest {
	@Before
	public void before() {
		baseRuleTest(new E2EConversionRule(null, null));
	}

	@Test
	public void e2eConversionRuleNullTest() {
		assertTrue(nullRule.getName().startsWith(E2EConversionRule.class.getSimpleName()));
		assertNotNull(nullRule.getSource());
		assertNotNull(nullRule.getTarget());
	}

	// TODO Determine lenses defined in the rule
}
