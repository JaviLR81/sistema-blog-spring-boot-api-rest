package com.sistema.blog.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.sistema.blog.entities.Comment;

public class PublicationDTO {

	private Long id;
	
	@NotEmpty
	@Size(min = 2,message="The publication title should be at least 2 characters")	
	private String title;
	
	@NotEmpty
	@Size(min = 10,message="The publication description should be at least 10 characters")
	private String description;
	
	private String content;
	// Conjunto de comentarios
	private Set<Comment> comments;

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public PublicationDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
