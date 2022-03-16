package com.sistema.blog.controllers;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.blog.dto.LoginDTO;
import com.sistema.blog.dto.RegisterDTO;
import com.sistema.blog.entities.Rol;
import com.sistema.blog.entities.User;
import com.sistema.blog.repositories.RolRepositorie;
import com.sistema.blog.repositories.UserRepositorie;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired 
	private UserRepositorie userRepositorie;
	
	@Autowired 
	private RolRepositorie rolRepositorie;
	
	// Cualquier cosa inyectada con un @bean se puede inyectar 
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/login")
	public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDTO){
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseEntity<>("Sucesfully login",HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO){
		
		if(userRepositorie.existsByUsername(registerDTO.getUsername())) {
			return new ResponseEntity<>("Ese nombre de usuario ya existe",HttpStatus.BAD_REQUEST);
		}
		
		if(userRepositorie.existsByEmail(registerDTO.getEmail())) {
			return new ResponseEntity<>("Ese email de usuario ya existe",HttpStatus.BAD_REQUEST);
		}
		
		User user = new User();
		user.setName(registerDTO.getName());
		user.setUsername(registerDTO.getUsername());
		user.setEmail(registerDTO.getEmail());
		user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
		
		Rol roles = rolRepositorie.findByName("ROLE_ADMIN").get();
		user.setRoles(Collections.singleton(roles));
		
		userRepositorie.save(user);
		return new ResponseEntity<>("Sucessfully User Registered",HttpStatus.OK);
	}

}
