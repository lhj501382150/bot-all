import http from './apiRequest'
import {baseUrl,AUTH_TOKEN} from '../utils/global'

http.setBaseUrl(baseUrl)
http.beforeRequestFilter = function(res){
	http.header[AUTH_TOKEN] = '';
	if(res.url.indexOf("/login") < 0){
		if (uni.getStorageSync('Token')) {
		    http.header[AUTH_TOKEN] = 'Bearer ' + uni.getStorageSync('Token');
		}
	} 
	return res;
}
http.beforeResponseFilter = function (res) {
	if(res.statusCode==200){
		res = res.data
		if(res.iCode != 0){
			uni.showToast({
				 icon: 'none',
				 title: res.sMsg,
			})
		}
		return res;
	}else if(res.statusCode==401){
		 uni.navigateTo({
		 	url:'/pages/login/login'
		 })
	}else{
		uni.showToast({
			 icon: 'none',
			 title: res.errMsg,
		})
		return res;
	}
   
}
 
let req = {
    'http': http
}
export default req