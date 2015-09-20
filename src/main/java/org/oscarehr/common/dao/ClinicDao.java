package org.oscarehr.common.dao;

import org.oscarehr.common.model.Clinic;
import org.springframework.stereotype.Repository;

@Repository
public class ClinicDao extends AbstractDao<Clinic> {
	public ClinicDao() {
		super(Clinic.class);
	}
}
