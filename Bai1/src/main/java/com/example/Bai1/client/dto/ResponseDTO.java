package com.example.Bai1.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor; //tự tạo constructor tất cả tham số.

@Data
@Builder // tạo đối tượng bằng cách gọi hàm
@NoArgsConstructor
@AllArgsConstructor

public class ResponseDTO<T> {
	private int status;
	private String msg;
	
	@JsonInclude(Include.NON_NULL) // nếu giá trí !null -> trả về dữ liệu
	private T data;
}
