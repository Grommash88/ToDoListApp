package com.grommash88.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Users", indexes = {
    @Index(name = "usernameindex", columnList = "name", unique = true)})
@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "class User", description =
    "Сущность сохраняемая в базу данных и получаемая оттуда,"
        + "описывает пользователя приложения, поля name и password соответствуют полям класса UserDto,"
        + "поле userId генерируется БД, поле tasks является списком не завершенных тасков,"
        + "сопостовляется с таблицей tasks по полю author_id")

public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ApiModelProperty(value = "id пользователя", example = "88")
  private Long userId;

  @ApiModelProperty(value = "Уникальное имя пользователя", example = "grommash")
  private String name;

  @ApiModelProperty(value = "Пароль от учетной записи, не короче 8 символов", example = "87654321")
  private String password;

  @ApiModelProperty(value = "Список незавершенных дел,"
      +  "сопостовляется с таблицей tasks по полю author_id ")
  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id")
  @JsonIgnore
  private List<Task> tasks;
}
