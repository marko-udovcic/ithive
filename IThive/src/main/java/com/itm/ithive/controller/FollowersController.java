package com.itm.ithive.controller;


import com.itm.ithive.model.Followers;
import com.itm.ithive.model.Users;
import com.itm.ithive.service.FollowersService;
import com.itm.ithive.service.UsersService;
import com.itm.ithive.util.CustomUserDetails;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/followers")
public class FollowersController {

    @Autowired
    private FollowersService followersService;

    @Autowired
    private UsersService userService;


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


    @PostMapping("/setUsersFollowing")
    public String setUsersFollowing(@RequestParam String username){
        Users user = userService.findUserByUsername(username).orElse(null);
        followersService.followUser(user);

        return "redirect:/profile/usersProfile";
    }
}
