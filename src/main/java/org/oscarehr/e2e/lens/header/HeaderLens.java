package org.oscarehr.e2e.lens.header;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.UUID;

import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.datatypes.generic.CS;
import org.marc.everest.datatypes.generic.LIST;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.marc.everest.rmim.uv.cdar2.vocabulary.BindingRealm;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_BasicConfidentialityKind;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.lens.Lens;
import org.oscarehr.e2e.lens.header.recordtarget.RecordTargetLens;
import org.oscarehr.e2e.model.PatientModel;

public class HeaderLens implements Lens<PatientModel, ClinicalDocument>{
	private final CE<String> code;
	private final II templateId;

	public HeaderLens(CE<String> code, II templateId) {
		this.code = code;
		this.templateId = templateId;
	}

	@Override
	public ClinicalDocument get(PatientModel patientModel) {
		ClinicalDocument clinicalDocument = new ClinicalDocument();

		// realmCode
		CS<BindingRealm> binding = new CS<BindingRealm>();
		binding.setCodeEx(new BindingRealm(Constants.DocumentHeader.E2E_DTC_CLINICAL_DOCUMENT_TYPE_REALM_CODE, null));
		clinicalDocument.setRealmCode(new SET<CS<BindingRealm>>(binding));

		// typeId
		clinicalDocument.setTypeId(new II(
				Constants.DocumentHeader.E2E_DTC_CLINICAL_DOCUMENT_TYPE_ID,
				Constants.DocumentHeader.E2E_DTC_CLINICAL_DOCUMENT_TYPE_ID_EXTENSION));

		// templateId
		LIST<II> templateIds = new LIST<II>();
		templateIds.add(new II(Constants.DocumentHeader.TEMPLATE_ID));
		templateIds.add(templateId);
		clinicalDocument.setTemplateId(templateIds);

		// id
		clinicalDocument.setId(UUID.randomUUID().toString().toUpperCase(), patientModel.getDemographic().getDemographicNo().toString());

		// code
		clinicalDocument.setCode(code);

		// title
		clinicalDocument.setTitle("E2E-DTC Record of ".concat(patientModel.getDemographic().getFirstName()).concat(" ").concat(patientModel.getDemographic().getLastName()));

		// effectiveTime
		clinicalDocument.setEffectiveTime(new GregorianCalendar(), TS.MINUTE);

		// confidentialityCode
		clinicalDocument.setConfidentialityCode(x_BasicConfidentialityKind.Normal);

		// languageCode
		clinicalDocument.setLanguageCode(Constants.DocumentHeader.LANGUAGE_ENGLISH_CANADIAN);

		// recordTarget
		RecordTarget recordTarget = new RecordTargetLens().get(patientModel.getDemographic());
		clinicalDocument.setRecordTarget(new ArrayList<RecordTarget>(Arrays.asList(recordTarget)));

		return clinicalDocument;
	}

	@Override
	public PatientModel put(ClinicalDocument clinicalDocument) {
		// TODO Auto-generated method stub
		return null;
	}
}
