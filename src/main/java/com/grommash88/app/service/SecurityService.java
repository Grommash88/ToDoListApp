package com.grommash88.app.service;

import com.grommash88.app.model.Role;
import com.grommash88.app.model.User;
import com.grommash88.app.repository.UserRepository;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface SecurityService {

  boolean isAuthenticated();

  String getUserName();

  void autoLogin(String username, String password);

  @Service
  @RequiredArgsConstructor
  class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {

      User user = userRepository.findByUsername(username);
      if (user == null) {
        throw new UsernameNotFoundException(username);
      }
      Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
      for (Role role : user.getRoles()) {
        grantedAuthorities.add(new SimpleGrantedAuthority(role.name()));
      }
      return new org.springframework.security.core.userdetails
          .User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
  }
}
