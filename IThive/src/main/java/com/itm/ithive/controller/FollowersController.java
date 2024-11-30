package com.itm.ithive.controller;


import com.itm.ithive.model.Followers;
import com.itm.ithive.service.FollowersService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/followers")
public class FollowersController {

    private final FollowersService followersService;

    public FollowersController(FollowersService followersService) {
        this.followersService = followersService;
    }

    @GetMapping
    public List<Followers> findAllFollowers() {
        return followersService.findAllFollowers();
    }

    @PostMapping
    public Followers saveFollower(@RequestBody Followers follower) {
        return followersService.saveFollower(follower);
    }

    @PutMapping("/{id}")
    public Followers updateFollower(@RequestBody Followers follower, @PathVariable long id) {
        return followersService.updateFollower(follower, id);
    }

    @DeleteMapping("/{id}")
    public void deleteFollower(@PathVariable long id) {
        followersService.deleteFollower(id);
    }
}
