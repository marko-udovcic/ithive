package com.itm.ithive.service;


import com.itm.ithive.model.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsersService {
    public Users createUsers(Users user);


    public Users registerUser(Users user);
    public boolean authenticateUser (String username, String password);
    public List<Users> listAll();

}
