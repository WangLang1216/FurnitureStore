<template>
    <!-- 顶部搜索框 -->
    <view class="search-view">
        <view class="search-box">
            <view class="search" @tap="toLogin">
                <view class="icon">
                    <image src="/static/icon/search.png"></image>
                </view>
                <view class="text">搜索您喜欢的商品</view>
            </view>
        </view>
    </view>
</template>

<script>
    export default {

        data() {
            return {

            };
        },

        methods: {
            toLogin() {
                uni.navigateTo({
                    url: '../user/login',
                });
            },
        },
    };
</script>

<style>
	/* 搜索框 */
	.search-view {
		width: 100%;
		position: fixed;
		top: 0;
		z-index: 99;
		padding: 1% 0 1% 2.5%;
		background: #ffffff;
	}
	.search-box {
		width: 95%;
	}
	.search-box .search {
	  height: 80rpx;
	  background: #f8f8f8;
	  border-radius: 60rpx;
	  display: flex;
	  align-items: center;
	}
	.search-box .search .icon {
	  width: 30rpx;
	  height: 30rpx;
	  font-size: 0;
	  margin-left: 20rpx;
	}
	.search-box .search .icon image {
		width: 100%;
		height: 100%;
	}
	.search-box .search .text {
	  color: #909399;
		font-size: 1.2em;
		font-weight: 100;
		line-height: 80rpx;
	  margin-left: 15rpx;
	}
</style>
