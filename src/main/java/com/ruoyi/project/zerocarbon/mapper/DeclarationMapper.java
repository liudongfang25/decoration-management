package com.ruoyi.project.zerocarbon.mapper;

import com.ruoyi.project.zerocarbon.domain.Declaration;
import com.ruoyi.project.zerocarbon.domain.vo.DeclarationVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 参数配置 数据层
 * 
 * @author ruoyi
 */
public interface DeclarationMapper
{
    public int insert(Declaration declaration);

//    public int update(Declaration declaration);

    public void updateById(Declaration declaration);

    public Declaration selectById(Long id);

    public Declaration selectByAccount(String account);

    public int deleteById(Long configId);

    public List<Declaration> selectDecorationList(DeclarationVo declarationVo);

    Integer countByRegion(@Param("province") String province, @Param("managementType")Integer managementType);

}