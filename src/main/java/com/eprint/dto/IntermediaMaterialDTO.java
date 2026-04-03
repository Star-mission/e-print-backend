package com.eprint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class IntermediaMaterialDTO {

    // 前端 IIM 字段
    private String buJianMingCheng;  // 部件名称
    private String yinShuaYanSe;    // 印刷颜色
    private String wuLiaoMingCheng; // 物料名称
    private String pinPai;          // 品牌
    private String caiLiaoGuiGe;    // 材料规格

    @JsonProperty("FSC")
    private String FSC;
    private Integer kaiShu;         // 开数
    private String shangJiChiCun;   // 上机尺寸
    private Integer paiBanMuShu;    // 排版模数
    private Integer yinChuShu;      // 印出数
    private Integer yinSun;         // 印损
    private Integer lingLiaoShu;    // 领料数（张）
    private String biaoMianChuLi;   // 表面处理
    private Integer yinShuaBanShu;  // 印刷版数目
    private String shengChanLuJing; // 生产路径
    private String paiBanFangShi;   // 排版方式

    // 进度相关字段（前端为 string 类型）
    private String kaiShiRiQi;   // 工序开始日期
    private String yuQiJieShu;   // 工序预期结束日期
    private Integer dangQianJinDu;  // 工序当前进度（前端为 number）

    // 后端扩展字段
    private Integer yiGouJianShu;  // 已采购件数
    private String head_PUR;  // 采购负责人
    private String head_OUT;  // 外发负责人

    private String notes;
}
