package com.ruoyi.project.zerocarbon.controller;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.zerocarbon.auth.APIValidateUtil;
import com.ruoyi.project.zerocarbon.domain.Declaration;
import com.ruoyi.project.zerocarbon.domain.DeclarationFile;
import com.ruoyi.project.zerocarbon.domain.DeclareAuthor;
import com.ruoyi.project.zerocarbon.domain.dto.DeclarationDTO;
import com.ruoyi.project.zerocarbon.mapper.DeclarationMapper;
import com.ruoyi.project.zerocarbon.mapper.DeclareAuthorMapper;
import com.ruoyi.project.zerocarbon.mapper.DeclareFileMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @类名: DeclarationController
 * @描述:
 * @作者:
 * @日期: 2021/6/26 15:07
 */
@Slf4j
@RequestMapping("/declaration")
@RestController
public class DeclarationController extends BaseController {

    @Autowired
    private DeclarationMapper declarationMapper;
    @Autowired
    private DeclareAuthorMapper declareAuthorMapper;
    @Autowired
    private DeclareFileMapper declareFileMapper;
    @Autowired
    private APIValidateUtil apiValidateUtil;
    @Value("${declaration.filePath}")
    private String defaultFilePath;
    @Value("${declaration.auth.accessKeyId}")
    private String accessKeyId;
    @Value("${declaration.auth.accessKeySecret}")
    private String accessKeySecret;
    @Value("${declaration.auth.zyzUrl}")
    private String zyzUrl;
    @Value("${declaration.auth.tokenUrl}")
    private String tokenUrl;

    @GetMapping("/signature/get")
    public AjaxResult getSignature(String zyzid)
    {
        Map<String,String> mapv=new HashMap<String, String>();
        mapv.put("AccessKeyId", accessKeyId);
        if (StringUtils.isNotEmpty(zyzid)){
            mapv.put("zyzid", zyzid);
        }
        String key= null;
        try {
            key = apiValidateUtil.computeSignature(mapv, accessKeySecret);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("getSignature: zyzid {},key {}",zyzid,key);
        return AjaxResult.success("操作成功",key);
    }

    @GetMapping("/zyz/getUserByZyzid")
    public AjaxResult getUserByZyzid(String zyzid)
    {
        if (StringUtils.isEmpty(zyzid)){
            return AjaxResult.error("zyzid不能为空");
        }
        Map<String,String> mapv=new HashMap<String, String>();
        mapv.put("AccessKeyId", accessKeyId);
        if (StringUtils.isNotEmpty(zyzid)){
            mapv.put("zyzid", zyzid);
        }
        try {
            String key = apiValidateUtil.computeSignature(mapv, accessKeySecret);
            String rspStr = "zyzid="+mapv.get("zyzid")+"&AccessKeyId="+mapv.get("AccessKeyId")+"&Signature="+key;
            String response = HttpUtils.sendGet(zyzUrl, rspStr);

            return AjaxResult.success(JSONObject.parseObject(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error("获取用户信息失败");
    }

    /**
     * 根据token获取志愿者id
     * @param token
     * @return
     */
    @GetMapping("/zyz/getZyzidByToken")
    public AjaxResult getZyzidByToken(String token)
    {
        if (StringUtils.isEmpty(token)){
            return AjaxResult.error("token不能为空");
        }
        Map<String,String> mapv=new HashMap<String, String>();
        mapv.put("AccessKeyId", accessKeyId);
        try {
            String key = apiValidateUtil.computeSignature(mapv, accessKeySecret);
            String rspStr = "&AccessKeyId="+mapv.get("AccessKeyId")+"&Signature="+key;
            String response = HttpUtils.sendPost(tokenUrl, rspStr, token);
            return AjaxResult.success(JSONObject.parseObject(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error("获取志愿者id失败");
    }

//    public static void main(String[] args) {
////        Map<String,String> mapv=new HashMap<String, String>();
////        mapv.put("AccessKeyId", "a0f215286d96449baeac25dc179a714a");
////        mapv.put("zyzid", "1621735478637215929cb86994f57bd6d43b3689a288f");
////        try {
////            String s = new APIValidateUtil().computeSignature(mapv, "a333fe38ecc34de2b4dc0b7704a02ef5");
////            System.out.println(mapv.toString());
//////            String rspStr = "ip=" + ip + "&json=true";
////            String rspStr = "zyzid="+mapv.get("zyzid")+"&AccessKeyId="+mapv.get("AccessKeyId")+"&Signature="+s;
////            System.out.println(rspStr);
////            String s1 = HttpUtils.sendGet("https://third.api.zyh365.com/api/volunteer/info.do", rspStr);
////
////            System.out.println(s1);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        Map<String,String> mapv=new HashMap<String, String>();
//        mapv.put("AccessKeyId", "a0f215286d96449baeac25dc179a714a");
//        mapv.put("username", "13758228031");
//        mapv.put("password", "Aa756920");
//        try {
//            String s = new APIValidateUtil().computeSignature(mapv, "a333fe38ecc34de2b4dc0b7704a02ef5");
//            System.out.println(s);
////            //            String rspStr = "ip=" + ip + "&json=true";
////            String rspStr = "zyzid="+mapv.get("zyzid")+"&AccessKeyId="+mapv.get("AccessKeyId")+"&Signature="+s;
////            System.out.println(rspStr);
////            String s1 = HttpUtils.sendGet("https://third.api.zyh365.com/api/volunteer/info.do", rspStr);
////
////            System.out.println(s1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 注册
     * @param dto
     * @return
     */
    @PostMapping("/subscribe")
    public AjaxResult subscribe(@Validated @RequestBody DeclarationDTO dto) {
        //每个用户只能提交一次
        Assert.notNull(dto.getAccount());
        Declaration declaration = new Declaration();
        declaration.setAccount(dto.getAccount());
        Declaration declarationHistory = declarationMapper.selectByAccount(dto.getAccount());
        if (declarationHistory != null){
            return AjaxResult.success(declarationHistory);
        }
        declaration.setSaveType(dto.getSaveType());
        declarationMapper.insert(declaration);
        return AjaxResult.success(declaration);
    }

    /**
     * 保存或提交申报
     * @param dto
     * @return
     */
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody DeclarationDTO dto) {
        //每个用户只能提交一次
        Assert.notNull(dto.getAccount());
        Declaration declaration = new Declaration();
        declaration.setAccount(dto.getAccount());
        Declaration declarationHistory = declarationMapper.selectByAccount(dto.getAccount());
        if (declarationHistory != null){
            if (declarationHistory.getSaveType() != null && declarationHistory.getSaveType() ==1){
                return AjaxResult.error("申报已经提交，不可修改");
            }
            declarationMapper.deleteById(declarationHistory.getId());
            declareAuthorMapper.removeByDeclarationId(declarationHistory.getId());
            declareFileMapper.removeByDeclarationId(declarationHistory.getId());
        }
        BeanUtils.copyProperties(dto,declaration);
        //序列号,查询已有省份+managementType类型数据
        Integer countByRegion = declarationMapper.countByRegion(dto.getProvince(), dto.getManagementType());
        if (countByRegion == null || countByRegion<=0){
            declaration.setRegionSerial(dto.getProvince()+1);
        }else {
            int count = countByRegion + 1;
            declaration.setRegionSerial(dto.getProvince()+count);
        }
        //作者人数
        if (!CollectionUtils.isEmpty(dto.getDeclareAuthors())){
            declaration.setAuthorNumber(dto.getDeclareAuthors().size());
        }
        int saveSuccess = declarationMapper.insert(declaration);
        if (saveSuccess > 0){
            Long declarationId = declaration.getId();
            if (!CollectionUtils.isEmpty(dto.getDeclareAuthors())){
                for (int i = 0; i < dto.getDeclareAuthors().size(); i++) {
                    DeclareAuthor declareAuthor = dto.getDeclareAuthors().get(i);
                    declareAuthor.setDeclarationId(declarationId);
                    declareAuthorMapper.insert(declareAuthor);
                }
            }
            if (!CollectionUtils.isEmpty(dto.getDeclarationFiles())){
                for (int i = 0; i < dto.getDeclarationFiles().size(); i++) {
                    DeclarationFile declareFile = dto.getDeclarationFiles().get(i);
                    declareFile.setDeclarationId(declarationId);
                    declareFileMapper.insert(declareFile);
                }
            }
        }
        return AjaxResult.success();
    }

    /**
     * 编辑申报草稿
     * @param dto
     * @return
     */
    @PostMapping("/update")
    public AjaxResult update(@Validated @RequestBody DeclarationDTO dto) {
        //每个用户只能提交一次
        Declaration declarationHistory = declarationMapper.selectById(dto.getId());
        if (declarationHistory != null){
            if (declarationHistory.getSaveType() != null && declarationHistory.getSaveType() ==1){
                return AjaxResult.error("申报已经提交，不可修改");
            }
            declareAuthorMapper.removeByDeclarationId(declarationHistory.getId());
            declareFileMapper.removeByDeclarationId(declarationHistory.getId());
            BeanUtils.copyProperties(dto,declarationHistory);
            declarationMapper.updateById(declarationHistory);
            if (!CollectionUtils.isEmpty(dto.getDeclareAuthors())){
                Long declarationId = declarationHistory.getId();
                for (int i = 0; i < dto.getDeclareAuthors().size(); i++) {
                    DeclareAuthor declareAuthor = dto.getDeclareAuthors().get(i);
                    declareAuthor.setDeclarationId(declarationId);
                    declareAuthorMapper.insert(declareAuthor);
                }
            }
            if (!CollectionUtils.isEmpty(dto.getDeclarationFiles())){
                for (int i = 0; i < dto.getDeclarationFiles().size(); i++) {
                    DeclarationFile declareFile = dto.getDeclarationFiles().get(i);
                    declareFile.setDeclarationId(declarationHistory.getId());
                    declareFileMapper.insert(declareFile);
                }
            }
            return AjaxResult.success();
        }
        Declaration declaration = new Declaration();
        BeanUtils.copyProperties(dto,declaration);
        int saveSuccess = declarationMapper.insert(declaration);
        if (saveSuccess > 0){
            Long declarationId = declaration.getId();
            if (!CollectionUtils.isEmpty(dto.getDeclareAuthors())){
                for (int i = 0; i < dto.getDeclareAuthors().size(); i++) {
                    DeclareAuthor declareAuthor = dto.getDeclareAuthors().get(i);
                    declareAuthor.setDeclarationId(declarationId);
                    declareAuthorMapper.insert(declareAuthor);
                }
            }
            if (!CollectionUtils.isEmpty(dto.getDeclarationFiles())){
                for (int i = 0; i < dto.getDeclarationFiles().size(); i++) {
                    DeclarationFile declareFile = dto.getDeclarationFiles().get(i);
                    declareFile.setDeclarationId(declarationId);
                    declareFileMapper.insert(declareFile);
                }
            }
        }
        return AjaxResult.success();
    }

    /**
     * 查看申报
     * @param account
     * @return
     */
    @GetMapping("/detail")
    public AjaxResult detail(String account) {
        Assert.notNull(account);
        Declaration declarationHistory = declarationMapper.selectByAccount(account);
        DeclarationDTO declarationDTO = new DeclarationDTO();
        if (declarationHistory != null){
            BeanUtils.copyProperties(declarationHistory,declarationDTO);
            List<DeclareAuthor> collection = declareAuthorMapper.selectByDeclarationId(declarationHistory.getId());
            declarationDTO.setDeclareAuthors(collection);
            return AjaxResult.success(declarationDTO);
        }
        return AjaxResult.success();
    }

    @PostMapping("/attach/upload")
    public AjaxResult upload(@RequestParam(value = "file") MultipartFile file) {
        if (file != null){
            File dest = new File(defaultFilePath + System.currentTimeMillis() + "-" + file.getOriginalFilename());
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            return AjaxResult.success(dest.getAbsolutePath());
            return AjaxResult.success("操作成功",dest.getAbsolutePath());
        }
        return AjaxResult.success();
    }

    @GetMapping("/attach/delete")
    public AjaxResult removeRobot(String filePath) {
        Assert.notNull(filePath);
        if (new File(filePath).exists()){
            new File(filePath).delete();
        }
        return AjaxResult.success();
    }
}
