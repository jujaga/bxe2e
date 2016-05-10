package org.oscarehr.e2e.model.export.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.ED;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.IVL;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActStatus;
import org.oscarehr.common.dao.DxresearchDao;
import org.oscarehr.common.model.Dxresearch;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.lens.common.TSDateLens;
import org.oscarehr.e2e.model.export.body.ProblemsModel;
import org.oscarehr.e2e.util.EverestUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProblemsModelTest {
	private static ApplicationContext context;
	private static DxresearchDao dao;
	private static Dxresearch problem;
	private static ProblemsModel problemsModel;

	private static Dxresearch nullProblem;
	private static ProblemsModel nullProblemsModel;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		dao = context.getBean(DxresearchDao.class);
		problem = dao.getDxResearchItemsByPatient(Constants.Runtime.VALID_DEMOGRAPHIC).get(0);
		problemsModel = new ProblemsModel(problem);

		nullProblem = new Dxresearch();
		nullProblemsModel = new ProblemsModel(nullProblem);
	}

	@Test
	public void problemsModelNullTest() {
		assertNotNull(new ProblemsModel(null));
	}

	@Test
	public void textSummaryTest() {
		String text = problemsModel.getTextSummary();
		assertNotNull(text);
	}

	@Test
	public void textSummaryNullTest() {
		String text = nullProblemsModel.getTextSummary();
		assertNotNull(text);
	}

	@Test
	public void idTest() {
		SET<II> ids = problemsModel.getIds();
		assertNotNull(ids);

		II id = ids.get(0);
		assertNotNull(id);
		assertEquals(Constants.EMR.EMR_OID, id.getRoot());
		assertEquals(Constants.EMR.EMR_VERSION, id.getAssigningAuthorityName());
		assertFalse(EverestUtils.isNullorEmptyorWhitespace(id.getExtension()));
		assertTrue(id.getExtension().contains(Constants.IdPrefixes.ProblemList.toString()));
		assertTrue(id.getExtension().contains(problem.getDxresearchNo().toString()));
	}

	@Test
	public void idNullTest() {
		SET<II> ids = nullProblemsModel.getIds();
		assertNotNull(ids);
	}

	@Test
	public void codeTest() {
		CD<String> code = problemsModel.getCode();
		assertNotNull(code);
		assertTrue(code.isNull());
		assertEquals(NullFlavor.NoInformation, code.getNullFlavor().getCode());
	}

	@Test
	public void codeNullTest() {
		CD<String> code = nullProblemsModel.getCode();
		assertNotNull(code);
		assertTrue(code.isNull());
		assertEquals(NullFlavor.NoInformation, code.getNullFlavor().getCode());
	}

	@Test
	public void textTest() {
		ED text = problemsModel.getText();
		assertNotNull(text);
	}

	@Test
	public void textNullTest() {
		ED text = nullProblemsModel.getText();
		assertNull(text);
	}

	@Test
	public void statusCodeActiveTest() {
		ActStatus status = problemsModel.getStatusCode();
		assertNotNull(status);
		assertEquals(ActStatus.Active, status);
	}

	@Test
	public void statusCodeCompleteTest() {
		Dxresearch problem2 = dao.getDxResearchItemsByPatient(Constants.Runtime.VALID_DEMOGRAPHIC).get(0);
		problem2.setStatus('C');
		ProblemsModel problemsModel2 = new ProblemsModel(problem2);

		ActStatus status = problemsModel2.getStatusCode();
		assertNotNull(status);
		assertEquals(ActStatus.Completed, status);
	}

	@Test
	public void statusCodeNullTest() {
		ActStatus status = nullProblemsModel.getStatusCode();
		assertNotNull(status);
		assertEquals(ActStatus.Completed, status);
	}

	@Test
	public void effectiveTimeTest() {
		IVL<TS> ivl = problemsModel.getEffectiveTime();
		assertNotNull(ivl);
		assertEquals(new TSDateLens().get(problem.getStartDate()), ivl.getLow());
	}

	@Test
	public void effectiveTimeNullTest() {
		IVL<TS> ivl = nullProblemsModel.getEffectiveTime();
		assertNull(ivl);
	}

	@Test
	public void valueTest() {
		CD<String> value = problemsModel.getValue();
		assertNotNull(value);
		assertTrue(value.isNull());
		assertEquals(NullFlavor.Unknown, value.getNullFlavor().getCode());
	}

	@Test
	public void valueNullTest() {
		CD<String> value = nullProblemsModel.getValue();
		assertNotNull(value);
		assertTrue(value.isNull());
		assertEquals(NullFlavor.Unknown, value.getNullFlavor().getCode());
	}

	@Test
	public void authorTest() {
		ArrayList<Author> authors = problemsModel.getAuthor();
		assertNotNull(authors);
		assertEquals(1, authors.size());
	}

	@Test
	public void authorNullTest() {
		ArrayList<Author> authors = nullProblemsModel.getAuthor();
		assertNotNull(authors);
		assertEquals(1, authors.size());
	}

	@Test
	public void lastReviewDateTest() {
		EntryRelationship entryRelationship = problemsModel.getDiagnosisDate();
		assertNotNull(entryRelationship);
	}

	@Test
	public void lastReviewDateNullTest() {
		EntryRelationship entryRelationship = nullProblemsModel.getDiagnosisDate();
		assertNull(entryRelationship);
	}
}
