package com.sistema.blog.services;

import com.sistema.blog.dto.PublicationDTO;
import com.sistema.blog.dto.PublicationResponse;

public interface PublicationService {
	
	public PublicationDTO createPublication(PublicationDTO publicationDTO);
	
	// 1° Implementacion
	// public List<PublicationDTO> getAllPublications(int pageNumber, int pageSize);
	
	public PublicationResponse getAllPublications(int pageNumber, int pageSize,String sortBy, String sortDirection);
		
	public PublicationDTO getPublicationByID(long id);
	
	public PublicationDTO updatePublication(PublicationDTO publicationDTO, long id);
	
	public void deletePublication(long id);

}
