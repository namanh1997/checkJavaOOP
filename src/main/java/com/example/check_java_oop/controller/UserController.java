package com.example.check_java_oop.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.check_java_oop.model.User;
import com.example.check_java_oop.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/userList")
	public String list(Model model) {
		model.addAttribute("users", userService.findAll());
		return "userList";
	}

	@GetMapping("/user/search")
	public String search(@RequestParam("term") String term, Model model) {
		if (StringUtils.isEmpty(term)) {
			return "redirect:/userList";
		}

		model.addAttribute("users", userService.search(term));
		return "userList";
	}

	@GetMapping("/user/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("user", userService.findOne(id));
		return "registration";
	}

	@GetMapping("/user/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes redirect) {
		userService.delete(id);
		redirect.addFlashAttribute("successMessage", "Xóa tài khoản thành công!");
		return "redirect:/userList";
	}

}
