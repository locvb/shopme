package com.shopme.admin.brand;

import com.shopme.common.entity.Brand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class BrandRepositoryTest {
    @Autowired
    private BrandRepository repo;

    @Test
    void testCreateBrand() {
        Brand brand = new Brand("test", "testlogo");
        Brand savedBrand= repo.save(brand);

        assertThat(savedBrand.getId()).isGreaterThan(0);
    }
}