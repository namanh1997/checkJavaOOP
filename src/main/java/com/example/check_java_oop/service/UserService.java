package com.example.check_java_oop.service;

import java.util.ArrayList;
import java.util.List;

import com.example.check_java_oop.model.Role;
import com.example.check_java_oop.model.User;
import com.example.check_java_oop.model.UserPrincipal;
import com.example.check_java_oop.repository.RoleRepository;
import com.example.check_java_oop.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	// private BCryptPasswordEncoder bCryptPasswordEncoder;

	// @Autowired
	// public UserService(UserRepository userRepository, RoleRepository
	// roleRepository,
	// BCryptPasswordEncoder bCryptPasswordEncoder) {
	// this.userRepository = userRepository;
	// this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	// }

//  public User findUserByUsername(String username) {
//    return userRepository.findByUsername(username);
//  }

	// public void saveUser(User user) {
	// user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	// // Role userRole = roleRepository.findByRole("ADMIN");
	// userRepository.save(user);
	// }

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) {

		User user = userRepository.findByUsername(username);

//		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
//		List<Role> roles = user.getRoles();
//		if (roles != null) {
//			for (Role role : roles) {
//				grantList.add(new SimpleGrantedAuthority(role.getName()));
//			}
//		}
//
//		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantList);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new UserPrincipal(user);
	}

}
