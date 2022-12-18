package com.example.bloggingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @RequestMapping(value = "/index")
    public String home(Model model) {
        return "redirect:/show/all";
    }
}
