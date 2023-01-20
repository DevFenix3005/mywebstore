package com.rebirth.mywebstore.domain.repositories;

import com.rebirth.mywebstore.domain.models.PurchaseOrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderProductRepository extends JpaRepository<PurchaseOrderProduct, Long> {
}
