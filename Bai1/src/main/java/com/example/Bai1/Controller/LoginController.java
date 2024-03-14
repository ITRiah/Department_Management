package com.example.Bai1.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.example.Bai1.client.dto.LoginUserDTO;
import com.example.Bai1.client.dto.ResponseDTO;
import com.example.Bai1.client.dto.TokenDTO;
import com.example.Bai1.client.service.WSLoginService;
import com.example.Bai1.dto.UserDTO;

import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpSession;
import javassist.tools.web.BadHttpRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class LoginController {
	
	@Autowired
	WSLoginService wsLoginService;

	@GetMapping("/login")
	public String login() {
		return "/login.html";
	}

	@PostMapping("/login")
	public String login(@RequestParam("username") String userName, @RequestParam("password") String passWord,
			HttpSession session) throws IOException {

		//TokenDTO response = remoteLogin(userName, passWord);
		
		//call API bằng openFeign
		ResponseDTO<String> login = wsLoginService.login(userName, passWord);
		String token = login.getData();
		
		if (token != null) {
			session.setAttribute("username", userName);
			session.setAttribute("token", token);			
			
			//call API bằng restTemplate
			LoginUserDTO loginUserDTO = getMe(token); // tự map các thuộc tính cấp 1 với nhau nếu trùng tên.
			
			session.setAttribute("user", loginUserDTO.getData());
						
			return "redirect:/user/list";
		} else
			return "redirect:/login";
	}

	private TokenDTO remoteLogin(String username, String password) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		LinkedMultiValueMap<String, String> requestBody = new LinkedMultiValueMap();
		requestBody.add("username", username);
		requestBody.add("password", password);

		HttpEntity<LinkedMultiValueMap<String, String>> entity = new HttpEntity<LinkedMultiValueMap<String, String>>(
				requestBody, headers);

		ResponseEntity<TokenDTO> response = restTemplate.exchange("http://localhost:8000/login", HttpMethod.POST, entity,
				TokenDTO.class);
		
		return response.getBody();
	}
	
	private LoginUserDTO getMe(String token) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);	

		HttpEntity<Void> entity = new HttpEntity<>(headers); //body không cần nhập

		ResponseEntity<LoginUserDTO> response = restTemplate.exchange("http://localhost:8000/me", HttpMethod.GET, entity,LoginUserDTO.class);
		
		if(response.getStatusCode() == HttpStatus.OK)
			return response.getBody();
		else
			throw new NoResultException();
	}
}
