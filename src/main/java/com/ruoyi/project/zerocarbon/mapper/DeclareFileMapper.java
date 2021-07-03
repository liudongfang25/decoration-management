package com.ruoyi.project.zerocarbon.mapper;

import com.ruoyi.project.zerocarbon.domain.DeclareFile;

import java.util.List;

/**
 * 参数配置 数据层
 * 
 * @author ruoyi
 */
public interface DeclareFileMapper
{
    public int insert(DeclareFile DeclareFile);

    public List<DeclareFile> selectByDeclarationId(Long declarationId);

    public int removeByDeclarationId(Long declarationId);

}