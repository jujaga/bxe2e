package org.oscarehr.e2e.rule.body;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.oscarehr.e2e.lens.body.problems.ProblemsAuthorLens;
import org.oscarehr.e2e.lens.body.problems.ProblemsCodeLens;
import org.oscarehr.e2e.lens.body.problems.ProblemsDiagnosisDateLens;
import org.oscarehr.e2e.lens.body.problems.ProblemsEffectiveTimeLens;
import org.oscarehr.e2e.lens.body.problems.ProblemsICD9Lens;
import org.oscarehr.e2e.lens.body.problems.ProblemsIdLens;
import org.oscarehr.e2e.lens.body.problems.ProblemsLens;
import org.oscarehr.e2e.lens.body.problems.ProblemsStatusCodeLens;
import org.oscarehr.e2e.lens.body.problems.ProblemsTextLens;
import org.oscarehr.e2e.lens.body.problems.ProblemsValueLens;
import org.oscarehr.e2e.rule.common.AbstractRule;
import org.oscarehr.e2e.rule.common.AbstractRuleTest;
import org.oscarehr.e2e.rule.common.IRule.Original;

public class ProblemsRuleTest extends AbstractRuleTest {
	@Before
	public void before() {
		setupBaseRuleTest(new ProblemsRule(null, null));
	}

	@Test
	public void problemsRuleLensDefinitionTest() {
		assertTrue(lensTypes.contains(ProblemsLens.class.getSimpleName()));
		assertTrue(lensTypes.contains(ProblemsIdLens.class.getSimpleName()));
		assertTrue(lensTypes.contains(ProblemsCodeLens.class.getSimpleName()));
		assertTrue(lensTypes.contains(ProblemsTextLens.class.getSimpleName()));
		assertTrue(lensTypes.contains(ProblemsStatusCodeLens.class.getSimpleName()));
		assertTrue(lensTypes.contains(ProblemsEffectiveTimeLens.class.getSimpleName()));
		assertTrue(lensTypes.contains(ProblemsValueLens.class.getSimpleName()));
		assertTrue(lensTypes.contains(ProblemsAuthorLens.class.getSimpleName()));
		assertTrue(lensTypes.contains(ProblemsICD9Lens.class.getSimpleName()));
		assertTrue(lensTypes.contains(ProblemsDiagnosisDateLens.class.getSimpleName()));
	}

	@Test
	public void problemsRuleNullTest() {
		assertTrue(rule.getName().startsWith(ProblemsRule.class.getSimpleName()));
		assertNotNull(rule.getSource());
		assertNotNull(rule.getTarget());
	}

	@Test
	public void problemsRuleSourceExecutionTest() {
		AbstractRule.setOriginal(Original.SOURCE);
		rule.execute();
		assertTrue(rule.executed());
	}

	@Test
	public void problemsRuleTargetExecutionTest() {
		AbstractRule.setOriginal(Original.TARGET);
		rule.execute();
		assertTrue(rule.executed());
	}
}
