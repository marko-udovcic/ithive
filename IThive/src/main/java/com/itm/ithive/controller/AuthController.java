package com.itm.ithive.controller;

import com.itm.ithive.exceptions.UserAlreadyExisting;
import com.itm.ithive.model.Users;
import com.itm.ithive.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping()
public class AuthController {

    private final UsersService usersService;

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
        usersService.registerUser(user);

        return "redirect:/login";
    }
}
