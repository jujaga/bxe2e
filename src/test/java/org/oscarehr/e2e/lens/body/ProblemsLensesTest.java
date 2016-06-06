package org.oscarehr.e2e.lens.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.ANY;
import org.marc.everest.datatypes.BL;
import org.marc.everest.datatypes.ED;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.CS;
import org.marc.everest.datatypes.generic.IVL;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.AssignedAuthor;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Entry;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Observation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActStatus;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActMoodDocumentObservation;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntry;
import org.oscarehr.common.dao.DxresearchDao;
import org.oscarehr.common.model.Dxresearch;
import org.oscarehr.e2e.constant.BodyConstants.AbstractBodyConstants;
import org.oscarehr.e2e.constant.BodyConstants.Problems;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.lens.body.problems.ProblemsAuthorLens;
import org.oscarehr.e2e.lens.body.problems.ProblemsCodeLens;
import org.oscarehr.e2e.lens.body.problems.ProblemsEffectiveTimeLens;
import org.oscarehr.e2e.lens.body.problems.ProblemsIdLens;
import org.oscarehr.e2e.lens.body.problems.ProblemsLens;
import org.oscarehr.e2e.lens.body.problems.ProblemsStatusCodeLens;
import org.oscarehr.e2e.lens.body.problems.ProblemsTextLens;
import org.oscarehr.e2e.lens.body.problems.ProblemsValueLens;
import org.oscarehr.e2e.util.EverestUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProblemsLensesTest {
	private static ApplicationContext context;
	private static AbstractBodyConstants bodyConstants;
	private static DxresearchDao dao;
	private static Dxresearch problem;

	private Pair<Dxresearch, Entry> blankPair;
	private Pair<Dxresearch, Entry> getPair;
	private Pair<Dxresearch, Entry> putPair;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);

		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		bodyConstants = Problems.getConstants();
		dao = context.getBean(DxresearchDao.class);
		problem = dao.getDxResearchItemsByPatient(Constants.Runtime.VALID_DEMOGRAPHIC).get(0);
	}

	@Before
	public void before() {
		Observation observation = new Observation(x_ActMoodDocumentObservation.Eventoccurrence);
		observation.setEntryRelationship(new ArrayList<>());

		Entry entry = new Entry(x_ActRelationshipEntry.DRIV, new BL(true), observation);
		entry.setTemplateId(Arrays.asList(new II(bodyConstants.ENTRY_TEMPLATE_ID)));

		blankPair = Pair.of(new Dxresearch(), new Entry());
		getPair = Pair.of(problem, entry);
		putPair = Pair.of(new Dxresearch(), entry);
	}

	@Test
	public void problemsLensGetTest() {
		ProblemsLens lens = new ProblemsLens();
		assertNotNull(lens);

		Pair<Dxresearch, Entry> pair = lens.get(blankPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());

		assertEquals(x_ActRelationshipEntry.DRIV, pair.getRight().getTypeCode().getCode());
		assertTrue(pair.getRight().getContextConductionInd().getValue());
		assertTrue(pair.getRight().getTemplateId().contains(new II(bodyConstants.ENTRY_TEMPLATE_ID)));

		Observation observation = pair.getRight().getClinicalStatementIfObservation();
		assertNotNull(observation);
		assertEquals(x_ActMoodDocumentObservation.Eventoccurrence, observation.getMoodCode().getCode());
		assertNotNull(observation.getEntryRelationship());
	}

	@Test
	public void problemsLensPutTest() {
		ProblemsLens lens = new ProblemsLens();
		assertNotNull(lens);

		Pair<Dxresearch, Entry> pair = lens.put(blankPair, blankPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
	}

	@Test
	public void problemsIdLensGetTest() {
		ProblemsIdLens lens = new ProblemsIdLens();
		assertNotNull(lens);

		Pair<Dxresearch, Entry> pair = lens.get(getPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());

		SET<II> id = pair.getRight().getClinicalStatementIfObservation().getId();
		assertNotNull(id);
		assertEquals(Constants.EMR.EMR_OID, id.get(0).getRoot());
		assertTrue(id.get(0).getExtension().startsWith(Constants.IdPrefixes.ProblemList.toString()));
		assertTrue(id.get(0).getExtension().endsWith(problem.getDxresearchNo().toString()));
	}

	@Test
	public void problemsIdLensPutTest() {
		ProblemsIdLens lens = new ProblemsIdLens();
		assertNotNull(lens);

		SET<II> id = new SET<>(new II(Constants.EMR.EMR_OID, Constants.IdPrefixes.ProblemList.toString() + "-" + problem.getDxresearchNo().toString()));
		putPair.getRight().getClinicalStatementIfObservation().setId(id);

		Pair<Dxresearch, Entry> pair = lens.put(putPair, putPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
		assertEquals(problem.getDxresearchNo(), pair.getLeft().getDxresearchNo());
	}

	@Test
	public void problemsCodeLensGetTest() {
		ProblemsCodeLens lens = new ProblemsCodeLens();
		assertNotNull(lens);

		Pair<Dxresearch, Entry> pair = lens.get(getPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());

		CD<String> code = pair.getRight().getClinicalStatementIfObservation().getCode();
		assertNotNull(code);
		assertTrue(code.isNull());
		assertEquals(NullFlavor.NoInformation, code.getNullFlavor().getCode());
	}

	@Test
	public void problemsCodeLensPutTest() {
		ProblemsCodeLens lens = new ProblemsCodeLens();
		assertNotNull(lens);

		Pair<Dxresearch, Entry> pair = lens.put(putPair, putPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
	}

	@Test
	public void problemsTextLensGetTest() {
		ProblemsTextLens lens = new ProblemsTextLens();
		assertNotNull(lens);

		Pair<Dxresearch, Entry> pair = lens.get(getPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());

		ED text = pair.getRight().getClinicalStatementIfObservation().getText();
		assertNotNull(text);
		assertEquals(EverestUtils.getICD9Description(problem.getDxresearchCode()), new String(text.getData()));
		assertEquals(Constants.DocumentHeader.LANGUAGE_ENGLISH_CANADIAN, text.getLanguage());
	}

	@Test
	public void problemsTextLensPutTest() {
		ProblemsTextLens lens = new ProblemsTextLens();
		assertNotNull(lens);

		Pair<Dxresearch, Entry> pair = lens.put(putPair, putPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
	}

	@Test
	public void problemsStatusCodeLensGetTest() {
		ProblemsStatusCodeLens lens = new ProblemsStatusCodeLens();
		assertNotNull(lens);

		Pair<Dxresearch, Entry> pair = lens.get(getPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());

		CS<ActStatus> statusCode = pair.getRight().getClinicalStatementIfObservation().getStatusCode();
		assertNotNull(statusCode);
		assertEquals(ActStatus.Active, statusCode.getCode());

		statusCode = null;
		getPair = Pair.of(new Dxresearch(), getPair.getRight());
		getPair.getLeft().setStatus('C');
		getPair.getRight().getClinicalStatementIfObservation().setStatusCode(statusCode);

		pair = lens.get(getPair);
		statusCode = pair.getRight().getClinicalStatementIfObservation().getStatusCode();
		assertEquals(ActStatus.Completed, statusCode.getCode());
	}

	@Test
	public void problemsStatusCodeLensPutTest() {
		ProblemsStatusCodeLens lens = new ProblemsStatusCodeLens();
		assertNotNull(lens);

		Pair<Dxresearch, Entry> pair = lens.put(putPair, putPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
		assertTrue(pair.getLeft().getStatus().equals('D'));

		putPair = Pair.of(new Dxresearch(), putPair.getRight());
		putPair.getRight().getClinicalStatementIfObservation().setStatusCode(ActStatus.Active);

		pair = lens.put(putPair, putPair);
		assertTrue(pair.getLeft().getStatus().equals('A'));

		putPair = Pair.of(new Dxresearch(), putPair.getRight());
		putPair.getRight().getClinicalStatementIfObservation().setStatusCode(ActStatus.Completed);

		pair = lens.put(putPair, putPair);
		assertTrue(pair.getLeft().getStatus().equals('C'));
	}

	@Test
	public void problemsEffectiveTimeLensGetTest() {
		ProblemsEffectiveTimeLens lens = new ProblemsEffectiveTimeLens();
		assertNotNull(lens);

		Pair<Dxresearch, Entry> pair = lens.get(getPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());

		IVL<TS> effectiveTime = pair.getRight().getClinicalStatementIfObservation().getEffectiveTime();
		assertNotNull(effectiveTime);
		assertFalse(effectiveTime.isNull());
		assertFalse(effectiveTime.getLow().isNull());
		assertFalse(effectiveTime.getLow().isInvalidDate());
	}

	@Test
	public void problemsEffectiveTimeLensPutTest() {
		ProblemsEffectiveTimeLens lens = new ProblemsEffectiveTimeLens();
		assertNotNull(lens);

		putPair.getRight().getClinicalStatementIfObservation().setEffectiveTime(new TS(new GregorianCalendar(), TS.DAY), null);

		Pair<Dxresearch, Entry> pair = lens.put(putPair, putPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
		assertNotNull(pair.getLeft().getStartDate());
	}

	@Test
	public void problemsValueLensGetTest() {
		ProblemsValueLens lens = new ProblemsValueLens();
		assertNotNull(lens);

		Pair<Dxresearch, Entry> pair = lens.get(getPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());

		ANY value = pair.getRight().getClinicalStatementIfObservation().getValue();
		assertNotNull(value);
		assertTrue(value.isNull());
		assertEquals(NullFlavor.NoInformation, value.getNullFlavor().getCode());
	}

	@Test
	public void problemsValueLensPutTest() {
		ProblemsValueLens lens = new ProblemsValueLens();
		assertNotNull(lens);

		Pair<Dxresearch, Entry> pair = lens.put(putPair, putPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
	}

	@Test
	public void problemsAuthorLensGetTest() {
		ProblemsAuthorLens lens = new ProblemsAuthorLens();
		assertNotNull(lens);

		Pair<Dxresearch, Entry> pair = lens.get(getPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());

		ArrayList<Author> authors = pair.getRight().getClinicalStatementIfObservation().getAuthor();
		assertNotNull(authors);
		assertEquals(1, authors.size());
		assertNotNull(authors.get(0).getAssignedAuthor());
	}

	@Test
	public void problemsAuthorLensPutTest() {
		ProblemsAuthorLens lens = new ProblemsAuthorLens();
		assertNotNull(lens);

		Author author = new Author(ContextControl.OverridingPropagating, null, new AssignedAuthor());
		author.setTime(new TS(new GregorianCalendar(), TS.DAY));
		putPair.getRight().getClinicalStatementIfObservation().setAuthor(new ArrayList<>(Arrays.asList(author)));

		Pair<Dxresearch, Entry> pair = lens.put(putPair, putPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
		assertNotNull(pair.getLeft().getUpdateDate());
	}
}
