<template>
    <!-- 手机号注册登录页面 -->
    <view class="other">
        <view class="title">
            <text>其他手机号登录</text>
            <text>未注册手机验证后即完成注册</text>
        </view>
        <view class="input uni-column uni-flex">
            <view>
                <input v-model="phone" class="uni-input" maxlength="11" type="tel" focus placeholder="请输入手机号码" />
            </view>
            <view class="code">
                <input v-model="code" class="uni-input" maxlength="6" type="number" placeholder="请输入验证码" />
                <button type="default" plain="true" @click="obtainCode">{{ obtainText }}</button>
            </view>
        </view>
        <view class="login">
            <button type="primary" @click="login">登录</button>
        </view>
    </view>
</template>

<script>
    export default {
        data() {
            return {
                phone: '',
                code: '',
                obtainText: '获取验证码',
            };
        },

        methods: {
            // 获取验证码
            obtainCode() {
                if (this.phone != '') {
                    uni.request({
                        url: 'http://localhost:8080/api/v1/code',
                        method: 'GET',
                        data: {
                            phone: this.phone,
                        },
                        header: {
                            'Content-type': 'application/x-www-form-urlencoded',
                        },
                        success: (res) => {
                            this.obtainText = '已发送';
                            setTimeout(() => {
                                this.obtainText = '获取验证码';
                            }, 30000);
                            console.log(res);
                        },
                    });
                }
            },

            // 登录
            login() {
                uni.reLaunch({
                    url: '../index/index',
                });
            },
        },
    };
</script>

<style>
	.other {
		background: #ffffff;
	}
	.title {
		width: 100%;
		padding-top: 15%;
		padding-left: 5%;
		font-size: 1.4em;
		font-weight: 600;
	}
	.title>text:last-child {
		display: block;
		color: #808080;
		font-size: 0.8em;
		font-weight: 200;
		margin-top: 4.5%;
	}
	.input {
		width: 100%;
		margin-top: 18%;
	}
	.input view {
		height: 4em;
		margin-top: 1.5%;
		margin-left: 5%;
		margin-right: 5%;
		border-bottom: #d1d1d1 1px solid;
	}
	.input input {
		width: 70%;
		color: #000;
		position: relative;
		padding-top: 5%;
		font-size: 1.3em;
		font-weight: 100;
	}
	.input .code {
		display: flex;
	}
	.input .code button {
		width: 40%;
		height: 2.7em;
		line-height: 2.6em;
		margin-top: 2%;
		color: #000;
		font-weight: 100;
		border-radius: 2px;
		border: 1px solid #e6e6e6;
	}
	.login {
		width: 100%;
		padding-top: 12%;
		border: 0;
	}
	.login button {
		width: 90%;
		height: 3em;
		color: white;
		font-weight: 600;
		font-size: 1.2em;
		line-height: 2.8em;
		letter-spacing: 3px;
		background: #7e1e1f;
	}
</style>
