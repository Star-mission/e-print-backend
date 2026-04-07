package com.eprint.controller;

import com.eprint.dto.WorkOrderDTO;
import com.eprint.service.WorkOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/workOrders")
@RequiredArgsConstructor
public class WorkOrderController {

    private final WorkOrderService workOrderService;
    private final ObjectMapper objectMapper;

    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public ResponseEntity<WorkOrderDTO> createWorkOrder(
            @RequestParam("workOrderData") String jsonData,
            @RequestParam(value = "files", required = false) List<MultipartFile> files) {

        try {
            log.info("=== 开始创建工程单 ===");
            log.info("接收到的 JSON 数据: {}", jsonData);
            log.info("附件数量: {}", files != null ? files.size() : 0);

            WorkOrderDTO workOrderDTO = objectMapper.readValue(jsonData, WorkOrderDTO.class);
            log.info("工程单反序列化成功 - work_id: {}, customer: {}",
                    workOrderDTO.getWork_id(), workOrderDTO.getCustomer());

            WorkOrderDTO result = workOrderService.createWorkOrder(workOrderDTO, files);
            log.info("工程单创建成功 - work_unique: {}", result.getWork_unique());

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("创建工程单失败", e);
            log.error("错误类型: {}", e.getClass().getName());
            log.error("错误信息: {}", e.getMessage());
            if (e.getCause() != null) {
                log.error("根本原因: {}", e.getCause().getMessage());
            }
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findById")
    public ResponseEntity<WorkOrderDTO> findById(@RequestParam("work_unique") String workUnique) {
        try {
            WorkOrderDTO workOrder = workOrderService.getWorkOrderById(workUnique);
            return ResponseEntity.ok(workOrder);
        } catch (Exception e) {
            log.error("Error finding work order by ID", e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findByClerk")
    public ResponseEntity<List<WorkOrderDTO>> findByClerk(@RequestParam("work_clerk") String clerk) {
        List<WorkOrderDTO> workOrders = workOrderService.getWorkOrdersByClerk(clerk);
        return ResponseEntity.ok(workOrders);
    }

    @GetMapping("/findByAudit")
    public ResponseEntity<List<WorkOrderDTO>> findByAudit(@RequestParam("work_audit") String audit) {
        List<WorkOrderDTO> workOrders = workOrderService.getWorkOrdersByAudit(audit);
        return ResponseEntity.ok(workOrders);
    }

    @GetMapping("/findWithStatus")
    public ResponseEntity<List<WorkOrderDTO>> findByStatus(@RequestParam("workorderstatus") String status) {
        log.info("=== 接收到按状态查询工程单请求 ===");
        log.info("请求参数 - workorderstatus: {}", status);
        try {
            List<WorkOrderDTO> workOrders = workOrderService.getWorkOrdersByStatus(status);
            log.info("查询成功，返回 {} 条工程单", workOrders.size());

            // 打印返回的工程单概要
            if (!workOrders.isEmpty()) {
                log.info("返回的工程单列表:");
                workOrders.forEach(wo -> {
                    log.info("  - work_unique: {}, customer: {}, status: {}",
                            wo.getWork_unique(), wo.getCustomer(), wo.getWorkorderstatus());
                });
            } else {
                log.warn("查询结果为空，没有找到状态为 {} 的工程单", status);
            }

            log.info("=== 按状态查询工程单请求完成 ===");
            return ResponseEntity.ok(workOrders);
        } catch (IllegalArgumentException e) {
            log.error("状态参数错误: {}", status, e);
            log.error("有效的状态值: 草稿, 待审核, 通过, 驳回, 生产中, 完成, 取消");
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("查询工程单时发生错误", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 添加审核员信息
     * POST /api/workOrders/addAuditInfo
     */
    @PostMapping("/addAuditInfo")
    public ResponseEntity<WorkOrderDTO> addAuditInfo(@RequestBody Map<String, String> request) {
        try {
            log.info("=== 开始添加审核员信息 ===");
            String workUnique = request.get("work_unique");
            String work_audit = request.get("work_audit");
            String audit_date = request.get("auditDate");

            log.info("工程单唯一标识: {}", workUnique);
            log.info("审核员: {}", work_audit);
            log.info("审核日期: {}", audit_date);

            WorkOrderDTO result = workOrderService.addAuditInfo(workUnique, work_audit, audit_date);
            log.info("审核员信息添加成功");
            log.info("=== 添加审核员信息完成 ===");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("=== 添加审核员信息失败 ===");
            log.error("错误类型: {}", e.getClass().getName());
            log.error("错误信息: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 添加审核日志
     * POST /api/workOrders/addAuditLog
     */
    @PostMapping("/addAuditLog")
    public ResponseEntity<WorkOrderDTO> addAuditLog(@RequestBody Map<String, Object> request) {
        try {
            log.info("=== 开始添加审核日志 ===");
            String workUnique = (String) request.get("work_unique");
            Map<String, Object> auditLog = (Map<String, Object>) request.get("auditLogs");

            log.info("工程单唯一标识: {}", workUnique);
            log.info("审核日志: {}", auditLog);

            WorkOrderDTO result = workOrderService.addAuditLog(workUnique, auditLog);
            log.info("审核日志添加成功");
            log.info("=== 添加审核日志完成 ===");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("=== 添加审核日志失败 ===");
            log.error("错误类型: {}", e.getClass().getName());
            log.error("错误信息: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<WorkOrderDTO> updateStatus(@RequestBody Map<String, String> request) {
        try {
            String workUnique = request.get("work_unique");
            String status = request.get("workorderstatus");

            WorkOrderDTO result = workOrderService.updateWorkOrderStatus(workUnique, status);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error updating work order status", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/updateProcess")
    public ResponseEntity<WorkOrderDTO> updateProcess(@RequestBody Map<String, Object> request) {
        try {
            String workId = (String) request.get("work_id");
            Integer process = (Integer) request.get("process");
            String dangQianJinDu = (String) request.get("dangQianJinDu");

            WorkOrderDTO result = workOrderService.updateWorkOrderProcess(workId, process, dangQianJinDu);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error updating work order process", e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 为工序分配采购负责人
     * POST /api/workOrders/addHeadPur
     */
    @PostMapping("/addHeadPur")
    public ResponseEntity<WorkOrderDTO> addHeadPur(@RequestBody Map<String, Object> request) {
        try {
            String workUnique = (String) request.get("work_unique");
            Integer intermediaID = (Integer) request.get("intermediaID");
            String headPUR = (String) request.get("head_PUR");

            WorkOrderDTO result = workOrderService.addHeadPur(workUnique, intermediaID, headPUR);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error adding PUR head", e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 为工序分配外发负责人
     * POST /api/workOrders/addHeadOut
     */
    @PostMapping("/addHeadOut")
    public ResponseEntity<WorkOrderDTO> addHeadOut(@RequestBody Map<String, Object> request) {
        try {
            String workUnique = (String) request.get("work_unique");
            Integer intermediaID = (Integer) request.get("intermediaID");
            String headOUT = (String) request.get("head_OUT");

            WorkOrderDTO result = workOrderService.addHeadOut(workUnique, intermediaID, headOUT);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error adding OUT head", e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 为工单分配生产装订负责人
     * POST /api/workOrders/addHeadMnf
     */
    @PostMapping("/addHeadMnf")
    public ResponseEntity<WorkOrderDTO> addHeadMnf(@RequestBody Map<String, Object> request) {
        try {
            String workUnique = (String) request.get("work_unique");
            String headMNF = (String) request.get("head_MNF");

            WorkOrderDTO result = workOrderService.addHeadMnf(workUnique, headMNF);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error adding MNF head", e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 更新采购进度
     * POST /api/workOrders/updateProgressPur
     */
    @PostMapping("/updateProgressPur")
    public ResponseEntity<WorkOrderDTO> updateProgressPur(@RequestBody Map<String, Object> request) {
        try {
            String workUnique = (String) request.get("work_unique");
            Integer intermediaID = (Integer) request.get("intermediaID");
            Integer yiGouJianShu = (Integer) request.get("yiGouJianShu");

            WorkOrderDTO result = workOrderService.updateProgressPur(workUnique, intermediaID, yiGouJianShu);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error updating PUR progress", e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 更新外发进度
     * POST /api/workOrders/updateProgressOut
     */
    @PostMapping("/updateProgressOut")
    public ResponseEntity<WorkOrderDTO> updateProgressOut(@RequestBody Map<String, Object> request) {
        try {
            String workUnique = (String) request.get("work_unique");
            Integer intermediaID = (Integer) request.get("intermediaID");
            String kaiShiRiQi = (String) request.get("kaiShiRiQi");
            String yuQiJieShu = (String) request.get("yuQiJieShu");
            String dangQianJinDu = (String) request.get("dangQianJinDu");

            WorkOrderDTO result = workOrderService.updateProgressOut(
                    workUnique, intermediaID, kaiShiRiQi, yuQiJieShu, dangQianJinDu);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error updating OUT progress", e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 软删除工程单
     * DELETE /api/workOrders/{workUnique}
     */
    @DeleteMapping("/{workUnique}")
    public ResponseEntity<Void> deleteWorkOrder(@PathVariable String workUnique) {
        try {
            workOrderService.deleteWorkOrder(workUnique);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error deleting work order", e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 更新生产装订进度
     * POST /api/workOrders/updateProgressMnf
     */
    @PostMapping("/updateProgressMnf")
    public ResponseEntity<WorkOrderDTO> updateProgressMnf(@RequestBody Map<String, Object> request) {
        try {
            String workUnique = (String) request.get("work_unique");
            Integer zhuangDingJianShu = (Integer) request.get("zhuangDingJianShu");

            WorkOrderDTO result = workOrderService.updateProgressMnf(workUnique, zhuangDingJianShu);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error updating MNF progress", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
