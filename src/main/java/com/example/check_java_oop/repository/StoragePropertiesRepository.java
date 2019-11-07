package com.example.check_java_oop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.check_java_oop.model.StorageProperties;

@Repository("storagePropertiesRepository")
public interface StoragePropertiesRepository extends JpaRepository<StorageProperties, Integer>{
	List<StorageProperties> findByNameContaining(String term);
}
