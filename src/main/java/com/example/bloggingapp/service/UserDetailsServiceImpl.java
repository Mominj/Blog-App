package com.example.bloggingapp.service;


import com.example.bloggingapp.model.Users;
import com.example.bloggingapp.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsersRepo userDao;

    @Override
    public MyUserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Users user = userDao.findUsersByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new MyUserDetails(user);
    }
}
