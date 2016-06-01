package org.oscarehr.e2e.lens.body.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.TS;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;
import org.oscarehr.e2e.constant.Constants;

public class AuthorParticipationLensTest {
	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
	}

	@Test
	public void documentBodyLensGetTest() {
		AuthorParticipationLens lens = new AuthorParticipationLens(Constants.Runtime.VALID_PROVIDER.toString());
		assertNotNull(lens);

		ArrayList<Author> nullAuthors = lens.get(null);
		assertNotNull(nullAuthors);
		assertEquals(1, nullAuthors.size());
		assertEquals(ContextControl.OverridingPropagating, nullAuthors.get(0).getContextControlCode().getCode());
		assertTrue(nullAuthors.get(0).getTemplateId().contains(new II(Constants.TemplateOids.AUTHOR_PARTICIPATION_TEMPLATE_ID)));
		assertNotNull(nullAuthors.get(0).getTime());
		assertTrue(nullAuthors.get(0).getTime().isNull());
		assertEquals(NullFlavor.Unknown, nullAuthors.get(0).getTime().getNullFlavor().getCode());
		assertNotNull(nullAuthors.get(0).getAssignedAuthor());
		assertNotNull(nullAuthors.get(0).getAssignedAuthor().getId());
		assertNotNull(nullAuthors.get(0).getAssignedAuthor().getAssignedAuthorChoiceIfAssignedPerson());

		ArrayList<Author> authors = lens.get(new Date());
		assertNotNull(authors);
		assertEquals(1, authors.size());
		assertEquals(ContextControl.OverridingPropagating, authors.get(0).getContextControlCode().getCode());
		assertTrue(authors.get(0).getTemplateId().contains(new II(Constants.TemplateOids.AUTHOR_PARTICIPATION_TEMPLATE_ID)));
		assertNotNull(authors.get(0).getTime());
		assertFalse(authors.get(0).getTime().isNull());
		assertFalse(authors.get(0).getTime().isInvalidDate());
		assertNotNull(authors.get(0).getAssignedAuthor());
		assertNotNull(authors.get(0).getAssignedAuthor().getId());
		assertNotNull(authors.get(0).getAssignedAuthor().getAssignedAuthorChoiceIfAssignedPerson());
	}

	@Test
	public void documentBodyLensPutTest() {
		Author author = new Author();
		author.setContextControlCode(ContextControl.OverridingPropagating);
		author.setTemplateId(Arrays.asList(new II(Constants.TemplateOids.AUTHOR_PARTICIPATION_TEMPLATE_ID)));
		author.setTime(new TS(new GregorianCalendar(), TS.DAY));

		AuthorParticipationLens lens = new AuthorParticipationLens(Constants.Runtime.VALID_PROVIDER.toString());
		assertNotNull(lens);

		assertNull(lens.put(new ArrayList<>()));
		assertNotNull(lens.put(new ArrayList<>(Arrays.asList(author))));
	}
}
