package com.grommash88.app.model;

import com.sun.istack.NotNull;
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
@ApiModel(value = "class TaskRequestDTO", description = "Обьект для транспортировки данных от "
    + "пользователя к сервису, для зодания записи о новом запланированном событии")
public class TaskRequestDTO {

  @NotNull
  @ApiModelProperty(value = "Описание события",  example = "выучить java")
  private String description;

  @NotNull
  @ApiModelProperty(value = "id автора и владельца записи",  example = "13")
  private long authorId;
}

