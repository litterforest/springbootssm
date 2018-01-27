package com.cobee.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cobee.demo.service.SecureUserService;

@Controller
@RequestMapping("/SecureUser")
public class SecureUserController {
	
	@Autowired
	private SecureUserService secureUserService;
	
	@GetMapping("/list")
	public String list(Model model)
	{
		model.addAttribute("secureUserList", secureUserService.listAll());
		return "userList";
	}
	
	@PostMapping("/delete")
	public String delete(Integer id, Model model)
	{
		secureUserService.deleteByLogic(id);
		model.addAttribute("secureUserList", secureUserService.listAll());
		return "redirect:list";
	}
	
}
