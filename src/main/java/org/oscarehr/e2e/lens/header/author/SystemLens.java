package org.oscarehr.e2e.lens.header.author;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.SC;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.AssignedAuthor;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.AuthoringDevice;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.lens.common.AbstractLens;

public class SystemLens extends AbstractLens<Pair<String, ArrayList<Author>>, Pair<String, ArrayList<Author>>> {
	public SystemLens() {
		get = source -> {
			Author system = new Author();
			AssignedAuthor assignedSystem = new AssignedAuthor();

			system.setContextControlCode(ContextControl.OverridingPropagating);
			system.setTime(new GregorianCalendar(), TS.DAY);
			system.setAssignedAuthor(assignedSystem);

			II id = new II();
			id.setNullFlavor(NullFlavor.NoInformation);
			assignedSystem.setId(new SET<>(id));

			AuthoringDevice device = new AuthoringDevice();
			device.setSoftwareName(new SC(Constants.EMR.EMR_VERSION));
			assignedSystem.setAssignedAuthorChoice(device);

			source.getRight().add(system);
			return new ImmutablePair<>(source.getLeft(), source.getRight());
		};

		put = (source, target) -> {
			return new ImmutablePair<>(target.getLeft(), target.getRight());
		};
	}
}
