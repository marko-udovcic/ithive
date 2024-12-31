package com.itm.ithive.controller.pages;

import com.itm.ithive.model.Blog;
import com.itm.ithive.service.impl.page.HomeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
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
