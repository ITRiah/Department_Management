package com.example.Bai1.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Bai1.Service.DepartmentService;
import com.example.Bai1.dto.DepartmentDTO;
import com.example.Bai1.dto.ListUserDTO;

import jakarta.validation.Valid;

@Controller
public class DepartmentController {
	@Autowired
	DepartmentService departmentService;
	
	@GetMapping("/department/list")
	public String getList(ListUserDTO listUserDTO, Model model) {
		Page<DepartmentDTO> list = departmentService.getAll(listUserDTO);
		model.addAttribute("departmentDTO", list);
		return "departments";
	}
	
	@GetMapping("/department/new")
	public String newDP(Model model) {
		model.addAttribute("departmentDTO", new DepartmentDTO());
		return "add-department";
	}
	
	@PostMapping("/department/new")
	public String add(
			@ModelAttribute("departmentDTO") @Valid DepartmentDTO departmentDTO,
			BindingResult bindingResult) {
		
		if(bindingResult.hasErrors())
			return"add-department";
					
		departmentService.create(departmentDTO);
		return "redirect:/department/list";
	}
	
	@GetMapping("/department/update")
	public String edit(@RequestParam("id") int id , Model model) {
		DepartmentDTO departmentDTO = departmentService.getById(id);
		model.addAttribute("departmentDTO", departmentDTO);
		return "update-department";
	}
	
	@PostMapping("/department/update")
	public String update(@ModelAttribute("departmentDTO") DepartmentDTO departmentDTO) {
		System.out.println(departmentDTO.getId()+ "id");
		departmentService.update(departmentDTO);
		return "redirect:/department/list";
	}
	
	@GetMapping("/department/delete")
	public String delete(@RequestParam("id") int id) {
		departmentService.delete(id);
		return "redirect:/department/list";
	}
	
	@GetMapping("/department/search")
	public String search(Model model , @RequestParam("keyword")String name) {
		List<DepartmentDTO> departmentDTO = departmentService.search(name);
		model.addAttribute("departmentDTO",departmentDTO);
		return "departments";
	}
}
