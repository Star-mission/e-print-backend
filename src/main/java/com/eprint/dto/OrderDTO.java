package com.eprint.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {

    private String order_id;
    private Integer order_ver;
    private String order_unique;
    private String orderstatus;
    private String sales;
    private String audit;
    private String customer;
    private String productName;
    private String customerPO;
    private String isbn;

    private Integer dingDanShuLiang;
    private Integer chuYangShuLiang;
    private Integer chaoBiLiShuLiang;

    private Double guigeGaoMm;
    private Double guigeKuanMm;
    private Double guigeHouMm;

    private String fuLiaoShuoMing;
    private String wuLiaoShuoMing;
    private String zhiLiangYaoQiu;
    private String beiZhu;
    private String keLaiXinXi;

    private LocalDateTime xiaZiliaodaiRiqi1;
    private LocalDateTime xiaZiliaodaiRiqi2;
    private LocalDateTime yinzhangRiqi1;
    private LocalDateTime yinzhangRiqi2;
    private LocalDateTime jiaoHuoRiQi1;
    private LocalDateTime jiaoHuoRiQi2;

    private String yeWuDaiBiaoFenJi;
    private String shenHeRen;
    private String daYinRen;

    private List<ProductDTO> chanPinMingXi;
    private List<AttachmentDTO> attachments;
    private List<AuditLogDTO> auditLogs;
}
