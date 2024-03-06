<template>
    <!-- 商品列表 -->
    <view class="searchIndex">
        <!-- 搜索 -->
        <view class="search">
            <view>
                <uni-easyinput v-model="searchContent" :styles="styles" confirmType="search" placeholderStyle="font-size:33rpx" prefixIcon="search" placeholder="请输入关键字搜索" @change="enterSearch()"></uni-easyinput>
            </view>
            <view @click="enterSearch()">
                <text>搜索</text>
            </view>
        </view>
        <!-- 筛选栏 -->
        <view class="screen">
            <view>
                <view @click="filterShow('first')">
                    <text :style="firstScreenContent ? { color: 'rgb(126, 30, 31)' } : { color: 'rgb(153, 153, 153)' }">排序</text>
                    <uni-icons :type="firstScreenContent ? 'up' : 'down'" :color="firstScreenContent ? '#7e1e1f' : '#999999'" size="20"></uni-icons>
                </view>
                <view @click="filterShow('second')">
                    <text :style="secondScreenContent ? { color: 'rgb(126, 30, 31)' } : { color: 'rgb(153, 153, 153)' }">风格</text>
                    <uni-icons :type="secondScreenContent ? 'up' : 'down'" :color="secondScreenContent ? '#7e1e1f' : '#999999'" size="20"></uni-icons>
                </view>
                <view @click="filterShow('third')">
                    <text :style="thirdScreenContent ? { color: 'rgb(126, 30, 31)' } : { color: 'black' }">筛选</text>
                    <uni-icons :type="thirdScreenContent ? 'up' : 'down'" :color="thirdScreenContent ? '#7e1e1f' : 'black'" size="20"></uni-icons>
                </view>
            </view>
            <view>
                <uni-icons :type="cardDisplay ? 'bars' : 'list'" size="30" @click="cardDisplay = !cardDisplay"></uni-icons>
            </view>
        </view>
        <!-- 第一个筛选内容 -->
        <view v-show="firstScreenContent" class="firstAndSecondScreenContent">
            <view @click="firstContentInfo('综合', 'productInfo.score')">
                <view>
                    <text>综合</text>
                </view>
                <view>
                    <uni-icons v-show="firstContent === '综合'" type="checkbox-filled" size="30" color="#7e1e1f"></uni-icons>
                </view>
            </view>
            <view @click="firstContentInfo('上新', 'create_time')">
                <view>
                    <text>上新</text>
                </view>
                <view>
                    <uni-icons v-show="firstContent === '上新'" type="checkbox-filled" size="30" color="#7e1e1f"></uni-icons>
                </view>
            </view>
            <view @click="firstContentInfo('销量', 'productInfo.sold')">
                <view>
                    <text>销量</text>
                </view>
                <view>
                    <uni-icons v-show="firstContent === '销量'" type="checkbox-filled" size="30" color="#7e1e1f"></uni-icons>
                </view>
            </view>
            <view @click="firstContentInfo('人气', 'productInfo.heat')">
                <view>
                    <text>人气</text>
                </view>
                <view>
                    <uni-icons v-show="firstContent === '人气'" type="checkbox-filled" size="30" color="#7e1e1f"></uni-icons>
                </view>
            </view>
            <view class="firstScreenContentSpecial">
                <view>
                    <text>价格</text>
                </view>
                <view>
                    <button @click="priceProduct(1)">由高到低</button>
                    <button @click="priceProduct(0)">由低到高</button>
                </view>
            </view>
        </view>
        <!-- 第二个筛选内容 -->
        <view v-show="secondScreenContent" class="secondScreenContent">
            <scroll-view :scrollTop="scrollTop" scrollY="true" class="scroll-Y1" @scroll="scroll">
                <view class="firstAndSecondScreenContent">
                    <view v-for="(item, index) in productStyle" :key="index" @click="secondContent = secondContent === item ? '' : item">
                        <view>
                            <text>{{ item }}</text>
                        </view>
                        <view v-show="secondContent === item">
                            <uni-icons type="checkbox-filled" size="30" color="#7e1e1f"></uni-icons>
                        </view>
                    </view>
                </view>
            </scroll-view>
            <view>
                <button @click="secondContent = ''">重置</button>
                <button @click="secondContentInfo(true)">确定</button>
            </view>
        </view>
        <!-- 第三个筛选内容 -->
        <view v-show="thirdScreenContent" class="thirdScreenContent">
            <scroll-view :scrollTop="scrollTop" scrollY="true" class="scroll-Y2" @scroll="scroll">
                <view class="thirdCategory">
                    <view>
                        <text>品类</text>
                    </view>
                    <view>
                        <view v-for="(item, index) in category" :key="index" @click="thirdContent = thirdContent === item ? '' : item;">
                            <text :style="thirdContent === item ? { color: 'black' } : { color: 'rgb(117, 117, 117)' }">{{ item }}</text>
                        </view>
                    </view>
                </view>
                <view class="thirdCategory">
                    <view>
                        <text>空间</text>
                    </view>
                    <view>
                        <view v-for="(item, index) in space" :key="index" @click="thirdContent = thirdContent === item ? '' : item;">
                            <text :style="thirdContent === item ? { color: 'black' } : { color: 'rgb(117, 117, 117)' }">{{ item }}</text>
                        </view>
                    </view>
                </view>
            </scroll-view>
            <view>
                <button @click="thirdContent = ''">重置</button>
                <button @click="secondContentInfo(false)">确定</button>
            </view>
        </view>
        <!-- 显示产品 -->
        <view class="content" :style="maskStyle()">
            <view v-for="(item, index) in product" :key="index" :style="cardDisplay ? { width: '91%' } : { width: '45.5%' }" @click="productInfo(item.productId)">
                <view>
                    <image :src="item.image" mode="widthFix"></image>
                </view>
                <view>
                    <text>{{ item.name }}</text>
                </view>
                <view>
                    ￥
                    <text>{{ item.price }}</text>
                    <text>已售 {{ item.sold }}</text>
                </view>
            </view>
        </view>
    </view>
</template>

<script>
    import { getScreenProduct } from '@/api/browseData.js';

    export default {
        data() {
            return {
                // 搜索内容
                searchContent: '',
                productType: '',
                productValue: '',
                // 筛选内容123
                firstContent: '',
                secondContent: '',
                thirdContent: '',
                // 第一/二/三个筛选内容
                firstScreenContent: false,
                secondScreenContent: false,
                thirdScreenContent: false,
                // 风格
                productStyle: ['轻奢主义', '现代极简', '自然北欧', '现代中式', '精品实木', '经典美式', '奢华欧法', '软装配饰', '佗寂风/中古风', '轻奢风'],
                // 第三个筛选内容 —— 品类
                category: ['沙发', '茶几', '电视柜', '鞋柜', '餐椅', '餐边柜', '床', '床头柜', '床垫', '餐桌', '休闲椅'],
                // 第三个筛选内容 —— 空间
                space: ['客厅', '餐厅', '卧室', '书房', '饰品', '厨房', '阳台'],
                // 产品
                product: [
                    // { name: '意式极简磨砂质感沙发科技布真皮沙发', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                    // { name: '凯瑟琳', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                    // { name: '客厅现代简约真皮沙发极简头层牛皮大象耳朵皮艺沙发', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                    // { name: '真皮沙发 头层牛皮沙发 客厅简约现代沙发 转角沙发', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                ],
                // 卡片显示
                cardDisplay: false,
                styles: {
                    borderColor: '#f8f8f8',
                },
                scrollTop: 0,
                old: {
                    scrollTop: 0,
                },
            };
        },

        mounted() {
            if (this.searchContent === '' && this.productType === '') {
                // 默认搜索全部
                const screenProductVO = {};
                screenProductVO.name = '';
                screenProductVO.field = null;
                screenProductVO.value = null;
                screenProductVO.sortField = null;
                screenProductVO.sort = null;
                getScreenProduct(screenProductVO).then((res) => {
                    this.product = res.data;
                });
            }
        },

        onLoad(optioon) {
            // 搜索内容
            this.searchContent = optioon.searchContent;
            if (this.searchContent !== '') {
                const screenProductVO = {};
                screenProductVO.name = this.searchContent;
                screenProductVO.field = null;
                screenProductVO.value = null;
                screenProductVO.sortField = null;
                screenProductVO.sort = null;
                getScreenProduct(screenProductVO).then((res) => {
                    this.product = res.data;
                });
            }
            this.productType = optioon.type;
            this.productValue = optioon.value;
            if (this.productType !== '') {
                const screenProductVO = {};
                screenProductVO.name = null;
                screenProductVO.field = this.productType;
                screenProductVO.value = this.productValue;
                screenProductVO.sortField = null;
                screenProductVO.sort = null;
                getScreenProduct(screenProductVO).then((res) => {
                    this.product = res.data;
                });
            }
        },

        methods: {
            // 回车搜索
            enterSearch() {
                const screenProductVO = {};
                screenProductVO.name = this.searchContent;
                screenProductVO.field = null;
                screenProductVO.value = null;
                screenProductVO.sortField = null;
                screenProductVO.sort = null;
                getScreenProduct(screenProductVO).then((res) => {
                    this.product = res.data;
                });
            },

            // 查看产品详情
            productInfo(productId) {
                uni.navigateTo({
                    url: `../product-details/productDetails?product_id=${productId}`,
                });
            },

            // 第一个筛选
            firstContentInfo(item, value) {
                if (this.firstContent === item) {
                    this.firstContent = '';
                    screenProductVO.name = this.searchContent;
                    screenProductVO.field = null;
                    screenProductVO.value = null;
                    screenProductVO.sortField = null;
                    screenProductVO.sort = null;
                    getScreenProduct(screenProductVO).then((res) => {
                        this.product = res.data;
                    });
                } else {
                    this.firstContent = item;
                    const screenProductVO = {};
                    screenProductVO.name = this.searchContent;
                    screenProductVO.field = null;
                    screenProductVO.value = null;
                    screenProductVO.sortField = value;
                    screenProductVO.sort = 0;
                    getScreenProduct(screenProductVO).then((res) => {
                        this.product = res.data;
                    });
                }
                this.firstScreenContent = this.secondContent = this.thirdContent = false;
            },

            // 价格筛选
            priceProduct(sort) {
                const screenProductVO = {};
                screenProductVO.name = this.searchContent;
                screenProductVO.field = null;
                screenProductVO.value = null;
                screenProductVO.sortField = 'productInfo.price';
                screenProductVO.sort = sort;
                getScreenProduct(screenProductVO).then((res) => {
                    this.product = res.data;
                });
                this.firstScreenContent = this.firstContent = this.secondContent = this.thirdContent = false;
            },

            // 第二三个筛选内容
            secondContentInfo(type) {
                const screenProductVO = {};
                screenProductVO.name = this.searchContent == null ? '' : this.searchContent;
                screenProductVO.sort = 0;
                if (type) {
                    screenProductVO.field = 'style';
                    screenProductVO.value = this.secondContent;
                } else {
                    let isOK = false;
                    this.category.forEach((element) => {
                        if (this.thirdContent === element) {
                            isOK = true;
                        }
                    });
                    screenProductVO.field = isOK ? 'category' : 'space';
                    screenProductVO.value = this.thirdContent;
                }
                screenProductVO.sortField = 'productInf.heat';
                getScreenProduct(screenProductVO).then((res) => {
                    this.product = res.data;
                });
                this.secondScreenContent = this.thirdScreenContent = this.firstContent = false;
            },

            // 筛选显示
            filterShow(order) {
                if (order === 'first') {
                    this.secondScreenContent = this.thirdScreenContent = false;
                    this.firstScreenContent = !this.firstScreenContent;
                } else if (order === 'second') {
                    this.firstScreenContent = this.thirdScreenContent = false;
                    this.secondScreenContent = !this.secondScreenContent;
                } else {
                    this.firstScreenContent = this.secondScreenContent = false;
                    this.thirdScreenContent = !this.thirdScreenContent;
                }
            },


            // 遮罩层样式
            maskStyle() {
                return this.firstScreenContent || this.secondScreenContent || this.thirdScreenContent
                    ? { filter: 'brightness(0.6)' }
                    : { filter: 'brightness(1)' };
            },

            scroll(e) {
                this.old.scrollTop = e.detail.scrollTop;
            },

        },
    };
</script>

<style>
	.searchIndex {
		width: 100%;
		background: #f8f8f8;
	}

	.search {
		display: flex;
		padding: 2% 3% 2% 3%;
		background: white;
		position: fixed;
		z-index: 99;
		width: 93%;
	}
	.search>view:first-child {
		flex: 1;
		margin-right: 2%;
		border-radius: 5rpx;
		background: #f8f8f8;
	}
	.search>view:last-child {
		line-height: 80rpx;
	}

	.screen {
		width: 100%;
		padding: 2% 3% 2% 3%;
		background: white;
		display: flex;
		position: fixed;
		top: 6%;
		z-index: 99;
	}
	.screen>view:first-child {
		flex: 9;
		display: flex;
		font-size: 35rpx;
		color: #999999;
	}
	.screen>view:last-child {
		flex: 1;
		margin-right: 6%;
		text-align: center;
		line-height: 70rpx;
	}
	.screen>view:first-child>view {
		line-height: 80rpx;
	}
	.screen>view:first-child>view:not(:first-child) {
		margin-left: 5%;
	}
	.screen>view:first-child>view:last-child {
		color: black;
	}

	.firstAndSecondScreenContent {
		width: 100%;
		position: fixed;
		margin-top: 25%;
		z-index: 98;
		background: white;
		border-top: 1rpx #eeeeee solid;
		padding: 0 3% 0 3%;
	}
	.firstAndSecondScreenContent>view {
		display: flex;
		font-size: 37rpx;
		padding: 3% 0 3% 0;
		letter-spacing: 1rpx;
		color: #666666;
		border: 1rpx #eeeeee solid;
	}
	.firstAndSecondScreenContent>view>view:first-child {
		flex: 9;
	}
	.firstAndSecondScreenContent>view>view:last-child {
		flex: 1;
		margin-right: 6%;
		text-align: center;
	}
	.firstScreenContentSpecial>view:last-child {
		flex: 9;
		display: flex;
	}
	.firstScreenContentSpecial>view:last-child button {
		width: 180rpx;
		margin-left: 2%;
		font-size: 25rpx;
		background: #f8f8f8;
		color: #666666;
		border: 0;
	}

	.scroll-Y1 {
			height: 687rpx;
	}
	.scroll-Y2 {
			height: 500rpx;
	}

	.secondScreenContent {
		width: 100%;
		position: fixed;
		z-index: 98;
		background: white;
	}
	.secondScreenContent .firstAndSecondScreenContent {
		position: absolute;
	}
	.secondScreenContent>view:last-child {
		display: flex;
		justify-content: space-between;
		padding: 3%;
	}
	.secondScreenContent>view:last-child>button {
		flex: 1;
		display: block;
		border: 0;
		border-radius: 0;
		letter-spacing: 1rpx;
		background: #f8f8f8;
		padding: 0.5%;
	}
	.secondScreenContent>view:last-child>button:last-child {
		background: #7e1e1f;
		color: white;
		font-weight: 600;
	}

	.thirdScreenContent {
		width: 100%;
		position: fixed;
		margin-top: 25%;
		z-index: 99;
		background: white;
	}
	.thirdCategory {
		margin: 3%;
		margin-bottom: 5%;
	}
	.thirdCategory>view:first-child {
		font-size: 37rpx;
		letter-spacing: 1rpx;
		font-weight: 600;
	}
	.thirdCategory>view:last-child {
		display: flex;
		flex-wrap: wrap;
	}
	.thirdCategory>view:last-child view {
		margin-left: 3%;
		margin-top: 5%;
		width: 20%;
		height: 70rpx;
		line-height: 70rpx;
		background: #f8f8f8;
		text-align: center;
	}
	.thirdCategory>view:last-child view text {
		color: #757575;
	}
	.thirdScreenContent>view:last-child {
		display: flex;
		justify-content: space-between;
		padding: 3%;
	}
	.thirdScreenContent>view:last-child>button {
		flex: 1;
		display: block;
		border: 0;
		border-radius: 0;
		letter-spacing: 1rpx;
		background: #f8f8f8;
		padding: 0.5%;
	}
	.thirdScreenContent>view:last-child>button:last-child {
		background: #7e1e1f;
		color: white;
		font-weight: 600;
	}

	.content {
		width: 100%;
		display: flex;
		flex-wrap: wrap;
		margin: 0 3% 3% 0;
		padding-top: 26%;
		background: #f8f8f8;
		padding-bottom: 100%;
	}
	.content>view {
		width: 45.5%;
		margin-left: 3%;
		margin-top: 5%;
		padding-bottom: 5%;
		background: white;
	}
	.content image {
		width: 100%;
		border-radius: 8rpx;
	}
	.content>view>view:nth-child(2) {
		overflow: hidden;
		white-space: nowrap;
		text-overflow: ellipsis;
		letter-spacing: 1rpx;
		font-size: 30rpx;
		margin-top: 4%;
		margin-left: 4%;
	}
	.content>view>view:last-child {
		display: flex;
		margin-top: 4%;
		margin-left: 3%;
		font-weight: 300;
	}
	.content>view>view:last-child>text {
		flex: 1;
	}
	.content>view>view:last-child>text:first-child {
		font-weight: 600;
		font-size: 33rpx;
	}
	.content>view>view:last-child>text:last-child {
		text-align: right;
		margin-right: 3%;
		color: #808080;
		font-size: 30rpx;
		font-weight: 500;
	}
</style>
