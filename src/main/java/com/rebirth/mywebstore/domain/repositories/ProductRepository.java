package com.rebirth.mywebstore.domain.repositories;

import com.rebirth.mywebstore.domain.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
