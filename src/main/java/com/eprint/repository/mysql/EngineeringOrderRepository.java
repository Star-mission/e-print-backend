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

    Optional<EngineeringOrder> findByWorkUniqueAndIsDeletedNot(String workUnique, String isDeleted);

    Optional<EngineeringOrder> findByWorkId(String workId);

    List<EngineeringOrder> findByWorkClerkAndIsDeletedNot(String workClerk, String isDeleted);

    List<EngineeringOrder> findByWorkAuditAndIsDeletedNot(String workAudit, String isDeleted);

    List<EngineeringOrder> findByReviewStatusAndIsDeletedNot(EngineeringOrder.OrderStatus reviewStatus, String isDeleted);
}
