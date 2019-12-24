package com.example.check_java_oop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.example.check_java_oop.model.User;
import com.example.check_java_oop.service.UserDetailsServiceImpl;
import com.example.check_java_oop.service.UserService;
import com.example.check_java_oop.validator.UserValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserValidator userValidator;

	@InitBinder
	protected void initBinder(WebDataBinder dataBinder) {
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		System.out.println("Target=" + target);

		if (target.getClass() == User.class) {
			dataBinder.setValidator(userValidator);
		}
	}

	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}

	@GetMapping("/registration")
	public String getRegistration(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}

	@PostMapping("/registration")
	public String saveRegistration(@ModelAttribute("user") @Validated User user,
			BindingResult bindingResult) {
//		userValidator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {
			return "registration";
		}
		userService.save(user);
		return "redirect:/userList";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "login";
	}

}
