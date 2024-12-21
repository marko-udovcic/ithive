package com.itm.ithive.service.impl;

import com.itm.ithive.model.Blog;
import com.itm.ithive.model.Enums.Role;
import com.itm.ithive.model.Enums.Status;
import com.itm.ithive.model.Users;
import com.itm.ithive.repository.BlogRepository;
import com.itm.ithive.repository.UsersRepository;
import com.itm.ithive.service.UsersService;
import com.itm.ithive.util.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl {
    private final UsersService usersService;
    private final UsersRepository usersRepository;
    private final BlogRepository blogRepository;

    public void setUserStatusToPending(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //get current auth user
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal(); // get main auth user (impl of CustomUserDetails)

        Users user = usersService.findUserByUsername(customUserDetails.getUsername()).orElse(null);

        if (user != null) {
            user.setStatus(Status.Pending);
            usersRepository.save(user);
        } else {
            throw new RuntimeException("user not found");
        }
    }

    public void updateUserStatusAndRole(String username, String status, String role) {
        Optional<Users> usersOptional = usersService.findUserByUsername(username);

        if (usersOptional.isPresent()) {

            Users user = usersOptional.get();
            System.out.println("vrijednost statusa je " + status);
            if (role.equals("Admin") || role.equals("Blogger")) {
                System.out.println("usa sam u if i minjam status");
                status = "Approved";
            } else {
                status = "Default";
            }
            System.out.println("vrijednost statusa nakon ifova  je " + status);
            user.setStatus(Status.valueOf(status));
            System.out.println("daj mi  value od statusa" + Status.valueOf(status));
            user.setRole(Role.valueOf(role));

            usersRepository.save(user);
        } else {
            throw new RuntimeException("user not found");
        }

    }

    public void updateBlog(String title, Long id, String content) {
        Optional<Blog> optionalBlog = blogRepository.findById(id);

        if (optionalBlog.isPresent()) {
            Blog blog = optionalBlog.get();
            blog.setTitle(title);
            blog.setContent(content);

            blogRepository.save(blog);
        } else {
            throw new RuntimeException("Blog not found");
        }
    }
}
