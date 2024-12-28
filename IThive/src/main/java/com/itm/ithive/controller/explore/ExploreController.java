package com.itm.ithive.controller.explore;

import com.itm.ithive.model.Blog;
import com.itm.ithive.service.impl.ExploreServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/explore")
@RequiredArgsConstructor
public class ExploreController {
    private final ExploreServiceImpl exploreService;

    @GetMapping
    public String explore(@RequestParam Map<String, String> params, Model model) {
        Page<Blog> blogsPage = exploreService.showBlogsOfNonFollowedUsers(params);
        System.out.println("Number of blogs retrieved: " + blogsPage.getTotalElements() + "pg:" + blogsPage.getTotalPages());

        model.addAttribute("blogs", blogsPage.getContent());
        model.addAttribute("totalSize", blogsPage.getTotalElements());
        model.addAttribute("currentSize", blogsPage.getSize());
        model.addAttribute("title", params.getOrDefault("title", ""));
        model.addAttribute("status", params.getOrDefault("status", "newest"));

        return "explore";
    }
}
