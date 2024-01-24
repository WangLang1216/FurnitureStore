<template>
    <!-- 猜你喜欢推荐 -->
    <view class="recommend">
        <uni-section title="猜你喜好" type="line" padding>
            <uni-grid :column="2" :showBorder="false" highlight="false">
                <uni-grid-item v-for="(item, index) in recommend" :key="index" class="grid-item">
                    <view class="grid-item-box">
                        <view>
                            <image :src="item.image" mode="widthFix"></image>
                        </view>
                        <view>
                            <text>{{ item.name }}</text>
                        </view>
                        <view>
                            <view>
                                ￥<text>{{ item.price }}</text>
                            </view>
                            <view>
                                <text>已售{{ item.sold }}</text>
                            </view>
                        </view>
                    </view>
                </uni-grid-item>
            </uni-grid>
        </uni-section>
    </view>
</template>

<script>
    export default {
        data() {
            return {
                recommend: [
                    { name: '意式极简磨砂质感沙发科技布真皮沙发', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                    { name: '凯瑟琳', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                    { name: '客厅现代简约真皮沙发极简头层牛皮大象耳朵皮艺沙发', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                    { name: '真皮沙发 头层牛皮沙发 客厅简约现代沙发 转角沙发', price: '4513', sold: '11', image: '/static/images/sofa_1.jpg' },
                ],
            };
        },

        methods: {

        },
    };
</script>

<style>
		.recommend {
			width: 100%;
			padding-left: 2%;
			background: #f1f1f1;
		}
		.grid-item {
			width: 45%;
			background: white;
			margin-left: 2%;
			margin-top: 4%;
			padding-bottom: 12%;
			border-top-left-radius: 3%;
			border-top-right-radius: 3%;
		}
		.grid-item-box image {
			width: 90%;
			border-top-left-radius: 3%;
			border-top-right-radius: 3%;
		}
		.grid-item-box>view:nth-child(2) {
			padding-right: 11%;
			padding-top: 2%;
			padding-left: 5%;
			white-space: nowrap;
			overflow: hidden;
			text-overflow: ellipsis;
			font-size: 30rpx;
			font-weight: 400;
			letter-spacing: 1px;
		}
		.grid-item-box>view:last-child {
			display: flex;
			padding-top: 3%;
		}
		.grid-item-box>view:last-child>view {
			flex: 1;
		}
		.grid-item-box>view:last-child>view:first-child {
			padding-left: 4%;
			font-size: 26rpx;
		}
		.grid-item-box>view:last-child>view:first-child text {
			font-size: 30rpx;
			font-weight: 600;
		}
		.grid-item-box>view:last-child>view:last-child {
			text-align: center;
			padding-right: 5%;
			font-size: 28rpx;
			color: #808080;
		}
</style>
