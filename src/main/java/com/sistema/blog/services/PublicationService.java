package com.sistema.blog.services;

import com.sistema.blog.dto.PublicationDTO;
import com.sistema.blog.dto.PublicationResponse;

public interface PublicationService {
	
	public PublicationDTO createPublication(PublicationDTO publicationDTO);
	
	public PublicationResponse getAllPublications(int pageNumber, int pageSize);
	
	public PublicationDTO getPublicationByID(long id);
	
	public PublicationDTO updatePublication(PublicationDTO publicationDTO, long id);
	
	public void deletePublication(long id);

}
