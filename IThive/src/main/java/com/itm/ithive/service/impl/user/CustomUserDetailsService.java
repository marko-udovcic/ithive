package com.itm.ithive.service.impl.user;

import com.itm.ithive.model.Users;
import com.itm.ithive.repository.UsersRepository;
import com.itm.ithive.util.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("could not found user"));
        return new CustomUserDetails(user);
    }
}
