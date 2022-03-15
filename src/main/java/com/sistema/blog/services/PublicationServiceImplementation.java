package com.sistema.blog.services;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sistema.blog.dto.PublicationDTO;
import com.sistema.blog.dto.PublicationResponse;
import com.sistema.blog.entities.Publication;
import com.sistema.blog.exceptions.ResourceNotFoundException;
import com.sistema.blog.repositories.PublicationRepositorie;

// Indicando que es un servicio
@Service
// Implementa nuestra intefaz
public class PublicationServiceImplementation implements PublicationService {

	
	// Inyectando el acceso a Jpa con el repositorio
	@Autowired
	private PublicationRepositorie publicationRepositorie;
	
	@Override
	public PublicationDTO createPublication(PublicationDTO publicationDTO) {
		/* // Convertimos de DTO a entidad
		Publication publication = new Publication();
		publication.setTitle(publicationDTO.getTitle());
		publication.setDescription(publicationDTO.getDescription());
		publication.setContent(publicationDTO.getContent());
		
		// En este punto ya esta persistiendo en la base de datos por medio de la interfaz Jpa
		Publication newPublication = publicationRepositorie.save(publication);
		
		// Convertimos de entidad a DTO (JSON)
		PublicationDTO publicationResponse = new PublicationDTO();
		publicationResponse.setId(newPublication.getId());
		publicationResponse.setTitle(newPublication.getTitle());
		publicationResponse.setDescription(newPublication.getDescription());
		publicationResponse.setContent(newPublication.getContent());
		
		return publicationResponse; */
		
		// Tomamos la data de JSON
		Publication publication = mapearEntidad(publicationDTO);
		// Creamos el objeto en la BD
		Publication newPublication = publicationRepositorie.save(publication);
		// Retornamos la data en formato JSON
		PublicationDTO publicationResponse = mapearDTO(newPublication);
		
		return publicationResponse;				
	}

	@Override
	public PublicationResponse getAllPublications(int pageNumber, int pageSize,String sortBy,String sortDirection) {
		
		// 1° Implementación
		// public List<PublicationDTO>
		// List<Publication> publications = publicationRepositorie.findAll();
		// return publications.stream().map(publication -> mapearDTO(publication)).collect(Collectors.toList());
		
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
		// Se puede hacer directo sin necesidad del objeto de arriba si no nos importa el asc o desc
		// Pageable pageable = PageRequest.of(pageNumber, pageSize,Sort.by(sortBy));
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		
		
		Page<Publication> publications = publicationRepositorie.findAll(pageable);
		
		// TODO Auto-generated method stub
		List<Publication> publicationsList = publications.getContent();
		
		List<PublicationDTO> content = publicationsList.stream().map(publication -> mapearDTO(publication)).collect(Collectors.toList());
		
		// Usando una clase personalizada para responder en la API
		PublicationResponse publicationResponse = new PublicationResponse();
		publicationResponse.setContent(content);
		publicationResponse.setPageNumber(publications.getNumber());
		publicationResponse.setPageSize(publications.getSize());
		publicationResponse.setTotalElements(publications.getTotalElements());
		publicationResponse.setTotalPages(publications.getTotalPages());
		publicationResponse.setLast(publications.isLast());
		
		return publicationResponse;
	}
	
	// Convertir entidad a DTO
	private PublicationDTO mapearDTO(Publication publication) {
		PublicationDTO publicationDTO = new PublicationDTO();
		// A este objecto DTO le estamos estableciendo la entidad que viene de los parametros
		publicationDTO.setId(publication.getId());
		publicationDTO.setTitle(publication.getTitle());
		publicationDTO.setDescription(publication.getDescription());
		publicationDTO.setContent(publication.getContent());
		
		return publicationDTO;
	}
	
	// Convertir DTO a Entidad
	private Publication mapearEntidad(PublicationDTO publicationDTO) {
		Publication publication = new Publication();
					
		publication.setTitle(publicationDTO.getTitle());
		publication.setDescription(publicationDTO.getDescription());
		publication.setContent(publicationDTO.getContent());
		
		return publication;
	}

	@Override
	public PublicationDTO getPublicationByID(long id) {
		Publication publication = publicationRepositorie
								.findById(id)
								.orElseThrow(
									() -> 
									new ResourceNotFoundException("Publicacion", "id", id));
		return mapearDTO(publication);		
	}

	@Override
	public PublicationDTO updatePublication(PublicationDTO publicationDTO, long id) {
		// TODO Auto-generated method stub
		Publication publication = publicationRepositorie
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Publicación", "id", id) );
		
		publication.setTitle(publicationDTO.getTitle());
		publication.setDescription(publicationDTO.getDescription());
		publication.setContent(publicationDTO.getContent());
		
		Publication updatedPublication = publicationRepositorie.save(publication);
		
		return mapearDTO(updatedPublication);		
	}

	@Override
	public void deletePublication(long id) {
		// TODO Auto-generated method stub
		Publication publication = publicationRepositorie
			.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publicación", "id", id) );
		
		publicationRepositorie.delete(publication);		
	}
	
}
