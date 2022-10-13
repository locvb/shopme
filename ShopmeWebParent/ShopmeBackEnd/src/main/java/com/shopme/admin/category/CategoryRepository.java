package com.shopme.admin.category;

import com.shopme.common.entity.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

    @Query("SELECT c from Category c where c.parent.id is NULL")
    public List<Category> listRootCategories(Sort sort);

    public Category findByName(String name);

    public Category findByAlias(String alias);
}
