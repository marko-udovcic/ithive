package com.itm.ithive.service.impl;

import com.itm.ithive.model.Blog;
import com.itm.ithive.model.Followers;
import com.itm.ithive.repository.BlogRepository;
import com.itm.ithive.specification.GenericSpecification;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.awt.print.Book;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExploreServiceImpl {
    private final BlogRepository blogRepository;
    private final UsersServiceImpl usersService;

    private Sort getSort(Map<String, String> params) {
        String status = params.getOrDefault("status", "newest");
        if ("newest".equalsIgnoreCase(status)) {
            return Sort.by("createdAt").descending();
        } else if ("most-popular".equalsIgnoreCase(status)) {
            return Sort.by("views").descending();
        }
        return Sort.by("createdAt").descending();
    }

    public Page<Blog> findBlogsNotFollowedByUserWithSpec(String followerId, Specification<Blog> spec, Pageable pageable) {
        return blogRepository.findAll(Specification.where(spec).and(
                (root, query, criteriaBuilder) -> {
                    // subquery to fetch the list of followed user IDs
                    Subquery<String> subquery = query.subquery(String.class);
                    Root<Followers> followersRoot = subquery.from(Followers.class);
                    subquery.select(followersRoot.get("followed").get("id"))
                            .where(criteriaBuilder.equal(followersRoot.get("follower").get("id"), followerId));

                    // Return blogs that are NOT from followed users and not from the current user
                    return criteriaBuilder.and(
                            criteriaBuilder.not(root.get("user").get("id").in(subquery)),
                            criteriaBuilder.notEqual(root.get("user").get("id"), followerId) // Exclude blogs by the current user
                    );
                }
        ), pageable);
    }

    public Page<Blog> showBlogsOfNonFollowedUsers(Map<String, String> params) {
        if (!params.containsKey("title")) {
            params.put("title", "");
        }

        int pageSize = Integer.parseInt(params.getOrDefault("size", "3"));
        int pageNumber = Integer.parseInt(params.getOrDefault("pageNumber", "0"));

        Pageable pageable = PageRequest.of(pageNumber, pageSize, getSort(params));
        Specification<Blog> spec = GenericSpecification.dynamicSearch(params, Blog.class);
        String followerId = usersService.getCurrentUser().getId();

        return findBlogsNotFollowedByUserWithSpec(followerId, spec, pageable);
    }
}
