package com.example.check_java_oop.service;

import com.example.check_java_oop.model.User;
import com.example.check_java_oop.repository.RoleRepository;
import com.example.check_java_oop.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {

  private UserRepository userRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public UserService(UserRepository userRepository, RoleRepository roleRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  public User findUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public void saveUser(User user) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    // Role userRole = roleRepository.findByRole("ADMIN");
    userRepository.save(user);
  }

}
