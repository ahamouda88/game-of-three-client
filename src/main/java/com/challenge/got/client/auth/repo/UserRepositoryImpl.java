package com.challenge.got.client.auth.repo;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.challenge.got.client.auth.model.User;

@Repository
public class UserRepositoryImpl extends AbstractRepository<User, Long> implements UserRepository {

	public UserRepositoryImpl() {
		super(User.class);
	}

	@Override
	public User findByUsername(String username) {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<User> cq = cb.createQuery(User.class);
			Root<User> from = cq.from(User.class);
			cq.select(from);
			Predicate usernamePredicate = cb.equal(cb.lower(from.get("username")), username.toLowerCase());
			cq.where(usernamePredicate);
			TypedQuery<User> query = entityManager.createQuery(cq);
			return query.getSingleResult();
		} catch (NoResultException ex) {
			logger.debug("User with the following username: " + username + " doesn't exist", ex);
			return null;
		}
	}
}
