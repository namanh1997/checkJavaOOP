package com.example.check_java_oop.repository;

import com.example.check_java_oop.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer> {
  Role findByRole(String role);

}
