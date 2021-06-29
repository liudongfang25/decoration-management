package com.ruoyi.project.zerocarbon.domain;

import com.ruoyi.framework.web.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Declaration extends BaseEntity {
  private Long id;

  private String title;
  private String province;
  private String unit;
  private String head;
  private String contactNumber;
  private String projectType;
  private String projectField;
  private String projectProfile;
  private String briefDescription;
  private String attachUrl;
  private String account;
  private String userName;
  private Integer saveType;//0：草稿；1：提交;2:APP预约

}
