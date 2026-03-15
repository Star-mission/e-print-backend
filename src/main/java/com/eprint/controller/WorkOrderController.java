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
            @RequestParam("data") String jsonData,
            @RequestParam(value = "files", required = false) List<MultipartFile> files) {

        try {
            log.info("Received work order creation request");
            WorkOrderDTO workOrderDTO = objectMapper.readValue(jsonData, WorkOrderDTO.class);
            WorkOrderDTO result = workOrderService.createWorkOrder(workOrderDTO, files);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error creating work order", e);
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
        List<WorkOrderDTO> workOrders = workOrderService.getWorkOrdersByStatus(status);
        return ResponseEntity.ok(workOrders);
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<WorkOrderDTO> updateStatus(@RequestBody Map<String, String> request) {
        try {
            String workUnique = request.get("work_unique");
            String status = request.get("status");

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
            String note = (String) request.get("note");

            WorkOrderDTO result = workOrderService.updateWorkOrderProcess(workId, process, note);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error updating work order process", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
