<template>
	<view class="qmbd-game">
		<uni-nav-bar  title="全民宝斗" background-color="rgb(250,81,81)" color="#fff" left-icon="back" :border="false" right-text="规则" @clickLeft="goBack" @clickRight="goRule"></uni-nav-bar>
		<view class="tab-bar">
			<view class="tab-item" :class="tabIndex==0 ? 'active':''">宝斗</view>
			<view class="tab-item" :class="tabIndex==1 ? 'active':''" @click="goRecord">下注记录</view>
		</view>
		<view class="row">
			<view class="draw-time red">{{result.DRAW}}</view>
			<view class="draw-num">
				<view class="tips">{{result.NUM}}</view>
				<view class="tips">{{result.STR}}</view>
			</view>
			<view class="link-btn" @click="goUrl">{{link.title}}</view>
		</view>
		<view class="row">
			<view class="num-item" v-for="(item,index) in result.data" :key="index" :class="'color'+item">
				{{item}}
			</view>
		</view>
		<view class="row">
			<view class="draw-time red">202512期</view>
			<view class="draw-time">封盘：<text style="color: red;">{{fptime}}</text></view>
			<view class="draw-time">开奖：<text style="color: blue;">{{kjtime}}</text></view>
		</view>
		<view class="row qing">
			<view class="draw-time">信用额度：0</view>
			<view class="draw-time">未结金额：<text style="color: blue;">0</text></view>
			<view class="draw-time">今日输赢：<text style="color: red;">0</text></view>
		</view>
		<view class="game-box">
			<view class="g-b-1">
				<view class="game-box-1" :class="items[0].check ? 'active':''" @click="chooseItem(items[0])">{{items[0].name}}</view>
				<view class="game-box-2" :class="items[1].check ? 'active':''" @click="chooseItem(items[1])">{{items[1].name}}</view>
				<view class="game-box-2" :class="items[2].check ? 'active':''" @click="chooseItem(items[2])">{{items[2].name}}</view>
				<view class="game-box-1" :class="items[3].check ? 'active':''" @click="chooseItem(items[3])">{{items[3].name}}</view>
			</view>
			<view class="g-b-2">
				<view class="game-box-3" :class="items[4].check ? 'active':''" @click="chooseItem(items[4])">{{items[4].name}}</view>
				<view class="game-box-4">
					<view class="game-box-4-1" :class="items[5].check ? 'active':''" @click="chooseItem(items[5])">
						<view class="text">{{items[5].name}}</view>
					</view>
					<view class="game-box-4-2" :class="items[6].check ? 'active':''" @click="chooseItem(items[6])">
						<view class="text">{{items[6].name}}</view>
					</view>
				</view>
				<view class="game-box-5">
					<view class="game-box-5-1" :class="items[7].check ? 'active':''" @click="chooseItem(items[7])">
						<view class="text">{{items[7].name}}</view>
					</view>
					<view class="game-box-5-2" :class="items[8].check ? 'active':''" @click="chooseItem(items[8])">
						<view class="text">{{items[8].name}}</view>
					</view>
				</view>
				<view class="game-box-3" :class="items[9].check ? 'active':''" @click="chooseItem(items[9])">{{items[9].name}}</view>
			</view>
			<view class="g-b-2">
				<view class="game-box-3" :class="items[10].check ? 'active':''" @click="chooseItem(items[10])">{{items[10].name}}</view>
				<view class="game-box-5">
					<view class="game-box-5-1" :class="items[11].check ? 'active':''" @click="chooseItem(items[11])">
						<view class="text">{{items[11].name}}</view>
					</view>
					<view class="game-box-5-2" :class="items[12].check ? 'active':''" @click="chooseItem(items[12])">
						<view class="text">{{items[12].name}}</view>
					</view>
				</view>
				<view class="game-box-4">
					<view class="game-box-4-1" :class="items[13].check ? 'active':''" @click="chooseItem(items[13])">
						<view class="text">{{items[13].name}}</view>
					</view>
					<view class="game-box-4-2" :class="items[14].check ? 'active':''" @click="chooseItem(items[14])">
						<view class="text">{{items[14].name}}</view>
					</view>
				</view>
				<view class="game-box-3" :class="items[15].check ? 'active':''" @click="chooseItem(items[15])">{{items[15].name}}</view>
			</view>
			<view class="g-b-1">
				<view class="game-box-1" :class="items[16].check ? 'active':''" @click="chooseItem(items[16])">{{items[16].name}}</view>
				<view class="game-box-2" :class="items[17].check ? 'active':''" @click="chooseItem(items[17])">{{items[17].name}}</view>
				<view class="game-box-2" :class="items[18].check ? 'active':''" @click="chooseItem(items[18])">{{items[18].name}}</view>
				<view class="game-box-1" :class="items[19].check ? 'active':''" @click="chooseItem(items[19])">{{items[19].name}}</view>
			</view>
			<view class="g-b-6">
				<view class="g-b-6-v">
					<view class="g-b-6-1" :class="items[20].check ? 'active':''" @click="chooseItem(items[20])">
						<view class="text">{{items[20].name}}</view>
					</view>
					<view class="g-b-6-2" :class="items[21].check ? 'active':''" @click="chooseItem(items[21])">
						<view class="text">{{items[21].name}}</view>
					</view>
					<view class="g-b-6-3" :class="items[22].check ? 'active':''" @click="chooseItem(items[22])">
						<view class="text">{{items[22].name}}</view>
					</view>
					<view class="g-b-6-4" :class="items[23].check ? 'active':''" @click="chooseItem(items[23])">
						<view class="text">{{items[23].name}}</view>
					</view>
				</view>
			</view>
			<view class="g-b-7"></view>
		</view>
		<view class="form">
			<view class="form">
				<uni-forms ref="form" :modelValue="formData" :rules="rules" label-position="top">
					<uni-forms-item label="投注金额" name="money">
						<uni-easyinput type="number" v-model="formData.money" placeholder="请输入投注金额" />
					</uni-forms-item>
				</uni-forms>
				<button class="btn" @click="submit">{{$t('register.confirm.text')}}</button>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				tabIndex:0,
				link:{
					title:'澳洲10在线直播',
					src:'https://m.228168d.com/html/aozxy10/index.html'
				},
				result:{
					DRAW:'2110322',
					NUM:1,
					STR:'入',
					data:['01','02','03','04','05','06','07','08','09','10']
				},
				fptime:0,
				kjtime:0,
				items:[
					{index:'1',name:'虎入角',rate:1,check:false},
					{index:'2',name:'入正卡虎',rate:1,check:false},
					{index:'3',name:'入正卡龙',rate:1,check:false},
					{index:'4',name:'龙入角',rate:1,check:false},
					{index:'5',name:'虎正卡入',rate:1,check:false},
					{index:'6',name:'虎同',rate:1,check:false},
					{index:'7',name:'入同',rate:1,check:false},
					{index:'8',name:'入串',rate:1,check:false},
					{index:'9',name:'龙同',rate:1,check:false},
					{index:'10',name:'龙正卡入',rate:1,check:false},
					{index:'11',name:'虎正卡出',rate:1,check:false},
					{index:'12',name:'虎串',rate:1,check:false},
					{index:'13',name:'出同',rate:1,check:false},
					{index:'14',name:'出串',rate:1,check:false},
					{index:'15',name:'龙串',rate:1,check:false},
					{index:'16',name:'龙正卡出',rate:1,check:false},
					{index:'17',name:'虎出角',rate:1,check:false},
					{index:'18',name:'出正卡虎',rate:1,check:false},
					{index:'19',name:'出正卡龙',rate:1,check:false},
					{index:'20',name:'龙出角',rate:1,check:false},
					{index:'21',name:'入股',rate:3,check:false},
					{index:'22',name:'龙股',rate:3,check:false},
					{index:'23',name:'出股',rate:3,check:false},
					{index:'24',name:'虎股',rate:3,check:false}
				],
				formData:{
					money:'',
					
				},
				rules: {
					 money: {
					 	rules: [
					 		{required: true,errorMessage: this.$t('ruls.xxx.empty',{name:this.$t('register.code.text')})}
					 	]
					 }
				},
			}
		},
		methods: {
			submit(){
				this.$refs.form.validate().then(res=>{
					const para = Object.assign({},this.formData)
					this.$http.post('/player/mail/bind',para,(res=>{
						if(res.code ==200){
							uni.showToast({
								title:this.$t('oper.tip.success.text'),
								icon:'none',
								success() {
									uni.switchTab({
										url:'/pages/user/user'
									})
								}
							})
						}else{
							this.isSendCode = false
						}
					}))
				}).catch(err =>{
					console.log( err);
				})
			},
			chooseItem(item){
				item.check = !item.check
			},
			goRecord(){
				uni.navigateTo({
					url:''
				})
			},
			goUrl(){
				window.location.href = this.link.src
			},
			goBack(){
				uni.switchTab({
					url:'/pages/home/home'
				})
			},
			goRule(){
				
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
		width: 100%;
		height: 80upx;
		padding-top: 20upx;
		padding-bottom: 20upx;
		border-bottom: 2upx solid #e2e2e2;
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
		height: 724upx;
		margin: 15upx;
		display: flex;
		flex-direction: column;
		justify-content: space-between;
		position: relative;
		background-color: #eee;
		.g-b-1{
			display: flex;
			justify-content: space-between;
			.game-box-1{
				background-color: #1785ed;
				width: 124upx;
				height: 124upx;
				display: flex;
				align-items: center;
				justify-content: center;
			}
			.game-box-2{
				background-color: #07be5e;
				width: 234upx;
				height: 124upx;
				display: flex;
				align-items: center;
				justify-content: center;
			}
		}
		.g-b-2{
			display: flex;
			justify-content: space-between;
			.game-box-3{
				background-color: #07be5e;
				width: 124upx;
				height: 234upx;
				display: flex;
				align-items: center;
				justify-content: center;
			}
			.game-box-4{
				height: 234upx;
				width: 234upx;
				display: flex;
				position: relative;
				overflow: hidden;
				.game-box-4-1{
					overflow: hidden;
					position: absolute;
					width: 150%;
					height: 100%;
					background-color: #f35458;
					transform: rotate(45deg) translate(-10%, 68.5%);
					display: flex;
					justify-content: center;
					align-items: center;
					.text{
						transform: rotate(-45deg);
						margin-top: -130upx;
						margin-left: -44upx;
					}
				}
				.game-box-4-2{
					overflow: hidden;
					position: absolute;
					width: 150%;
					height: 100%;
					background-color: #f35458;
					-webkit-transform: rotate(45deg) translate(-10%, -33%);
					transform: rotate(45deg) translate(-10%, -33%);
					display: flex;
					justify-content: center;
					align-items: center;
					.text{
						transform: rotate(-45deg);
						margin-top: 100upx;
						margin-left: -44upx;
					}
				}
			}
			.game-box-5{
				height: 234upx;
				width: 234upx;
				display: flex;
				position: relative;
				overflow: hidden;
				.game-box-5-1{
					overflow: hidden;
					position: absolute;
					width: 150%;
					height: 100%;
					background-color: #f35458;
					-webkit-transform: rotate(135deg) translate(10%, 68.5%);
					transform: rotate(135deg) translate(10%, 68.5%);
					display: flex;
					justify-content: center;
					align-items: center;
					.text{
						transform: rotate(-135deg);
						margin-top: -46px;
					}
				}
				.game-box-5-2{
					overflow: hidden;
					position: absolute;
					width: 150%;
					height: 100%;
					background-color: #f35458;
					-webkit-transform: rotate(135deg) translate(10%, -33%);
					transform: rotate(135deg) translate(10%, -33%);
					display: flex;
					justify-content: center;
					align-items: center;
					.text{
						transform: rotate(-135deg);
						margin-left: -20upx;
						margin-top: 130upx;
					}
					
				}
			}
		}
		.g-b-6{
			position: absolute;
			background-color: #eee;
			width: 248upx;
			height: 248upx;
			margin-left: 238upx;
			margin-top: 238upx;
			.g-b-6-v{
				position: relative;
				width: 248upx;
				height: 248upx;
				overflow: hidden;
				.g-b-6-1{
					overflow: hidden;
					position: absolute;
					width: 150%;
					height: 100%;
					background-color: #f99d3d;
					-webkit-transform: rotate(135deg) translate(-39%, 68%);
					transform: rotate(135deg) translate(-39%, 68%);
					display: flex;
					justify-content: center;
					align-items: center;
					.text{
						transform: rotate(-135deg);
						margin: -260upx -208upx -114upx 82upx;
					}
				}
				.g-b-6-2{
					overflow: hidden;
					position: absolute;
					width: 150%;
					height: 100%;
					background-color: #f99d3d;
					-webkit-transform: rotate(135deg) translate(-39%, -33%);
					transform: rotate(135deg) translate(-39%, -33%);
					display: flex;
					justify-content: center;
					align-items: center;
					.text{
						transform: rotate(-135deg);
						margin: 144upx -270upx 0upx 0upx;
					}
				}
				.g-b-6-3{
					overflow: hidden;
					position: absolute;
					width: 150%;
					height: 100%;
					background-color: #f99d3d;
					-webkit-transform: rotate(-135deg) translate(-38.5%, -67.5%);
					transform: rotate(-135deg) translate(-38.5%, -67.5%);
					display: flex;
					justify-content: center;
					align-items: center;
					.text{
						transform: rotate(135deg);
						margin: 134upx -260upx 0upx 0upx;
					}
				}
				.g-b-6-4{
					overflow: hidden;
					position: absolute;
					width: 150%;
					height: 100%;
					background-color: #f99d3d;
					-webkit-transform: rotate(-135deg) translate(62%, -67.5%);
					transform: rotate(-135deg) translate(62%, -67.5%);
					display: flex;
					justify-content: center;
					align-items: center;
					.text{
						transform: rotate(135deg);
						margin: 104upx 248upx -30upx 0upx;
					}
				}
			}
		}
		.g-b-7{
			position: absolute;
			width: 82upx;
			height: 82upx;
			background-color: #fff;
			margin-left: 320upx;
			margin-top: 330upx;
			display: flex;
			justify-content: space-between;
			background-size: 100%;
			background-image: url('../../static/images/home/qp.jpg');
		}
		.active{
			background-color: #fff!important;
		}
	}
	.form{
		width: 670upx;
		::v-deep .uni-forms-item__label{
			color: #fff;
		}
		::v-deep .uni-easyinput__content{
			background-color: rgb(24, 24, 34)!important;
			border-color: rgb(24, 24, 34)!important;
			color: rgb(255,255,255)!important;
		}
		.btn{
			background-color: $fontColor;
			color: #fff;
		}
	}
}
</style>
