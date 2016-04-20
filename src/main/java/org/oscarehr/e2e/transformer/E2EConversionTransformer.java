package org.oscarehr.e2e.transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Custodian;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.InformationRecipient;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.oscarehr.common.model.Clinic;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.e2e.model.PatientModel;
import org.oscarehr.e2e.rule.E2EConversionRule;
import org.oscarehr.e2e.rule.common.IRule;
import org.oscarehr.e2e.rule.common.IRule.Original;
import org.oscarehr.e2e.rule.header.AuthorRule;
import org.oscarehr.e2e.rule.header.CustodianRule;
import org.oscarehr.e2e.rule.header.InformationRecipientRule;
import org.oscarehr.e2e.rule.header.RecordTargetRule;

public class E2EConversionTransformer extends AbstractTransformer<PatientModel, ClinicalDocument> {
	private Map<String, Pair<?, ?>> results;

	public E2EConversionTransformer(PatientModel model, ClinicalDocument target, Original original) {
		super(model, target, original);
		if(this.model == null) {
			this.model = new PatientModel();
		}
		if(this.target == null) {
			this.target = new ClinicalDocument();
		}

		transform();
	}

	// TODO Consider Reflection to parse model and target
	@Override
	protected void transform() {
		map();
		reduce();
	}

	private void map() {
		List<IRule<?, ?>> rules = new ArrayList<>();
		rules.add(new E2EConversionRule(model, target));

		Demographic demographic = model.getDemographic();
		RecordTarget recordTarget = null;
		if(target.getRecordTarget() != null && !target.getRecordTarget().isEmpty()) {
			recordTarget = target.getRecordTarget().get(0);
		}
		rules.add(new RecordTargetRule(demographic, recordTarget));

		String providerNo = null;
		if(demographic != null) {
			providerNo = model.getDemographic().getProviderNo();
		}
		rules.add(new AuthorRule(providerNo, target.getAuthor()));
		rules.add(new CustodianRule(model.getClinic(), target.getCustodian()));
		rules.add(new InformationRecipientRule(null, target.getInformationRecipient()));

		// Run all rules
		results = rules.stream()
				.map(IRule::execute)
				.collect(Collectors.toMap(IRule::getName, IRule::getPair));
	}

	@SuppressWarnings("unchecked")
	private void reduce() {
		if(original == Original.SOURCE) {
			target = (ClinicalDocument) results.get(E2EConversionRule.class.getSimpleName()).getRight();
			target.setRecordTarget(new ArrayList<>(Arrays.asList((RecordTarget) results.get(RecordTargetRule.class.getSimpleName()).getRight())));
			target.setAuthor((ArrayList<Author>) results.get(AuthorRule.class.getSimpleName()).getRight());
			target.setCustodian((Custodian) results.get(CustodianRule.class.getSimpleName()).getRight());
			target.setInformationRecipient((ArrayList<InformationRecipient>) results.get(InformationRecipientRule.class.getSimpleName()).getRight());
		} else {
			model = (PatientModel) results.get(E2EConversionRule.class.getSimpleName()).getLeft();
			model.setDemographic((Demographic) results.get(RecordTargetRule.class.getSimpleName()).getLeft());
			model.getDemographic().setProviderNo((String) results.get(AuthorRule.class.getSimpleName()).getLeft());
			model.setClinic((Clinic) results.get(CustodianRule.class.getSimpleName()).getLeft());
		}
	}
}
