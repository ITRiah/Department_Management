package com.example.Bai1.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String login() {
		return "/login.html";
	}
	
	@PostMapping("/login")
	public String login(
			@RequestParam("username") String userName,
			@RequestParam("password") String passWord,
			HttpSession session) {
		if(userName.equals("admin") && passWord.equals("123")) {
			session.setAttribute("userName",userName );
			return "redirect:/user/list";
		}
		else
			return "redirect:/login";
	}
}
