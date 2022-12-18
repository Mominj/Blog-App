package com.example.bloggingapp.service;

import com.example.bloggingapp.model.Role;
import com.example.bloggingapp.model.Users;
import com.example.bloggingapp.repository.RoleRepo;
import com.example.bloggingapp.repository.UsersRepo;
import com.example.bloggingapp.utill.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private RoleRepo roleRepo;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean isPresent(String username ) {
       int  count = usersRepo.findByUserName(username);
       if(count > 0) {
           return true;
       } else {
           return false;
       }

    }


    public boolean checkIfEnabledUser(String userName) {
        Optional<Users> optionalUser = usersRepo.findByUser(userName);
        if(optionalUser.isPresent()) {
            Users user = optionalUser.get();
            if(user.getEnabled().equals(true)) {
                return true;
            } else {
                return true;
            }
        }  else {
            return true;
        }

    }

    public Optional<Users> isPresentandPassworMatchandEnable(Users users) {
        Optional<Users> optionalUser = usersRepo.findByUser(users.getUserName());
        if(optionalUser.isPresent()) {
            Users user = optionalUser.get();
            if(user.getPassword().equals(users.getPassword()) && user.getEnabled().equals(true)) {
                return  optionalUser;
            } else {
                return null;
            }
        }  else {
            return null;
        }

    }
    

    public Collection<Users> userList() {
        Collection<Users>  users =  usersRepo.getAll();
        return users;
    }
    public Role setRole(String roleName) {
        Role role =  roleRepo.findByRoleName(roleName);


//        Set<Role> roles1 = Set.singletonList(role);
       return role;

    }

    public boolean registerUser(Users users) {


        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setEnabled(false);
        Role userRole = this.setRole("ROLE_USER");
        Set<Role> roles = new HashSet<Role>();
        roles.add(userRole);
        users.setRoles(roles);

        Users users1 =  usersRepo.saveAndFlush(users);

        System.out.println(users);
        return  true;
    }
    public boolean registerUserForAdmin(Users users) {


        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setEnabled(false);
        Role userRole = this.setRole("ROLE_ADMIN");
        Set<Role> roles = new HashSet<Role>();
        roles.add(userRole);
        users.setRoles(roles);

        Users users1 =  usersRepo.saveAndFlush(users);

        System.out.println(users);
        return  true;
    }


    

}
