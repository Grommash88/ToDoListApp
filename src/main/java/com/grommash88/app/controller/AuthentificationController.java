package com.grommash88.app.controller;

import com.grommash88.app.dto.LoginRequest;
import com.grommash88.app.dto.UserDto;
import com.grommash88.app.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/authentification")
public class AuthentificationController {

  private final UserService userService;

  @GetMapping("/registration")
  public String getRegistrationForm(Model model) {

    model.addAttribute("userDto", new UserDto());
    return "registration";
  }

  @GetMapping("/login")
  public String getLoginForm(Model model) {

    model.addAttribute("loginRequest", new LoginRequest());
    return "login";
  }

  @PostMapping("/registration")
  public String doRegistration(@ModelAttribute("userDto") UserDto userDto) {


    userService.saveUser(userDto);
    return "login";
  }

}
