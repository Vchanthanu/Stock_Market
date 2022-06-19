package com.cognizant.authentication.service;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognizant.authentication.exception.UserAlreadyExistsException;
import com.cognizant.authentication.model.User;
import com.cognizant.authentication.repository.UserRepository;
import com.cognizant.authentication.security.AppUser;


@Service
public class AppUserDetailsService implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppUserDetailsService.class);
	@Autowired
	private UserRepository userRepository;

	public AppUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		LOGGER.info("Inside Service");
		if (user == null) {
			throw new UsernameNotFoundException("Username is not found");
		} else {
			return new AppUser(user);
		}

	}

	@Transactional
	public void addUser(@Valid User user) throws UserAlreadyExistsException {
		User users = userRepository.findByEmail(user.getEmail());
		if (users == null) {
//			user.setPassword(passwordEncoder().encode(user.getPassword()));
			userRepository.save(user);
		} else {
			throw new UserAlreadyExistsException("User Already Exists");
		}
	}

	@Transactional
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}


	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
