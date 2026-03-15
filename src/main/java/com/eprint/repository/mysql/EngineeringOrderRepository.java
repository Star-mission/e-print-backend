package com.eprint.repository.mysql;

import com.eprint.entity.EngineeringOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EngineeringOrderRepository extends JpaRepository<EngineeringOrder, Long> {

    Optional<EngineeringOrder> findByEngineeringOrderId(String engineeringOrderId);

    Optional<EngineeringOrder> findByWorkUnique(String workUnique);

    Optional<EngineeringOrder> findByWorkId(String workId);

    List<EngineeringOrder> findByWorkClerk(String workClerk);

    List<EngineeringOrder> findByWorkAudit(String workAudit);

    List<EngineeringOrder> findByReviewStatus(EngineeringOrder.OrderStatus reviewStatus);
}
