<template>
    <!-- 分类页面 -->
    <view class="classification">
        <!-- 搜索框 -->
        <search class="search"></search>
        <view class="imagesTop">
            <image mode="aspectFit" src="../../static/images/classification/top.jpg"></image>
        </view>
        <!-- 导航 -->
        <view class="nav">
            <scroll-view class="scroll" scrollTop="100" scrollX="true" scrollLeft="0" @scroll="scroll">
                <view v-for="(item, index) in nav" :key="index" class="nav-item uni-column uni-flex" @click="searchFor('space', item.name)">
                    <view class="flex-item flex-item-V">
                        <image :src="item.image" :mode="item.mode"></image>
                    </view>
                    <view class="flex-item flex-item-V">
                        <text>{{ item.name }}</text>
                    </view>
                </view>
            </scroll-view>
        </view>
        <view class="segmentation"></view>
        <!-- 分类 -->
        <view class="type">
            <view class="left">
                <text>品类</text>
            </view>
            <view class="right">
                <uni-section title="品类" type="line" padding>
                    <uni-grid :column="3" :showBorder="false" highlight="false">
                        <uni-grid-item v-for="(item, index) in category" :key="index" class="grid-item">
                            <view class="grid-item-box" @click="searchFor('category', item.name)">
                                <image :src="item.image" mode="widthFix"></image>
                                <text class="text">{{ item.name }}</text>
                            </view>
                        </uni-grid-item>
                    </uni-grid>
                </uni-section>
            </view>
        </view>
        <!-- 底部导航条 -->
        <bottom-navigation :index="2"></bottom-navigation>
    </view>
</template>

<script>
    import { getCategorySpaceInfo } from '@/api/browseData.js';

    import search from '../search/search.vue';
    import bottomNavigation from '../navigation/bottomNavigation.vue';

    export default {
        components: {
            search,
            bottomNavigation,
        },

        data() {
            return {
                // 导航
                nav: [
                    // { title: '客厅', mode: 'widthFix', icon: '/static/images/classification/nav1.jpg' },
                    // { title: '餐厅', mode: 'widthFix', icon: '/static/images/classification/nav2.jpg' },
                    // { title: '卧室', mode: 'widthFix', icon: '/static/images/classification/nav3.jpg' },
                    // { title: '书房', mode: 'widthFix', icon: '/static/images/classification/nav4.jpg' },
                    // { title: '饰品', mode: 'widthFix', icon: '/static/images/classification/nav5.jpg' },
                    // { title: '厨房', mode: 'aspectFit', icon: '/static/images/classification/nav6.jpg' },
                    // { title: '阳台', mode: 'aspectFit', icon: '/static/images/classification/nav7.jpg' },
                ],
                // 分类数据
                category: [
                    // { name: '沙发', image: '/static/images/classification/sofa.jpg' },
                    // { name: '茶几', image: '/static/images/classification/tea-table.jpg' },
                    // { name: '电视柜', image: '/static/images/classification/TVcabinet.jpg' },
                    // { name: '鞋柜', image: '/static/images/classification/shoe-cabinet.jpg' },
                    // { name: '餐椅', image: '/static/images/classification/dining-chair.jpg' },
                    // { name: '餐边柜', image: '/static/images/classification/meal-side cabinet.jpg' },
                    // { name: '床', image: '/static/images/classification/bed.jpg' },
                    // { name: '床头柜', image: '/static/images/classification/bedside-table.jpg' },
                    // { name: '床垫', image: '/static/images/classification/mattress.jpg' },
                    // { name: '餐桌', image: '/static/images/classification/board.jpg' },
                    // { name: '休闲椅', image: '/static/images/classification/leisure-chairs.jpg' },
                ],
                old: {
                    scrollTop: 0,
                },
            };
        },

        mounted() {
            // 取消顶部返回首页按钮
            uni.hideHomeButton();
            // 获取品类空间信息
            getCategorySpaceInfo().then((res) => {
                if (res.code === 200) {
                    let space = [];
                    let categor = [];
                    res.data.forEach((element) => {
                        element.type === 'category' ? categor = element.spaceInfo : space = element.spaceInfo;
                    });
                    const nav = [];
                    space.forEach((element) => {
                        element.name !== '厨房' && element.name !== '阳台' ? element.mode = 'widthFix' : element.mode = 'aspectFit';
                        nav.push(element);
                    });
                    this.nav = nav;
                    this.category = categor;
                }
            });
        },

        methods: {
            // 前往搜索
            searchFor(type, value) {
                return uni.navigateTo({
                    url: `/pages/search/search-index?type=${type}&value=${value}`,
                });
            },

            scroll(e) {
                this.old.scrollTop = e.detail.scrollTop;
            },
        },
    };
</script>

<style>
	.classification {
		width: 100%;
		/* background: #f8f8f8; */
		background: deeppink;
	}
	.classification .search {
		position: fixed;
		z-index: 99;
		top: 0;
	}
	.classification .imagesTop {
		width: 100%;
		position: fixed;
		z-index: 99;
		top: 7%;
		background: white;
	}
	.classification>.imagesTop image {
		width: 100%;
		height: 120rpx;
	}
	.classification .nav {
		width: 100%;
		position: fixed;
		top: 16%;
		z-index: 99;
		background: white;
	}
	.nav .scroll {
		width: 100%;
		padding-bottom: 3%;
		white-space: nowrap;
	}
	.nav .nav-item {
		display: inline-block;
		width: 20%;
		text-align: center;
		font-weight: 500;
	}
	.nav .nav-item image {
		width: 90%;
	}
	.nav .nav-item>view:last-child {
		margin-top: -22%;
	}
	.nav .nav-item:nth-child(6) image, .nav .nav-item:last-child image {
		height: 130rpx;
	}
	.classification .segmentation {
		width: 100%;
		height: 50rpx;
		background: #f8f8f8;
		position: fixed;
		z-index: 98;
		top: 28%;
	}
	.classification .type {
		width: 100%;
		position: absolute;
		top: 30%;
		background: #f8f8f8;
	}
	.type .left {
		position: fixed;
		background: #f8f8f8;
	}
	.type .left text {
		display: block;
		width: 186%;
		height: 100rpx;
		font-size: 35rpx;
		font-weight: 700;
		line-height: 118rpx;
		padding-left: 5%;
		border-left: 8rpx solid #7e1e1f;
		background: white;
	}
	.type .right {
		margin-left: 20%;
		padding-bottom: 15%;
		background: white;
	}
	.type .right .grid-item {
		height: 250rpx;
	}
	.type .right .grid-item-box {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}
	.type .right .grid-item-box image {
		width: 90%;
	}
</style>
