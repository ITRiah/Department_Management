package com.example.Bai1.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Bai1.dto.UserDTO;
import com.example.Bai1.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	
	@Query("SELECT u FROM User u Where u.name LIKE :x")
	List<User> searchByName(@Param("x") String s);
	
	Page<User> findAll(Pageable pageable);
}
