package com.summer.manageserver.controller;

import com.summer.commonmodule.entity.bo.HomeDataInfoBO;
import com.summer.commonmodule.response.ResponseEntity;
import com.summer.manageserver.entity.vo.*;
import com.summer.manageserver.service.OrderInfoService;
import com.summer.manageserver.service.ProductInfoService;
import com.summer.manageserver.service.RuleService;
import com.summer.manageserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 管理平台产品信息
 * @author WangLang
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ProductInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private UserService userService;

    /**
     * 查询订单状态
     * @return 订单状态数据
     */
    @GetMapping("/order/state")
    public ResponseEntity<OrderStateVO> getOrderState() {
        OrderStateVO orderStateVO = orderInfoService.getOrderState();

        return ResponseEntity.success(orderStateVO);
    }

    /**
     * 查询产品热度或销量榜单
     * @param type 类型，heat表示热度，sold表示销量
     * @return 产品热度/销售信息
     */
    @GetMapping("/product/heat-sales/{type}")
    public ResponseEntity<ProductHeatOrSalesVO> getProductHeatOrSales(@PathVariable("type") String type) {
        ProductHeatOrSalesVO productHeatOrSalesVO = productInfoService.getProductHeatOrSales(type);

        return ResponseEntity.success(productHeatOrSalesVO);
    }

    /**
     * 查询店铺销售信息
     * @return 产品销售信息集合
     */
    @GetMapping("/shop-sales")
    public ResponseEntity<List<ShopSalesVO>> getShopSales() {
        List<ShopSalesVO> shopSalesVOS = orderInfoService.getShopSales();

        return ResponseEntity.success(shopSalesVOS);
    }

    /**
     * 新增/修改产品信息
     */
    @PostMapping("/product")
    public ResponseEntity<Void> addProductInfo(@RequestParam("name") String name, @RequestParam("introduce") String introduce,
                                               @RequestParam("identifier") String identifier, @RequestParam("producer") String producer,
                                               @RequestParam("materialQuality") String materialQuality, @RequestParam("filler") String filler,
                                               @RequestParam("style") String style, @RequestParam("category") String category,
                                               @RequestParam("space") String space, @RequestParam("price") Integer price,
                                               @RequestParam("inventory") Integer inventory, @RequestParam("size") String size,
                                               @RequestParam("colour") String colour, @RequestParam("materialType") String materialType,
                                               @RequestParam("images") MultipartFile[] images, @RequestParam("carouselImages") MultipartFile[] carouselImages,
                                               @RequestParam("physicalImages") MultipartFile[] physicalImages, @RequestParam("detailsImages") MultipartFile[] detailsImages,
                                               @RequestParam("productId") String productId, @RequestParam("opt") Boolean opt) {
        ProductInfoVO productInfoVO = new ProductInfoVO();
        productInfoVO.setProductId(productId)
                .setName(name)
                .setIntroduce(introduce)
                .setIdentifier(identifier)
                .setProducer(producer)
                .setMaterialQuality(materialQuality)
                .setFiller(filler)
                .setStyle(style)
                .setCategory(category)
                .setSpace(space)
                .setPrice(price)
                .setInventory(inventory)
                .setSize(size)
                .setColour(colour)
                .setMaterialType(materialType)
                .setImages(images)
                .setCarouselImages(carouselImages)
                .setPhysicalImages(physicalImages)
                .setDetailsImages(detailsImages);
        if (Boolean.TRUE.equals(opt)) {
            productInfoService.addProductInfo(productInfoVO);
        } else {
            productInfoService.updateProductInfo(productInfoVO);
        }

        return ResponseEntity.success();
    }

    /**
     * 查询产品记录信息
     * @param queryVO 查询信息
     * @return 产品记录信息集合
     */
    @PostMapping("/product-record")
    public ResponseEntity<ProductRecordVO> getProductRecord(@RequestBody QueryVO queryVO) {
        ProductRecordVO productRecordVO = productInfoService.getProductRecord(queryVO.getFiled(), queryVO.getValue(), queryVO.getPage());

        return ResponseEntity.success(productRecordVO);
    }

    /**
     * 删除产品信息
     * @param productId 产品ID集合
     */
    @PostMapping("/product/del")
    public ResponseEntity<Void> delProductInfo(@RequestBody List<String> productId) {
        productInfoService.delProductInfo(productId);

        return ResponseEntity.success();
    }

    /**
     * 根据ID查询产品信息
     * @param id 产品ID
     * @return 产品信息
     */
    @GetMapping("/product/{id}")
    public ResponseEntity<ProductInfoVO> getProductById(@PathVariable("id") String id) {
        ProductInfoVO productInfoVO = productInfoService.getProductById(id);

        return ResponseEntity.success(productInfoVO);
    }

    /**
     * 查询订单信息
     * @param queryVO 查询信息
     * @return 订单信息集合
     */
    @PostMapping("/order")
    public ResponseEntity<OrderInfoVO> getOrderInfo(@RequestBody QueryVO queryVO) {
        OrderInfoVO orderInfoVO = orderInfoService.getOrderInfo(queryVO.getFiled(), queryVO.getValue(), queryVO.getPage());

        return ResponseEntity.success(orderInfoVO);
    }

    /**
     * 修改订单状态
     * @param orderStateUpdateVO 订单修改状态信息
     */
    @PutMapping("/order")
    public ResponseEntity<Void> updateOrderState(@RequestBody OrderStateUpdateVO orderStateUpdateVO) {
        orderInfoService.updateOrderState(orderStateUpdateVO);

        return ResponseEntity.success();
    }

    /**
     * 设置Token过期时间
     * @param accessTokenExpireTime 请求Token时间
     * @param refreshTokenExpireTime 刷新Token时间
     */
    @PutMapping("/token/{access}/{refresh}")
    public ResponseEntity<Void> setTokenExpireTime(@PathVariable("access") Integer accessTokenExpireTime, @PathVariable("refresh") Integer refreshTokenExpireTime) {
        ruleService.setTokenExpireTime(accessTokenExpireTime, refreshTokenExpireTime);

        return ResponseEntity.success();
    }

    /**
     * 获取微信主页数据品类
     * @return 微信小程序主页数据品类信息集合
     */
    @GetMapping("/we-chat/home/category")
    public ResponseEntity<List<HomeDataInfoBO>> getWeChatHomeDataCategory() {
        List<HomeDataInfoBO> homeDataInfoBOS = ruleService.getWeChatHomeDataCategory();

        return ResponseEntity.success(homeDataInfoBOS);
    }

    /**
     * 保存微信主页数据品类信息
     * @param homeDataInfoBOS 微信小程序主页数据品类信息集合
     */
    @PostMapping("/we-chat/home/category")
    public ResponseEntity<Void> saveWeChatHomeDataCategory(@RequestBody List<HomeDataInfoBO> homeDataInfoBOS) {
        ruleService.saveWeChatHomeDataCategory(homeDataInfoBOS);

        return ResponseEntity.success();
    }

    /**
     * 获取用户信息
     * @param queryVO 查询条件
     * @param sysType 系统类型，1为管理普通，2为小程序，0为全部
     * @return 用户信息集合
     */
    @PostMapping("/users/{type}")
    public ResponseEntity<List<UserInfoVO>> getUserInfo(@RequestBody QueryVO queryVO, @PathVariable("type") Integer sysType) {
        List<UserInfoVO> userInfoVOS = userService.getUserInfo(queryVO.getFiled(), queryVO.getValue(), sysType);

        return ResponseEntity.success(userInfoVOS);
    }

    /**
     * 新增管理用户
     * @param adminVO 用户信息
     */
    @PostMapping("/user")
    public ResponseEntity<Void> addAdmin(@RequestBody AdminVO adminVO) {
        userService.addAdmin(adminVO);

        return ResponseEntity.success();
    }

    /**
     * 删除管理端用户信息
     * @param userIds 用户ID集合
     */
    @PostMapping("/del-user")
    public ResponseEntity<Void> delUser(@RequestBody List<String> userIds) {
        userService.delUser(userIds);

        return ResponseEntity.success();
    }

    /**
     * 修改用户状态
     * @param userId 用户ID
     * @param state 状态
     * @param sysType 系统类型，true为管理端，false为小程序端
     */
    @GetMapping("/user/state/{id}/{state}/{type}")
    public ResponseEntity<Void> updateUserState(@PathVariable("id") String userId, @PathVariable("state") Boolean state,
                                                @PathVariable("type") Boolean sysType) {
        userService.updateUserState(userId, state, sysType);

        return ResponseEntity.success();
    }

}
