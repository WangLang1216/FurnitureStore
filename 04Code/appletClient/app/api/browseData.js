import request from '@/util/request.js'

// 获取品类空间信息
export const getCategorySpaceInfo = () => {
	return request({
		url: '/ua/category-spaceInfo',
	})
}

// 获取主页数据信息
export const geIndexData = () => {
	return request({
		url: '/ua/we-chat/index',
	})
}

// 获取产品详情
export const getProductInfo = (params) => {
	return request({
		url: '/ua/product-info',
		data: {
			product_id: params
		}
	})
}

// 获取产品规格信息
export const getProductSpecs = (params) => {
	return request({
		url: '/ua/product-specs',
		data: {
			product_id: params
		}
	})
}

// 修改产品热度
export const updateProductHeat = (productId, number) => {
	return request({
		url: '/ua/product-heat',
		data: {
			product_id: productId,
			number: number
		}
	})
}

// 筛选内容
export const getScreenProduct = (params) => {
	return request({
		url: '/ua/product-screen',
		method: 'POST',
		data: params
	})
}

// 喜好推荐
export const getUserLikeProduct = (params) => {
	return request({
		url: '/ua/product-like',
		data: {
			token: params
		}
	})
}


