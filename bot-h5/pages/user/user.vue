<template>
	<view class="user">
		<uni-nav-bar  title="个人中心" background-color="rgb(40,148,255)" color="#fff" :border="false" right-text="客服" @clickRight="goWeb"></uni-nav-bar>
		<view class="user-info">
			<view class="left">
				<image src="../../static/images/user/tou.png" mode="scaleToFill"></image>
			</view>
			<view class="right">
				<view class="name">{{user.USERNAME}}</view>
				<view class="id">ID:{{user.USERNO}}</view>
				<view class="level">等级:{{user.ORGTYPE}}</view>
			</view>
		</view>
		<view class="user-money">
			账户余额：{{user.BALANCE}}
		</view>
		
		<view class="user-menu">
			<view class="menu-item" v-for="(item,index) in menus" :key="index">
				<view class="menu-title" >
					<img :src="item.icon"></img>
					{{item.name}}
				</view>
				<uni-icons type="right" color="#848484" size="22"></uni-icons>
			</view>
		</view>
		<view class="logout">
			<view class="title">退出登录</view>
			<uni-icons type="right" color="#000" size="22"></uni-icons>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				user:{
					USERNAME:'111',
					USERNO:'11',
					ORGTYPE:'11',
					BALANCE:0
				},
				menus:[
					{name:'交易记录',icon:'../../static/images/user/record.png',path:''},
					{name:'开奖公告',icon:'../../static/images/user/kjgg.png',path:''},
					{name:'代理中心',icon:'../../static/images/user/dlzx.png',path:''},
					{name:'投注记录',icon:'../../static/images/user/tzjl.png',path:''},
					{name:'平台公告',icon:'../../static/images/user/ptgg.png',path:''},
					{name:'报表查询',icon:'../../static/images/user/bbcx.png',path:''},
					{name:'修改密码',icon:'../../static/images/user/pwd.png',path:''},
				]
			}
		},
		methods: {
			//客服链接
			goWeb(){
				
			}
		}
	}
</script>

<style scoped lang="scss">
.user{
	width: 750upx;
	height: 100%;
	.user-info{
		display: flex;
		align-items: center;
		height: 200upx;
		background-color: #fff;
		.left{
			width: 30%;
			display: flex;
			justify-content: center;
			align-items: center;
			image{
				width: 100upx;
				height: 100upx;
			}
		}
		.right{
			width: 70%;
			font-size: 26upx;
			.name{
				font-size: 32upx;
				font-weight: 600;
				margin-bottom: 20upx;
			}
		}
	}
	.user-money{
		height: 80upx;
		line-height: 80upx;
		text-align: center;
		margin-top: 4upx;
		background-color: #fff;
	}
	.user-menu{
		margin-top: 40upx;
		.menu-item{
			display: flex;
			justify-content: space-between;
			align-items: center;
			padding: 10upx 40upx;
			background-color: #fff;
			margin-top: 4upx;
			height: 80upx;
			.menu-title{
				display: flex;
				align-items: center;
				img{
					width: 50upx;
					height: auto;
					margin-right: 20upx;
				}
			}
		}
	}
	.logout{
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 10upx 40upx;
		background-color: #fff;
		margin-top: 100upx;
		height: 80upx;
	}
}
</style>
