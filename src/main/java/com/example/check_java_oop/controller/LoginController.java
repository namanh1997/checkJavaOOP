package com.example.check_java_oop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.example.check_java_oop.model.User;
import com.example.check_java_oop.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
  @Autowired
  private UserService userService;

  // @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
  // public ModelAndView login() {
  //   ModelAndView modelAndView = new ModelAndView();
  //   modelAndView.setViewName("login");
  //   return modelAndView;
  // }

  @GetMapping("/login") 
  public String getLogin() {
      return "login";
  }

  @RequestMapping(value = "/registration", method = RequestMethod.GET)
  public ModelAndView registration() {
    ModelAndView modelAndView = new ModelAndView();
    User user = new User();
    modelAndView.addObject("user", user);
    modelAndView.setViewName("registration");
    return modelAndView;
  }

  // @RequestMapping(value = "/registration", method = RequestMethod.POST)
  // public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
  //   ModelAndView modelAndView = new ModelAndView();
  //   User userExists = userService.findUserByUsername(user.getUsername());
  //   if (userExists != null) {
  //     bindingResult.rejectValue("username", "error.user", "There is already a user registered with the username provided");
  //   }
  //   if (bindingResult.hasErrors()) {
  //     modelAndView.setViewName("registration");
  //   } else {
  //     userService.saveUser(user);
  //     modelAndView.addObject("successMessage", "User has been registered successfully");
  //     modelAndView.addObject("user", new User());
  //     modelAndView.setViewName("registration");

  //   }
  //   return modelAndView;
  // }

//  @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
//  public ModelAndView home() {
//    ModelAndView modelAndView = new ModelAndView();
//    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//    User user = (User) userService.loadUserByUsername(auth.getName());
//    modelAndView.addObject("userName", "Welcome " + user.getUsername());
//    modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
//    modelAndView.setViewName("admin/home");
//    return modelAndView;
//  }
  
  @GetMapping("/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response) {
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      if (auth != null) {
          new SecurityContextLogoutHandler().logout(request, response, auth);
      }
      return "login";
  }

}
