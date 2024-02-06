<template>
    <!-- 首页 -->
    <view class="index">
        <!-- 搜索框 -->
        <search></search>
        <view class="title">
            <text>巧夺天工，风华绝伦，松柏家具</text>
            <view></view>
        </view>
        <!-- 轮播图 -->
        <view class="carousel">
            <uni-swiper-dot class="uni-swiper-dot-box" mode="round" :info="carousel" :current="current" :dotsStyles="dotsStyles" field="content" @clickItem="clickItem">
                <swiper class="swiper" autoplay="true" interval="2500" :current="swiperDotIndex" @change="change">
                    <swiper-item v-for="(item, index) in carousel" :key="index">
                        <image :src="item" mode="aspectFill"></image>
                    </swiper-item>
                </swiper>
            </uni-swiper-dot>
        </view>
        <!-- 导航 -->
        <view class="nav">
            <scroll-view class="scroll" scrollTop="100" scrollX="true" scrollLeft="0" @scroll="scroll">
                <view v-for="(item, index) in nav" :key="index" class="nav-item uni-column uni-flex">
                    <view class="flex-item flex-item-V">
                        <image :src="item.icon" mode="heightFix"></image>
                    </view>
                    <view class="flex-item flex-item-V">
                        <text>{{ item.title }}</text>
                    </view>
                </view>
            </scroll-view>
            <view></view>
        </view>
        <!-- 数据 -->
        <view class="data">
            <view v-for="(item, index) in data" :key="index" class="warp">
                <view class="title">
                    <view><text>{{ item.title }}</text></view>
                    <view>
                        <view class="uni-flex uni-row">
                            <text class="flex-item">全部</text>
                            <uni-icons class="flex-item test" type="forward" size="20"></uni-icons>
                        </view>
                    </view>
                </view>
                <uni-grid :column="2" :showBorder="false" :square="false">
                    <uni-grid-item v-for="(wares, key) in item.commodity" :key="key" :style="getStyle(key)" :index="key">
                        <view class="info" @click="detail(wares.id)">
                            <view>
                                <image :src="wares.image" mode="heightFix"></image>
                            </view>
                            <view>
                                <text>{{ wares.name }}</text>
                            </view>
                            <view>
                                <view>
                                    <text style="font-weight: 100;">￥</text>
                                    <text style="font-weight: 700;font-size: 1em;">{{ wares.price }}</text>
                                </view>
                                <view>
                                    <text>已售</text>
                                    <text>{{ wares.sold }}</text>
                                </view>
                            </view>
                        </view>
                    </uni-grid-item>
                </uni-grid>
            </view>
        </view>
        <!-- 底部导航条 -->
        <bottom-navigation :index="1"></bottom-navigation>
    </view>
</template>

<script>
    import search from '../search/search.vue';
    import bottomNavigation from '../navigation/bottomNavigation.vue';

    export default {

        components: {
            search,
            bottomNavigation,
        },

        data() {
            return {
                // 轮播
                carousel: [
                    '/static/images/index_carousel_1.jpg',
                    '/static/images/loginBackground.jpg',
                ],
                current: 0,
                swiperDotIndex: 0,
                dotsStyles: {
                    backgroundColor: 'rgba(83, 200, 249,0.3)',
                    border: '1px rgba(83, 200, 249,0.3) solid',
                    color: '#fff',
                    selectedBackgroundColor: '#f8f8f8',
                    selectedBorder: '1px #f8f8f8 solid',
                },
                // 导航
                nav: [
                    { title: '沙发', icon: '/static/images/index_nav_1.jpg' },
                    { title: '休闲椅', icon: '/static/images/index_nav_2.jpg' },
                    { title: '茶几', icon: '/static/images/index_nav_3.jpg' },
                    { title: '电视柜', icon: '/static/images/index_nav_4.jpg' },
                    { title: '餐桌', icon: '/static/images/index_nav_5.jpg' },
                ],
                old: {
                    scrollTop: 0,
                },
                // 数据
                data: [
                    {
                        title: '松柏爆款沙发',
                        commodity: [
                            { id: 1, name: '意式极简磨砂质感沙发科技布真皮沙发', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                            { id: 1, name: '意式极简磨砂质感沙发科技布真皮沙发', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                            { id: 1, name: '意式极简磨砂质感沙发科技布真皮沙发', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                            { id: 1, name: '意式极简磨砂质感沙发科技布真皮沙发', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                            { id: 1, name: '意式极简磨砂质感沙发科技布真皮沙发', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                            { id: 1, name: '意式极简磨砂质感沙发科技布真皮沙发', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                            { id: 1, name: '意式极简磨砂质感沙发科技布真皮沙发', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                        ],
                    },
                    {
                        title: '松柏爆推大床',
                        commodity: [
                            { id: 1, name: '意式极简磨砂质感沙发科技布真皮沙发', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                            { id: 1, name: '意式极简磨砂质感沙发科技布真皮沙发', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                            { id: 1, name: '意式极简磨砂质感沙发科技布真皮沙发', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                            { id: 1, name: '意式极简磨砂质感沙发科技布真皮沙发', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                            { id: 1, name: '意式极简磨砂质感沙发科技布真皮沙发', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                            { id: 1, name: '意式极简磨砂质感沙发科技布真皮沙发', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                        ],
                    },
                    {
                        title: '爆款餐桌椅',
                        commodity: [
                            { id: 1, name: '意式极简磨砂质感沙发科技布真皮沙发', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                            { id: 1, name: '意式极简磨砂质感沙发科技布真皮沙发', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                            { id: 1, name: '意式极简磨砂质感沙发科技布真皮沙发', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                            { id: 1, name: '意式极简磨砂质感沙发科技布真皮沙发', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                        ],
                    },
                ],
            };
        },

        methods: {
            // 查看商品详情
            detail(id) {
                uni.navigateTo({
                    url: '../product-details/productDetails',
                });
            },

            change(e) {
                this.current = e.detail.current;
            },

            clickItem(e) {
                this.swiperDotIndex = e;
            },

            scroll(e) {
                this.old.scrollTop = e.detail.scrollTop;
            },

            getStyle(e) {
                return ((e + 1) % 2) == 0 ? { textAlign: 'right' } : { textAlign: 'left' };
            },
        },
    };
</script>

<style>
	@import "./index.css";
</style>
