package com.itm.ithive.service.impl;


import com.itm.ithive.model.Blog;
import com.itm.ithive.model.Likes;
import com.itm.ithive.model.Users;
import com.itm.ithive.repository.LikesRepository;
import com.itm.ithive.service.LikesService;
import com.itm.ithive.service.UsersService;
import com.itm.ithive.util.CustomUserDetails;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LikesServiceImpl implements LikesService {

    private final LikesRepository likesRepository;

    @Autowired
    private UsersService usersService;

    @Override
    public List<Likes> findAllLikes() {
        return likesRepository.findAll();
    }

    @Override
    public Likes saveLike(Likes like) {
        return likesRepository.save(like);
    }

    @Override
    public Likes updateLike(Likes like, long id) {
        Likes existingLike = likesRepository.findById(id).orElse(null);

        if (existingLike != null) {
            existingLike.setId(id);
            existingLike.setUser(like.getUser());
            existingLike.setBlog(like.getBlog());

            return likesRepository.save(existingLike);
        }
        like.setId(id);
        return likesRepository.save(like);
    }

    @Override
    public void deleteLike(long id) {
        likesRepository.deleteById(id);
    }


    @Override
    public List<Likes> findLikesByBlog(Blog blog){
        return likesRepository.findLikesByBlog(blog);
    }

    @Override
    public void addLikeByBlog(Blog blog){
        Users user = usersService.getCurrentUser();

        Likes like = new Likes();
        like.setBlog(blog);
        like.setUser(user);

        likesRepository.save(like);
    }

    @Override
    public boolean doILike(Blog blog) {
        Users user = usersService.getCurrentUser();
        List<Likes> allLikesForThisBlog = likesRepository.findLikesByBlog(blog);

        for (Likes like : allLikesForThisBlog){
            if (Objects.equals(like.getUser().getUsername(), user.getUsername())){
                return true;
            }
        }

        return false;
    }

    @Override
    public void deleteLikeByBlog(Blog blog) {
        Users user = usersService.getCurrentUser();
        List<Likes> allLikesForThisBlog = likesRepository.findLikesByBlog(blog);

        for (Likes like : allLikesForThisBlog){
            if (Objects.equals(like.getUser().getUsername(), user.getUsername())){
                deleteLike(like.getId());
            }
        }
    }
}
