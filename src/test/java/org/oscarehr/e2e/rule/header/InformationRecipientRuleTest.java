package org.oscarehr.e2e.rule.header;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.oscarehr.e2e.lens.header.InformationRecipientLens;
import org.oscarehr.e2e.rule.common.AbstractRule;
import org.oscarehr.e2e.rule.common.AbstractRuleTest;
import org.oscarehr.e2e.rule.common.IRule.Original;

public class InformationRecipientRuleTest extends AbstractRuleTest {
	@Before
	public void before() {
		setupBaseRuleTest(new InformationRecipientRule(null, null));
	}

	@Test
	public void informationRecipientRuleLensDefinitionTest() {
		assertTrue(lensTypes.contains(InformationRecipientLens.class.getSimpleName()));
	}

	@Test
	public void informationRecipientRuleNullTest() {
		assertTrue(rule.getName().startsWith(InformationRecipientRule.class.getSimpleName()));
		assertNotNull(rule.getSource());
		assertNotNull(rule.getTarget());
	}

	@Test
	public void informationRecipientRuleSourceExecutionTest() {
		AbstractRule.setOriginal(Original.SOURCE);
		rule.execute();
		assertTrue(rule.executed());
	}

	@Test
	public void informationRecipientRuleTargetExecutionTest() {
		AbstractRule.setOriginal(Original.TARGET);
		rule.execute();
		assertTrue(rule.executed());
	}
}
