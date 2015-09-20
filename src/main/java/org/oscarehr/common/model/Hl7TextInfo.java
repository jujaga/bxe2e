package org.oscarehr.common.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: hl7TextInfo
 *
 */
@Entity
@Table(name="hl7TextInfo")
public class Hl7TextInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "lab_no")
	private Integer labNumber;
	@Column(name = "sex")
	private String sex;
	@Column(name = "health_no")
	private String healthNumber;
	@Column(name = "result_status")
	private String resultStatus;
	@Column(name = "final_result_count")
	private Integer finalResultCount;
	@Column(name = "obr_date")
	private String obrDate;
	private String priority;
	@Column(name = "requesting_client")
	private String requestingProvider;
	private String discipline;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "report_status")
	private String reportStatus;
	@Column(name = "accessionNum")
	private String accessionNumber;
	@Column(name = "filler_order_num")
	private String fillerOrderNum;
	@Column(name = "sending_facility")
	private String sendingFacility;
	private String label;

	public Hl7TextInfo() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLabNumber() {
		return labNumber;
	}

	public void setLabNumber(Integer labNumber) {
		this.labNumber = labNumber;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getHealthNumber() {
		return healthNumber;
	}

	public void setHealthNumber(String healthNumber) {
		this.healthNumber = healthNumber;
	}

	public String getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}

	public Integer getFinalResultCount() {
		return finalResultCount;
	}

	public void setFinalResultCount(Integer finalResultCount) {
		this.finalResultCount = finalResultCount;
	}

	public String getObrDate() {
		return obrDate;
	}

	public void setObrDate(String obrDate) {
		this.obrDate = obrDate;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getRequestingProvider() {
		return requestingProvider;
	}

	public void setRequestingProvider(String requestingProvider) {
		this.requestingProvider = requestingProvider;
	}

	public String getDiscipline() {
		return discipline;
	}

	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

	public String getAccessionNumber() {
		return accessionNumber;
	}

	public void setAccessionNumber(String accessionNumber) {
		this.accessionNumber = accessionNumber;
	}

	public String getFillerOrderNum() {
		return fillerOrderNum;
	}

	public void setFillerOrderNum(String fillerOrderNum) {
		this.fillerOrderNum = fillerOrderNum;
	}

	public String getSendingFacility() {
		return sendingFacility;
	}

	public void setSendingFacility(String sendingFacility) {
		this.sendingFacility = sendingFacility;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
