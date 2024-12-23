package com.itm.ithive.controller;


import com.itm.ithive.model.Users;
import com.itm.ithive.service.FollowersService;
import com.itm.ithive.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/users")
public class UsersController {
    private final UsersService userService;
    private final FollowersService followersService;

    @PostMapping
    public ResponseEntity<Users> createUsers(@RequestBody Users user){
        Users createdUser = userService.createUsers(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/listUsers")
    public List<Users> listAllUsers(){
        return userService.listAll();
    }



    @GetMapping("/userProfile/{username}")
    public String showUsersProfile(@PathVariable String username, Model model, Authentication authentication) {
        Users user = userService.findUserByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        model = followersService.userProfile(model, authentication, user);

        return "userProfile";
    }
}
