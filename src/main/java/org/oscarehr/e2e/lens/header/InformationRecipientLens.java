package org.oscarehr.e2e.lens.header;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.InformationRecipient;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.IntendedRecipient;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_InformationRecipient;
import org.oscarehr.e2e.lens.common.AbstractLens;

public class InformationRecipientLens extends AbstractLens<Pair<Object, ArrayList<InformationRecipient>>, Pair<Object, ArrayList<InformationRecipient>>> {
	public InformationRecipientLens() {
		get = source -> {
			ArrayList<InformationRecipient> informationRecipients = source.getRight();

			if(informationRecipients.isEmpty()) {
				IntendedRecipient intendedRecipient = new IntendedRecipient();
				intendedRecipient.setNullFlavor(NullFlavor.NoInformation);

				informationRecipients.add(new InformationRecipient(x_InformationRecipient.PRCP, intendedRecipient));
			}

			return new ImmutablePair<>(source.getLeft(), informationRecipients);
		};

		put = (source, target) -> {
			return new ImmutablePair<>(target.getLeft(), target.getRight());
		};
	}
}
