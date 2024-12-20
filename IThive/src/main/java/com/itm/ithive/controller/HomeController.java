package com.itm.ithive.controller;

import com.itm.ithive.model.Blog;
import com.itm.ithive.model.Users;
import com.itm.ithive.service.BlogService;
import com.itm.ithive.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

//@RestController
@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final BlogService blogService;

    //placeholder method
    @GetMapping
    public String home(Model model) {
        List<Blog> blogs = blogService.listAll();
        model.addAttribute("blogs", blogs);
        return "home";
    }
}
