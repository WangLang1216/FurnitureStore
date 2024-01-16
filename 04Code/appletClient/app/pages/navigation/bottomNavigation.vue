<template>
    <!-- 底部导航栏 -->
    <view class="bottomNavigation">
        <view class="content">
            <view @click="route(1)">
                <uni-icons type="home-filled" :color="colorSetOf(index == 1)" size="28"></uni-icons>
                <text :style="{ color: colorSetOf(index == 1) }">首页</text>
            </view>
            <view @click="route(2)">
                <uni-icons type="list" :color="colorSetOf(index == 2)" size="28"></uni-icons>
                <text :style="{ color: colorSetOf(index == 2) }">分类</text>
            </view>
            <view @click="route(3)">
                <uni-icons type="cart-filled" :color="colorSetOf(index == 3)" size="28"></uni-icons>
                <text :style="{ color: colorSetOf(index == 3) }">购物车</text>
            </view>
            <view @click="route(4)">
                <uni-icons type="person-filled" :color="colorSetOf(index == 4)" size="28"></uni-icons>
                <text :style="{ color: colorSetOf(index == 4) }">我的</text>
            </view>
        </view>
    </view>
</template>

<script>
    export default {
        props: {
            index: {
                type: Number,
                require: true,
                default: 1,
            },
        },

        data() {
            return {
                initColor: '#7f7d7e',
                changeColor: '#6d2323',
            };
        },

        methods: {
            colorSetOf(condition) {
                return condition ? this.changeColor : this.initColor;
            },

            // 路由跳转
            route(index) {
                if (index == 1) {
                    // 跳转首页
                    uni.reLaunch({
                        url: '../index/index',
                    });
                } else if (index == 2) {
                    // 跳转分类页面
                    uni.reLaunch({
                        url: '../classification/classification',
                    });
                } else if (index == 3) {
                    // 跳转购物车页面
                    uni.reLaunch({
                        url: '../shopping-trolley/shoppingTrolley',
                    });
                } else {
                }
            },
        },
    };
</script>

<style>
	[v-cloak] {
		display: none;
	}
	.textInitColor {
		color: #7f7d7e;
	}
	.textChangeColor {
		color: #6d2323;
	}

	.bottomNavigation {
		width: 100%;
		height: 100rpx;
		background-color: white;
		position: fixed;
		bottom: 0rpx;
		border-top: 1px solid gainsboro;
	}
	.content {
		display: flex;
	}
	.content>view {
		flex: 1;
		text-align: center;
	}
	.content>view uni-icons {
		display: block;
		margin-top: 1%;
	}
	.content>view text {
		font-size: 25rpx;
		olor: #7f7d7e;
	}
	.content>view:nth-child(1) {
		color: #6d2323;
	}
</style>
