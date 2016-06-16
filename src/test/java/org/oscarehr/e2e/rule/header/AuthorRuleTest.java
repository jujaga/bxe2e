package org.oscarehr.e2e.rule.header;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.oscarehr.e2e.lens.header.author.AuthorLens;
import org.oscarehr.e2e.lens.header.author.ProviderIdLens;
import org.oscarehr.e2e.lens.header.author.ProviderLens;
import org.oscarehr.e2e.lens.header.author.ProviderPersonLens;
import org.oscarehr.e2e.lens.header.author.ProviderTelecomLens;
import org.oscarehr.e2e.lens.header.author.SystemLens;
import org.oscarehr.e2e.rule.common.AbstractRuleTest;
import org.oscarehr.e2e.rule.common.IRule.Original;

public class AuthorRuleTest extends AbstractRuleTest {
	@Before
	public void before() {
		setupBaseRuleTest(new AuthorRule(null, null));
	}

	@Test
	public void authorRuleLensDefinitionTest() {
		assertTrue(lensTypes.contains(AuthorLens.class.getSimpleName()));
		assertTrue(lensTypes.contains(ProviderLens.class.getSimpleName()));
		assertTrue(lensTypes.contains(ProviderIdLens.class.getSimpleName()));
		assertTrue(lensTypes.contains(ProviderTelecomLens.class.getSimpleName()));
		assertTrue(lensTypes.contains(ProviderPersonLens.class.getSimpleName()));
		assertTrue(lensTypes.contains(SystemLens.class.getSimpleName()));
	}

	@Test
	public void authorRuleNullTest() {
		assertTrue(rule.getName().startsWith(AuthorRule.class.getSimpleName()));
		assertNotNull(rule.getTarget());
	}

	@Test
	public void authorRuleSourceExecutionTest() {
		rule.setOriginal(Original.SOURCE);
		rule.execute();
		assertTrue(rule.executed());
	}

	@Test
	public void authorRuleTargetExecutionTest() {
		rule.setOriginal(Original.TARGET);
		rule.execute();
		assertTrue(rule.executed());
	}
}
