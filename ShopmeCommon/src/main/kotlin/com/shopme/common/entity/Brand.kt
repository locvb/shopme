package com.shopme.common.entity

import com.shopme.common.entity.Category
import javax.persistence.*

@Entity
@Table(name = "brands")
class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(length = 128, nullable = false, unique = true)
    var name: String? = null

    @Column(length = 128, nullable = false)
    var logo: String? = null

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "brands_categories", joinColumns = [JoinColumn(name = "brand_id")], inverseJoinColumns = [JoinColumn(name = "category_id")])
    private val categories: Set<Category> = HashSet()

    constructor(name: String?, logo: String?) {
        this.name = name
        this.logo = logo
    }

    constructor() {}

    override fun toString(): String {
        return "Brand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                ", categories=" + categories +
                '}'
    }
}