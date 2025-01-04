package com.itm.ithive.controller.pages;

import com.itm.ithive.service.impl.page.ProfileServiceImpl;
import com.itm.ithive.service.interfaces.BlogService;
import com.itm.ithive.service.interfaces.FollowersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final BlogService blogService;
    private final ProfileServiceImpl profileService;
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
        redirectAttributes.addFlashAttribute("message",
                blogService.deleteBlog(id) ?
                        "Blog deleted successfully!" : "Failed to delete the blog.");

        return "redirect:/profile";
    }

    @PostMapping("/add-blog")
    public String addBlog(
            @RequestParam String title,
            @RequestParam Long categoryId,
            @RequestParam String content,
            @RequestParam String image_name) {
        profileService.addNewBlog(title, categoryId, content, image_name);
        return "redirect:/home";
    }

}
