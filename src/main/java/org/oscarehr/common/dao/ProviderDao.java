package org.oscarehr.common.dao;

import org.oscarehr.common.model.Provider;
import org.springframework.stereotype.Repository;

@Repository
public class ProviderDao extends AbstractDao<Provider> {
	public ProviderDao() {
		super(Provider.class);
	}
}
