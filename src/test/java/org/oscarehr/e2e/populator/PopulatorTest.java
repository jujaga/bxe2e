package org.oscarehr.e2e.populator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.PatientExport;
import org.oscarehr.e2e.populator.AbstractPopulator;
import org.oscarehr.e2e.populator.EmrExportPopulator;

public class PopulatorTest {
	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
	}

	@Test
	public void emptyEmrExportPopulatorTest() {
		PatientExport patientExport = new PatientExport(Constants.Runtime.EMPTY_DEMOGRAPHIC);
		CE<String> code = Constants.EMRConversionDocument.CODE;
		II templateId = new II(Constants.EMRConversionDocument.TEMPLATE_ID);

		AbstractPopulator populator = new EmrExportPopulator(patientExport, code, templateId);
		populator.populate();
		assertNotNull(populator);

		ClinicalDocument clinicalDocument = populator.getClinicalDocument();
		assertNotNull(clinicalDocument);
	}

	@Test
	public void invalidEmrExportPopulatorTest() {
		PatientExport patientExport = new PatientExport(Constants.Runtime.INVALID_VALUE);
		CE<String> code = Constants.EMRConversionDocument.CODE;
		II templateId = new II(Constants.EMRConversionDocument.TEMPLATE_ID);

		AbstractPopulator populator = new EmrExportPopulator(patientExport, code, templateId);
		populator.populate();
		assertNotNull(populator);

		ClinicalDocument clinicalDocument = populator.getClinicalDocument();
		assertNull(clinicalDocument);
	}
}
