package com.sistema.blog.utilities;

import org.springframework.boot.SpringApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sistema.blog.SistemaBlogSpringBootApiRestApplication;

public class PasswordEncoderGenerator {
	
	public static void main(String[] args) {
		
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println(passwordEncoder.encode("password"));
	}

}
