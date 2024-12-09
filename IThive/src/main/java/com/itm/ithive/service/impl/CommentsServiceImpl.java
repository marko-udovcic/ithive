package com.itm.ithive.service.impl;


import com.itm.ithive.model.Comments;
import com.itm.ithive.repository.CommentsRepository;
import com.itm.ithive.service.CommentsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository commentsRepository;

    //

    @Override
    public List<Comments> findAllComments() {
        return commentsRepository.findAll();
    }

    @Override
    public Comments saveComment(Comments comment) {
        return commentsRepository.save(comment);
    }

    @Override
    public Comments updateComment(Comments comment, long id) {

        Comments existingComment = commentsRepository.findById(id).orElse(null);


        if (existingComment != null) {
            existingComment.setId(id);
            existingComment.setParent(comment.getParent());
            existingComment.setText(comment.getText());
//            existingComment.setBlog(comment.getBlog());
            existingComment.setDepth(comment.getDepth());
            existingComment.setUser(comment.getUser());
            existingComment.setCreatedAt(comment.getCreatedAt());

            return commentsRepository.save(existingComment);
        }
        comment.setId(id);
            // ili da prebacimo na neki http response
        return commentsRepository.save(comment);
    }

    @Override
    public void deleteComment(long id) {
        commentsRepository.deleteById(id);
    }
}
