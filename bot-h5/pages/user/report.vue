<template>
	<view class="report">
		<uni-nav-bar left-icon="left"  title="报表查询" background-color="rgb(40,148,255)" color="#fff" :border="false" @clickLeft="goBack"></uni-nav-bar>
		<view class="title">
			<view class="sub-type">类型：{{getStatus(clevel)}}报表</view>
			<view class="sub-type">账号：{{userno}}</view>
		</view>
		<view class="tabs">
			<view class="tab-item" :class="tabIndex==item.val?'active':''" v-for="(item,index) in types" :key="index" @click="query(item)">
				{{item.name}}
			</view>
		</view>
		 <view class="table-data">
			 <view class="table-row">
				 <view class="row-col col1">日期</view>
				 <view class="row-col col2">注数</view>
				 <view class="row-col col3">下注金额</view>
				 <view class="row-col col4">有效金额</view>
				 <view class="row-col col5">退水</view>
				 <view class="row-col col6">盈亏</view>
			 </view>
			 <view class="table-row" v-for="(item,index) in records" :key="index">
				<view class="row-col col1">{{item.fdate}}</view>
				<view class="row-col col2">{{item.nums}}</view>
				<view class="row-col col3">{{item.sumBAIL}}</view>
				<view class="row-col col4" :class="item.nums > 0?'link':''" @click="showRecord(item)">{{item.realBail}}</view>
				<view class="row-col col5">0</view>
				<view class="row-col col6">{{item.loss}}</view>
			 </view>
			 <view class="table-row">
				<view class="row-col col1">合计</view>
				<view class="row-col col2">{{sum.nums}}</view>
				<view class="row-col col3">{{sum.sumBAIL}}</view>
				<view class="row-col col4">{{sum.realBail}}</view>
				<view class="row-col col5">0</view>
				<view class="row-col col6">{{sum.loss}}</view>
			 </view>
		 </view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				records:[],
				tabIndex:0,
				types:[
					{val:0,name:'本周'},
					{val:1,name:'上周'}
				],
				tabs:[
					{clevel:1,name:'分公司'},
					{clevel:2,name:'股东'},
					{clevel:3,name:'总代理'},
					{clevel:4,name:'代理'},
					{clevel:99,name:'普通会员'}
				],
				userno:'',
				clevel:'',
				orgtype:'',
				userinfo:{},
				sum:{
					nums:0,
					sumBAIL:0,
					realBail:0,
					loss:0,
					comm:0
				}
			}
		},
		onLoad(option) {
			this.userinfo = JSON.parse(uni.getStorageSync('userinfo'))
			this.userno = option.userno || this.userinfo.userno
			this.clevel = option.clevel || this.userinfo.clevel
			this.orgtype = option.orgtype || this.userinfo.orgtype
			this.loadData()
			
		},
		methods: {
			showRecord(item){
				uni.navigateTo({
					url:'./order?userno='+this.userno + '&orgtype='+this.orgtype+'&fdate='+item.fdate
				})
			},
			query(item){
				this.tabIndex = item.val
				this.loadData()
			},
			loadData(){
				this.records = []
				this.sum={
					nums:0,
					sumBAIL:0,
					realBail:0,
					loss:0,
					comm:0
				}
				let para = {
					iType:this.tabIndex,
					userno:this.userno
				}
				this.$http.post("/Query/SubSumOrdList",para,res => {
					this.records = res.rData || []
					this.records.forEach(item=>{
						this.sum.nums += item.nums
						this.sum.sumBAIL += item.sumBAIL
						this.sum.realBail += item.realBail
						this.sum.loss += item.loss
						this.sum.comm += item.comm
					})
				})
			},
			getStatus(clevel){
				if(this.orgtype == 2){
					return '普通会员'
				}else{
					let item = this.tabs.find(item=>item.clevel==clevel)
					return item.name
				}
			},
			goBack(){
				uni.navigateBack({
					delta:1
				})
			}
		}
	}
</script>

<style scoped lang="scss">
.report{
	width: 750upx;
	height: 100vh;
	background-color: #fff;
	margin: 0 auto;
	.title{
		display: flex;
		flex-direction: column;
		align-items: center;
		margin-top: 20upx;
		margin-bottom: 20upx;
		.sub-type{
			font-size: 32upx;
			font-weight: bold;
			margin-bottom: 10upx;
		}
	}
	.tabs{
		width: 670upx;
		padding-left: 40upx;
		padding-right: 40upx;
		display: flex;
		align-items: center;
		.tab-item{
			width:50%;
			text-align: center;
			border-bottom: 6upx solid #eeeeee;
			padding-bottom: 20upx;
		}
		.active{
			border-bottom: 6upx solid rgb(40,148,255);
		}
		
	}
	.table-data{
		width:100%;
		margin-top: 40upx;
		.table-row{
			display: flex;
			justify-content: space-between;
			align-items: center;
			border-bottom: 1upx solid #eeeeee;
			padding-top:20upx;
			padding-bottom:20upx;
			.row-col{
				text-align: center;
				font-size: 13px;
			}
			.col1{
				width: 150upx;
			}
			.col2{
				width: 100upx;
			}
			.col3{
				width: 120upx;
			}
			.col4{
				width: 120upx;
			}
			.link{
				color:rgb(40,148,255);
				cursor: pointer;
			}
			.col5{
				width: 100upx;
			}
			.col6{
				width: 120upx;
			}
		}
	}
	 
}
</style>
