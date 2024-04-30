/**
 * 时间日期相关操作
 */


/**
 * 时间格式化
 * 将 2018-09-23T11:54:16.000+0000 格式化成 2018/09/23 11:54:16
 * @param datetime 国际化日期格式
 */
export function format (datetime) {
  return formatWithSeperator(datetime, "/", ":");
}

/**
 * 时间格式化
 * 将 2018-09-23T11:54:16.000+0000 格式化成类似 2018/09/23 11:54:16
 * 可以指定日期和时间分隔符
 * @param datetime 国际化日期格式
 */
export function formatWithSeperator (datetime, dateSeprator, timeSeprator) {
  if (datetime != null) {
    var dateMat = new Date(datetime);
    var year = dateMat.getFullYear();
    var month = dateMat.getMonth() + 1;
    if (month >= 1 && month <= 9) {
      month = "0" + month;
    }
    var day = dateMat.getDate();
    if (day >= 1 && day <= 9) {
      day = "0" + day;
    }
    var hh = dateMat.getHours();
    if (hh >= 1 && hh <= 9) {
      hh = "0" + hh;
    }
    var mm = dateMat.getMinutes();
    if (mm <= 9) {
      mm = "0" + mm;
    }
    var ss = dateMat.getSeconds();
    if (ss <= 10) {
      ss = "0" + ss;
    }
    const timeFormat = year + dateSeprator + month + dateSeprator + day + " " + hh + timeSeprator + mm + timeSeprator + ss;
    return timeFormat;
  }
}
export function getCurrentDate(){
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
      month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
      strDate = "0" + strDate;
    }
    var currentdate = year +'-'+ month  +'-'+ strDate ;
    return currentdate;
}


export function getDate(datetime){
  var date= new Date(Date.parse(datetime.replace(/-/g,  "/")));
  return date;
}

export function subTime(str1,str2,str){
  var date = getDate(str);
  var date1 = getDate(str1);
  var date2 = getDate(str2);
  var datetime = date.getTime() + (date1.getTime() - date2.getTime())
  return formatWithSeperator(datetime,"-",":");
}
