<template>
	<view class="qmbd-game">
		<uni-nav-bar  title="全民宝斗" background-color="rgb(250,81,81)" color="#fff" left-icon="back" :border="false" right-text="规则" @clickLeft="goBack" @clickRight="goRule"></uni-nav-bar>
		<view class="tab-bar">
			<view class="tab-item" :class="tabIndex==0 ? 'active':''">宝斗</view>
			<view class="tab-item" :class="tabIndex==1 ? 'active':''" @click="goRecord">下注记录</view>
		</view>
		<view class="row">
			<view class="draw-time red">{{result.DRAW}}</view>
			<view class="draw-num">
				<view class="tips">{{result.NUM}}</view>
				<view class="tips">{{result.STR}}</view>
			</view>
			<view class="link-btn" @click="goUrl">{{link.title}}</view>
		</view>
		<view class="row">
			<view class="num-item" v-for="(item,index) in result.data" :key="index" :class="'color'+item">
				{{item}}
			</view>
		</view>
		<view class="row">
			<view class="draw-time red">202512期</view>
			<view class="draw-time">封盘：<text style="color: red;">{{fptime}}</text></view>
			<view class="draw-time">开奖：<text style="color: blue;">{{kjtime}}</text></view>
		</view>
		<view class="row qing">
			<view class="draw-time">信用额度：0</view>
			<view class="draw-time">未结金额：<text style="color: blue;">0</text></view>
			<view class="draw-time">今日输赢：<text style="color: red;">0</text></view>
		</view>
		<view class=""></view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				tabIndex:0,
				link:{
					title:'澳洲10在线直播',
					src:'https://m.228168d.com/html/aozxy10/index.html'
				},
				result:{
					DRAW:'2110322',
					NUM:1,
					STR:'入',
					data:['01','02','03','04','05','06','07','08','09','10']
				},
				fptime:0,
				kjtime:0,
				items:[
					{index:'1',name:'虎入角',rate:1,check:false},
					{index:'2',name:'入正卡虎',rate:1,check:false},
					{index:'3',name:'入正卡龙',rate:1,check:false},
					{index:'4',name:'龙入角',rate:1,check:false},
					{index:'5',name:'虎正卡入',rate:1,check:false},
					{index:'6',name:'虎同',rate:1,check:false},
					{index:'7',name:'入同',rate:1,check:false},
					{index:'8',name:'入串',rate:1,check:false},
					{index:'9',name:'龙同',rate:1,check:false},
					{index:'10',name:'龙正卡入',rate:1,check:false},
					{index:'11',name:'虎正卡出',rate:1,check:false},
					{index:'12',name:'虎串',rate:1,check:false},
					{index:'13',name:'出同',rate:1,check:false},
					{index:'14',name:'出串',rate:1,check:false},
					{index:'15',name:'龙串',rate:1,check:false},
					{index:'16',name:'龙正卡出',rate:1,check:false},
					{index:'17',name:'虎出角',rate:1,check:false},
					{index:'18',name:'出正卡虎',rate:1,check:false},
					{index:'19',name:'出正卡龙',rate:1,check:false},
					{index:'20',name:'龙出角',rate:1,check:false},
					{index:'21',name:'虎股',rate:3,check:false},
					{index:'22',name:'入股',rate:3,check:false},
					{index:'23',name:'龙股',rate:3,check:false},
					{index:'24',name:'出股',rate:3,check:false}
				]
			}
		},
		methods: {
			goRecord(){
				uni.navigateTo({
					url:''
				})
			},
			goUrl(){
				window.location.href = this.link.src
			},
			goBack(){
				uni.switchTab({
					url:'/pages/home/home'
				})
			},
			goRule(){
				
			}
		}
	}
</script>

<style scoped lang="scss">
.qmbd-game{
	width: 750upx;
	min-height: 100vh;
	background-color: #fff;
	.tab-bar{
		width: 100%;
		height: 100upx;
		display: flex;
		justify-content: space-between;
		align-items: center;
		.tab-item{
			width: 50%;
			height: 80upx;
			line-height: 80upx;
			text-align: center;
			border-bottom: 6upx solid #e2e2e2;
		}
		.active{
			color:rgb(250,81,81);
			border-bottom: 6upx solid rgb(250,81,81);
		}
	}
	.qing{
		background-color: #d7f0ff;
	}
	.row{
		display: flex;
		justify-content: space-between;
		align-items: center;
		width: 100%;
		height: 80upx;
		padding-top: 20upx;
		padding-bottom: 20upx;
		border-bottom: 2upx solid #e2e2e2;
		.draw-time{
			width: 33%;
			text-align: center;
		}
		.draw-num{
			width: 27%;
			display: flex;
			align-items: center;
			justify-content: flex-end;
			.tips{
				width: 40upx;
				height: 40upx;
				line-height: 40upx;
				border-radius: 50%;
				background-color: rgb(250,81,81);
				color: #fff;
				text-align: center;
				font-size: 26upx;
				margin-right: 20upx;
			}
		}
		.link-btn{
			width: 250upx;
			height: 60upx;
			line-height: 60upx;
			background-color: rgb(250,41,41);
			color: #fff;
			margin-right: 20upx;
			padding-left: 30upx;
			padding-right: 30upx;
			border-radius: 20upx;
		}
		.red{
			color: rgb(250,81,81);
		}
		.num-item{
			width: 59upx;
			height: 59upx;
			margin-left: 10upx;
			margin-right: 6upx;
			border-radius: 50%;
			display: flex;
			justify-content: center;
			align-items: center;
			color: #fff;
		}
		.color01{
			background-color:#FFD700;
		}
		.color02{
			background-color:#00BFFF;
		}
		.color03{
			background-color:#8B4513;
		}
		.color04{
			background-color:#FFA500;
		}
		.color05{
			background-color:#48D1CC;
		}
		.color06{
			background-color:#7B68EE;
		}
		.color07{
			background-color:#778899;
		}
		.color08{
			background-color:#DC143C;
		}
		.color09{
			background-color:#FA8072;
		}
		.color10{
			background-color:#32CD32;
		}
	}
}
</style>
