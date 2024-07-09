<template>
	<view class="qmbd-game">
		<uni-nav-bar  title="四方通宝" background-color="rgb(250,81,81)" color="#fff" left-icon="back" :border="false" right-text="规则" @clickLeft="goBack" @clickRight="openRule"></uni-nav-bar>
		<view class="tab-bar">
			<view class="tab-item" :class="tabIndex==0 ? 'active':''">牛牛</view>
			<view class="tab-item" :class="tabIndex==1 ? 'active':''" @click="goRecord">下注记录</view>
		</view>
		<view class="row">
			<view class="draw-time red">{{result.ISSUE-1}}期</view>
			<view class="link-btn" @click="goUrl">{{link.title}}</view>
		</view>
		<view class="row">
			<view class="num-item" v-for="(item,index) in result.data" :key="index" :class="'color'+item">
				{{item}}
			</view>
		</view>
		<view class="row">
			<view class="draw-time red">{{result.ISSUE}}期</view>
			<view class="draw-time">封盘：<text style="color: red;">{{formatTime(fptime)}}</text></view>
			<view class="draw-time">开奖：<text style="color: blue;">{{formatTime(kjtime)}}</text></view>
		</view>
		<view class="row qing">
			<view class="draw-money">现金额度：{{user.enable}}</view>
			<view class="draw-money">未结金额：<text style="color: blue;">{{user.freeze}}</text></view>
			<view class="draw-money">今日输赢：<text style="color: red;">{{user.curLoss}}</text></view>
		</view>
		<view class="game-box">
			 <view class="game-item" v-for="(item,index) in items" :key="index" :class="item.check ? 'active':''" @click="chooseItem(item)">
				 <view class="text">{{item.name}}</view>
			 </view>
		</view>
		<view class="form">
			<uni-forms ref="form" :modelValue="formData" :rules="rules">
				<!-- <uni-forms-item  name="type">
					<radio-group @change="radioChange">
						<label>
							<radio value="1" :checked="formData.type==1"/><text>平投</text>
						</label>
						<label>
							<radio value="2" :checked="formData.type==2"/><text>倍投</text>
						</label>
					</radio-group>
				</uni-forms-item> -->
				<uni-forms-item name="type" required>
					<uni-data-checkbox v-model="formData.type" :localdata="types" />
				</uni-forms-item>
				<uni-forms-item  name="money" required>
					<uni-easyinput type="number" v-model="formData.money" placeholder="请输入投注金额" />
				</uni-forms-item>
			</uni-forms>
			<button :class="isStop?'btn1':'btn'" @click="submit">{{isStop?'封盘中':'确认下注'}}</button>
		</view>
		<view class="result">
			<view class="result-item" :class="'color'+item.no" v-for="(item,index) in results" :key="index">
				{{item.status}}
			</view>
		</view>
		
		<uni-popup ref="errPopup" type="dialog">
			<uni-popup-dialog mode="base" :duration="2000" :before-close="true"  @close="refresh"  @confirm="refresh">
			{{errMsg}}
			</uni-popup-dialog>
		</uni-popup>
		<uni-popup ref="popup" type="dialog">
			<uni-popup-dialog mode="base" :duration="2000" :before-close="true" @close="close" @confirm="confirm">
			<text v-if="formData.type==1">平投</text>
			<text v-if="formData.type==2">倍投</text>
			共选择了{{formData.selected.length}}注，共计{{formData.money * formData.selected.length}}元
			</uni-popup-dialog>
		</uni-popup>
		<uni-popup ref="rulePopup" :mask-click="false" background-color="#fff" borderRadius="10upx 10upx 0upx 0upx">
			<view class="rule-content">
				<view>牛牛规则</view>
				<view>玩法：庄，闲一，闲二，闲三，闲四，闲五</view>
				<view>串、角1:1/同、念1:2/古1:3</view>
				<view>念角下注要分开（没有明宝）</view>
				<!-- <view>中奖平台抽水4%</view> -->
				<view>开奖结果以1-2-3-4的先后排名为准，最靠近第一名的为最终开奖结果。</view>
				<view>例如：7-6-10-9-8-2-5-4-3-1</view>
				<view>开奖结果为（2龙）</view>
				
				<button @click="closeRule" class="pop-btn">关闭</button>
			</view>
			
		</uni-popup>
		<NoticeDialog :isShow="showNotice"></NoticeDialog>
	</view>
</template>

<script>
	import { getSecond,getCurTime } from '../../utils/util'
	import {botId, webSocketUrl} from '@/static/config/config.js'
	import NoticeDialog from '@/components/notice-dialog.vue'
	import md5 from '@/utils/md5.js'
	export default {
		components:{
			NoticeDialog
		},
		data() {
			return {
				user:{},
				tabIndex:0,
				link:{
					title:'澳洲10在线直播',
					src:'https://m.228168d.com/html/aozxy10/index.html'
				},
				result:{},
				fptime:0,
				kjtime:0,
				items:[
					{index:'1',name:'闲一',rate:1.96,rate1:0,check:false},
					{index:'2',name:'闲二',rate:2.92,rate1:1,check:false},
					{index:'3',name:'闲三',rate:2.92,rate1:1,check:false},
					{index:'4',name:'闲四',rate:1.96,rate1:0,check:false},
					{index:'5',name:'闲五',rate:2.92,rate1:1,check:false}
				],
				types:[
					{text:'平投',value:'1'},
					{text:'倍投',value:'2'}
				],
				formData:{
					money:'',
					type:'1',
					selected:[]
				},
				rules: {
					 money: {
					 	rules: [
					 		{required: true,errorMessage: '请输入人下注金额'}
					 	]
					 }
				},
				results:[],
				isStop:false,
				socketTask: null,
				// 确保websocket是打开状态
				is_open_socket: false,
				leftTime:0,
				timer:'',
				statusList:[
					{val:1,name:'入'},
					{val:2,name:'龙'},
					{val:3,name:'出'},
					{val:4,name:'虎'}
				],
				orgtype:'',
				errMsg:'',
				curStatus:false,
				showNotice:false,
				tempShowNotice:false
			}
		},
		onLoad() {
			this.getUserBalance()
			this.loadData()
			this.getShowNoticePara()
			this.connectSocketInit()
			let user = JSON.parse(uni.getStorageSync('userinfo'))
			this.orgtype = user.orgtype
		},
		beforeDestroy() {
			this.closeSocket()
		},
		methods: {
			radioChange(value){
				this.formData.type= value
			},
			getShowNoticePara(){
				let para = {
					sysid:11
				}
				try{
					this.$http.post('/Query/SysPara',para,res=>{
					  if(res.iCode==0){
						  this.tempShowNotice =  res.rData.sval =='Y'
					  }
					})
				}catch(e){
					this.tempShowNotice = false
				}
			},
			loadData(){
				this.results = []
				let para = {
					pageIdx:0,
					pageSize:50,
					userno :uni.getStorageSync('userno')
				}
				this.$http.post("/Query/ReustList",para,res => {
					let datas = res.rData || []
					datas.forEach(item=>{
						let temp ={
							no:item.bNo,
							status: this.getStatus(item.bNo)
						}
						this.results.push(temp)
					})
				})
			},
			getUserBalance(){
				let userno = uni.getStorageSync('userno')
				let para = {
					userNo : userno
				}
				this.$http.post('/Account/GetBalance',para,res=>{
					 this.user = res.rData || {}
				})
			},
			// 进入这个页面的时候创建websocket连接【整个页面随时使用】
			connectSocketInit() {
				let userno = uni.getStorageSync('userno')
				if(!userno){
					uni.navigateTo({
						url:'/pages/login/login'
					})
				}
				let pwd = md5(userno+userno)
				let url = webSocketUrl + 'niu/' + userno+"-" + pwd;
				this.socketTask = uni.connectSocket({
					url: url ,
					success(data) {
						console.log("websocket连接成功");
					},
				});
				// 消息的发送和接收必须在正常连接打开中,才能发送或接收【否则会失败】
				this.socketTask.onOpen((res) => {
					console.log("WebSocket连接正常打开中...！");
					this.is_open_socket = true;
					// 注：只有连接正常打开中 ，才能正常成功发送消息
					this.socketTask.send({
						data: "uni-app发送一条消息",
						async success() {
							// console.log("消息发送成功");
						},
					});
					// 注：只有连接正常打开中 ，才能正常收到消息
					this.socketTask.onMessage((res) => {
						console.log("收到服务器内容：" + res.data);
						try{
							const data = JSON.parse(res.data);
							if(data.status==1){
								clearTimeout(this.timer)
								this.getCurStatus()
								// console.log(this.curStatus)
								if(this.curStatus){
									this.isStop = false
								}else{
									this.isStop = true
								}
								this.showNotice = this.tempShowNotice
								this.result = JSON.parse(data.data)
								this.result.data = this.result.CODE.split(',') || []
								this.leftTime = getSecond(this.result.TIME) + 15
								this.fillTime()
								this.getUserBalance()
								this.loadData()
							}else if(data.status == 4){
								this.isStop = true
								this.fptime = 0
								this.kjtime = 30
								this.getShowNoticePara()
							}
						}catch(e){
							console.log('消息处理异常：' + e)
						}
						
					});
				})
				// 这里仅是事件监听【如果socket关闭了会执行】
				this.socketTask.onClose(() => {
					console.log("已经被关闭了")
				})
			},
			getCurStatus(){
				let curTime = parseInt(getCurTime())
				if(curTime >=55800 && curTime <= 65900){
					this.curStatus = false
				}else{
					this.curStatus = true
				}
			},
			startCount(){
				if(this.fptime > 0){
					this.fptime--
				}else{
					this.isStop= true
				}
				if(this.kjtime > 0){
					this.kjtime--
				}
				this.timer = setTimeout(this.startCount,1000)
			},
			fillTime(){
				this.fptime = this.leftTime - 30 < 0 ? 0 :this.leftTime - 30
				this.kjtime = this.leftTime
				this.startCount()
			},
			formatTime(time){
				if(time > 60){
					const min = parseInt(time / 60) 
					let sec = time % 60 
					if(sec < 10) sec = '0' + sec
					return min +  ':' + sec
				}else{
					if(time < 10){
						return '00:0' + time
					}else{
						return '00:' + time
					}
					
				}
			},
			// 关闭websocket【离开这个页面的时候执行关闭】
			closeSocket() {
				this.socketTask.close({
					success(res) {
						this.is_open_socket = false;
						console.log("关闭成功", res)
					},
					fail(err) {
						console.log("关闭失败", err)
					}
				})
			},
			openRule(){
				this.$refs.rulePopup.open()
			},
			closeRule(){
				this.$refs.rulePopup.close()
			},
			close(){
				this.$refs.popup.close()
			},
			refresh(){
				this.$refs.errPopup.close()
				window.location.reload()
			},
			confirm() {
				this.close()
				let orders = this.formData.selected.map(item=>{
					let para = {
						integRal:this.formData.money,
						ruId:item.name,
						type:this.formData.type
					}
					return para
				})
				let para = {
					botId:botId,
					dataId:this.result.ID,
					userId:uni.getStorageSync('userno'),
					userName:uni.getStorageSync("nickname"),
					orders:orders
				}
				
				this.$http.post('/Order/Order',para,(res=>{
					if(res.iCode ==0){
						this.formData.money = ''
						this.formData.type='1'
						this.formData.selected = []
						this.items.forEach(item=> item.check=false)
						this.getUserBalance()
						uni.showToast({
							title:'下注成功',
							icon:'success'
						})
					}else if(res.iCode == -100){
						 this.errMsg = res.sMsg
						 this.$refs.errPopup.open()
					}
				}))
				
			},
			submit(){
				if(this.isStop) return;
				if(this.orgtype==1){
					uni.showToast({
						title:'代理不能下注',
						icon:'error',
						duration:2000
					})
					return
				}
				if(this.formData.money <= 0){
					uni.showToast({
						title:'输入金额不正确',
						icon:'error',
						duration:2000
					})
					return
				}
				this.$refs.form.validate().then(res=>{
					let arr = this.items.filter(item=>item.check==true) || []
					if(arr.length == 0){
						uni.showToast({
							title:'请选择下注类型',
							icon:'error',
							duration:2000
						})
						return
					}
					this.formData.selected = arr
					this.$refs.popup.open()
				}).catch(err =>{
					console.log( err);
				})
			},
			getStatus(status){
				const item = this.statusList.find(item=> item.val==status) || {}
				return item.name
			},
			chooseItem(item){
				item.check = !item.check
			},
			goRecord(){
				uni.switchTab({
					url:'/pages/record/record'
				})
			},
			goUrl(){
				window.open(this.link.src,'_blank')
			},
			goBack(){
				uni.switchTab({
					url:'/pages/home/home'
				})
			}
		}
	}
</script>

<style scoped lang="scss">
.qmbd-game{
	width: 750upx;
	min-height: 100vh;
	background-color: #fff;
	.tab-bar{
		width: 100%;
		height: 100upx;
		display: flex;
		justify-content: space-between;
		align-items: center;
		.tab-item{
			width: 50%;
			height: 80upx;
			line-height: 80upx;
			text-align: center;
			border-bottom: 6upx solid #e2e2e2;
		}
		.active{
			color:rgb(250,81,81);
			border-bottom: 6upx solid rgb(250,81,81);
		}
	}
	.qing{
		background-color: #d7f0ff;
	}
	.row{
		display: flex;
		justify-content: space-between;
		align-items: center;
		width: 710upx;
		height: 80upx;
		padding: 20upx;
		border-bottom: 2upx solid #e2e2e2;
		margin:0 auto;
		.draw-time{
			width: 33%;
			text-align: center;
		}
		.draw-num{
			width: 27%;
			display: flex;
			align-items: center;
			justify-content: flex-end;
			.tips{
				width: 40upx;
				height: 40upx;
				line-height: 40upx;
				border-radius: 50%;
				background-color: rgb(250,81,81);
				color: #fff;
				text-align: center;
				font-size: 26upx;
				margin-right: 20upx;
			}
		}
		.link-btn{
			width: 250upx;
			height: 60upx;
			line-height: 60upx;
			background-color: rgb(250,41,41);
			color: #fff;
			margin-right: 20upx;
			padding-left: 30upx;
			padding-right: 30upx;
			border-radius: 20upx;
		}
		.red{
			color: rgb(250,81,81);
		}
		.num-item{
			width: 59upx;
			height: 59upx;
			margin-left: 10upx;
			margin-right: 6upx;
			border-radius: 50%;
			display: flex;
			justify-content: center;
			align-items: center;
			color: #fff;
		}
		.color01{
			background-color:#FFD700;
		}
		.color02{
			background-color:#00BFFF;
		}
		.color03{
			background-color:#8B4513;
		}
		.color04{
			background-color:#FFA500;
		}
		.color05{
			background-color:#48D1CC;
		}
		.color06{
			background-color:#7B68EE;
		}
		.color07{
			background-color:#778899;
		}
		.color08{
			background-color:#DC143C;
		}
		.color09{
			background-color:#FA8072;
		}
		.color10{
			background-color:#32CD32;
		}
	}
	.game-box{
		width: 724upx;
		display: flex;
		flex-wrap: wrap;
		justify-content: space-between;
		position: relative;
		background-color: #eee;
		margin:15upx auto;
		padding-bottom: 15upx;
		.game-item{
			width: 30%;
			height: 120upx;
			margin-top: 20upx;
			border-radius: 20upx;
			background-image: linear-gradient(to bottom,#8f91fa,#5500ff);
			display: flex;
			justify-content: center;
			align-items: center;
			font-size: 36upx;
			color: #ffffff;
			font-weight: 600;
			letter-spacing: 10upx;
		}
		.active{
			background-image: linear-gradient(to bottom,#91fa8b,#13ac02);
		}
	}
	.form{
		width: 670upx;
		border-top: 10upx solid #e2e2e2;
		padding: 40upx;
		margin: 0 auto;
		::v-deep .uni-easyinput__content{
			height: 100upx;
			font-size: 30upx;
			border: 4upx solid #f35458!important;
		}
		.btn{
			background-color: #f35458;
			color: #fff;
		}
		.btn1{
			background-color: #5e6262;
			color: #fff;
		}
	}
	.result{
		margin-top: 60upx;
		display: flex;
		justify-content: flex-start;
		flex-wrap: wrap;
		padding-left: 20upx;
		padding-right: 20upx;
		.result-item{
			width: 60upx;
			height: 60upx;
			border-radius: 50%;
			display: flex;
			justify-content: center;
			align-items: center;
			margin-bottom: 20upx;
			color: #fff;
			margin-left: 10upx;
		}
		.color1{
			background-color:#FFD700;
		}
		.color2{
			background-color:#00BFFF;
		}
		.color3{
			background-color:#32CD32;
		}
		.color4{
			background-color:#DC143C;
		}
	}
	.rule-content{
		width: 600upx;
		padding: 40upx;
		.pop-btn{
			margin-top: 100upx;
			width: 200upx;
			background-color: #f35458;
			color: #fff;
		}
	}
	
}
</style>
