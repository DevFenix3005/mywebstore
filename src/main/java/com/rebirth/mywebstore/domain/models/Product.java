package com.rebirth.mywebstore.domain.models;

import com.google.common.base.Objects;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product",
        indexes = {
                @Index(name = "unique_name", columnList = "name", unique = true)
        }
)
@NaturalIdCache
@Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
@AttributeOverride(
        name = "id",
        column = @Column(name = "product_id", updatable = false, insertable = false)
)
public class Product extends Audit {

    @NaturalId
    private String name;
    private Float price;
    private String description;
    private String image;

    private List<PurchaseOrderProduct> purchaseOrders = new ArrayList<>();

    public Product() {
    }

    public Product(String name, Float price, String description, String image) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equal(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Column(name = "name", nullable = false, length = 150)
    public String getName() {
        return name;
    }

    @Column(name = "price", nullable = false, precision = 3)
    public Float getPrice() {
        return price;
    }

    @Column(name = "description", nullable = false, length = 300)
    public String getDescription() {
        return description;
    }

    @Column(name = "image", nullable = false)
    public String getImage() {
        return image;
    }

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    public List<PurchaseOrderProduct> getPurchaseOrders() {
        return purchaseOrders;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPurchaseOrders(List<PurchaseOrderProduct> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }
}
