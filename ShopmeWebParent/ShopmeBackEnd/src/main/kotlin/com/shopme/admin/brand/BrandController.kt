package com.shopme.admin.brand

import com.shopme.admin.FileUploadUtil
import com.shopme.admin.category.CategoryService
import com.shopme.common.entity.Brand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes

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

    @GetMapping("/brands/edit/{id}")
    fun editBrand(@PathVariable(name = "id") id: Int, model: Model, ra: RedirectAttributes): String {
        return try {
            val brand = brandService.get(id)
            model["brand"] = brand!!
            model["listCategories"] = categoryService.listCategoriesUsedInForm()!!
            "brands/brand_form"
        } catch(e: Exception) {
            ra.addFlashAttribute("message", e.message)
            "redirect:/brands"
        }
    }

    @PostMapping("/brands/save")
    fun saveBrand(brand: Brand, @RequestParam("fileImage") multipartFile: MultipartFile, ra: RedirectAttributes): String {
        if (!multipartFile.isEmpty) {
            val filename = StringUtils.cleanPath(multipartFile.originalFilename)
            brand.logo = filename

            val savedBrand= brandService.save(brand)

            val uploadDir = "../brand-images/${savedBrand.id}"
            FileUploadUtil.cleanDir(uploadDir)
            FileUploadUtil.saveFile(uploadDir, filename, multipartFile)
        }
        else {
            brandService.save(brand)
        }

        ra.addFlashAttribute("message", "The brand has been saved successfully!")
        return "redirect:/brands"
    }

    @GetMapping("/brands/delete/{id}")
    fun deleteBrand(@PathVariable(name = "id") id: Int, model: Model, ra: RedirectAttributes): String {
        try{
            brandService.delete(id)

            FileUploadUtil.removeDir("../brand-logos/$id")
            ra.addFlashAttribute(
                "message",
                "The brand ID $id has been deleted successfuly"
            )
        }
        catch (ex: BrandNotFoundException) {
            ra.addFlashAttribute("message", ex.message)
        }
        return "redirect:/brands"
    }
}
