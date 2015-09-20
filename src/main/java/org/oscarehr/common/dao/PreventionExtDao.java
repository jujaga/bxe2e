package org.oscarehr.common.dao;

import java.util.List;

import javax.persistence.Query;

import org.oscarehr.common.model.PreventionExt;
import org.springframework.stereotype.Repository;

@Repository
public class PreventionExtDao extends AbstractDao<PreventionExt> {
	public PreventionExtDao() {
		super(PreventionExt.class);
	}

	@SuppressWarnings("unchecked")
	public List<PreventionExt> findByPreventionId(Integer preventionId) {
		String sqlCommand = "SELECT x FROM " + modelClass.getName() + " x WHERE x.preventionId = ?1";
		Query query = entityManager.createQuery(sqlCommand);
		query.setParameter(1, preventionId);
		return query.getResultList();
	}
}
