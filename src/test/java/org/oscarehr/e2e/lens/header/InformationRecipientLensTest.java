package org.oscarehr.e2e.lens.header;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.InformationRecipient;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_InformationRecipient;

public class InformationRecipientLensTest {
	private Pair<Object, ArrayList<InformationRecipient>> inputPair;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
	}

	@Before
	public void before() {
		inputPair = Pair.of(new Object(), new ArrayList<>());
	}

	@Test
	public void informationRecipientLensGetTest() {
		InformationRecipientLens lens = new InformationRecipientLens();
		assertNotNull(lens);

		Pair<Object, ArrayList<InformationRecipient>> pair = lens.get(inputPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
		assertEquals(1, pair.getRight().size());
		assertEquals(x_InformationRecipient.PRCP, pair.getRight().get(0).getTypeCode().getCode());
		assertNotNull(pair.getRight().get(0).getIntendedRecipient());
		assertEquals(NullFlavor.NoInformation, pair.getRight().get(0).getIntendedRecipient().getNullFlavor().getCode());
	}

	@Test
	public void informationRecipientPutTest() {
		InformationRecipientLens lens = new InformationRecipientLens();
		assertNotNull(lens);

		Pair<Object, ArrayList<InformationRecipient>> pair = lens.put(inputPair, inputPair);
		assertNotNull(pair);
		assertNotNull(pair.getLeft());
		assertNotNull(pair.getRight());
	}
}
