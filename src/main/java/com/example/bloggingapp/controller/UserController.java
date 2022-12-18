package com.example.bloggingapp.controller;


import com.example.bloggingapp.dto.JwtRequest;
import com.example.bloggingapp.model.Users;
import com.example.bloggingapp.repository.UsersRepo;
import com.example.bloggingapp.service.AuthService;
import com.example.bloggingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private AuthService authService;




    @RequestMapping(value = "/signup")
    public String userCreate(Model model)  {
        Users users = new Users();
        model.addAttribute("users", users);
        return "signup";
    }

    @PostMapping("user/create")
    public String userCreate(Model model,
                             @Valid @ModelAttribute("users") Users users, final BindingResult bindingResult) {
        model.addAttribute("users", users);

        boolean isPresent = userService.isPresent(users.getUserName());
        if (isPresent){
            bindingResult
                    .rejectValue("userName", "error.userData",
                            "There is already an user registered with this Username");
        }

        if(!bindingResult.hasErrors()){

           boolean created = userService.registerUser(users);
           if(created) {
               return "redirect:/login";
           }
        }
        return "redirect:/signup";

    }

    @RequestMapping(value = "/signup/admin")
    public String userCreateAdmin(Model model)  {
        Users users = new Users();
        model.addAttribute("users", users);
        return "signupadmin";
    }

    @PostMapping("user/create/admin")
    public String userCreateAdmin(Model model,
                             @ModelAttribute("users") Users users, final BindingResult bindingResult) {
        model.addAttribute("users", users);

        boolean isPresent = userService.isPresent(users.getUserName());
        if (isPresent){
            bindingResult
                    .rejectValue("username", "error.userData",
                            "There is already an user registered with this Username");
        }

        if(!bindingResult.hasErrors()){

            boolean created = userService.registerUserForAdmin(users);
            if(created) {
                return "redirect:/show/all";
            }
        }
        return "redirect:/signup";

    }

    @GetMapping(value = "/user/list")
    public String userList(Model model)   {

        Collection<Users> users = usersRepo.getAll();
        model.addAttribute("users", users);
        return "userList";
    }

    @GetMapping(value = "/edit/{id}")
    public String statusChane(@PathVariable Integer id, Model model)   {
        Optional<Users> users = usersRepo.findById(id);
          users.get().getEnabled();
       if(users.isPresent()) {
          Users users1 =  users.get();
          if(users1.getEnabled() == false) {
              users1.setEnabled(true);
          } else {
              users1.setEnabled(false);
          }
           usersRepo.save(users1);
       }
        model.addAttribute("users", users);
        return "redirect:/user/list";
    }

    @RequestMapping(value = "/login_pro", method = RequestMethod.POST)
    public String Login(Model model, @ModelAttribute("users") Users users, final BindingResult bindingResult,
                        HttpServletRequest request, HttpSession session) throws Exception {
        model.addAttribute("users", users);
        Optional<Users>  optionalUsers = userService.isPresentandPassworMatchandEnable(users);
        JwtRequest authenticationRequest = new JwtRequest();
        authenticationRequest.setUsername(users.getUserName());
        authenticationRequest.setPassword(users.getPassword());
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
}
