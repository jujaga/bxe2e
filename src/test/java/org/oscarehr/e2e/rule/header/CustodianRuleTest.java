package org.oscarehr.e2e.rule.header;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.oscarehr.e2e.lens.header.custodian.CustodianIdLens;
import org.oscarehr.e2e.lens.header.custodian.CustodianLens;
import org.oscarehr.e2e.lens.header.custodian.CustodianNameLens;
import org.oscarehr.e2e.rule.common.AbstractRuleTest;
import org.oscarehr.e2e.rule.common.IRule.Original;

public class CustodianRuleTest extends AbstractRuleTest {
	@Before
	public void before() {
		setupBaseRuleTest(new CustodianRule(null, null));
	}

	@Test
	public void custodianRuleLensDefinitionTest() {
		assertTrue(lensTypes.contains(CustodianLens.class.getSimpleName()));
		assertTrue(lensTypes.contains(CustodianIdLens.class.getSimpleName()));
		assertTrue(lensTypes.contains(CustodianNameLens.class.getSimpleName()));
	}

	@Test
	public void custodianRuleNullTest() {
		assertTrue(rule.getName().startsWith(CustodianRule.class.getSimpleName()));
		assertNotNull(rule.getSource());
		assertNotNull(rule.getTarget());
	}

	@Test
	public void custodianRuleSourceExecutionTest() {
		rule.setOriginal(Original.SOURCE);
		rule.execute();
		assertTrue(rule.executed());
	}

	@Test
	public void custodianRuleTargetExecutionTest() {
		rule.setOriginal(Original.TARGET);
		rule.execute();
		assertTrue(rule.executed());
	}
}
