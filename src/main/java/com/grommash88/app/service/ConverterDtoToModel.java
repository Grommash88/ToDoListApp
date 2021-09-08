package com.grommash88.app.service;

import com.grommash88.app.model.Task;
import com.grommash88.app.model.TaskRequestDTO;

public class ConverterDtoToModel {

  public static Task convertDtoToModel(TaskRequestDTO taskRequestDTO) {

    return Task.builder()
        .description(taskRequestDTO.getDescription())
        .authorId(taskRequestDTO.getAuthorId())
        .isCompleted(false)
        .completedDate("Активен")
        .build();
  }
}