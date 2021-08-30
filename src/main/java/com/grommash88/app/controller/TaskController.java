package com.grommash88.app.controller;

import com.grommash88.app.model.Task;
import com.grommash88.app.model.dto.TaskRequestDTO;
import com.grommash88.app.service.TaskManagerService;
import com.grommash88.app.service.impl.UserManagerServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Api(value = "/tasks", description = "Api для работы с тасками")
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/tasks")
public class TaskController {

  private final TaskManagerService taskManagerService;
  private final UserManagerServiceImpl userManagerServiceImpl;

  @GetMapping("/new")
  public String addTask(@ApiParam(value =
          "Объект класса TaskRequestDTO полученный от пользователя в теле запроса в виде JSON файла.",
          required = true)
      @ModelAttribute("taskRequestDTO") TaskRequestDTO taskRequestDTO,
      RedirectAttributes redirectAttributes) {
    taskManagerService.addTask(taskRequestDTO);
    redirectAttributes.addFlashAttribute("loginRequest",
        userManagerServiceImpl.getLoginRequestById(taskRequestDTO.getAuthorId()));
    return "redirect:/authentification/update-page";
  }

  @GetMapping("task/{id}")
  public String getForm(
      @ApiParam(value = "id запрашиваемого таска", required = true, example = "41")
      Model model, @PathVariable Long id) {
    model.addAttribute("task", taskManagerService.getTask(id));
    return "task";
  }

  @DeleteMapping("deleteTask/{id}")
  public String deleteTask(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("loginRequest", userManagerServiceImpl
        .getLoginRequestById(taskManagerService.getTask(id).getAuthorId()));
    taskManagerService.removeTask(id);
    return "redirect:/authentification/update-page";
  }

  @PatchMapping("updateTask/{id}")
  public String updateTask(@ModelAttribute("task") Task task, @PathVariable long id,
      RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("loginRequest", userManagerServiceImpl
        .getLoginRequestById(taskManagerService.getTask(id).getAuthorId()));
    taskManagerService.updateTask(id, task.getDescription());
    return "redirect:/authentification/update-page";
  }

  @PatchMapping("/task/complete/{id}")
  public String completeTask(@PathVariable long id, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("loginRequest", userManagerServiceImpl
        .getLoginRequestById(taskManagerService.getTask(id).getAuthorId()));
    taskManagerService.completeTask(id);
    return "redirect:/authentification/update-page";
  }

}

