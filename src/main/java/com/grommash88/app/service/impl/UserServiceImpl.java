package com.grommash88.app.service.impl;

import com.grommash88.app.model.Role;
import com.grommash88.app.model.User;

//import com.grommash88.app.repository.RoleRepository;
import com.grommash88.app.repository.UserRepository;
import com.grommash88.app.service.UserService;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

//  private final RoleRepository roleRepository;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public void save(User user) {

    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    user.setRoles(Collections.singleton(Role.USER));
    userRepository.save(user);
  }

  @Override
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

}
