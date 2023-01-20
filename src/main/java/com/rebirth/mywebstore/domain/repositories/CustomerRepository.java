package com.rebirth.mywebstore.domain.repositories;

import com.rebirth.mywebstore.domain.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findUserByEmail(String email);

    @Query("""
            SELECT SUM(po.generatedPoints)
            FROM Customer u inner join u.purchaseOrders po
            WHERE u.id =:userId AND
                po.openingDate between :initDate and :endDate
            """)
    Long sumAwaredPointInOrderByUserIdAndBetweenCreateAtDate(@Param("userId") Long userId,
                                                             @Param("initDate") LocalDateTime initDate,
                                                             @Param("endDate") LocalDateTime endDate);
}
