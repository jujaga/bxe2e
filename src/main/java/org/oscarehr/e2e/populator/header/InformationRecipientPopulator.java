package org.oscarehr.e2e.populator.header;

import java.util.ArrayList;
import java.util.Arrays;

import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.InformationRecipient;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.IntendedRecipient;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_InformationRecipient;
import org.oscarehr.e2e.populator.AbstractPopulator;

class InformationRecipientPopulator extends AbstractPopulator {
	InformationRecipientPopulator() {
	}

	@Override
	public void populate() {
		InformationRecipient informationRecipient = new InformationRecipient();
		IntendedRecipient intendedRecipient = new IntendedRecipient();

		informationRecipient.setIntendedRecipient(intendedRecipient);
		informationRecipient.setTypeCode(x_InformationRecipient.PRCP);

		intendedRecipient.setNullFlavor(NullFlavor.NoInformation);

		clinicalDocument.setInformationRecipient(new ArrayList<InformationRecipient>(Arrays.asList(informationRecipient)));
	}
}
