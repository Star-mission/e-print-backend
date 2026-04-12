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

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WorkOrderMapper {

    public WorkOrderDTO toDTO(EngineeringOrder workOrder, List<AuditLog> auditLogs) {
        WorkOrderDTO dto = new WorkOrderDTO();

        dto.setWork_id(workOrder.getWorkId());
        dto.setWork_ver(workOrder.getWorkVer());
        dto.setWork_unique(workOrder.getWorkUnique());
        dto.setWorkorderstatus(workOrder.getReviewStatus() != null ? workOrder.getReviewStatus().name() : null);
        dto.setWork_clerk(workOrder.getWorkClerk());
        dto.setClerkDate(workOrder.getClerkDate() != null ? workOrder.getClerkDate().toString() : null);
        dto.setWork_audit(workOrder.getWorkAudit());
        dto.setAuditDate(workOrder.getAuditDate() != null ? workOrder.getAuditDate().toString() : null);
        dto.setCustomer(workOrder.getKeHu());
        dto.setCustomerPO(workOrder.getPo());
        dto.setProductName(workOrder.getChengPinMingCheng());
        dto.setChanPinGuiGe(workOrder.getChanPinGuiGe());
        dto.setGongDanLeiXing(workOrder.getGongDanLeiXing());
        dto.setCaiLiao(workOrder.getCaiLiao());
        dto.setChanPinLeiXing(workOrder.getChanPinLeiXing());
        dto.setZhiDanShiJian(workOrder.getZhiDanShiJian() != null ? workOrder.getZhiDanShiJian().toString() : null);

        dto.setDingDanShuLiang(workOrder.getDingDanShuLiang());
        dto.setChuYangShuLiang(workOrder.getChuYangShu());
        dto.setChaoBiLiShuLiang(workOrder.getChaoBiLi());
        dto.setBenChangFangSun(workOrder.getBenChangFangSun());
        dto.setChuYangRiqiRequired(workOrder.getChuYangRiqiRequired() != null ? workOrder.getChuYangRiqiRequired().toString() : null);
        dto.setChuHuoRiqiRequired(workOrder.getChuHuoRiqiRequired() != null ? workOrder.getChuHuoRiqiRequired().toString() : null);

        dto.setZhuangDingJianShu(workOrder.getZhuangDingJianShu());
        dto.setZhuangDingStart(workOrder.getZhuangDingStart() != null ? workOrder.getZhuangDingStart().toString() : null);
        dto.setZhuangDingEnd(workOrder.getZhuangDingEnd() != null ? workOrder.getZhuangDingEnd().toString() : null);
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
        workOrder.setClerkDate(parseDateTime(dto.getClerkDate()));
        workOrder.setWorkAudit(dto.getWork_audit());
        workOrder.setAuditDate(parseDateTime(dto.getAuditDate()));
        workOrder.setKeHu(dto.getCustomer());
        workOrder.setPo(dto.getCustomerPO());
        workOrder.setChengPinMingCheng(dto.getProductName());
        workOrder.setChanPinGuiGe(dto.getChanPinGuiGe());
        workOrder.setGongDanLeiXing(dto.getGongDanLeiXing());
        workOrder.setCaiLiao(dto.getCaiLiao());
        workOrder.setChanPinLeiXing(dto.getChanPinLeiXing());
        workOrder.setZhiDanShiJian(parseDateTime(dto.getZhiDanShiJian()));

        workOrder.setDingDanShuLiang(dto.getDingDanShuLiang());
        workOrder.setChuYangShu(dto.getChuYangShuLiang());
        workOrder.setChaoBiLi(dto.getChaoBiLiShuLiang());
        workOrder.setBenChangFangSun(dto.getBenChangFangSun());
        workOrder.setChuYangRiqiRequired(parseDateTime(dto.getChuYangRiqiRequired()));
        workOrder.setChuHuoRiqiRequired(parseDateTime(dto.getChuHuoRiqiRequired()));

        workOrder.setZhuangDingJianShu(dto.getZhuangDingJianShu());
        workOrder.setZhuangDingStart(parseDateTime(dto.getZhuangDingStart()));
        workOrder.setZhuangDingEnd(parseDateTime(dto.getZhuangDingEnd()));
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
        dto.setKaiShiRiQi(line.getKaiShiShiJian() != null ? line.getKaiShiShiJian().toString() : null);
        dto.setYuQiJieShu(line.getJieShuShiJian() != null ? line.getJieShuShiJian().toString() : null);
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

        // 安全解析日期字符串，处理空字符串情况
        line.setKaiShiShiJian(dto.getKaiShiRiQi() != null && !dto.getKaiShiRiQi().isEmpty()
            ? java.time.LocalDateTime.parse(dto.getKaiShiRiQi()) : null);
        line.setJieShuShiJian(dto.getYuQiJieShu() != null && !dto.getYuQiJieShu().isEmpty()
            ? java.time.LocalDateTime.parse(dto.getYuQiJieShu()) : null);

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

    private java.time.LocalDateTime parseDateTime(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        try {
            if (dateStr.length() == 10) return java.time.LocalDate.parse(dateStr).atStartOfDay();
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
