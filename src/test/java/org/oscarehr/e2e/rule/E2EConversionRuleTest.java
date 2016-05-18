package org.oscarehr.e2e.rule;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.oscarehr.e2e.lens.CDALens;
import org.oscarehr.e2e.lens.E2EConversionLens;
import org.oscarehr.e2e.lens.body.DocumentBodyLens;
import org.oscarehr.e2e.lens.body.section.AdvanceDirectivesSectionLens;
import org.oscarehr.e2e.lens.body.section.ProblemsSectionLens;
import org.oscarehr.e2e.rule.common.AbstractRule;
import org.oscarehr.e2e.rule.common.AbstractRuleTest;
import org.oscarehr.e2e.rule.common.IRule.Original;

public class E2EConversionRuleTest extends AbstractRuleTest {
	@Before
	public void before() {
		setupBaseRuleTest(new E2EConversionRule(null, null));
	}

	@Test
	public void e2eConversionRuleLensDefinitionTest() {
		assertTrue(lensTypes.contains(CDALens.class.getSimpleName()));
		assertTrue(lensTypes.contains(DocumentBodyLens.class.getSimpleName()));
		assertTrue(lensTypes.contains(E2EConversionLens.class.getSimpleName()));
		assertTrue(lensTypes.contains(AdvanceDirectivesSectionLens.class.getSimpleName()));
		assertTrue(lensTypes.contains(ProblemsSectionLens.class.getSimpleName()));
	}

	@Test
	public void e2eConversionRuleNullTest() {
		assertTrue(rule.getName().startsWith(E2EConversionRule.class.getSimpleName()));
		assertNotNull(rule.getSource());
		assertNotNull(rule.getTarget());
	}

	@Test
	public void e2eConversionRuleSourceExecutionTest() {
		AbstractRule.setOriginal(Original.SOURCE);
		rule.execute();
		assertTrue(rule.executed());
	}

	@Test
	public void e2eConversionRuleTargetExecutionTest() {
		AbstractRule.setOriginal(Original.TARGET);
		rule.execute();
		assertTrue(rule.executed());
	}
}
