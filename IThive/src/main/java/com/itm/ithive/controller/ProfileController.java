package com.itm.ithive.controller;

import com.itm.ithive.util.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/profile")

public class ProfileController {
    @GetMapping

    public String showUserDetails(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            String firstLetterOfFirstname = String.valueOf(customUserDetails.getFirstName().charAt(0));
            model.addAttribute("username", customUserDetails.getUsername());
            model.addAttribute("email", customUserDetails.getEmail());
            model.addAttribute("firstName", customUserDetails.getFirstName());
            model.addAttribute("lastName", customUserDetails.getLastName());
            model.addAttribute("firstLetter", firstLetterOfFirstname);
            model.addAttribute("role", customUserDetails.getRole());

        }
        return "profile";
    }
}
