package com.ruoyi.project.zerocarbon.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.zerocarbon.domain.Declaration;
import com.ruoyi.project.zerocarbon.domain.DeclareAuthor;
import com.ruoyi.project.zerocarbon.domain.dto.DeclarationDTO;
import com.ruoyi.project.zerocarbon.domain.vo.DeclarationAuditVo;
import com.ruoyi.project.zerocarbon.domain.vo.DeclarationVo;
import com.ruoyi.project.zerocarbon.mapper.DeclarationMapper;
import com.ruoyi.project.zerocarbon.mapper.DeclareAuthorMapper;
import com.ruoyi.project.zerocarbon.service.IDeclarationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    private DeclareAuthorMapper declareAuthorMapper;
    @Override
    public Declaration auditDecoration(DeclarationAuditVo declarationAuditVo) {
        Declaration declaration = new Declaration();
        declaration.setId(declarationAuditVo.getDeclarationId());
        declaration.setSaveType(declarationAuditVo.getSaveType());
        declarationMapper.updateById(declaration);
        return declarationMapper.selectById(declarationAuditVo.getDeclarationId());
    }

    @Override
    public List<Declaration> getDecorationList(DeclarationVo declarationVo) {
        if (StringUtils.isEmpty(declarationVo.getRegion())){
            return new ArrayList<>();
        }
        return declarationMapper.selectDecorationList(declarationVo);
    }

    @Override
    public DeclarationDTO getDecorationDTO(Long declarationId) {
        Declaration declarationHistory = declarationMapper.selectById(declarationId);
        DeclarationDTO declarationDTO = new DeclarationDTO();
        if (declarationHistory != null){
            BeanUtils.copyProperties(declarationHistory,declarationDTO);
            List<DeclareAuthor> collection = declareAuthorMapper.selectByDeclarationId(declarationHistory.getId());
            declarationDTO.setDeclareAuthors(collection);
            return declarationDTO;
        }
        return null;
    }

}
