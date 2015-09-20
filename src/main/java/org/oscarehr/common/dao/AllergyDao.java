package org.oscarehr.common.dao;

import java.util.List;

import javax.persistence.Query;

import org.oscarehr.common.model.Allergy;
import org.springframework.stereotype.Repository;

@Repository
public class AllergyDao extends AbstractDao<Allergy> {
	public AllergyDao() {
		super(Allergy.class);
	}

	@SuppressWarnings("unchecked")
	public List<Allergy> findAllergies(Integer demographic_no) {
		String sqlCommand = "SELECT x FROM " + modelClass.getName() + " x WHERE x.id=?1 ORDER BY x.archived, x.severityOfReaction DESC";
		Query query = entityManager.createQuery(sqlCommand);
		query.setParameter(1, demographic_no);
		return query.getResultList();
	}
}
