package com.challenge.got.client.auth.repo;

import com.challenge.got.client.auth.model.User;

/**
 * An interface that extends {@link MainRepository}
 */
public interface UserRepository extends MainRepository<User, Long> {

	/**
	 * This method finds a user that is mapped to a given username
	 * 
	 * @param username
	 *            the user's user name
	 * @return the user mapped to the given user name, or <b>null</b> if user name doesn't exist
	 */
	public abstract User findByUsername(String username);
}
