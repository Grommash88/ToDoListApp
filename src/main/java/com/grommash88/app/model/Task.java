package com.grommash88.app.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Table(name = "task")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ApiModel(value = "class Task", description =
    "Сущность сохраняемая в базу данных и получаемая оттуда,"
        + " результат работы метода convertDtoToModel, класса ConverterDtoToModel,"
        + " принимающего на вход аргумент TaskRequestDTO. Все поля соответствуют классу TaskRequestDTO,"
        + " кроме id который, генерируется автоматически.")
public class Task {

  // Переменная для хранения значения уникального ключа.
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @ApiModelProperty(value = "id таска", example = "88")
  private long id;

  // Заголовок.
  @ApiModelProperty(value = "Имя таска", example = "Сходить в Мфц")
  private String name;

  // Описание.
  @ApiModelProperty(value = "Описание таска",
      example = "Сходить в мфц подать документы на замену паспорта")
  private String description;


}
