package com.itm.ithive.controller;

import com.itm.ithive.model.Blog;
import com.itm.ithive.model.Category;
import com.itm.ithive.model.Users;
import com.itm.ithive.service.BlogService;
import com.itm.ithive.service.CategoryService;
import com.itm.ithive.service.UsersService;
import com.itm.ithive.util.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final CategoryService categoryService;
    private final BlogService blogService;
    private final UsersService usersService;

    @GetMapping

    public String showUserDetails(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {

            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            String role = customUserDetails.getRole().name();
            String firstLetterOfFirstname = String.valueOf(customUserDetails.getFirstName().charAt(0));

            model.addAttribute("username", customUserDetails.getUsername());
            model.addAttribute("email", customUserDetails.getEmail());
            model.addAttribute("firstName", customUserDetails.getFirstName());
            model.addAttribute("lastName", customUserDetails.getLastName());
            model.addAttribute("firstLetter", firstLetterOfFirstname);
            model.addAttribute("role", role);

            List<Category> categories = categoryService.findAllCategories();
            categories.forEach(category -> System.out.println(category.getName()));
            model.addAttribute("categories", categories);

        }
        return "profile";
    }

    @GetMapping("/add-blog")
    public String addBlog(
            @RequestParam String title,
            @RequestParam Long categoryId,
            @RequestParam String content) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            String userId = customUserDetails.getId().toString();
            Users currentUser = usersService.findByID(userId);
            Category selectedCategory = categoryService.getCategoryById(categoryId);
            if (currentUser != null && selectedCategory != null) {
                Blog newBlog = Blog.builder().title(title)
                        .content(content)
                        .user(currentUser)
                        .category(selectedCategory)
                        .createdAt(LocalDateTime.now())
                        .build();
                blogService.createBlog(newBlog);

            } else {
                System.out.println("error during inserting");
            }
        }

        return "redirect:/home";
    }
}
