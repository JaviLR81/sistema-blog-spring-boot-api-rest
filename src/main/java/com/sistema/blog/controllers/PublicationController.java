package com.sistema.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.blog.dto.PublicationDTO;
import com.sistema.blog.dto.PublicationResponse;
import com.sistema.blog.services.PublicationService;
import com.sistema.blog.utilities.AppConstants;

@RestController
@RequestMapping("api/publications")
public class PublicationController {

	// Inyectando el servicio
	@Autowired
	private PublicationService publicationService;

	@GetMapping
	// Configurando la paginación
	// 1° Implementación public List<PublicationDTO>
	public PublicationResponse publicationList(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER_DEFAULT, required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE_DEFAULT, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_DEFAULT, required = false) String sortBy,
			@RequestParam(value = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION_DEFAULT, required = false) String sortDirection)
	{
		return publicationService.getAllPublications(pageNumber,pageSize,sortBy,sortDirection);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PublicationDTO> getPublicationByID(@PathVariable(name = "id") long id) {
		return ResponseEntity.ok(publicationService.getPublicationByID(id));
	}

	@PostMapping
	public ResponseEntity<PublicationDTO> savePublication(@RequestBody PublicationDTO publicationDTO) {
		return new ResponseEntity<>(publicationService.createPublication(publicationDTO), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PublicationDTO> updatePublication(@RequestBody PublicationDTO publicationDTO,
			@PathVariable(name = "id") long id) {

		PublicationDTO publicationResponse = publicationService.updatePublication(publicationDTO, id);
		return new ResponseEntity<>(publicationResponse, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePublication(@PathVariable(name = "id") long id) {
		publicationService.deletePublication(id);
		return new ResponseEntity<>("Succesfully deleted publication", HttpStatus.OK);
	}
}
