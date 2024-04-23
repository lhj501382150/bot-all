<template>
	<view class="user">
		<uni-nav-bar  title="个人中心" background-color="rgb(40,148,255)" color="#fff" :border="false" right-text="客服" @clickRight="goWeb"></uni-nav-bar>
		<view class="user-info">
			<view class="left">
				<image src="../../static/images/user/tou.png" mode="scaleToFill"></image>
			</view>
			<view class="right">
				<view class="name">{{userinfo.nickname}}</view>
				<view class="id">ID: {{userinfo.userno}}</view>
				<view class="level" v-if="userinfo.orgtype==1">类型: {{getClevel(userinfo.clevel)}}</view>
				<view class="level" v-else>类型: 普通会员</view>
			</view>
		</view>
		<view class="user-money">
			账户余额：{{user.blance}}
		</view>
		
		<view class="user-menu">
			<view class="menu-item" v-for="(item,index) in menus" :key="index" @click="goPage(item.path)">
				<view class="menu-title" >
					<img :src="item.icon"></img>
					{{item.name}}
				</view>
				<uni-icons type="right" color="#848484" size="22"></uni-icons>
			</view>
		</view>
		<view class="logout" @click="logout">
			<view class="title">退出登录</view>
			<uni-icons type="right" color="#000" size="22"></uni-icons>
		</view>
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
				userinfo:{},
				user:{
					freeze:'',
					enable:'',
					blance:0
				},
				menus:[],
				clevels:[
					{clevel:1,name:'分公司'},
					{clevel:2,name:'股东'},
					{clevel:3,name:'总代理'},
					{clevel:3,name:'代理'},
					{clevel:-1,name:'普通会员'}
				],
			}
		},
		onShow() {
			this.getUserBalance()
			this.initMenus()
			this.userinfo = JSON.parse(uni.getStorageSync('userinfo'))
			this.userinfo.clevel = 1
		},
		methods: {
			getClevel(clevel){
				if(!clevel) clevel  = -1
				console.log(clevel)
				let item = this.clevels.find(item => item.clevel==clevel)
				if(item){
					return item.name
				}else{
					return '普通会员'
				}
			},
			initMenus(){
				if(this.userinfo.orgtype==1){
					this.menus = [
						{name:'交易记录',icon:'../../static/images/user/record.png',path:'./moneyRecord'},
						{name:'开奖公告',icon:'../../static/images/user/kjgg.png',path:'./result'},
						{name:'代理中心',icon:'../../static/images/user/dlzx.png',path:'./account'},
						{name:'投注记录',icon:'../../static/images/user/tzjl.png',path:'./order'},
						{name:'平台公告',icon:'../../static/images/user/ptgg.png',path:'./notice'},
						{name:'报表查询',icon:'../../static/images/user/bbcx.png',path:''},
						{name:'修改密码',icon:'../../static/images/user/pwd.png',path:'./updatePwd'},
					]
				}else{
					this.menus = [
						{name:'交易记录',icon:'../../static/images/user/record.png',path:'./moneyRecord'},
						{name:'开奖公告',icon:'../../static/images/user/kjgg.png',path:'./result'},
						{name:'投注记录',icon:'../../static/images/user/tzjl.png',path:'./order'},
						{name:'平台公告',icon:'../../static/images/user/ptgg.png',path:'./notice'},
						{name:'报表查询',icon:'../../static/images/user/bbcx.png',path:''},
						{name:'修改密码',icon:'../../static/images/user/pwd.png',path:'./updatePwd'},
					]
				}
			},
			/*获取系统参数*/
			getSysPara(){
				let para = {
					sysid:''
				}
				this.$http.post('/Query/SysPara',para,res=>{
					  
				})
			},
			getUserBalance(){
				let userno = uni.getStorageSync('userno')
				if(!userno){
					uni.navigateTo({
						url:'/pages/login/login'
					})
				}
				let para = {
					userNo : userno
				}
				this.$http.post('/Query/GetBalance',para,res=>{
					 this.user = res.rData
				})
			},
			logout(){
				uni.removeStorageSync('Token')
				uni.removeStorageSync('userinfo')
				uni.removeStorageSync('userno')
				uni.navigateTo({
					url:'/pages/login/login'
				})
			},
			goPage(path){
				uni.navigateTo({
					url:path
				})
			},
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
