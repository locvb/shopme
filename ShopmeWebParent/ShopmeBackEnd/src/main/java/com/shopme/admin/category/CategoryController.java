package com.shopme.admin.category;

import com.shopme.admin.FileUploadUtil;
import com.shopme.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class CategoryController {
    private CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/categories")
    public String listAll(Model model) {
        List<Category> categories = service.listAll();
        model.addAttribute("listCategories", categories);
        return "categories/categories";
    }

    @GetMapping("/categories/new")
    public String newCategory(Model model) {
        List<Category> listCategories = service.listCategoriesUsedInForm();

        model.addAttribute("pageTitle", "Create New Category");
        model.addAttribute("category", new Category());
        model.addAttribute("listCategories", listCategories);
        return "categories/category_form";
    }

    @PostMapping("/categories/save")
    public String saveCategory(Category category,
                               @RequestParam("fileImage") MultipartFile multipartFile,
                               RedirectAttributes ra) throws IOException {
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        category.setImage(filename);

        Category savedCategory = service.save(category);
        String uploadDir = "../category-images/" + savedCategory.getId();
        FileUploadUtil.saveFile(uploadDir, filename, multipartFile);

        ra.addFlashAttribute("message", "The category has been saved successfully.");
        return "redirect:/categories";
    }
}
