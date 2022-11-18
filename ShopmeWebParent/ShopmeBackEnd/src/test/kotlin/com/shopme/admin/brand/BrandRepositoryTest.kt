package com.shopme.admin.brand

import com.shopme.common.entity.Brand
import com.shopme.common.entity.Category
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
        val acer = Brand("Acer")
        acer.categories.add(Category(5))
        var savedBrand = repo.save(acer)
        AssertionsForClassTypes.assertThat(savedBrand.name).isEqualTo(acer.name)

        val apple = Brand("apple")
        apple.categories.addAll(
            listOf(
                Category(4),
                Category(7)
            )
        )
        savedBrand = repo.save(apple)
        AssertionsForClassTypes.assertThat(savedBrand.name).isEqualTo(apple.name)

        val samsung = Brand("Samsung")
        samsung.categories.addAll(
            listOf(
                Category(29),
                Category(24)
            )
        )
        savedBrand = repo.save(samsung)
        AssertionsForClassTypes.assertThat(savedBrand.name).isEqualTo(samsung.name)
    }

    @Test
    fun testPrintAllBrands() {
        val brands = repo.findAll()
        brands.forEach { println(it) }
    }

    @Test
    fun testGetBranById() {
        val findingId = 5
        val brand = repo.findById(findingId).get()
        AssertionsForClassTypes.assertThat(brand.id).isEqualTo(findingId)
    }

    @Test
    fun testUpdateBrandName() {
        val brandName = "Samsung"
        val brand = repo.getBrandByName(brandName)

        AssertionsForClassTypes.assertThat(brand?.name).isEqualTo(brandName)

        val newBrandName = "Samsung Electronics"
        brand?.name = newBrandName

        val savedBrand = brand?.let { repo.save(it) }
        AssertionsForClassTypes.assertThat(savedBrand?.name).isEqualTo(newBrandName)
    }

    @Test
    fun testDeleteBrand() {
        val deletingId = 5
        repo.deleteById(deletingId)
    }
}