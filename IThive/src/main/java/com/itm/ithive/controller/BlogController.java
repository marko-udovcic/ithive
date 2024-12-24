package com.itm.ithive.controller;


import com.itm.ithive.model.Blog;
import com.itm.ithive.model.Comments;
import com.itm.ithive.model.Users;
import com.itm.ithive.repository.UsersRepository;
import com.itm.ithive.service.*;
import com.itm.ithive.util.CustomUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private LikesService likesService;

    @Autowired
    private FollowersService followersService;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private UsersService usersService;


    @GetMapping
    public List<Blog> listBlogs() {
        return blogService.listAll();
    }


    @PostMapping
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog) {
        Blog createdBlog = blogService.createBlog(blog);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBlog);
    }

    @GetMapping("/blogView/{id}")
    public String viewBlog (@PathVariable Long id, Model model){
        Blog blog = blogService.findBlogById(id);
        Users user = usersService.getCurrentUser();
        model = blogService.blogSetup(id, model);

        if(Objects.equals(user.getUsername(), blog.getUser().getUsername())){
            model.addAttribute("button", false);
        }
        else{
            if (followersService.doIFollow(blog.getUser())){
                model.addAttribute("button", "Following");
            }
            else {
                model.addAttribute("button", "Follow");
            }
        }


        return "blog";
    }


}

