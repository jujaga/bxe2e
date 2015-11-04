package org.oscarehr.e2e.lens.header;

import java.util.ArrayList;
import java.util.Arrays;

import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.InformationRecipient;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.IntendedRecipient;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_InformationRecipient;
import org.oscarehr.e2e.lens.common.AbstractLens;

public class InformationRecipientLens extends AbstractLens<Object, ArrayList<InformationRecipient>> {
	public InformationRecipientLens() {
		get = object -> {
			InformationRecipient informationRecipient = new InformationRecipient();
			IntendedRecipient intendedRecipient = new IntendedRecipient();

			informationRecipient.setIntendedRecipient(intendedRecipient);
			informationRecipient.setTypeCode(x_InformationRecipient.PRCP);

			intendedRecipient.setNullFlavor(NullFlavor.NoInformation);
			return new ArrayList<>(Arrays.asList(informationRecipient));
		};

		put = (object, informationRecipients) -> {
			return null;
		};
	}
}
