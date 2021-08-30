package com.grommash88.app.controller;

import com.grommash88.app.model.dto.LoginRequestDto;
import com.grommash88.app.model.dto.TaskRequestDTO;
import com.grommash88.app.model.dto.UserDto;
import com.grommash88.app.model.User;
import com.grommash88.app.service.impl.UserManagerServiceImpl;
import java.util.Objects;
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

  private final UserManagerServiceImpl userManagerServiceImpl;

  @GetMapping("/registration")
  public String getRegistrationForm(Model model) {
    model.addAttribute("userDto", new UserDto());
    return "registration";
  }

  @PostMapping("/registration")
  public String doRegistration(@ModelAttribute("userDto") UserDto userDto) {
    userManagerServiceImpl.saveUser(userDto);
    return "redirect:/";
  }

  @GetMapping("/login")
  public String doLogin(@ModelAttribute("loginRequest") LoginRequestDto loginRequestDto, Model model) {
    model.addAttribute("user", userManagerServiceImpl.login(loginRequestDto));
    model.addAttribute("taskRequestDTO", new TaskRequestDTO());
    model.addAttribute("tasks", userManagerServiceImpl.getNotCompleteTasks(
        (User) Objects.requireNonNull(model.getAttribute("user"))));
    return "user_task_list";
  }

  @GetMapping("/update-page")
  public String updatePage(Model model) {
    model.addAttribute("user", userManagerServiceImpl.login(
        (LoginRequestDto) Objects.requireNonNull(model.getAttribute("loginRequest"))));
    model.addAttribute("taskRequestDTO", new TaskRequestDTO());
    model.addAttribute("tasks", userManagerServiceImpl.getNotCompleteTasks(
        (User) Objects.requireNonNull(model.getAttribute("user"))));
    return "user_task_list";
  }
}
