package com.example.Bai1.dto;

import java.util.Objects;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
