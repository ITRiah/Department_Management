package com.example.Bai1.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.Bai1.dto.ListUserDTO;
import com.example.Bai1.dto.UserDTO;
import com.example.Bai1.entity.User;
import com.example.Bai1.repository.UserRepo;

@Service //tự tạo một đối tượng UserService và lưu lại
public class UserService {
	
	@Autowired
	UserRepo userRepo; //đối tương db
	
	public Page<UserDTO> getAll(ListUserDTO listUserDTO) {
		
		if(listUserDTO.getCurrentPage() == null)
			listUserDTO.setCurrentPage(0);
		if(listUserDTO.getSize() == null)
			listUserDTO.setSize(2);
		
		Sort sortBy = Sort.by("age").descending();
		
		PageRequest pageRequest = PageRequest.of(listUserDTO.getCurrentPage(), listUserDTO.getSize(), sortBy);
		
		Page<User> userPage = userRepo.findAll(pageRequest);
		
		//ModelMapper
		Page<UserDTO> userDTOPage = userPage.map(user -> new ModelMapper().map(user, UserDTO.class));
		return userDTOPage;
	}
	
	public void creat(UserDTO userDTO) {
		System.out.println(userDTO.toString());
		if(!userRepo.findById(userDTO.getId()).isPresent())
		{
			User user = new ModelMapper().map(userDTO, User.class);
			userRepo.save(user);
		}
	}
	
	public User getByID(int id) {
		return userRepo.getById(id);
	}
	
	public void delete(int id) {
		userRepo.deleteById(id);
	}
	
	public void update(UserDTO userDTO) {
		if(userRepo.findById(userDTO.getId()).isPresent())
		{
			User u = new ModelMapper().map(userDTO, User.class);
			System.out.println(u.toString());
			userRepo.save(u);
		}
	}
	
	public List<UserDTO> searchByName(String name){
		List<User> userList = userRepo.searchByName("%" + name + "%");
		List<UserDTO> userDTOList = userList.stream().map(user -> new ModelMapper().map(user, UserDTO.class)).collect(Collectors.toList());
		return userDTOList;
	}
}
