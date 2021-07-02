package com.ruoyi.project.zerocarbon.service;


import com.ruoyi.project.zerocarbon.domain.Declaration;
import com.ruoyi.project.zerocarbon.domain.dto.DeclarationDTO;
import com.ruoyi.project.zerocarbon.domain.vo.DeclarationAuditVo;
import com.ruoyi.project.zerocarbon.domain.vo.DeclarationVo;

import java.util.List;

/**
 * 参数配置 服务层
 * 
 * @author ruoyi
 */
public interface IDeclarationService
{
    public Declaration auditDecoration(DeclarationAuditVo declarationAuditVo);

    public List<Declaration> getDecorationList(DeclarationVo declarationVo);

    public DeclarationDTO getDecorationDTO(Long declarationId);

}
