package com.grommash88.app;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.grommash88.app.controller.TaskController;
import com.grommash88.app.controller.UserController;
import com.grommash88.app.model.Task;
import com.grommash88.app.model.TaskRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql",
    "/create-tasks-before.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-user-after.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails("имячко")
public class TaskControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TaskController taskController;

  private final TaskRequestDTO expectedTaskRequestDTO = new TaskRequestDTO();

  private final List<Task> expectedTasks = new ArrayList<>();

  private final Task expectedTask = Task.builder()
      .id(11)
      .authorId(1)
      .description("some description")
      .isCompleted(false)
      .statusDescription("Active")
      .build();

  @BeforeEach
  private void initExpectedTasks() {

    Task taskOne = new Task();
    taskOne.setId(1);
    taskOne.setAuthorId(1);
    taskOne.setStatusDescription("Active");
    taskOne.setDescription("some task number one");
    taskOne.setCompleted(false);
    expectedTasks.add(taskOne);

    Task taskThree = new Task();
    taskThree.setId(3);
    taskThree.setAuthorId(1);
    taskThree.setStatusDescription("Active");
    taskThree.setDescription("some task number three");
    taskThree.setCompleted(false);
    expectedTasks.add(taskThree);

    Task taskFive = new Task();
    taskFive.setId(5);
    taskFive.setAuthorId(1);
    taskFive.setStatusDescription("Active");
    taskFive.setDescription("some task number five");
    taskFive.setCompleted(false);
    expectedTasks.add(taskFive);

    Task taskSeven = new Task();
    taskSeven.setId(7);
    taskSeven.setAuthorId(1);
    taskSeven.setStatusDescription("Active");
    taskSeven.setDescription("some task number seven");
    taskSeven.setCompleted(false);
    expectedTasks.add(taskSeven);

    Task taskNine = new Task();
    taskNine.setId(9);
    taskNine.setAuthorId(1);
    taskNine.setStatusDescription("Active");
    taskNine.setDescription("some task number nine");
    taskNine.setCompleted(false);
    expectedTasks.add(taskNine);

    expectedTaskRequestDTO.setAuthorId(1);
  }

  @Test
  public void loadContextTest() throws Exception {

    getWelcomePage(expectedTaskRequestDTO)
        .andExpect(model().attribute("tasks", expectedTasks))
        .andExpect(content().string(containsString("some task number one")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("some task number three")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("some task number five")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("some task number seven")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("some task number nine")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")));
  }

  @Test
  public void createNewTaskTest() throws Exception {

    createNewTask();

    expectedTasks.add(expectedTask);

    getWelcomePage(expectedTaskRequestDTO)
        .andExpect(model().attribute("tasks", expectedTasks))
        .andExpect(content().string(containsString("some description")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")));
  }

  @Test
  public void getTaskTest() throws Exception {

    this.mockMvc.perform(get("/tasks/task/3"))
        .andDo(print())
        .andExpect(handler().handlerType(TaskController.class))
        .andExpect(authenticated())
        .andExpect(view().name("task"))
        .andExpect(model().attributeExists("task"))
        .andExpect(model().attribute("task", expectedTasks.get(1)))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Task Description:")))
        .andExpect(content().string(containsString("some task number three")))
        .andExpect(content().string(containsString("Save")))
        .andExpect(content().string(containsString("Delete")));
  }

  @Test
  public void completedTaskTest() throws Exception {

    getWelcomePage(expectedTaskRequestDTO)
        .andExpect(model().attribute("tasks", expectedTasks))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")));
    completeTask(5L);
    expectedTasks.remove(2);
    getWelcomePage(expectedTaskRequestDTO)
        .andExpect(model().attribute("tasks", expectedTasks))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")));
  }

  @Test
  public void deleteTaskTest() throws Exception {
    expectedTasks.remove(1);
    deleteTask();
    getWelcomePage(expectedTaskRequestDTO)
        .andExpect(model().attribute("tasks", expectedTasks))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")));
  }

  @Test
  public void updateTaskTest() throws Exception {

    updateTask();
    expectedTasks.get(4).setDescription("new description task number nine");
    getWelcomePage(expectedTaskRequestDTO)
        .andExpect(model().attribute("tasks", expectedTasks))
        .andExpect(content().string(containsString("some task number one")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("some task number three")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("some task number five")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("some task number seven")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("new description task number nine")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")));
  }

  @Test
  public void getAllTasksTest() throws Exception {

    getWelcomePage(expectedTaskRequestDTO)
        .andExpect(model().attribute("tasks", expectedTasks))
        .andExpect(content().string(containsString("some task number one")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("some task number three")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("some task number five")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("some task number seven")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("some task number nine")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")));

    completeTask(Long.parseLong("1"));
    expectedTasks.get(0).setStatusDescription("Completed: " + DateTimeFormatter
        .ofPattern("dd.MM.yyyy HH:mm").format(LocalDateTime.now()));
    expectedTasks.get(0).setCompleted(true);
    completeTask(Long.parseLong("3"));
    expectedTasks.get(1).setStatusDescription("Completed: " + DateTimeFormatter
        .ofPattern("dd.MM.yyyy HH:mm").format(LocalDateTime.now()));
    expectedTasks.get(1).setCompleted(true);

    getAllTasks();

    this.mockMvc.perform(get("/welcome")
            .flashAttr("tasks", expectedTasks)
            .flashAttr("taskRequestDTO", new TaskRequestDTO())
            .with(csrf()))
        .andDo(print())
        .andExpect(handler().handlerType(UserController.class))
        .andExpect(authenticated())
        .andExpect(view().name("task_list"))
        .andExpect(model().attributeExists("taskRequestDTO"))
        .andExpect(model().attribute("taskRequestDTO", expectedTaskRequestDTO))
        .andExpect(model().attributeExists("tasks"))
        .andExpect(model().attribute("tasks", expectedTasks))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Show no completed tasks")))
        .andExpect(content().string(containsString("Show completed tasks")))
        .andExpect(content().string(containsString("Show all tasks")))
        .andExpect(content().string(containsString("Log out")))
        .andExpect(content().string(containsString("What's planned?")))
        .andExpect(content().string(containsString("Add to the list")))
        .andExpect(content().string(containsString("some task number one")))
        .andExpect(content().string(containsString("Completed: " + DateTimeFormatter
            .ofPattern("dd.MM.yyyy HH:mm").format(LocalDateTime.now()))))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("some task number three")))
        .andExpect(content().string(containsString("Completed: " + DateTimeFormatter
            .ofPattern("dd.MM.yyyy HH:mm").format(LocalDateTime.now()))))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("some task number five")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("some task number seven")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("some task number nine")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")));
  }

  @Test
  public void getNoCompletedTasksTest() throws Exception {

    getWelcomePage(expectedTaskRequestDTO)
        .andExpect(model().attribute("tasks", expectedTasks))
        .andExpect(content().string(containsString("some task number one")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("some task number three")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("some task number five")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("some task number seven")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("some task number nine")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")));

    completeTask(Long.parseLong("1"));
    expectedTasks.remove(0);
    completeTask(Long.parseLong("3"));
    expectedTasks.remove(0);

    getNoCompletedTasks();

    this.mockMvc.perform(get("/welcome")
            .flashAttr("tasks", expectedTasks)
            .flashAttr("taskRequestDTO", new TaskRequestDTO())
            .with(csrf()))
        .andDo(print())
        .andExpect(handler().handlerType(UserController.class))
        .andExpect(authenticated())
        .andExpect(view().name("task_list"))
        .andExpect(model().attributeExists("taskRequestDTO"))
        .andExpect(model().attribute("taskRequestDTO", expectedTaskRequestDTO))
        .andExpect(model().attributeExists("tasks"))
        .andExpect(model().attribute("tasks", expectedTasks))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Show no completed tasks")))
        .andExpect(content().string(containsString("Show completed tasks")))
        .andExpect(content().string(containsString("Show all tasks")))
        .andExpect(content().string(containsString("Log out")))
        .andExpect(content().string(containsString("What's planned?")))
        .andExpect(content().string(containsString("Add to the list")))
        .andExpect(content().string(containsString("some task number five")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("some task number seven")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("some task number nine")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")));
  }

  @Test
  public void getCompletedTasksTest() throws Exception {

    getWelcomePage(expectedTaskRequestDTO)
        .andExpect(model().attribute("tasks", expectedTasks))
        .andExpect(content().string(containsString("some task number one")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("some task number three")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("some task number five")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("some task number seven")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("some task number nine")))
        .andExpect(content().string(containsString("Active")))
        .andExpect(content().string(containsString("To complete")))
        .andExpect(content().string(containsString("Edit")));

    completeTask(Long.parseLong("1"));
    expectedTasks.get(0).setStatusDescription("Completed: " + DateTimeFormatter
        .ofPattern("dd.MM.yyyy HH:mm").format(LocalDateTime.now()));
    expectedTasks.get(0).setCompleted(true);
    completeTask(Long.parseLong("3"));
    expectedTasks.get(1).setStatusDescription("Completed: " + DateTimeFormatter
        .ofPattern("dd.MM.yyyy HH:mm").format(LocalDateTime.now()));
    expectedTasks.get(1).setCompleted(true);
    expectedTasks.remove(2);
    expectedTasks.remove(2);
    expectedTasks.remove(2);

    getCompletedTasks();

    this.mockMvc.perform(get("/welcome")
            .flashAttr("tasks", expectedTasks)
            .flashAttr("taskRequestDTO", new TaskRequestDTO())
            .with(csrf()))
        .andDo(print())
        .andExpect(handler().handlerType(UserController.class))
        .andExpect(authenticated())
        .andExpect(view().name("task_list"))
        .andExpect(model().attributeExists("taskRequestDTO"))
        .andExpect(model().attribute("taskRequestDTO", expectedTaskRequestDTO))
        .andExpect(model().attributeExists("tasks"))
        .andExpect(model().attribute("tasks", expectedTasks))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Show no completed tasks")))
        .andExpect(content().string(containsString("Show completed tasks")))
        .andExpect(content().string(containsString("Show all tasks")))
        .andExpect(content().string(containsString("Log out")))
        .andExpect(content().string(containsString("What's planned?")))
        .andExpect(content().string(containsString("Add to the list")))
        .andExpect(content().string(containsString("some task number one")))
        .andExpect(content().string(containsString("Completed: " + DateTimeFormatter
            .ofPattern("dd.MM.yyyy HH:mm").format(LocalDateTime.now()))))
        .andExpect(content().string(containsString("Edit")))
        .andExpect(content().string(containsString("some task number three")))
        .andExpect(content().string(containsString("Completed: " + DateTimeFormatter
            .ofPattern("dd.MM.yyyy HH:mm").format(LocalDateTime.now()))))
        .andExpect(content().string(containsString("Edit")));
  }

  private ResultActions getWelcomePage(TaskRequestDTO expectedTaskRequestDTO) throws Exception {

    return this.mockMvc.perform(get("/welcome"))
        .andDo(print())
        .andExpect(handler().handlerType(UserController.class))
        .andExpect(authenticated())
        .andExpect(view().name("task_list"))
        .andExpect(model().attributeExists("taskRequestDTO"))
        .andExpect(model().attribute("taskRequestDTO", expectedTaskRequestDTO))
        .andExpect(model().attributeExists("tasks"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Show no completed tasks")))
        .andExpect(content().string(containsString("Show completed tasks")))
        .andExpect(content().string(containsString("Show all tasks")))
        .andExpect(content().string(containsString("Log out")))
        .andExpect(content().string(containsString("What's planned?")))
        .andExpect(content().string(containsString("Add to the list")));
  }

  private void createNewTask() throws Exception {

    this.mockMvc.perform(post("/tasks/new")
            .param("description", "some description")
            .param("authorId", "1")
            .flashAttr("taskRequestDTO", new TaskRequestDTO())
            .with(csrf()))
        .andDo(print())
        .andExpect(handler().handlerType(TaskController.class))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/welcome"));
  }

  private void completeTask(Long id) throws Exception {

    this.mockMvc.perform(patch(String.format("/tasks/task/complete/%d", id))
            .with(csrf()))
        .andDo(print())
        .andExpect(handler().handlerType(TaskController.class))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/welcome"));
  }

  private void deleteTask() throws Exception {

    this.mockMvc.perform(delete("/tasks/deleteTask/3")
            .with(csrf()))
        .andDo(print())
        .andExpect(handler().handlerType(TaskController.class))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/welcome"));
  }

  private void updateTask() throws Exception {

    this.mockMvc.perform(patch("/tasks/updateTask/9")
            .param("description", "new description task number nine")
            .flashAttr("task", new Task())
            .with(csrf()))
        .andDo(print())
        .andExpect(handler().handlerType(TaskController.class))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/welcome"));
  }

  private void getAllTasks() throws Exception {

    this.mockMvc.perform(get("/tasks/all_tasks")
            .flashAttr("authorId", 1L)
            .with(csrf()))
        .andDo(print())
        .andExpect(handler().handlerType(TaskController.class))
        .andExpect(authenticated())
        .andExpect(flash().attribute("tasks", expectedTasks))
        .andExpect(redirectedUrl("/welcome"));
  }

  private void getNoCompletedTasks() throws Exception {

    this.mockMvc.perform(get("/tasks/no_completed_tasks")
            .flashAttr("authorId", 1L)
            .with(csrf()))
        .andDo(print())
        .andExpect(handler().handlerType(TaskController.class))
        .andExpect(authenticated())
        .andExpect(flash().attribute("tasks", expectedTasks))
        .andExpect(redirectedUrl("/welcome"));
  }

  private void getCompletedTasks() throws Exception {

    this.mockMvc.perform(get("/tasks/completed_tasks")
            .flashAttr("authorId", 1L)
            .with(csrf()))
        .andDo(print())
        .andExpect(handler().handlerType(TaskController.class))
        .andExpect(authenticated())
        .andExpect(flash().attribute("tasks", expectedTasks))
        .andExpect(redirectedUrl("/welcome"));
  }
}
