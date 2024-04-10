<template>
	<view class="login">
		<uni-nav-bar  title="登录" background-color="rgb(40,148,255)" color="#fff" :border="false" right-text="客服" @clickRight="goWeb"></uni-nav-bar>
		<view class="content">
			<view class="title">
				全民宝斗
			</view>
			<view class="form">
				<uni-forms ref="form" :modelValue="formData" :rules="rules" >
					<uni-forms-item label="账号" name="userno">
						<uni-easyinput type="text" prefixIcon="auth" v-model="formData.userno" placeholder="请输入账号" />
					</uni-forms-item>
					<uni-forms-item label="密码" name="paypwd">
						<uni-easyinput type="password" prefixIcon="locked" v-model="formData.paypwd" placeholder="请输入密码"/>
					</uni-forms-item>
					
				</uni-forms>
				 
				<button class="btn" @click="submit">立即登录</button>
			</view>
		</view>
	</view>
</template>

<script>
	import md5 from '@/utils/md5.js'
	export default {
		data() {
			return {
				formData:{
					paypwd :'1234567',
					userno :'abc00001'
				},
				rules: {
					userno: {
						rules: [
							{required: true,errorMessage: '请输入账号'}
						]
					},
					 paypwd: {
					 	rules: [
					 		{required: true,errorMessage: '请输入密码'}
					 	]
					 }
				}
			}
		},
		onLoad() {
		},
		methods: {
			submit(){
				this.$refs.form.validate().then(res=>{
					const para = Object.assign({},this.formData)
					para.md5 = md5(this.formData.userno + this.formData.paypwd)
					this.$http.post('/api/User/Login',para,(res=>{
						console.log(res,'-------------')
						if(res.iCode ==0){
							uni.setStorageSync("Token",res.rData.token)
							uni.setStorageSync("userno",res.rData.userno)
							uni.switchTab({
								url:'/pages/home/home'
							})
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
			goWeb(){
				
			}
		}	 
	}
</script>

<style lang="scss" scoped>
.login{
	width: 750upx;
	height: 100vh;
	background: repeating-linear-gradient(0deg,#297ce2,#f9f2f2);
	.content{
		display: flex;
		flex-direction: column;
		align-items: center;
		.title{
			color: #297ce2;
			font-size: 36px;
			font-weight: 600;
			margin-top: 100upx;
		}
		.form{
			width: 670upx;
			margin-top: 100upx;
			::v-deep .uni-forms-item__label{
				color: #fff;
				font-size: 30upx;
				height: 100upx;
			}
			::v-deep .uni-easyinput__content{
				height: 100upx;
				font-size: 28upx;
				border-radius: 10upx;
			} 
			.btn{
				background: linear-gradient(90deg,#2894ff,#66b3ff);
				color: #fff;
				border-radius: 20upx;
				margin-top: 20upx;
			}
		}
	}
}
</style>
