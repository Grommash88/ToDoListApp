package com.grommash88.app.model;

import com.sun.istack.NotNull;
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
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator="task_seq")
  @GenericGenerator(name = "task_seq", strategy="increment")
  private long id;

  @NotNull
  private String description;

  @Column(name = "author_id")
  @NotNull
  private long authorId;

  @NotNull
  private boolean isCompleted;

  @NotNull
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
