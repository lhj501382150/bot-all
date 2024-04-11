<template>
	<view class="account">
		<uni-nav-bar left-icon="left"  title="代理中心" background-color="rgb(40,148,255)" color="#fff" :border="false" @clickLeft="goBack"></uni-nav-bar>
		 <view class="row">
			 <button class="add-btn" @click="open">新建普通会员</button>
			 <view class="text">所属会员：</view>
		 </view>
		 <uni-popup ref="popup" type="bottom" background-color="#fff">
			 <view class="form">
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
				}
			}
		},
		onLoad() {
		},
		methods: {
			open() {
				this.$refs.popup.open()
			},
			close() {
				this.$refs.popup.close()
			},
			submit(){
				this.$refs.form.validate().then(res=>{
					const para = Object.assign({},this.formData)
					this.$http.post('/api/User/AddAccount',para,(res=>{
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
	.form{
		 padding: 40upx;
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
