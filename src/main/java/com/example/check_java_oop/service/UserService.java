package com.example.check_java_oop.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.check_java_oop.exception.ResourceNotFoundException;
import com.example.check_java_oop.model.User;
import com.example.check_java_oop.repository.RoleRepository;
import com.example.check_java_oop.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

	public List<User> search(String term) {
		return userRepository.findByUsernameContaining(term);
	}

	public User findOne(Integer id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
	}

	public void delete(Integer id) {
		userRepository.deleteById(id);
	}

	public void save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
		userRepository.save(user);
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
