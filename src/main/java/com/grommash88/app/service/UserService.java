package com.grommash88.app.service;

import com.grommash88.app.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
