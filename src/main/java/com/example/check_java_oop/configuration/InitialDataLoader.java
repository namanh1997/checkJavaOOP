package com.example.check_java_oop.configuration;

import java.util.Arrays;

import javax.transaction.Transactional;

import com.example.check_java_oop.model.Role;
import com.example.check_java_oop.model.User;
import com.example.check_java_oop.repository.RoleRepository;
import com.example.check_java_oop.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitialDataLoader implements
  ApplicationListener<ContextRefreshedEvent> {
 
    boolean alreadySetup = false;
 
    @Autowired
    private UserRepository userRepository;
  
    @Autowired
    private RoleRepository roleRepository;

    // @Autowired
    // private PasswordEncoder passwordEncoder;
  
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
  
      if (alreadySetup)
        return;
        
      createRoleIfNotFound("ROLE_ADMIN");
      createRoleIfNotFound("ROLE_USER");

      User admin = new User();
      admin.setUsername("admin");
      admin.setPassword("123456");
      // admin.setPassword(passwordEncoder.encode("123456"));
      admin.setEnabled(true);
      admin.setRoles(roleRepository.findAll());
      userRepository.save(admin);

      User user = new User();
      user.setUsername("user");
      user.setPassword("123456");
      // user.setPassword(passwordEncoder.encode("123456"));
      user.setEnabled(true);
      user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
      userRepository.save(user);

      alreadySetup = true;
    }
 
    @Transactional
    private Role createRoleIfNotFound(String name) {
  
      Role role = roleRepository.findByName(name);
      if (role == null) {
        role = new Role(name);
        roleRepository.save(role);
      }
      return role;
    }
}