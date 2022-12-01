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

    fun get(id: Int): Brand? {
        try {
            return brandRepository.findById(id).get()
        }
        catch (ex: NoSuchElementException) {
            throw BrandNotFoundException("Could not find any category with ID $id")
        }
    }

    fun save(brand: Brand): Brand{
        return brandRepository.save(brand)
    }

    fun delete(id: Int) {
        val countById = brandRepository.countById(id)
        if (countById == null || countById == 0L) {
            throw BrandNotFoundException("Could not find any brand wid id $id")
        }

        brandRepository.deleteById(id)
    }
}