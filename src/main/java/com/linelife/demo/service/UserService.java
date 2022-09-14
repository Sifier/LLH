package com.linelife.demo.service;

import com.linelife.demo.model.User;

public interface UserService {

    User findByUsername(String username);

    User findByEmail(String email);

    void delete(Long id);

    User saveUser(User user);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
