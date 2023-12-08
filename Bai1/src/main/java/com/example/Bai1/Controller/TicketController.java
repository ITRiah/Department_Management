package com.example.Bai1.Controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Bai1.Service.DepartmentService;
import com.example.Bai1.Service.TicketService;
import com.example.Bai1.dto.DepartmentDTO;
import com.example.Bai1.dto.ListUserDTO;
import com.example.Bai1.dto.SearchTicketDTO;
import com.example.Bai1.dto.TicketDTO;

@Controller
public class TicketController {
	@Autowired 
	TicketService ticketService;
	@Autowired
	DepartmentService departmentService;
	
	@GetMapping("/ticket/list")
	public String getList(@ModelAttribute SearchTicketDTO dto, Model model) {
		Page<TicketDTO> page = ticketService.getAll(dto);
		Page<DepartmentDTO> page2 = departmentService.getAll(new ListUserDTO());
		List<Integer> lst = new ArrayList<Integer>();
		for (int i = 1; i <= page.getTotalPages(); i++) {
			lst.add(i);
		}
		
		model.addAttribute("departmentList" , page2.getContent());
		model.addAttribute("ticketList" , page.getContent());
		model.addAttribute("pages" , lst);
		return "tickets.html";
	}
	
	@GetMapping("/ticket/new")
	public String add(Model model) {
	 Page<DepartmentDTO> page = departmentService.getAll(new ListUserDTO());
	 model.addAttribute("departmentList" , page.getContent());
	 return "ticket-new";
	}
	
	@PostMapping("/ticket/new")
	public String addTicket(@ModelAttribute TicketDTO t) {
		ticketService.create(t);
		return "redirect:/ticket/list";
	}
	
	@GetMapping("/ticket/update")
	public String edit(@RequestParam("id") int id, Model model) {
		TicketDTO t = ticketService.getByID(id);
		Page<DepartmentDTO> page = departmentService.getAll(new ListUserDTO());
		model.addAttribute("departmentList" , page.getContent());
		model.addAttribute("t" , t);
		return"update_Ticket";
	}
	
	@PostMapping("/ticket/update")
	public String update(@ModelAttribute TicketDTO t){
		ticketService.update(t);
		return "redirect:/ticket/list";
	}
	
	@GetMapping("/ticket/delete")
	public String delete(@RequestParam("id") int id) {
		ticketService.delete(id);
		return "redirect:/ticket/list";
	}
	
	@GetMapping("/ticket/search")
	public String search(@ModelAttribute SearchTicketDTO dto , Model model) {
		Page<TicketDTO> page = ticketService.search(dto);
		Page<DepartmentDTO> page2 = departmentService.getAll(new ListUserDTO());
		model.addAttribute("departmentList" , page2.getContent());
		model.addAttribute("ticketList" , page.getContent());
		return "tickets";
	}
}


