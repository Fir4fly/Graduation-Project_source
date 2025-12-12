package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.User;
import com.example.demo.service.NewUserService;

@Controller
public class NewLoginController {
	
	@Autowired
	private NewUserService newUserService;
	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "newlogin";
	}
	
	@PostMapping("/register")
	public String registerUser(
						@ModelAttribute User user,
						BindingResult bindingResult,
						@RequestParam("confPass")String confPass,
						Model model) {
		if(!user.getLoginPass().equals(confPass)) {
			model.addAttribute("error","パスワードが一致していません");
			model.addAttribute("user",user);
			return "newlogin";
		}
		
		User registeredUser = newUserService.registerNewUser(user);
		
		if(registeredUser == null) {
			model.addAttribute("error","既に使用されているIDです");
			model.addAttribute("user",user);
			return "newlogin";
		}
		
		return "redirect:/login";
	}
}
