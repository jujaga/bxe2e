package org.oscarehr.e2e.lens.body.template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.TS;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.AssignedAuthor;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.lens.common.AbstractLens;
import org.oscarehr.e2e.lens.common.AuthorIdLens;
import org.oscarehr.e2e.lens.common.AuthorPersonLens;
import org.oscarehr.e2e.lens.common.TSDateLens;

public class AuthorParticipationLens extends AbstractLens<Date, ArrayList<Author>> {
	public AuthorParticipationLens(final String providerNo) {
		get = date -> {
			TS time = new TSDateLens().get(date);
			if(time == null) {
				time = new TS();
				time.setNullFlavor(NullFlavor.Unknown);
			}

			AssignedAuthor assignedAuthor = new AssignedAuthor();
			assignedAuthor.setId(new AuthorIdLens().get(providerNo));
			assignedAuthor.setAssignedAuthorChoice(new AuthorPersonLens().get(providerNo));

			Author author = new Author();
			author.setContextControlCode(ContextControl.OverridingPropagating);
			author.setTemplateId(Arrays.asList(new II(Constants.TemplateOids.AUTHOR_PARTICIPATION_TEMPLATE_ID)));
			author.setTime(time);
			author.setAssignedAuthor(assignedAuthor);

			return new ArrayList<>(Arrays.asList(author));
		};

		put = (date, authors) -> {
			if(authors != null && !authors.isEmpty()) {
				Author author = authors.get(0);
				if(author != null) {
					date = new TSDateLens().put(author.getTime());
				}
			}

			return date;
		};
	}
}
