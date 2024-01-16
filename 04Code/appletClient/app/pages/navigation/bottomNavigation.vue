<template>
    <!-- 底部导航栏 -->
    <view class="bottomNavigation">
        <view class="content">
            <view @click="changeColor(1)">
                <uni-icons type="home-filled" :color="iconInitColor1" size="28"></uni-icons>
                <text :class="{ textInitColor: textInitColorBool1, textChangeColor: textChangeColorBool1 }">首页</text>
            </view>
            <view @click="changeColor(2)">
                <uni-icons type="list" :color="iconInitColor2" size="28"></uni-icons>
                <text :class="{ textInitColor: textInitColorBool2, textChangeColor: textChangeColorBool2 }">分类</text>
            </view>
            <view @click="changeColor(3)">
                <uni-icons type="cart-filled" :color="iconInitColor3" size="28"></uni-icons>
                <text :class="{ textInitColor: textInitColorBool3, textChangeColor: textChangeColorBool3 }">购物车</text>
            </view>
            <view @click="changeColor(4)">
                <uni-icons type="person-filled" :color="iconInitColor4" size="28"></uni-icons>
                <text :class="{ textInitColor: textInitColorBool4, textChangeColor: textChangeColorBool4 }">我的</text>
            </view>
        </view>
    </view>
</template>

<script>
    export default {
        data() {
            return {
                iconInitColor1: '#6d2323',
                iconInitColor2: '#7f7d7e',
                iconInitColor3: '#7f7d7e',
                iconInitColor4: '#7f7d7e',
                textInitColorBool1: false,
                textChangeColorBool1: true,
                textInitColorBool2: true,
                textChangeColorBool2: false,
                textInitColorBool3: true,
                textChangeColorBool3: false,
                textInitColorBool4: true,
                textChangeColorBool4: false,
                textInitColor: 'textInitColor',
                textChangeColor: 'textChangeColor',
            };
        },

        methods: {
            changeColor(index) {
                if (index == 1) {
                    // 首页
                    this.iconInitColor1 = '#6d2323';
                    this.iconInitColor2 = this.iconInitColor3 = this.iconInitColor4 = '#7f7d7e';
                    this.textInitColorBool1 = this.textChangeColorBool2 =
                        this.textChangeColorBool3 = this.textChangeColorBool4 = false;
                    this.textChangeColorBool1 = this.textInitColorBool2 =
                        this.textInitColorBool3 = this.textInitColorBool4 = true;
                    // 跳转首页
                    uni.reLaunch({
                        url: '../index/index',
                    });
                } else if (index == 2) {
                    // 分类
                    this.iconInitColor2 = '#6d2323';
                    this.iconInitColor1 = this.iconInitColor3 = this.iconInitColor4 = '#7f7d7e';
                    this.textInitColorBool2 = this.textChangeColorBool1 =
                        this.textChangeColorBool3 = this.textChangeColorBool4 = false;
                    this.textChangeColorBool2 = this.textInitColorBool1 =
                        this.textInitColorBool3 = this.textInitColorBool4 = true;
                    // 跳转分类页面
                    uni.reLaunch({
                        url: '../classification/classification',
                    });
                } else if (index == 3) {
                    // 购物车
                    this.iconInitColor3 = '#6d2323';
                    this.iconInitColor1 = this.iconInitColor2 = this.iconInitColor4 = '#7f7d7e';
                    this.textInitColorBool3 = this.textChangeColorBool2 =
                        this.textChangeColorBool1 = this.textChangeColorBool4 = false;
                    this.textChangeColorBool3 = this.textInitColorBool2 =
                        this.textInitColorBool1 = this.textInitColorBool4 = true;
                } else {
                    // 我的;
                    this.iconInitColor4 = '#6d2323';
                    this.iconInitColor1 = this.iconInitColor2 = this.iconInitColor3 = '#7f7d7e';
                    this.textInitColorBool4 = this.textChangeColorBool2 =
                        this.textChangeColorBool3 = this.textChangeColorBool1 = false;
                    this.textChangeColorBool4 = this.textInitColorBool2 =
                        this.textInitColorBool3 = this.textInitColorBool1 = true;
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
