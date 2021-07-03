package com.ruoyi.project.zerocarbon.domain.vo;

import com.ruoyi.framework.web.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class DeclarationVo extends BaseEntity implements Serializable {

  private Long userId;
  private String managementName;
  private Integer managementType;
  private String title;
  private String region;
  private String unit;
  private String head;
  private String contactNumber;
  private String projectType;
  private String projectField;
  private String account;
  private String userName;//填报人
  private Integer saveType;//0：草稿；1：提交;2:APP预约
  private Integer authorNumber;//成员人数
  private String regionSerial;//省+序列号
  private List<Integer> saveTypeList;

}
