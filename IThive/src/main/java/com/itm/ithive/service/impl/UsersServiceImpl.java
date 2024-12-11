package com.itm.ithive.service.impl;


import com.itm.ithive.exceptions.SomethingWrong;
import com.itm.ithive.exceptions.UserAlreadyExisting;
import com.itm.ithive.model.Enums.Role;
import com.itm.ithive.model.Enums.Status;
import com.itm.ithive.model.Users;
import com.itm.ithive.repository.UsersRepository;
import com.itm.ithive.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
