package com.shopme.admin.category;

import com.shopme.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class CategoryService {
    private CategoryRepository repo;

    @Autowired
    public CategoryService(CategoryRepository repo) {
        this.repo = repo;
    }

    public List<Category> listAll() {
        List<Category> rootCategories = repo.listRootCategories();
        return listHierarchicalCategories(rootCategories);
    };

    private List<Category> listHierarchicalCategories(List<Category> rootCategories) {
        List<Category> hierarchicalCategories = new ArrayList<>();

        for (Category rootCategory : rootCategories) {
            hierarchicalCategories.add(Category.copyFull(rootCategory));

            Set<Category> children = rootCategory.getChildren();
            for (Category subCategory : children) {
                String name = "--" + subCategory.getName();
                hierarchicalCategories.add(Category.copyFull(subCategory, name));
                listSubHierarchicalCategories(hierarchicalCategories, subCategory, 1);
            }
        }

        return hierarchicalCategories;
    }

    private void listSubHierarchicalCategories(List<Category> hierarchicalCategories, Category parent, int level) {
        Set<Category> children = parent.getChildren();
        int newSubLevel = level + 1;
        for (Category subCategory : children) {
            String name = "";
            for (int i = 0; i < newSubLevel; i++) {
                name += "--";
            }
            name += subCategory.getName();
            hierarchicalCategories.add(Category.copyFull(subCategory, name));
            listSubHierarchicalCategories(hierarchicalCategories, subCategory, newSubLevel);
        }

    }

    public List<Category> listCategoriesUsedInForm() {
        List<Category> categoriesUsedInForm = new ArrayList<>();
        Iterable<Category> categoriesInDB = repo.findAll();
        for (Category category : categoriesInDB) {
            if (category.getParent() == null) {
                categoriesUsedInForm.add(Category.copyIdAndName(category));

                Set<Category> children = category.getChildren();
                for (Category subCategory : children) {
                    String name = "--"  + subCategory.getName();
                    categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));

                    listCategoriesUsedInForm(categoriesUsedInForm, subCategory, 1);
                }
            }
        }

        return categoriesUsedInForm;
    }

    private void listCategoriesUsedInForm(List<Category> categoriesUsedInForm, Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;
        Set<Category> children = parent.getChildren();

        for (Category subCategory : children) {
            String name = "";
            for (int i = 0; i < newSubLevel; i++) {
                name += "--";
            }
            name += subCategory.getName();

            categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));
            listCategoriesUsedInForm(categoriesUsedInForm, subCategory, newSubLevel);
        }
    }

    public Category save(Category category) {
        return repo.save(category);
    }

    public Category get(Integer id) throws CategoryNotFoundException {
        try {
            return repo.findById(id).get();
        }
        catch (NoSuchElementException ex) {
            throw new CategoryNotFoundException("Could not find any category with ID " + id);
        }
    }
}
