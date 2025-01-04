package com.itm.ithive.controller.blog;

import com.itm.ithive.model.Blog;
import com.itm.ithive.model.Users;
import com.itm.ithive.service.interfaces.BlogService;
import com.itm.ithive.service.interfaces.FollowersService;
import com.itm.ithive.service.interfaces.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Controller
@RequestMapping("/blog")
public class BlogController {
    private final BlogService blogService;
    private final FollowersService followersService;
    private final UsersService usersService;

    @GetMapping
    public List<Blog> listBlogs() {
        return blogService.listAll();
    }

    @PostMapping
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog) {
        Blog createdBlog = blogService.createBlog(blog);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBlog);
    }

    @GetMapping("/{id}")
    public String viewBlog(@PathVariable Long id, Model model) {
        Blog blog = blogService.findBlogById(id);
        Users user = usersService.getCurrentUser();
        model = blogService.blogSetup(id, model);

        model.addAttribute("me", user);
        if (Objects.equals(user.getUsername(), blog.getUser().getUsername())) {
            model.addAttribute("button", false);
        } else {
            if (followersService.doIFollow(blog.getUser())) {
                model.addAttribute("button", "Following");
            } else {
                model.addAttribute("button", "Follow");
            }
        }

        return "blog";
    }
}

