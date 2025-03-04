package com.drbooleani.blogging.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.drbooleani.blogging.models.User;

public interface UserRepository extends CrudRepository<User, Integer>, PagingAndSortingRepository<User, Integer> {
	@Query("SELECT u FROM User u WHERE u.fullName LIKE %:fullName%")
    Page<User> findByFullNameLike(@Param("fullName") String fullName, Pageable pageable);
	boolean existsByEmail(String email);
}
