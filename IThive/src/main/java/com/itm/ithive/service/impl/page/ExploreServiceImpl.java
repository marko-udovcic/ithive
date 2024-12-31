package com.itm.ithive.service.impl;

import com.itm.ithive.model.Blog;
import com.itm.ithive.model.Followers;
import com.itm.ithive.repository.BlogRepository;
import com.itm.ithive.specification.GenericSpecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExploreServiceImpl {
    private final BlogRepository blogRepository;
    private final UsersServiceImpl usersService;

    @PersistenceContext
    private EntityManager entityManager;

    public Sort getSort(Map<String, String> params) {
        String status = params.getOrDefault("status", "newest");
        if ("newest".equalsIgnoreCase(status)) {
            return Sort.by("createdAt").descending();

        } else if ("title".equalsIgnoreCase(status)) {
            return Sort.by("title").ascending();
        }
        return Sort.unsorted();
    }

    private long countTotalBlogs(String followerId) {
        String countSql = "SELECT COUNT(DISTINCT b.id) FROM Blog b " +
                "WHERE b.user_id != :followerId " +
                "AND b.user_id NOT IN (" +
                "  SELECT f.followed_id FROM Followers f WHERE f.follower_id = :followerId" +
                ")";

        Query countQuery = entityManager.createNativeQuery(countSql);
        countQuery.setParameter("followerId", followerId);

        return ((Number) countQuery.getSingleResult()).longValue();
    }

    public Page<Blog> findBlogsSortedByLikes(String followerId, Pageable pageable) {
        String sql = "SELECT b.* FROM Blog b " +
                "LEFT JOIN Likes l ON b.id = l.blog_id " +
                "WHERE b.user_id != :followerId " +
                "AND b.user_id NOT IN (" +
                "  SELECT f.followed_id FROM Followers f WHERE f.follower_id = :followerId" +
                ") " +
                "GROUP BY b.id " +
                "ORDER BY COUNT(l.id) DESC, b.id";

        Query query = entityManager.createNativeQuery(sql, Blog.class);
        query.setParameter("followerId", followerId);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        @SuppressWarnings("unchecked")
        List<Blog> blogs = query.getResultList();

        return new PageImpl<>(blogs, pageable, countTotalBlogs(followerId));
    }

    public Page<Blog> checkIfUnsorted(String followerId, Specification<Blog> spec, Pageable pageable) {

        Page<Blog> blogPage = findBlogsNotFollowedByUserWithSpec(followerId, spec, pageable);

        if (pageable.getSort().isUnsorted()) {
            List<Blog> shuffledBlogs = new ArrayList<>(blogPage.getContent());
            Collections.shuffle(shuffledBlogs);
            return new PageImpl<>(shuffledBlogs, pageable, blogPage.getTotalElements());
        }

        return blogPage;

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
            String oldValue = params.getOrDefault("oldTitle", "");
            params.put("title", oldValue);
        }
        if (!params.containsKey("status")) {
            String oldValue = params.getOrDefault("oldStatus", "newest");
            params.put("status", oldValue);
        }

        int pageSize = Integer.parseInt(params.getOrDefault("size", "3"));
        int pageNumber = Integer.parseInt(params.getOrDefault("pageNumber", "0"));

        Pageable pageable = PageRequest.of(pageNumber, pageSize, getSort(params));
        Specification<Blog> spec = GenericSpecification.dynamicSearch(params, Blog.class);
        String followerId = usersService.getCurrentUser().getId();


        return params.get("status").equals("most-popular") ?
                findBlogsSortedByLikes(followerId, pageable) :
                checkIfUnsorted(followerId, spec, pageable);

    }
}
