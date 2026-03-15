package com.eprint.repository.mysql;

import com.eprint.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderNumber(String orderNumber);

    Optional<Order> findByOrderUnique(String orderUnique);

    List<Order> findBySales(String sales);

    List<Order> findByAudit(String audit);

    List<Order> findByStatus(Order.OrderStatus status);
}
