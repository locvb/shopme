package com.shopme.admin.category;

import com.shopme.common.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

    @Query("SELECT c from Category c where c.parent.id is NULL")
    public List<Category> listRootCategories(Sort sort);

    @Query("SELECT c from Category c where c.parent.id is NULL")
    public Page<Category> listRootCategories(Pageable pageable);

    @Query("SELECT c from Category c where c.name LIKE %?1%")
    public Page<Category> search(String keyword, Pageable pageable);

    public Category findByName(String name);

    public Category findByAlias(String alias);

    public Long countById(Integer id);

    @Query("UPDATE Category c SET c.enabled = ?2 WHERE c.id = ?1")
    @Modifying
    public void updateEnabledStatus(Integer id, boolean enabled);
}
