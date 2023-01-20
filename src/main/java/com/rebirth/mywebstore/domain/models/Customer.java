package com.rebirth.mywebstore.domain.models;

import com.google.common.base.Objects;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer", indexes = {@Index(name = "unique_email", unique = true, columnList = "email")})
@AttributeOverride(name = "id",
        column = @Column(name = "customer_id", updatable = false, insertable = false)
)
public class Customer extends Audit {

    private String name;
    private String lastname;
    private LocalDate birthday;
    private String email;
    private List<Address> addressList = new ArrayList<>();
    private List<PurchaseOrder> purchaseOrders = new ArrayList<>();


    public Customer() {
    }

    public Customer(String name, String lastname, LocalDate birthday, String email) {
        this.name = name;
        this.lastname = lastname;
        this.birthday = birthday;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equal(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    @Column(name = "lastname", nullable = false, length = 45)
    public String getLastname() {
        return lastname;
    }

    @Column(name = "birthday", nullable = false)
    public LocalDate getBirthday() {
        return birthday;
    }

    @Column(name = "email", nullable = false, length = 90)
    public String getEmail() {
        return email;
    }

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Address> getAddressList() {
        return addressList;
    }

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public void setPurchaseOrders(List<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }
}

