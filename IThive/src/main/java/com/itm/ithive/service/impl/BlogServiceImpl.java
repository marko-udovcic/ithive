package com.itm.ithive.service.impl;

import com.itm.ithive.model.Blog;
import com.itm.ithive.repository.BlogRepository;
import com.itm.ithive.service.BlogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {
    private BlogRepository blogRepository;

    @Override
    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }
}
