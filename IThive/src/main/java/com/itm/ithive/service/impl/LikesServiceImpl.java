package com.itm.ithive.service.impl;


import com.itm.ithive.model.Likes;
import com.itm.ithive.repository.LikesRepository;
import com.itm.ithive.service.LikesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikesServiceImpl implements LikesService {

    private final LikesRepository likesRepository;

    public LikesServiceImpl(LikesRepository likesRepository) {
        this.likesRepository = likesRepository;
    }

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
}
