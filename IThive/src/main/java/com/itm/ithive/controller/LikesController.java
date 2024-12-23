package com.itm.ithive.controller;


import com.itm.ithive.model.Blog;
import com.itm.ithive.model.Likes;
import com.itm.ithive.service.BlogService;
import com.itm.ithive.service.LikesService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/likes")
public class LikesController {

    @Autowired
    private LikesService likesService;

    @Autowired
    private BlogService blogService;

    @GetMapping
    public List<Likes> findAllLikes() {
        return likesService.findAllLikes();
    }

    @PostMapping
    public Likes saveLike(@RequestBody Likes like) {
        return likesService.saveLike(like);
    }

    @PutMapping("/{id}")
    public Likes updateLike(@RequestBody Likes like, @PathVariable long id) {
        return likesService.updateLike(like, id);
    }

    @DeleteMapping("/{id}")
    public void deleteLike(@PathVariable long id) {
        likesService.deleteLike(id);
    }

    @PostMapping("/addLikes")
    public String addLikes(@RequestParam String username,
                           @RequestParam Long blogId,
                           @RequestParam boolean doILike,
                           HttpServletRequest request){


        Blog blog = blogService.findBlogById(blogId);
        if (doILike){
            likesService.deleteLikeByBlog(blog);
        }
        else{
            likesService.addLikeByBlog(blog);
        }

        return "redirect:/blog/blogView/" + blogId;
    }
}
