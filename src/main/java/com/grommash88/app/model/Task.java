package com.grommash88.app.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
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
import org.springframework.boot.context.properties.bind.DefaultValue;

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
        + " принимающего на вход аргумент TaskRequestDTO. Все поля соответствуют классу TaskRequestDTO"
        + " кроме, id который, генерируется автоматически и isCompleted, по умолчанию равного false.")
public class Task {


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @ApiModelProperty(value = "id таска", example = "88")
  private long id;


  @ApiModelProperty(value = "Описание таска",
      example = "Сходить в мфц подать документы на замену паспорта")
  private String description;

  @ApiModelProperty(value = "id автора таска.",
      example = "77")
  @Column(name = "author_id")
  private long authorId;

  @ApiModelProperty(value = "Флаг завершенности таска, по дефолту равен false",
      example = "false")
  private boolean isCompleted;
}
