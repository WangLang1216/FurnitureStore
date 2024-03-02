package com.summer.commonmodule.service;

import com.summer.commonmodule.entity.vo.CategorySpaceVO;
import com.summer.commonmodule.entity.vo.WeChatIndexDataVO;

import java.util.List;

/**
 * 浏览数据信息管理
 * @author WangLang
 */
public interface BrowseDataService {

    /**
     * 获取品类空间信息
     * @return 品类空间信息集合，信息包含在CategorySpaceVO中的List<CategorySpaceInfoBO>中
     */
    List<CategorySpaceVO> getCategorySpaceInfo();

    /**
     * 获取微信小程序主页数据
     * @return 微信小程序主页数据信息集合
     */
    List<WeChatIndexDataVO> getWeChatIndexData();
}
