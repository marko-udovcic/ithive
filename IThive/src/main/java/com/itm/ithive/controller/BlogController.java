package com.itm.ithive.controller;


import com.itm.ithive.model.Blog;
import com.itm.ithive.model.Users;
import com.itm.ithive.repository.UsersRepository;
import com.itm.ithive.service.BlogService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/blog")
public class BlogController {
    private BlogService blogService;

    @GetMapping
    public List<Blog> listBlogs() {
        return blogService.listAll();
    }


    @PostMapping
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog) {
        Blog createdBlog = blogService.createBlog(blog);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBlog);
    }


}
