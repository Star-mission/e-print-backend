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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public OrderDTO toDTO(Order order, List<AuditLog> auditLogs) {
        OrderDTO dto = new OrderDTO();

        dto.setOrder_id(order.getOrderNumber());
        dto.setOrder_ver(order.getOrderVer());
        dto.setOrder_unique(order.getOrderUnique());
        dto.setOrderstatus(order.getStatus() != null ? order.getStatus().name() : null);
        dto.setSales(order.getSales());
        dto.setSalesDate(formatDate(order.getSalesDate()));
        dto.setAudit(order.getAudit());
        dto.setAuditDate(formatDate(order.getAuditDate()));
        dto.setCustomer(order.getCustomer());
        dto.setProductName(order.getProductName());
        dto.setCustomerPO(order.getCustomerPO());
        dto.setJiuBianMa(order.getJiuBianMa());
        dto.setQiTaShiBie(order.getQiTaShiBie());
        dto.setIsbn(order.getIsbn());
        dto.setBaoJiaDanHao(order.getBaoJiaDanHao());
        dto.setXiLieDanMing(order.getXiLieDanMing());
        dto.setChanPinDaLei(order.getChanPinDaLei());
        dto.setZiLeiXing(order.getZiLeiXing());
        dto.setFscType(order.getFscType());
        dto.setFenBanShuoMing(order.getFenBanShuoMing());
        dto.setBaoLiuQianSe(order.getBaoLiuQianSe());
        dto.setCpcQueRen(order.getCpcQueRen());
        dto.setWaixiaoFlag(order.getWaixiaoFlag());
        dto.setCpsiaYaoqiu(order.getCpsiaYaoqiu());
        dto.setDingZhiBeiZhu(order.getDingZhiBeiZhu());

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

        dto.setYeWuRiqi(formatDate(order.getYeWuRiqi()));
        dto.setShenHeRiqi(formatDate(order.getShenHeRiqi()));
        dto.setDaYinRiqi(formatDate(order.getDaYinRiqi()));

        dto.setDingDanShuLiang(order.getDingDanShuLiang());
        dto.setChuYangShuLiang(order.getChuYangShuLiang());
        dto.setChaoBiLiShuLiang(order.getChaoBiLiShuLiang());
        dto.setTeShuLiuYangZhang(order.getTeShuLiuYangZhang());
        dto.setBeiPinShuLiang(order.getBeiPinShuLiang());
        dto.setTeShuLiuShuYang(order.getTeShuLiuShuYang());
        dto.setZongShuLiang(order.getZongShuLiang());
        dto.setChuYangShuoMing(order.getChuYangShuoMing());
        dto.setChuHuoShuLiang(order.getChuHuoShuLiang());

        dto.setGuigeGaoMm(order.getGuigeGaoMm());
        dto.setGuigeKuanMm(order.getGuigeKuanMm());
        dto.setGuigeHouMm(order.getGuigeHouMm());
        dto.setZhuangDingFangShi(order.getZhuangDingFangShi());
        dto.setGenSeZhiShi(order.getGenSeZhiShi());

        dto.setFuLiaoShuoMing(order.getFuLiaoShuoMing());
        dto.setWuLiaoShuoMing(order.getWuLiaoShuoMing());
        dto.setZhiLiangYaoQiu(order.getZhiLiangYaoQiu());
        dto.setBeiZhu(order.getBeiZhu());
        dto.setKeLaiXinxi(order.getKeLaiXinXi());

        // 日期字段映射（实体1/2 → DTO Required/Promise）
        dto.setXiaZiliaodaiRiqiRequired(formatDate(order.getXiaZiliaodaiRiqi1()));
        dto.setXiaZiliaodaiRiqiPromise(formatDate(order.getXiaZiliaodaiRiqi2()));
        dto.setYinzhangRiqiRequired(formatDate(order.getYinzhangRiqi1()));
        dto.setYinzhangRiqiPromise(formatDate(order.getYinzhangRiqi2()));
        dto.setZhepaiRiqiRequired(formatDate(order.getZhepaiRiqi1()));
        dto.setZhepaiRiqiPromise(formatDate(order.getZhepaiRiqi2()));
        dto.setChuyangRiqiRequired(formatDate(order.getChuyangRiqi1()));
        dto.setChuyangRiqiPromise(formatDate(order.getChuyangRiqi2()));
        dto.setChuHuoRiqiRequired(formatDate(order.getJiaoHuoRiQi1()));
        dto.setChuHuoRiqiPromise(formatDate(order.getJiaoHuoRiQi2()));

        dto.setYongTu(order.getYongTu());
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
        order.setSalesDate(parseDate(dto.getSalesDate()));
        order.setAudit(dto.getAudit());
        order.setAuditDate(parseDate(dto.getAuditDate()));
        order.setCustomer(dto.getCustomer());
        order.setProductName(dto.getProductName());
        order.setCustomerPO(dto.getCustomerPO());
        order.setJiuBianMa(dto.getJiuBianMa());
        order.setQiTaShiBie(dto.getQiTaShiBie());
        order.setIsbn(dto.getIsbn());
        order.setBaoJiaDanHao(dto.getBaoJiaDanHao());
        order.setXiLieDanMing(dto.getXiLieDanMing());
        order.setChanPinDaLei(dto.getChanPinDaLei());
        order.setZiLeiXing(dto.getZiLeiXing());
        order.setFscType(dto.getFscType());
        order.setFenBanShuoMing(dto.getFenBanShuoMing());
        order.setBaoLiuQianSe(dto.getBaoLiuQianSe());
        order.setCpcQueRen(dto.getCpcQueRen());
        order.setWaixiaoFlag(dto.getWaixiaoFlag());
        order.setCpsiaYaoqiu(dto.getCpsiaYaoqiu());
        order.setDingZhiBeiZhu(dto.getDingZhiBeiZhu());

        order.setDingDanShuLiang(dto.getDingDanShuLiang());
        order.setChuYangShuLiang(dto.getChuYangShuLiang());
        order.setChaoBiLiShuLiang(dto.getChaoBiLiShuLiang());
        order.setTeShuLiuYangZhang(dto.getTeShuLiuYangZhang());
        order.setBeiPinShuLiang(dto.getBeiPinShuLiang());
        order.setTeShuLiuShuYang(dto.getTeShuLiuShuYang());
        order.setZongShuLiang(dto.getZongShuLiang());
        order.setChuYangShuoMing(dto.getChuYangShuoMing());
        order.setChuHuoShuLiang(dto.getChuHuoShuLiang());

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

        order.setYeWuRiqi(parseDate(dto.getYeWuRiqi()));
        order.setShenHeRiqi(parseDate(dto.getShenHeRiqi()));
        order.setDaYinRiqi(parseDate(dto.getDaYinRiqi()));

        order.setGuigeGaoMm(dto.getGuigeGaoMm());
        order.setGuigeKuanMm(dto.getGuigeKuanMm());
        order.setGuigeHouMm(dto.getGuigeHouMm());
        order.setZhuangDingFangShi(dto.getZhuangDingFangShi());
        order.setGenSeZhiShi(dto.getGenSeZhiShi());

        order.setFuLiaoShuoMing(dto.getFuLiaoShuoMing());
        order.setWuLiaoShuoMing(dto.getWuLiaoShuoMing());
        order.setChanPinMingXiTeBieShuoMing(dto.getChanPinMingXiTeBieShuoMing());
        order.setZhiLiangYaoQiu(dto.getZhiLiangYaoQiu());
        order.setBeiZhu(dto.getBeiZhu());
        order.setKeLaiXinXi(dto.getKeLaiXinxi());
        order.setYongTu(dto.getYongTu());

        // 日期字段：前端使用 Required/Promise 命名，后端实体使用 1/2 命名
        order.setXiaZiliaodaiRiqi1(parseDate(dto.getXiaZiliaodaiRiqiRequired()));
        order.setXiaZiliaodaiRiqi2(parseDate(dto.getXiaZiliaodaiRiqiPromise()));
        order.setYinzhangRiqi1(parseDate(dto.getYinzhangRiqiRequired()));
        order.setYinzhangRiqi2(parseDate(dto.getYinzhangRiqiPromise()));
        order.setZhepaiRiqi1(parseDate(dto.getZhepaiRiqiRequired()));
        order.setZhepaiRiqi2(parseDate(dto.getZhepaiRiqiPromise()));
        order.setChuyangRiqi1(parseDate(dto.getChuyangRiqiRequired()));
        order.setChuyangRiqi2(parseDate(dto.getChuyangRiqiPromise()));
        order.setJiaoHuoRiQi1(parseDate(dto.getChuHuoRiqiRequired()));
        order.setJiaoHuoRiQi2(parseDate(dto.getChuHuoRiqiPromise()));

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
        dto.setBeiZhu(item.getNotes());

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
        item.setNotes(dto.getBeiZhu() != null ? dto.getBeiZhu() : dto.getNotes());

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

    private String formatDate(LocalDateTime date) {
        return date != null ? date.toLocalDate().format(DATE_FORMATTER) : null;
    }

    private LocalDateTime parseDate(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) {
            return null;
        }
        try {
            String normalized = dateStr.trim();
            if (normalized.contains(" ")) {
                normalized = normalized.replace(" ", "T");
            }
            if (normalized.length() == 10) {
                return LocalDate.parse(normalized, DATE_FORMATTER).atStartOfDay();
            }
            return LocalDateTime.parse(normalized).toLocalDate().atStartOfDay();
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
