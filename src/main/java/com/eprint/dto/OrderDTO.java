package com.eprint.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {

    private String order_id;
    private String order_ver;  // 前端为 string 类型（如 "V1"）
    private String order_unique;
    private String orderstatus;
    private String sales;
    private String salesDate;
    private String audit;
    private String auditDate;
    private String customer;
    private String productName;
    private String customerPO;
    private String isbn;
    private String baoJiaDanHao;
    private String xiLieDanMing;
    private String chanPinDaLei;
    private String ziLeiXing;
    private String fscType;
    private String fenBanShuoMing;
    private String baoLiuQianSe;
    private Boolean cpcQueRen;
    private Boolean waixiaoFlag;
    private Boolean cpsiaYaoqiu;
    private String dingZhiBeiZhu;
    private String zhuangDingFangShi;
    private String genSeZhiShi;
    private String yongTu;
    private String chanPinMingXiTeBieShuoMing;

    private Integer dingDanShuLiang;
    private Integer chuYangShuLiang;
    private Integer chaoBiLiShuLiang;
    private Integer teShuLiuYangZhang;
    private Integer beiPinShuLiang;
    private Integer teShuLiuShuYang;
    private Integer zongShuLiang;
    private Integer chuYangShuoMing;
    private Integer chuHuoShuLiang;

    private Double guigeGaoMm;
    private Double guigeKuanMm;
    private Double guigeHouMm;

    private String fuLiaoShuoMing;
    private String wuLiaoShuoMing;
    private String zhiLiangYaoQiu;
    private String beiZhu;
    private String keLaiXinxi;  // 前端字段名为小写 xi

    // 排期信息（与前端字段名对齐）
    private String xiaZiliaodaiRiqiRequired;
    private String xiaZiliaodaiRiqiPromise;
    private String yinzhangRiqiRequired;
    private String yinzhangRiqiPromise;
    private String zhepaiRiqiRequired;
    private String zhepaiRiqiPromise;
    private String chuyangRiqiRequired;
    private String chuyangRiqiPromise;
    private String chuHuoRiqiRequired;
    private String chuHuoRiqiPromise;

    private String yeWuDaiBiaoFenJi;
    private String shenHeRen;
    private String daYinRen;

    private String isDeleted;  // 是否删除

    private List<ProductDTO> chanPinMingXi;
    private List<AttachmentDTO> attachments;
    private List<AuditLogDTO> auditLogs;
}
