package org.oscarehr.e2e.lens.header;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.AD;
import org.marc.everest.datatypes.ADXP;
import org.marc.everest.datatypes.AddressPartType;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.PostalAddressUse;
import org.marc.everest.datatypes.TEL;
import org.marc.everest.datatypes.TelecommunicationsAddressUse;
import org.marc.everest.datatypes.generic.CS;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Patient;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.PatientRole;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.RecordTarget;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;
import org.oscarehr.common.dao.DemographicDao;
import org.oscarehr.common.model.Demographic;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.constant.Constants.TelecomType;
import org.oscarehr.e2e.lens.common.TelecomPartLens;
import org.oscarehr.e2e.lens.header.recordtarget.AddressLens;
import org.oscarehr.e2e.lens.header.recordtarget.HinIdLens;
import org.oscarehr.e2e.lens.header.recordtarget.RecordTargetLens;
import org.oscarehr.e2e.lens.header.recordtarget.TelecomLens;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RecordTargetLensesTest {
	private static ApplicationContext context;
	private static DemographicDao dao;
	private static Demographic demographic;

	private Pair<Demographic, RecordTarget> blankPair;
	private Pair<Demographic, RecordTarget> getPair;
	private Pair<Demographic, RecordTarget> putPair;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);

		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		dao = context.getBean(DemographicDao.class);
		demographic = dao.find(Constants.Runtime.VALID_DEMOGRAPHIC);
	}

	@Before
	public void before() {
		PatientRole patientRole = new PatientRole();
		patientRole.setPatient(new Patient());
		RecordTarget recordTarget = new RecordTarget(ContextControl.OverridingPropagating, patientRole);

		blankPair = Pair.of(new Demographic(), new RecordTarget());
		getPair = Pair.of(demographic, recordTarget);
		putPair = Pair.of(new Demographic(), recordTarget);
	}

	@Test
	public void recordTargetLensGetTest() {
		RecordTargetLens lens = new RecordTargetLens();
		assertNotNull(lens);

		Pair<Demographic, RecordTarget> pair = lens.get(blankPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
		assertEquals(ContextControl.OverridingPropagating, pair.getRight().getContextControlCode().getCode());
		assertNotNull(pair.getRight().getPatientRole());
		assertNotNull(pair.getRight().getPatientRole().getPatient());
	}

	@Test
	public void recordTargetLensPutTest() {
		RecordTargetLens lens = new RecordTargetLens();
		assertNotNull(lens);

		Pair<Demographic, RecordTarget> pair = lens.put(blankPair, blankPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
	}

	@Test
	public void hinIdLensGetTest() {
		HinIdLens lens = new HinIdLens();
		assertNotNull(lens);

		Pair<Demographic, RecordTarget> pair = lens.get(getPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());

		SET<II> id = pair.getRight().getPatientRole().getId();
		assertNotNull(id);
		assertEquals(Constants.DocumentHeader.BC_PHN_OID, id.get(0).getRoot());
		assertEquals(Constants.DocumentHeader.BC_PHN_OID_ASSIGNING_AUTHORITY_NAME, id.get(0).getAssigningAuthorityName());
		assertEquals(demographic.getHin(), id.get(0).getExtension());
	}

	@Test
	public void hinIdLensPutTest() {
		HinIdLens lens = new HinIdLens();
		assertNotNull(lens);

		SET<II> id = new SET<>(new II(Constants.DocumentHeader.BC_PHN_OID, demographic.getHin()));
		putPair.getRight().getPatientRole().setId(id);

		Pair<Demographic, RecordTarget> pair = lens.put(putPair, putPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
		assertEquals(demographic.getHin(), pair.getLeft().getHin());
	}

	@Test
	public void addressLensGetTest() {
		AddressLens lens = new AddressLens();
		assertNotNull(lens);

		Pair<Demographic, RecordTarget> pair = lens.get(getPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());

		SET<AD> addresses = pair.getRight().getPatientRole().getAddr();
		assertNotNull(addresses);

		List<ADXP> addrParts = addresses.get(0).getPart();
		assertNotNull(addrParts);
		assertEquals(4, addrParts.size());
		assertTrue(addrParts.contains(new ADXP(demographic.getAddress(), AddressPartType.Delimiter)));
		assertTrue(addrParts.contains(new ADXP(demographic.getCity(), AddressPartType.City)));
		assertTrue(addrParts.contains(new ADXP(demographic.getProvince(), AddressPartType.State)));
		assertTrue(addrParts.contains(new ADXP(demographic.getPostal(), AddressPartType.PostalCode)));
	}

	@Test
	public void addressLensPutTest() {
		AddressLens lens = new AddressLens();
		assertNotNull(lens);

		List<ADXP> addrParts = new ArrayList<ADXP>();
		addrParts.add(new ADXP(demographic.getAddress(), AddressPartType.Delimiter));
		addrParts.add(new ADXP(demographic.getCity(), AddressPartType.City));
		addrParts.add(new ADXP(demographic.getProvince(), AddressPartType.State));
		addrParts.add(new ADXP(demographic.getPostal(), AddressPartType.PostalCode));

		SET<AD> addresses = new SET<>(new AD(new CS<>(PostalAddressUse.HomeAddress), addrParts));
		putPair.getRight().getPatientRole().setAddr(addresses);;

		Pair<Demographic, RecordTarget> pair = lens.put(putPair, putPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
		assertEquals(demographic.getAddress(), pair.getLeft().getAddress());
		assertEquals(demographic.getCity(), pair.getLeft().getCity());
		assertEquals(demographic.getProvince(), pair.getLeft().getProvince());
		assertEquals(demographic.getPostal(), pair.getLeft().getPostal());
	}

	@Test
	public void telecomGetTest() {
		TelecomLens lens = new TelecomLens();
		assertNotNull(lens);

		Pair<Demographic, RecordTarget> pair = lens.get(getPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());

		SET<TEL> telecoms = pair.getRight().getPatientRole().getTelecom();
		assertNotNull(telecoms);
		assertEquals(3, telecoms.size());
		assertTrue(telecoms.contains(new TEL(Constants.DocumentHeader.TEL_PREFIX + demographic.getPhone().replaceAll("[^0-9]", ""), TelecommunicationsAddressUse.Home)));
		assertTrue(telecoms.contains(new TEL(Constants.DocumentHeader.TEL_PREFIX + demographic.getPhone2().replaceAll("[^0-9]", ""), TelecommunicationsAddressUse.WorkPlace)));
		assertTrue(telecoms.contains(new TEL(Constants.DocumentHeader.EMAIL_PREFIX + demographic.getEmail(), TelecommunicationsAddressUse.Home)));
	}

	@Test
	public void telecomPutTest() {
		TelecomLens lens = new TelecomLens();
		assertNotNull(lens);

		ArrayList<TEL> tels = new ArrayList<>();
		tels.add(new TelecomPartLens(TelecomType.TELEPHONE, TelecommunicationsAddressUse.Home).get(demographic.getPhone()));
		tels.add(new TelecomPartLens(TelecomType.TELEPHONE, TelecommunicationsAddressUse.WorkPlace).get(demographic.getPhone2()));
		tels.add(new TelecomPartLens(TelecomType.EMAIL, TelecommunicationsAddressUse.Home).get(demographic.getEmail()));

		SET<TEL> telecoms = new SET<>(tels);
		putPair.getRight().getPatientRole().setTelecom(telecoms);

		Pair<Demographic, RecordTarget> pair = lens.put(putPair, putPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
		assertEquals(demographic.getPhone().replaceAll("[^0-9]", ""), pair.getLeft().getPhone());
		assertEquals(demographic.getPhone2().replaceAll("[^0-9]", ""), pair.getLeft().getPhone2());
		assertEquals(demographic.getEmail(), pair.getLeft().getEmail());
	}
}
