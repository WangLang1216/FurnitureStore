// 全局请求封装
const base_url = 'http://xiaopengyou.love:8081/api/v1'
// 需要修改token，和根据实际修改请求头
export default (params) => {
	let url = params.url;
	let method = params.method || "GET";
	let data = params.data || {};
	let header = {}
	if (method == "POST" || method == "PUT") {
		header = {
			// 'Content-type': 'application/x-www-form-urlencoded',
			"Content-type": "application/json",
			// "Content-type": "multipart/form-data",
			// "Content-type": "text/xml"
		};
	}
	// 获取本地token
	if (uni.getStorageSync("accessToken")) {
		header['Authorization'] = uni.getStorageSync("accessToken");
	}

	return new Promise((resolve, reject) => {
		uni.request({
			url: base_url + url,
			method: method,
			header: header,
			data: data,
			success(response) {
				const res = response
				// 根据返回的状态码做出对应的操作
				if (res.data.code === 200) {
					return resolve(res.data);
				} else {
					switch (res.data.code) {
						case 400:
							uni.clearStorageSync();
							uni.showModal({
								title: "提示",
								content: "请登录",
								showCancel: false,
								success(res) {
									return setTimeout(() => {
										uni.navigateTo({
											url: "/pages/user/login",
										})
									}, 1000);
								},
							});
							break;
						case 404:
							uni.showToast({
								title: '请求地址不存在...',
								duration: 2000,
							})
							break;
						default:
							uni.showToast({
								title: res.data.msg,
								duration: 2000,
							})
							break;
					}
				}
			},
			fail(err) {
				console.log(err)
				if (err.errMsg.indexOf('request:fail') !== -1) {
					wx.showToast({
						title: '网络异常',
						icon: "error",
						duration: 2000
					})
				} else {
					wx.showToast({
						title: '未知异常',
						duration: 2000
					})
				}
				reject(err);

			},
			complete() {
				// 不管成功还是失败都会执行
				uni.hideLoading();
				uni.hideToast();
			}
		});
	}).catch((e) => {});
};