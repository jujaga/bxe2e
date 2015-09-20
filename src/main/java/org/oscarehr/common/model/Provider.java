package org.oscarehr.common.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity implementation class for Entity: Provider
 *
 */
@Entity
@Table(name="provider")
public class Provider implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String SYSTEM_PROVIDER_NO = "-1";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer providerNo;
	private String lastName;
	private String firstName;
	private String providerType;
	private String specialty;
	private String team;
	private String sex;
	@Temporal(TemporalType.DATE)
	private Date dob;
	private String address;
	private String phone;
	private String workPhone;

	private String ohipNo;
	private String rmaNo;
	private String billingNo;
	private String hsoNo;
	private String status; // "1"=active, "0"=inactive
	private String comments;
	private String providerActivity;
	private String practitionerNo;

	private String email;
	private String title;
	private String lastUpdateUser;
	@Temporal(TemporalType.DATE)
	private Date lastUpdateDate = new Date();
	@Temporal(TemporalType.DATE)
	private Date SignedConfidentiality;

	public Provider() {
		super();
	}

	public Integer getProviderNo() {
		return providerNo;
	}

	public void setProviderNo(Integer providerNo) {
		this.providerNo = providerNo;
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

	public String getProviderType() {
		return providerType;
	}

	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getOhipNo() {
		return ohipNo;
	}

	public void setOhipNo(String ohipNo) {
		this.ohipNo = ohipNo;
	}

	public String getRmaNo() {
		return rmaNo;
	}

	public void setRmaNo(String rmaNo) {
		this.rmaNo = rmaNo;
	}

	public String getBillingNo() {
		return billingNo;
	}

	public void setBillingNo(String billingNo) {
		this.billingNo = billingNo;
	}

	public String getHsoNo() {
		return hsoNo;
	}

	public void setHsoNo(String hsoNo) {
		this.hsoNo = hsoNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getProviderActivity() {
		return providerActivity;
	}

	public void setProviderActivity(String providerActivity) {
		this.providerActivity = providerActivity;
	}

	public String getPractitionerNo() {
		return practitionerNo;
	}

	public void setPractitionerNo(String practitionerNo) {
		this.practitionerNo = practitionerNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Date getSignedConfidentiality() {
		return SignedConfidentiality;
	}

	public void setSignedConfidentiality(Date signedConfidentiality) {
		SignedConfidentiality = signedConfidentiality;
	}
}
