package org.oscarehr.e2e.rule.header;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.oscarehr.e2e.lens.header.recordtarget.AddressLens;
import org.oscarehr.e2e.lens.header.recordtarget.BirthDateLens;
import org.oscarehr.e2e.lens.header.recordtarget.GenderLens;
import org.oscarehr.e2e.lens.header.recordtarget.HinIdLens;
import org.oscarehr.e2e.lens.header.recordtarget.LanguageLens;
import org.oscarehr.e2e.lens.header.recordtarget.NameLens;
import org.oscarehr.e2e.lens.header.recordtarget.RecordTargetLens;
import org.oscarehr.e2e.lens.header.recordtarget.TelecomLens;
import org.oscarehr.e2e.rule.common.AbstractRuleTest;
import org.oscarehr.e2e.rule.common.IRule.Original;

public class RecordTargetRuleTest extends AbstractRuleTest {
	@Before
	public void before() {
		setupBaseRuleTest(new RecordTargetRule(null, null));
	}

	@Test
	public void recordTargetRuleLensDefinitionTest() {
		assertTrue(lensTypes.contains(RecordTargetLens.class.getSimpleName()));
		assertTrue(lensTypes.contains(HinIdLens.class.getSimpleName()));
		assertTrue(lensTypes.contains(AddressLens.class.getSimpleName()));
		assertTrue(lensTypes.contains(TelecomLens.class.getSimpleName()));
		assertTrue(lensTypes.contains(NameLens.class.getSimpleName()));
		assertTrue(lensTypes.contains(GenderLens.class.getSimpleName()));
		assertTrue(lensTypes.contains(BirthDateLens.class.getSimpleName()));
		assertTrue(lensTypes.contains(LanguageLens.class.getSimpleName()));
	}

	@Test
	public void recordTargetRuleNullTest() {
		assertTrue(rule.getName().startsWith(RecordTargetRule.class.getSimpleName()));
		assertNotNull(rule.getSource());
		assertNotNull(rule.getTarget());
	}

	@Test
	public void recordTargetRuleSourceExecutionTest() {
		rule.setOriginal(Original.SOURCE);
		rule.execute();
		assertTrue(rule.executed());
	}

	@Test
	public void recordTargetRuleTargetExecutionTest() {
		rule.setOriginal(Original.TARGET);
		rule.execute();
		assertTrue(rule.executed());
	}
}
