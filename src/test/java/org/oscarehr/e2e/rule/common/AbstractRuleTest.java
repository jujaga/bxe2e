package org.oscarehr.e2e.rule.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeNotNull;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;

public class AbstractRuleTest {
	protected IRule<?, ?> nullRule = null;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
	}

	/**
	 * Base rule test. This should be ran on every inherited rule in the before clause.
	 *
	 * @param rule the rule
	 */
	protected void baseRuleTest(IRule<?, ?> rule) {
		if(nullRule == null) {
			assumeNotNull(rule);
			assertNotNull(rule.getName());
			assertFalse(rule.executed());
			assertNotNull(rule.getPair());

			nullRule = rule;
		}
	}
}
