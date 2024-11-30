package com.itm.ithive.service;


import com.itm.ithive.model.Comments;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CommentsService {
    List<Comments> findAllComments();
    Comments saveComment(Comments comment);
    Comments updateComment(Comments comment, long id);
    void deleteComment(long id);
}
