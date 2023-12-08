package com.example.Bai1.dto;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class DepartmentDTO {
	private int id;
	private String name;
	private Date createAt;
	private Date updateAt;
}
