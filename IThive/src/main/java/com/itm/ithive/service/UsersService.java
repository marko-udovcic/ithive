package com.itm.ithive.service;


import com.itm.ithive.exceptions.UserAlreadyExisting;
import com.itm.ithive.model.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UsersService {
    public Users createUsers(Users user);

    public Users registerUser(Users user) throws UserAlreadyExisting;

    public List<Users> listAll();

    Users findByID(String id);

}
