<template>
	<view class="order">
		<uni-nav-bar left-icon="left"  title="投注记录" background-color="rgb(40,148,255)" color="#fff" :border="false" @clickLeft="goBack"></uni-nav-bar>
		<view class="search-date" v-if="fdate">查询日期：{{fdate}}</view>
		<scroll-view scroll-y="true" @scrolltolower="scrolltolower" style="height: 95%"
		        @refresherrefresh="getRefresherrefresh" :refresher-enabled="false" :refresher-triggered="refresherTriggered"
		        refresher-background="transparent">
			<view class="record-list">
				 <view class="record-item" v-for="(item,index) in records" :key="index">
					  <view class="left">
						 <view class="row" v-if="userinfo.orgtype==1">所属账号：{{item.userno}}</view>
						  <view class="row">下注单号：No.{{item.orderno}}</view>
						  <view class="row">期数：{{item.issue}}</view>
						  <view class="row">开奖号码：<text class="row-result">{{item.result}}</text></view>
						  <view class="row">龙虎出入：{{getStatus(item.bno)}}</view>
						  <view class="row">游戏：<text class="red">宝斗</text></view>
						  <view class="row">玩法：<text class="red">{{item.artid}}</text></view>
						  <view class="row">倍率：<text class="red">{{item.cpright}}</text></view>
						  <view class="row">金额：<text class="red">{{item.bailmoney}}</text></view>
						  <view class="row">中奖金额：<text class="red" v-if="item.bno">{{item.loss + item.bailmoney - item.comm}}</text></view>
						  <view class="row">下注时间：{{item.ordtime}}</view>
					  </view>
					  <view class="right">
						  <view class="red" v-if="!item.bno">未开奖</view>
						  <view v-else>
							  <view class="red" v-if="item.loss < 0 ">未中奖</view>
							  <view class="blue" v-else-if="item.loss==0">打和</view>
							  <view class="blue" v-else>中奖</view>
						  </view>
						 
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
				statusList:[
					{val:1,name:'入'},
					{val:2,name:'龙'},
					{val:3,name:'出'},
					{val:4,name:'虎'}
				],
				search:{
					pageIdx:0,
					pageSize:10
				},
				totalPage:1,
				totalCount:0,
				loadMore:false,
				refresherTriggered:false,
				userinfo:{},
				orgtype:'',
				userno:'',
				fdate:''
			}
		},
		onLoad(option) {
			this.userinfo = JSON.parse(uni.getStorageSync('userinfo'))
			this.orgtype = this.userinfo.orgtype
			this.userno = this.userinfo.userno
			this.records = []
			// this.loadData()
		},
		onShow(){
			this.userinfo = JSON.parse(uni.getStorageSync('userinfo'))
			this.orgtype = this.userinfo.orgtype
			this.userno = this.userinfo.userno
			this.records = []
			this.search.pageIdx = 0
			this.totalPage = 1
			this.totalCount = 0
			this.loadData()
		},
		methods: {
			scrolltolower() {
				if (this.records.length >= this.totalCount) return
				if(!this.loadMore){
					this.loadMore = true
					this.loadData()
				}
			},
			//下拉刷新
			getRefresherrefresh(){
				this.refresherTriggered = true
				this.search.pageIdx = 0
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
					if (this.search.pageIdx >= this.totalCount) {
						this.search.pageIdx = this.totalCount + 1;
					} else {
						this.search.pageIdx = this.search.pageIdx + this.search.pageSize
					}
					this.refresherTriggered = false
					this.loadMore = false
				})
			},
			goBack(){
				uni.navigateTo({
					url:'/pages/home/qmbd'
				})
			},
			getStatus(status){
				const item = this.statusList.find(item=> item.val==status) || {}
				return item.name
			}
		}
	}
</script>

<style scoped lang="scss">
.order{
	width: 750upx;
	height: 100vh;
	.search-date{
		padding: 20upx;
	}
	.record-list{
		.record-item{
			background-color: #fff;
			padding: 20upx;
			display: flex;
			justify-content: space-between;
			align-items: center;
			width: 670upx;
			margin: 10upx auto;
			.row-result{
				font-size: 26upx;
			}
			.blue{
				color:blue;
			}
			.red{
				color:red;
			}
			.right{
				font-size: 32upx;
				font-weight: 600;
				padding-right: 40upx;
			}
		}
	}
}
</style>
