<template>
    <!-- 购物车页面 -->
    <view class="shoppingTrolley">
        <!-- 已登录时 -->
        <view v-if="isLogin" class="login">
            <!-- 权限和编辑按钮 -->
            <view class="top">
                <view>
                    <label @click="isSelectAll = !isSelectAll">
                        <radio borderColor="#cccccc" color="#7e1e1f" :checked="isSelectAll" value="r2"></radio>全选
                    </label>
                </view>
                <view>
                    <text @click="notDelete = !notDelete">编辑</text>
                </view>
            </view>
            <!-- 购物车卡片 -->
            <view v-for="(item, index) in shoppingTrolley" :key="index" class="card">
                <view>
                    <radio borderColor="#cccccc" color="#7e1e1f" :checked="item.isSelect" value="r2" @click="item.isSelect = !item.isSelect; status = !status"></radio>
                </view>
                <view>
                    <image :src="item.image" mode="aspectFit"></image>
                </view>
                <view class="cardRight">
                    <view>
                        <text>{{ item.name }}</text>
                        <text> 样色：{{ item.colour + ',' + item.materialQuality + ',' + item.size }}</text>
                    </view>
                    <view>
                        <view>
                            ￥<text>{{ item.price }}</text>
                        </view>
                        <view>
                            <button :style="limitation(item.quantity, false)" @click="quantityChange(index, false)">-</button>
                            <text>{{ item.quantity }}</text>
                            <button :style="limitation(item.quantity, true)" @click="quantityChange(index, true)">+</button>
                        </view>
                    </view>
                </view>
            </view>
        </view>
        <!-- 未登录时 -->
        <view v-else class="notLogin">
            <image src="@/static/images/not-login.jpg" mode="aspectFit"></image>
            <text>请先登录，登录后查看您的购物车商品</text>
            <button>去登录</button>
        </view>
        <!-- 猜你喜欢推荐 -->
        <text class="recommendedTitles">猜您喜欢</text>
        <recommend></recommend>
        <!-- 底部操作区域 -->
        <view class="bottomSettlement">
            <view v-show="notDelete">
                合计：
                <text>￥</text>
                <text>{{ getTotalOrderAmount() }}</text>
            </view>
            <!-- 占位 -->
            <view v-show="notDelete == false"></view>
            <view>
                <button v-show="notDelete">预订({{ getOrderQuantity() }})</button>
                <button v-show="notDelete">结算({{ getOrderQuantity() }})</button>
                <button v-show="notDelete == false" class="deleteButton" @click="orderRemove()">删除</button>
            </view>
        </view>
        <!-- 底部导航条 -->
        <bottom-navigation :index="3"></bottom-navigation>
    </view>
</template>

<script>
    import bottomNavigation from '../navigation/bottomNavigation.vue';
    import recommend from '../recommend/recommend.vue';
    export default {
        components: {
            bottomNavigation,
            recommend,
        },

        data() {
            return {
                // 是否已登录
                isLogin: true,
                // 是否全选
                isSelectAll: false,
                // 购物车信息
                shoppingTrolley: [
                    { isSelect: false, name: '意式极简磨砂质感沙发 科技布真皮沙发1', size: '单人1050*1050*700', materialQuality: '磨砂布科技布', colour: '先锋XFB538-1', price: 4513, quantity: 1, image: '../../static/images/shopping/shopping1.jpg' },
                    { isSelect: false, name: '意式极简磨砂质感沙发 科技布真皮沙发2', size: '单人1050*1050*700', materialQuality: '磨砂布科技布', colour: '先锋XFB538-1', price: 4513, quantity: 1, image: '../../static/images/shopping/shopping1.jpg' },
                    // { isSelect: false, name: '意式极简磨砂质感沙发 科技布真皮沙发3', size: '单人1050*1050*700', materialQuality: '磨砂布科技布', colour: '先锋XFB538-1', price: 4513, quantity: 1, image: '../../static/images/shopping/shopping1.jpg' },
                ],
                // 订单数量
                orderQuantity: 0,
                // 订单总额
                totalOrderAmount: 0,
                // 是否为非编辑
                notDelete: true,
                // 数组状态
                status: false,
                // 数量限制按钮样式
                normalButtonStyle: '{background: #f2f3f5, color: #323233}',
                limitsButtonStyle: '{background: #f7f8fa, color: #c8c9cc}',
            };
        },

        computed: {

        },

        watch: {
            isSelectAll() {
                if (this.isSelectAll) {
                    this.shoppingTrolley.forEach((element) => {
                        element.isSelect = true;
                    });
                    this.status = !this.status;
                } else {
                    if (this.orderQuantity == this.shoppingTrolley.length) {
                        this.shoppingTrolley.forEach((element) => {
                            element.isSelect = false;
                        });
                        // 重置
                        this.orderQuantity = this.totalOrderAmount = 0;
                    }
                }
            },

            // 数组改变重新计算
            status() {
                // 计算总额和总数
                this.totalOrderAmount = 0;
                this.orderQuantity = 0;
                this.shoppingTrolley.forEach((element) => {
                    if (element.isSelect) {
                        this.totalOrderAmount += element.price * element.quantity;
                        this.orderQuantity++;
                    }
                });
                // 全选或非全选
                this.isSelectAll = this.orderQuantity == this.shoppingTrolley.length;
            },
        },

        mounted() {
            // 取消顶部返回首页按钮
            uni.hideHomeButton();
        },

        methods: {
            // 订单删除
            orderRemove() {
                console.log('123');
                if (this.orderQuantity == this.shoppingTrolley.length) {
                    // 是否全部删除
                    this.shoppingTrolley = [];
                } else if (this.orderQuantity != 0) {
                    const temp = this.shoppingTrolley.filter((element) => {
                        return element.isSelect == false;
                    });
                    this.shoppingTrolley = temp;
                }
            },

            // 订单总额
            getTotalOrderAmount() {
                return this.isSelectAll || (this.orderQuantity != 0) ? this.totalOrderAmount : 0;
            },

            // 订单总数
            getOrderQuantity() {
                return this.isSelectAll || (this.orderQuantity != 0) ? this.orderQuantity : 0;
            },

            // 数量改变
            quantityChange(index, operationType) {
                let quantity = this.shoppingTrolley[index].quantity;
                // 添加数量
                if (operationType) {
                    if (++quantity > 5) {
                        // 数量最高为5
                        return;
                    }
                    this.shoppingTrolley[index].quantity++;
                } else {
                    if (--quantity < 1) {
                        // 数量最低为1
                        return;
                    }
                    this.shoppingTrolley[index].quantity--;
                }
                this.status = !this.status;
            },

            // 数量限制按钮样式改变
            limitation(quantity, operationType) {
                return operationType
                    ? quantity == 5 ? this.limitsButtonStyle : this.normalButtonStyle
                    : quantity == 1 ? this.limitsButtonStyle : this.normalButtonStyle;
            },
        },
    };
</script>

<style>
	.shoppingTrolley {
		width: 100%;
		padding-bottom: 35%;
		background: #f8f8f8;
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
		padding-bottom: 8%;
		background: #f8f8f8;
		padding-top: 15%;
	}
	.login .top {
		width: 100%;
		height: 90rpx;
		background: white;
		position: fixed;
		top: 0;
		z-index: 99;
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
	.login .card {
		width: 100%;
		padding-bottom: 3%;
		/* margin-top: 3%; */
		display: flex;
		padding-left: 4%;
		background: white;
	}
	.login .card>view:first-child {
		flex: 1;
		padding-left: 1%;
		padding-top: 15%;
	}
	.login .card>view:nth-child(2) {
		flex: 5;
		text-align: center;
	}
	.login .card>view:nth-child(2) image {
		width: 90%;
		height: 68%;
		margin-top: 10%;
		border-radius: 10%;
	}
	.login .card>view:last-child {
		flex: 8;
		background: white;
	}
	.cardRight {
		display: flex;
		flex-direction: column;
	}
	.cardRight>view:first-child {
		flex: 5;
		padding-right: 4%;
		padding-left: 1%;
		padding-top: 5%;
	}
	.cardRight>view:first-child>text:first-child {
		font-size: 34rpx;
		font-weight: 600;
		letter-spacing: 1rpx;
	}
	.cardRight>view:first-child>text:last-child {
		display: block;
		width: 94%;
		position: relative;
		top: 7%;
		color: #757575;
		border: 1rpx solid #f7f7f7;
	}
	.cardRight>view:last-child {
		flex: 2;
		display: flex;
	}
	.cardRight>view:last-child>view {
		flex: 1;
		margin-top: 2%;
	}
	.cardRight>view:last-child>view:first-child {
		font-size: 28rpx;
		padding-top: 5%;
	}
	.cardRight>view:last-child>view:first-child text {
		font-size: 34rpx;
		font-weight: 600;
	}
	.cardRight>view:last-child>view:last-child {
		display: flex;
		font-weight: 600;
		padding-right: 8%;
		padding-top: 3%;
	}
	.cardRight>view:last-child>view:last-child button {
		width: 80rpx;
		height: 60rpx;
		margin-left: 1%;
		margin-right: 1%;
		font-size: 50rpx;
		line-height: 50rpx;
		background: #f2f3f5;
	}
	.cardRight>view:last-child>view:last-child>button:first-child {
		border-top-right-radius: 0;
		border-bottom-right-radius: 0;
	}
	.cardRight>view:last-child>view:last-child>button:last-child {
		border-top-left-radius: 0;
		border-bottom-left-radius: 0;
	}
	.cardRight>view:last-child>view:last-child text {
		display: block;
		width: 90rpx;
		height: 60rpx;
		color: #323233;
		line-height: 60rpx;
		background: #f2f3f5;
		text-align: center;
	}


	.shoppingTrolley .bottomSettlement {
		width: 100%;
		height: 130rpx;
		position: fixed;
		bottom: 100rpx;
		display: flex;
		border-top: 1rpx solid gainsboro;
		background: white;
	}
	.bottomSettlement>view {
		flex: 1;
	}
	.bottomSettlement>view:first-child {
		text-align: left;
		flex: 3;
		padding-left: 4%;
		font-size: 33rpx;
		line-height: 120rpx;
	}
	.bottomSettlement>view:first-child>text:first-child {
		font-weight: 600;
	}
	.bottomSettlement>view:first-child>text:last-child {
		font-size: 40rpx;
		font-weight: 600;
	}
	.bottomSettlement>view:last-child {
		flex: 4;
		display: flex;
		padding-right: 3%;
		padding-top: 1.5%;
	}
	.bottomSettlement>view:last-child button {
		width: 60%;
		height: 100rpx;
		color: white;
		margin-right: 2%;
		line-height: 100rpx;
		font-size: 38rpx;
		background: #7e1e1f;
	}
	.bottomSettlement>view:last-child button:first-child {
		background: #f59359;
	}
	.bottomSettlement>view:last-child .deleteButton {
		width: 33%;
		background: #f7f7f7;
		color: #ff4d4d;
	}

	.recommendedTitles {
		font-size: 43rpx;
		padding-left: 5%;
		font-weight: 600;
		letter-spacing: 1rpx;
	}
</style>
