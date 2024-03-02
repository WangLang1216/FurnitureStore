package com.summer.commonmodule.controller;

import com.summer.commonmodule.entity.vo.CategorySpaceVO;
import com.summer.commonmodule.entity.vo.WeChatIndexDataVO;
import com.summer.commonmodule.response.ResponseEntity;
import com.summer.commonmodule.service.BrowseDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 浏览数据信息
 * @author WangLang
 */
@RestController
@RequestMapping("/api/v1/ua")
public class BrowseDataController {

    @Autowired
    private BrowseDataService browseDataService;

    /**
     * 获取品类空间信息
     * @return 品类空间信息集合
     */
    @GetMapping("/category-spaceInfo")
    public ResponseEntity<List<CategorySpaceVO>> getCategorySpaceInfo() {
        List<CategorySpaceVO> categorySpaceVOS = browseDataService.getCategorySpaceInfo();

        return ResponseEntity.success(categorySpaceVOS);
    }

    /**
     * 获取微信小程序主页数据
     * @return 微信小程序主页数据信息集合
     */
    @GetMapping("/we-chat/index")
    public ResponseEntity<List<WeChatIndexDataVO>> getWeChatIndexData() {
        List<WeChatIndexDataVO> weChatIndexDataVOS = browseDataService.getWeChatIndexData();

        return ResponseEntity.success(weChatIndexDataVOS);
    }

}
