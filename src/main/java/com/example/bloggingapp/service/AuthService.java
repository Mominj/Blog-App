package com.example.bloggingapp.service;


import com.example.bloggingapp.dto.JwtRequest;
import com.example.bloggingapp.model.Users;
import com.example.bloggingapp.repository.UsersRepo;
import com.example.bloggingapp.utill.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    UsersRepo userDao;

    public ResponseEntity<?> createAuthenticationToken(JwtRequest authenticationRequest) throws Exception {


        try {
            final UserDetails userDetails = userDetailsServiceImpl
                    .loadUserByUsername(authenticationRequest.getUsername());
            final String token = jwtTokenUtil.generateToken(userDetails);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Authorization", "Bearer " + token);
            return ResponseEntity.ok().headers(responseHeaders).build();

        } catch(AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }

    }




    public boolean checkIfEnabledUser(String username) {
        Users users = userDao.findUsersByUserName(username);
        if (!users.getEnabled()){
            return false;
        }
        return true;
    }
}
