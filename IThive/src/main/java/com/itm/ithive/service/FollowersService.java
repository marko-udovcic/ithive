package com.itm.ithive.service;


import com.itm.ithive.model.Followers;
import com.itm.ithive.model.Users;
import org.apache.catalina.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;


public interface FollowersService {
    List<Followers> findAllFollowers();

    Followers saveFollower(Followers follower);

    Followers updateFollower(Followers follower, long id);

    void deleteFollower(long id);

    List<Followers> listWhoFollowsUser(Users user);

    List<Followers> listWhoIsUserFollowing(Users user);

    void followUser(Users user);

    public Model userProfile(Model model, Authentication authentication, Users user);

    public boolean doIFollow(Users user);
}
