package com.grommash88.app.service.impl;

import com.grommash88.app.model.Task;
import com.grommash88.app.model.User;
import com.grommash88.app.model.dto.LoginRequestDto;
import com.grommash88.app.model.dto.UserDto;
import com.grommash88.app.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManagerServiceImpl {

  private final UserRepository userRepository;

  public void saveUser(UserDto userDto) {

    if (userDto.getPassword().equals(userDto.getRepeatPassword())) {
      userRepository.save(User.builder()
          .name(userDto.getName())
          .password(userDto.getPassword())
          .build());
    } else {
      throw new IllegalArgumentException("Указанные пароли должны совпадать.");
    }
  }

  public User login(LoginRequestDto loginRequestDto) {

    String userName = loginRequestDto.getName();
    String password = loginRequestDto.getPassword();

    User user = userRepository.findByName(userName).orElseThrow(() ->
        new IllegalArgumentException(String
            .format("Пользователь с именем: %s не зарегистрирован.", userName)));

    if (user.getPassword().equals(password)) {
      return user;
    } else {
      throw new IllegalArgumentException("Неверный пароль.");
    }
  }

  public LoginRequestDto getLoginRequestById(long id) {
    User user = userRepository.getById(id);
    return LoginRequestDto.builder().name(user.getName()).password(user.getPassword()).build();
  }

  public List<Task> getNotCompleteTasks(User user) {
    return user.getTasks().stream()
        .filter(t -> !t.isCompleted())
        .collect(Collectors.toList());
  }
}
