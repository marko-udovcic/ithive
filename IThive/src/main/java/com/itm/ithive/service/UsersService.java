package com.itm.ithive.service;


import com.itm.ithive.exceptions.UserAlreadyExisting;
import com.itm.ithive.model.Enums.Status;
import com.itm.ithive.model.Users;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsersService {
    public Users createUsers(Users user);

    public Users registerUser(Users user) throws UserAlreadyExisting;

    public List<Users> listAll();

    Users findByID(String id);

    public Optional<Users> findUserByUsername(String username);

    List<Users> findByStatus(Status status);

    Users getCurrentUser();
}
