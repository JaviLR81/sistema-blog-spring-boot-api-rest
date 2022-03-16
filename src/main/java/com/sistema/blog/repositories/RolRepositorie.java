package com.sistema.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.blog.entities.Rol;

public interface RolRepositorie extends JpaRepository<Rol, Long> {
	
	public Optional<Rol> findByName(String name);
	
}
