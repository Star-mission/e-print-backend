package com.eprint.repository.mongo;

import com.eprint.entity.mongo.AuditLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuditLogRepository extends MongoRepository<AuditLog, String> {

    List<AuditLog> findByOrderNumber(String orderNumber);

    List<AuditLog> findByUserId(String userId);

    List<AuditLog> findByEntityTypeAndEntityId(String entityType, String entityId);
}
