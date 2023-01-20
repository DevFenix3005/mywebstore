package com.rebirth.mywebstore.domain.repositories;

import com.rebirth.mywebstore.domain.models.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
}
