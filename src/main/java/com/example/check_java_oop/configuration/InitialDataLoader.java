package com.example.check_java_oop.configuration;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import com.example.check_java_oop.model.Role;
import com.example.check_java_oop.model.User;
import com.example.check_java_oop.repository.RoleRepository;
import com.example.check_java_oop.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		// Roles
		if (roleRepository.findByName("ROLE_ADMIN") == null) {
			roleRepository.save(new Role("ROLE_ADMIN"));
		}

		if (roleRepository.findByName("ROLE_USER") == null) {
			roleRepository.save(new Role("ROLE_USER"));
		}

		// Admin account
		if (userRepository.findByUsername("admin") == null) {
			User admin = new User();
			admin.setUsername("admin");
			admin.setPassword(passwordEncoder.encode("123456"));
//			admin.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//			admin.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
			List<Role> roles = new ArrayList<Role>();
			roles.add(roleRepository.findByName("ROLE_ADMIN"));
			roles.add(roleRepository.findByName("ROLE_USER"));
			admin.setRoles(roles);
			admin.setEnabled(true);
			userRepository.save(admin);
		}

		// Member account
		if (userRepository.findByUsername("user") == null) {
			User user = new User();
			user.setUsername("user");
			user.setPassword(passwordEncoder.encode("123456"));
//			user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//			user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
			List<Role> roles = new ArrayList<Role>();
			roles.add(roleRepository.findByName("ROLE_USER"));
			user.setEnabled(true);
			user.setRoles(roles);
			userRepository.save(user);
		}
	}
}