// 格式化数字 没三位加 ，
export const formatNum =  (val)=>{
	val = val.toString();
	var arrNum=val.split('').reverse().join('').replace(/(\d{3})/g,'$1,').split('').reverse().join('');
	// var arrLength=arrNum.length;
	// if(arrLength%4==0){
	// 	 arrNum=arrNum.substring(1);
	// }
	return arrNum
}
export const formatDate = (timestamp,type)=>{
	 const date = new Date(timestamp);
    const year = date.getFullYear();
      const month = ("0" + (date.getMonth() + 1)).slice(-2);
      const day = ("0" + date.getDate()).slice(-2);
      const hours = ("0" + date.getHours()).slice(-2);
      const minutes = ("0" + date.getMinutes()).slice(-2);
      const seconds = ("0" + date.getSeconds()).slice(-2);
		if(type==1){//mm-dd HH:mm
			return `${month}-${day} ${hours}:${minutes}`;
		}else if(type==2){//mm-dd HH:mm
			return `${year}-${month}-${day}`;
		}else if(type==3){//mm-dd HH:mm
			return `${hours}${minutes}${seconds}`;
		}else{
			return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
		}
}

export const divide = (num) =>{
	if(num > 0){
		num = (num / 100).toFixed(2)
		let str = formatNum(num)
		if (str.charAt(0) === ',') {
		  str = str.slice(1);
		}
		return str
	}else{
		return 0;
	}
}

export const divide100 = (num) =>{
	if(num != 0){
		return num = (num / 100).toFixed(2)
	}else{
		return 0;
	}
}

export const getSecond = (time) =>{
	var currentDate = Date.parse(new Date());
	var specifiedDate = Date.parse(new Date(time.replace(/-/g,'/'))); // 假设这是你要计算的指定时间
	var timeDifferenceInMilliseconds = specifiedDate - currentDate;
	// 转换为秒数
	var timeDifferenceInSeconds = Math.floor(timeDifferenceInMilliseconds / 1000);
	if(timeDifferenceInSeconds < 0 ){
		timeDifferenceInSeconds = 0
	}
	return timeDifferenceInSeconds;
}

export const getCurTime = ()=>{
	 const date = new Date();
      const hours = ("0" + date.getHours()).slice(-2);
      const minutes = ("0" + date.getMinutes()).slice(-2);
      const seconds = ("0" + date.getSeconds()).slice(-2);
	  return `${hours}${minutes}${seconds}`;
}