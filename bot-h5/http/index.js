import http from './apiRequest'
import {baseUrl,AUTH_TOKEN} from '../utils/global'

http.setBaseUrl(baseUrl)
http.beforeRequestFilter = function(res){
	http.header[AUTH_TOKEN] = '';
	if(res.url.indexOf("/login") < 0){
		if (uni.getStorageSync(AUTH_TOKEN)) {
		    http.header[AUTH_TOKEN] = uni.getStorageSync(AUTH_TOKEN);
		}
	} 
	return res;
}
http.beforeResponseFilter = function (res) {
    //X-Auth-Token
	console.log(res,'-------------')
    
	if(res.statusCode==200){
		res = res.data
		if(res.code != 200){
			uni.showToast({
				 icon: 'none',
				 title: res.message,
			})
		}
		return res;
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