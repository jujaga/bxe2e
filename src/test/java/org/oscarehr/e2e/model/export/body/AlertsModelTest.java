package org.oscarehr.e2e.model.export.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.ED;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.TS;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.CE;
import org.marc.everest.datatypes.generic.IVL;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActStatus;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_BasicConfidentialityKind;
import org.oscarehr.casemgmt.dao.CaseManagementNoteDao;
import org.oscarehr.casemgmt.model.CaseManagementNote;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.export.body.AlertsModel;
import org.oscarehr.e2e.util.EverestUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AlertsModelTest {
	private static ApplicationContext context;
	private static CaseManagementNoteDao dao;
	private static CaseManagementNote alert;
	private static AlertsModel alertsModel;

	private static CaseManagementNote nullAlert;
	private static AlertsModel nullAlertsModel;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		dao = context.getBean(CaseManagementNoteDao.class);
		alert = dao.getNotesByDemographic(Constants.Runtime.VALID_DEMOGRAPHIC.toString()).get(5);
		alertsModel = new AlertsModel(alert);

		nullAlert = new CaseManagementNote();
		nullAlertsModel = new AlertsModel(nullAlert);
	}

	@Test
	public void alertsModelNullTest() {
		assertNotNull(new AlertsModel(null));
	}

	@Test
	public void textSummaryTest() {
		String text = alertsModel.getTextSummary();
		assertNotNull(text);
	}

	@Test
	public void textSummaryNullTest() {
		String text = nullAlertsModel.getTextSummary();
		assertNotNull(text);
	}

	@Test
	public void idTest() {
		SET<II> ids = alertsModel.getIds();
		assertNotNull(ids);

		II id = ids.get(0);
		assertNotNull(id);
		assertEquals(Constants.EMR.EMR_OID, id.getRoot());
		assertEquals(Constants.EMR.EMR_VERSION, id.getAssigningAuthorityName());
		assertFalse(EverestUtils.isNullorEmptyorWhitespace(id.getExtension()));
		assertTrue(id.getExtension().contains(Constants.IdPrefixes.Alerts.toString()));
		assertTrue(id.getExtension().contains(alert.getId().toString()));
	}

	@Test
	public void idNullTest() {
		SET<II> ids = nullAlertsModel.getIds();
		assertNotNull(ids);
	}

	@Test
	public void codeTest() {
		CD<String> code = alertsModel.getCode();
		assertNotNull(code);
		assertTrue(code.isNull());
		assertEquals(NullFlavor.NoInformation, code.getNullFlavor().getCode());
	}

	@Test
	public void codeNullTest() {
		CD<String> code = nullAlertsModel.getCode();
		assertNotNull(code);
		assertTrue(code.isNull());
		assertEquals(NullFlavor.NoInformation, code.getNullFlavor().getCode());
	}

	@Test
	public void textTest() {
		ED text = alertsModel.getText();
		assertNotNull(text);
		assertEquals(alert.getNote(), new String(text.getData()));
	}

	@Test
	public void textNullTest() {
		ED text = nullAlertsModel.getText();
		assertNotNull(text);
		assertTrue(text.isNull());
		assertEquals(NullFlavor.NoInformation, text.getNullFlavor().getCode());
	}

	@Test
	public void statusCodeTest() {
		ActStatus status = alertsModel.getStatusCode();
		assertNotNull(status);
		assertEquals(ActStatus.Active, status);
	}

	@Test
	public void statusCodeNullTest() {
		ActStatus status = nullAlertsModel.getStatusCode();
		assertNotNull(status);
		assertEquals(ActStatus.Completed, status);
	}

	@Test
	public void effectiveTimeTest() {
		IVL<TS> ivl = alertsModel.getEffectiveTime();
		assertNotNull(ivl);
		assertEquals(EverestUtils.buildTSFromDate(alert.getObservation_date()), ivl.getLow());
	}

	@Test
	public void effectiveTimeNullTest() {
		IVL<TS> ivl = nullAlertsModel.getEffectiveTime();
		assertNotNull(ivl);
		assertTrue(ivl.isNull());
		assertEquals(NullFlavor.NoInformation, ivl.getNullFlavor().getCode());
	}

	@Test
	public void confidentialityTest() {
		CE<x_BasicConfidentialityKind> confidentiality = alertsModel.getConfidentiality();
		assertNotNull(confidentiality);
		assertEquals(x_BasicConfidentialityKind.Normal, confidentiality.getCode());
	}

	@Test
	public void confidentialityNullTest() {
		CE<x_BasicConfidentialityKind> confidentiality = nullAlertsModel.getConfidentiality();
		assertNotNull(confidentiality);
		assertEquals(x_BasicConfidentialityKind.Normal, confidentiality.getCode());
	}
}
