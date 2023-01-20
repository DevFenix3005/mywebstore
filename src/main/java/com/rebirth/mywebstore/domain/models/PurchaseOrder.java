package com.rebirth.mywebstore.domain.models;

import com.google.common.base.Objects;
import com.rebirth.mywebstore.domain.enums.OrderState;
import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "purchase_order",
        indexes = {
                @Index(name = "uq_order_code", columnList = "order_code", unique = true)
        }
)
@AttributeOverride(
        name = "id",
        column = @Column(name = "purchase_order_id", updatable = false, insertable = false)
)
public class PurchaseOrder extends Audit {

    private UUID orderCode;
    private LocalDateTime openingDate;
    private OrderState state;
    private Float total;
    private Integer generatedPoints;
    private Customer customer;
    private Address address;
    private List<PurchaseOrderProduct> products = new ArrayList<>();

    public PurchaseOrder() {
        this.orderCode = UUID.randomUUID();
        this.openingDate = LocalDateTime.now();
        this.state = OrderState.CART;
        this.generatedPoints = 0;
        this.total = 0.0f;
    }

    public PurchaseOrder(Customer customer) {
        this();
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseOrder purchaseOrder = (PurchaseOrder) o;
        return Objects.equal(orderCode, purchaseOrder.orderCode);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderCode);
    }

    @NaturalId
    @Column(name = "order_code", nullable = false)
    public UUID getOrderCode() {
        return orderCode;
    }

    @Column(name = "opening_date", nullable = false)
    public LocalDateTime getOpeningDate() {
        return openingDate;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false, length = 10)
    public OrderState getState() {
        return state;
    }

    @Column(name = "total")
    public Float getTotal() {
        return total;
    }

    @Column(name = "generated_points")
    public Integer getGeneratedPoints() {
        return generatedPoints;
    }

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    public Customer getCustomer() {
        return customer;
    }

    @ManyToOne
    @JoinColumn(name = "address_id")
    public Address getAddress() {
        return address;
    }

    @OneToMany(
            mappedBy = "purchaseOrder",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    public List<PurchaseOrderProduct> getProducts() {
        return products;
    }

    public void setOrderCode(UUID orderCode) {
        this.orderCode = orderCode;
    }

    public void setOpeningDate(LocalDateTime openingDate) {
        this.openingDate = openingDate;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public void setGeneratedPoints(Integer generatedPoints) {
        this.generatedPoints = generatedPoints;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setProducts(List<PurchaseOrderProduct> products) {
        this.products = products;
    }


}
