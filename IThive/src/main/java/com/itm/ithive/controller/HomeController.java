package com.example.IThive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//@RestController
@RequestMapping()
@Controller
public class HomeController {
    @GetMapping("/test1")
    public String home(){
        return "index";
    }
    @GetMapping("/")
    public String home2(Model model){
        model.addAttribute("message", "Thymeleaf!");
        return "index";
    }
}
