<template>
    <!-- 购物车页面 -->
    <view class="shoppingTrolley">
        <!-- 已登录时 -->
        <view v-if="isLogin" class="login">
            <!-- 权限和编辑按钮 -->
            <view class="top">
                <view>
                    <label @click="selectAllChange()">
                        <radio borderColor="#cccccc" color="#7e1e1f" :checked="isSelectAll" value="r2"></radio>全选
                    </label>
                </view>
                <view>
                    <text>编辑</text>
                </view>
            </view>
            <!-- 购物车卡片 -->
            <view class="card"></view>
        </view>
        <!-- 未登录时 -->
        <view v-else class="notLogin">
            <image src="@/static/images/not-login.jpg" mode="aspectFit"></image>
            <text>请先登录，登录后查看您的购物车商品</text>
            <button>去登录</button>
        </view>
        <!-- 底部导航条 -->
        <bottom-navigation :index="3"></bottom-navigation>
    </view>
</template>

<script>
    import bottomNavigation from '../navigation/bottomNavigation.vue';
    export default {
        components: {
            bottomNavigation,
        },

        data() {
            return {
                // 是否已登录
                isLogin: true,
                // 是否全选
                isSelectAll: false,
                // 购物车信息
                shoppingTrolley: [
                    { name: '意式极简磨砂质感沙发 科技布真皮沙发', size: '单人1050*1050*700', materialQuality: '磨砂布科技布', colour: '先锋XFB538-1', price: 4513, quantity: 1 },
                    { name: '意式极简磨砂质感沙发 科技布真皮沙发', size: '单人1050*1050*700', materialQuality: '磨砂布科技布', colour: '先锋XFB538-1', price: 4513, quantity: 1 },
                    { name: '意式极简磨砂质感沙发 科技布真皮沙发', size: '单人1050*1050*700', materialQuality: '磨砂布科技布', colour: '先锋XFB538-1', price: 4513, quantity: 1 },
                ],
            };
        },

        mounted() {
            // 取消顶部返回首页按钮
            uni.hideHomeButton();
        },

        methods: {
            // 全选改变
            selectAllChange() {
                this.isSelectAll = !this.isSelectAll;
            },
        },
    };
</script>

<style>
	.shoppingTrolley {
		width: 100%;
		height: 1000rpx;
		background: red;
	}
	.shoppingTrolley .notLogin {
		width: 100%;
		height: 700rpx;
		background: white;
		padding-top: 20%;
		line-height: 70rpx;
		text-align: center;
	}
	.notLogin image {
		width: 20%;
		height: 170rpx;
	}
	.notLogin text {
		display: block;
		color: #444444;
		font-weight: 400;
		font-size: 35rpx;
	}
	.notLogin button {
		width: 30%;
		color: white;
		margin-top: 2%;
		font-weight: 600;
		background: #7e1e1f;
	}
	.shoppingTrolley .login {
		width: 100%;
		height: 500rpx;
		background: deepskyblue;
	}
	.login .top {
		width: 100%;
		height: 90rpx;
		background: white;
		display: flex;
	}
	.login .top>view {
		flex: 1;
		padding-left: 5%;
		padding-right: 5%;
		line-height: 90rpx;
		font-size: 35rpx;
	}
	.login .top>view:first-child {
		font-weight: 600;
	}
	.login .top>view:last-child {
		text-align: right;
	}
</style>
