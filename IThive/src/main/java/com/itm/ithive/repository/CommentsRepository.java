package com.itm.ithive.repository;

import com.itm.ithive.model.Blog;
import com.itm.ithive.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findCommentsByBlog(Blog blog);
    List<Comments> findCommentsByParent(int id);
    void deleteByBlog(Blog blog);
}
