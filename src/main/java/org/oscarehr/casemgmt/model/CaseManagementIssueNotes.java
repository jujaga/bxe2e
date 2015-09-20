package org.oscarehr.casemgmt.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: CaseManagementIssueNotes
 *
 */
@Entity
@Table(name="casemgmt_issue_notes")
public class CaseManagementIssueNotes implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer note_id;

	public CaseManagementIssueNotes() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNote_id() {
		return note_id;
	}

	public void setNote_id(Integer note_id) {
		this.note_id = note_id;
	}
}
