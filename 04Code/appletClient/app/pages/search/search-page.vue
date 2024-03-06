<template>
    <!-- 搜索页面 -->
    <view class="searchPage">
        <view class="search">
            <uni-easyinput v-model="searchContent" :styles="styles" confirmType="search" placeholderStyle="font-size:33rpx" prefixIcon="search" placeholder="请输入关键字搜索" @change="enterSearch()"></uni-easyinput>
        </view>
        <view style="margin-top: 15%;"></view>
        <!-- 历史 -->
        <view v-if="historicalSearch.length !== 0 && searchContent === ''" class="history">
            <view>
                <text>历史</text>
                <uni-icons type="trash" size="26" @click="$refs.popup.open();"></uni-icons>
            </view>
            <view>
                <text v-for="(item, index) in historicalSearch" :key="index">{{ item }}</text>
            </view>
        </view>
        <!-- 发现 -->
        <view v-show="searchContent === ''" class="find">
            <view>
                <text>发现</text>
                <uni-icons :type="findState ? 'eye-slash' : 'eye'" size="26" @click="findState = !findState"></uni-icons>
            </view>
            <view>
                <text v-for="(item, index) in (findState ? discoveryContent : [])" :key="index">{{ item }}</text>
            </view>
        </view>
        <!-- 热搜榜 -->
        <view v-show="searchContent === ''" class="hotSearch">
            <view>
                <uni-icons type="fire-filled" size="26" color="#7e1e1f"></uni-icons>
                <text>热搜榜</text>
            </view>
            <view>
                <uni-grid column="2" :showBorder="false" :square="false">
                    <uni-grid-item v-for="(item, index) in hotSearchList" :key="index">
                        <view class="item">
                            <view>{{ index + 1 }}</view>
                            <text>{{ item.name }}</text>
                            <text>人气 {{ item.heat }}</text>
                        </view>
                    </uni-grid-item>
                </uni-grid>
            </view>
        </view>
        <!-- 搜索检索内容 -->
        <view v-show="searchContent !== ''" class="retrieval">
            <text v-for="(item, index) in searchRetrieval" :key="index">{{ item }}</text>
        </view>
        <!-- 清除历史搜索内容提示框 -->
        <uni-popup ref="popup" type="dialog">
            <uni-popup-dialog mode="base" title="温馨提示" content="是否清除历史搜索记录？" :duration="2000" beforeClose="true" @close="$refs.popup.close()" @confirm="removeHistorySearch"></uni-popup-dialog>
        </uni-popup>
    </view>
</template>

<script>
    export default {
        data() {
            return {
                // 搜索内容
                searchContent: '',
                // 历史搜索
                historicalSearch: [
                    '轻奢', '意式极简', '岩板家具', '意式极简大理石茶几设计师款客厅沙发极简边几大小茶几后现代简约', '沙发',
                ],
                // 发现内容
                discoveryContent: [
                    '喜临门', 'MUJI', '岩板家具', '马鞍皮椅', '意式极简',
                ],
                // 热搜榜
                hotSearchList: [
                    { name: '床', heat: 1252 },
                    { name: '沙发', heat: 1171 },
                    { name: '茶几', heat: 413 },
                    { name: '床头柜', heat: 357 },
                    { name: '餐桌', heat: 240 },
                    { name: '餐椅', heat: 179 },
                    { name: '休闲椅', heat: 162 },
                    { name: '椅', heat: 114 },
                    { name: '悬浮床', heat: 98 },
                    { name: '电视柜', heat: 71 },
                ],
                // 搜索检索
                searchRetrieval: [
                    '现代轻奢餐桌岩板餐桌椅组合现代简约家用小户型餐桌意式岩板餐桌椅子蛇脚餐台',
                    '凯瑟琳',
                    '(11.11特价真皮沙发)客厅现代简约真皮沙发极简头层牛皮大象耳朵皮艺沙发',
                    '真皮沙发头层牛皮沙发客厅简约现代沙发转角沙发',
                    '现代极简头层牛皮沙发仿真皮沙发客厅简约现代沙发转角沙发',
                    '头层牛皮沙发仿真皮沙发科技皮客厅简约现代沙发转角沙发',
                    '头层牛皮沙发脚机音响+蓝牙+USB端口仿真皮沙发客厅简约现代沙发直排沙发',
                    '头层牛皮沙发真皮转角沙发客厅简约现代小户型',
                ],
                // 是否显示发现栏
                findState: true,
                styles: {
                    borderColor: '#f8f8f8',
                },
            };
        },

        methods: {
            // 清除历史搜索
            removeHistorySearch() {
                this.historicalSearch = [];
                this.$refs.popup.close();
            },

            // 回车搜索
            enterSearch() {
                if (this.searchContent !== '') {
                    uni.navigateTo({
                        url: `/pages/search/search-index?searchContent=${this.searchContent}`,
                    });
                }
            },
        },
    };
</script>

<style>
	.searchPage {
		width: 100%;
		background: white;
		padding: 4%;
		padding-top: 2%;
	}

	.search {
		background: #f8f8f8;
		margin-right: 8%;
		line-height: 60rpx;
		font-size: 35rpx;
		padding: 0;
		border-radius: 8rpx;
		position: fixed;
		width: 92%;
		z-index: 99;
	}

	.history, .find {
		margin-top: 5%;
		background: white;
		margin-right: 8%;
	}
	.history>view:first-child, .find>view:first-child {
		font-size: 38rpx;
		font-weight: 600;
		display: flex;
		letter-spacing: 1rpx;
		justify-content: space-between;
	}
	.history>view:first-child uni-icons, .find>view:first-child uni-icons {
		font-weight: 500;
	}
	.history>view:last-child, .find>view:last-child {
		display: flex;
		flex-wrap: wrap;
	}
	.history>view:last-child text, .find>view:last-child text {
		display: block;
		padding: 2.8%;
		border: 3rpx #e6e6e6 solid;
		border-radius: 5rpx;
		margin-left: 2%;
		margin-top: 3%;
		font-size: 35rpx;
		letter-spacing: 1rpx;
	}

	.hotSearch {
		margin-top: 5%;
		background: white;
		margin-right: 8%;
	}
	.hotSearch>view:first-child {
		font-size: 38rpx;
		font-weight: 600;
		letter-spacing: 1rpx;
	}
	.hotSearch>view:last-child .item {
		display: flex;
		margin-top: 15%;
	}
	.hotSearch>view:last-child .item>view {
		width: 40rpx;
		height: 40rpx;
		text-align: center;
		line-height: 40rpx;
		border-radius: 5rpx;
		margin-left: 5%;
		border: 1rpx #e5e5e5 solid;
		background: white;
	}
	.hotSearch>view:last-child .item>text:nth-child(2) {
		font-size: 35rpx;
		font-weight: 600;
		margin-left: 6%;
		letter-spacing: 1rpx;
	}
	.hotSearch>view:last-child .item>text:last-child {
		font-size: 28rpx;
		color: #a4a4a4;
		margin-left: 5%;
	}
	.hotSearch>view:last-child uni-grid-item:first-child .item>view {
		background: #f5cb59;
		color: white;
		border: 1rpx #f5cb59 solid;
	}
	.hotSearch>view:last-child uni-grid-item:nth-child(2) .item>view {
		background: #a8a9b2;
		color: white;
		border: 1rpx #a8a9b2 solid;
	}
	.hotSearch>view:last-child uni-grid-item:nth-child(3) .item>view {
		background: #f5cb59;
		color: white;
		border: 1rpx #f5cb59 solid;
	}

	.retrieval {
		width: 100%;
		position: absolute;
		margin-top: -2%;
		margin-left: -4%;
	}
	.retrieval>text {
		display: block;
		color: #666666;
		padding: 3%;
		font-size: 30rpx;
		letter-spacing: 1rpx;
		line-height: 2;
		border-top: 1rpx #eeeeee solid;
	}
</style>
