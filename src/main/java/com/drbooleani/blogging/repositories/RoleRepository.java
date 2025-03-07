package com.drbooleani.blogging.repositories;

import org.springframework.data.repository.CrudRepository;

import com.drbooleani.blogging.models.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
	Role findByName(String name);
}
