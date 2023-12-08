package com.example.Bai1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.Bai1.entity.Ticket;

public interface TicketRepo extends JpaRepository<Ticket, Integer> {
	@Query("SELECT t FROM Ticket t WHERE t.clientName LIKE :x")
	Page<Ticket> searchByName(String x, Pageable pageable); 
	
	@Query("SELECT t FROM Ticket t WHERE t.department.id = :x")
	Page<Ticket> searchByDepartmentID(int x, Pageable pageable); 
}
