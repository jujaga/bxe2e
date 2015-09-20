package org.oscarehr.casemgmt.dao;

import java.util.List;

import javax.persistence.Query;

import org.oscarehr.casemgmt.model.CaseManagementIssue;
import org.oscarehr.common.dao.AbstractDao;
import org.springframework.stereotype.Repository;

@Repository
public class CaseManagementIssueDao extends AbstractDao<CaseManagementIssue> {
	public CaseManagementIssueDao() {
		super(CaseManagementIssue.class);
	}

	@SuppressWarnings("unchecked")
	public List<CaseManagementIssue> getIssuesByDemographic(String demographic_no) {
		String sqlCommand = "SELECT x FROM " + modelClass.getName() + " x WHERE x.demographic_no=?1";
		Query query = entityManager.createQuery(sqlCommand);
		query.setParameter(1, demographic_no);
		return query.getResultList();
	}
}
