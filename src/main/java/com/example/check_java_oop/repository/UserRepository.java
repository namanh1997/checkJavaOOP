package com.example.check_java_oop.repository;

import com.example.check_java_oop.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);
}
