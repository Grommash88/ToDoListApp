package com.grommash88.app.controller;

import com.grommash88.app.dto.TaskRequestDTO;
import com.grommash88.app.service.TaskManagerService;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.Optional;
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
import com.grommash88.app.model.Task;
import com.grommash88.app.service.ConverterDtoToModel;


@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/tasks")
public class WebController {

  private final TaskManagerService taskManagerService;

  @GetMapping
  public String index(Model model) {

    ArrayList<Task> tasks = (ArrayList<Task>) taskManagerService.getTasks();

    model.addAttribute("tasks", tasks);
    model.addAttribute("taskRequestDTO", new TaskRequestDTO());
    return "user_task_list";
  }

  @PostMapping("new")
  public String addTask(@ApiParam(value =
      "Объект класса TaskRequestDTO полученный от пользователя в теле запроса в виде JSON файла.",
      required = true)
  @ModelAttribute("taskRequestDTO") TaskRequestDTO taskRequestDTO) {
    Task newSavedTask = ConverterDtoToModel.convertDtoToModel(taskRequestDTO);

    taskManagerService.addTask(newSavedTask);
    return "redirect:/";
  }

  @GetMapping("task/{id}")
  public String getForm(
      @ApiParam(value = "id запрашиваемого таска", required = true, example = "41")
          Model model, @PathVariable Long id) {
    Optional<Task> optionalTask = taskManagerService.getTask(id);
    if (!optionalTask.isPresent()) {
      return "not found";
    }
    model.addAttribute("task", optionalTask.get());
    return "task";
  }

  @DeleteMapping("deleteTask/{id}")
  public String deleteTask(@PathVariable Long id) {
    taskManagerService.removeTask(id);

    return "redirect:/";
  }

  @PatchMapping("updateTask/{id}")
  public String updateTask(@ModelAttribute("task") Task task) {
    taskManagerService.updateTask(task.getId(), task.getDescription());
    return "redirect:/";
  }
}

