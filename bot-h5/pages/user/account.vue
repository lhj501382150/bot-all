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
		<view class="row" v-if="tabIndex == 99 || tabIndex==userinfo.clevel + 1">
			<button class="add-btn" @click="open" size="mini">新建{{getStatus(tabIndex)}}</button>
		</view>
		<scroll-view scroll-y="true" @scrolltolower="scrolltolower" style="height: 80%;"
		        @refresherrefresh="getRefresherrefresh" :refresher-enabled="false" :refresher-triggered="refresherTriggered"
		        refresher-background="transparent">
			<view class="record-list">
				 <view class="record-item" v-for="(item,index) in records" :key="index">
					  <view class="user-info">
					  	<view class="left">
					  		<image src="../../static/images/user/tou.png" mode="scaleToFill"></image>
					  		<view class="user-name">
					  			<view class="row-item">用户ID：{{item.userno}}</view>
					  			<view class="row-item">昵称：{{item.nickname}}</view>
					  			<view class="row-item" v-if="item.orgtype==2">普通会员</view>
								<view class="row-item" v-else>{{getStatus(tabIndex)}}</view>
					  		</view>
					  	</view>
					  	<view class="right">
					  		<view class="row-item">信用额度：{{item.enable}}</view>
					  		<button type="primary" size="mini" @click="editBalance(item)" v-if="item.parentno == userinfo.userno">修改信用额度</button>
					  	</view>
					  </view>
					  <view class="report-row">
					  	<view>本周下注金额:{{item.curBail}}</view>
					  	<view>本周盈亏:{{item.curLoss}}</view>
					  </view>
					  <view class="report-row">
					  	<view>上周下注金额:{{item.curBail}}</view>
					  	<view>上周盈亏:{{item.curLoss}}</view>
					  </view>
				 </view>
			</view>
			 
		</scroll-view>
		
		 <uni-popup ref="scorePopup" type="center" background-color="#fff">
		 			 <view class="form">
		 				 <view class="form-title">修改信用额度</view>
						 <view class="form-sub-title">操作账号：{{scoreForm.userno}}</view>
		 			 	<uni-forms ref="scoreForm" :modelValue="scoreForm" :rules="scoreFormRules" >
		 			 		<uni-forms-item  name="type">
		 			 			<view class="radio-box">
									<radio-group @change="radioChange">
										<label>
											<radio value="1" :checked="scoreForm.type==1"/><text>上分</text>
										</label>
										<label>
												<radio value="2" :checked="scoreForm.type==2"/><text>下分</text>
										</label>
									</radio-group>
								</view>
		 			 		</uni-forms-item>
		 			 		<uni-forms-item label="分数" name="money">
		 			 			<uni-easyinput type="number" v-model="scoreForm.money" placeholder="请输入分数"/>
		 			 		</uni-forms-item>
		 			 	</uni-forms>
		 			 	 
		 			 	<view class="form-btn">
		 					<button class="cal-btn" size="mini" @click="closeScorePopup">取消</button>
		 					<button class="sub-btn"  size="mini" @click="submitScore">确认</button>
		 				</view>
		 			 </view>
		 </uni-popup>
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
					{clevel:99,name:'普通会员'}
				],
				tabIndex:4,
				records:[],
				search:{
					orgtype:'',
					userno:'',
					clevel:'',
					pageIdx:0,
					pageSize:10
				},
				totalPage:1,
				totalCount:0,
				refresherTriggered:false,
				clevelName:'',
				scoreForm:{
					type:1,
					userno:'',
					money:''
				},
				scoreFormRules: {
					 money: {
					 	rules: [
					 		{required: true,errorMessage: '请输入分数'}
					 	]
					 }
					 
				},
			}
		},
		onLoad() {
			this.userno = uni.getStorageSync("userno")
			this.userinfo = JSON.parse(uni.getStorageSync('userinfo'))
			this.tabIndex = this.userinfo.clevel + 1
			this.loadData()
		},
		methods: {
			radioChange(value){
				this.scoreForm.type= value
			},
			closeScorePopup(){
				this.$refs.scorePopup.close()
				this.scoreForm.userno = ''
				this.scoreForm.type = 1
			},
			editBalance(item){
				this.scoreForm.userno = item.userno
				this.$refs.scorePopup.open()
			},
			submitScore(){
				this.$refs.scoreForm.validate().then(res=>{
					const para = Object.assign({},this.scoreForm)
					 
					this.$http.post('/User/AddAccount',para,(res=>{
						if(res.iCode ==0){
							this.scoreForm.userno = ''
							this.scoreForm.type = 1
							this.scoreForm.money = ''
							 uni.showToast({
							 	title:'操作成功',
							 	icon:'success',
								duration:3000
							 })
							 this.closeScorePopup()
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
			findData(item){
				this.tabIndex = item.clevel
				this.search.pageIdx = 0
				this.totalPage = 1
				this.totalCount = 0
				this.records = []
				this.loadData()
			},
			scrolltolower() {
				if (this.search.pageIdx > this.totalPage){
					return
				}
				this.loadData()
			},
			//下拉刷新
			getRefresherrefresh(){
				// this.refresherTriggered = true
				this.search.pageIdx = 0
				this.totalPage = 1
				this.totalCount = 0
				this.records = []
				this.loadData()
			},
			loadData(){
				this.search.userno = this.userinfo.userno
				if(this.tabIndex == 99){
					this.search.orgtype = 2
					this.search.clevel = null
				}else{
					this.search.clevel = this.tabIndex
					this.search.orgtype = 1
				}
				this.$http.post("/Query/SubFirmList",this.search,res => {
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
					if(this.tabIndex == 99){
						para.orgtype = 2
					}else{
						para.orgtype = 1
					}
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
							 this.getRefresherrefresh()
							 this.close()
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
	.record-list{
		.record-item{
			margin-bottom:20upx;
			padding:20upx;
			border-bottom:5upx solid #eeeeee;
			.user-info{
				display: flex;
				justify-content: space-between;
				align-items: center;
				margin-bottom:10upx;
				.left{
					width: 60%;
					display: flex;
					justify-content: flex-start;
					align-items: center;
					image{
						width: 100upx;
						height: 100upx;
					}
					.user-name{
						margin-left: 30upx ;
					}
				}
				.right{
					padding-right:20upx;
					.row-item{
						margin-bottom:20upx;
					}
				}
			}
			.report-row{
				display: flex;
				justify-content: space-between;
				align-items: center;
				margin-top:10upx;
				font-size:26upx;
				padding: 5upx 20upx 0upx 20upx;
			}
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
		 .form-sub-title{
			 font-size: 14px;
			 text-align: center;
			 width:100%;
			 margin-bottom:10px;
		 }
		 .radio-box{
			 text-align: center;
			 width:100%;
			 margin-top: 20upx;
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
