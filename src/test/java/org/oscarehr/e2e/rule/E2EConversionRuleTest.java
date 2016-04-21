package org.oscarehr.e2e.rule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oscarehr.e2e.rule.common.IRule;

public class E2EConversionRuleTest {
	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
	}

	// TODO Consider abstracting this up since it's common to all rules
	@Test
	public void e2eConversionRuleNullTest() {
		IRule<?, ?> e2eConversionRule = new E2EConversionRule(null, null);
		assertNotNull(e2eConversionRule);
		assertNotNull(e2eConversionRule.getName());
		assertEquals(E2EConversionRule.class.getSimpleName(), e2eConversionRule.getName());

		assertFalse(e2eConversionRule.executed());
		assertNotNull(e2eConversionRule.getPair());
		assertNotNull(e2eConversionRule.getSource());
		assertNotNull(e2eConversionRule.getTarget());
	}
}
