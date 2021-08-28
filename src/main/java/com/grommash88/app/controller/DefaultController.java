package com.grommash88.app.controller;

import com.grommash88.app.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/")
public class DefaultController {

  @GetMapping
  public String index() {
    return "index";
  }

}
