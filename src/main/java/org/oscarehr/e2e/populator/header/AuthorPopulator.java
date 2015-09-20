package org.oscarehr.e2e.populator.header;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.marc.everest.datatypes.TS;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.AssignedAuthor;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;
import org.oscarehr.common.model.Provider;
import org.oscarehr.e2e.model.PatientExport;
import org.oscarehr.e2e.model.export.header.AuthorModel;
import org.oscarehr.e2e.populator.AbstractPopulator;
import org.oscarehr.e2e.util.EverestUtils;

class AuthorPopulator extends AbstractPopulator {
	private final AuthorModel authorModel;

	AuthorPopulator(PatientExport patientExport) {
		Provider provider = EverestUtils.getProviderFromString(patientExport.getDemographic().getProviderNo());
		authorModel = new AuthorModel(provider);
	}

	@Override
	public void populate() {
		ArrayList<Author> authors = new ArrayList<Author>();

		authors.add(getProvider());
		authors.add(getSystem());

		clinicalDocument.setAuthor(authors);
	}

	private Author getProvider() {
		Author provider = new Author();
		AssignedAuthor assignedAuthor = new AssignedAuthor();

		provider.setContextControlCode(ContextControl.OverridingPropagating);
		provider.setTime(new GregorianCalendar(), TS.DAY);
		provider.setAssignedAuthor(assignedAuthor);

		assignedAuthor.setId(authorModel.getIds());
		assignedAuthor.setTelecom(authorModel.getTelecoms());
		assignedAuthor.setAssignedAuthorChoice(authorModel.getPerson());

		return provider;
	}

	private Author getSystem() {
		Author system = new Author();
		AssignedAuthor assignedSystem = new AssignedAuthor();

		system.setContextControlCode(ContextControl.OverridingPropagating);
		system.setTime(new GregorianCalendar(), TS.DAY);
		system.setAssignedAuthor(assignedSystem);

		assignedSystem.setId(authorModel.getDeviceIds());
		assignedSystem.setAssignedAuthorChoice(authorModel.getDevice());

		return system;
	}
}
