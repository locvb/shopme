package com.shopme.admin.brand

import com.shopme.common.entity.Brand
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface BrandRepository : PagingAndSortingRepository<Brand, Int> {
    override fun findAll(): List<Brand>

    fun getBrandByName(name: String?): Brand?
    fun countById(id: Int): Long
}