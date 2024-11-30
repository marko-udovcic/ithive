package com.itm.ithive.service;


import com.itm.ithive.model.Followers;
import org.springframework.stereotype.Service;

import java.util.List;


public interface FollowersService {
    List<Followers> findAllFollowers();
    Followers saveFollower(Followers follower);
    Followers updateFollower(Followers follower, long id);
    void deleteFollower(long id);
}
