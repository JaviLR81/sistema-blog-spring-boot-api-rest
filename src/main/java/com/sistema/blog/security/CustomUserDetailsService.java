package com.sistema.blog.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sistema.blog.entities.Rol;
import com.sistema.blog.entities.User;
import com.sistema.blog.repositories.UserRepositorie;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	
	@Autowired
	private UserRepositorie userRepositorie;
	
	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		User userVariable = userRepositorie.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
					.orElseThrow(() -> new UsernameNotFoundException("User not found with name or email: "+usernameOrEmail));
		
		return new org.springframework.security.core.userdetails.User(userVariable.getEmail(),userVariable.getPassword(), mapearRoles(userVariable.getRoles()));		
	}
	
	private Collection<? extends GrantedAuthority> mapearRoles(Set<Rol> roles){
		return roles.stream().map( rol -> new SimpleGrantedAuthority(rol.getName())).collect(Collectors.toList());
	}

}
