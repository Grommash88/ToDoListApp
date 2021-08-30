package com.grommash88.app.service.impl;

import com.grommash88.app.model.Task;
import com.grommash88.app.model.dto.TaskRequestDTO;
import com.grommash88.app.repository.TaskRepository;
import com.grommash88.app.service.ConverterDtoToModel;
import com.grommash88.app.service.TaskManagerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskManagerServiceImpl implements TaskManagerService {

  private final TaskRepository taskRepository;

  @Override
  public long addTask(TaskRequestDTO taskRequestDTO) {
    Task task = ConverterDtoToModel.convertDtoToModel(taskRequestDTO);
    taskRepository.save(task);
    return task.getId();
  }


  @Override
  public Task getTask(Long id) {
    return taskRepository.findById(id).orElseThrow(() ->
        new IllegalArgumentException(String.format("Событие с id=%d не найдено.", id)));
  }

  @Override
  public List<Task> getTasks() {
    return taskRepository.findAll();
  }

  @Override
  public boolean updateTask(Long id, String newDescription) {

    if (taskRepository.findById(id).isEmpty()) {
      throw new IllegalArgumentException(String.format("Событие с id=%d не найдено.", id));
    }
    Task updatingTask = taskRepository.findById(id).get();
    updatingTask.setDescription(newDescription);
    taskRepository.save(updatingTask);
    return true;

  }

  @Override
  public boolean removeTask(Long id) {

    if (taskRepository.findById(id).isPresent()) {
      taskRepository.delete(taskRepository.findById(id).get());
      return true;
    } else {
      throw new IllegalArgumentException(String.format("Событие с id=%d не найдено.", id));
    }
  }

  @Override
  public boolean completeTask(long id) {
    if (taskRepository.findById(id).isEmpty()) {
      throw new IllegalArgumentException(String.format("Событие с id=%d не найдено.", id));
    }
    Task updatingTask = taskRepository.findById(id).get();
    updatingTask.setCompleted(true);
    taskRepository.save(updatingTask);
    return true;
  }
}
