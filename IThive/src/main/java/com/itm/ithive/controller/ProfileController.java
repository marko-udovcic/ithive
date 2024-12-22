package com.itm.ithive.controller;

import com.itm.ithive.model.Blog;
import com.itm.ithive.model.Category;
import com.itm.ithive.model.Users;
import com.itm.ithive.service.*;
import com.itm.ithive.service.impl.ProfileServiceImpl;
import com.itm.ithive.service.impl.UploadServiceImpl;
import com.itm.ithive.util.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final CategoryService categoryService;
    private final BlogService blogService;
    private final UsersService usersService;
    private final ProfileServiceImpl profileService;

    private final UploadService uploadService;
    private final FollowersService followersService;

    @GetMapping
    public String showUserDetails(Model model, Authentication authentication) {
        model = followersService.userProfile(model, authentication, null);

        model.addAttribute("img_UUID", UUID.randomUUID().toString());
        return "profile";
    }


    @GetMapping("/become-blogger")
    public String becomeBlogger(Model model) {
        profileService.setUserStatusToPending(model);

        return "redirect:/profile";
    }

    @PostMapping("/updateUserStatus")
    public String updateUserStatus(
            @RequestParam("username") String username,
            @RequestParam("status") String status,
            @RequestParam("roles") String role) {

        profileService.updateUserStatusAndRole(username, status, role);
        return "redirect:/profile";

    }

    @PostMapping("/edit-blog")
    public String updateBlog(@RequestParam("title") String title,
                             @RequestParam("blog-id") Long id,
                             @RequestParam("content") String content) {

        profileService.updateBlog(title, id, content);

        return "redirect:/profile";
    }

    @PostMapping("/delete-blog/{id}")
    public String deleteBlog(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean isDeleted = blogService.deleteBlog(id);

        if (isDeleted) {
            redirectAttributes.addFlashAttribute("message", "Blog deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Failed to delete the blog.");
        }

        return "redirect:/profile";
    }

    @PostMapping("/add-blog")
    public String addBlog(
            @RequestParam String title,
            @RequestParam Long categoryId,
            @RequestParam String content,
            @RequestParam String image_name) {

        image_name = image_name + ".jpeg";
        System.out.println(image_name);
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
                        .imgUrl(uploadService.imageExists(image_name) ?
                                image_name : "defaultBlogImage.png")
                        .build();
                blogService.createBlog(newBlog);

            } else {
                System.out.println("error during inserting");
            }
        }

        return "redirect:/home";
    }


    @GetMapping("/usersProfile")
    public String showUsersProfile(HttpServletRequest request, Model model) {

        Users user = usersService.findUserByUsername("admin").orElse(null);
//        admin is used as placeholder, send other user when implementing function

        String url = request.getRequestURI();
//        model = followersService.userProfile(url, model, user);


        return "profile";
    }

}
