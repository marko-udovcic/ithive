package com.itm.ithive.service;


import com.itm.ithive.model.Blog;
import com.itm.ithive.model.Likes;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LikesService {
    List<Likes> findAllLikes();
    Likes saveLike(Likes like);
    Likes updateLike(Likes like, long id);
    void deleteLike(long id);

    List<Likes> findLikesByBlog(Blog blog);
    void addLikeByBlog(Blog blog);

    boolean doILike(Blog blog);
    void deleteLikeByBlog (Blog blog);
}
