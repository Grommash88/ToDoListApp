package com.grommash88.app;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.grommash88.app.controller.UserController;
import com.grommash88.app.model.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-user-after.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class RegistrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserController userController;

  @Test
  public void contextLoadsTest() throws Exception {

    User expectedUser = new User();

    this.mockMvc.perform(get("/registration"))
        .andDo(print())
        .andExpect(handler().handlerType(UserController.class))
        .andExpect(status().isOk())
        .andExpect(view().name("registration"))
        .andExpect(model().attributeExists("userForm"))
        .andExpect(model().attribute("userForm", expectedUser))
        .andExpect(content().string(containsString("username")))
        .andExpect(content().string(containsString("password")))
        .andExpect(content().string(containsString("passwordConfirm")))
        .andExpect(content().string(containsString("Submit")));
  }

  @Test
  public void badCredentialsTest() throws Exception {

    this.mockMvc.perform(post("/registration")
            .param("username", "имячко")
            .param("password", "12345678")
            .param("passwordConfirm", "12345678")
            .flashAttr("userForm", new User())
            .with(csrf()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("registration"))
        .andExpect(handler().handlerType(UserController.class))
        .andExpect(content().string(containsString("Someone already has that username.")))
        .andExpect(content().string(containsString("username")))
        .andExpect(content().string(containsString("password")))
        .andExpect(content().string(containsString("passwordConfirm")))
        .andExpect(content().string(containsString("Create an account")));

    this.mockMvc.perform(post("/registration")
            .param("username", "")
            .param("password", "12345678")
            .param("passwordConfirm", "12345678")
            .flashAttr("userForm", new User())
            .with(csrf()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("registration"))
        .andExpect(handler().handlerType(UserController.class))
        .andExpect(content().string(containsString("This field is required.")))
        .andExpect(content().string(containsString("username")))
        .andExpect(content().string(containsString("password")))
        .andExpect(content().string(containsString("passwordConfirm")))
        .andExpect(content().string(containsString("Create an account")));

    this.mockMvc.perform(post("/registration")
            .param("username", "новый пользователь")
            .param("password", "")
            .param("passwordConfirm", "12345678")
            .flashAttr("userForm", new User())
            .with(csrf()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("registration"))
        .andExpect(handler().handlerType(UserController.class))
        .andExpect(content().string(containsString("These passwords don&#39;t match.")))
        .andExpect(content().string(containsString("username")))
        .andExpect(content().string(containsString("password")))
        .andExpect(content().string(containsString("passwordConfirm")))
        .andExpect(content().string(containsString("Create an account")));

    this.mockMvc.perform(post("/registration")
            .param("username", "новый пользователь")
            .param("password", "12345678")
            .param("passwordConfirm", "")
            .flashAttr("userForm", new User())
            .with(csrf()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("registration"))
        .andExpect(handler().handlerType(UserController.class))
        .andExpect(content().string(containsString("These passwords don&#39;t match.")))
        .andExpect(content().string(containsString("username")))
        .andExpect(content().string(containsString("password")))
        .andExpect(content().string(containsString("passwordConfirm")))
        .andExpect(content().string(containsString("Create an account")));

    this.mockMvc.perform(post("/registration")
            .param("username", "новый пользователь")
            .param("password", "12345678")
            .param("passwordConfirm", "87654321")
            .flashAttr("userForm", new User())
            .with(csrf()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("registration"))
        .andExpect(handler().handlerType(UserController.class))
        .andExpect(content().string(containsString("These passwords don&#39;t match.")))
        .andExpect(content().string(containsString("username")))
        .andExpect(content().string(containsString("password")))
        .andExpect(content().string(containsString("passwordConfirm")))
        .andExpect(content().string(containsString("Create an account")));

    this.mockMvc.perform(post("/registration")
            .param("username", "имячк")
            .param("password", "12345678")
            .param("passwordConfirm", "12345678")
            .flashAttr("userForm", new User())
            .with(csrf()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("registration"))
        .andExpect(handler().handlerType(UserController.class))
        .andExpect(content()
            .string(containsString("Please use between 6 and 32 characters.")))
        .andExpect(content().string(containsString("username")))
        .andExpect(content().string(containsString("password")))
        .andExpect(content().string(containsString("passwordConfirm")))
        .andExpect(content().string(containsString("Create an account")));

    this.mockMvc.perform(post("/registration")
            .param("username", "имячко123456789876543212345678987")
            .param("password", "12345678")
            .param("passwordConfirm", "12345678")
            .flashAttr("userForm", new User())
            .with(csrf()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("registration"))
        .andExpect(handler().handlerType(UserController.class))
        .andExpect(content()
            .string(containsString("Please use between 6 and 32 characters.")))
        .andExpect(content().string(containsString("username")))
        .andExpect(content().string(containsString("password")))
        .andExpect(content().string(containsString("passwordConfirm")))
        .andExpect(content().string(containsString("Create an account")));

    this.mockMvc.perform(post("/registration")
            .param("username", "имячко")
            .param("password", "1234567")
            .param("passwordConfirm", "1234567")
            .flashAttr("userForm", new User())
            .with(csrf()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("registration"))
        .andExpect(handler().handlerType(UserController.class))
        .andExpect(content().string(containsString("Try one with at least 8 characters.")))
        .andExpect(content().string(containsString("username")))
        .andExpect(content().string(containsString("password")))
        .andExpect(content().string(containsString("passwordConfirm")))
        .andExpect(content().string(containsString("Create an account")));
  }

  @Test
  public void correctRegistrationTest() throws Exception {

    this.mockMvc.perform(post("/registration")
            .param("username", "новый пользователь")
            .param("password", "12345678")
            .param("passwordConfirm", "12345678")
            .flashAttr("userForm", new User())
            .with(csrf()))
        .andDo(print())
        .andExpect(handler().handlerType(UserController.class))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/welcome"))
        .andExpect(redirectedUrl("/welcome"));
  }
}

