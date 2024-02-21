<template>
    <!-- 用户信息页面 -->
    <view class="info">
        <view>
            <view>
                <view>头像</view>
                <view style="padding-left: 65%;">
                    <uni-file-picker limit="1" :delIcon="false" disablePreview :imageStyles="imageStyles" fileMediatype="image" @success="success" @fail="fail">
                        <image src="../../static/images/default-avatar.png" mode="widthFix"></image>
                    </uni-file-picker>
                </view>
            </view>
            <view>
                <view>手机号</view>
                <view @click="$refs.popup.open()">
                    <text>{{ phone }}</text>
                    <uni-icons type="right" size="25"></uni-icons>
                </view>
            </view>
            <view>
                <view>昵称</view>
                <view style="display: flex;">
                    <input v-model="name" style="margin-top: 8%;" />
                    <uni-icons type="right" size="25"></uni-icons>
                </view>
            </view>
            <view>
                <view>绑定微信</view>
                <view>已绑定微信</view>
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
                        <input placeholder="请输入需要修改的手机号码" />
                    </view>
                    <view>
                        <view>
                            <input placeholder="请输入验证码" />
                        </view>
                        <view>
                            <button>获取验证码</button>
                        </view>
                    </view>
                </view>
                <view>
                    <button>确定修改</button>
                </view>
            </view>
        </uni-popup>
        <view>
            <button>退出登录</button>
        </view>
    </view>
</template>

<script>
    export default {
        data() {
            return {
                phone: '17345449129',
                name: '诗随远方',

                imageStyles: {
                    width: 64,
                    height: 64,
                    border: {
                        radius: '50%',
                    },
                },
            };
        },

        methods: {
            // 上传成功
            success(e) {
                console.log('上传成功');
            },

            // 上传失败
            fail(e) {
                console.log('上传失败：', e);
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
