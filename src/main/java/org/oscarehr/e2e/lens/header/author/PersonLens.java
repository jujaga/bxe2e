package org.oscarehr.e2e.lens.header.author;

import java.util.ArrayList;
import java.util.List;

import org.marc.everest.datatypes.ENXP;
import org.marc.everest.datatypes.EntityNamePartType;
import org.marc.everest.datatypes.EntityNameUse;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.PN;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Person;
import org.oscarehr.common.model.Provider;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.lens.common.NamePartLens;
import org.oscarehr.e2e.util.EverestUtils;

public class PersonLens extends AbstractLens<Provider, Person> {
	PersonLens() {
		get = provider -> {
			Person person = new Person();
			SET<PN> names = new SET<>();
			List<ENXP> name = new ArrayList<>();
			if(!EverestUtils.isNullorEmptyorWhitespace(provider.getFirstName())) {
				name.add(new NamePartLens(EntityNamePartType.Given).get(provider.getFirstName()));
			}
			if(!EverestUtils.isNullorEmptyorWhitespace(provider.getLastName())) {
				name.add(new NamePartLens(EntityNamePartType.Family).get(provider.getLastName()));
			}

			if(!name.isEmpty()) {
				names = new SET<>(new PN(EntityNameUse.OfficialRecord, name));
			} else {
				PN pn = new PN();
				pn.setNullFlavor(NullFlavor.NoInformation);
				names.add(pn);
			}
			person.setName(names);
			return person;
		};

		// TODO Put Function
	}
}