package com.cognizant.authentication.controller;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.authentication.security.AppUser;
import com.cognizant.authentication.service.AppUserDetailsService;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {
	public static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
	@Autowired
	private AppUserDetailsService appUserDetailsService;
	@GetMapping
	public Map<String, String> authenticate(@RequestHeader("Authorization") String authHeader) {
		LOGGER.info("START Authenticate ");
		Map<String, String> jwt = new HashMap<String, String>();
		String user = getUser(authHeader);
		String token = generateJwt(user);
		AppUser appUser = (AppUser) appUserDetailsService.loadUserByUsername(user);
		jwt.put("token", token);
		String role = appUser.getRole(); 
		jwt.put("role", role);
		jwt.put("user", user);
		jwt.put("id", String.valueOf(appUser.getUser().getId()));
		LOGGER.info("END Authenticate ");
		return jwt;
	}
	private String generateJwt(String user) {
		JwtBuilder builder = Jwts.builder();
		builder.setSubject(user);
		builder.setIssuedAt(new Date());
		builder.setExpiration(new Date((new Date()).getTime() + 1200000));
		builder.signWith(SignatureAlgorithm.HS256, "secretkey");
		String token = builder.compact();
		return token;
	}

	private String getUser(String authHeader) {
		LOGGER.info("Start get user method");
		String encodedCredentials = authHeader.split(" ")[1];
		byte[] credentials = Base64.getDecoder().decode(encodedCredentials);
		String user = new String(credentials).split(":")[0];
		LOGGER.info("End get user method");
		return user;
	}
}
