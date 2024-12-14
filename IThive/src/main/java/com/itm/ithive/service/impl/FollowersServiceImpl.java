package com.itm.ithive.service.impl;


import com.itm.ithive.exceptions.SomethingWrong;
import com.itm.ithive.model.Category;
import com.itm.ithive.model.Followers;
import com.itm.ithive.model.Users;
import com.itm.ithive.repository.FollowersRepository;
import com.itm.ithive.service.FollowersService;
import com.itm.ithive.util.CustomUserDetails;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowersServiceImpl implements FollowersService {

    @Autowired
    private UsersServiceImpl usersService;

    @Autowired
    private BlogServiceImpl blogService;

    private final CategoryServiceImpl categoryService;

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

    @Override
    public List<Followers> listWhoFollowsUser(Users user) {
        return followersRepository.findByFollowed(user);
    }

    @Override
    public List<Followers> listWhoIsUserFollowing(Users user) {
        return followersRepository.findByFollower(user);
    }

    @Override
    public void followUser(Users user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Users mainUser = usersService.findUserByUsername(customUserDetails.getUsername()).orElse(null);
        if (user != null || mainUser != null) {
            Followers follow = new Followers();
            follow.setFollower(mainUser);
            follow.setFollowed(user);
            saveFollower(follow);
        }
//        add an exception
    }

    @Override
    public Model userProfile(Model model, Authentication authentication, Users user) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return model;
        }
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        if (user == null) { // main user
            user = usersService.findUserByUsername(customUserDetails.getUsername()).orElseThrow(null); // make it right
            model.addAttribute("button", false);
        } else { // other user
            if (!doIFollow(user)) {
                model.addAttribute("button", "Follow");
            }

        }
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("firstName", customUserDetails.getFirstName());
        model.addAttribute("lastName", customUserDetails.getLastName());
        model.addAttribute("role", customUserDetails.getRole().name());
        model.addAttribute("followers", listWhoFollowsUser(user).size());
        model.addAttribute("following", listWhoIsUserFollowing(user).size());
        model.addAttribute("blogs", blogService.findBlogByUser(user).size());

        List<Category> categories = categoryService.findAllCategories();
        model.addAttribute("categories", categories);

        return model;
    }

    @Override
    public boolean doIFollow(Users user) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();

        Users mainUser = usersService.findUserByUsername(customUserDetails.getUsername()).orElse(null);

        if (user == null || mainUser == null) {
            throw new SomethingWrong("Error occured try again"); // change to throw an exception to see if session is still going
//            usual error is "cannot convert String to customUserDetails"
        }

        List<Followers> userFollowersList = listWhoFollowsUser(user);
        for (Followers f : userFollowersList) {
            if (f.getFollower().getUsername().equals(mainUser.getUsername())) {
                return true;
            }
        }

        return false;
    }

}
