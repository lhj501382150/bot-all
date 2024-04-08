<script>
	export default {
		onLaunch: function() {
			const noLogin=['/pages/index/index','/pages/home/home','/pages/login/login']//不需要登录的页面
			let setIntervalL=null
			let curPage = null//当前页面
			let curPageData=null//当前页面数据
			
			// 登录方法
			const xcxLogin=()=>{
				
			}
			// 拦截器方法
			const addInterceptorFN=(item,url=null)=>{
				// 路由拦截
				const routerObject={
					invoke(args) {
						// 没有url，禁止跳转
						if(!args.url){
							return false;
						}
						let url=args.url.split('?')[0]
						let notNeed=noLogin.includes(url)
						// 初始换页面进入的，登录后刷新页面
						if(args.isLaunch){
							// 第一种刷新页面
							uni.reLaunch({
								url:args.url
							})
							return false;
						}
						// 不需要验证登录
						if(notNeed){
							return true
						}else{
							// 没有登录
							uni.hideLoading()
							const token = localStorage.getItem('Token');
							if(!token){
								uni.navigateTo({
									url:'/pages/login/login'
								})
								return false
							}else{
								return true
							}
						}
						
					}
				}
				// 初始化，会将当前的路由传过来，然后验证
				if(url){
					routerObject.invoke({url:url,isLaunch:true})
				}
				// uniapp 拦截器
				uni.addInterceptor(item,routerObject)
			}
			// 初始化拦截
			const initPage=()=>{
				let pages=getCurrentPages()
				curPage=pages[pages.length-1]
				if(curPage){
					curPageData=curPage.data
					// 页面加载完成后
					if(setIntervalL)clearInterval(setIntervalL)
					// 登录
					let url=curPage.$page.fullPath
					url=url.substr(0,1)=='/'?url:('/'+url)
					let urlThis=url.split('?')[0]
					let notNeed=noLogin.includes(urlThis)
					let isReLaunch=false
					// 没有登录过，需要登录后刷新页面
					const token = localStorage.getItem('Token');
					if(!token){
						isReLaunch=true
					}
					if(!notNeed){
						uni.navigateTo({
							url:'/pages/login/login'
						})
						return
					}
				}
			}
			// 第一次进入
			setIntervalL=setInterval(()=>{
				initPage();
			},100)
			const addInterceptor=['navigateTo','redirectTo','reLaunch','switchTab']
			addInterceptor.forEach(item=>{
				// 挂载拦截器
				addInterceptorFN(item)
			})
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
