package com.grommash88.app;

import static org.assertj.core.api.Assertions.assertThat;

import com.grommash88.app.controller.TaskController;
import com.grommash88.app.controller.UserController;
import com.grommash88.app.controller.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class TaskTrackerApplicationTest {

  @Autowired
  private UserController userController;

  @Autowired
  private UserValidator userValidator;

  @Autowired
  private TaskController taskController;

  @Test
  void contextLoads() throws Exception {

    assertThat(userController).isNotNull();
    assertThat(userValidator).isNotNull();
    assertThat(taskController).isNotNull();
  }
}
