package com.itm.ithive.controller;

import com.itm.ithive.model.Blog;
import com.itm.ithive.model.Users;
import com.itm.ithive.service.BlogService;
import com.itm.ithive.service.UsersService;
import com.itm.ithive.service.impl.HomeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

//@RestController
@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final BlogService blogService;
    private final HomeServiceImpl homeService;

    @GetMapping
    public String home(@RequestParam Map<String, String> params, Model model) {
        Page<Blog> blogs = homeService.showBlogsOfFollowedUsers(params);
        model.addAttribute("blogs", blogs.getContent());
        model.addAttribute("totalSize", blogs.getTotalElements());
        model.addAttribute("currentSize", blogs.getSize());
        return "home";
    }
}
