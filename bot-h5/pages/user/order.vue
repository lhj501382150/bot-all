<template>
	<view class="order">
		<uni-nav-bar left-icon="left"  title="投注记录" background-color="rgb(40,148,255)" color="#fff" :border="false" @clickLeft="goBack"></uni-nav-bar>
		<scroll-view scroll-y="true" @scrolltolower="scrolltolower" style="height: 95%"
		        @refresherrefresh="getRefresherrefresh" :refresher-enabled="false" :refresher-triggered="refresherTriggered"
		        refresher-background="transparent">
			<view class="record-list">
				 <view class="record-item" v-for="(item,index) in records" :key="index">
					  <view class="left">
						 <view class="row" v-if="userinfo.orgtype==1">所属账号：{{item.userno}}</view>
						  <view class="row">下注单号：No.{{item.orderno}}</view>
						  <view class="row">期数：{{item.contnum}}</view>
						  <view class="row">开奖号码：{{item.artid}}</view>
						  <view class="row">龙虎出入：{{item.artid}}</view>
						  <view class="row">游戏：<text class="red">宝斗</text></view>
						  <view class="row">玩法：<text class="red">{{item.artid}}</text></view>
						  <view class="row">倍率：<text class="red">{{item.cpright}}</text></view>
						  <view class="row">金额：<text class="red">{{item.bailmoney}}</text></view>
						  <view class="row">中奖金额：<text class="red">{{item.loss + item.bailmoney}}</text></view>
						  <view class="row">下注时间：{{item.ordtime}}</view>
					  </view>
					  <view class="right">
						  <view class="red" v-if="item.loss < 0 ">未中奖</view>
						  <view class="blue" v-else-if="item.loss==0">打和</view>
						  <view class="blue" v-else>中奖</view>
					  </view>
				 </view>
			</view>
		</scroll-view>
	</view>
</template>

<script>
	export default {
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
				userinfo:{},
				orgtype:'',
				userno:'',
				fdate:''
			}
		},
		onLoad(option) {
			this.userinfo = JSON.parse(uni.getStorageSync('userinfo'))
			this.orgtype = option.orgtype || this.userinfo.orgtype
			this.userno = option.userno || this.userinfo.userno
			this.fdate = option.fdate
			this.records = []
			this.loadData()
		},
		methods: {
			scrolltolower() {
				if (this.search.pageIdx > this.totalPage) return
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
				let url = ''
				if(this.orgtype==1){
					url = '/Query/SubOrdList'
					this.search.fdate = this.fdate
				}else{
					url = '/Query/GetOrderList'
				}
				this.search.userno = this.userno
				this.$http.post(url,this.search,res => {
					let datas = res.rData || []
					this.records = [...this.records,...datas]
					this.totalCount = res.iCount;
					this.totalPage = this.totalCount % this.search.pageSize == 0 ? this.totalCount / this.search.pageSize : this.totalCount / this.search.pageSize + 1
					if (this.search.pageIdx >= this.totalPage) {
						this.search.pageIdx = this.totalPage + 1;
					} else {
						this.search.pageIdx = this.search.pageIdx + 1
					}
						
					this.refresherTriggered = false
				})
			},
			goBack(){
				uni.switchTab({
					url:'/pages/user/user'
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
		padding: 20upx;
		.record-item{
			background-color: #fff;
			padding: 20upx;
			display: flex;
			justify-content: space-between;
			align-items: center;
			width: 670upx;
			margin: 10upx auto;
			.blue{
				color:blue;
			}
			.red{
				color:red;
			}
			.right{
				font-size: 40upx;
				font-weight: 600;
				padding-right: 40upx;
			}
		}
	}
}
</style>
