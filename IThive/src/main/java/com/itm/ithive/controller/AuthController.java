package com.itm.ithive.controller;

import com.itm.ithive.model.Users;
import com.itm.ithive.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping()
public class AuthController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/")
    public String authHome() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/checkLogin")
    public String authenticateUser(@RequestParam String username, @RequestParam String password) {
        if(usersService.authenticateUser(username, password)){
            return "redirect:/home";
        }
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String registrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute Users user) {
        usersService.registerUser(user);
        return "redirect:/login";
    }
}
