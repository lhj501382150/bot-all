<script>
	import initPermission from './utils/permission'
	export default {
		onLaunch: function() {
			initPermission()
		},
		onShow: function() {
			const leaveTime = uni.getStorageSync('leave') || 0
			const curTime = new Date().getTime()
			if(leaveTime > 0 && curTime -leaveTime > 1000 * 60 * 30){
				uni.removeStorageSync("userinfo")
				uni.removeStorageSync("Token")
				uni.removeStorageSync("userno")
				uni.removeStorageSync("mask")
			}
			const mask = uni.getStorageSync('mask')
			if(!mask){
				uni.reLaunch({
					url:'/pages/index/index'
				})
			}
		},
		onHide: function() {
			uni.setStorageSync('leave',new Date().getTime())
		},
		data(){
			return {
				leaveTime:0
			}
		}
	}
</script>

<style lang="scss">
	/*每个页面公共css */
	*{
		padding: 0upx;
		margin: 0upx;
	}
	page{
		background-color: rgb(241,241,241);
	}
</style>
