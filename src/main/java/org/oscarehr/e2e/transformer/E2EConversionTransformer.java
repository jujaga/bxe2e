package org.oscarehr.e2e.transformer;

import java.util.ArrayList;
import java.util.Arrays;

import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Custodian;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.InformationRecipient;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.oscarehr.e2e.model.PatientModel;
import org.oscarehr.e2e.rule.E2EConversionRule;
import org.oscarehr.e2e.rule.header.AuthorRule;
import org.oscarehr.e2e.rule.header.CustodianRule;
import org.oscarehr.e2e.rule.header.InformationRecipientRule;
import org.oscarehr.e2e.rule.header.RecordTargetRule;

public class E2EConversionTransformer extends AbstractTransformer<PatientModel, ClinicalDocument> {
	public E2EConversionTransformer(PatientModel model, ClinicalDocument target) {
		super(model, target);

		transform();
	}

	// TODO Design cleaner transformation solution
	@Override
	protected void transform() {
		// Instantiate and map all rules
		E2EConversionRule e2eConversionRule = new E2EConversionRule(model, target);

		String providerNo = null;
		try {
			providerNo = model.getDemographic().getProviderNo();
		} catch (NullPointerException e) {
			providerNo = null;
		}
		ArrayList<Author> authors = null;
		try {
			authors = target.getAuthor();
		} catch (Exception e) {
			authors = null;
		}
		AuthorRule authorRule = new AuthorRule(providerNo, authors);

		Custodian custodian = null;
		try {
			custodian = target.getCustodian();
		} catch (Exception e) {
			custodian = null;
		}
		CustodianRule custodianRule = new CustodianRule(model.getClinic(), custodian);

		ArrayList<InformationRecipient> informationRecipient = null;
		try {
			informationRecipient = target.getInformationRecipient();
		} catch (Exception e) {
			informationRecipient = null;
		}
		InformationRecipientRule informationRecipientRule = new InformationRecipientRule(null, informationRecipient);

		RecordTarget recordTarget = null;
		try {
			recordTarget = target.getRecordTarget().get(0);
		} catch (Exception e) {
			recordTarget = null;
		}
		RecordTargetRule recordTargetRule = new RecordTargetRule(model.getDemographic(), recordTarget);

		// Run all rules

		// Grab contents from rules and reduce
		target = e2eConversionRule.getTarget();
		target.setRecordTarget(new ArrayList<>(Arrays.asList(recordTargetRule.getTarget())));
		target.setAuthor(authorRule.getTarget());
		target.setCustodian(custodianRule.getTarget());
		target.setInformationRecipient(informationRecipientRule.getTarget());

		model = (PatientModel) e2eConversionRule.getSource();
		model.setDemographic(recordTargetRule.getSource());
		model.getDemographic().setProviderNo(authorRule.getSource());
		model.setClinic(custodianRule.getSource());

		// Consider separate aggregation functions?
	}
}
