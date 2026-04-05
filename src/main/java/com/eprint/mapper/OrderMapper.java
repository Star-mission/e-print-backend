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
        dto.setJiuBianMa(order.getJiuBianMa());
        dto.setQiTaShiBie(order.getQiTaShiBie());
        dto.setIsbn(order.getIsbn());

        dto.setFenBanShuoMing2(order.getFenBanShuoMing2());
        dto.setYinShuaGenSeYaoQiu(order.getYinShuaGenSeYaoQiu());
        dto.setZhuangDingShouGongYaoQiu(order.getZhuangDingShouGongYaoQiu());
        dto.setQiTa(order.getQiTa());
        dto.setKeHuFanKui(order.getKeHuFanKui());
        dto.setTeShuYaoQiu(order.getTeShuYaoQiu());
        dto.setKongZhiFangFa(order.getKongZhiFangFa());
        dto.setDingDanTeBieShuoMing(order.getDingDanTeBieShuoMing());
        dto.setYangPinPingShenXinXi(order.getYangPinPingShenXinXi());
        dto.setDingDanPingShenXinXi(order.getDingDanPingShenXinXi());

        dto.setYeWuRiqi(order.getYeWuRiqi() != null ? order.getYeWuRiqi().toString() : null);
        dto.setShenHeRiqi(order.getShenHeRiqi() != null ? order.getShenHeRiqi().toString() : null);
        dto.setDaYinRiqi(order.getDaYinRiqi() != null ? order.getDaYinRiqi().toString() : null);

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
        dto.setKeLaiXinxi(order.getKeLaiXinXi());

        // 日期字段映射（实体1/2 → DTO Required/Promise）
        dto.setXiaZiliaodaiRiqiRequired(order.getXiaZiliaodaiRiqi1() != null ? order.getXiaZiliaodaiRiqi1().toString() : null);
        dto.setXiaZiliaodaiRiqiPromise(order.getXiaZiliaodaiRiqi2() != null ? order.getXiaZiliaodaiRiqi2().toString() : null);
        dto.setYinzhangRiqiRequired(order.getYinzhangRiqi1() != null ? order.getYinzhangRiqi1().toString() : null);
        dto.setYinzhangRiqiPromise(order.getYinzhangRiqi2() != null ? order.getYinzhangRiqi2().toString() : null);
        dto.setChuHuoRiqiRequired(order.getJiaoHuoRiQi1() != null ? order.getJiaoHuoRiQi1().toString() : null);
        dto.setChuHuoRiqiPromise(order.getJiaoHuoRiQi2() != null ? order.getJiaoHuoRiQi2().toString() : null);

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
        order.setJiuBianMa(dto.getJiuBianMa());
        order.setQiTaShiBie(dto.getQiTaShiBie());
        order.setIsbn(dto.getIsbn());

        order.setDingDanShuLiang(dto.getDingDanShuLiang());
        order.setChuYangShuLiang(dto.getChuYangShuLiang());
        order.setChaoBiLiShuLiang(dto.getChaoBiLiShuLiang());

        order.setFenBanShuoMing2(dto.getFenBanShuoMing2());
        order.setYinShuaGenSeYaoQiu(dto.getYinShuaGenSeYaoQiu());
        order.setZhuangDingShouGongYaoQiu(dto.getZhuangDingShouGongYaoQiu());
        order.setQiTa(dto.getQiTa());
        order.setKeHuFanKui(dto.getKeHuFanKui());
        order.setTeShuYaoQiu(dto.getTeShuYaoQiu());
        order.setKongZhiFangFa(dto.getKongZhiFangFa());
        order.setDingDanTeBieShuoMing(dto.getDingDanTeBieShuoMing());
        order.setYangPinPingShenXinXi(dto.getYangPinPingShenXinXi());
        order.setDingDanPingShenXinXi(dto.getDingDanPingShenXinXi());

        order.setYeWuRiqi(parseDateTime(dto.getYeWuRiqi()));
        order.setShenHeRiqi(parseDateTime(dto.getShenHeRiqi()));
        order.setDaYinRiqi(parseDateTime(dto.getDaYinRiqi()));

        order.setGuigeGaoMm(dto.getGuigeGaoMm());
        order.setGuigeKuanMm(dto.getGuigeKuanMm());
        order.setGuigeHouMm(dto.getGuigeHouMm());

        order.setFuLiaoShuoMing(dto.getFuLiaoShuoMing());
        order.setWuLiaoShuoMing(dto.getWuLiaoShuoMing());
        order.setZhiLiangYaoQiu(dto.getZhiLiangYaoQiu());
        order.setBeiZhu(dto.getBeiZhu());
        order.setKeLaiXinXi(dto.getKeLaiXinxi());

        // 日期字段：前端使用 Required/Promise 命名，后端实体使用 1/2 命名
        order.setXiaZiliaodaiRiqi1(parseDateTime(dto.getXiaZiliaodaiRiqiRequired()));
        order.setXiaZiliaodaiRiqi2(parseDateTime(dto.getXiaZiliaodaiRiqiPromise()));
        order.setYinzhangRiqi1(parseDateTime(dto.getYinzhangRiqiRequired()));
        order.setYinzhangRiqi2(parseDateTime(dto.getYinzhangRiqiPromise()));
        order.setJiaoHuoRiQi1(parseDateTime(dto.getChuHuoRiqiRequired()));
        order.setJiaoHuoRiQi2(parseDateTime(dto.getChuHuoRiqiPromise()));

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

        dto.setNeiWen(item.getNeiWen());
        dto.setYongZhiChiCun(item.getYongZhiChiCun());
        dto.setHouDu(item.getHouDu());
        dto.setKeZhong(item.getKeZhong());
        dto.setChanDi(item.getChanDi());
        dto.setPinPai(item.getPinPai());
        dto.setZhiLei(item.getZhiLei());
        dto.setFSC(item.getFSC());
        dto.setYeShu(item.getYeShu());
        dto.setYinSe(item.getYinSe());
        dto.setZhuanSe(item.getZhuanSe());
        dto.setBiaoMianChuLi(item.getBiaoMianChuLi());
        dto.setZhuangDingGongYi(item.getZhuangDingGongYi());

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

        item.setNeiWen(dto.getNeiWen());
        item.setYongZhiChiCun(dto.getYongZhiChiCun());
        item.setHouDu(dto.getHouDu());
        item.setKeZhong(dto.getKeZhong());
        item.setChanDi(dto.getChanDi());
        item.setPinPai(dto.getPinPai());
        item.setZhiLei(dto.getZhiLei());
        item.setFSC(dto.getFSC());
        item.setYeShu(dto.getYeShu());
        item.setYinSe(dto.getYinSe());
        item.setZhuanSe(dto.getZhuanSe());
        item.setBiaoMianChuLi(dto.getBiaoMianChuLi());
        item.setZhuangDingGongYi(dto.getZhuangDingGongYi());

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

    private java.time.LocalDateTime parseDateTime(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        try {
            // 处理 YYYY-MM-DD
            if (dateStr.length() == 10) return java.time.LocalDate.parse(dateStr).atStartOfDay();
            // 处理 YYYY-MM-DD HH:mm:ss (将空格替换为 T 以符合 ISO 格式)
            if (dateStr.contains(" ")) {
                dateStr = dateStr.replace(" ", "T");
            }
            return java.time.LocalDateTime.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    private AuditLogDTO toAuditLogDTO(AuditLog log) {
        AuditLogDTO dto = new AuditLogDTO();
        dto.setAction(log.getAction());
        dto.setComment(log.getActionDescription());
        dto.setOperator(log.getUserId());
        dto.setOldValue(log.getOldValue());
        dto.setNewValue(log.getNewValue());
        dto.setTime(log.getTime() != null ? log.getTime().toString() : null);
        return dto;
    }
}
