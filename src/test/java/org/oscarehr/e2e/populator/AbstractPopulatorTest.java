package org.oscarehr.e2e.populator;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.director.E2ECreator;

public abstract class AbstractPopulatorTest {
	protected static ClinicalDocument clinicalDocument;

	@BeforeClass
	public static void abstractBeforeClass() {
		clinicalDocument = E2ECreator.createEmrConversionDocument(Constants.Runtime.VALID_DEMOGRAPHIC);
		assertNotNull(clinicalDocument);
	}
}
