package com.grommash88.app.service;

import com.grommash88.app.model.dto.TaskRequestDTO;
import com.grommash88.app.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskManagerService {

  long addTask(TaskRequestDTO taskRequestDTO);

  Task getTask(Long id);

  List<Task> getTasks();

  boolean updateTask(Long id, String newDescription);

  boolean removeTask(Long id);

  boolean completeTask(long id);
}
