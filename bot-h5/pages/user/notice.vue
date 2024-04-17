<template>
	<view class="notcie">
		<uni-nav-bar left-icon="left"  title="平台公告" background-color="rgb(40,148,255)" color="#fff" :border="false" @clickLeft="goBack"></uni-nav-bar>
		<scroll-view scroll-y="true" @scrolltolower="scrolltolower" style="height: 95%;"
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
	</view>
</template>

<script>
	export default {
		data() {
			return {
				records:[],
				search:{
					pageIdx:0,
					pageSize:10
				},
				totalPage:1,
				totalCount:0,
				refresherTriggered:false,
			}
		},
		onLoad() {
			this.records = []
			this.loadData()
		},
		methods: {
			showDetail(item){
				let no = item.noticeno
				uni.navigateTo({
					url:'./noticeDetail?no='+ no
				})
			},
			scrolltolower() {
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
			goBack(){
				uni.switchTab({
					url:'/pages/user/user'
				})
			}
		}
	}
</script>

<style scoped lang="scss">
.notcie{
	width: 750upx;
	height: 100vh;
	.record-list{
		padding: 20upx;
		.record-item{
			background-color: #fff;
			padding: 20upx;
			margin-bottom: 20upx;
			.head{
				font-size: 32upx;
			}
			.time{
				margin-top: 20upx;
				font-size: 26upx;
				color: #787878;
			}
		}
	}
}
</style>
