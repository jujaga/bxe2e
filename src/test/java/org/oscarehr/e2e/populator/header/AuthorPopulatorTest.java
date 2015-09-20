package org.oscarehr.e2e.populator.header;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;
import org.marc.everest.datatypes.TS;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.AssignedAuthor;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Author;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ContextControl;
import org.oscarehr.e2e.populator.AbstractPopulatorTest;

public class AuthorPopulatorTest extends AbstractPopulatorTest {
	@Test
	public void authorTest() {
		ArrayList<Author> authors = clinicalDocument.getAuthor();
		assertNotNull(authors);
		assertEquals(2, authors.size());
	}

	@Test
	public void authorProviderTest() {
		Author author = clinicalDocument.getAuthor().get(0);
		assertNotNull(author);
		assertEquals(ContextControl.OverridingPropagating, author.getContextControlCode().getCode());
		assertFalse(author.getTime().isInvalidDate());

		TS now = TS.now();
		now.setDateValuePrecision(TS.DAY);
		assertTrue(author.getTime().toString().contains(now.toString()));

		AssignedAuthor assignedAuthor = author.getAssignedAuthor();
		assertNotNull(assignedAuthor);
		assertNotNull(assignedAuthor.getAssignedAuthorChoiceIfAssignedPerson());
	}

	@Test
	public void authorSystemTest() {
		Author author = clinicalDocument.getAuthor().get(1);
		assertNotNull(author);
		assertEquals(ContextControl.OverridingPropagating, author.getContextControlCode().getCode());
		assertFalse(author.getTime().isInvalidDate());

		TS now = TS.now();
		now.setDateValuePrecision(TS.DAY);
		assertTrue(author.getTime().toString().contains(now.toString()));

		AssignedAuthor assignedAuthor = author.getAssignedAuthor();
		assertNotNull(assignedAuthor);
		assertNotNull(assignedAuthor.getAssignedAuthorChoiceIfAssignedAuthoringDevice());
	}
}
