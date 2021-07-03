package com.ruoyi.project.zerocarbon.mapper;

import com.ruoyi.project.zerocarbon.domain.DeclarationFile;

import java.util.List;

/**
 * 参数配置 数据层
 * 
 * @author ruoyi
 */
public interface DeclareFileMapper
{
    public int insert(DeclarationFile declarationFile);

    public List<DeclarationFile> selectByDeclarationId(Long declarationId);

    public int removeByDeclarationId(Long declarationId);

}