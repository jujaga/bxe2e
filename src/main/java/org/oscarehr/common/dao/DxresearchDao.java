package org.oscarehr.common.dao;

import java.util.List;

import javax.persistence.Query;

import org.oscarehr.common.model.Dxresearch;
import org.springframework.stereotype.Repository;

@Repository
public class DxresearchDao extends AbstractDao<Dxresearch> {
	public DxresearchDao() {
		super(Dxresearch.class);
	}

	@SuppressWarnings("unchecked")
	public List<Dxresearch> getDxResearchItemsByPatient(Integer demographicNo) {
		String sqlCommand = "SELECT x FROM " + modelClass.getName() + " x WHERE x.demographicNo=?1";
		Query query = entityManager.createQuery(sqlCommand);
		query.setParameter(1, demographicNo);
		List<Dxresearch> items = query.getResultList();
		return items;
	}
}
