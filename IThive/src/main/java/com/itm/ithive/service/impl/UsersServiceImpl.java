package com.itm.ithive.service.impl;


import com.itm.ithive.model.Enums.Role;
import com.itm.ithive.model.Enums.Status;
import com.itm.ithive.model.Users;
import com.itm.ithive.repository.UsersRepository;
import com.itm.ithive.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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
    public Users registerUser (Users user){
        if (user == null){
            return new Users();
            // throw an exception
        }
        if (usersRepository.findByUsername(user.getUsername()) != null){
            return new Users();
            // throw an exception
        }

        user.setStatus(Status.Default);
        user.setRole(Role.User);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return usersRepository.save(user);
    }

    @Override
    public boolean authenticateUser (String username, String password){
        Users user = findByUsername(username);

        if (user != null && passwordEncoder.matches(password, user.getPassword())){
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

    public boolean checkPassword(Users user, String plainPassword){
        return passwordEncoder.matches(plainPassword, user.getPassword());
    }

    public Users findByUsername (String username){
        return usersRepository.findByUsername(username).orElse(null);
    }

}
