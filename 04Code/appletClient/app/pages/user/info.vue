<template>
    <!-- 用户信息页面 -->
    <view class="info">
        <view>
            <view>
                <view>头像</view>
                <view style="padding-left: 65%;">
                    <uni-file-picker limit="1" :delIcon="false" disablePreview :imageStyles="imageStyles" fileMediatype="image" @select="select">
                        <image :src="userInfo.picture" mode="widthFix"></image>
                    </uni-file-picker>
                </view>
            </view>
            <view>
                <view>手机号</view>
                <view @click="$refs.popup.open()">
                    <text>{{ userInfo.phone }}</text>
                    <uni-icons type="right" size="25"></uni-icons>
                </view>
            </view>
            <view>
                <view>昵称</view>
                <view style="display: flex;">
                    <input v-model="userInfo.nickname" style="margin-top: 8%;" @blur="updateNickname()" />
                    <uni-icons type="right" size="25"></uni-icons>
                </view>
            </view>
            <view>
                <view>绑定微信</view>
                <view v-if="userInfo.bindWeChat">已绑定微信</view>
                <view v-else style="color: #7e1e1f;" @click="bindWeChat()">点击进行绑定</view>
            </view>
        </view>
        <uni-popup ref="popup" type="dialog">
            <view class="popup">
                <view>
                    <view>修改手机号</view>
                    <view>
                        <uni-icons type="closeempty" size="30" @click="$refs.popup.close()"></uni-icons>
                    </view>
                </view>
                <view>
                    <view>
                        <input v-model="newPhone" placeholder="请输入需要修改的手机号码" />
                    </view>
                    <view>
                        <view>
                            <input v-model="smsCode" placeholder="请输入验证码" />
                        </view>
                        <view>
                            <button @click="sendSmsCode()">{{ countdown }}</button>
                        </view>
                    </view>
                </view>
                <view>
                    <button @click="updatePhone()">确定修改</button>
                </view>
            </view>
        </uni-popup>
        <view>
            <button @click="logout()">退出登录</button>
        </view>
    </view>
</template>

<script>
    import { bindWeChat, getUserInfo, logout, saveUserNickname, saveUserPhone, sendSmsCode } from '@/api/user.js';

    export default {
        data() {
            return {
                // 用户信息
                userInfo: '',
                // 输入信息
                newPhone: '',
                // 验证码
                smsCode: '',
                // 倒计时
                countdown: '获取验证码',
                imageStyles: {
                    width: 64,
                    height: 64,
                    border: {
                        radius: '50%',
                    },
                },
            };
        },

        mounted() {
            // 查询用户信息
            getUserInfo().then((res) => {
                this.userInfo = res.data;
            });
        },

        methods: {
            // 发送短信验证码
            sendSmsCode() {
                if (this.countdown === '获取成功') {
                    return uni.showToast({
                        icon: 'error',
                        title: '不可重复获取',
                        duration: 3000,
                    });
                }
                if (this.newPhone !== '') {
                    sendSmsCode(this.newPhone).then((res) => {
                        if (res.code === 200) {
                            this.countdown = '获取成功';
                            uni.showToast({
                                title: '获取成功，五分钟有效',
                                duration: 3000,
                            });
                        }
                    });
                } else {
                    uni.showToast({
                        icon: 'none',
                        title: '请输入手机号',
                        duration: 2000,
                    });
                }
            },

            // 修改手机号
            updatePhone() {
                if (this.smsCode !== '') {
                    const phoneCodeInfo = {};
                    phoneCodeInfo.phone = this.newPhone;
                    phoneCodeInfo.code = this.smsCode;
                    phoneCodeInfo.sysType = true;
                    saveUserPhone(phoneCodeInfo).then((res) => {
                        if (res.code === 200) {
                            getUserInfo().then((res) => {
                                this.userInfo = res.data;
                            });
                            this.$refs.popup.close();
                            this.countdown = '获取验证码';
                        }
                    });
                } else {
                    uni.showToast({
                        icon: 'none',
                        title: '请输入验证码',
                        duration: 2000,
                    });
                }
            },

            // 修改昵称
            updateNickname() {
                if (this.userInfo.nickname === '') {
                    uni.showToast({
                        title: '昵称不能为空',
                        duration: 3000,
                    });
                } else {
                    saveUserNickname(this.userInfo.nickname).then((res) => {
                        if (res.code === 200) {
                            getUserInfo().then((res) => {
                                this.userInfo = res.data;
                            });
                            uni.showToast({
                                icon: 'success',
                                title: '修改成功',
                                duration: 3000,
                            });
                        }
                    });
                }
            },

            // 上传头像
            select(e) {
                uni.uploadFile({
                    url: 'http://localhost:8081/api/v1/user/picture',
                    filePath: e.tempFilePaths[0],
                    name: 'picture',
                    formData: {},
                    header: {
                        Authorization: uni.getStorageSync('accessToken'),
                    },
                    success(res) {
                        const resJson = JSON.parse(res.data);
                        if (resJson.code === 200) {
                            getUserInfo().then((res) => {
                                this.userInfo = res.data;
                            });
                            uni.showToast({
                                icon: 'success',
                                title: '修改成功',
                                duration: 3000,
                            });
                        } else {
                            uni.showToast({
                                icon: 'error',
                                title: '请求失败，请重试！',
                                duration: 3000,
                            });
                        }
                    },
                });
            },

            // 绑定微信
            bindWeChat() {
                uni.login({
                    provider: 'weixin',
                    success: (resLogin) => {
                        uni.showLoading({
                            title: '加载中',
                        });
                        bindWeChat(resLogin.code).then((resBind) => {
                            console.log(resBind);
                            if (resBind.code != 200) {
                                uni.showToast({
                                    icon: 'error',
                                    title: '请求失败，请重试！',
                                    duration: 3000,
                                });
                                return uni.hideToast();
                            }
                            // 查询用户信息
                            getUserInfo().then((res) => {
                                this.userInfo = res.data;
                            });
                        });
                    },
                });
            },

            // 退出登录
            logout() {
                logout().then((res) => {
                    if (res.code === 200) {
                        uni.showToast({
                            icon: 'success',
                            title: '退出成功',
                            duration: 1000,
                        });
                        setTimeout(() => {
                            uni.reLaunch({
                                url: '/pages/user/login',
                            });
                        }, 1000);
                    } else {
                        uni.showToast({
                            icon: 'error',
                            title: '退出失败，请重试',
                            duration: 3000,
                        });
                    }
                });
            },
        },
    };
</script>

<style>
	.info {
		width: 100%;
		position: fixed;
		padding-bottom: 200%;
		background: #f8f8f8;
	}
	.info>view:first-child>view {
		display: flex;
		background: white;
		padding: 2% 5% 2% 5%;
		line-height: 100rpx;
	}
	.info>view:first-child>view:not(:last-child) {
		border-bottom: 1rpx #f5f5f5 solid;
	}
	.info>view:first-child>view>view {
		flex: 1;
	}
	.info>view:first-child>view image {
		width: 100%;
	}
	.info>view:first-child>view>view:first-child {
		font-size: 37rpx;
		color: #444444;
	}
	.info>view:first-child>view>view:last-child {
		font-size: 37rpx;
		color: #303133;
		text-align: right;
	}
	.info>view:first-child>view:last-child>view:last-child {
		color: #999999;
	}

	.popup {
		width: 600rpx;
		margin-left: -5%;
		background: white;
		border-radius: 10rpx;
		padding: 5%;
	}
	.popup>view:first-child {
		display: flex;
	}
	.popup>view:first-child>view:first-child {
		flex: 1;
		text-align: center;
		color: black;
		font-size: 40rpx;
		font-weight: 600;
		letter-spacing: 1rpx;
	}
	.popup>view:nth-child(2)>view:first-child {
		margin-top: 5%;
		font-size: 35rpx;
		padding-bottom: 3%;
		border-bottom: 1rpx #eeeeee solid;
	}
	.popup>view:nth-child(2)>view:nth-child(2) {
		display: flex;
		margin-top: 5%;
		border-bottom: 1rpx #eeeeee solid;
	}
	.popup>view:nth-child(2)>view:nth-child(2)>view {
		flex: 1;
	}
	.popup>view:nth-child(2)>view:nth-child(2)>view:first-child {
		margin-top: 5%;
		font-size: 35rpx;
		padding-bottom: 3%;
	}
	.popup>view:nth-child(2)>view:nth-child(2)>view:last-child button {
		width: 80%;
		height: 80rpx;
		color: white;
		background: #7e1e1f;
		line-height: 80rpx;
		border-radius: 5rpx;
		margin-left: 20%;
	}
	.popup>view:last-child {
		margin-top: 10%;
	}
	.popup>view:last-child button {
		background: #7e1e1f;
		color: white;
		font-weight: 600;
	}


	.info>view:last-child {
		position: fixed;
		bottom: 0rpx;
		padding: 2% 3% 2% 3%;
		width: 100%;
		background: white;
		height: 110rpx;
		border-top: 1rpx #eeeeee solid;
	}
	.info>view:last-child button {
		margin-right: 6%;
		height: 100rpx;
		background: #7e1e1f;
		border-radius: 8rpx;
		color: white;
		margin-top: 0.5%;
	}
</style>
