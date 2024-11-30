package com.itm.ithive.controller;


import com.itm.ithive.model.Users;
import com.itm.ithive.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UsersController {
    private UsersService userService;
    
    @PostMapping
    public ResponseEntity<Users> createUsers(@RequestBody Users user){
        Users createdUser = userService.createUsers(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
