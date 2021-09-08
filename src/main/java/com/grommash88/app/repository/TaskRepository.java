package com.grommash88.app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.grommash88.app.model.Task;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

  List<Task> findAllByAuthorId(Long authorId);

  List<Task> findAllByAuthorIdAndIsCompleted(Long authorId, boolean completed);
}
