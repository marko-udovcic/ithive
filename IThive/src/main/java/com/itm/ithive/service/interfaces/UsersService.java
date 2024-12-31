package com.itm.ithive.service.interfaces;


import com.itm.ithive.exceptions.UserAlreadyExisting;
import com.itm.ithive.model.Enums.Status;
import com.itm.ithive.model.Users;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    public Users createUsers(Users user);

    public Users registerUser(Users user) throws UserAlreadyExisting;

    Users findByID(String id);

    public Optional<Users> findUserByUsername(String username);

    List<Users> findByStatus(Status status);

    Users getCurrentUser();
}
