package com.itm.ithive.service.impl;

import com.itm.ithive.model.Blog;
import com.itm.ithive.model.Comments;
import com.itm.ithive.model.Users;
import com.itm.ithive.repository.BlogRepository;
import com.itm.ithive.repository.CommentsRepository;
import com.itm.ithive.repository.LikesRepository;
import com.itm.ithive.service.BlogService;
import com.itm.ithive.service.CommentsService;
import com.itm.ithive.service.LikesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final LikesRepository likesRepository;
    private final CommentsRepository commentsRepository;
    private final BlogRepository blogRepository;
    private final LikesService likesService;
    private final CommentsService commentsService;

    @Override
    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public List<Blog> listAll() {
        return blogRepository.findAll();
    }

    @Override
    public List<Blog> findBlogByUser(Users user) {
        return blogRepository.findByUser(user);
    }

    @Override
    public Blog getBlogById(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public boolean deleteBlog(Long id) {
        if (blogRepository.existsById(id)) {
            Blog blog = findBlogById(id);

            likesRepository.deleteByBlog(blog);
            commentsRepository.deleteByBlog(blog);
            blogRepository.deleteById(id);

            if (!"defaultBlogImage.png".equals(blog.getImgUrl())) {
                deleteBlogImage(blog.getImgUrl());
            }

            return true;
        }
        return false;
    }

    @Override
    public Blog findBlogById(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    public void deleteBlogImage(String fileName) {
        try {
            Path path = new ClassPathResource("static/appUploads").getFile().toPath();
            Path filePath = path.resolve(fileName);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + fileName, e);
        }
    }

    @Override
    public Model blogSetup(Long blogId, Model model) {
        Blog blog = findBlogById(blogId);
        String formattedCreatedAt = blog.getCreatedAt().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
        List<Comments> allComments = commentsService.findCommentsByBlog(blog);
        Map<Long, Integer> commentsMap = new HashMap<>();

        model.addAttribute("blog", blog);
        model.addAttribute("likes", likesService.findLikesByBlog(blog).size());
        model.addAttribute("listOfComments", allComments); // sort by date
        model.addAttribute("numberOfComments", allComments.size());
        model.addAttribute("formattedCreatedAt", formattedCreatedAt);

        for (Comments c : allComments) {
            int count = commentsService.findCommentsByParent(c.getId()).size();
            commentsMap.put(c.getId(), count);
        }
        model.addAttribute("commentsMap", commentsMap);
        model.addAttribute("doILike", likesService.doILike(blog));

        return model;

    }
}
