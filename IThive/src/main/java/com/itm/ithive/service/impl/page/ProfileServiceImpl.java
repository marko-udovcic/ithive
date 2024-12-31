package com.itm.ithive.service.impl.page;

import com.itm.ithive.exceptions.BlogNotFound;
import com.itm.ithive.exceptions.UserNotFound;
import com.itm.ithive.model.Blog;
import com.itm.ithive.model.Category;
import com.itm.ithive.model.Enums.Role;
import com.itm.ithive.model.Enums.Status;
import com.itm.ithive.model.Users;
import com.itm.ithive.service.interfaces.BlogService;
import com.itm.ithive.service.interfaces.CategoryService;
import com.itm.ithive.service.interfaces.UploadService;
import com.itm.ithive.service.interfaces.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl {

    private final UsersService usersService;
    private final BlogService blogService;
    private final CategoryService categoryService;
    private final UploadService uploadService;


    public void setUserStatusToPending(Model model) {
        Users user = usersService.getCurrentUser();
        if (user != null) {
            user.setStatus(Status.Pending);
            usersService.createUsers(user);
        } else {
            throw new RuntimeException("user not found");
        }
    }


    public void updateUserStatusAndRole(String username, String status, String role) {
        Optional<Users> usersOptional = usersService.findUserByUsername(username);

        if (usersOptional.isPresent()) {
            Users user = usersOptional.get();
            status = role.equals("User") ? "Default" : "Approved";
            user.setStatus(Status.valueOf(status));
            user.setRole(Role.valueOf(role));
            usersService.createUsers(user);
        } else {
            throw new UserNotFound("User not found!");
        }
    }


    public void updateBlog(String title, Long id, String content) {
        Optional<Blog> optionalBlog = Optional.ofNullable(blogService.findBlogById(id));

        if (optionalBlog.isPresent()) {
            Blog blog = optionalBlog.get();
            blog.setTitle(title);
            blog.setContent(content);
            blogService.createBlog(blog);
        } else {
            throw new BlogNotFound("Blog not found");
        }
    }


    public void addNewBlog(String title, Long categoryId, String content, String image_name) {
        image_name = image_name + ".jpeg";
        Users currentUser = usersService.getCurrentUser();
        Category selectedCategory = categoryService.getCategoryById(categoryId);


        if (currentUser != null && selectedCategory != null) {
            Blog newBlog = Blog.builder().title(title)
                    .content(content)
                    .user(currentUser)
                    .category(selectedCategory)
                    .createdAt(LocalDateTime.now())
                    .imgUrl(uploadService.imageExists(image_name) ?
                            image_name : "defaultBlogImage.png")
                    .build();

            blogService.createBlog(newBlog);
        } else {
            throw new UserNotFound("User cannot be found! Redirection to login page in few seconds...");
        }
    }
}
