package com.sistema.blog.services;

import java.util.List;

import com.sistema.blog.dto.PublicationDTO;

public interface PublicationService {
	
	public PublicationDTO createPublication(PublicationDTO publicationDTO);
	
	public List<PublicationDTO> getAllPublications();
	
	public PublicationDTO getPublicationByID(long id);
	
	public PublicationDTO updatePublication(PublicationDTO publicationDTO, long id);
	
	public void deletePublication(long id);

}
