package com.itm.ithive.service.interfaces;


import com.itm.ithive.model.Blog;
import com.itm.ithive.model.Comments;

import java.util.List;


public interface CommentsService {
    List<Comments> findAllComments();

    Comments saveComment(Comments comment);

    Comments updateComment(Comments comment, long id);

    void deleteComment(long id);

    List<Comments> findCommentsByBlog(Blog blog);

    List<Comments> findCommentsByParent(long id);
}
