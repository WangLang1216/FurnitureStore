package com.summer.manageserver.entity.vo;

import com.summer.manageserver.entity.bo.UserInfoBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 用户信息
 * @author WangLang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserInfoVO {

    /**
     * 系统类型，1为管理普通，2为小程序，0为全部
     */
    private Integer sysType;

    /**
     * 用户信息集合
     */
    private List<UserInfoBO> userInfoBOS;

}
