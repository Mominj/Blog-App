package com.example.bloggingapp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/role")
public class RoleController {


    @GetMapping("/save")
    public String save(@RequestParam String name) {
//        Role role = new Role();

//        role.setRoleName(name);
//        roleRepo.save(role);
        return "ok";
    }

}
