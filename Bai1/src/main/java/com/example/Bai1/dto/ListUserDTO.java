package com.example.Bai1.dto;

import lombok.Data;

@Data
public class ListUserDTO {
	private String keyword;
	private Integer currentPage;
	private Integer size;
	
	public ListUserDTO() {
		super();
	}
	
	
}
