<template>
	<view class="sys-popup">
		<uni-popup ref="sysPopup" :mask-click="false" background-color="#fff" borderRadius="10upx 10upx 0upx 0upx">
			<view class="content">
				<view class="heaer">
					系统公告
				</view>
				<view class="tips">系统维护中......</view>
			</view>
		</uni-popup>
	</view>
</template>

<script>
	import {systemid} from '@/static/config/config.js'
	import {encrypto,decrypto} from '@/utils/js-aox.js'
	export default {
		data() {
			return {
				
			}
		},
		mounted() { 
			this.initStatus()
			this.getDate()
		},
		methods: {
			getDate(){
				const time = Date.parse(new Date());
				let expireTime = time + 1000 * 60 * 60 * 24 * 30
				const str = encrypto(expireTime.toString(),88,16);
				console.log(str,'--------------------')
			},
			initStatus(){
				let expire = 0
				const time = Date.parse(new Date());
				try{
					expire = decrypto(systemid,88,16)
					const reg = /^\d+$/
					if(reg.test(expire)){
						console.log(expire,new Date(parseInt(expire)),time)
					}else{
						expire = 0
					}
				}catch(e){
					
				}
				if(expire < time){
					this.$refs.sysPopup.open()
				}else{
					this.$refs.sysPopup.close()
				}
			}
		}
	}
</script>

<style scoped lang="scss">
.content{
	width: 670upx;
	height: 50vh;
	.heaer{
		font-size: 36upx;
		font-weight: 600;
		margin-top: 50upx;
		border-bottom: 2upx solid #e2e2e2;
		text-align: center;
		padding-bottom: 20upx;
	}
	.tips{
		padding: 40upx;
	}
	
}
</style>
