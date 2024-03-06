package com.summer.appletserver.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.summer.appletserver.entity.vo.OrderInfoVO;
import com.summer.appletserver.entity.vo.ShoppingInfoVO;
import com.summer.appletserver.entity.vo.ShoppingUpdateVO;
import com.summer.appletserver.entity.vo.ShoppingVO;
import com.summer.appletserver.service.UserProductService;
import com.summer.commonmodule.entity.bo.TokenInfoBO;
import com.summer.commonmodule.entity.model.*;
import com.summer.commonmodule.exception.RecordLoggerThrowException;
import com.summer.commonmodule.mapper.*;
import com.summer.commonmodule.response.ResponseEnum;
import com.summer.commonmodule.utils.EncryptionUtil;
import com.summer.commonmodule.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 用户产品信息
 * @author WangLang
 */
@Service
public class UserProductServiceImpl implements UserProductService {

    @Autowired
    private ProductRecordMapper productRecordMapper;

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Autowired
    private ShoppingInfoMapper shoppingInfoMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private UserLikeMapper userLikeMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private EncryptionUtil encryptionUtil;

    /**
     * 订单有效时间
     */
    private static final Integer ORDER_EXPIRE_TIME = 600;

    private static final Logger logger = LoggerFactory.getLogger(UserProductServiceImpl.class);

    @Override
    public Boolean getUserCollectSate(String token, String productId) {
        if (CharSequenceUtil.isBlank(token) || CharSequenceUtil.isBlank(productId)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        // 获取Token中的数据
        TokenInfoBO tokenInfoBO = (TokenInfoBO) redisUtil.get(encryptionUtil.publicKeyDecryption(token));
        // 查询产品记录信息
        ProductRecord productRecord = productRecordMapper.queryProductRecordById(productId);

        // 是否包含该用户
        return !Objects.isNull(productRecord.getCollect()) && productRecord.getCollect().contains(tokenInfoBO.getUserInfoTokenBO().getUserId());
    }

    @Override
    public void updateUserCollectSate(String token, String productId) {
        if (CharSequenceUtil.isBlank(token) || CharSequenceUtil.isBlank(productId)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        // 获取Token中的数据
        TokenInfoBO tokenInfoBO = (TokenInfoBO) redisUtil.get(encryptionUtil.publicKeyDecryption(token));
        // 查询产品记录信息
        ProductRecord productRecord = productRecordMapper.queryProductRecordById(productId);
        if (Objects.isNull(productRecord)) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, productId + "产品不存在", logger);
        }

        String collect = productRecord.getCollect();
        // 是否包含该用户
        boolean contains = !Objects.isNull(collect) && collect.contains(tokenInfoBO.getUserInfoTokenBO().getUserId());
        if (contains) {
            String[] split = collect.split(tokenInfoBO.getUserInfoTokenBO().getUserId() + ",");
            if (split.length == 0) {
                productRecord.setCollect(null);
            } else {
                productRecord.setCollect(split[0] + split[1]);
            }
        } else {
            productRecord.setCollect(tokenInfoBO.getUserInfoTokenBO().getUserId() + ",");
        }

        // 修改保存
        ProductRecord saveProductRecord = productRecordMapper.saveProductRecord(productRecord);
        if (Objects.isNull(saveProductRecord)) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, productId + "该产品不存在", logger);
        }

    }

    @Override
    public void addShopping(String token, ShoppingVO shoppingVO) {
        if (CharSequenceUtil.isBlank(token) || Objects.isNull(shoppingVO)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        // 获取Token中的数据
        TokenInfoBO tokenInfoBO = (TokenInfoBO) redisUtil.get(encryptionUtil.publicKeyDecryption(token));
        // 查询该产品是否存在
        ProductRecord productRecord = productRecordMapper.queryProductRecordById(shoppingVO.getProductId());
        if (Objects.isNull(productRecord)) {
            RecordLoggerThrowException.record(ResponseEnum.SHOW_FAIL, shoppingVO.getProductId() + "该产品不存在", logger);
        }

        // 新增订单信息
        ShoppingInfo shoppingInfo = new ShoppingInfo();
        BeanUtil.copyProperties(shoppingVO, shoppingInfo);
        shoppingInfo.setUserId(tokenInfoBO.getUserInfoTokenBO().getUserId());

        // 新增购物车信息
        ShoppingInfo info = shoppingInfoMapper.insertShoppingInfo(shoppingInfo);
        if (Objects.isNull(info)) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, logger);
        }

    }

    @Override
    public List<ShoppingInfoVO> getShopping(String token) {
        if (CharSequenceUtil.isBlank(token)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        // 获取Token中的数据
        TokenInfoBO tokenInfoBO = (TokenInfoBO) redisUtil.get(encryptionUtil.publicKeyDecryption(token));
        // 查询购物车信息
        List<ShoppingInfo> shoppingInfos = shoppingInfoMapper.queryShoppingInfoByUserId(tokenInfoBO.getUserInfoTokenBO().getUserId());

        // 装填数据
        List<ShoppingInfoVO> shoppingInfoVOS = new ArrayList<>();
        for (ShoppingInfo shoppingInfo : shoppingInfos) {
            ShoppingInfoVO shoppingInfoVO = new ShoppingInfoVO();
            BeanUtil.copyProperties(shoppingInfo, shoppingInfoVO);
            shoppingInfoVO.setIsSelect(false);
            shoppingInfoVOS.add(shoppingInfoVO);
        }

        return shoppingInfoVOS;
    }

    @Override
    public void updateShopping(ShoppingUpdateVO shoppingUpdateVO) {
        if (Objects.isNull(shoppingUpdateVO)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        // 查询购物车信息
        ShoppingInfo shoppingInfo = shoppingInfoMapper.queryShoppingById(shoppingUpdateVO.getShoppingId());
        if (Objects.isNull(shoppingInfo)) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, shoppingUpdateVO.getShoppingId() + "没有该购物信息", logger);
        }

        shoppingInfo.setQuantity(shoppingUpdateVO.getQuantity());
        // 修改购物车信息
        ShoppingInfo info = shoppingInfoMapper.saveShoppingInfo(shoppingInfo);
        if (Objects.isNull(info)) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, shoppingUpdateVO.getShoppingId() + "没有该购物信息", logger);
        }

    }

    @Override
    public void deleteShopping(String shoppingIds) {
        if (CharSequenceUtil.isBlank(shoppingIds)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, "要删除购物ID为空", logger);
        }

        // 删除
        long delCount = shoppingInfoMapper.deleteShoppingByIds(shoppingIds);

        if ((int) delCount != shoppingIds.split(StrUtil.COMMA).length) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, "已删除" + delCount, logger);
        }

    }

    @Override
    public void addOrder(OrderInfoVO orderInfoVO) {
        if (Objects.isNull(orderInfoVO)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        List<OrderInfo> orderInfos = new ArrayList<>();

        // 查询购物车数据
        List<ShoppingInfo> shoppingInfos = shoppingInfoMapper.queryShoppingListById(orderInfoVO.getShoppingIds());
        if (shoppingInfos.isEmpty()) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, orderInfoVO.getShoppingIds() + "没有该购物信息", logger);
        }
        for (ShoppingInfo shoppingInfo : shoppingInfos) {
            OrderInfo orderInfo = new OrderInfo();
            BeanUtil.copyProperties(shoppingInfo, orderInfo);
            // 设置状态
            orderInfo.setOrderId(IdUtil.simpleUUID())
                    .setState(orderInfoVO.getOrderState() ? 1 : 0);

            // 判断支付状态，未支付存入Redis中，过期时间未10分钟，已支付存储在数据库
            if (orderInfoVO.getOrderState()) {
                OrderInfo info = orderInfoMapper.insertOrderInfo(orderInfo);
                if (Objects.isNull(info)) {
                    RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, logger);
                }
            } else {
                orderInfos.add(orderInfo);
            }
        }
        // 存入Redis
        if (!orderInfoVO.getOrderState()) {
            boolean orderKey = redisUtil.set(shoppingInfos.get(0).getUserId(), orderInfos, ORDER_EXPIRE_TIME);
            if (!orderKey) {
                RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, "Redis存储失败", logger);
            }
        }

        // 删除购物车信息
        long delCount = shoppingInfoMapper.deleteShoppingByIds(orderInfoVO.getShoppingIds());
        if ((int) delCount != orderInfoVO.getShoppingIds().split(StrUtil.COMMA).length) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, "已删除" + delCount, logger);
        }

    }

    @Override
    public void addLikeWeight(String token, String productId) {
        if (CharSequenceUtil.isBlank(token) || CharSequenceUtil.isBlank(productId)) {
            RecordLoggerThrowException.record(ResponseEnum.HTTP_MESSAGE_NOT_READABLE, logger);
        }

        // 获取Token中的数据
        TokenInfoBO tokenInfoBO = (TokenInfoBO) redisUtil.get(encryptionUtil.publicKeyDecryption(token));
        // 查询产品信息
        ProductInfo productInfo = productInfoMapper.queryProductInfoById(productId);
        if (Objects.isNull(productInfo)) {
            RecordLoggerThrowException.record(ResponseEnum.SHOW_FAIL, productId + "产品信息为空", logger);
        }

        // 查询喜好信息
        UserLike userLike = userLikeMapper.queryUserLikeById(tokenInfoBO.getUserInfoTokenBO().getUserId());
        if (Objects.isNull(userLike)) {
            // 不存在者插入
            LikeProduct likeProduct = new LikeProduct();
            likeProduct.setCategory(productInfo.getCategory())
                    .setWeight(1);
            List<LikeProduct> likeProducts = new ArrayList<>();
            likeProducts.add(likeProduct);
            UserLike userLikeInfo = new UserLike();
            userLikeInfo.setUlId(tokenInfoBO.getUserInfoTokenBO().getUserId())
                    .setLikeProducts(likeProducts)
                    .setTotal(1);
            UserLike insertUserLike = userLikeMapper.insertUserLike(userLikeInfo);
            if (Objects.isNull(insertUserLike)) {
                RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, "插入失败", logger);
            }
            return;
        }

        // 存在则更新
        for (LikeProduct likeProduct : userLike.getLikeProducts()) {
            if (likeProduct.getCategory().equals(productInfo.getCategory())) {
                likeProduct.setWeight(likeProduct.getWeight() + 1);
                break;
            }
        }
        userLike.setTotal(userLike.getTotal() + 1);
        UserLike saveUserLike = userLikeMapper.saveUserLike(userLike);
        if (Objects.isNull(saveUserLike)) {
            RecordLoggerThrowException.record(ResponseEnum.INTERNAL_SERVER_ERROR, "保存失败", logger);
        }

    }
}
