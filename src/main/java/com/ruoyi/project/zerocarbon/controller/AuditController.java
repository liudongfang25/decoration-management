package com.ruoyi.project.zerocarbon.controller;

import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.zerocarbon.domain.Declaration;
import com.ruoyi.project.zerocarbon.domain.DeclareAuthor;
import com.ruoyi.project.zerocarbon.domain.UserRegion;
import com.ruoyi.project.zerocarbon.domain.dto.DeclarationDTO;
import com.ruoyi.project.zerocarbon.domain.vo.DeclarationAuditVo;
import com.ruoyi.project.zerocarbon.domain.vo.DeclarationVo;
import com.ruoyi.project.zerocarbon.mapper.UserRegionMapper;
import com.ruoyi.project.zerocarbon.service.IDeclarationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @类名: AuditController
 * @描述:
 * @作者:
 * @日期: 2021/6/26 15:07
 */
@Slf4j
@RequestMapping("/audit")
@RestController
public class AuditController extends BaseController {

    @Autowired
    private UserRegionMapper userRegionMapper;
    @Autowired
    private IDeclarationService declarationService;
    /**
     * 地区列表，如果是全国，则前端显示全部地区
     * @param userId
     * @return
     */
    @GetMapping("/decoration/region/list")
    public AjaxResult add(Long userId) {
        //每个用户只能提交一次
        Assert.notNull(userId);
        List<UserRegion> userRegions = userRegionMapper.selectByUserId(userId);
        if (CollectionUtils.isNotEmpty(userRegions))
            return AjaxResult.success(userRegions.stream().map(UserRegion::getRegion).collect(Collectors.toList()));
        return AjaxResult.success();
    }

    /**
     * 审核接口
     */
    @PostMapping("/decoration/status/save")
    public AjaxResult auditDecoration(@Validated @RequestBody DeclarationAuditVo auditVo) {
        Assert.notNull(auditVo.getDeclarationId());
        Assert.notNull(auditVo.getSaveType());

        return AjaxResult.success(declarationService.auditDecoration(auditVo));
    }

    /**
     * 报名列表
     * @param declarationVo
     * @return
     */
    @PostMapping("/decoration/signUp/list")
    public TableDataInfo signUpList(@Validated @RequestBody DeclarationVo declarationVo) {
        //每个用户只能提交一次
        startPage();
        if (declarationVo.getSaveType() != null){
            declarationVo.setSaveTypeList(Arrays.asList(0,1,2));
        }
        List<Declaration> list = declarationService.getDecorationList(declarationVo);
//        0：待提交（草稿）；1：已提交; 2:已报名（APP预约）
        return getDataTable(list);
    }

//    0：待提交（草稿）；1：已提交; 2:已报名（APP预约），未审核
//3.已审核（初审），未审核（复审）4.已驳回（初审）5.已审核（复审）6.已驳回（复审）

    /**
     * 初审列表
     * @param declarationVo
     * @return
     */
    @PostMapping("/decoration/preAudit/list")
    public TableDataInfo preAuditList(@Validated @RequestBody DeclarationVo declarationVo) {
        //每个用户只能提交一次
        startPage();
        if (declarationVo.getSaveType() != null){
            declarationVo.setSaveTypeList(Arrays.asList(2,3,4));
        }
        //2.未审核,3.已审核4.已驳回
        List<Declaration> list = declarationService.getDecorationList(declarationVo);
        return getDataTable(list);
    }

    /**
     * 复核列表
     * @param declarationVo
     * @return
     */
    @PostMapping("/decoration/recheck/list")
    public TableDataInfo recheckList(@Validated @RequestBody DeclarationVo declarationVo) {
        //每个用户只能提交一次
//        3.未审核5.已审核6.已驳回
        if (declarationVo.getSaveType() != null){
            declarationVo.setSaveTypeList(Arrays.asList(3,5,6));
        }
        List<Declaration> list = declarationService.getDecorationList(declarationVo);
        return getDataTable(list);
    }

    /**
     * 最终列表
     * @param declarationVo
     * @return
     */
    @PostMapping("/decoration/final/list")
    public TableDataInfo finalList(@Validated @RequestBody DeclarationVo declarationVo) {
        //每个用户只能提交一次
//        5.已审核
        declarationVo.setSaveType(5);
        List<Declaration> list = declarationService.getDecorationList(declarationVo);
        return getDataTable(list);
    }

    /**
     * 最终列表
     * @param dto
     * @return
     */
    @PostMapping("/decoration/export")
    public AjaxResult export(@Validated @RequestBody DeclarationDTO dto) {
        //每个用户只能提交一次
        Assert.notNull(dto.getAccount());

        return AjaxResult.success();
    }

    /**
     * 查看申报
     * @param declarationId
     * @return
     */
    @GetMapping("/decoration/detail")
    public AjaxResult detail(Long declarationId) {
        Assert.notNull(declarationId);
        return AjaxResult.success(declarationService.getDecorationDTO(declarationId));
    }

}
