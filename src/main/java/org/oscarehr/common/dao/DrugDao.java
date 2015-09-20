package org.oscarehr.common.dao;

import java.util.List;

import javax.persistence.Query;

import org.oscarehr.common.model.Drug;
import org.springframework.stereotype.Repository;

@Repository
public class DrugDao extends AbstractDao<Drug> {
	public DrugDao() {
		super(Drug.class);
	}

	public List<Drug> findByDemographicId(Integer demographicId) {
		return findByDemographicId(demographicId, null);
	}

	@SuppressWarnings("unchecked")
	public List<Drug> findByDemographicId(Integer demographicId, Boolean archived) {
		String sqlCommand = "SELECT x FROM " + modelClass.getName() + " x WHERE x.demographicId=?1" + (archived == null ? "" : " AND x.archived=?2");
		Query query = entityManager.createQuery(sqlCommand);
		query.setParameter(1, demographicId);
		if (archived != null) {
			query.setParameter(2, archived);
		}
		return query.getResultList();
	}
}
