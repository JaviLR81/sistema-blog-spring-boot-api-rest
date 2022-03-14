package com.sistema.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.blog.entities.Publication;

public interface PublicationRepositorie extends JpaRepository<Publication, Long> {

}
