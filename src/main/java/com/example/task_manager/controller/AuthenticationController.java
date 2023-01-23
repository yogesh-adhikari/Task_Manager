package com.example.task_manager.controller;

import com.example.task_manager.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
    public class AuthenticationController {

    private  UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "users/login";
    }


    @GetMapping("/accessDenied")
    public String accessDenied(){
        return "accessDenied";
    }
    }

