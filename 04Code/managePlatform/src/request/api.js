import { get, post, getDynamicynamic, postType, put, putBody, putBodyId, Delete, DeleteBody, DeleteBody1, patch, getFileUseBlobByPost } from './index'

// 刷新Token
export const refreshToken = p => post("/ua/token/refresh", p);

// 使用账号密码登录
export const loginByAccount = p => post("/ua/web/login", p);

// 使用手机号登录
export const loginByPhone = p => post("/ua/phone/login", p);

// 获取验证码
export const sendSmsCode = p => get("/ua/sms", p);

// 退出登录
export const logout = () => DeleteBody("/log-out");

// 查看订单状态集合
export const getOrderState = () => get("/order/state");

// 查询热度或销量信息
export const getProductHeatOrSales = p => getDynamicynamic("/product/heat-sales", p);

// 查询店铺销售信息
export const getShopSales = () => get("/shop-sales");

// 新增产品信息
export const addProductInfo = p => post("/product", p);

// 根据条件查询产品记录信息
export const getProductRecord = p => post("/product-record", p);

// 删除产品信息
export const delProductInfo = p => DeleteBody("/product", p);

// 根据ID查询产品信息
export const getProductById = p => getDynamicynamic("/product", p);

// 根据条件查询订单信息
export const getOrderInfo = p => post("/order", p);

// 批量修改订单状态
export const updateOrderState = p => putBody("/order", p);

// 设置Token过期时间
export const setTokenExpireTime = p => put("/token", p);

// 获取微信主页数据品类信息
export const getWeChatHomeDataCategory = () => get("/we-chat/home/category");

// 保存微信主页数据品类信息
export const saveWeChatHomeDataCategory = p => post("/we-chat/home/category", p);

// 获取用户信息
export const getUserInfo = (i, p) => postType("/users", i, p);

// 新增管理用户
export const addAdmin = p => post("/user", p);

// 删除管理端用户
export const delUser = p => post("/del-user", p);

// 修改用户状态
export const updateUserState = p => getDynamicynamic("/user/state", p);
