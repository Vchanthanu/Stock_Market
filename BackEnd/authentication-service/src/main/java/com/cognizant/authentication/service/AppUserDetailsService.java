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
import com.cognizant.authentication.model.Role;
import com.cognizant.authentication.model.User;
import com.cognizant.authentication.repository.RoleRepository;
import com.cognizant.authentication.repository.UserRepository;
import com.cognizant.authentication.security.AppUser;


@Service
public class AppUserDetailsService implements UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppUserDetailsService.class);
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	public AppUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(userName);
		LOGGER.info("Inside Service");
		if (user == null) {
			throw new UsernameNotFoundException("Username is not found");
		} else {
			return new AppUser(user);
		}

	}

	@Transactional
	public void addUser(@Valid User user) throws UserAlreadyExistsException {
		User users = userRepository.findByUserName(user.getUserName());
		if (users == null) {
			user.setPassword(passwordEncoder().encode(user.getPassword()));
			Role role = roleRepository.findById(2).get();
			user.setRole(role);
			userRepository.save(user);
		} else {
			throw new UserAlreadyExistsException("User Already Exists");
		}
	}

	@Transactional
	public User findByUserName(String username) {
		return userRepository.findByUserName(username);
	}


	@Transactional
	public void modifyUser(User user) {
		Role role = roleRepository.findById(2).get();
		user.setRole(role);
		userRepository.save(user);
	}
	
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
