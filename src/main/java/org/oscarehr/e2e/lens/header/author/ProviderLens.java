package org.oscarehr.e2e.lens.header.author;

import java.util.GregorianCalendar;

import org.marc.everest.datatypes.TS;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.AssignedAuthor;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;
import org.oscarehr.common.model.Provider;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.util.EverestUtils;

public class ProviderLens extends AbstractLens<String, Author> {
	ProviderLens() {
		get = providerNo -> {
			Provider provider = EverestUtils.getProviderFromString(providerNo);

			Author author = new Author();
			AssignedAuthor assignedAuthor = new AssignedAuthor();

			author.setContextControlCode(ContextControl.OverridingPropagating);
			author.setTime(new GregorianCalendar(), TS.DAY);
			author.setAssignedAuthor(assignedAuthor);

			assignedAuthor.setId(new ProviderIdLens().get(provider));
			assignedAuthor.setTelecom(new TelecomLens().get(provider));
			assignedAuthor.setAssignedAuthorChoice(new PersonLens().get(provider));

			return author;
		};

		// TODO Put Function
	}
}
