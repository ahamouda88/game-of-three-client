package com.challenge.got.client.auth.repo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An abstract Dao class that defines the standard and generic operations to be performed on a model object(s)
 * 
 * @param <T>
 *            the model's data type
 * @param <E>
 *            the id's data type
 */
public abstract class AbstractRepository<T, E extends Serializable> implements MainRepository<T, E> {

	protected static Logger logger = LoggerFactory.getLogger(AbstractRepository.class);

	@PersistenceContext
	protected EntityManager entityManager;
	private Class<T> classType;

	public AbstractRepository(Class<T> classType) {
		this.classType = classType;
	}

	@Override
	public boolean create(T model) {
		try {
			entityManager.persist(model);
		} catch (Exception ex) {
			logger.error("Unable to persist the following entity: " + model, ex);
			return false;
		}
		return true;
	}

	@Override
	public boolean update(T model) {
		try {
			entityManager.merge(model);
		} catch (Exception ex) {
			logger.error("Unable to update the following entity: " + model, ex);
			return false;
		}
		return true;
	}

	@Override
	public T remove(T model) {
		try {
			entityManager.remove(model);
		} catch (Exception ex) {
			logger.error("Unable to remove the following entity: " + model, ex);
			return null;
		}
		return model;
	}

	@Override
	public List<T> findAll() {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<T> cq = cb.createQuery(classType);
			Root<T> from = cq.from(classType);
			CriteriaQuery<T> all = cq.select(from);
			TypedQuery<T> allQuery = entityManager.createQuery(all);
			return allQuery.getResultList();
		} catch (Exception ex) {
			logger.error("Unable to get all models", ex);
			return null;
		}
	}

	@Override
	public T find(E id) {
		return entityManager.find(classType, id);
	}
}
