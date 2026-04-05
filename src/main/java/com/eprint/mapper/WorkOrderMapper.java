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
        dto.setWork_audit(workOrder.getWorkAudit());
        dto.setCustomer(workOrder.getKeHu());
        dto.setCustomerPO(workOrder.getPo());
        dto.setProductName(workOrder.getChengPinMingCheng());

        dto.setChuYangShuLiang(workOrder.getChuYangShu());
        dto.setChaoBiLiShuLiang(workOrder.getChaoBiLi());

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
        workOrder.setWorkAudit(dto.getWork_audit());
        workOrder.setKeHu(dto.getCustomer());
        workOrder.setPo(dto.getCustomerPO());
        workOrder.setChengPinMingCheng(dto.getProductName());

        workOrder.setChuYangShu(dto.getChuYangShuLiang());
        workOrder.setChaoBiLi(dto.getChaoBiLiShuLiang());

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
        dto.setWuLiaoMingCheng(line.getMaterialName());
        dto.setCaiLiaoGuiGe(line.getSpecification());
        dto.setYinChuShu(line.getQuantity());
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
        line.setMaterialName(dto.getWuLiaoMingCheng());
        line.setSpecification(dto.getCaiLiaoGuiGe());
        line.setQuantity(dto.getYinChuShu());
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
