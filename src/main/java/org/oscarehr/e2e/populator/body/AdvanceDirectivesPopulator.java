package org.oscarehr.e2e.populator.body;

import java.util.Arrays;
import java.util.List;

import org.marc.everest.datatypes.ED;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.IVL;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalStatement;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActStatus;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.constant.BodyConstants.AdvanceDirectives;
import org.oscarehr.e2e.util.EverestUtils;

public class AdvanceDirectivesPopulator extends AbstractBodyPopulator<AdvanceDirectivesPopulator> {
	AdvanceDirectivesPopulator() {
		bodyConstants = AdvanceDirectives.getConstants();
		populateClinicalStatement(Arrays.asList(this));
	}

	@Override
	public ClinicalStatement populateClinicalStatement(List<AdvanceDirectivesPopulator> list) {
		return null;
	}

	@Override
	public ClinicalStatement populateNullFlavorClinicalStatement() {
		Observation observation = new Observation(x_ActMoodDocumentObservation.Eventoccurrence);

		observation.setId(EverestUtils.buildUniqueId(Constants.IdPrefixes.AdvanceDirectives, null));
		observation.setCode(new CD<String>() {{setNullFlavor(NullFlavor.NoInformation);}});
		observation.setText(new ED() {{setNullFlavor(NullFlavor.NoInformation);}});
		observation.setStatusCode(ActStatus.Completed);
		observation.setEffectiveTime(new IVL<TS>() {{setNullFlavor(NullFlavor.NoInformation);}});

		return observation;
	}

	@Override
	public List<String> populateText() {
		return null;
	}
}
