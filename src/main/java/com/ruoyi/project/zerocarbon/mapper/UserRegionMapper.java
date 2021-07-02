package com.ruoyi.project.zerocarbon.mapper;

import com.ruoyi.project.zerocarbon.domain.UserRegion;

import java.util.List;

/**
 * 参数配置 数据层
 * 
 * @author ruoyi
 */
public interface UserRegionMapper
{
    public int insert(UserRegion userRegion);

    public List<UserRegion> selectByUserId(Long userId);

    public int deleteByUserId(Long userId);

}