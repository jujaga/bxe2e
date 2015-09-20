package org.oscarehr.casemgmt.dao;

import java.util.List;

import javax.persistence.Query;

import org.oscarehr.casemgmt.model.CaseManagementNote;
import org.oscarehr.common.dao.AbstractDao;
import org.springframework.stereotype.Repository;

@Repository
public class CaseManagementNoteDao extends AbstractDao<CaseManagementNote> {
	public CaseManagementNoteDao() {
		super(CaseManagementNote.class);
	}

	@SuppressWarnings("unchecked")
	public List<CaseManagementNote> getNotesByDemographic(String demographic_no) {
		String sqlCommand = "SELECT x FROM " + modelClass.getName() + " x WHERE x.demographic_no=?1 AND x.id = (SELECT MAX(y.id) FROM " + modelClass.getName() + " y WHERE y.uuid = x.uuid) ORDER BY x.observation_date";
		Query query = entityManager.createQuery(sqlCommand);
		query.setParameter(1, demographic_no);
		return query.getResultList();
	}
}
