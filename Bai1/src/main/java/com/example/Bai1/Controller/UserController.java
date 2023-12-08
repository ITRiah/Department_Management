package com.example.Bai1.Controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.Bai1.Service.DepartmentService;
import com.example.Bai1.Service.UserService;
import com.example.Bai1.dto.DepartmentDTO;
import com.example.Bai1.dto.ListUserDTO;
import com.example.Bai1.dto.UserDTO;
import com.example.Bai1.entity.User;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class UserController {
	
	@Autowired //Tìm và gán đối tượng đã được tạo trước nếu không nó là null
	UserService service;
	@Autowired
	DepartmentService departmentService;
	
	@GetMapping("/user/new")
	public String newUser(Model model) {
		model.addAttribute("userDTO", new UserDTO());
		Page<DepartmentDTO> page = departmentService.getAll(new ListUserDTO());
		model.addAttribute( "departments" , page.getContent());
		System.out.println("2");
		
		return "add-User.html";
	}
	
	@PostMapping("/user/new")
	public String newUser(
			Model model,
			@ModelAttribute("userDTO") @Valid UserDTO u,
			BindingResult bindingResult)
			throws IllegalStateException, IOException {
		
		if(bindingResult.hasErrors())
		{
			Page<DepartmentDTO> page = departmentService.getAll(new ListUserDTO());
			model.addAttribute( "departments" , page.getContent());
			return "add-User";
		}
		System.out.println("3");
		//Upload file
		String fileName = u.getFile().getOriginalFilename();
		if (!u.getFile().isEmpty()) {
			File file = new File("E:/" + fileName);
			u.getFile().transferTo(file);
//			model.addAttribute(file);//khong can thiet, vi ko day file qua view dc nhu vay, file la binary 
		}
		System.out.println("4");

		u.setAvatarURL(fileName);
		service.creat(u);

		return "redirect:/user/list";
	}
	
	@GetMapping("/download")
	public void download(@RequestParam("fileName") String fileName , HttpServletResponse response) throws IOException {
		File file = new File("E:/" + fileName); 
		Files.copy(file.toPath(), response.getOutputStream());//lấy dữ liệu từ file để tải về hình ảnh cho web
	}
	
	
	
	@GetMapping("/user/list")
	public String getList(Model model , @ModelAttribute ListUserDTO abc ) {
//		-Với ModelAttribute thì khi đặt tên , tên đó sẽ được sử dựng trong trang html
//		-Khi không đặt tên thì tên sử dụng trong trang sẽ là tên của đối tượng (không phải tên class)
		Page<DepartmentDTO> page2 = departmentService.getAll(new ListUserDTO());
		Page<UserDTO> page = service.getAll(abc);
		List<Integer> lst = new ArrayList<Integer>();
		for (int i = 1; i <= page.getTotalPages(); i++) {
			lst.add(i);
		}
		
		model.addAttribute("userList", page.getContent());
		model.addAttribute("departmentList", page2.getContent());
		model.addAttribute("pages", lst);
		
		return "users";
	}
	
	@PostMapping("/user/list")
	public String listPage(
					@ModelAttribute ListUserDTO listUserDTO,
					Model model,
					@RequestParam("totalPages") int totalPages) {
		int currentPage = listUserDTO.getCurrentPage();
		if(currentPage + 1 < totalPages)
			currentPage ++;
		listUserDTO.setCurrentPage(currentPage);
		
		Page<UserDTO> page = service.getAll(listUserDTO);
		model.addAttribute("userList" , page);
		model.addAttribute("totalPages" , page.getTotalPages());
		
		return "users";
	}
	
	@GetMapping("/user/delete")
	public String delete(@RequestParam("id") int id) {
		service.delete(id);
		
		return "redirect:/user/list";
	}

	@GetMapping("/user/update")
	public String update(@RequestParam("id") int id, Model model) {
		User u = service.getByID(id);
		Page<DepartmentDTO> page = departmentService.getAll(new ListUserDTO());
		
		model.addAttribute("departments", page.getContent());
		model.addAttribute("user", u);
		
		return "update-user.html";
	}
	
	@PostMapping("/user/update")
	public String updateUser(UserDTO u , @RequestParam("fileName")MultipartFile upLoad) throws IllegalStateException, IOException {
		
		if(!upLoad.isEmpty()) {
			String fileName  = upLoad.getOriginalFilename();
			File file = new File("E:/" + fileName);
			upLoad.transferTo(file);
			u.setAvatarURL(fileName);
		}
		
		service.update(u);
		return "redirect:/user/list";
	}
	
	@GetMapping("/user/search")
	public String search(@RequestParam("keyword") String name, Model model) {
		
		List<UserDTO> lst = service.searchByName(name);
		Page<DepartmentDTO> page = departmentService.getAll(new ListUserDTO());
		model.addAttribute( "departmentList" , page.getContent());
		model.addAttribute("userList", lst);
		model.addAttribute("keyword" , name);
		
		return "users.html";
	}
	
	
}
