package com.shopme.admin.brand

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.query.Param
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BrandRestController {
    @Autowired
    private lateinit var service: BrandService;

    @PostMapping("/brands/check_unique")
    fun checkUnique(@Param("id") id: Int?, @Param("name") name: String?): String {
        return service.checkUnique(id = id, name = name)
    }
}