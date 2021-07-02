package com.ruoyi.project.zerocarbon.service.impl;

import com.ruoyi.project.zerocarbon.domain.Declaration;
import com.ruoyi.project.zerocarbon.domain.vo.DeclarationAuditVo;
import com.ruoyi.project.zerocarbon.domain.vo.DeclarationVo;
import com.ruoyi.project.zerocarbon.mapper.DeclarationMapper;
import com.ruoyi.project.zerocarbon.service.IDeclarationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 参数配置 服务层实现
 * 
 * @author ruoyi
 */
@Service
public class DeclarationServiceImpl implements IDeclarationService
{
    @Autowired
    private DeclarationMapper declarationMapper;

    @Override
    public void auditDecoration(DeclarationAuditVo declarationAuditVo) {
        Declaration declaration = new Declaration();
        declaration.setId(declarationAuditVo.getDeclarationId());
        declaration.setSaveType(declarationAuditVo.getSaveType());
        declarationMapper.updateById(declaration);
    }

    @Override
    public List<Declaration> getDecorationList(DeclarationVo declarationVo) {
        return declarationMapper.selectDecorationList(declarationVo);
    }

}
