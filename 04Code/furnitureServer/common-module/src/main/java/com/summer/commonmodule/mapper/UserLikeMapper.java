package com.summer.commonmodule.mapper;

import com.summer.commonmodule.entity.model.UserLike;

/**
 * 用户喜好信息
 * @author WangLang
 */
public interface UserLikeMapper {

    /**
     * 根据ID查询用户喜好信息
     * @param ulId 喜好ID
     * @return 用户喜好信息
     */
    UserLike queryUserLikeById(String ulId);

    /**
     * 插入用户喜好信息
     * @param userLike 用户喜好信息
     * @return 用户喜好信息
     */
    UserLike insertUserLike(UserLike userLike);

    /**
     * 保存用户喜好信息
     * @param userLike 用户喜好信息
     * @return 用户喜好信息
     */
    UserLike saveUserLike(UserLike userLike);

}
