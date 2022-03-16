package com.sistema.blog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sistema.blog.dto.CommentDTO;
import com.sistema.blog.entities.Comment;
import com.sistema.blog.entities.Publication;
import com.sistema.blog.exceptions.BlogAppException;
import com.sistema.blog.exceptions.ResourceNotFoundException;
import com.sistema.blog.repositories.CommentRepositorie;
import com.sistema.blog.repositories.PublicationRepositorie;

@Service
public class CommentServiceImplementation implements CommentService {

		@Autowired
		private ModelMapper modelMapper;
	
		// Inyectando los dos repositorios involucrados
	 	@Autowired
	    private CommentRepositorie commentRepositorie;

	    @Autowired
	    private PublicationRepositorie publicationRepositorie;

	    @Override
	    public CommentDTO createComment(long publicationId, CommentDTO commentDTO) {
	        // TODO Auto-generated method stub
	        Comment comment = mapearEntidad(commentDTO);

	        Publication publication = publicationRepositorie
	        .findById(publicationId).orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicationId) );

	        comment.setPublication(publication);
	        Comment newComment = commentRepositorie.save(comment);
	        return mapearDTO(newComment);        
	    }

		@Override
		public List<CommentDTO> getCommentsByPublicationId(long publicationId) {			
			List<Comment> comments = commentRepositorie.findByPublicationId(publicationId);			
			return comments.stream().map(comment -> mapearDTO(comment)).collect(Collectors.toList());
		}

		@Override
		public CommentDTO getCommentById(Long publicationId, Long commentId) {
			
			Publication publication = publicationRepositorie
			        .findById(publicationId).orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicationId) );
			
			Comment comment = commentRepositorie.findById(commentId)
								.orElseThrow( () -> new ResourceNotFoundException("Comentario", "id", commentId) );
			
			// Sino estamos buscando un comentario que pertenezca a tal publicación retornamos
			// una excepción
			if(!comment.getPublication().getId().equals(publication.getId())) {
				throw new BlogAppException(HttpStatus.BAD_REQUEST,"El comentario no pertenece a la publicación");
			}
			
			return mapearDTO(comment);
		}

		@Override
		public CommentDTO updateComment(Long publicationId,Long commentId,CommentDTO commentRequest) {
			
			Publication publication = publicationRepositorie
			        .findById(publicationId).orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicationId) );
			
			Comment comment = commentRepositorie.findById(commentId)
								.orElseThrow( () -> new ResourceNotFoundException("Comentario", "id", commentId) );
			
			// Sino estamos buscando un comentario que pertenezca a tal publicación retornamos
			// una excepción
			if(!comment.getPublication().getId().equals(publication.getId())) {
				throw new BlogAppException(HttpStatus.BAD_REQUEST,"El comentario no pertenece a la publicación");
			}
			
			comment.setName(commentRequest.getName());
			comment.setEmail(commentRequest.getEmail());
			comment.setBody(commentRequest.getBody());
			
			Comment updatedComment = commentRepositorie.save(comment);
			
			return mapearDTO(updatedComment);
		}

		@Override
		public void deleteComment(Long publicationId, Long commentId) {
			// TODO Auto-generated method stub
			Publication publication = publicationRepositorie
			        .findById(publicationId).orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicationId) );
			
			Comment comment = commentRepositorie.findById(commentId)
								.orElseThrow( () -> new ResourceNotFoundException("Comentario", "id", commentId) );
			
			// Sino estamos buscando un comentario que pertenezca a tal publicación retornamos
			// una excepción
			if(!comment.getPublication().getId().equals(publication.getId())) {
				throw new BlogAppException(HttpStatus.BAD_REQUEST,"El comentario no pertenece a la publicación");
			}
			
			commentRepositorie.delete(comment);			
		}
		
		private CommentDTO mapearDTO(Comment comment) {			
			CommentDTO commentDTO =  modelMapper.map(comment, CommentDTO.class);
			return commentDTO;
		}
		
		private Comment mapearEntidad(CommentDTO comementDTO) {
			Comment comment = modelMapper.map(comementDTO, Comment.class);
			return comment;
		}
}
