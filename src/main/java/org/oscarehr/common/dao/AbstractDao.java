package org.oscarehr.common.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractDao<T> {
	protected Class<T> modelClass;

	@PersistenceContext
	protected EntityManager entityManager;

	protected AbstractDao(Class<T> modelClass) {
		this.modelClass = modelClass;
	}

	// Create
	public T persist(T o) {
		entityManager.persist(o);
		return o;
	}

	// Read
	public T find(Object id) {
		return entityManager.find(modelClass, id);
	}

	// Update
	public T merge(T o) {
		return entityManager.merge(o);
	}

	// Delete
	public void remove(T o) {
		o = entityManager.merge(o);
		entityManager.remove(o);
	}

	protected T getSingleResultOrNull(Query query) {
		query.setMaxResults(1);
		@SuppressWarnings("unchecked")
		List<T> results = query.getResultList();
		if (results.size() == 1) {
			return results.get(0);
		} else {
			return null;
		}
	}
}
