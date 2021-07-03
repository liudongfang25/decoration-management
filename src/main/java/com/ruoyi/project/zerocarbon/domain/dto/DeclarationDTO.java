package com.ruoyi.project.zerocarbon.domain.dto;

import com.ruoyi.project.zerocarbon.domain.DeclareFile;
import com.ruoyi.project.zerocarbon.domain.DeclareAuthor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DeclarationDTO implements Serializable {
  private static final long serialVersionUID = -455603808262483595L;

  private Long id;
  private String managementName;
  private Integer managementType;
  private String title;
  private String province;
  private String unit;
  private String head;
  private String contactNumber;
  private String projectType;//项目组别，多选，以逗号分隔
  private String projectField;//项目领域，多选，以逗号分隔
  private String projectProfile;
  private String briefDescription;
  private String innovation;
  private String practicability;
  private String exhibition;
  private String attachUrl;
  private String account;
  private String userName;
  private Integer saveType;//0：草稿；1：提交
  private List<DeclareAuthor> declareAuthors;
  private List<DeclareFile> DeclareFiles;

}
