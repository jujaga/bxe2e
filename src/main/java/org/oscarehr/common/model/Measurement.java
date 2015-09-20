package org.oscarehr.common.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity implementation class for Entity: Measurement
 *
 */
@Entity
@Table(name="measurements")
public class Measurement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "type")
	private String type;
	@Column(name = "demographicNo")
	private Integer demographicId;
	@Column(name = "providerNo")	
	private String providerNo;
	@Column(name = "dataField", nullable=false, length=255)
	private String dataField = "";
	@Column(name = "measuringInstruction", length=255)
	private String measuringInstruction = "";
	@Column(name = "comments", length=255)
	private String comments = "";
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dateObserved")
	private Date dateObserved;
	@Column(name = "appointmentNo")
	private Integer appointmentNo;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dateEntered")
	private Date createDate = new Date();

	public Measurement() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getDemographicId() {
		return demographicId;
	}

	public void setDemographicId(Integer demographicId) {
		this.demographicId = demographicId;
	}

	public String getProviderNo() {
		return providerNo;
	}

	public void setProviderNo(String providerNo) {
		this.providerNo = providerNo;
	}

	public String getDataField() {
		return dataField;
	}

	public void setDataField(String dataField) {
		this.dataField = dataField;
	}

	public String getMeasuringInstruction() {
		return measuringInstruction;
	}

	public void setMeasuringInstruction(String measuringInstruction) {
		this.measuringInstruction = measuringInstruction;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getDateObserved() {
		return dateObserved;
	}

	public void setDateObserved(Date dateObserved) {
		this.dateObserved = dateObserved;
	}

	public Integer getAppointmentNo() {
		return appointmentNo;
	}

	public void setAppointmentNo(Integer appointmentNo) {
		this.appointmentNo = appointmentNo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
