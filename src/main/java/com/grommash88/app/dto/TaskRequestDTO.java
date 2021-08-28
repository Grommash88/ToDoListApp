package com.grommash88.app.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "class TaskRequestDTO")
//Тело запроса Таска
public class TaskRequestDTO {

  // Имя Таска
  @ApiModelProperty(value = "Имя таска", example = "Сходить в Мфц")
  private String name;

  //Описание Таска
  @ApiModelProperty(value = "Описание таска", example = "Сходить в мфц подать документы на замену паспорта")
  private String description;
}
