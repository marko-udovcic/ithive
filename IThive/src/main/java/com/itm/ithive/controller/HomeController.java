package com.itm.ithive.controller;

import com.itm.ithive.model.Users;
import com.itm.ithive.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@Controller
@RequestMapping("/home")
public class HomeController {
    @GetMapping
    public String home() {
        return "test";
    }
}
