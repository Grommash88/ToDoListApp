package com.grommash88.app.controller;

import com.grommash88.app.model.User;
import com.grommash88.app.service.SecurityService;
import com.grommash88.app.service.TaskService;
import com.grommash88.app.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Api(value = "Работа с тасками")
public class UserController {

  private final UserService userService;

  private final SecurityService securityService;

  private final UserValidator userValidator;

  private final TaskService taskService;

  @GetMapping("/registration")
  public String registration(Model model) {

    if (securityService.isAuthenticated()) {
      return "redirect:/";
    }
    model.addAttribute("userForm", new User());
    return "registration";
  }

  @PostMapping("/registration")
  public String registration(@ModelAttribute("userForm") User userForm,
      BindingResult bindingResult) {

    userValidator.validate(userForm, bindingResult);
    if (bindingResult.hasErrors()) {
      return "registration";
    }
    userService.save(userForm);
    securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
    return "redirect:/welcome";
  }

  @GetMapping("/login")
  public String login(Model model, String error, String logout) {

    if (securityService.isAuthenticated()) {
      return "redirect:/";
    }
    if (error != null) {
      model.addAttribute("error", "Your username and password is invalid.");
    }
    if (logout != null) {
      model.addAttribute("message", "You have been logged out successfully.");
    }
    return "login";
  }

  @GetMapping({"/", "/welcome"})
  public String welcome(Model model) {

    if (model.getAttribute("tasks") == null) {
      model.addAttribute("tasks", taskService.getNoCompletedTasks(userService
          .findByUsername(securityService.getUserName()).getId()));
    }
    model.addAttribute("taskRequestDTO", taskService
        .getTaskRequestDto(userService.findByUsername(securityService.getUserName())));
    return "task_list";
  }
}
