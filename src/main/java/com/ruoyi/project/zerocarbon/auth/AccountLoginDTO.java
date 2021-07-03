package com.ruoyi.project.zerocarbon.auth;

import lombok.Data;

import java.io.Serializable;

/**
 * @类名: AccountLogin
 * @描述:
 * @作者: liudf
 * @日期: 2021/7/3 12:02
 */
@Data
public class AccountLoginDTO implements Serializable {

    private String account;
    private String password;

}
