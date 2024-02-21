<template>
    <!-- 商品列表 -->
    <view class="searchIndex">
        <!-- 搜索 -->
        <view class="search">
            <view>
                <uni-easyinput v-model="searchContent" :styles="styles" confirmType="search" placeholderStyle="font-size:33rpx" prefixIcon="search" placeholder="请输入关键字搜索" @change="enterSearch()"></uni-easyinput>
            </view>
            <view>
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
            <view>
                <view>
                    <text>综合</text>
                </view>
                <view>
                    <uni-icons type="checkbox-filled" size="30" color="#7e1e1f"></uni-icons>
                </view>
            </view>
            <view>
                <view>
                    <text>上新</text>
                </view>
                <view>
                    <uni-icons type="checkbox-filled" size="30" color="#7e1e1f"></uni-icons>
                </view>
            </view>
            <view>
                <view>
                    <text>销量</text>
                </view>
                <view>
                    <uni-icons type="checkbox-filled" size="30" color="#7e1e1f"></uni-icons>
                </view>
            </view>
            <view>
                <view>
                    <text>人气</text>
                </view>
                <view>
                    <uni-icons type="checkbox-filled" size="30" color="#7e1e1f"></uni-icons>
                </view>
            </view>
            <view class="firstScreenContentSpecial">
                <view>
                    <text>价格</text>
                </view>
                <view>
                    <button>由高到低</button>
                    <button>由低到高</button>
                </view>
            </view>
        </view>
        <!-- 第二个筛选内容 -->
        <view v-show="secondScreenContent" class="secondScreenContent">
            <scroll-view :scrollTop="scrollTop" scrollY="true" class="scroll-Y1" @scroll="scroll">
                <view class="firstAndSecondScreenContent">
                    <view v-for="(item, index) in productStyle" :key="index">
                        <view>
                            <text>{{ item.name }}</text>
                        </view>
                        <view v-show="item.selected">
                            <uni-icons type="checkbox-filled" size="30" color="#7e1e1f"></uni-icons>
                        </view>
                    </view>
                </view>
            </scroll-view>
            <view>
                <button>重置</button>
                <button>确定</button>
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
                        <view v-for="(item, index) in category" :key="index" @click="clickFilterContent(index, true)">
                            <text :style="item.selected ? { color: 'black' } : { color: 'rgb(117, 117, 117)' }">{{ item.name }}</text>
                        </view>
                    </view>
                </view>
                <view class="thirdCategory">
                    <view>
                        <text>空间</text>
                    </view>
                    <view>
                        <view v-for="(item, index) in space" :key="index" @click="clickFilterContent(index, false)">
                            <text :style="item.selected ? { color: 'black' } : { color: 'rgb(117, 117, 117)' }">{{ item.name }}</text>
                        </view>
                    </view>
                </view>
            </scroll-view>
            <view>
                <button>重置</button>
                <button>确定</button>
            </view>
        </view>
        <!-- 显示产品 -->
        <view class="content" :style="maskStyle()">
            <view v-for="(item, index) in product" :key="index" :style="cardDisplay ? { width: '91%' } : { width: '45.5%' }">
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
    export default {
        data() {
            return {
                // 搜索内容
                searchContent: '',
                // 第一/二/三个筛选内容
                firstScreenContent: false,
                secondScreenContent: false,
                thirdScreenContent: false,
                // 风格
                productStyle: [
                    { name: '轻奢主义', selected: true },
                    { name: '现代简约', selected: false },
                    { name: '自然北欧', selected: false },
                    { name: '现代中式', selected: false },
                    { name: '精品实木', selected: false },
                    { name: '经典美式', selected: false },
                    { name: '奢华欧法', selected: false },
                    { name: '软装配饰', selected: false },
                    { name: '佗寂风/中古风', selected: false },
                    { name: '轻奢风', selected: false },
                ],
                // 第三个筛选内容 —— 品类
                category: [
                    { name: '沙发', selected: true },
                    { name: '茶几', selected: false },
                    { name: '电视柜', selected: false },
                    { name: '鞋柜', selected: false },
                    { name: '餐椅', selected: false },
                    { name: '餐边柜', selected: false },
                    { name: '床', selected: false },
                    { name: '床头柜', selected: false },
                    { name: '床垫', selected: false },
                    { name: '餐桌', selected: false },
                    { name: '休闲椅', selected: false },
                ],
                // 第三个筛选内容 —— 空间
                space: [
                    { name: '客厅', selected: true },
                    { name: '餐厅', selected: false },
                    { name: '卧室', selected: false },
                    { name: '书房', selected: false },
                    { name: '饰品', selected: false },
                    { name: '厨房', selected: false },
                    { name: '阳台', selected: false },
                ],
                // 选择品类
                selectCategory: [],
                // 选择空间
                selectSpace: [],
                // 产品
                product: [
                    { name: '意式极简磨砂质感沙发科技布真皮沙发', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                    { name: '凯瑟琳', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                    { name: '客厅现代简约真皮沙发极简头层牛皮大象耳朵皮艺沙发', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                    { name: '真皮沙发 头层牛皮沙发 客厅简约现代沙发 转角沙发', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
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

        onLoad(optioon) {
            // 搜索内容
            this.searchContent = optioon.searchContent;
            console.log(this.searchContent);
        },

        methods: {
            // 回车搜索
            enterSearch() {
                if (this.searchContent !== '') {
                    console.log(this.searchContent);
                }
            },

            // 筛选内容
            clickFilterContent(index, type) {
                if (type) {
                    this.selectCategory = [];
                    this.category[index].selected = !this.category[index].selected;
                    this.category.forEach((element) => {
                        if (element.selected) {
                            this.selectCategory.push(element.name);
                        }
                    });
                } else {
                    this.selectSpace = [];
                    this.space[index].selected = !this.space[index].selected;
                    this.space.forEach((element) => {
                        if (element.selected) {
                            this.selectSpace.push(element.name);
                        }
                    });
                }
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
