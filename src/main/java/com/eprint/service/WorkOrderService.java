package com.eprint.service;

import com.eprint.dto.WorkOrderDTO;
import com.eprint.entity.EngineeringOrder;
import com.eprint.entity.MaterialLine;
import com.eprint.entity.Document;
import com.eprint.entity.Order;
import com.eprint.entity.AuditLog;
import com.eprint.mapper.WorkOrderMapper;
import com.eprint.repository.mysql.EngineeringOrderRepository;
import com.eprint.repository.mysql.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        log.info("=== WorkOrderService.createWorkOrder 开始 ===");
        log.info("work_id: {}, work_unique: {}", workOrderDTO.getWork_id(), workOrderDTO.getWork_unique());

        EngineeringOrder workOrder;
        boolean isUpdate = false;

        if (workOrderDTO.getWork_unique() != null && !workOrderDTO.getWork_unique().isEmpty()) {
            log.info("使用已有工程单唯一标识: {}", workOrderDTO.getWork_unique());
            workOrder = engineeringOrderRepository.findByWorkUnique(workOrderDTO.getWork_unique())
                    .orElseGet(() -> {
                        log.info("未找到已有工程单，创建新工程单");
                        EngineeringOrder newWorkOrder = new EngineeringOrder();
                        newWorkOrder.setEngineeringOrderId(generateWorkOrderId());
                        newWorkOrder.setWorkVer(1);
                        return newWorkOrder;
                    });
            if (workOrder.getId() != null) {
                log.info("找到已有工程单，ID: {}", workOrder.getId());
                isUpdate = true;
            }
        } else {
            log.info("创建全新工程单");
            workOrder = new EngineeringOrder();
            workOrder.setEngineeringOrderId(generateWorkOrderId());
            workOrder.setWorkVer(1);
        }

        log.info("开始映射 DTO 到 Entity");
        workOrderMapper.updateWorkOrderFromDTO(workOrderDTO, workOrder);
        log.info("映射完成");

        if (workOrder.getWorkUnique() == null) {
            workOrder.setWorkUnique(workOrder.getWorkId() + "_" + workOrder.getWorkVer());
            log.info("生成 work_unique: {}", workOrder.getWorkUnique());
        }

        if (files != null && !files.isEmpty()) {
            log.info("处理 {} 个附件", files.size());
            for (MultipartFile file : files) {
                Document doc = fileStorageService.storeFile(file, Document.DocumentCategory.WorkOrderAttachment);
                doc.setEngineeringOrder(workOrder);
                workOrder.getDocuments().add(doc);
            }
        }

        log.info("保存工程单到数据库");
        EngineeringOrder savedWorkOrder = engineeringOrderRepository.save(workOrder);
        log.info("工程单保存成功，ID: {}", savedWorkOrder.getId());

        log.info("创建审计日志");
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

    /**
     * 从订单自动创建工单（已废弃）
     *
     * @deprecated 此方法已不再使用。工程单创建已改为由前端在审核通过时手动创建。
     *             前端通过 POST /api/workOrders/create 接口提交工程单数据。
     *             保留此方法仅用于向后兼容或特殊场景。
     */
    @Deprecated
    @Transactional
    public void createWorkOrderFromOrder(Order order) {
        log.info("=== 从订单自动创建工单开始 ===");
        log.info("订单号: {}", order.getOrderNumber());
        log.info("客户: {}", order.getCustomer());
        log.info("产品: {}", order.getProductName());

        EngineeringOrder workOrder = new EngineeringOrder();
        String workOrderId = generateWorkOrderId();
        log.info("生成工单号: {}", workOrderId);

        workOrder.setEngineeringOrderId(workOrderId);
        workOrder.setWorkId(order.getOrderNumber());
        workOrder.setWorkVer(1);
        workOrder.setWorkUnique(workOrder.getWorkId() + "_" + workOrder.getWorkVer());
        workOrder.setReviewStatus(EngineeringOrder.OrderStatus.待审核);
        workOrder.setKeHu(order.getCustomer());
        workOrder.setPo(order.getCustomerPO());
        workOrder.setChengPinMingCheng(order.getProductName());
        workOrder.setChuYangShu(order.getChuYangShuLiang());
        workOrder.setChaoBiLi(order.getChaoBiLiShuLiang());

        log.info("保存工单到数据库 - workUnique: {}", workOrder.getWorkUnique());
        engineeringOrderRepository.save(workOrder);
        log.info("工单保存成功");

        log.info("记录审计日志");
        createAuditLog(
                "AUTO_CREATE_WORK_ORDER",
                "订单通过后自动创建工单",
                "EngineeringOrder",
                workOrder.getEngineeringOrderId(),
                order.getOrderNumber(),
                workOrder.getWorkUnique()
        );

        log.info("=== 工单创建完成 ===");
    }

    @Transactional(readOnly = true)
    public WorkOrderDTO getWorkOrderById(String workUnique) {
        EngineeringOrder workOrder = engineeringOrderRepository.findByWorkUnique(workUnique)
                .orElseThrow(() -> new RuntimeException("Work order not found: " + workUnique));

        if ("是".equals(workOrder.getIsDeleted())) {
            throw new RuntimeException("Work order has been deleted: " + workUnique);
        }

        List<AuditLog> auditLogs = getAuditLogs(workOrder.getEngineeringOrderId());
        return workOrderMapper.toDTO(workOrder, auditLogs);
    }

    @Transactional(readOnly = true)
    public List<WorkOrderDTO> getWorkOrdersByClerk(String clerk) {
        List<EngineeringOrder> workOrders = engineeringOrderRepository.findByWorkClerkAndIsDeletedNot(clerk, "是");
        return workOrders.stream()
                .map(wo -> workOrderMapper.toDTO(wo, getAuditLogs(wo.getEngineeringOrderId())))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<WorkOrderDTO> getWorkOrdersByAudit(String audit) {
        List<EngineeringOrder> workOrders = engineeringOrderRepository.findByWorkAuditAndIsDeletedNot(audit, "是");
        return workOrders.stream()
                .map(wo -> workOrderMapper.toDTO(wo, getAuditLogs(wo.getEngineeringOrderId())))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<WorkOrderDTO> getWorkOrdersByStatus(String statusText) {
        log.info("=== 开始查询工程单 - 按状态 ===");
        log.info("查询状态: {}", statusText);

        EngineeringOrder.OrderStatus status = EngineeringOrder.OrderStatus.valueOf(statusText);
        log.info("状态枚举转换成功: {}", status);

        List<EngineeringOrder> workOrders = engineeringOrderRepository.findByReviewStatusAndIsDeletedNot(status, "是");
        log.info("数据库查询完成，找到 {} 条工程单", workOrders.size());

        if (workOrders.isEmpty()) {
            log.warn("未找到状态为 {} 的工程单", statusText);
            return List.of();
        }

        // 打印每个工程单的基本信息
        workOrders.forEach(wo -> {
            log.info("工程单详情 - work_unique: {}, work_id: {}, customer: {}, status: {}, isDeleted: {}",
                    wo.getWorkUnique(), wo.getWorkId(), wo.getKeHu(), wo.getReviewStatus(), wo.getIsDeleted());
        });

        List<WorkOrderDTO> result = workOrders.stream()
                .map(wo -> workOrderMapper.toDTO(wo, getAuditLogs(wo.getEngineeringOrderId())))
                .collect(Collectors.toList());

        log.info("DTO 转换完成，返回 {} 条记录", result.size());
        log.info("=== 查询工程单完成 ===");

        return result;
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

    /**
     * 添加审核员信息
     */
    @Transactional
    public WorkOrderDTO addAuditInfo(String workUnique, String workAudit, String auditDate) {
        log.info("Adding audit info to work order: {}", workUnique);
        log.info("Auditor: {}, Audit date: {}", workAudit, auditDate);

        EngineeringOrder workOrder = engineeringOrderRepository.findByWorkUnique(workUnique)
                .orElseThrow(() -> new RuntimeException("Work order not found: " + workUnique));

        workOrder.setWorkAudit(workAudit);
        workOrder.setAuditDate(parseDate(auditDate));

        EngineeringOrder savedWorkOrder = engineeringOrderRepository.save(workOrder);

        createAuditLog(
                "ADD_AUDIT_INFO",
                "添加审核员信息",
                "EngineeringOrder",
                savedWorkOrder.getEngineeringOrderId(),
                null,
                workAudit
        );

        return workOrderMapper.toDTO(savedWorkOrder, getAuditLogs(savedWorkOrder.getEngineeringOrderId()));
    }

    /**
     * 添加审核日志
     */
    @Transactional
    public WorkOrderDTO addAuditLog(String workUnique, Map<String, Object> auditLogData) {
        log.info("Adding audit log to work order: {}", workUnique);

        EngineeringOrder workOrder = engineeringOrderRepository.findByWorkUnique(workUnique)
                .orElseThrow(() -> new RuntimeException("Work order not found: " + workUnique));

        String operator = (String) auditLogData.get("operator");
        String action = (String) auditLogData.get("action");
        String comment = (String) auditLogData.get("comment");

        createAuditLog(
                action != null ? action : "AUDIT_LOG",
                comment != null ? comment : "审核日志",
                "EngineeringOrder",
                workOrder.getEngineeringOrderId(),
                null,
                operator
        );

        return workOrderMapper.toDTO(workOrder, getAuditLogs(workOrder.getEngineeringOrderId()));
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
     * 按 intermediaID 为工序分配采购负责人
     */
    @Transactional
    public WorkOrderDTO addHeadPur(String workUnique, Integer intermediaID, String headPUR) {
        log.info("Adding PUR head to work order: {} intermediaID: {}", workUnique, intermediaID);

        EngineeringOrder workOrder = engineeringOrderRepository.findByWorkUnique(workUnique)
                .orElseThrow(() -> new RuntimeException("Work order not found: " + workUnique));

        MaterialLine materialLine = findMaterialLineByIntermediaID(workOrder, intermediaID);
        materialLine.setHeadPUR(headPUR);

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
     * 按 intermediaID 为工序分配外发负责人
     */
    @Transactional
    public WorkOrderDTO addHeadOut(String workUnique, Integer intermediaID, String headOUT) {
        log.info("Adding OUT head to work order: {} intermediaID: {}", workUnique, intermediaID);

        EngineeringOrder workOrder = engineeringOrderRepository.findByWorkUnique(workUnique)
                .orElseThrow(() -> new RuntimeException("Work order not found: " + workUnique));

        MaterialLine materialLine = findMaterialLineByIntermediaID(workOrder, intermediaID);
        materialLine.setHeadOUT(headOUT);

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
     * 按 intermediaID 更新采购进度
     */
    @Transactional
    public WorkOrderDTO updateProgressPur(String workUnique, Integer intermediaID, Integer yiGouJianShu) {
        log.info("Updating PUR progress for work order: {} intermediaID: {}", workUnique, intermediaID);

        EngineeringOrder workOrder = engineeringOrderRepository.findByWorkUnique(workUnique)
                .orElseThrow(() -> new RuntimeException("Work order not found: " + workUnique));

        MaterialLine materialLine = findMaterialLineByIntermediaID(workOrder, intermediaID);
        materialLine.setYiGouJianShu(yiGouJianShu);

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
     * 按 intermediaID 更新外发进度
     */
    @Transactional
    public WorkOrderDTO updateProgressOut(String workUnique, Integer intermediaID,
                                         String kaiShiRiQi, String yuQiJieShu, String dangQianJinDu) {
        log.info("Updating OUT progress for work order: {} intermediaID: {}", workUnique, intermediaID);

        EngineeringOrder workOrder = engineeringOrderRepository.findByWorkUnique(workUnique)
                .orElseThrow(() -> new RuntimeException("Work order not found: " + workUnique));

        MaterialLine materialLine = findMaterialLineByIntermediaID(workOrder, intermediaID);
        if (kaiShiRiQi != null) {
            materialLine.setKaiShiShiJian(parseDate(kaiShiRiQi));
        }
        if (yuQiJieShu != null) {
            materialLine.setJieShuShiJian(parseDate(yuQiJieShu));
        }
        materialLine.setDangQianJinDu(dangQianJinDu);

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

    /**
     * 软删除工程单
     */
    @Transactional
    public void deleteWorkOrder(String workUnique) {
        EngineeringOrder workOrder = engineeringOrderRepository.findByWorkUnique(workUnique)
                .orElseThrow(() -> new RuntimeException("Work order not found: " + workUnique));

        workOrder.setIsDeleted("是");
        engineeringOrderRepository.save(workOrder);

        createAuditLog(
                "DELETE_WORK_ORDER",
                "工程单已软删除",
                "EngineeringOrder",
                workOrder.getEngineeringOrderId(),
                workOrder.getWorkUnique(),
                null
        );
    }

    private String generateWorkOrderId() {
        return "WORK-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8);
    }

    private List<AuditLog> getAuditLogs(String engineeringOrderId) {
        return auditLogRepository.findByEntityTypeAndEntityId("EngineeringOrder", engineeringOrderId);
    }

    private LocalDateTime parseDate(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) return null;
        try {
            String normalized = dateStr.trim();
            if (normalized.contains(" ")) {
                normalized = normalized.replace(" ", "T");
            }
            if (normalized.length() == 10) {
                return LocalDate.parse(normalized).atStartOfDay();
            }
            return LocalDateTime.parse(normalized).toLocalDate().atStartOfDay();
        } catch (Exception e) {
            return null;
        }
    }


    private MaterialLine findMaterialLineByIntermediaID(EngineeringOrder workOrder, Integer intermediaID) {
        if (intermediaID == null) {
            throw new RuntimeException("intermediaID cannot be null");
        }

        return workOrder.getMaterialLines().stream()
                .filter(line -> intermediaID.equals(line.getIntermediaID()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "MaterialLine not found for intermediaID: " + intermediaID + ", workUnique: " + workOrder.getWorkUnique()));
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
