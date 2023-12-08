package com.example.Bai1.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.Bai1.dto.DepartmentDTO;
import com.example.Bai1.dto.ListUserDTO;
import com.example.Bai1.entity.Department;
import com.example.Bai1.repository.DepartmentRepo;

@Service
public class DepartmentService {
	@Autowired
	DepartmentRepo departmentRepo;
	
	public Page<DepartmentDTO> getAll(ListUserDTO listUserDTO){
		if(listUserDTO.getCurrentPage() == null)
			listUserDTO.setCurrentPage(0);
		if(listUserDTO.getSize() == null)
			listUserDTO.setSize(3);
		
		System.out.println(listUserDTO.toString());

		
		PageRequest pageRequest = PageRequest.of(listUserDTO.getCurrentPage(), listUserDTO.getSize());
		Page<Department> page = departmentRepo.findAll(pageRequest);
		
		Page<DepartmentDTO> page2 = page.map(department -> new ModelMapper().map(department, DepartmentDTO.class));
		return page2;
	}
	
	public void create(DepartmentDTO departmentDTO) {
		if(!departmentRepo.findById(departmentDTO.getId()).isPresent()) {
			Department department = new ModelMapper().map(departmentDTO, Department.class);
			departmentRepo.save(department);
		}
	}
	
	public void update(DepartmentDTO departmentDTO) {
		Department department = departmentRepo.getById(departmentDTO.getId());
		if(department != null) {
			department.setName(departmentDTO.getName());
			departmentRepo.save(department);
		}
	}
	
	public void delete(int id) {
		departmentRepo.deleteById(id);
	}
	
	
	public DepartmentDTO getById(int id) {
		Department department = departmentRepo.getById(id);
		if(department != null) {
			DepartmentDTO departmentDTO = new ModelMapper().map(department, DepartmentDTO.class);
			return departmentDTO;
		}
		return null;
	}

	public List<DepartmentDTO> search(String name){
		List<Department> departments = departmentRepo.searchName("%" + name +"%");
		
		List<DepartmentDTO> departmentDTOs = departments.stream().map(department -> 
											new ModelMapper().map(department, DepartmentDTO.class)).collect(Collectors.toList());
		return departmentDTOs;
 	}
	
	
}
