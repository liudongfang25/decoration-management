package com.ruoyi.project.zerocarbon.domain;

import com.ruoyi.framework.web.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Declaration extends BaseEntity {
  private Long id;

  private String managementName;
  private Integer managementType;
  private String title;
  private String province;
  private String unit;
  private String head;
  private String contactNumber;
  private String projectType;
  private String projectField;
  private String projectProfile;
  private String briefDescription;
  private String innovation;
  private String practicability;
  private String exhibition;
  private String attachUrl;
  private String account;
  private String userName;
  private Integer saveType;//0：草稿；1：提交;2:APP预约
  private Integer authorNumber;//成员人数
  private String regionSerial;//省+序列号

}
