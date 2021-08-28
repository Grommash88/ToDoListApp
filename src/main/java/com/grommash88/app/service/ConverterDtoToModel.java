package com.grommash88.app.service;

import com.grommash88.app.dto.TaskRequestDTO;
import com.grommash88.app.model.Task;

public class ConverterDtoToModel {

  public static Task convertDtoToModel(TaskRequestDTO taskRequestDTO) {
    return Task.builder()
        .name(taskRequestDTO.getName())
        .description(taskRequestDTO.getDescription())
        .build();
  }
}
