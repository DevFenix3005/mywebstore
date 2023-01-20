package com.rebirth.mywebstore.domain.models;

import com.google.common.base.Objects;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "address",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_addrees_by_user", columnNames = {"street", "city", "state", "country", "zipcode", "customer_id"})
        }
)
@AttributeOverride(
        name = "id",
        column = @Column(name = "address_id", updatable = false, insertable = false)
)
public class Address extends Audit {

    private String street;
    private String city;
    private String state;
    private String country;
    private String zipcode;
    private Customer customer;
    private List<PurchaseOrder> purchaseOrderList = new ArrayList<>();

    public Address() {
    }

    public Address(String street, String city, String state, String country, String zipcode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipcode = zipcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equal(street, address.street) &&
                Objects.equal(city, address.city) &&
                Objects.equal(state, address.state) &&
                Objects.equal(country, address.country) &&
                Objects.equal(zipcode, address.zipcode) &&
                Objects.equal(customer.getId(), address.customer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(street, city, state, country, zipcode, customer.getId());
    }

    @Column(name = "street", nullable = false, length = 90)
    public String getStreet() {
        return street;
    }

    @Column(name = "city", nullable = false, length = 90)
    public String getCity() {
        return city;
    }

    @Column(name = "state", nullable = false, length = 90)
    public String getState() {
        return state;
    }

    @Column(name = "country", nullable = false, length = 90)
    public String getCountry() {
        return country;
    }

    @Column(name = "zipcode", nullable = false, length = 90)
    public String getZipcode() {
        return zipcode;
    }

    @JoinColumn(name = "customer_id", nullable = false)
    @ManyToOne(optional = false)
    public Customer getCustomer() {
        return customer;
    }

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<PurchaseOrder> getOrderList() {
        return purchaseOrderList;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setOrderList(List<PurchaseOrder> purchaseOrderList) {
        this.purchaseOrderList = purchaseOrderList;
    }
}
