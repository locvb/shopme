package com.shopme.admin.brand

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class BrandController {
    @Autowired
    lateinit var brandService: BrandService;

    @GetMapping("/brands")
    fun listBrands(model: Model): String {
        model["listBrands"] = brandService.listByPage()
        return "brands/brands"
    }
}
