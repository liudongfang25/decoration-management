package com.ruoyi.project.zerocarbon.domain;

import com.ruoyi.framework.web.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DeclarationFile extends BaseEntity {

  private Long id;

  private Long declarationId;
  private Integer type;
  private String path;

}
