package com.sistema.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.blog.entities.Comment;

public interface CommentRepositorie extends JpaRepository<Comment,Long> {
	
	public List<Comment> findByPublicationId(long publicationId);
	
}
