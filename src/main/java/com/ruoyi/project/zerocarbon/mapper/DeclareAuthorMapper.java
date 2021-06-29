package com.ruoyi.project.zerocarbon.mapper;

import com.ruoyi.project.zerocarbon.domain.DeclareAuthor;

import java.util.List;

/**
 * 参数配置 数据层
 * 
 * @author ruoyi
 */
public interface DeclareAuthorMapper
{
    public int insert(DeclareAuthor declareAuthor);

    public List<DeclareAuthor> selectByDeclarationId(Long declarationId);

    public void removeByDeclarationId(Long declarationId);
}