package com.eprint.repository.mysql;

import com.eprint.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByOrderNumber(String orderNumber);

    List<AuditLog> findByEntityTypeAndEntityId(String entityType, String entityId);

    List<AuditLog> findByUserId(String userId);

    List<AuditLog> findByAction(String action);
}
