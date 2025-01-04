package com.itm.ithive.service.interfaces;


import com.itm.ithive.model.Blog;
import com.itm.ithive.model.Users;
import org.springframework.ui.Model;

import java.util.List;

public interface BlogService {
    public Blog createBlog(Blog blog);

    public List<Blog> listAll();

    public List<Blog> findBlogByUser(Users user);

    public Blog getBlogById(Long id);

    public boolean deleteBlog(Long id);

    public Blog findBlogById(Long id);

    public Model blogSetup(Long blogId, Model model);
}
