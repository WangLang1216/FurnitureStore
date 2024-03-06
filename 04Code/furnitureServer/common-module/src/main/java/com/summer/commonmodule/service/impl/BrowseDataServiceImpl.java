package com.summer.commonmodule.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.summer.commonmodule.entity.bo.CategorySpaceInfoBO;
import com.summer.commonmodule.entity.bo.HomeDataInfoBO;
import com.summer.commonmodule.entity.bo.TokenInfoBO;
import com.summer.commonmodule.entity.bo.WeChatIndexDataBO;
import com.summer.commonmodule.entity.dto.ProductRecordDTO;
import com.summer.commonmodule.entity.model.*;
import com.summer.commonmodule.entity.vo.CategorySpaceVO;
import com.summer.commonmodule.entity.vo.ProductInfoVO;
import com.summer.commonmodule.entity.vo.ProductSpecsVO;
import com.summer.commonmodule.entity.vo.WeChatIndexDataVO;
import com.summer.commonmodule.exception.RecordLoggerThrowException;
import com.summer.commonmodule.mapper.*;
import com.summer.commonmodule.response.ResponseEnum;
import com.summer.commonmodule.service.BrowseDataService;
import com.summer.commonmodule.utils.EncryptionUtil;
import com.summer.commonmodule.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private ProductSpecsMapper productSpecsMapper;

    @Autowired
    private ProductRecordMapper productRecordMapper;

    @Autowired
    private UserLikeMapper userLikeMapper;

    @Autowired
    private EncryptionUtil encryptionUtil;

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
    /**
     *
     */
    private static final Integer PRODUCT_EXPIRE_TIME = 24 * 60 * 60;

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
            if (homeDataInfoBO.getShowType()) {
                productRecordDTOS = productInfoMapper.queryProductInfo("category", homeDataInfoBO.getCategory(),
                        "productInfo.heat", Sort.Direction.DESC,
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
            weChatProductDataCopy(weChatIndexDataBOS, productRecordDTOS);

            WeChatIndexDataVO weChatIndexDataVO = new WeChatIndexDataVO();
            weChatIndexDataVO.setTitle(homeDataInfoBO.getTitle())
                    .setCommodity(weChatIndexDataBOS);

            weChatIndexDataVOS.add(weChatIndexDataVO);
        }

        return weChatIndexDataVOS;
    }

    @Override
    public ProductInfoVO getProductInfo(String productId) {
        if(CharSequenceUtil.isBlank(productId)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        // 从Redis中查询是否存在该产品信息，存在则从Redis中查询
        boolean productInfoKey = redisUtil.hasKey(productId);
        if (productInfoKey) {
            return (ProductInfoVO) redisUtil.get(productId);
        }

        ProductInfoVO productInfoVO = new ProductInfoVO();
        // 不存在则从数据库中查询，根据id只会查询出一个
        List<ProductRecordDTO> productRecordDTOS = productInfoMapper.queryProductInfo("_id", productId, "create_time", Sort.Direction.DESC, 0, 1);
        for (ProductRecordDTO dto : productRecordDTOS) {
            // 复制
            BeanUtil.copyProperties(dto, productInfoVO);
            // 修改内容
            productInfoVO.setPrice(dto.getProductInfo().getPrice())
                    .setSold(dto.getProductInfo().getSold())
                    .setHeat(dto.getProductInfo().getHeat());
        }

        // 存储在Redis中，失效时间为一天，且无需关心存储状态，存储失败不需要抛出异常
        redisUtil.set(productId, productInfoVO, PRODUCT_EXPIRE_TIME);

        return productInfoVO;
    }

    @Override
    public ProductSpecsVO getProductSpecs(String productId) {
        if(CharSequenceUtil.isBlank(productId)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        // 查询产品规格信息
        ProductSpecs productSpecs = productSpecsMapper.queryProductSpecsById(productId);
        List<String> sizeImage = new ArrayList<>();
        for (Image image : productSpecs.getImages()) {
            sizeImage.add(image.getImage());
        }

        ProductSpecsVO productSpecsVO = new ProductSpecsVO();
        BeanUtil.copyProperties(productSpecs, productSpecsVO);
        productSpecsVO.setSizeImages(sizeImage);

        return productSpecsVO;
    }

    @Override
    public void updateProductHeat(String productId, Integer number) {
        if (CharSequenceUtil.isBlank(productId) || Objects.isNull(number)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        // 查询该产品
        ProductRecord productRecord = productRecordMapper.queryProductRecordById(productId);
        if (Objects.isNull(productRecord)) {
            RecordLoggerThrowException.record(ResponseEnum.SHOW_FAIL,  productId+ "产品不存在", logger);
        }

        // 修改热度
        productRecord.setHeat(productRecord.getHeat() + number);
        ProductRecord saveProductRecord = productRecordMapper.saveProductRecord(productRecord);
        if (Objects.isNull(saveProductRecord)) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, logger);
        }

        // Redis中可能有该信息，进行更新
        boolean productKey = redisUtil.hasKey(productId);
        if (productKey) {
            ProductInfoVO productInfoVO = (ProductInfoVO) redisUtil.get(productId);
            productInfoVO.setHeat(saveProductRecord.getHeat());
            redisUtil.del(productId);
            redisUtil.set(productId, productInfoVO, PRODUCT_EXPIRE_TIME);
        }

    }

    @Override
    public List<WeChatIndexDataBO> getScreenProduct(String name, String field, String value, String sortField, Integer sort) {
        List<WeChatIndexDataBO> weChatIndexDataBOS = new ArrayList<>();

        Sort.Direction direction = Sort.Direction.DESC;
        if (!Objects.isNull(sort) && sort == 0) {
            direction = Sort.Direction.ASC;
        }
        // 查询
        List<ProductRecordDTO> productRecordDTOS = productInfoMapper.queryProductInfo(name, field, value, sortField, direction, 0, 20);

        weChatProductDataCopy(weChatIndexDataBOS, productRecordDTOS);

        return weChatIndexDataBOS;
    }

    @Override
    public List<WeChatIndexDataBO> getUserLikeProduct(String token) {
        List<WeChatIndexDataBO> weChatIndexDataBOS = new ArrayList<>();

        // 默认查询
        List<ProductRecordDTO> productRecordDTOS = productInfoMapper.queryProductInfo(null, null, null, null, null, 0, 10);
        weChatProductDataCopy(weChatIndexDataBOS, productRecordDTOS);
        // 判断该用户是否登录存在，不存在则未登录系统，默认推送
        if (CharSequenceUtil.isBlank(token)) {
            return weChatIndexDataBOS;
        }

        // 获取Token中的数据
        TokenInfoBO tokenInfoBO = (TokenInfoBO) redisUtil.get(encryptionUtil.publicKeyDecryption(token));
        if (Objects.isNull(tokenInfoBO)) {
            return weChatIndexDataBOS;
        }
        // 通过用户ID查询
        UserLike userLike = userLikeMapper.queryUserLikeById(tokenInfoBO.getUserInfoTokenBO().getUserId());
        if (Objects.isNull(userLike)) {
            return weChatIndexDataBOS;
        }

        // 根据权重计算比例并请求数量
        List<WeChatIndexDataBO> weChatIndexDataBOList = new ArrayList<>();
        for (LikeProduct likeProduct : userLike.getLikeProducts()) {
            int proportion = likeProduct.getWeight() / userLike.getTotal() * 10;
            if (proportion > 0) {
                List<ProductRecordDTO> productRecordDTOList = productInfoMapper.queryProductInfo("category",
                        likeProduct.getCategory(), "productInfo.heat", Sort.Direction.DESC, 0, proportion);

                weChatProductDataCopy(weChatIndexDataBOList, productRecordDTOList);
            }
        }

        return weChatIndexDataBOList;
    }

    /**
     * 数据复制转换
     * @param weChatIndexDataBOS 数据集合
     * @param productRecordDTOS 查询数据集合
     */
    private void weChatProductDataCopy(List<WeChatIndexDataBO> weChatIndexDataBOS, List<ProductRecordDTO> productRecordDTOS) {
        for (ProductRecordDTO dto : productRecordDTOS) {
            WeChatIndexDataBO weChatIndexData = new WeChatIndexDataBO();
            weChatIndexData.setProductId(dto.getProductId())
                    .setName(dto.getName())
                    .setImage(dto.getCarouselImages().get(0).getImage())
                    .setPrice(dto.getProductInfo().getPrice())
                    .setSold(dto.getProductInfo().getPrice());
            weChatIndexDataBOS.add(weChatIndexData);
        }
    }

}
