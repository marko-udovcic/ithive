package com.itm.ithive.service.impl;


import com.itm.ithive.exceptions.SomethingWrong;
import com.itm.ithive.exceptions.UserAlreadyExisting;
import com.itm.ithive.model.Enums.Role;
import com.itm.ithive.model.Enums.Status;
import com.itm.ithive.model.Followers;
import com.itm.ithive.model.Users;
import com.itm.ithive.repository.UsersRepository;
import com.itm.ithive.service.UsersService;
import com.itm.ithive.util.CustomUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Users createUsers(Users user) {
        return usersRepository.save(user);
    }


    @Override
    public Users registerUser(Users user) throws UserAlreadyExisting {
        if (user == null) {
            throw new SomethingWrong("Something's wrong come back in a minute");
        }
        if (usersRepository.findByUsername(user.getUsername()).isPresent() ||
                usersRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExisting("Credentials already existing, please try something else");
        }

        user.setStatus(Status.Default);
        user.setRole(Role.User);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return usersRepository.save(user);
    }


    @Override
    public List<Users> listAll() {
        return usersRepository.findAll();
        // make pagination
    }

    @Override
    public Users findByID(String id) {
        return usersRepository.findById(id).orElse(null);
    }


    @Override
    public Optional<Users> findUserByUsername(String username) {
        return findByUsername(username);
    }

    @Override
    public List<Users> findByStatus(Status status) {
        return usersRepository.findByStatus(status);
    }

    @Override
    public Users getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()){
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            return findUserByUsername(customUserDetails.getUsername()).orElse(null);
        }

        throw new SomethingWrong("Not authenticated!");
    }


    public boolean checkPassword(Users user, String plainPassword) {
        return passwordEncoder.matches(plainPassword, user.getPassword());
    }

    public Optional<Users> findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    public Optional<Users> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}
