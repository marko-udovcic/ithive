package com.itm.ithive.controller;

import com.itm.ithive.model.Comments;
import com.itm.ithive.model.Users;
import com.itm.ithive.service.CommentsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@Controller
@RequestMapping("/comments")
public class CommentsController {

    private final CommentsService commentsService;

    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    //

    @GetMapping
    public List<Comments> findAllComments () {
        return commentsService.findAllComments();
    }

    @PostMapping
    public Comments saveComments (@RequestBody Comments comments){
        return commentsService.saveComment(comments);
    }

    @PutMapping("/{id}")
    public Comments updateComments (@RequestBody Comments comments, @PathVariable long id){
        return commentsService.updateComment(comments, id);
    }

    @DeleteMapping("/{id}")
    public void deleteComments (@PathVariable long id){
        commentsService.deleteComment(id);
    }





}
