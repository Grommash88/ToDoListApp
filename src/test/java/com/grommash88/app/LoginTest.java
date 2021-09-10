package com.grommash88.app;


import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.grommash88.app.controller.UserController;
import com.grommash88.app.model.TaskRequestDTO;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-user-after.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class LoginTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserController userController;

  @Test
  public void contextLoadsTest() throws Exception {

    this.mockMvc.perform(MockMvcRequestBuilders.get("/login"))
        .andDo(print())
        .andExpect(handler().handlerType(UserController.class))
        .andExpect(status().isOk())
        .andExpect(view().name("login"))
        .andExpect(content().string(containsString("username")))
        .andExpect(content().string(containsString("password")))
        .andExpect(content().string(containsString("Log In")))
        .andExpect(content().string(containsString("Create an account")));
  }

  @Test
  public void accessDeniedTest() throws Exception {

    this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
        .andDo(print())
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("http://localhost/login"));

    this.mockMvc.perform(MockMvcRequestBuilders.get("/welcome"))
        .andDo(print())
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("http://localhost/login"));

    this.mockMvc.perform(MockMvcRequestBuilders.get("/tasks/updateTask/1"))
        .andDo(print())
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("http://localhost/login"));
  }

  @Test
  public void correctLoginTest() throws Exception {

    this.mockMvc.perform(formLogin().user("имячко").password("12345678"))
        .andDo(print())
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/"));
  }

  @Test
  public void badCredentialsTest() throws Exception {

    this.mockMvc.perform(formLogin().user("неимячко").password("12345678"))
        .andDo(print()).andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/login?error"));
  }

  @Test
  @WithUserDetails("имячко")
  public void welcomePageTest() throws Exception {

    TaskRequestDTO expectedTaskRequestDTO = new TaskRequestDTO();
    expectedTaskRequestDTO.setAuthorId(1);

    this.mockMvc.perform(MockMvcRequestBuilders.get("/welcome"))
        .andDo(print())
        .andExpect(handler().handlerType(UserController.class))
        .andExpect(authenticated())
        .andExpect(view().name("task_list"))
        .andExpect(model().attributeExists("taskRequestDTO"))
        .andExpect(model().attribute("taskRequestDTO", expectedTaskRequestDTO))
        .andExpect(model().attributeExists("tasks"))
        .andExpect(status().isOk());
  }
}
