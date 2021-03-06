package com.stockmarket.authentication.service;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stockmarket.authentication.exception.UserAlreadyExistsException;
import com.stockmarket.authentication.model.User;
import com.stockmarket.authentication.repository.UserRepository;
import com.stockmarket.authentication.security.AppUser;

@Service
public class AppUserDetailsService implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppUserDetailsService.class);
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		LOGGER.info("Inside loadUserByUsername method");
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
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(user);
		} else {
			throw new UserAlreadyExistsException("User Already Exists");
		}
	}

	@Transactional
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
