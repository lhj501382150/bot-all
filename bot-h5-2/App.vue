<script>
	import initPermission from './utils/permission'
	export default {
		onLaunch: function() {
			initPermission()
		},
		onShow: function() {
			// console.log('App Show')
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
