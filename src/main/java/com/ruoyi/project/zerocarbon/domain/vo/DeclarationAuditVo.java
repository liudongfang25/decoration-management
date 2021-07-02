package com.ruoyi.project.zerocarbon.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class DeclarationAuditVo implements Serializable {

  @NotBlank(message = "申报id不能为空")
  private Long declarationId;
  @NotBlank(message = "审核类型不能为空")
  private Integer saveType;//0：草稿；1：提交;2:APP预约

}
