package org.oscarehr.e2e.rule.common;

import static org.junit.Assert.assertFalse;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.rule.common.IRule.Original;

public class BadRuleTest {
	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
	}

	@Test
	public void badRuleSourceExecutionTest() {
		BadRule rule = new BadRule(null, null);
		AbstractRule.setOriginal(Original.SOURCE);
		rule.execute();
		assertFalse(rule.executed());
	}

	private static class BadRule extends AbstractRule<Object, Object> {
		protected BadRule(Object source, Object target) {
			super(source, target);
		}

		@Override
		protected AbstractLens<Pair<Object, Object>, Pair<Object, Object>> defineLens() {
			throw new NullPointerException();
		}
	}
}
