package com.eprint.mapper;

import com.eprint.dto.OrderDTO;
import com.eprint.dto.ProductDTO;
import com.eprint.dto.AttachmentDTO;
import com.eprint.dto.AuditLogDTO;
import com.eprint.entity.Order;
import com.eprint.entity.OrderItem;
import com.eprint.entity.Document;
import com.eprint.entity.AuditLog;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderDTO toDTO(Order order, List<AuditLog> auditLogs) {
        OrderDTO dto = new OrderDTO();

        dto.setOrder_id(order.getOrderNumber());
        dto.setOrder_ver(order.getOrderVer());
        dto.setOrder_unique(order.getOrderUnique());
        dto.setOrderstatus(order.getStatus() != null ? order.getStatus().name() : null);
        dto.setSales(order.getSales());
        dto.setAudit(order.getAudit());
        dto.setCustomer(order.getCustomer());
        dto.setProductName(order.getProductName());
        dto.setCustomerPO(order.getCustomerPO());
        dto.setIsbn(order.getIsbn());

        dto.setDingDanShuLiang(order.getDingDanShuLiang());
        dto.setChuYangShuLiang(order.getChuYangShuLiang());
        dto.setChaoBiLiShuLiang(order.getChaoBiLiShuLiang());

        dto.setGuigeGaoMm(order.getGuigeGaoMm());
        dto.setGuigeKuanMm(order.getGuigeKuanMm());
        dto.setGuigeHouMm(order.getGuigeHouMm());

        dto.setFuLiaoShuoMing(order.getFuLiaoShuoMing());
        dto.setWuLiaoShuoMing(order.getWuLiaoShuoMing());
        dto.setZhiLiangYaoQiu(order.getZhiLiangYaoQiu());
        dto.setBeiZhu(order.getBeiZhu());
        dto.setKeLaiXinXi(order.getKeLaiXinXi());

        dto.setXiaZiliaodaiRiqi1(order.getXiaZiliaodaiRiqi1());
        dto.setXiaZiliaodaiRiqi2(order.getXiaZiliaodaiRiqi2());
        dto.setYinzhangRiqi1(order.getYinzhangRiqi1());
        dto.setYinzhangRiqi2(order.getYinzhangRiqi2());
        dto.setJiaoHuoRiQi1(order.getJiaoHuoRiQi1());
        dto.setJiaoHuoRiQi2(order.getJiaoHuoRiQi2());

        dto.setYeWuDaiBiaoFenJi(order.getYeWuDaiBiaoFenJi());
        dto.setShenHeRen(order.getShenHeRen());
        dto.setDaYinRen(order.getDaYinRen());

        dto.setChanPinMingXi(order.getOrderItems().stream()
                .map(this::toProductDTO)
                .collect(Collectors.toList()));

        dto.setAttachments(order.getDocuments().stream()
                .map(this::toAttachmentDTO)
                .collect(Collectors.toList()));

        if (auditLogs != null) {
            dto.setAuditLogs(auditLogs.stream()
                    .map(this::toAuditLogDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public void updateOrderFromDTO(OrderDTO dto, Order order) {
        if (dto.getOrder_id() != null) {
            order.setOrderNumber(dto.getOrder_id());
        }
        if (dto.getOrder_ver() != null) {
            order.setOrderVer(dto.getOrder_ver());
        }
        if (dto.getOrder_unique() != null) {
            order.setOrderUnique(dto.getOrder_unique());
        }

        order.setSales(dto.getSales());
        order.setAudit(dto.getAudit());
        order.setCustomer(dto.getCustomer());
        order.setProductName(dto.getProductName());
        order.setCustomerPO(dto.getCustomerPO());
        order.setIsbn(dto.getIsbn());

        order.setDingDanShuLiang(dto.getDingDanShuLiang());
        order.setChuYangShuLiang(dto.getChuYangShuLiang());
        order.setChaoBiLiShuLiang(dto.getChaoBiLiShuLiang());

        order.setGuigeGaoMm(dto.getGuigeGaoMm());
        order.setGuigeKuanMm(dto.getGuigeKuanMm());
        order.setGuigeHouMm(dto.getGuigeHouMm());

        order.setFuLiaoShuoMing(dto.getFuLiaoShuoMing());
        order.setWuLiaoShuoMing(dto.getWuLiaoShuoMing());
        order.setZhiLiangYaoQiu(dto.getZhiLiangYaoQiu());
        order.setBeiZhu(dto.getBeiZhu());
        order.setKeLaiXinXi(dto.getKeLaiXinXi());

        order.setXiaZiliaodaiRiqi1(dto.getXiaZiliaodaiRiqi1());
        order.setXiaZiliaodaiRiqi2(dto.getXiaZiliaodaiRiqi2());
        order.setYinzhangRiqi1(dto.getYinzhangRiqi1());
        order.setYinzhangRiqi2(dto.getYinzhangRiqi2());
        order.setJiaoHuoRiQi1(dto.getJiaoHuoRiQi1());
        order.setJiaoHuoRiQi2(dto.getJiaoHuoRiQi2());

        order.setYeWuDaiBiaoFenJi(dto.getYeWuDaiBiaoFenJi());
        order.setShenHeRen(dto.getShenHeRen());
        order.setDaYinRen(dto.getDaYinRen());

        if (dto.getChanPinMingXi() != null) {
            order.getOrderItems().clear();
            dto.getChanPinMingXi().forEach(productDTO -> {
                OrderItem item = toOrderItem(productDTO);
                item.setOrder(order);
                order.getOrderItems().add(item);
            });
        }
    }

    private ProductDTO toProductDTO(OrderItem item) {
        ProductDTO dto = new ProductDTO();
        dto.setProductName(item.getProductName());
        dto.setQuantity(item.getQuantity());
        dto.setSpecification(item.getSpecification());
        dto.setUnit(item.getUnit());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setTotalPrice(item.getTotalPrice());
        dto.setNotes(item.getNotes());
        return dto;
    }

    private OrderItem toOrderItem(ProductDTO dto) {
        OrderItem item = new OrderItem();
        item.setProductName(dto.getProductName());
        item.setQuantity(dto.getQuantity());
        item.setSpecification(dto.getSpecification());
        item.setUnit(dto.getUnit());
        item.setUnitPrice(dto.getUnitPrice());
        item.setTotalPrice(dto.getTotalPrice());
        item.setNotes(dto.getNotes());
        return item;
    }

    private AttachmentDTO toAttachmentDTO(Document doc) {
        AttachmentDTO dto = new AttachmentDTO();
        dto.setFileName(doc.getFileName());
        dto.setFilePath(doc.getFilePath());
        dto.setFileType(doc.getFileType());
        dto.setFileSize(doc.getFileSize());
        dto.setUploadedAt(doc.getUploadedAt());
        return dto;
    }

    private AuditLogDTO toAuditLogDTO(AuditLog log) {
        AuditLogDTO dto = new AuditLogDTO();
        dto.setAction(log.getAction());
        dto.setActionDescription(log.getActionDescription());
        dto.setUserId(log.getUserId());
        dto.setOldValue(log.getOldValue());
        dto.setNewValue(log.getNewValue());
        dto.setTime(log.getTime());
        return dto;
    }
}
