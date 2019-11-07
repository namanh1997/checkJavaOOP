package com.example.check_java_oop.controller;

import com.example.check_java_oop.model.User;
import com.example.check_java_oop.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UploadController {

  @Autowired
  private UserService userService;

  @RequestMapping(value = "/upload", method = RequestMethod.GET)
  public ModelAndView upload() {
    ModelAndView modelAndView = new ModelAndView();
    User user = new User();
    modelAndView.addObject("user", user);
    modelAndView.setViewName("upload");
    return modelAndView;
  }  
}