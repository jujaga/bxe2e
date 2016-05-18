package org.oscarehr.e2e.rule.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeNotNull;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;

public class AbstractRuleTest {
	protected AbstractRule<?, ?> rule;
	protected List<String> lensTypes;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
	}

	/**
	 * Setup base rule test. This should be ran on every inherited rule in the before clause.
	 *
	 * @param rule the rule
	 */
	protected void setupBaseRuleTest(AbstractRule<?, ?> rule) {
		assumeNotNull(rule);
		assertNotNull(rule.getName());
		assertFalse(rule.executed());
		assertNotNull(rule.getPair());
		assertNotNull(rule.getLens());

		this.rule = rule;
		this.lensTypes = rule.getLens().getLensTypes();
	}
}
