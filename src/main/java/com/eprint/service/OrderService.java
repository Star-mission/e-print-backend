package com.eprint.service;

import com.eprint.dto.OrderDTO;
import com.eprint.entity.Order;
import com.eprint.entity.OrderItem;
import com.eprint.entity.Document;
import com.eprint.entity.AuditLog;
import com.eprint.mapper.OrderMapper;
import com.eprint.repository.mysql.OrderRepository;
import com.eprint.repository.mysql.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 订单业务逻辑服务类
 *
 * 功能：
 * - 订单的创建、查询、更新、删除
 * - 订单状态管理和流转
 * - 文件附件处理
 * - 审计日志记录
 * - 订单通过后自动创建工单
 *
 * 事务管理：
 * - 所有写操作使用 @Transactional 保证数据一致性
 * - 查询操作使用 @Transactional(readOnly = true) 优化性能
 *
 * @author E-Print Team
 */
@Slf4j  // Lombok 注解：自动生成日志对象 log
@Service  // Spring 注解：标记为服务层组件
@RequiredArgsConstructor  // Lombok 注解：自动生成包含 final 字段的构造函数（依赖注入）
public class OrderService {

    // ==========================================
    // 依赖注入（通过构造函数注入）
    // ==========================================

    /**
     * 订单数据访问层（MySQL）
     */
    private final OrderRepository orderRepository;

    /**
     * 审计日志数据访问层（MySQL）
     */
    private final AuditLogRepository auditLogRepository;

    /**
     * 订单 DTO 映射器
     */
    private final OrderMapper orderMapper;

    /**
     * 文件存储服务
     */
    private final FileStorageService fileStorageService;

    /**
     * 工单服务（用于订单通过后自动创建工单）
     */
    private final WorkOrderService workOrderService;

    // ==========================================
    // 核心业务方法
    // ==========================================

    /**
     * 创建或更新订单
     *
     * @param orderDTO 订单数据传输对象
     * @param isDraft 是否为草稿（true=草稿，false=待审核）
     * @param files 附件文件列表
     * @return 创建或更新后的订单 DTO
     *
     * 业务逻辑：
     * 1. 判断是创建新订单还是更新现有订单
     * 2. 如果是新订单，自动生成订单号和版本号
     * 3. 根据 isDraft 参数设置订单状态
     * 4. 处理上传的附件文件
     * 5. 保存订单到数据库
     * 6. 记录审计日志到 MongoDB
     * 7. 返回完整的订单信息（包含审计日志）
     */
    @Transactional  // 开启事务，确保数据一致性
    public OrderDTO createOrder(OrderDTO orderDTO, boolean isDraft, List<MultipartFile> files) {
        log.info("Creating order: {}", orderDTO.getOrder_id());

        Order order;
        boolean isUpdate = false;

        String orderId = orderDTO.getOrder_id();

        if (orderId != null && !orderId.isEmpty()) {
            // 按订单号查找已有订单（不含版本号）
            Optional<Order> existingOpt = orderRepository.findByOrderNumber(orderId);

            if (existingOpt.isEmpty()) {
                // 情况1：库里没有，直接创建，版本号为1
                order = new Order();
                order.setOrderNumber(orderId);
                order.setOrderVer(1);
            } else {
                Order existing = existingOpt.get();
                Order.OrderStatus existingStatus = existing.getStatus();

                if (existingStatus == Order.OrderStatus.驳回) {
                    // 情况2.1：订单被拒绝，覆盖原订单，版本号重置为1
                    order = existing;
                    order.setOrderVer(1);
                    isUpdate = true;
                } else if (existingStatus == Order.OrderStatus.通过
                        || existingStatus == Order.OrderStatus.生产中) {
                    // 情况2.2：订单已通过或生产中，新建版本号+1
                    order = new Order();
                    order.setOrderNumber(orderId);
                    order.setOrderVer(existing.getOrderVer() != null ? existing.getOrderVer() + 1 : 2);
                } else {
                    // 其他状态（草稿、待审核等）：直接覆盖
                    order = existing;
                    isUpdate = true;
                }
            }
        } else {
            // 未提供订单号，自动生成
            order = new Order();
            order.setOrderNumber(generateOrderNumber());
            order.setOrderVer(1);
        }

        // 将 DTO 数据映射到实体对象
        orderMapper.updateOrderFromDTO(orderDTO, order);

        // 设置订单状态
        if (isDraft) {
            order.setStatus(Order.OrderStatus.草稿);
        } else {
            order.setStatus(Order.OrderStatus.待审核);
        }

        // 生成/更新订单唯一标识
        order.setOrderUnique(order.getOrderNumber() + "_" + order.getOrderVer());

        // 处理上传的附件文件
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                // 存储文件并创建文档记录
                Document doc = fileStorageService.storeFile(file, Document.DocumentCategory.OrderAttachment);
                doc.setOrder(order);  // 关联到订单
                order.getDocuments().add(doc);  // 添加到订单的文档列表
            }
        }

        // 保存订单到数据库（级联保存订单明细和文档）
        Order savedOrder = orderRepository.save(order);

        // 记录审计日志到 MongoDB
        createAuditLog(
                isUpdate ? "UPDATE_ORDER" : "CREATE_ORDER",  // 操作类型
                isUpdate ? "订单已更新" : "订单已创建",  // 操作描述
                "Order",  // 实体类型
                savedOrder.getOrderNumber(),  // 实体 ID
                null,  // 旧值
                savedOrder.getOrderUnique()  // 新值
        );

        // 返回 DTO（包含审计日志）
        return orderMapper.toDTO(savedOrder, getAuditLogs(savedOrder.getOrderNumber()));
    }

    /**
     * 根据订单号查询订单详情
     *
     * @param orderNumber 订单号
     * @return 订单 DTO（包含订单明细、附件、审计日志）
     * @throws RuntimeException 如果订单不存在
     */
    @Transactional(readOnly = true)  // 只读事务，优化性能
    public OrderDTO getOrderByNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderNumber));

        List<AuditLog> auditLogs = getAuditLogs(orderNumber);
        return orderMapper.toDTO(order, auditLogs);
    }

    /**
     * 根据订单唯一标识查询订单详情
     *
     * @param orderUnique 订单唯一标识（orderNumber_version）
     * @return 订单 DTO（包含订单明细、附件、审计日志）
     * @throws RuntimeException 如果订单不存在
     */
    @Transactional(readOnly = true)
    public OrderDTO getOrderByUnique(String orderUnique) {
        Order order = orderRepository.findByOrderUnique(orderUnique)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderUnique));

        List<AuditLog> auditLogs = getAuditLogs(order.getOrderNumber());
        return orderMapper.toDTO(order, auditLogs);
    }

    /**
     * 根据业务员姓名查询订单列表
     *
     * @param sales 业务员姓名
     * @return 该业务员的所有订单列表
     *
     * 用途：业务员查看自己负责的订单
     */
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersBySales(String sales) {
        List<Order> orders = orderRepository.findBySales(sales);
        return orders.stream()
                .map(order -> orderMapper.toDTO(order, getAuditLogs(order.getOrderNumber())))
                .collect(Collectors.toList());
    }

    /**
     * 根据审核员姓名查询订单列表
     *
     * @param audit 审核员姓名
     * @return 该审核员相关的所有订单列表
     *
     * 用途：审核员查看待审核或已审核的订单
     */
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByAudit(String audit) {
        List<Order> orders = orderRepository.findByAudit(audit);
        return orders.stream()
                .map(order -> orderMapper.toDTO(order, getAuditLogs(order.getOrderNumber())))
                .collect(Collectors.toList());
    }

    /**
     * 根据订单状态查询订单列表
     *
     * @param statusText 状态文本（草稿、待审核、通过、驳回、生产中、完成、取消）
     * @return 指定状态的所有订单列表
     *
     * 用途：按状态筛选订单
     */
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByStatus(String statusText) {
        Order.OrderStatus status = Order.OrderStatus.valueOf(statusText);
        List<Order> orders = orderRepository.findByStatus(status);
        return orders.stream()
                .map(order -> orderMapper.toDTO(order, getAuditLogs(order.getOrderNumber())))
                .collect(Collectors.toList());
    }

    /**
     * 查询所有订单
     *
     * @return 所有订单列表
     *
     * 用途：管理员查看所有订单
     */
    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> orderMapper.toDTO(order, getAuditLogs(order.getOrderNumber())))
                .collect(Collectors.toList());
    }

    /**
     * 更新订单状态
     *
     * @param orderUnique 订单唯一标识
     * @param statusText 新状态文本
     * @param auditorName 审核员姓名
     * @return 更新后的订单 DTO
     *
     * 业务逻辑：
     * 1. 查找订单
     * 2. 更新状态和审核员
     * 3. 保存到数据库
     * 4. 记录审计日志
     * 5. 如果状态变更为"通过"，自动创建工单
     * 6. 返回更新后的订单信息
     */
    @Transactional
    public OrderDTO updateOrderStatus(String orderUnique, String statusText, String auditorName) {
        log.info("Updating order status: {} to {}", orderUnique, statusText);

        // 查找订单
        Order order = orderRepository.findByOrderUnique(orderUnique)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderUnique));

        // 记录旧状态
        Order.OrderStatus oldStatus = order.getStatus();
        Order.OrderStatus newStatus = Order.OrderStatus.valueOf(statusText);

        // 更新状态
        order.setStatus(newStatus);
        if (auditorName != null) {
            order.setAudit(auditorName);
        }

        // 保存到数据库
        Order savedOrder = orderRepository.save(order);

        // 记录审计日志
        createAuditLog(
                "UPDATE_STATUS",
                "订单状态已更新",
                "Order",
                savedOrder.getOrderNumber(),
                oldStatus.name(),
                newStatus.name()
        );

        // 如果订单状态变更为"通过"，自动创建工单
        if (newStatus == Order.OrderStatus.通过) {
            workOrderService.createWorkOrderFromOrder(savedOrder);
        }

        return orderMapper.toDTO(savedOrder, getAuditLogs(savedOrder.getOrderNumber()));
    }

    /**
     * 删除订单
     *
     * @param id 订单主键 ID
     *
     * 业务逻辑：
     * 1. 查找订单
     * 2. 记录删除操作的审计日志
     * 3. 从数据库删除订单（级联删除订单明细和文档）
     */
    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));

        // 软删除：标记为已删除，不物理删除数据
        order.setIsDeleted("是");
        orderRepository.save(order);

        // 记录删除操作的审计日志
        createAuditLog(
                "DELETE_ORDER",
                "订单已软删除",
                "Order",
                order.getOrderNumber(),
                order.getOrderUnique(),
                null
        );
    }

    // ==========================================
    // 私有辅助方法
    // ==========================================

    /**
     * 生成订单号
     *
     * @return 订单号（格式：AUTO-时间戳-UUID前8位）
     *
     * 示例：AUTO-1710501234567-a1b2c3d4
     */
    private String generateOrderNumber() {
        return "AUTO-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * 获取订单的审计日志列表
     *
     * @param orderNumber 订单号
     * @return 审计日志列表（从 MongoDB 查询）
     */
    private List<AuditLog> getAuditLogs(String orderNumber) {
        return auditLogRepository.findByOrderNumber(orderNumber);
    }

    /**
     * 创建审计日志
     *
     * @param action 操作类型（CREATE_ORDER、UPDATE_ORDER、UPDATE_STATUS、DELETE_ORDER）
     * @param description 操作描述（中文说明）
     * @param entityType 实体类型（Order）
     * @param entityId 实体 ID（订单号）
     * @param oldValue 旧值
     * @param newValue 新值
     *
     * 说明：
     * - 审计日志保存到 MongoDB
     * - 用于追踪所有订单操作历史
     * - 支持合规性审计和问题追溯
     */

    private void createAuditLog(String action, String description, String entityType,
                                String entityId, String oldValue, String newValue) {
        AuditLog auditLog = new AuditLog();
        auditLog.setAction(action);
        auditLog.setActionDescription(description);
        auditLog.setUserId("SYSTEM");  // TODO: 集成真实的用户认证系统后替换为实际用户 ID
        auditLog.setEntityType(entityType);
        auditLog.setEntityId(entityId);
        auditLog.setOrderNumber(entityId);
        auditLog.setOldValue(oldValue);
        auditLog.setNewValue(newValue);
        auditLog.setTime(LocalDateTime.now());

        auditLogRepository.save(auditLog);
    }
}
