package com.grommash88.app.service;

import com.grommash88.app.model.TaskRequestDTO;
import com.grommash88.app.model.Task;

import com.grommash88.app.model.User;
import java.util.List;

public interface TaskService {

  void addTask(TaskRequestDTO taskRequestDTO);

  Task getTask(Long id);

  List<Task> getTasks(Long authorId);

  List<Task> getNoCompletedTasks(Long authorId);

  List<Task> getCompletedTasks(Long authorId);

  TaskRequestDTO getTaskRequestDto(User user);

  void updateTask(Long id, String newDescription);

  void removeTask(Long id);

  void completeTask(long id);
}
