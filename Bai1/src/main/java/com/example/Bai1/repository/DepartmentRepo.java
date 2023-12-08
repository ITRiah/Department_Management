package com.example.Bai1.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.Bai1.entity.Department;

public interface DepartmentRepo extends JpaRepository<Department, Integer> {
	
	@Query("SELECT d FROM Department d WHERE d.name LIKE :name")
	List<Department> searchName(String name);
	
	Page<Department> findAll(Pageable pageable);
}
