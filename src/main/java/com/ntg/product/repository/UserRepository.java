package com.ntg.product.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ntg.product.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	  User findByEmail(String email);
	  Boolean existsByUserName(String username);
	  Boolean existsByEmail(String email);
	
}
