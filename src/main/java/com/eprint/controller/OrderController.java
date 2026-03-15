package com.eprint.controller;

import com.eprint.dto.OrderDTO;
import com.eprint.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 订单控制器 - REST API 接口
 *
 * 提供订单管理的 HTTP 接口：
 * - 订单的创建、查询、更新、删除
 * - 按业务员、审核员、状态查询订单
 * - 订单状态更新
 * - 文件上传处理
 *
 * 基础路径：/api/orders
 *
 * @author E-Print Team
 */
@Slf4j  // Lombok 注解：自动生成日志对象
@RestController  // Spring 注解：标记为 REST 控制器，自动将返回值序列化为 JSON
@RequestMapping("/api/orders")  // 基础路径映射
@RequiredArgsConstructor  // Lombok 注解：自动生成构造函数（依赖注入）
public class OrderController {

    // ==========================================
    // 依赖注入
    // ==========================================

    /**
     * 订单业务逻辑服务
     */
    private final OrderService orderService;

    /**
     * JSON 序列化/反序列化工具
     */
    private final ObjectMapper objectMapper;

    // ==========================================
    // API 端点
    // ==========================================

    /**
     * 健康检查接口
     * GET /api/orders/health
     *
     * @return 服务状态信息
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "OK", "service", "E-Print Backend"));
    }

    /**
     * 创建或更新订单
     * POST /api/orders 或 POST /api/orders/create
     *
     * 请求格式：multipart/form-data
     * @param jsonData 订单 JSON 数据（字符串）
     * @param isDraft 是否为草稿（默认 false）
     * @param files 附件文件列表（可选）
     * @return 创建或更新后的订单信息
     *
     * 说明：
     * - 前端使用 FormData 发送数据
     * - JSON 数据作为字符串传递，需要反序列化
     * - 支持多文件上传
     */
    @PostMapping(value = {"/", "/create"}, consumes = "multipart/form-data")
    public ResponseEntity<OrderDTO> createOrder(
            @RequestParam("data") String jsonData,
            @RequestParam(value = "isDraft", defaultValue = "false") boolean isDraft,
            @RequestParam(value = "files", required = false) List<MultipartFile> files) {

        try {
            log.info("Received order creation request");
            // 将 JSON 字符串反序列化为 OrderDTO 对象
            OrderDTO orderDTO = objectMapper.readValue(jsonData, OrderDTO.class);
            // 调用业务逻辑层处理订单
            OrderDTO result = orderService.createOrder(orderDTO, isDraft, files);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error creating order", e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 根据订单唯一标识查询订单
     * GET /api/orders/findById?order_id=AUTO-123_1
     *
     * @param orderId 订单唯一标识（order_unique）
     * @return 订单详情（包含订单明细、附件、审计日志）
     */
    @GetMapping("/findById")
    public ResponseEntity<OrderDTO> findById(@RequestParam("order_id") String orderId) {
        try {
            OrderDTO order = orderService.getOrderByUnique(orderId);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            log.error("Error finding order by ID", e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 根据业务员姓名查询订单列表
     * GET /api/orders/findBySales?sales=张三
     *
     * @param sales 业务员姓名
     * @return 该业务员的所有订单列表
     */
    @GetMapping("/findBySales")
    public ResponseEntity<List<OrderDTO>> findBySales(@RequestParam("sales") String sales) {
        List<OrderDTO> orders = orderService.getOrdersBySales(sales);
        return ResponseEntity.ok(orders);
    }

    /**
     * 根据审核员姓名查询订单列表
     * GET /api/orders/findByAudit?audit=李四
     *
     * @param audit 审核员姓名
     * @return 该审核员相关的所有订单列表
     */
    @GetMapping("/findByAudit")
    public ResponseEntity<List<OrderDTO>> findByAudit(@RequestParam("audit") String audit) {
        List<OrderDTO> orders = orderService.getOrdersByAudit(audit);
        return ResponseEntity.ok(orders);
    }

    /**
     * 根据订单状态查询订单列表
     * GET /api/orders/status?orderstatus=待审核
     *
     * @param status 订单状态（草稿、待审核、通过、驳回、生产中、完成、取消）
     * @return 指定状态的所有订单列表
     */
    @GetMapping("/status")
    public ResponseEntity<List<OrderDTO>> findByStatus(@RequestParam("orderstatus") String status) {
        List<OrderDTO> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }

    /**
     * 查询所有订单
     * GET /api/orders/all
     *
     * @return 所有订单列表
     */
    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> findAll() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    /**
     * 根据订单主键 ID 查询订单（RESTful 风格）
     * GET /api/orders/{id}
     *
     * @param id 订单主键 ID
     * @return 订单详情
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getById(@PathVariable Long id) {
        try {
            OrderDTO order = orderService.getOrderByNumber(id.toString());
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            log.error("Error getting order by ID", e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 更新订单状态
     * POST /api/orders/updateStatus
     *
     * 请求体（JSON）：
     * {
     *   "order_unique": "AUTO-123_1",
     *   "status": "通过",
     *   "auditor": "李四"
     * }
     *
     * @param request 请求参数 Map
     * @return 更新后的订单信息
     *
     * 说明：
     * - 如果状态变更为"通过"，会自动创建工单
     */
    @PostMapping("/updateStatus")
    public ResponseEntity<OrderDTO> updateStatus(@RequestBody Map<String, String> request) {
        try {
            String orderUnique = request.get("order_unique");
            String status = request.get("status");
            String auditor = request.get("auditor");

            OrderDTO result = orderService.updateOrderStatus(orderUnique, status, auditor);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error updating order status", e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 删除订单
     * DELETE /api/orders/{id}
     *
     * @param id 订单主键 ID
     * @return 204 No Content（成功）或 404 Not Found（订单不存在）
     *
     * 说明：
     * - 级联删除订单明细和附件
     * - 记录删除操作的审计日志
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error deleting order", e);
            return ResponseEntity.notFound().build();
        }
    }
}
