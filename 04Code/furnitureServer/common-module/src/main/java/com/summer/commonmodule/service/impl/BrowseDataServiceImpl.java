package com.summer.commonmodule.service.impl;

import com.summer.commonmodule.entity.bo.CategorySpaceInfoBO;
import com.summer.commonmodule.entity.bo.HomeDataInfoBO;
import com.summer.commonmodule.entity.bo.WeChatIndexDataBO;
import com.summer.commonmodule.entity.dto.ProductRecordDTO;
import com.summer.commonmodule.entity.model.CategorySpace;
import com.summer.commonmodule.entity.vo.CategorySpaceVO;
import com.summer.commonmodule.entity.vo.WeChatIndexDataVO;
import com.summer.commonmodule.mapper.CategorySpaceMapper;
import com.summer.commonmodule.mapper.ProductInfoMapper;
import com.summer.commonmodule.service.BrowseDataService;
import com.summer.commonmodule.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 品类类型
     */
    private static final String CATEGORY_TYPE = "category";
    /**
     * 空间类型
     */
    private static final String SPACE_TYPE = "space";
    /**
     * 微信小程序主页数据key，存储在Redis中
     */
    private static final String HOME_DATA_KEY = "homeData";

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

    @Override
    public List<WeChatIndexDataVO> getWeChatIndexData() {
        // 从Redis中校验主页数据key状态
        boolean homeDataKey = redisUtil.hasKey(HOME_DATA_KEY);

        List<HomeDataInfoBO> homeDataInfoBOS = new ArrayList<>();

        // 如果存在，则使用该数据信息，不存在则使用默认信息
        if (homeDataKey) {
            List<Object> objects = redisUtil.lGet(HOME_DATA_KEY, 0, -1);
            for (Object object : objects) {
                homeDataInfoBOS.add((HomeDataInfoBO) object);
            }
        } else {
            HomeDataInfoBO sofa = new HomeDataInfoBO();
            HomeDataInfoBO bed = new HomeDataInfoBO();
            HomeDataInfoBO board = new HomeDataInfoBO();
            sofa.setTitle("松柏爆款沙发")
                    .setCategory("沙发")
                    .setShowType(true);
            bed.setTitle("松柏爆推大床")
                    .setCategory("bed")
                    .setShowType(true);
            board.setTitle("爆款餐桌椅")
                    .setCategory("board")
                    .setShowType(true);
            homeDataInfoBOS.add(sofa);
            homeDataInfoBOS.add(bed);
            homeDataInfoBOS.add(board);
        }

        List<WeChatIndexDataVO> weChatIndexDataVOS = new ArrayList<>();

        // 查询信息
        for (HomeDataInfoBO homeDataInfoBO : homeDataInfoBOS) {
            List<ProductRecordDTO> productRecordDTOS;
            if (Boolean.TRUE.equals(homeDataInfoBO.getShowType())) {
                productRecordDTOS = productInfoMapper.queryProductInfo("category", homeDataInfoBO.getCategory(),
                        "heat", Sort.Direction.DESC,
                        0, 8);
            } else {
                productRecordDTOS = productInfoMapper.queryProductInfo("category", homeDataInfoBO.getCategory(),
                        "create_time", Sort.Direction.DESC,
                        0, 8);
            }
            // 装填数据
            if (productRecordDTOS.isEmpty()) {
                continue;
            }
            List<WeChatIndexDataBO> weChatIndexDataBOS = new ArrayList<>();
            for (ProductRecordDTO dto : productRecordDTOS) {
                WeChatIndexDataBO weChatIndexData = new WeChatIndexDataBO();
                weChatIndexData.setProductId(dto.getProductId())
                        .setName(dto.getName())
                        .setImage(dto.getCarouselImages().get(0).getImage())
                        .setPrice(dto.getProductInfo().getPrice())
                        .setSold(dto.getProductInfo().getPrice());
                weChatIndexDataBOS.add(weChatIndexData);
            }

            WeChatIndexDataVO weChatIndexDataVO = new WeChatIndexDataVO();
            weChatIndexDataVO.setTitle(homeDataInfoBO.getTitle())
                    .setCommodity(weChatIndexDataBOS);

            weChatIndexDataVOS.add(weChatIndexDataVO);
        }

        return weChatIndexDataVOS;
    }


}
