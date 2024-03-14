package com.example.Bai1.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.Bai1.client.dto.CategoryDTO;
import com.example.Bai1.client.service.WSLoginService;


import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class CategoryController {
	@Autowired
	WSLoginService loginService;

	@GetMapping("/category/new")
	public String newDP(Model model) {
		model.addAttribute("categoryDTO", new CategoryDTO());
		return "add-category";
	}

	@PostMapping("/category/new")
	public String add(@ModelAttribute("categoryDTO") @Valid CategoryDTO categoryDTO, BindingResult bindingResult, HttpSession httpSession) {

		if (bindingResult.hasErrors())
			return "add-category";
		
		String token = (String) httpSession.getAttribute("token");
		loginService.create(categoryDTO, "Bearer " + token );
		return "users.html";
	}
	
}
