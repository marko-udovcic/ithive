package com.itm.ithive.service.impl.page;

import com.itm.ithive.model.Blog;
import com.itm.ithive.model.Followers;
import com.itm.ithive.repository.BlogRepository;
import com.itm.ithive.service.impl.user.UsersServiceImpl;
import com.itm.ithive.specification.GenericSpecification;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl {
    private final BlogRepository blogRepository;
    private final UsersServiceImpl usersService;
    private final ExploreServiceImpl exploreService;

    public Page<Blog> findBlogsFromFollowedUsersWithSpec(String followerId, Specification<Blog> spec, Pageable pageable) {
        return blogRepository.findAll(Specification.where(spec).and(
                (root, query, criteriaBuilder) -> {
                    // Subquery to fetch the list of followed user IDs
                    Subquery<String> subquery = query.subquery(String.class);
                    Root<Followers> followersRoot = subquery.from(Followers.class);
                    subquery.select(followersRoot.get("followed").get("id"))
                            .where(criteriaBuilder.equal(followersRoot.get("follower").get("id"), followerId));

                    // Return blogs only from followed users
                    return root.get("user").get("id").in(subquery);
                }
        ), pageable);
    }

    public Page<Blog> showBlogsOfFollowedUsers(Map<String, String> params) {
        if (!params.containsKey("title")) {
            params.put("title", "");
        }

        int pageSize = Integer.parseInt(params.getOrDefault("size", "3"));
        int pageNumber = Integer.parseInt(params.getOrDefault("pageNumber", "0"));

        Pageable pageable = PageRequest.of(pageNumber, pageSize, exploreService.getSort(params));
        Specification<Blog> spec = GenericSpecification.dynamicSearch(params, Blog.class);
        String followerId = usersService.getCurrentUser().getId();

        return findBlogsFromFollowedUsersWithSpec(followerId, spec, pageable);
    }

}
