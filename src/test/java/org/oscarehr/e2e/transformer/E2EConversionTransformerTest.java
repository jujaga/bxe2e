package org.oscarehr.e2e.transformer;

import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.oscarehr.e2e.model.PatientModel;
import org.oscarehr.e2e.rule.common.IRule.Original;

public class E2EConversionTransformerTest {
	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
	}

	@Test(expected=NullPointerException.class)
	public void nullOriginalTest() {
		new E2EConversionTransformer(null, null, null);
	}

	@Test
	public void modelTransformationTest() {
		E2EConversionTransformer transformer = new E2EConversionTransformer(new PatientModel(), null, Original.SOURCE);
		assertNotNull(transformer.getTarget());
	}

	@Test
	public void targetTransformationTest() {
		E2EConversionTransformer transformer = new E2EConversionTransformer(null, new ClinicalDocument(), Original.TARGET);
		assertNotNull(transformer.getModel());
	}
}
