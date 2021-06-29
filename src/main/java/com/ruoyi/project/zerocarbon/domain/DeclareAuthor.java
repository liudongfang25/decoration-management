package com.ruoyi.project.zerocarbon.domain;

import com.ruoyi.framework.web.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DeclareAuthor extends BaseEntity {
  private Long id;
  private Long declarationId;
  private String name;
  private String unit;
  private String position;
  private String email;
  private String contactNumber;

}
