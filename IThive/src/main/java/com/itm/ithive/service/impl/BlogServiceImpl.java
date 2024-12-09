package com.itm.ithive.service.impl;

import com.itm.ithive.model.Blog;
import com.itm.ithive.repository.BlogRepository;
import com.itm.ithive.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;

    @Override
    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }
}
