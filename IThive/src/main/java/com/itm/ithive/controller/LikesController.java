package com.itm.ithive.controller;


import com.itm.ithive.model.Likes;
import com.itm.ithive.service.LikesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikesController {

    private final LikesService likesService;

    public LikesController(LikesService likesService) {
        this.likesService = likesService;
    }

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
}
