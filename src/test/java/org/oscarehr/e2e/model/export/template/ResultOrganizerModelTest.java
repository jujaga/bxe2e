package org.oscarehr.e2e.model.export.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.marc.everest.datatypes.II;
import org.marc.everest.datatypes.NullFlavor;
import org.marc.everest.datatypes.generic.CD;
import org.marc.everest.datatypes.generic.CS;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Component4;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.EntryRelationship;
import org.marc.everest.rmim.uv.cdar2.pocd_mt000040uv.Organizer;
import org.marc.everest.rmim.uv.cdar2.vocabulary.ActStatus;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActClassDocumentEntryOrganizer;
import org.marc.everest.rmim.uv.cdar2.vocabulary.x_ActRelationshipEntryRelationship;
import org.oscarehr.e2e.constant.Constants;
import org.oscarehr.e2e.model.PatientExport.LabOrganizer;
import org.oscarehr.e2e.model.export.template.ResultOrganizerModel;

public class ResultOrganizerModelTest {
	private static ResultOrganizerModel resultOrganizerModel;

	@BeforeClass
	public static void beforeClass() {
		Logger.getRootLogger().setLevel(Level.FATAL);
		resultOrganizerModel = new ResultOrganizerModel();
	}

	@Test
	public void resultOrganizerActiveTest() {
		LabOrganizer labOrganizer = new LabOrganizer(Constants.Runtime.VALID_LAB_NO, "P");
		Organizer organizer = resultOrganizerStructureTestHelper(labOrganizer);

		CS<ActStatus> statusCode = organizer.getStatusCode();
		assertEquals(ActStatus.Active, statusCode.getCode());
	}

	@Test
	public void resultOrganizerCompleteTest() {
		LabOrganizer labOrganizer = new LabOrganizer(Constants.Runtime.VALID_LAB_NO, "F");
		Organizer organizer = resultOrganizerStructureTestHelper(labOrganizer);

		CS<ActStatus> statusCode = organizer.getStatusCode();
		assertEquals(ActStatus.Completed, statusCode.getCode());
	}

	@Test
	public void resultOrganizerNullTest() {
		Organizer organizer = resultOrganizerStructureTestHelper(null);
		CS<ActStatus> statusCode = organizer.getStatusCode();

		assertTrue(statusCode.isNull());
		assertEquals(NullFlavor.Unknown, statusCode.getNullFlavor().getCode());
	}

	private Organizer resultOrganizerStructureTestHelper(LabOrganizer labOrganizer) {
		EntryRelationship entryRelationship = resultOrganizerModel.getEntryRelationship(labOrganizer);
		assertNotNull(entryRelationship);
		assertEquals(x_ActRelationshipEntryRelationship.HasComponent, entryRelationship.getTypeCode().getCode());
		assertTrue(entryRelationship.getContextConductionInd().toBoolean());
		assertEquals(Constants.TemplateOids.RESULT_ORGANIZER_TEMPLATE_ID, entryRelationship.getTemplateId().get(0).getRoot());

		Organizer organizer = entryRelationship.getClinicalStatementIfOrganizer();
		assertNotNull(organizer);
		assertEquals(x_ActClassDocumentEntryOrganizer.BATTERY, organizer.getClassCode().getCode());

		if(labOrganizer != null) {
			II id = organizer.getId().get(0);
			assertNotNull(id);
			assertTrue(id.getExtension().contains(Constants.IdPrefixes.LabOBR.toString()));
			assertTrue(id.getExtension().contains(labOrganizer.getGroupId().toString()));
		}

		CD<String> code = organizer.getCode();
		assertNotNull(code);
		assertTrue(code.isNull());
		assertEquals(NullFlavor.Unknown, code.getNullFlavor().getCode());

		CS<ActStatus> statusCode = organizer.getStatusCode();
		assertNotNull(statusCode);

		ArrayList<Component4> labComponents = organizer.getComponent();
		assertNotNull(labComponents);
		assertEquals(1, labComponents.size());

		return organizer;
	}
}
