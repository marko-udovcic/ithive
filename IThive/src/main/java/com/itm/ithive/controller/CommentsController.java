package com.itm.ithive.controller;

import com.itm.ithive.model.Comments;
import com.itm.ithive.model.Users;
import com.itm.ithive.service.BlogService;
import com.itm.ithive.service.CommentsService;
import com.itm.ithive.service.UsersService;
import com.itm.ithive.util.CustomUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private BlogService blogService;

    //

    @GetMapping
    public List<Comments> findAllComments () {
        return commentsService.findAllComments();
    }

    @PutMapping("/{id}")
    public Comments updateComments (@RequestBody Comments comments, @PathVariable long id){
        return commentsService.updateComment(comments, id);
    }

    @DeleteMapping("/{id}")
    public void deleteComments (@PathVariable long id){
        commentsService.deleteComment(id);
    }

    @PostMapping
    public String saveComments (@RequestParam(required = false) String reply,
                                @RequestParam String comment,
                                @RequestParam Integer blogId){

        if (comment != null && !comment.trim().isEmpty()){

            if (reply == null){
                reply = "reply_0_-1";
            }
            String[] target = reply.split("_");
            //  target[0] -> reply
            //  target[1] -> parent
            //  target[2] -> depth

            Comments comments = Comments.builder().blog(blogService.findBlogById(Long.valueOf(blogId)))
                    .depth(Integer.parseInt(target[2]) + 1)
                    .text(comment)
                    .parent(Integer.parseInt(target[1]))
                    .user(usersService.getCurrentUser())
                    .build();

            commentsService.saveComment(comments);
        }


        return "redirect:/blog/blogView/" + blogId;
    }



}
