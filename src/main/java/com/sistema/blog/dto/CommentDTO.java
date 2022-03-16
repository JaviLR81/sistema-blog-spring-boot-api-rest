package com.sistema.blog.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CommentDTO {

	private long id;
	@NotEmpty(message = "The name should not be empty")
	private String name;
	@NotEmpty(message = "The email should not be empty")
	@Email
	private String email;
	
	@NotEmpty
	@Size(min = 10,message="The comment body should be at least 10 characters")
	private String body;

	public CommentDTO() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
