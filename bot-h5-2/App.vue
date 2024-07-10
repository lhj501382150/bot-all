<script>
	import initPermission from './utils/permission'
	export default {
		onLaunch: function() {
			initPermission()
		},
		onShow: function() {
			// console.log('App Show')
			const mask = uni.getStorageSync('mask')
			if(!mask){
				uni.reLaunch({
					url:'/pages/index/index'
				})
			}
		},
		onHide: function() {
			// console.log('App Hide')
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
