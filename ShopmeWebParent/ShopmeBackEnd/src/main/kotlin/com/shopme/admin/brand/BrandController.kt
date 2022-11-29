package com.shopme.admin.brand

import com.shopme.admin.category.CategoryService
import com.shopme.common.entity.Brand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class BrandController {
    @Autowired
    lateinit var brandService: BrandService;
    @Autowired
    lateinit var categoryService: CategoryService

    @GetMapping("/brands")
    fun listBrands(model: Model): String {
        model["listBrands"] = brandService.listByPage()
        return "brands/brands"
    }

    @GetMapping("/brands/new")
    fun newBrand(model: Model): String {
        model["pageTitle"] = "Brand"
        model["brand"] = Brand()
        model["listCategories"] = categoryService.listCategoriesUsedInForm()
        return  "brands/brand_form"
    }
}
