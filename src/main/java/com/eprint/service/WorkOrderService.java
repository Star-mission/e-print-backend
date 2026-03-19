package com.eprint.service;

import com.eprint.dto.WorkOrderDTO;
import com.eprint.entity.EngineeringOrder;
import com.eprint.entity.MaterialLine;
import com.eprint.entity.Document;
import com.eprint.entity.Order;
import com.eprint.entity.mongo.AuditLog;
import com.eprint.mapper.WorkOrderMapper;
import com.eprint.repository.mysql.EngineeringOrderRepository;
import com.eprint.repository.mongo.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkOrderService {

    private final EngineeringOrderRepository engineeringOrderRepository;
    private final AuditLogRepository auditLogRepository;
    private final WorkOrderMapper workOrderMapper;
    private final FileStorageService fileStorageService;

    @Transactional
    public WorkOrderDTO createWorkOrder(WorkOrderDTO workOrderDTO, List<MultipartFile> files) {
        log.info("Creating work order: {}", workOrderDTO.getWork_id());

        EngineeringOrder workOrder;
        boolean isUpdate = false;

        if (workOrderDTO.getWork_unique() != null && !workOrderDTO.getWork_unique().isEmpty()) {
            workOrder = engineeringOrderRepository.findByWorkUnique(workOrderDTO.getWork_unique())
                    .orElse(new EngineeringOrder());
            if (workOrder.getId() != null) {
                isUpdate = true;
            }
        } else {
            workOrder = new EngineeringOrder();
            workOrder.setEngineeringOrderId(generateWorkOrderId());
            workOrder.setWorkVer(1);
        }

        workOrderMapper.updateWorkOrderFromDTO(workOrderDTO, workOrder);

        if (workOrder.getWorkUnique() == null) {
            workOrder.setWorkUnique(workOrder.getWorkId() + "_" + workOrder.getWorkVer());
        }

        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                Document doc = fileStorageService.storeFile(file, Document.DocumentCategory.WorkOrderAttachment);
                doc.setEngineeringOrder(workOrder);
                workOrder.getDocuments().add(doc);
            }
        }

        EngineeringOrder savedWorkOrder = engineeringOrderRepository.save(workOrder);

        createAuditLog(
                isUpdate ? "UPDATE_WORK_ORDER" : "CREATE_WORK_ORDER",
                isUpdate ? "工单已更新" : "工单已创建",
                "EngineeringOrder",
                savedWorkOrder.getEngineeringOrderId(),
                null,
                savedWorkOrder.getWorkUnique()
        );

        return workOrderMapper.toDTO(savedWorkOrder, getAuditLogs(savedWorkOrder.getEngineeringOrderId()));
    }

    @Transactional
    public void createWorkOrderFromOrder(Order order) {
        log.info("Auto-creating work order from approved order: {}", order.getOrderNumber());

        EngineeringOrder workOrder = new EngineeringOrder();
        workOrder.setEngineeringOrderId(generateWorkOrderId());
        workOrder.setWorkId(order.getOrderNumber());
        workOrder.setWorkVer(1);
        workOrder.setWorkUnique(workOrder.getWorkId() + "_" + workOrder.getWorkVer());
        workOrder.setReviewStatus(EngineeringOrder.OrderStatus.待审核);
        workOrder.setKeHu(order.getCustomer());
        workOrder.setPo(order.getCustomerPO());
        workOrder.setChengPinMingCheng(order.getProductName());
        workOrder.setChuYangShu(order.getChuYangShuLiang());
        workOrder.setChaoBiLi(order.getChaoBiLiShuLiang());

        engineeringOrderRepository.save(workOrder);

        createAuditLog(
                "AUTO_CREATE_WORK_ORDER",
                "订单通过后自动创建工单",
                "EngineeringOrder",
                workOrder.getEngineeringOrderId(),
                order.getOrderNumber(),
                workOrder.getWorkUnique()
        );
    }

    @Transactional(readOnly = true)
    public WorkOrderDTO getWorkOrderById(String workUnique) {
        EngineeringOrder workOrder = engineeringOrderRepository.findByWorkUnique(workUnique)
                .orElseThrow(() -> new RuntimeException("Work order not found: " + workUnique));

        List<AuditLog> auditLogs = getAuditLogs(workOrder.getEngineeringOrderId());
        return workOrderMapper.toDTO(workOrder, auditLogs);
    }

    @Transactional(readOnly = true)
    public List<WorkOrderDTO> getWorkOrdersByClerk(String clerk) {
        List<EngineeringOrder> workOrders = engineeringOrderRepository.findByWorkClerk(clerk);
        return workOrders.stream()
                .map(wo -> workOrderMapper.toDTO(wo, getAuditLogs(wo.getEngineeringOrderId())))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<WorkOrderDTO> getWorkOrdersByAudit(String audit) {
        List<EngineeringOrder> workOrders = engineeringOrderRepository.findByWorkAudit(audit);
        return workOrders.stream()
                .map(wo -> workOrderMapper.toDTO(wo, getAuditLogs(wo.getEngineeringOrderId())))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<WorkOrderDTO> getWorkOrdersByStatus(String statusText) {
        EngineeringOrder.OrderStatus status = EngineeringOrder.OrderStatus.valueOf(statusText);
        List<EngineeringOrder> workOrders = engineeringOrderRepository.findByReviewStatus(status);
        return workOrders.stream()
                .map(wo -> workOrderMapper.toDTO(wo, getAuditLogs(wo.getEngineeringOrderId())))
                .collect(Collectors.toList());
    }

    @Transactional
    public WorkOrderDTO updateWorkOrderStatus(String workUnique, String statusText) {
        log.info("Updating work order status: {} to {}", workUnique, statusText);

        EngineeringOrder workOrder = engineeringOrderRepository.findByWorkUnique(workUnique)
                .orElseThrow(() -> new RuntimeException("Work order not found: " + workUnique));

        EngineeringOrder.OrderStatus oldStatus = workOrder.getReviewStatus();
        EngineeringOrder.OrderStatus newStatus = EngineeringOrder.OrderStatus.valueOf(statusText);

        workOrder.setReviewStatus(newStatus);

        EngineeringOrder savedWorkOrder = engineeringOrderRepository.save(workOrder);

        createAuditLog(
                "UPDATE_WORK_ORDER_STATUS",
                "工单状态已更新",
                "EngineeringOrder",
                savedWorkOrder.getEngineeringOrderId(),
                oldStatus.name(),
                newStatus.name()
        );

        return workOrderMapper.toDTO(savedWorkOrder, getAuditLogs(savedWorkOrder.getEngineeringOrderId()));
    }

    @Transactional
    public WorkOrderDTO updateWorkOrderProcess(String workId, Integer process, String note) {
        log.info("Updating work order process: {} to {}%", workId, process);

        EngineeringOrder workOrder = engineeringOrderRepository.findByWorkId(workId)
                .orElseThrow(() -> new RuntimeException("Work order not found: " + workId));

        if (!workOrder.getMaterialLines().isEmpty()) {
            MaterialLine firstLine = workOrder.getMaterialLines().get(0);
            firstLine.setDangQianJinDu(process != null ? process.toString() : null);
            if (note != null) {
                firstLine.setNotes(note);
            }
        }

        EngineeringOrder savedWorkOrder = engineeringOrderRepository.save(workOrder);

        createAuditLog(
                "UPDATE_WORK_ORDER_PROCESS",
                "工单进度已更新",
                "EngineeringOrder",
                savedWorkOrder.getEngineeringOrderId(),
                null,
                process.toString()
        );

        return workOrderMapper.toDTO(savedWorkOrder, getAuditLogs(savedWorkOrder.getEngineeringOrderId()));
    }

    /**
     * 为工序分配采购负责人
     */
    @Transactional
    public WorkOrderDTO addHeadPur(String workUnique, Integer intermediaID, String headPUR) {
        log.info("Adding PUR head to work order: {} intermedia: {}", workUnique, intermediaID);

        EngineeringOrder workOrder = engineeringOrderRepository.findByWorkUnique(workUnique)
                .orElseThrow(() -> new RuntimeException("Work order not found: " + workUnique));

        List<MaterialLine> materialLines = workOrder.getMaterialLines();
        if (intermediaID >= 0 && intermediaID < materialLines.size()) {
            MaterialLine materialLine = materialLines.get(intermediaID);
            materialLine.setHeadPUR(headPUR);
        } else {
            throw new RuntimeException("Invalid intermedia index: " + intermediaID);
        }

        EngineeringOrder savedWorkOrder = engineeringOrderRepository.save(workOrder);

        createAuditLog(
                "ADD_HEAD_PUR",
                "分配采购负责人",
                "EngineeringOrder",
                savedWorkOrder.getEngineeringOrderId(),
                null,
                headPUR
        );

        return workOrderMapper.toDTO(savedWorkOrder, getAuditLogs(savedWorkOrder.getEngineeringOrderId()));
    }

    /**
     * 为工序分配外发负责人
     */
    @Transactional
    public WorkOrderDTO addHeadOut(String workUnique, Integer intermediaID, String headOUT) {
        log.info("Adding OUT head to work order: {} intermedia: {}", workUnique, intermediaID);

        EngineeringOrder workOrder = engineeringOrderRepository.findByWorkUnique(workUnique)
                .orElseThrow(() -> new RuntimeException("Work order not found: " + workUnique));

        List<MaterialLine> materialLines = workOrder.getMaterialLines();
        if (intermediaID >= 0 && intermediaID < materialLines.size()) {
            MaterialLine materialLine = materialLines.get(intermediaID);
            materialLine.setHeadOUT(headOUT);
        } else {
            throw new RuntimeException("Invalid intermedia index: " + intermediaID);
        }

        EngineeringOrder savedWorkOrder = engineeringOrderRepository.save(workOrder);

        createAuditLog(
                "ADD_HEAD_OUT",
                "分配外发负责人",
                "EngineeringOrder",
                savedWorkOrder.getEngineeringOrderId(),
                null,
                headOUT
        );

        return workOrderMapper.toDTO(savedWorkOrder, getAuditLogs(savedWorkOrder.getEngineeringOrderId()));
    }

    /**
     * 为工单分配生产装订负责人
     */
    @Transactional
    public WorkOrderDTO addHeadMnf(String workUnique, String headMNF) {
        log.info("Adding MNF head to work order: {}", workUnique);

        EngineeringOrder workOrder = engineeringOrderRepository.findByWorkUnique(workUnique)
                .orElseThrow(() -> new RuntimeException("Work order not found: " + workUnique));

        workOrder.setHeadMNF(headMNF);

        EngineeringOrder savedWorkOrder = engineeringOrderRepository.save(workOrder);

        createAuditLog(
                "ADD_HEAD_MNF",
                "分配生产负责人",
                "EngineeringOrder",
                savedWorkOrder.getEngineeringOrderId(),
                null,
                headMNF
        );

        return workOrderMapper.toDTO(savedWorkOrder, getAuditLogs(savedWorkOrder.getEngineeringOrderId()));
    }

    /**
     * 更新采购进度
     */
    @Transactional
    public WorkOrderDTO updateProgressPur(String workUnique, Integer intermediaID, Integer yiGouJianShu) {
        log.info("Updating PUR progress for work order: {} intermedia: {}", workUnique, intermediaID);

        EngineeringOrder workOrder = engineeringOrderRepository.findByWorkUnique(workUnique)
                .orElseThrow(() -> new RuntimeException("Work order not found: " + workUnique));

        List<MaterialLine> materialLines = workOrder.getMaterialLines();
        if (intermediaID >= 0 && intermediaID < materialLines.size()) {
            MaterialLine materialLine = materialLines.get(intermediaID);
            materialLine.setYiGouJianShu(yiGouJianShu);
        } else {
            throw new RuntimeException("Invalid intermedia index: " + intermediaID);
        }

        EngineeringOrder savedWorkOrder = engineeringOrderRepository.save(workOrder);

        createAuditLog(
                "UPDATE_PROGRESS_PUR",
                "更新采购进度",
                "EngineeringOrder",
                savedWorkOrder.getEngineeringOrderId(),
                null,
                yiGouJianShu.toString()
        );

        return workOrderMapper.toDTO(savedWorkOrder, getAuditLogs(savedWorkOrder.getEngineeringOrderId()));
    }

    /**
     * 更新外发进度
     */
    @Transactional
    public WorkOrderDTO updateProgressOut(String workUnique, Integer intermediaID,
                                         String kaiShiRiQi, String yuQiJieShu, String dangQianJinDu) {
        log.info("Updating OUT progress for work order: {} intermedia: {}", workUnique, intermediaID);

        EngineeringOrder workOrder = engineeringOrderRepository.findByWorkUnique(workUnique)
                .orElseThrow(() -> new RuntimeException("Work order not found: " + workUnique));

        List<MaterialLine> materialLines = workOrder.getMaterialLines();
        if (intermediaID >= 0 && intermediaID < materialLines.size()) {
            MaterialLine materialLine = materialLines.get(intermediaID);
            if (kaiShiRiQi != null) {
                materialLine.setKaiShiShiJian(LocalDateTime.parse(kaiShiRiQi));
            }
            if (yuQiJieShu != null) {
                materialLine.setJieShuShiJian(LocalDateTime.parse(yuQiJieShu));
            }
            materialLine.setDangQianJinDu(dangQianJinDu);
        } else {
            throw new RuntimeException("Invalid intermedia index: " + intermediaID);
        }

        EngineeringOrder savedWorkOrder = engineeringOrderRepository.save(workOrder);

        createAuditLog(
                "UPDATE_PROGRESS_OUT",
                "更新外发进度",
                "EngineeringOrder",
                savedWorkOrder.getEngineeringOrderId(),
                null,
                dangQianJinDu
        );

        return workOrderMapper.toDTO(savedWorkOrder, getAuditLogs(savedWorkOrder.getEngineeringOrderId()));
    }

    /**
     * 更新生产装订进度
     */
    @Transactional
    public WorkOrderDTO updateProgressMnf(String workUnique, Integer zhuangDingJianShu) {
        log.info("Updating MNF progress for work order: {}", workUnique);

        EngineeringOrder workOrder = engineeringOrderRepository.findByWorkUnique(workUnique)
                .orElseThrow(() -> new RuntimeException("Work order not found: " + workUnique));

        workOrder.setZhuangDingJianShu(zhuangDingJianShu);

        EngineeringOrder savedWorkOrder = engineeringOrderRepository.save(workOrder);

        createAuditLog(
                "UPDATE_PROGRESS_MNF",
                "更新生产装订进度",
                "EngineeringOrder",
                savedWorkOrder.getEngineeringOrderId(),
                null,
                zhuangDingJianShu.toString()
        );

        return workOrderMapper.toDTO(savedWorkOrder, getAuditLogs(savedWorkOrder.getEngineeringOrderId()));
    }

    private String generateWorkOrderId() {
        return "WORK-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8);
    }

    private List<AuditLog> getAuditLogs(String engineeringOrderId) {
        return auditLogRepository.findByEntityTypeAndEntityId("EngineeringOrder", engineeringOrderId);
    }

    private void createAuditLog(String action, String description, String entityType,
                                String entityId, String oldValue, String newValue) {
        AuditLog auditLog = new AuditLog();
        auditLog.setAction(action);
        auditLog.setActionDescription(description);
        auditLog.setUserId("SYSTEM");
        auditLog.setEntityType(entityType);
        auditLog.setEntityId(entityId);
        auditLog.setOldValue(oldValue);
        auditLog.setNewValue(newValue);
        auditLog.setTime(LocalDateTime.now());

        auditLogRepository.save(auditLog);
    }
}
