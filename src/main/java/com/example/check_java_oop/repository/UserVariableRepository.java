package com.example.check_java_oop.repository;

import com.example.check_java_oop.model.UserVariable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userVariableRepository")
public interface UserVariableRepository extends JpaRepository<UserVariable, Integer> {
  UserVariable findByName(String name);
}
