package com.itm.ithive.service;


import com.itm.ithive.model.Blog;
import com.itm.ithive.model.Users;

import java.util.List;

public interface BlogService {
    public Blog createBlog(Blog blog);

    public List<Blog> listAll();
    public List<Blog> findBlogByUser(Users user);
}
