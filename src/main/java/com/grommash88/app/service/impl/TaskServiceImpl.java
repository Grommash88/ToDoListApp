package com.grommash88.app.service.impl;

import com.grommash88.app.exception.NotFoundException;
import com.grommash88.app.model.Task;
import com.grommash88.app.model.TaskRequestDTO;
import com.grommash88.app.model.User;
import com.grommash88.app.repository.TaskRepository;
import com.grommash88.app.service.ConverterDtoToModel;
import com.grommash88.app.service.TaskService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;

  private final String expMsgPattern = "Task with id =% d not found.";

  @Override
  public void addTask(TaskRequestDTO taskRequestDTO) {

    Task task = ConverterDtoToModel.convertDtoToModel(taskRequestDTO);
    taskRepository.save(task);
  }

  @Override
  public Task getTask(Long id) {

    return taskRepository.findById(id).orElseThrow(() ->
        new NotFoundException(String.format(expMsgPattern, id)));
  }

  @Override
  public List<Task> getTasks(Long authorId) {
    return taskRepository.findAllByAuthorId(authorId);
  }

  @Override
  public List<Task> getNoCompletedTasks(Long authorId) {

    return taskRepository.findAllByAuthorIdAndIsCompleted(authorId, false);
  }

  @Override
  public List<Task> getCompletedTasks(Long authorId) {

    return taskRepository.findAllByAuthorIdAndIsCompleted(authorId, true);
  }

  @Override
  public TaskRequestDTO getTaskRequestDto(User user) {

    TaskRequestDTO taskRequestDTO = new TaskRequestDTO();
    taskRequestDTO.setAuthorId(user.getId());
    return taskRequestDTO;
  }

  @Override
  public void updateTask(Long id, String newDescription) {

    if (taskRepository.findById(id).isEmpty()) {
      throw new NotFoundException(String.format(expMsgPattern, id));
    }
    Task updatingTask = taskRepository.findById(id).get();
    updatingTask.setDescription(newDescription);
    taskRepository.save(updatingTask);
  }

  @Override
  public void removeTask(Long id) {

    if (taskRepository.findById(id).isPresent()) {
      taskRepository.delete(taskRepository.findById(id).get());
    } else {
      throw new NotFoundException(String.format(expMsgPattern, id));
    }
  }

  @Override
  public void completeTask(long id) {

    if (taskRepository.findById(id).isEmpty()) {
      throw new NotFoundException(String.format(expMsgPattern, id));
    }
    Task updatingTask = taskRepository.findById(id).get();
    updatingTask.setCompleted(true);
    updatingTask.setStatusDescription("Completed: " + DateTimeFormatter
        .ofPattern("dd.MM.yyyy HH:mm").format(LocalDateTime.now()));
    taskRepository.save(updatingTask);
  }
}
