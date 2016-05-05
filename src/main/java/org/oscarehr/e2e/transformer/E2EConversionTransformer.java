package org.oscarehr.e2e.transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.generic.LIST;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.ClinicalDocument;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Component3;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Custodian;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.InformationRecipient;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.oscarehr.common.model.Clinic;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.common.model.Dxresearch;
import org.oscarehr.e2e.constant.BodyConstants.AbstractBodyConstants;
import org.oscarehr.e2e.constant.BodyConstants.Problems;
import org.oscarehr.e2e.model.PatientModel;
import org.oscarehr.e2e.rule.E2EConversionRule;
import org.oscarehr.e2e.rule.body.ProblemsRule;
import org.oscarehr.e2e.rule.common.IRule;
import org.oscarehr.e2e.rule.common.IRule.Original;
import org.oscarehr.e2e.rule.header.AuthorRule;
import org.oscarehr.e2e.rule.header.CustodianRule;
import org.oscarehr.e2e.rule.header.InformationRecipientRule;
import org.oscarehr.e2e.rule.header.RecordTargetRule;

public class E2EConversionTransformer extends AbstractTransformer<PatientModel, ClinicalDocument> {
	private Map<String, Pair<?, ?>> results;
	private String patientUUID;

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

	public String getPatientUUID() {
		return patientUUID;
	}

	@Override
	protected void transform() {
		// Map
		results = map();

		// Reduce
		if(original == Original.SOURCE) {
			reduceTarget();
		} else {
			reduceModel();
		}

		// Save Patient UUID session
		try {
			patientUUID = target.getId().getRoot();
		} catch (NullPointerException e) {
			patientUUID = null;
		}
	}

	private Map<String, Pair<?, ?>> map() {
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

		// TODO Factor in Target problems on map
		List<Dxresearch> problems = model.getProblems();
		if(problems != null && !problems.isEmpty()) {
			for(Integer i = 0; i < problems.size(); i++) {
				Dxresearch problem = problems.get(i);
				rules.add(new ProblemsRule(problem, null, i));
			}
		}

		// Run all rules
		return rules.parallelStream()
				.map(IRule::execute)
				.sequential()
				.collect(Collectors.toConcurrentMap(IRule::getName, IRule::getPair));
	}

	@SuppressWarnings("unchecked")
	private void reduceTarget() {
		Map<String, ?> right = results.entrySet().stream()
				.collect(Collectors.toConcurrentMap(Map.Entry::getKey, e -> e.getValue().getRight()));

		target = (ClinicalDocument) right.get(E2EConversionRule.class.getSimpleName());
		target.setRecordTarget(new ArrayList<>(Arrays.asList((RecordTarget) right.get(RecordTargetRule.class.getSimpleName()))));
		target.setAuthor((ArrayList<Author>) right.get(AuthorRule.class.getSimpleName()));
		target.setCustodian((Custodian) right.get(CustodianRule.class.getSimpleName()));
		target.setInformationRecipient((ArrayList<InformationRecipient>) right.get(InformationRecipientRule.class.getSimpleName()));

		ArrayList<Component3> components = target.getComponent().getBodyChoiceIfStructuredBody().getComponent();

		Component3 problemsSection = components.stream()
				.filter(e -> filledEntryFilter.test(componentToII.apply(e), Problems.getConstants()))
				.findFirst()
				.orElse(null);

		if(problemsSection != null) {
			ArrayList<Entry> entries = problemsSection.getSection().getEntry();

			for(Map.Entry<String, ?> e : right.entrySet()) {
				if(e.getKey().startsWith(ProblemsRule.class.getSimpleName())) {
					entries.add((Entry) e.getValue());
				}
			}

			problemsSection.getSection().setEntry(entries);
			components.set(components.indexOf(problemsSection), problemsSection);
		}

		target.getComponent().getBodyChoiceIfStructuredBody().setComponent(components);
	}

	private void reduceModel() {
		Map<String, ?> left = results.entrySet().stream()
				.collect(Collectors.toConcurrentMap(Map.Entry::getKey, e -> e.getValue().getLeft()));

		model = (PatientModel) left.get(E2EConversionRule.class.getSimpleName());
		model.setDemographic((Demographic) left.get(RecordTargetRule.class.getSimpleName()));
		model.getDemographic().setProviderNo((String) left.get(AuthorRule.class.getSimpleName()));
		model.setClinic((Clinic) left.get(CustodianRule.class.getSimpleName()));
	}

	// TODO Consolidate to Constants
	private Function<Component3, LIST<II>> componentToII = e -> {
		if(e.getSection() != null) {
			return e.getSection().getTemplateId();
		}
		return null;
	};

	// TODO Consolidate to Constants
	private BiPredicate<LIST<II>, AbstractBodyConstants> filledEntryFilter = (e, bc) -> {
		if(e != null && !e.isNull() && !e.isEmpty()) {
			return e.stream().anyMatch(ii -> {
				return ii.getRoot().equals(bc.WITH_ENTRIES_TEMPLATE_ID);
			});
		}
		return false;
	};
}
