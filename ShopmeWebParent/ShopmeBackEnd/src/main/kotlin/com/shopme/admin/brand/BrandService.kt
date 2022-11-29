package com.shopme.admin.brand

import com.shopme.common.entity.Brand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BrandService {
    @Autowired
    lateinit var brandRepository: BrandRepository

    fun listByPage(): List<Brand> {
        return brandRepository.findAll()
    }
}