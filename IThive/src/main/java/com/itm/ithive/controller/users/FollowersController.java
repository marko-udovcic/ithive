package com.itm.ithive.controller.users;


import com.itm.ithive.model.Followers;
import com.itm.ithive.model.Users;
import com.itm.ithive.service.interfaces.FollowersService;
import com.itm.ithive.service.interfaces.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
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
    public String setUsersFollowing(HttpServletRequest request,
                                    @RequestParam String username,
                                    @RequestParam String button) {


        Users user = userService.findUserByUsername(username).orElse(null);

        if (Objects.equals(button, "Follow")) {
            followersService.followUser(user);
        } else {
            followersService.deleteFollowerByFollowed(user);
        }

        String referer = request.getHeader("Referer");
        if (referer != null) {
            String refererPath = referer.replaceFirst("http://localhost:8089", "");
            return "redirect:" + refererPath;
        }

        return "redirect:/profile";
    }
}
