package org.oscarehr.common.dao;

import org.oscarehr.common.model.Demographic;
import org.springframework.stereotype.Repository;

@Repository
public class DemographicDao extends AbstractDao<Demographic> {
	public DemographicDao() {
		super(Demographic.class);
	}
}
