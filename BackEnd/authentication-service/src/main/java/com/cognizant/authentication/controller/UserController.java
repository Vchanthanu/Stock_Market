package com.cognizant.authentication.controller;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.authentication.bean.CommonResponse;
import com.cognizant.authentication.exception.UserAlreadyExistsException;
import com.cognizant.authentication.model.User;
import com.cognizant.authentication.service.AppUserDetailsService;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@RestController
@RequestMapping("/user")
public class UserController {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private AppUserDetailsService appUserDetailsService;

	@PostMapping("/signup")
	private CommonResponse addUser(@RequestBody @Valid User user)  {
		CommonResponse res = new CommonResponse();
		try {
			LOGGER.info("User creation request received :: {}",user.toString());
			appUserDetailsService.addUser(user);
			res.setStatus(true);
			res.setMessage("User Added successfully");
		} catch (UserAlreadyExistsException e) {
			res.setMessage(e.getMessage());
		}
		return res;
	}

//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
	
	
	@GetMapping("/login")
	public Map<String, String> authenticate(@RequestHeader("Authorization") String authHeader) {
		LOGGER.info("START Authenticate ");
		Map<String, String> jwt = new HashMap<String, String>();
		String email = getEmail(authHeader);
		User appUser = (User) appUserDetailsService.findByEmail(email);
		if(null != appUser && appUser.getPassword().contentEquals(getPassword(authHeader))) {
			String token = generateJwt(appUser.getUserName());
			jwt.put("token", token);
			jwt.put("user", appUser.getUserName());
			jwt.put("id", String.valueOf(appUser.getId()));
			jwt.put("status","true");
			jwt.put("message","Logged in successfully");
		}else{
			if(null == appUser) {
				jwt.put("status","false");
				jwt.put("message","Invalid emailId.");
			}else {
				jwt.put("status","false");
				jwt.put("message","Invalid password.");
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
		String user = new String(credentials).split(":")[0];
		LOGGER.info("End get user method");
		return user;
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


