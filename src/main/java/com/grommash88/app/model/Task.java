package com.grommash88.app.model;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "task")
@Builder
@ApiModel(value = "class Task", description = "Сущьнось моделирующая некое запланированное событие.")
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator="task_seq")
  @GenericGenerator(name = "task_seq", strategy="increment")
  @ApiModelProperty(value = "id таска",  example = "88")
  private long id;

  @NotNull
  @ApiModelProperty(value = "описание запланированного события",  example = "выучить java")
  private String description;

  @Column(name = "author_id")
  @NotNull
  @ApiModelProperty(value = "id автора и владельца записи",  example = "13")
  private long authorId;

  @NotNull
  @ApiModelProperty(value = "флаг завершенности",  example = "false")
  private boolean isCompleted;

  @NotNull
  @ApiModelProperty(value = "описание статуса таска",  example = "Active")
  private String statusDescription;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Task task = (Task) o;
    return Objects.equals(id, task.id);
  }

  @Override
  public int hashCode() {
    return 0;
  }
}
