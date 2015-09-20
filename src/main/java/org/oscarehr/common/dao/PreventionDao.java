package org.oscarehr.common.dao;

import java.util.List;

import javax.persistence.Query;

import org.oscarehr.common.model.Prevention;
import org.springframework.stereotype.Repository;

@Repository
public class PreventionDao extends AbstractDao<Prevention> {
	public PreventionDao() {
		super(Prevention.class);
	}

	@SuppressWarnings("unchecked")
	public List<Prevention> findNotDeletedByDemographicId(Integer demographicId) {
		String sqlCommand = "SELECT x FROM " + modelClass.getName() + " x WHERE x.demographicId=?1 AND x.deleted=?2";
		Query query = entityManager.createQuery(sqlCommand);
		query.setParameter(1, demographicId);
		query.setParameter(2, '0');
		return query.getResultList();
	}
}
