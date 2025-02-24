package org.nexus.nexkartfrontend.common.brand;


import jakarta.persistence.*;
import org.nexus.nexkartfrontend.aws.Constants;
import org.nexus.nexkartfrontend.common.category.Category;

import java.util.HashSet;
import java.util.Set;


@Entity(name = "brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50 , unique = true )
    private String name;

    @Column(nullable = false, length = 50 )
    private String logo;

    @ManyToMany
    @JoinTable(
            name = "brands_categories",
            joinColumns = @JoinColumn(name = "brand_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    public Brand(String acer) {
    }

    public Brand() {

    }
    public Brand(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public String toString() {
        return "Brand [id=" + id + ", name=" + name + ", logo=" + logo + "]";

    }

    @Transient
    public String getLogoPath() {

        if (this.id == null) return "/images/default.png";
        return Constants.S3_BASE_URI + "/brand-logos/" + this.id + "/" + this.logo;

//        return "/brands-logo/" + this.id + "/" + this.logo;

    }

}
