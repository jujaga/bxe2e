package org.oscarehr.common.dao;

import javax.persistence.Query;

import org.oscarehr.common.model.Hl7TextInfo;
import org.springframework.stereotype.Repository;

@Repository
public class Hl7TextInfoDao extends AbstractDao<Hl7TextInfo> {
	public Hl7TextInfoDao() {
		super(Hl7TextInfo.class);
	}

	public Hl7TextInfo findLabId(Integer labId) {
		String sqlCommand="SELECT x FROM " + modelClass.getName() + " x WHERE x.labNumber=?1";
		Query query = entityManager.createQuery(sqlCommand);
		query.setParameter(1, labId);
		return getSingleResultOrNull(query);
	}
}
