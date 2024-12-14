package com.itm.ithive.repository;

import com.itm.ithive.model.Blog;
import com.itm.ithive.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByUser(Users user);
}
