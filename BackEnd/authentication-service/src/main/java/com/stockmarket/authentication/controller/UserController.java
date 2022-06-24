package com.stockmarket.authentication.controller;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockmarket.authentication.bean.CommonResponse;
import com.stockmarket.authentication.exception.UserAlreadyExistsException;
import com.stockmarket.authentication.model.User;
import com.stockmarket.authentication.service.AppUserDetailsService;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/api/v1.0/market/authentication/user")
public class UserController {

	public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private AppUserDetailsService appUserDetailsService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@PostMapping("/signup")
	public CommonResponse addUser(@RequestBody @Valid User user) {
		CommonResponse res = new CommonResponse();
		try {
			LOGGER.info("User creation request received :: {}", user.toString());
			appUserDetailsService.addUser(user);
			res.setStatus(true);
			res.setMessage("User Added successfully");
		} catch (UserAlreadyExistsException e) {
			res.setMessage(e.getMessage());
		}
		return res;
	}

	@GetMapping("/login")
	public Map<String, String> authenticate(@RequestHeader("Authorization") String authHeader) {
		LOGGER.info("START Authenticate ");
		Map<String, String> jwt = new HashMap<String, String>();
		String email = getEmail(authHeader);
		User appUser = appUserDetailsService.findByEmail(email);
		if (null != appUser && passwordEncoder.matches(getPassword(authHeader), appUser.getPassword())) {
			String token = generateJwt(email);
			jwt.put("token", token);
			jwt.put("user", appUser.getUserName());
			jwt.put("email", appUser.getEmail());
			jwt.put("id", String.valueOf(appUser.getId()));
			jwt.put("status", "true");
			jwt.put("message", "Logged in successfully");
		} else {
			if (null == appUser) {
				jwt.put("status", "false");
				jwt.put("message", "Invalid email.");
			} else {
				jwt.put("status", "false");
				jwt.put("message", "Invalid password.");
			}
		}
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

	private String getEmail(String authHeader) {
		LOGGER.info("Start get user method");
		String encodedCredentials = authHeader.split(" ")[1];
		byte[] credentials = Base64.getDecoder().decode(encodedCredentials);
		String email = new String(credentials).split(":")[0];
		LOGGER.info("End get user method");
		return email;
	}

	private String getPassword(String authHeader) {
		LOGGER.info("Start get user method");
		String encodedCredentials = authHeader.split(" ")[1];
		byte[] credentials = Base64.getDecoder().decode(encodedCredentials);
		String password = new String(credentials).split(":")[1];
		LOGGER.info("End get user method");
		return password;
	}
}
