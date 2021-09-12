package com.grommash88.app.controller;

import com.grommash88.app.model.Task;
import com.grommash88.app.model.TaskRequestDTO;
import com.grommash88.app.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/tasks")
public class TaskController {

  private final TaskService taskService;

  @PostMapping("/new")
  public String addTask(@ModelAttribute("taskRequestDTO") TaskRequestDTO taskRequestDTO) {

    taskService.addTask(taskRequestDTO);
    return "redirect:/welcome";
  }

  @GetMapping("/task/{id}")
  public String getForm(Model model, @PathVariable Long id) {

    model.addAttribute("task", taskService.getTask(id));
    return "task";
  }

  @GetMapping("/no_completed_tasks")
  public String getNoCompletedTasks(@ModelAttribute("authorId") Long authorId,
      RedirectAttributes redirectAttributes) {

    redirectAttributes
        .addFlashAttribute("tasks", taskService.getNoCompletedTasks(authorId));
    return "redirect:/welcome";
  }

  @GetMapping("/completed_tasks")
  public String getCompletedTasks(@ModelAttribute("authorId") Long authorId,
      RedirectAttributes redirectAttributes) {

    redirectAttributes
        .addFlashAttribute("tasks", taskService.getCompletedTasks(authorId));
    return "redirect:/welcome";
  }

  @GetMapping("/all_tasks")
  public String getAllTasks(@ModelAttribute("authorId") Long authorId,
      RedirectAttributes redirectAttributes) {

    redirectAttributes
        .addFlashAttribute("tasks", taskService.getTasks(authorId));
    return "redirect:/welcome";
  }

  @DeleteMapping("/deleteTask/{id}")
  public String deleteTask(@PathVariable Long id) {

    taskService.removeTask(id);
    return "redirect:/welcome";
  }

  @PatchMapping("updateTask/{id}")
  public String updateTask(@ModelAttribute("task") Task task, @PathVariable long id) {

    taskService.updateTask(id, task.getDescription());
    return "redirect:/welcome";
  }

  @PatchMapping("/task/complete/{id}")
  public String completeTask(@PathVariable long id) {

    taskService.completeTask(id);
    return "redirect:/welcome";
  }
}