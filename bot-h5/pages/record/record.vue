<template>
	<view class="order">
		<uni-nav-bar left-icon="left"  title="投注记录" background-color="rgb(40,148,255)" color="#fff" :border="false" @clickLeft="goBack"></uni-nav-bar>
		<scroll-view scroll-y="true" @scrolltolower="scrolltolower" style="height: 95%;"
		        @refresherrefresh="getRefresherrefresh" :refresher-enabled="false" :refresher-triggered="refresherTriggered"
		        refresher-background="transparent">
			<view class="record-list">
				 <view class="record-item" v-for="(item,index) in records" :key="index">
					  <view class="row">
						  <view class="left">类型：{{item.artid}}</view>
					      <view class="right">金额：{{item.bailmoney}}</view>
					  </view>
					  <view class="row">
						  <view class="left">倍数：{{item.cpright}}</view>
					      <view class="right">本局输赢：<text  :style="{color:item.loss>=0?'red':'green'}">{{item.loss}}</text></view>
					  </view>
					  <view class="row2">
						  <view class="left">编号：{{item.orderno}}</view>
					      <view class="right">时间：{{item.ordtime}}</view>
					  </view>
				 </view>
			</view>
		</scroll-view>
		<sys-status></sys-status>
	</view>
</template>

<script>
	import SysStatus from '../../components/sys-status/sys-status.vue'
	export default {
		components:{
			SysStatus
		},
		data() {
			return {
				records:[],
				search:{
					pageIdx:0,
					pageSize:10
				},
				totalPage:1,
				totalCount:0,
				refresherTriggered:false,
			}
		},
		onShow() {
			this.records = []
			this.search={
				pageIdx:0,
				pageSize:10
			}
			this.loadData()
		},
		methods: {
			scrolltolower() {
				this.loadData()
			},
			//下拉刷新
			getRefresherrefresh(){
				this.refresherTriggered = true
				this.search.pageIdx = 1
				this.totalPage = 1
				this.totalCount = 0
				this.records = []
				this.loadData()
			},
			loadData(){
				this.search.userno = uni.getStorageSync('userno')
				this.$http.post("/Query/GetOrderList",this.search,res => {
					this.records = [...this.records,...res.rData]
					this.totalCount = res.iCount;
					this.totalPage = this.totalCount % this.search.pageSize == 0 ? parseInt(this.totalCount / this.search.pageSize) : parseInt(this.totalCount / this.search.pageSize) + 1
					if (this.search.pageIdx >= this.totalPage) {
						this.search.pageIdx = this.totalPage + 1;
					} else {
						this.search.pageIdx = this.search.pageIdx + 1
					}
						
					this.refresherTriggered = false
				})
			},
			goBack(){
				uni.navigateTo({
					url:'/pages/home/qmbd'
				})
			}
		}
	}
</script>

<style scoped lang="scss">
.order{
	width: 750upx;
	height: 100vh;
	.record-list{
		padding: 40upx;
		.record-item{
			background-color: #fff;
			padding: 20upx;
			margin-bottom: 20upx;
			.row,.row2{
				display: flex;
				justify-content: space-between;
				align-items: center;
			}	
			.row{
				font-size: 30upx;
				padding-bottom: 20upx;
			}
			.row2{
				font-size: 26upx;
				color: #787878;
			}
		}
	}
}
</style>
