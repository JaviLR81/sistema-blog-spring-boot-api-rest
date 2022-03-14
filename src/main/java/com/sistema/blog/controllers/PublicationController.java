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

@RestController
@RequestMapping("api/publications")
public class PublicationController {

	// Inyectando el servicio
	@Autowired
	private PublicationService publicationService;

	@GetMapping
	// Configurando la paginación
	public PublicationResponse publicationList(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		return publicationService.getAllPublications(pageNumber,pageSize);
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

		PublicationDTO publicationRespnse = publicationService.updatePublication(publicationDTO, id);
		return new ResponseEntity<>(publicationRespnse, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePublication(@PathVariable(name = "id") long id) {
		publicationService.deletePublication(id);
		return new ResponseEntity<>("Succesfully deleted publication", HttpStatus.OK);
	}
}
