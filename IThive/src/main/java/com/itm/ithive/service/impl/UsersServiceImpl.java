package com.itm.ithive.service.impl;


import com.itm.ithive.model.Users;
import com.itm.ithive.repository.UsersRepository;
import com.itm.ithive.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {
    private UsersRepository usersRepository;
    @Override
    public Users createUsers(Users user) {
        return usersRepository.save(user);
    }
}
