package com.summer.commonmodule.service.impl;

import com.summer.commonmodule.entity.bo.CategorySpaceInfoBO;
import com.summer.commonmodule.entity.model.CategorySpace;
import com.summer.commonmodule.entity.vo.CategorySpaceVO;
import com.summer.commonmodule.mapper.CategorySpaceMapper;
import com.summer.commonmodule.service.BrowseDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 浏览数据信息管理
 * @author WangLang
 */
@Service
public class BrowseDataServiceImpl implements BrowseDataService {

    @Autowired
    private CategorySpaceMapper categorySpaceMapper;

    /**
     * 品类类型
     */
    private static final String CATEGORY_TYPE = "category";
    /**
     * 空间类型
     */
    private static final String SPACE_TYPE = "space";

    private static final Logger logger = LoggerFactory.getLogger(BrowseDataServiceImpl.class);

    @Override
    public List<CategorySpaceVO> getCategorySpaceInfo() {
        CategorySpaceVO categoryVO = new CategorySpaceVO();
        CategorySpaceVO spaceVO = new CategorySpaceVO();

        List<CategorySpaceInfoBO> categoryList = new ArrayList<>();
        List<CategorySpaceInfoBO> spaceList = new ArrayList<>();

        List<CategorySpace> categorySpaces = categorySpaceMapper.queryCategorySpaceAll();
        for (CategorySpace categorySpace : categorySpaces) {
            CategorySpaceInfoBO categorySpaceInfoBO = new CategorySpaceInfoBO();
            categorySpaceInfoBO.setCsId(categorySpace.getCsId())
                    .setName(categorySpace.getName())
                    .setImage(categorySpace.getImage())
                    .setHeat(categorySpace.getHeat());
            // 判断类型
            if (CATEGORY_TYPE.equals(categorySpace.getType())) {
                categoryList.add(categorySpaceInfoBO);
            } else {
                spaceList.add(categorySpaceInfoBO);
            }
        }

        // 装填数据
        categoryVO.setType(CATEGORY_TYPE)
                        .setSpaceInfo(categoryList);
        spaceVO.setType(SPACE_TYPE)
                .setSpaceInfo(spaceList);

        List<CategorySpaceVO> categorySpaceVOS = new ArrayList<>();
        categorySpaceVOS.add(categoryVO);
        categorySpaceVOS.add(spaceVO);

        return categorySpaceVOS;
    }
}
