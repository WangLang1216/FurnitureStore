import request from '@/util/request.js'

// 微信登录
export const loginByWeChat = (params) => {
	return request({
		url: '/ua/wx/login',
		data: {
			code: params
		}
	})
}

// 绑定微信
export const bindWeChat = (params) => {
	return request({
		url: '/user/bind/we-chat',
		data: {
			code: params
		}
	})
}


// 使用手机号登录
export const loginByPhone = (params) => {
	return request({
		url: '/ua/phone/login',
		method: 'POST',
		data: params
	})
}

// 查询用户信息
export const getUserInfo = () => {
	return request({
		url: '/user',
	})
}

// 发送验证码
export const sendSmsCode = (params) => {
	return request({
		url: '/ua/sms',
		data: {
			phone: params
		}
	})
}

// 更改用户手机号信息
export const saveUserPhone = (params) => {
	return request({
		url: '/user/phone',
		method: 'PUT',
		data: params
	})
}

// 更改用户手机号信息
export const saveUserNickname = (params) => {
	return request({
		url: '/user/nickname',
		method: 'PUT',
		data: params
	})
}

// 退出登录
export const logout = () => {
	return request({
		url: '/log-out',
		method: 'DELETE',
	})
}

// 获取用户收藏状态
export const getUserCollectSate = (params) => {
	return request({
		url: '/user-product/collect',
		data: {
			product_id: params
		}
	})
}

// 修改用户收藏状态
export const updateUserCollectSate = (params) => {
	return request({
		url: '/user-product/collect',
		method: 'PUT',
		data: params
	})
}

// 新增购物车信息
export const addShopping = (params) => {
	return request({
		url: '/user-product/shopping',
		method: 'POST',
		data: params
	})
}

// 查询购物车信息
export const getShopping = () => {
	return request({
		url: '/user-product/shopping',
	})
}

// 修改购物车信息
export const updateShopping = (params) => {
	return request({
		url: '/user-product/shopping',
		method: 'PUT',
		data: params
	})
}

// 删除购物车信息
export const deleteShopping = (params) => {
	return request({
		url: '/user-product/shopping',
		method: 'DELETE',
		data: params
	})
}

// 新增订单
export const addOrder = (params) => {
	return request({
		url: '/user-product/order',
		method: 'POST',
		data: params
	})
}

// 修改喜好信息
export const addLikeWeight = (params) => {
	return request({
		url: '/user-product/like',
		method: 'POST',
		data: params
	})
}


