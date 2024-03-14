package com.example.Bai1.client.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Bai1.client.dto.CategoryDTO;
import com.example.Bai1.client.dto.ResponseDTO;

import jakarta.validation.Valid;

@FeignClient(value = "loginService"  ,url = "http://localhost:8000")
public interface WSLoginService {
	@PostMapping("/login")
	public ResponseDTO<String> login(@RequestParam("username") String username,@RequestParam("password") String password);
	
	@PostMapping("/admin/category/")
	public ResponseDTO<Void> create(@RequestBody @Valid CategoryDTO categoryDTO , @RequestHeader("Authorization") String bearerToken);
}


