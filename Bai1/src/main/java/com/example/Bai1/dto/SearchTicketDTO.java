package com.example.Bai1.dto;

import java.util.Date;

import groovy.transform.EqualsAndHashCode;
import lombok.Data;

@Data
@EqualsAndHashCode(callSuper = false)
public class SearchTicketDTO extends ListUserDTO {
	private Date start;
	private Date end;
	private boolean status;
	private Integer departmentID;
}
