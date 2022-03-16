package com.sistema.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.blog.entities.User;

public interface UserRepositorie extends JpaRepository<User,Long> {

	public Optional<User> findByEmail(String email);
	
	public Optional<User> findByUsernameOrEmail(String username,String email);
	
	public Optional<User> findByUsername(String username);
	
	public boolean existsByUsername(String username);
	
	public boolean existsByEmail(String email);	
}
