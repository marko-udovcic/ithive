package com.itm.ithive.repository;

import com.itm.ithive.model.Blog;
import com.itm.ithive.model.Followers;
import com.itm.ithive.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface FollowersRepository extends JpaRepository<Followers, Long> {
    List<Followers> findByFollower(Users user);
    List<Followers> findByFollowed(Users user);
    void deleteByFollowed(Users user);
}
