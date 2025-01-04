package com.itm.ithive.repository;

import com.itm.ithive.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository <Category, Long> {
}
