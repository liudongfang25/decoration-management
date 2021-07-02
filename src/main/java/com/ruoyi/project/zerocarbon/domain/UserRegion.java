package com.ruoyi.project.zerocarbon.domain;

import com.ruoyi.framework.web.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserRegion extends BaseEntity {

  private Long id;

  private Long userId;
  private String region;

}
