package com.example.Bai1.dto;

import java.util.Objects;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {
	private int id;
	@NotBlank(message = "Không được để trống")
	private String name;
	private int age;
	private String avatarURL;
	private String username;
	private String password;
	private MultipartFile file;
	
	private DepartmentDTO department;

	
	public UserDTO(int id) {
		this.id = id;
	}
	
	public UserDTO() {
		
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		return id == other.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
