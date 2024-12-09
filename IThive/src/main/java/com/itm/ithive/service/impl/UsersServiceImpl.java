package com.itm.ithive.service.impl;


import com.itm.ithive.model.Enums.Role;
import com.itm.ithive.model.Enums.Status;
import com.itm.ithive.model.Users;
import com.itm.ithive.repository.UsersRepository;
import com.itm.ithive.service.UsersService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Users createUsers(Users user) {
        return usersRepository.save(user);
    }


    @Override
    public Users registerUser(Users user) {
        if (user == null) {
            return new Users();
            // throw an exception
        }
        if (!(usersRepository.findByUsername(user.getUsername()).isEmpty())) {
            return new Users();
            // throw an exception
        }

        user.setStatus(Status.Default);
        user.setRole(Role.User);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return usersRepository.save(user);
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        Optional<Users> user = findByUsername(username);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            System.out.println(password);
//            UsernamePasswordAuthenticationToken authenticationToken =
//                    new UsernamePasswordAuthenticationToken(username, password);
//
//            authenticationManager.authenticate(authenticationToken);
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            return true;
        }

        return false;
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

}
