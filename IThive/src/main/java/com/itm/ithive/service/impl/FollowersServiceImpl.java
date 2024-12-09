package com.itm.ithive.service.impl;


import com.itm.ithive.model.Followers;
import com.itm.ithive.repository.FollowersRepository;
import com.itm.ithive.service.FollowersService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowersServiceImpl implements FollowersService {

    private final FollowersRepository followersRepository;

    @Override
    public List<Followers> findAllFollowers() {
        return followersRepository.findAll();
    }

    @Override
    public Followers saveFollower(Followers follower) {
        return followersRepository.save(follower);
    }

    @Override
    public Followers updateFollower(Followers follower, long id) {

        Followers existingFollower = followersRepository.findById(id).orElse(null);

        if (existingFollower != null) {
            existingFollower.setId(id);
            existingFollower.setFollower(follower.getFollower());
            existingFollower.setFollowed(follower.getFollowed());
            existingFollower.setDateFollowing(follower.getDateFollowing());

            return followersRepository.save(existingFollower);
        }
        follower.setId(id);
        return followersRepository.save(follower);
    }

    @Override
    public void deleteFollower(long id) {
        followersRepository.deleteById(id);
    }
}
