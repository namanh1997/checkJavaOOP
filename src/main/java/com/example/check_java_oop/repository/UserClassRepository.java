package com.example.check_java_oop.repository;

import com.example.check_java_oop.model.UserClass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userClassRepository")
public interface UserClassRepository extends JpaRepository<UserClass, Integer> {
	UserClass findByName(String name);
}
