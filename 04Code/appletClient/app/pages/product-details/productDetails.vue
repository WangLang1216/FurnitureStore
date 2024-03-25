<template>
    <!-- 商品详情页面 -->
    <view class="productDetails">
        <!-- 轮播图 -->
        <view class="carousel">
            <uni-swiper-dot
                class="uni-swiper-dot-box" :info="commodity.carouselImages" :current="current" mode="nav" :dotsStyles="dotsStyle"
                field="content" @clickItem="clickItem"
            >
                <swiper class="swiper" autoplay="true" interval="3500" :current="swiperDotIndex" @change="change">
                    <swiper-item v-for="(item, index) in commodity.carouselImages" :key="index">
                        <view class="swiper-item">
                            <image :src="item.image" mode="aspectFit"></image>
                        </view>
                    </swiper-item>
                </swiper>
            </uni-swiper-dot>
        </view>
        <!-- 信息 -->
        <view class="information">
            <view>
                <text>￥ {{ commodity.price }}</text>
                <text>{{ commodity.name }}</text>
            </view>
            <view>
                <view>
                    <text>已售 {{ commodity.sold }}</text>
                </view>
                <view>
                    <text>人气 {{ commodity.heat }}</text>
                </view>
            </view>
            <view>
                <text>{{ commodity.introduce }}</text>
            </view>
            <view @click="$refs.popup.open();">
                <uni-icons type="list" size="25" color="#fef9fb"></uni-icons>
                <text>规格：</text>请选择商品规格
                <uni-icons type="right" size="20" color="#353536"></uni-icons>
            </view>
            <view>
                <view @click="collect()">
                    <uni-icons
                        :type="collectState ? 'star-filled' : 'star'" size="28"
                        :color="collectState ? '#6c2324' : '#6a6a6a'"
                    ></uni-icons>
                    <text>{{ collectState ? '已收藏' : '收藏' }}</text>
                </view>
                <view>
                    <uni-icons type="personadd" size="28"></uni-icons>
                    <text>邀请好友</text>
                </view>
                <view>
                    <text>分享九宫格</text>
                </view>
            </view>
        </view>
        <!-- 商品评价 -->
        <view class="evaluate">
            <view>
                <view>
                    <text>商品评论</text>
                </view>
                <view>
                    <text>全部</text>
                    <uni-icons type="right" size="18"></uni-icons>
                </view>
            </view>
            <view>
                <view v-show="evaluate.length === 0">
                    <text>暂无评论</text>
                </view>
            </view>
        </view>
        <!-- 商品信息-折叠展示 -->
        <view class="commodityInformation">
            <view>
                <text>商品信息</text>
            </view>
            <view>
                <view>
                    <text>商品编号</text>
                    <text>{{ commodity.identifier }}</text>
                </view>
                <view>
                    <text>工厂编号</text>
                    <text>{{ commodity.factoryNumber }}</text>
                </view>
                <view>
                    <text>商品产地</text>
                    <text>{{ commodity.producer }}</text>
                </view>
                <view>
                    <text>填充物</text>
                    <text>{{ commodity.piece }}</text>
                </view>
            </view>
            <view v-show="informationDisplay">
                <view>
                    <text>包件</text>
                    <text>{{ commodity.piece }}</text>
                </view>
                <view>
                    <text>工艺</text>
                    <text>{{ commodity.filler }}</text>
                </view>
                <view>
                    <text>安装方式</text>
                    <text>{{ commodity.installationMethod }}</text>
                </view>
                <view>
                    <text>风格</text>
                    <text>{{ commodity.style }}</text>
                </view>
                <view>
                    <text>空间</text>
                    <text>{{ commodity.space }}</text>
                </view>
                <view>
                    <text>货期</text>
                    <text>{{ commodity.term }}</text>
                </view>
                <view>
                    <text>服务</text>
                    <text>{{ commodity.service }}</text>
                </view>
            </view>
            <view>
                <text @click="informationDisplay = !informationDisplay">{{ informationDisplay ? '收起' : '展开更多' }}</text>
                <uni-icons :type="informationDisplay ? 'up' : 'down'" size="18" color="#999999" @click="informationDisplay = !informationDisplay"></uni-icons>
            </view>
        </view>
        <!-- 商品实拍图 -->
        <view class="realGraph">
            <view>
                <text>商品实拍图</text>
            </view>
            <view>
                <view v-for="(item, index) in commodity.physicalImages" :key="index">
                    <image :src="item.image" mode="aspectFill"></image>
                </view>
            </view>
        </view>
        <!-- 商品详情 -->
        <view class="details">
            <view>
                ——— <text>商品详情</text> ———
            </view>
            <view>
                <view v-for="(item, index) in commodity.detailsImages" :key="index">
                    <image :src="item.image" mode="widthFix"></image>
                </view>
            </view>
        </view>
        <!-- 底部加入购物车 -->
        <view class="joinSelection">
            <view @click="$refs.popup.open();">
                <text>加入购物车</text>
            </view>
            <view @click="$refs.popup.open();">
                <text>立即购买</text>
            </view>
        </view>
        <!-- 弹出层-产品选择 -->
        <uni-popup ref="popup" type="bottom" backgroundColor="white">
            <view class="productSelection">
                <view>
                    <view>
                        <image :src="selectImage()" mode="aspectFit"></image>
                    </view>
                    <view>
                        <text>￥ {{ commodity.price }}</text>
                        <text>已选：{{ selectedName() }}</text>
                    </view>
                    <view>
                        <uni-icons type="closeempty" size="25" color="#909197" @click="$refs.popup.close();"></uni-icons>
                    </view>
                </view>
                <view>
                    <scroll-view :scrollTop="scrollTop" scrollY="true" class="scroll-Y" @scroll="scroll">
                        <view class="scrollOuter">
                            <text>尺寸</text>
                            <view>
                                <view v-for="(item, index) in specs.size" :key="index" :style="sizeStyle(item)" @click="selectSize(item)">
                                    <text>{{ item }}</text>
                                </view>
                            </view>
                        </view>
                        <view class="scrollOuter">
                            <text>颜色</text>
                            <view>
                                <view v-for="(item, index) in specs.colour" :key="index" :style="colourStyle(item)" @click="selectColour(item)">
                                    <text>{{ item }}</text>
                                </view>
                            </view>
                        </view>
                        <view class="scrollOuter">
                            <text>材质</text>
                            <view>
                                <view v-for="(item, index) in specs.materialType" :key="index" :style="materialTypeStyle(item)" @click="selectMaterialType(item)">
                                    <text>{{ item }}</text>
                                </view>
                            </view>
                        </view>
                    </scroll-view>
                </view>
                <view>
                    <view>
                        <text>数量</text>
                    </view>
                    <view>
                        <button @click="quantityChange(false)">-</button>
                        <text>{{ purchaseQuantity }}</text>
                        <button @click="quantityChange(true)">+</button>
                    </view>
                </view>
                <view style="z-index: 99;">
                    <view @click="addShoppingInfo(false)">
                        <text>加入购物车</text>
                    </view>
                    <view @click="addShoppingInfo(true)">
                        <text>立即抢购</text>
                    </view>
                </view>
            </view>
        </uni-popup>
        <!-- 推荐 -->
        <view class="recommend">
            <view>
                <view>
                    <text>看了又看</text>
                </view>
                <view>
                    <text>全部</text>
                    <uni-icons type="right" size="18"></uni-icons>
                </view>
            </view>
            <recommend></recommend>
        </view>
    </view>
</template>

<script>
    import { getProductInfo, getProductSpecs, updateProductHeat } from '@/api/browseData.js';
    import { addLikeWeight, addShopping, getUserCollectSate, updateUserCollectSate } from '@/api/user.js';

    import recommend from '../recommend/recommend.vue';
    export default {
        components: {
            recommend,
        },

        data() {
            return {
                productId: '',
                // 收藏状态
                collectState: false,
                // 商品信息
                commodity: {
                    // id: 1,
                    // name: '现代意式网红北欧客厅转角简约组合直排纳米科技布沙发现代小户型奢华',
                    // price: 6750,
                    // sold: 0,
                    // heat: 1286,
                    // introduce: '1.科技布+高回弹海绵+实木框架 2.沙发图颜色为3D效果图跟实物有色差问题，请以实物颜色为准，请选对应面料下单。 可定制尺寸，具体请咨询客服。',
                    // identifier: 'J140-X04-109-2072#',
                    // factoryNumber: 'J140',
                    // producer: '广东佛山',
                    // materialQuality: '实木框架',
                    // filler: '其他',
                    // piece: '1件',
                    // technology: '其他',
                    // installationMethod: '组装',
                    // style: '现代简约',
                    // space: '客厅',
                    // term: '15天',
                    // service: '包送、包安装',
                    // images: [
                    //     { url: '../../static/images/commodity/carousel1.jpg', content: '' },
                    //     { url: '../../static/images/commodity/carousel2.jpg', content: '' },
                    //     { url: '../../static/images/commodity/carousel3.jpg', content: '' },
                    //     { url: '../../static/images/commodity/carousel4.jpg', content: '' },
                    //     { url: '../../static/images/commodity/carousel5.jpg', content: '' },
                    // ],
                    // physicalImages: [
                    //     '../../static/images/commodity/physical-image1.jpg',
                    //     '../../static/images/commodity/physical-image2.jpg',
                    //     '../../static/images/commodity/physical-image3.jpg',
                    // ],
                    // detailsImages: [
                    //     '../../static/images/commodity/details-1.jpg',
                    //     '../../static/images/commodity/details-2.jpg',
                    //     '../../static/images/commodity/details-3.jpg',
                    //     '../../static/images/commodity/details-4.jpg',
                    //     '../../static/images/commodity/details-5.jpg',
                    //     '../../static/images/commodity/details-6.jpg',
                    //     '../../static/images/commodity/details-7.jpg',
                    //     '../../static/images/commodity/details-8.jpg',
                    //     '../../static/images/commodity/details-9.jpg',
                    //     '../../static/images/commodity/details-10.jpg',
                    //     '../../static/images/commodity/details-11.jpg',
                    //     '../../static/images/commodity/details-12.jpg',
                    //     '../../static/images/commodity/details-13.jpg',
                    //     '../../static/images/commodity/details-14.jpg',
                    //     '../../static/images/commodity/details-15.jpg',
                    //     '../../static/images/commodity/details-16.jpg',
                    //     '../../static/images/commodity/details-17.jpg',
                    //     '../../static/images/commodity/details-18.jpg',
                    //     '../../static/images/commodity/details-19.jpg',
                    //     '../../static/images/commodity/details-20.jpg',
                    //     '../../static/images/commodity/details-21.jpg',
                    //     '../../static/images/commodity/details-22.jpg',
                    //     '../../static/images/commodity/details-23.jpg',
                    //     '../../static/images/commodity/details-24.jpg',
                    //     '../../static/images/commodity/details-25.jpg',
                    //     '../../static/images/commodity/details-26.jpg',
                    //     '../../static/images/commodity/details-27.jpg',
                    // ],
                },
                // 规格信息
                specs: {
                    // sizeImages: [
                    //     '../../static/images/commodity/select1.jpg',
                    //     '../../static/images/commodity/select2.jpg',
                    //     '../../static/images/commodity/select3.jpg',
                    //     '../../static/images/commodity/select4.jpg',
                    // ],
                    // size: [
                    //     '三人位2400*980*850',
                    //     '四人位2800*980*850',
                    //     '转角沙发右贵妃3500*980*1400*850',
                    //     '转角沙发左贵妃3500*980*1400*850',
                    // ],
                    // colour: [
                    //     '下单备注颜色',
                    //     'RX197-4',
                    // ],
                    // materialType: [
                    //     '改科技布',
                    //     '绒布',
                    // ],
                },
                // 选购数量
                purchaseQuantity: 1,
                // 选购产品
                selectPurchase: {
                    size: '', colour: '', materialType: '',
                },
                // 限制条件
                limitations: [
                    { colour: ['下单备注颜色'], materialType: ['改科技布'] },
                    { colour: ['RX197-4'], materialType: ['绒布'] },
                ],
                limitationsColour: [
                    { colour: '下单备注颜色', materialType: ['改科技布'] },
                    { colour: 'RX197-4', materialType: ['绒布'] },
                ],
                limitationsMaterialType: [
                    { materialType: '改科技布', colour: ['下单备注颜色'] },
                    { materialType: '绒布', colour: ['RX197-4'] },
                ],
                // 评论
                evaluate: [],
                // 是否信息展开
                informationDisplay: false,
                // 轮播图点样式
                dotsStyle: {
                    backgroundColor: 'rgba(0, 0, 0, 0.3)',
                    color: '#fff',
                },
                current: 0,
                swiperDotIndex: 0,
                scrollTop: 0,
                old: {
                    scrollTop: 0,
                },
            };
        },

        onLoad(option) {
            this.productId = option.product_id;
            console.log(this.productId);
        },

        mounted() {
            // 查询产品信息
            getProductInfo(this.productId).then((res) => {
                if (res.code === 200) {
                    this.commodity = res.data;
                    // 导航栏标题
                    uni.setNavigationBarTitle({
                        title: this.commodity.name,
                    });
                }
            });
            // 获取产品规格信息
            getProductSpecs(this.productId).then((res) => {
                if (res.code === 200) {
                    this.specs = res.data;
                }
            });
            // 增加热度
            updateProductHeat(this.productId, 10);
            // 查询收藏信息
            if (uni.getStorageSync('accessToken')) {
                getUserCollectSate(this.productId).then((res) => {
                    if (res.code === 200) {
                        this.collectState = res.data;
                    }
                });
                addLikeWeight(this.productId);
            } else {
                this.collectState = false;
            }
        },

        methods: {
            // 收藏
            collect() {
                if (!uni.getStorageSync('accessToken')) {
                    return uni.showModal({
                        icon: 'error',
                        title: '请登录后收藏',
                        showCancel: true,
                        success(res) {
                            if (res.confirm) {
                                setTimeout(() => {
                                    uni.navigateTo({
                                        url: '/pages/user/login',
                                    });
                                }, 1000);
                            }
                        },
                    });
                }
                updateUserCollectSate(this.productId).then((res) => {
                    if (res.code === 200) {
                        this.collectState = !this.collectState;
                    } else {
                        uni.showToast({
                            title: res.msg,
                            duration: 2000,
                        });
                    }
                });
                updateProductHeat(this.productId, 30);
            },

            // 新增购物车信息
            addShoppingInfo(type) {
                if (!uni.getStorageSync('accessToken')) {
                    return uni.showModal({
                        icon: 'error',
                        title: '请先进行登录',
                        showCancel: true,
                        success(res) {
                            if (res.confirm) {
                                setTimeout(() => {
                                    uni.navigateTo({
                                        url: '/pages/user/login',
                                    });
                                }, 1000);
                            }
                        },
                    });
                }
                if (this.selectPurchase.size === '') {
                    return uni.showToast({
                        icon: 'none',
                        title: '请选择尺寸',
                        duration: 2000,
                    });
                }
                if (this.selectPurchase.colour === '') {
                    return uni.showToast({
                        icon: 'none',
                        title: '请选择颜色',
                        duration: 2000,
                    });
                }
                if (this.selectPurchase.materialType === '') {
                    return uni.showToast({
                        icon: 'none',
                        title: '请选择材质',
                        duration: 2000,
                    });
                }
                const shopping = {};
                shopping.productId = this.productId;
                shopping.productName = this.commodity.name;
                shopping.productSize = this.selectPurchase.size;
                shopping.productColour = this.selectPurchase.colour;
                shopping.materialType = this.selectPurchase.materialType;
                shopping.image = this.specs.sizeImages[this.specs.size.indexOf(this.selectPurchase.size)];
                shopping.quantity = this.purchaseQuantity;
                shopping.price = this.commodity.price;
                addShopping(shopping).then((res) => {
                    if (res.code === 200) {
                        uni.showToast({
                            icon: 'success',
                            title: '加入成功',
                            duration: 3000,
                        });
                    } else {
                        uni.showToast({
                            title: '加入失败',
                            duration: 2000,
                        });
                    }
                    this.$refs.popup.close();
                });
                updateProductHeat(this.productId, 50);
                if (type) {
                    return setTimeout(() => {
                        uni.reLaunch({
                            url: '/pages/shopping-trolley/shoppingTrolley',
                        });
                    }, 1000);
                }
            },

            // 产品选择图片
            selectImage() {
                if (this.selectPurchase.size !== '') {
                    const index = this.specs.size.indexOf(this.selectPurchase.size);
                    return this.specs.sizeImages[index];
                } else {
                    return this.commodity.carouselImages[0].image;
                }
            },

            // 选择尺寸
            selectSize(size) {
                this.selectPurchase.size = this.selectPurchase.size === size ? '' : size;
            },

            // 选择颜色
            selectColour(colour) {
                this.selectPurchase.colour = this.selectPurchase.colour === colour ? '' : colour;
            },

            // 选择材质
            selectMaterialType(materialType) {
                this.selectPurchase.materialType = this.selectPurchase.materialType === materialType ? '' : materialType;
            },

            // 已选产品展示
            selectedName() {
                let size = '';
                let colour = '';
                if (this.selectPurchase.size !== '') {
                    size = this.selectPurchase.size;
                    if (this.selectPurchase.colour !== '' || this.selectPurchase.materialType != '') {
                        size += '、';
                    }
                }
                if (this.selectPurchase.colour !== '') {
                    colour = this.selectPurchase.colour;
                    if (this.selectPurchase.materialType !== '') {
                        colour += '、';
                    }
                }
                return size + colour + this.selectPurchase.materialType;
            },

            // 改变选购数量
            quantityChange(operationType) {
                let quantity = this.purchaseQuantity;
                if (operationType) {
                    if (++quantity > 5) {
                        return;
                    }
                    // 增加
                    this.purchaseQuantity++;
                } else {
                    if (--quantity < 1) {
                        return;
                    }
                    // 减少
                    this.purchaseQuantity--;
                }
            },

            // 改变尺寸的样式
            sizeStyle(size) {
                return this.selectStyle(this.selectPurchase.size, size);
            },

            // 改变颜色的样式
            colourStyle(colour) {
                return this.selectStyle(this.selectPurchase.colour, colour);
            },

            // 改变材质的样式
            materialTypeStyle(materialType) {
                return this.selectStyle(this.selectPurchase.materialType, materialType);
            },

            // 改变选择样式
            selectStyle(original, value) {
                if (original === value) {
                    return { border: '1rpx #fcbb06 solid', background: '#feeab4' };
                }
                return { border: '1rpx #f8f8f8 solid', background: '#f8f8f8' };
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
        },
    };
</script>

<style>
	@import "./productDetails.css";
</style>
