package org.oscarehr.e2e.populator.header;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Test;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.InformationRecipient;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.IntendedRecipient;
import org.oscarehr.e2e.populator.AbstractPopulatorTest;

public class InformationRecipientPopulatorTest extends AbstractPopulatorTest {
	@Test
	public void informationRecipientTest() {
		ArrayList<InformationRecipient> informationRecipients = clinicalDocument.getInformationRecipient();
		assertNotNull(informationRecipients);
		assertEquals(1, informationRecipients.size());
		assertNotNull(informationRecipients.get(0));
	}

	@Test
	public void intendedRecipientTest() {
		IntendedRecipient intendedRecipient = clinicalDocument.getInformationRecipient().get(0).getIntendedRecipient();
		assertNotNull(intendedRecipient);
	}
}
