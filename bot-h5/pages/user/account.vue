<template>
	<view class="account">
		<uni-nav-bar left-icon="left"  title="代理中心" background-color="rgb(40,148,255)" color="#fff" :border="false" @clickLeft="goBack"></uni-nav-bar>
		<view class="row">
			<view class="text">当前账号： {{getStatus(userinfo.clevel)}} - {{userno}}</view>
			<view class="text" v-if="userinfo.clevel > 1">所属{{getStatus(userinfo.clevel - 1)}}: {{userinfo.parentNo}}</view>
		</view>
		<view class="tab-bar">
			<view class="tab-item"  :class="item.clevel == tabIndex ? 'active':''" v-for="(item,index) in tabs" :key="index" @click="findData(item)" v-if="item.clevel > userinfo.clevel">
				{{item.name}}
			</view>
		</view>
		<view class="row">
			<button class="add-btn" @click="open" size="mini" v-if="tabIndex == 99 || tabIndex==userinfo.clevel + 1">新建{{getStatus(tabIndex)}}</button>
		</view>
		<scroll-view scroll-y="true" @scrolltolower="scrolltolower" style="height: 80%;"
		        @refresherrefresh="getRefresherrefresh" :refresher-enabled="false" :refresher-triggered="refresherTriggered"
		        refresher-background="transparent">
			<view class="record-list">
				 <view class="record-item" v-for="(item,index) in records" :key="index" @click="showDetail(item)">
					  <view class="head">
						  {{item.title}}
					  </view>
					  <view class="time">
						  {{item.nottime}}
					  </view>
				 </view>
			</view>
		</scroll-view>
		
		 
		 <uni-popup ref="popup" type="bottom" background-color="#fff">
			 <view class="form">
				 <view class="form-title">新建{{getStatus(tabIndex)}}</view>
			 	<uni-forms ref="form" :modelValue="formData" :rules="rules" >
			 		<uni-forms-item label="账号" name="userno">
			 			<uni-easyinput type="text" prefixIcon="auth" v-model="formData.userno" placeholder="请输入账号"/>
			 		</uni-forms-item>
			 		<uni-forms-item label="昵称" name="nickname">
			 			<uni-easyinput type="text" prefixIcon="person" v-model="formData.nickname" placeholder="请输入昵称"/>
			 		</uni-forms-item>
					<uni-forms-item label="密码" name="paypwd">
						<uni-easyinput type="password" prefixIcon="locked" v-model="formData.paypwd" placeholder="请输入密码"/>
					</uni-forms-item>
			 		
			 	</uni-forms>
			 	 
			 	<view class="form-btn">
					<button class="cal-btn" @click="close">取消</button>
					<button class="sub-btn" @click="submit">确认创建</button>
				</view>
			 </view>
		 </uni-popup>
	</view>
</template>

<script>
	import md5 from '@/utils/md5.js'
	export default {
		data() {
			return {
				formData:{
					userno :'',
					nickname:'',
					paypwd:''
				},
				rules: {
					userno: {
						rules: [
							{required: true,errorMessage: '请输入账号'}
						]
					},
					 nickname: {
					 	rules: [
					 		{required: true,errorMessage: '请输入昵称'}
					 	]
					 },
					 paypwd: {
					 	rules: [
					 		{required: true,errorMessage: '请输入密码'}
					 	]
					 },
				},
				userno:'',
				userinfo:{},
				tabs:[
					{clevel:1,name:'分公司'},
					{clevel:2,name:'股东'},
					{clevel:3,name:'总代理'},
					{clevel:4,name:'代理'},
					{clevel:99,name:'会员'}
				],
				tabIndex:4,
				records:[],
				search:{
					pageIdx:0,
					pageSize:10
				},
				totalPage:1,
				totalCount:0,
				refresherTriggered:false,
				clevelName:''
			}
		},
		onLoad() {
			this.userno = uni.getStorageSync("userno")
			this.userinfo = JSON.parse(uni.getStorageSync('userinfo'))
			this.userinfo.clevel = 2
			this.tabIndex = this.userinfo.clevel + 1
		},
		methods: {
			findData(item){
				this.tabIndex = item.clevel
			},
			scrolltolower() {
				if (this.search.pageIdx > this.totalPage){
					return
				}
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
				this.$http.post("/Notice/GetList",this.search,res => {
					this.records = [...this.records,...res.rData]
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
			getStatus(clevel){
				if(clevel == 99){
					return '普通会员'
				}else{
					let item = this.tabs.find(item=>item.clevel==clevel)
					return item.name
				}
			},
			open() {
				this.$refs.popup.open()
			},
			close() {
				this.$refs.popup.close()
			},
			submit(){
				this.$refs.form.validate().then(res=>{
					const para = Object.assign({},this.formData)
					para.tjno = this.userno
					para.paypwd = md5(this.formData.userno + this.formData.paypwd)
					this.$http.post('/User/AddAccount',para,(res=>{
						if(res.iCode ==0){
							this.formData.userno = ''
							this.formData.nickname = ''
							this.formData.paypwd = ''
							 uni.showToast({
							 	title:'添加成功',
							 	icon:'success',
								duration:3000
							 })
							 setTimeout(this.close,2000)
						}else{
							uni.showToast({
								title:res.sMsg,
								icon:'error'
							})
						}
					}))
				}).catch(err =>{
					console.log(err);
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
.account{
	width: 750upx;
	height: 100vh;
	background-color: #fff;
	.row{
		padding: 40upx;
		.add-btn{
			width: 400upx;
			background-color: rgb(40,148,255);
			color: #fff;
		}
		.text{
			margin-top: 40upx;
		}
	}
	.tab-bar{
		display: flex;
		width: 670upx;
		padding-left: 40upx;
		padding-right: 40upx;
		.tab-item{
			width:150upx;
			height:80upx;
			line-height: 80upx;
			text-align: center;
			border-bottom:5upx solid #eeeeee;
		}
		.active{
			border-bottom: 5upx solid rgb(40,148,255);
		}
	}
	.form{
		 padding: 40upx;
		 .form-title{
			 font-size: 16px;
			 font-weight:600;
			 text-align: center;
			 width:100%;
			 margin-bottom:10px;
		 }
		 .form-btn{
			width: 100%;
			display: flex;
			justify-content: space-around;
		 }
		 .sub-btn{
			 width: 35%;
			 background-color: rgb(40,148,255);
			 color: #fff;
			 border-radius: 20upx;
		 }
		 .cal-btn{
			 width: 35%;
			 background-color: #fff;
			 color: #000;
			 border-radius: 20upx;
		 }
	}
}
</style>
