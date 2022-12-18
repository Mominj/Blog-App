package com.example.bloggingapp.controller;


import com.example.bloggingapp.dto.JwtRequest;
import com.example.bloggingapp.service.AuthService;
import com.example.bloggingapp.service.PostService;
import com.example.bloggingapp.service.RegistrationService;
import com.example.bloggingapp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class AuthController {


    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private AuthService authService;

    @Autowired
    private PostService postService;

    final Logger logger = Logger.getLogger(AuthController.class.getName());

    @GetMapping("/")
    public String index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/show/all";
        }
        return "redirect:/login";
    }



    @GetMapping("/login")
    public String loginView(Model model) {
        model.addAttribute("authenticationRequest", new JwtRequest());
        return "login";
    }

    @PostMapping("/process_login")
    public String loginAuth(@ModelAttribute(value="authenticationRequest") JwtRequest authenticationRequest,
                            final BindingResult bindingResult,
                            HttpServletRequest request, HttpSession session,
                            @Valid @ModelAttribute(value="authenticationRequest") JwtRequest jwtRequest) throws Exception {
        if (!registrationService.checkIfUserExist(authenticationRequest.getUsername())){
            bindingResult
                    .rejectValue("username", "error.authenticationRequest",
                            "User does not exist");
        }

        if (registrationService.checkIfUserExist(authenticationRequest.getUsername())) {
            if (!authService.checkIfEnabledUser(authenticationRequest.getUsername())) {
                bindingResult
                        .rejectValue("username", "error.authenticationRequest",
                                "Wait for user approval from Admin");
            }
        }

        if (!registrationService.passWordMatch(authenticationRequest) ){

                bindingResult
                        .rejectValue("username", "error.authenticationRequest",
                                "user name or password not match");

        }

        if(!bindingResult.hasErrors()){

            session.invalidate();

            ResponseEntity<?> authResponse = authService.createAuthenticationToken(authenticationRequest);
            if (authResponse.getStatusCode() == HttpStatus.OK) {
                String tokenObj = authResponse.getHeaders().get("Authorization").get(0);
                HttpSession newSession = request.getSession(); // create session
                newSession.setAttribute("token", tokenObj);

                System.err.println(tokenObj);
                return "redirect:/show/all";
            } else {
                return "redirect:/login";
            }
        }
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors ) {
            logger.info("*********************"+error.getObjectName() + " ---> " + error.getDefaultMessage() + "*********************");
        }
        return "redirect:/login";
    }


}
