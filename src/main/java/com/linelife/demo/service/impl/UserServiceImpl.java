package com.linelife.demo.service.impl;

import com.linelife.demo.model.Role;
import com.linelife.demo.model.Status;
import com.linelife.demo.model.User;
import com.linelife.demo.repository.RoleRepository;
import com.linelife.demo.repository.UserRepository;
import com.linelife.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        if(result!=null&&result.getStatus()!=Status.DELETE)
            return result;
        else
            return null;
    }

    @Override
    public User findByEmail(String email) {
        User result = userRepository.findByEmail(email);
        if(result!=null&&result.getStatus()!=Status.DELETE)
            return result;
        else
            return null;
    }

    @Override
    public void delete(Long id) {

        User result = userRepository.findById(id).orElse(null);
        result.setStatus(Status.DELETE);
    }

    @Override
    public User saveUser(User user) {
        List<Role> roles = new ArrayList<Role>();
        Role role = roleRepository.findByName("ROLE_USER");
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }
}
