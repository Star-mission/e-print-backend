package com.eprint.repository.mysql;

import com.eprint.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderNumber(String orderNumber);

    Optional<Order> findByOrderNumberAndIsDeletedNot(String orderNumber, String isDeleted);

    Optional<Order> findByOrderUnique(String orderUnique);

    Optional<Order> findByOrderUniqueAndIsDeletedNot(String orderUnique, String isDeleted);

    List<Order> findBySalesAndIsDeletedNot(String sales, String isDeleted);

    List<Order> findByAuditAndIsDeletedNot(String audit, String isDeleted);

    List<Order> findByStatusAndIsDeletedNot(Order.OrderStatus status, String isDeleted);

    List<Order> findAllByIsDeletedNot(String isDeleted);
}
