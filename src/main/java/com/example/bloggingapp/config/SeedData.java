package com.example.bloggingapp.config;


import com.example.bloggingapp.model.Role;
import com.example.bloggingapp.model.Users;
import com.example.bloggingapp.repository.PostRepo;
import com.example.bloggingapp.repository.RoleRepo;
import com.example.bloggingapp.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SeedData implements CommandLineRunner {
    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private PostRepo postRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
//        List<Posts> posts = postService.getAll();
//
//        if (posts.size() == 0) {

            Role user = new Role();
            user.setId(1);
            user.setRoleName("ROLE_USER");
            roleRepo.save(user);

            Role admin = new Role();
            admin.setId(2);
            admin.setRoleName("ROLE_ADMIN");
            roleRepo.save(admin);

            Users users1 = new Users();
            Users user2 = new Users();
            users1.setUserName("user");
            users1.setPassword( passwordEncoder.encode("12345"));
            users1.setEnabled(true);

            Set<Role> roles = new HashSet<>();
            Users users11 = new Users();

            Role role =  roleRepo.findByRoleName("ROLE_USER");

            Set<Role> roles1 = new HashSet<Role>();
            roles1.add(role);

            users1.setRoles(roles1);

            users11 =  usersRepo.saveAndFlush(users1);




            user2.setUserName("admin");
            user2.setPassword( passwordEncoder.encode("12345"));
            user2.setEnabled(true);

            Users users22 = new Users();

            Role role2  =  roleRepo.findByRoleName("ROLE_ADMIN");

             Set<Role> roles11 = new HashSet<Role>();
             roles11.add(role2);
             user2.setRoles(roles11);
            users22  = usersRepo.saveAndFlush(user2);


    }

}
