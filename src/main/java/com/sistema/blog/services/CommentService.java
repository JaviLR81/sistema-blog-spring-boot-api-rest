package com.sistema.blog.services;

import java.util.List;

import com.sistema.blog.dto.CommentDTO;

public interface CommentService {

	public CommentDTO createComment(long publicationId, CommentDTO commentDTO);
	
	public List<CommentDTO> getCommentsByPublicationId(long publicationId);
	
	public CommentDTO getCommentById(Long publicationId,Long commentId);
	
	public CommentDTO updateComment(Long publicationId,Long commentId,CommentDTO commentRequest);
	
	public void deleteComment(Long publicationId,Long commentId);
}
