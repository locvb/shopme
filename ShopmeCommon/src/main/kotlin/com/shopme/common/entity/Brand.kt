package com.shopme.common.entity

import org.springframework.data.annotation.Transient
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
    @JoinTable(
        name = "brands_categories",
        joinColumns = [JoinColumn(name = "brand_id")],
        inverseJoinColumns = [JoinColumn(name = "category_id")]
    )
    val categories: MutableSet<Category> = HashSet()

    constructor(name: String?, logo: String? = "brand-logo.png") {
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



    @Transient
    fun getLogoPath(): String? {
        return if (id == null) "/images/image-thumbnail.png" else "/brand-images/" + id + "/" + this.logo
    }
}