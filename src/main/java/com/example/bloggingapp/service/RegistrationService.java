package com.example.bloggingapp.service;


import com.example.bloggingapp.dto.JwtRequest;
import com.example.bloggingapp.dto.RegistrationDto;
import com.example.bloggingapp.model.Role;
import com.example.bloggingapp.model.Users;
import com.example.bloggingapp.repository.RoleRepo;
import com.example.bloggingapp.repository.UsersRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RegistrationService {

    @Autowired
    private UsersRepo userDao;

    @Autowired
    private RoleRepo roleDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void registerUser(RegistrationDto registrationDto) throws Exception {
        Set<Role> roleSet = new HashSet<>();
        Role role = roleDao.findByRoleName("ROLE_BLOGGER");
        roleSet.add(role);
        Users users = new Users();
        users.setUserName(registrationDto.getUserName());
        users.setPassword(encodePassword(registrationDto.getUserPassword()));
        users.setEnabled(false);
        users.setRoles(roleSet);
        userDao.save(users);
    }

    public void registerAdminUser(Users user) throws Exception {
        Set<Role> roleSet = new HashSet<>();
        Role role = roleDao.findByRoleName("ROLE_ADMIN");
        roleSet.add(role);
        user.setPassword(encodePassword(user.getPassword()));
        user.setEnabled(true);
        user.setRoles(roleSet);
        userDao.save(user);
    }



    public boolean checkIfUserExist(String userName) {
        Users users = userDao.findUsersByUserName(userName);

        if (users != null) return true;
        return false;
    }

    public boolean passWordMatch(JwtRequest JwtRequest) {
        Users users = userDao.findUsersByUserName(JwtRequest.getUsername());

        if (users != null) {
            boolean result = passwordEncoder.matches(JwtRequest.getPassword(), users.getPassword());
            if(result == true) {
                return true;
            }

        }
        return false;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
