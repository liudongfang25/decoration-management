package com.ruoyi.project.zerocarbon.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class DeclarationAuditVo implements Serializable {

  private Long declarationId;

  private Integer saveType;//0：草稿；1：提交;2:APP预约

}
