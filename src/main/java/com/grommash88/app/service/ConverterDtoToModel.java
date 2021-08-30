package com.grommash88.app.service;

import com.grommash88.app.model.dto.TaskRequestDTO;
import com.grommash88.app.model.Task;

public class ConverterDtoToModel {

  public static Task convertDtoToModel(TaskRequestDTO taskRequestDTO) {
    System.out.println(taskRequestDTO.toString());
    return Task.builder()
        .description(taskRequestDTO.getDescription())
        .authorId(taskRequestDTO.getAuthorId())
        .isCompleted(false)
        .build();
  }
}
