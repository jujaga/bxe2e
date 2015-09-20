package org.oscarehr.e2e.populator.body;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.marc.everest.datatypes.ED;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.IVL;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.AssignedEntity;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Performer2;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.constant.BodyConstants.OrdersAndRequests;
import org.oscarehr.e2e.model.export.template.AuthorParticipationModel;
import org.oscarehr.e2e.util.EverestUtils;

public class OrdersAndRequestsPopulator extends AbstractBodyPopulator<OrdersAndRequestsPopulator> {
	OrdersAndRequestsPopulator() {
		bodyConstants = OrdersAndRequests.getConstants();
		populateClinicalStatement(Arrays.asList(this));
	}

	@Override
	public ClinicalStatement populateClinicalStatement(List<OrdersAndRequestsPopulator> list) {
		return null;
	}

	@Override
	public ClinicalStatement populateNullFlavorClinicalStatement() {
		Observation observation = new Observation(x_ActMoodDocumentObservation.RQO);

		observation.setId(EverestUtils.buildUniqueId(Constants.IdPrefixes.Referrals, 0));
		observation.setCode(new CD<String>() {{setNullFlavor(NullFlavor.NoInformation);}});
		observation.setText(new ED() {{setNullFlavor(NullFlavor.NoInformation);}});
		observation.setEffectiveTime(new IVL<TS>() {{setNullFlavor(NullFlavor.NoInformation);}});

		II ii = new II();
		ii.setNullFlavor(NullFlavor.NoInformation);
		AssignedEntity assignedEntity = new AssignedEntity();
		assignedEntity.setId(new SET<II>(ii));
		Performer2 performer = new Performer2(assignedEntity);
		observation.setPerformer(new ArrayList<Performer2>(Arrays.asList(performer)));
		observation.setAuthor(new ArrayList<Author>(Arrays.asList(new AuthorParticipationModel().getAuthor(null))));

		return observation;
	}

	@Override
	public List<String> populateText() {
		return null;
	}
}
