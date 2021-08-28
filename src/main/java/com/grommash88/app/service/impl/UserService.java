package com.grommash88.app.service.impl;

import com.grommash88.app.dto.UserDto;
import com.grommash88.app.model.User;
import com.grommash88.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

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
}
