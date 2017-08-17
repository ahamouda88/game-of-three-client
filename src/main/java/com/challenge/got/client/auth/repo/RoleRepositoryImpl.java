package com.challenge.got.client.auth.repo;

import com.challenge.got.client.auth.model.Role;

public class RoleRepositoryImpl extends AbstractRepository<Role, Long> implements RoleRepository {

	public RoleRepositoryImpl() {
		super(Role.class);
	}

}
