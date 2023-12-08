package com.example.Bai1.Service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.Bai1.dto.SearchTicketDTO;
import com.example.Bai1.dto.TicketDTO;
import com.example.Bai1.entity.Ticket;
import com.example.Bai1.repository.TicketRepo;

import ch.qos.logback.core.model.Model;

@Service
public class TicketService {
	@Autowired
	TicketRepo repo;
	
	public Page<TicketDTO> getAll(SearchTicketDTO dto) {
		if(dto.getCurrentPage() ==  null)
			dto.setCurrentPage(0);
		if(dto.getSize() == null)
			dto.setSize(2);
		
		PageRequest pageRequest = PageRequest.of(dto.getCurrentPage(), dto.getSize());
		Page<Ticket> page = repo.findAll(pageRequest);
		Page<TicketDTO> page2 = page.map(ticket ->  new ModelMapper().map(ticket, TicketDTO.class));
		
		return page2;
	}
	
	public void create(TicketDTO t) {
		//khoong can check vi chua co id o DTO
			Ticket ticket = new ModelMapper().map(t, Ticket.class);
			repo.save(ticket);
	}
	
	public TicketDTO getByID(int id) {
		Ticket ticket = repo.findById(id).orElse(null);
		if(ticket != null)
		{
			TicketDTO dto = new ModelMapper().map(ticket, TicketDTO.class);
			return dto;
		}
		return null;
	}
	
	public void update(TicketDTO t) {
		if(repo.findById(t.getId()) !=  null) {
			Ticket ticket = new ModelMapper().map(t, Ticket.class);
			repo.save(ticket);
		}
	}
	
	public void delete(int id) {
		repo.deleteById(id);;
	}
	
	public Page<TicketDTO> search(SearchTicketDTO dto) {
		Sort sort = Sort.by("id").ascending();
		if(dto.getCurrentPage() ==  null)
			dto.setCurrentPage(0);
		if(dto.getSize() == null)
			dto.setSize(2);
		
		PageRequest pageRequest = PageRequest.of(dto.getCurrentPage(), dto.getSize(), sort);
		Page<Ticket> page = repo.findAll(pageRequest);
		
		if(dto.getKeyword() != null) {
			page = repo.searchByName("%" + dto.getKeyword() + "%", pageRequest);
		}
		if(dto.getDepartmentID() != null)
			page = repo.searchByDepartmentID(dto.getDepartmentID(), pageRequest);
		
		Page<TicketDTO> page2 = page.map(ticket -> new ModelMapper().map(ticket, TicketDTO.class));
		return page2;
	}	
}
