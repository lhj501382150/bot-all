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
			<view class="link-btn">{{link.title}}</view>
		</view>
		<view class="row">
			<view class="num-item" v-for="(item,index) in result.data" :key="index" :class="'color'+item">
				{{item}}
			</view>
		</view>
		<view class="row">
			<view class="draw-time">202512期</view>
			<view class="draw-time">封盘：<text style="color: red;">{{fptime}}</text></view>
			<view class="draw-time">开奖：<text style="color: blue;">{{kjtime}}</text></view>
		</view>
		<view class="row">
			<view class="draw-time">信用额度：0</view>
			<view class="draw-time">未结金额：<text style="color: blue;">0</text></view>
			<view class="draw-time">今日输赢：<text style="color: red;">0</text></view>
		</view>
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
				kjtime:0
			}
		},
		methods: {
			goRecord(){
				uni.navigateTo({
					url:''
				})
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
	.row{
		display: flex;
		justify-content: space-between;
		align-items: center;
		width: 100%;
		height: 80upx;
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
	}
}
</style>
