package com.itm.ithive.repository;

import com.itm.ithive.model.Blog;
import com.itm.ithive.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    List<Likes> findLikesByBlog(Blog blog);
    void deleteByBlog(Blog blog);
}
