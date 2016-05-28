package org.oscarehr.e2e.lens.header;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.ENXP;
import org.marc.everest.datatypes.EntityNamePartType;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.TEL;
import org.marc.everest.datatypes.TelecommunicationsAddressUse;
import org.marc.everest.datatypes.generic.SET;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.AssignedAuthor;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.AuthoringDevice;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Person;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;
import org.oscarehr.common.dao.ProviderDao;
import org.oscarehr.common.model.Provider;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.lens.header.author.AuthorLens;
import org.oscarehr.e2e.lens.header.author.ProviderIdLens;
import org.oscarehr.e2e.lens.header.author.ProviderLens;
import org.oscarehr.e2e.lens.header.author.ProviderPersonLens;
import org.oscarehr.e2e.lens.header.author.ProviderTelecomLens;
import org.oscarehr.e2e.lens.header.author.SystemLens;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AuthorLensesTest {
	private static ApplicationContext context;
	private static ProviderDao dao;
	private static Provider provider;

	private Pair<String, ArrayList<Author>> blankPair;
	private Pair<String, ArrayList<Author>> getPair;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);

		context = new ClassPathXmlApplicationContext(Constants.Runtime.SPRING_APPLICATION_CONTEXT);
		dao = context.getBean(ProviderDao.class);
		provider = dao.find(Constants.Runtime.VALID_PROVIDER);
	}

	@Before
	public void before() {
		Author author = new Author(null, null, new AssignedAuthor());

		blankPair = Pair.of(null, new ArrayList<>());
		getPair = Pair.of(Constants.Runtime.VALID_PROVIDER.toString(), new ArrayList<>(Arrays.asList(author)));
	}

	@Test
	public void authorLensGetTest() {
		AuthorLens lens = new AuthorLens();
		assertNotNull(lens);

		Pair<String, ArrayList<Author>> pair = lens.get(blankPair);
		assertNotNull(pair);
		assertNull(pair.getLeft());
		assertNotNull(pair.getRight());
	}

	@Test
	public void authorLensPutTest() {
		AuthorLens lens = new AuthorLens();
		assertNotNull(lens);

		Pair<String, ArrayList<Author>> pair = lens.put(blankPair, blankPair);
		assertNotNull(pair);
		assertNull(pair.getLeft());
		assertNotNull(pair.getRight());
	}

	@Test
	public void providerLensGetTest() {
		ProviderLens lens = new ProviderLens();
		assertNotNull(lens);

		Pair<String, ArrayList<Author>> pair = lens.get(blankPair);
		assertNotNull(pair);
		assertNull(pair.getLeft());
		assertNotNull(pair.getRight());

		Author author = pair.getRight().get(0);
		assertEquals(ContextControl.OverridingPropagating, author.getContextControlCode().getCode());
		assertNotNull(author.getTime());
		assertFalse(author.getTime().isNull());
		assertFalse(author.getTime().isInvalidDate());
		assertNotNull(author.getAssignedAuthor());
	}

	@Test
	public void providerLensPutTest() {
		ProviderLens lens = new ProviderLens();
		assertNotNull(lens);

		Pair<String, ArrayList<Author>> pair = lens.put(blankPair, blankPair);
		assertNotNull(pair);
		assertNull(pair.getLeft());
		assertNotNull(pair.getRight());
	}

	@Test
	public void providerIdLensGetTest() {
		ProviderIdLens lens = new ProviderIdLens();
		assertNotNull(lens);

		Pair<String, ArrayList<Author>> pair = lens.get(getPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());

		SET<II> ids = pair.getRight().get(0).getAssignedAuthor().getId();
		assertNotNull(ids);
		assertFalse(ids.get(0).isNull());
		assertEquals(provider.getPractitionerNo(), ids.get(0).getExtension());
	}

	@Test
	public void providerIdLensPutTest() {
		ProviderIdLens lens = new ProviderIdLens();
		assertNotNull(lens);

		Pair<String, ArrayList<Author>> pair = lens.put(blankPair, blankPair);
		assertNotNull(pair);
		assertNull(pair.getLeft());
		assertNotNull(pair.getRight());
	}

	@Test
	public void providerPersonLensGetTest() {
		ProviderPersonLens lens = new ProviderPersonLens();
		assertNotNull(lens);

		Pair<String, ArrayList<Author>> pair = lens.get(getPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());

		Person person = pair.getRight().get(0).getAssignedAuthor().getAssignedAuthorChoiceIfAssignedPerson();
		assertNotNull(person);
		assertTrue(person.getName().get(0).getParts().contains(new ENXP(provider.getFirstName(), EntityNamePartType.Given)));
		assertTrue(person.getName().get(0).getParts().contains(new ENXP(provider.getLastName(), EntityNamePartType.Family)));
	}

	@Test
	public void providerPersonLensPutTest() {
		ProviderPersonLens lens = new ProviderPersonLens();
		assertNotNull(lens);

		Pair<String, ArrayList<Author>> pair = lens.put(blankPair, blankPair);
		assertNotNull(pair);
		assertNull(pair.getLeft());
		assertNotNull(pair.getRight());
	}

	@Test
	public void providerTelecomLensGetTest() {
		ProviderTelecomLens lens = new ProviderTelecomLens();
		assertNotNull(lens);

		Pair<String, ArrayList<Author>> pair = lens.get(getPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());

		SET<TEL> telecoms = pair.getRight().get(0).getAssignedAuthor().getTelecom();
		assertNotNull(telecoms);
		assertEquals(3, telecoms.size());
		assertTrue(telecoms.contains(new TEL(Constants.DocumentHeader.TEL_PREFIX + provider.getPhone().replaceAll("[^0-9]", ""), TelecommunicationsAddressUse.Home)));
		assertTrue(telecoms.contains(new TEL(Constants.DocumentHeader.TEL_PREFIX + provider.getWorkPhone().replaceAll("[^0-9]", ""), TelecommunicationsAddressUse.WorkPlace)));
		assertTrue(telecoms.contains(new TEL(Constants.DocumentHeader.EMAIL_PREFIX + provider.getEmail(), TelecommunicationsAddressUse.Home)));
	}

	@Test
	public void providerTelecomLensPutTest() {
		ProviderTelecomLens lens = new ProviderTelecomLens();
		assertNotNull(lens);

		Pair<String, ArrayList<Author>> pair = lens.put(blankPair, blankPair);
		assertNotNull(pair);
		assertNull(pair.getLeft());
		assertNotNull(pair.getRight());
	}

	@Test
	public void systemLensGetTest() {
		SystemLens lens = new SystemLens();
		assertNotNull(lens);

		Pair<String, ArrayList<Author>> pair = lens.get(blankPair);
		assertNotNull(pair);
		assertNull(pair.getLeft());
		assertNotNull(pair.getRight());

		Author system = pair.getRight().get(0);
		assertEquals(ContextControl.OverridingPropagating, system.getContextControlCode().getCode());
		assertNotNull(system.getTime());
		assertFalse(system.getTime().isNull());
		assertFalse(system.getTime().isInvalidDate());

		AssignedAuthor assignedSystem = system.getAssignedAuthor();
		assertNotNull(assignedSystem);
		assertNotNull(assignedSystem.getId());
		assertNotNull(assignedSystem.getId().get(0).isNull());
		assertEquals(NullFlavor.NoInformation, assignedSystem.getId().get(0).getNullFlavor().getCode());

		AuthoringDevice device = assignedSystem.getAssignedAuthorChoiceIfAssignedAuthoringDevice();
		assertNotNull(device);
		assertEquals(Constants.EMR.EMR_VERSION, device.getSoftwareName().getValue());
	}

	@Test
	public void systemLensPutTest() {
		SystemLens lens = new SystemLens();
		assertNotNull(lens);

		Pair<String, ArrayList<Author>> pair = lens.put(blankPair, blankPair);
		assertNotNull(pair);
		assertNull(pair.getLeft());
		assertNotNull(pair.getRight());
	}
}
