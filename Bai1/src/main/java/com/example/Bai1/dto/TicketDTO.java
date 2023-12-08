package com.example.Bai1.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class TicketDTO {
	public Integer id;
	private String clientName;
	private String clientPhone;
	private String content;
	private Boolean status;
	private Date createAt;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date processDate;
	private DepartmentDTO department;
}