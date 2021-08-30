package com.grommash88.app.controller;

import com.grommash88.app.model.dto.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/")
public class DefaultController {

  @GetMapping
  public String index( Model model) {
    model.addAttribute("loginRequest", new LoginRequestDto());
    return "index";
  }
}
