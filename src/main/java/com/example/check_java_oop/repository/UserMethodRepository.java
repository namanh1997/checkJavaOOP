package com.example.check_java_oop.repository;

import com.example.check_java_oop.model.UserMethod;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userMethodRepository")
public interface UserMethodRepository extends JpaRepository<UserMethod, Integer> {
  UserMethod findByName(String name);
}
