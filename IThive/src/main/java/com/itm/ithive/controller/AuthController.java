package com.itm.ithive.controller;

import com.itm.ithive.exceptions.UserAlreadyExisting;
import com.itm.ithive.model.Users;
import com.itm.ithive.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String loginForm(HttpServletRequest request, Model model) {
        String error = request.getParameter("error");
        model.addAttribute("error", error);

        return "login";
    }


    @GetMapping("/register")
    public String registrationForm(HttpServletRequest request, Model model) {
        String error = request.getParameter("error");
        model.addAttribute("error", error);

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute Users user) throws UserAlreadyExisting {
        try {
            usersService.registerUser(user);
        } catch (UserAlreadyExisting ex) {
            throw ex;
        }

        return "redirect:/login";
    }
}
