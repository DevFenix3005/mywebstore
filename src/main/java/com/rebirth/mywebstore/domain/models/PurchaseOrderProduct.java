package com.rebirth.mywebstore.domain.models;

import com.google.common.base.Objects;
import jakarta.persistence.*;

@Entity
@Table(name = "purchase_order_product",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_product_in_order", columnNames = {"purchase_order_id", "product_id"})
        }
)
@AttributeOverride(
        name = "id",
        column = @Column(name = "purchase_order_product_id", updatable = false, insertable = false)
)
public class PurchaseOrderProduct extends Audit {

    private PurchaseOrder purchaseOrder;
    private Product product;
    private Float currentUnitPrice;
    private Integer quantity;

    public PurchaseOrderProduct() {
    }

    public PurchaseOrderProduct(PurchaseOrder purchaseOrder, Product product) {
        this.purchaseOrder = purchaseOrder;
        this.product = product;
        this.currentUnitPrice = product.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseOrderProduct that = (PurchaseOrderProduct) o;
        return Objects.equal(purchaseOrder, that.purchaseOrder) &&
                Objects.equal(product, that.product) &&
                Objects.equal(currentUnitPrice, that.currentUnitPrice) &&
                Objects.equal(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(purchaseOrder, product, currentUnitPrice, quantity);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    public Product getProduct() {
        return product;
    }

    @Column(name = "current_unit_price", nullable = false, precision = 3)
    public Float getCurrentUnitPrice() {
        return currentUnitPrice;
    }

    @Column(name = "quantity", nullable = false, precision = 3)
    public Integer getQuantity() {
        return quantity;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.currentUnitPrice = product.getPrice();
    }

    public void setCurrentUnitPrice(Float currentUnitPrice) {
        this.currentUnitPrice = currentUnitPrice;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
