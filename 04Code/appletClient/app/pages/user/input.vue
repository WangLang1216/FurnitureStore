<template>
    <!-- 选择头像和填写昵称 -->
    <view class="input">
        <!-- 头像 -->
        <view>
            <image mode="aspectFit" :src="picture"></image>
        </view>
        <!-- 昵称 -->
        <view>
            <input v-model="nickName" type="nickname" class="uni-input" placeholder-style="color:#F76260" placeholder="请输入昵称" @focus="bindblur" />
        </view>
        <view>
            <button type="default" open-type="chooseAvatar" @chooseavatar="onChooseAvatar">上传头像</button>
            <button type="primary" @click="toIndex">进入小程序</button>
        </view>
    </view>
</template>

<script>
    export default {
        data() {
            return {
                nickName: '',
                picture: 'https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0',
            };
        },

        methods: {

            // 使用微信头像
            onChooseAvatar(e) {
                this.picture = e.detail.avatarUrl;
                console.log(this.picture);
            },

            // 获取微信昵称
            bindblur(e) {
                this.nickName = e.detail.value;
                console.log(this.nickName);
            },

            // 进入小程序
            toIndex() {
                uni.uploadFile({
                    url: 'http://localhost:8081/api/v1/user',
                    filePath: this.picture,
                    name: 'picture',
                    formData: {
                        nickname: this.nickName,
                    },
                    header: {
                        Authorization: uni.getStorageSync('accessToken'),
                    },
                    success(res) {
                        const resJson = JSON.parse(res.data);
                        if (resJson.code === 200) {
                            uni.reLaunch({
                                url: '/pages/index/index',
                            });
                        } else {
                            uni.showToast({
                                icon: 'error',
                                title: '请求失败，请重试！',
                                duration: 3000,
                            });
                            uni.reLaunch({
                                url: '/pages/user/login',
                            });
                        }
                    },
                });
                // 请求后台
                // uni.request({
                //     url: 'http://localhost:8081/api/v1/ua/user',
                //     method: 'POST',
                //     data: {
                //         nickname: this.nickName,
                //         picture: this.picture,
                //     },
                //     header: {
                //         'Content-type': 'application/x-www-form-urlencoded',
                //     },
                //     success: (resAppId) => {
                //         console.log(resAppId);
                //         uni.navigateTo({
                //             url: 'input',
                //         });
                //     },
                // });
            },

        },
    };
</script>

<style>
	.input {
		width: 90%;
		padding-left: 5%;
	}
	.input>view:first-child {
		width: 100%;
		height: 300rpx;
		text-align: center;
	}
	.input>view:first-child image {
		width: 30%;
	}
	.input>view:nth-child(2) {
		margin-top: 8%;
		height: 100rpx;
	}
	.input>view:nth-child(2) input {
		height: 80rpx;
		font-size: 1.1em;
		text-align: center;
	}
	.input>view:last-child {
		padding-top: 2%;
	}
	.input>view:last-child button {
		height: 100rpx;
		line-height: 100rpx;
	}
	.input>view:last-child button:last-child {
		margin-top: 5%;
		background: #7e1e1f;
	}
</style>
