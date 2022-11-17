package com.shopme.admin.brand

import com.shopme.common.entity.Brand
import org.assertj.core.api.AssertionsForClassTypes
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.Rollback

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
internal open class BrandRepositoryTest {
    @Autowired
    lateinit var repo: BrandRepository
    @Test
    fun testCreateBrands() {
        val acer = Brand("Acer", "brand-logo.png")
        var savedBrand = repo.save(acer)
        AssertionsForClassTypes.assertThat(savedBrand.name).isEqualTo(acer.name)
        val apple = Brand("apple", "brand-logo.png")
        savedBrand = repo.save(apple)
        AssertionsForClassTypes.assertThat(savedBrand.name).isEqualTo(apple.name)
        val samsung = Brand("Samsung", "brand-logo.png")
        savedBrand = repo.save(samsung)
        AssertionsForClassTypes.assertThat(savedBrand.name).isEqualTo(samsung.name)
    }

    @Test
    fun testPrintAllBrands() {
        val brands = repo.findAll()
        brands.forEach { println(it) }
    }
}