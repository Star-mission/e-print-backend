package com.eprint.mapper;

import com.eprint.dto.WorkOrderDTO;
import com.eprint.dto.IntermediaMaterialDTO;
import com.eprint.dto.AttachmentDTO;
import com.eprint.dto.AuditLogDTO;
import com.eprint.entity.EngineeringOrder;
import com.eprint.entity.MaterialLine;
import com.eprint.entity.Document;
import com.eprint.entity.AuditLog;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WorkOrderMapper {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public WorkOrderDTO toDTO(EngineeringOrder workOrder, List<AuditLog> auditLogs) {
        WorkOrderDTO dto = new WorkOrderDTO();

        dto.setWork_id(workOrder.getWorkId());
        dto.setWork_ver(workOrder.getWorkVer());
        dto.setWork_unique(workOrder.getWorkUnique());
        dto.setWorkorderstatus(workOrder.getReviewStatus() != null ? workOrder.getReviewStatus().name() : null);
        dto.setWork_clerk(workOrder.getWorkClerk());
        dto.setClerkDate(formatDate(workOrder.getClerkDate()));
        dto.setWork_audit(workOrder.getWorkAudit());
        dto.setAuditDate(formatDate(workOrder.getAuditDate()));
        dto.setCustomer(workOrder.getKeHu());
        dto.setCustomerPO(workOrder.getPo());
        dto.setProductName(workOrder.getChengPinMingCheng());
        dto.setChanPinGuiGe(workOrder.getChanPinGuiGe());
        dto.setGongDanLeiXing(workOrder.getGongDanLeiXing());
        dto.setCaiLiao(workOrder.getCaiLiao());
        dto.setChanPinLeiXing(workOrder.getChanPinLeiXing());
        dto.setZhiDanShiJian(formatDate(workOrder.getZhiDanShiJian()));

        dto.setDingDanShuLiang(workOrder.getDingDanShuLiang());
        dto.setChuYangShuLiang(workOrder.getChuYangShu());
        dto.setChaoBiLiShuLiang(workOrder.getChaoBiLi());
        dto.setBenChangFangSun(workOrder.getBenChangFangSun());
        dto.setChuYangRiqiRequired(formatDate(workOrder.getChuYangRiqiRequired()));
        dto.setChuHuoRiqiRequired(formatDate(workOrder.getChuHuoRiqiRequired()));

        dto.setZhuangDingJianShu(workOrder.getZhuangDingJianShu());
        dto.setZhuangDingStart(formatDate(workOrder.getZhuangDingStart()));
        dto.setZhuangDingEnd(formatDate(workOrder.getZhuangDingEnd()));
        dto.setHead_MNF(workOrder.getHeadMNF());
        dto.setBeiZhu(workOrder.getBeiZhu());

        dto.setIntermedia(workOrder.getMaterialLines().stream()
                .map(this::toIntermediaMaterialDTO)
                .collect(Collectors.toList()));

        dto.setAttachments(workOrder.getDocuments().stream()
                .map(this::toAttachmentDTO)
                .collect(Collectors.toList()));

        if (auditLogs != null) {
            dto.setAuditLogs(auditLogs.stream()
                    .map(this::toAuditLogDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public void updateWorkOrderFromDTO(WorkOrderDTO dto, EngineeringOrder workOrder) {
        if (dto.getWork_id() != null) {
            workOrder.setWorkId(dto.getWork_id());
        }
        if (dto.getWork_ver() != null) {
            workOrder.setWorkVer(dto.getWork_ver());
        }
        if (dto.getWork_unique() != null) {
            workOrder.setWorkUnique(dto.getWork_unique());
        }
        if (dto.getWorkorderstatus() != null) {
            workOrder.setReviewStatus(EngineeringOrder.OrderStatus.valueOf(dto.getWorkorderstatus()));
        }

        workOrder.setWorkClerk(dto.getWork_clerk());
        workOrder.setClerkDate(parseDate(dto.getClerkDate()));
        workOrder.setWorkAudit(dto.getWork_audit());
        workOrder.setAuditDate(parseDate(dto.getAuditDate()));
        workOrder.setKeHu(dto.getCustomer());
        workOrder.setPo(dto.getCustomerPO());
        workOrder.setChengPinMingCheng(dto.getProductName());
        workOrder.setChanPinGuiGe(dto.getChanPinGuiGe());
        workOrder.setGongDanLeiXing(dto.getGongDanLeiXing());
        workOrder.setCaiLiao(dto.getCaiLiao());
        workOrder.setChanPinLeiXing(dto.getChanPinLeiXing());
        workOrder.setZhiDanShiJian(parseDate(dto.getZhiDanShiJian()));

        workOrder.setDingDanShuLiang(dto.getDingDanShuLiang());
        workOrder.setChuYangShu(dto.getChuYangShuLiang());
        workOrder.setChaoBiLi(dto.getChaoBiLiShuLiang());
        workOrder.setBenChangFangSun(dto.getBenChangFangSun());
        workOrder.setChuYangRiqiRequired(parseDate(dto.getChuYangRiqiRequired()));
        workOrder.setChuHuoRiqiRequired(parseDate(dto.getChuHuoRiqiRequired()));

        workOrder.setZhuangDingJianShu(dto.getZhuangDingJianShu());
        workOrder.setZhuangDingStart(parseDate(dto.getZhuangDingStart()));
        workOrder.setZhuangDingEnd(parseDate(dto.getZhuangDingEnd()));
        workOrder.setHeadMNF(dto.getHead_MNF());
        workOrder.setBeiZhu(dto.getBeiZhu());

        if (dto.getIntermedia() != null) {
            workOrder.getMaterialLines().clear();
            dto.getIntermedia().forEach(materialDTO -> {
                MaterialLine line = toMaterialLine(materialDTO);
                line.setEngineeringOrder(workOrder);
                workOrder.getMaterialLines().add(line);
            });
        }
    }

    private IntermediaMaterialDTO toIntermediaMaterialDTO(MaterialLine line) {
        IntermediaMaterialDTO dto = new IntermediaMaterialDTO();
        dto.setIntermediaID(line.getIntermediaID());
        dto.setBuJianMingCheng(line.getBuJianMingCheng());
        dto.setYinShuaYanSe(line.getYinShuaYanSe());
        dto.setWuLiaoMingCheng(line.getMaterialName());
        dto.setPinPai(line.getPinPai());
        dto.setCaiLiaoGuiGe(line.getSpecification());
        dto.setFSC(line.getFSC());
        dto.setKaiShu(line.getKaiShu());
        dto.setShangJiChiCun(line.getShangJiChiCun());
        dto.setPaiBanMuShu(line.getPaiBanMuShu());
        dto.setYinChuShu(line.getQuantity());
        dto.setYinSun(line.getYinSun());
        dto.setLingLiaoShu(line.getLingLiaoShu());
        dto.setBiaoMianChuLi(line.getBiaoMianChuLi());
        dto.setYinShuaBanShu(line.getYinShuaBanShu());
        dto.setShengChanLuJing(line.getShengChanLuJing());
        dto.setPaiBanFangShi(line.getPaiBanFangShi());
        dto.setYiGouJianShu(line.getYiGouJianShu());
        dto.setHead_PUR(line.getHeadPUR());
        dto.setHead_OUT(line.getHeadOUT());
        dto.setKaiShiRiQi(formatDate(line.getKaiShiShiJian()));
        dto.setYuQiJieShu(formatDate(line.getJieShuShiJian()));
        dto.setDangQianJinDu(line.getDangQianJinDu() != null ? Integer.parseInt(line.getDangQianJinDu()) : null);
        dto.setNotes(line.getNotes());
        return dto;
    }

    private MaterialLine toMaterialLine(IntermediaMaterialDTO dto) {
        MaterialLine line = new MaterialLine();
        line.setIntermediaID(dto.getIntermediaID());
        line.setBuJianMingCheng(dto.getBuJianMingCheng());
        line.setYinShuaYanSe(dto.getYinShuaYanSe());
        line.setMaterialName(dto.getWuLiaoMingCheng());
        line.setPinPai(dto.getPinPai());
        line.setSpecification(dto.getCaiLiaoGuiGe());
        line.setFSC(dto.getFSC());
        line.setKaiShu(dto.getKaiShu());
        line.setShangJiChiCun(dto.getShangJiChiCun());
        line.setPaiBanMuShu(dto.getPaiBanMuShu());
        line.setQuantity(dto.getYinChuShu());
        line.setYinSun(dto.getYinSun());
        line.setLingLiaoShu(dto.getLingLiaoShu());
        line.setBiaoMianChuLi(dto.getBiaoMianChuLi());
        line.setYinShuaBanShu(dto.getYinShuaBanShu());
        line.setShengChanLuJing(dto.getShengChanLuJing());
        line.setPaiBanFangShi(dto.getPaiBanFangShi());
        line.setYiGouJianShu(dto.getYiGouJianShu());
        line.setHeadPUR(dto.getHead_PUR());
        line.setHeadOUT(dto.getHead_OUT());

        line.setKaiShiShiJian(parseDate(dto.getKaiShiRiQi()));
        line.setJieShuShiJian(parseDate(dto.getYuQiJieShu()));

        line.setDangQianJinDu(dto.getDangQianJinDu() != null ? dto.getDangQianJinDu().toString() : null);
        line.setNotes(dto.getNotes());
        return line;
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

    private LocalDateTime parseDateTime(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) {
            return null;
        }
        try {
            String normalized = dateStr.trim();
            if (normalized.contains(" ")) {
                normalized = normalized.replace(" ", "T");
            }
            return LocalDateTime.parse(normalized);
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
