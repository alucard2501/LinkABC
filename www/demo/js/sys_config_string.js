//读数据库相关
var GET_DATA = 'do';
var WX_API = 'wxapi';
var SESSION_ID = readCookie("sessionid");

var USER_LAT=0;
var USER_LNG=0;
var LAN = 'cn';
/*--获取网页传递的参数--*/

function request(paras) {
    var url = location.href;
    var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
    var paraObj = {}
    for (i = 0; j = paraString[i]; i++) {
        paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=") + 1, j.length);
    }
    var returnValue = paraObj[paras.toLowerCase()];
    if (typeof (returnValue) == "undefined") {
        return "";
    } else {
        return returnValue;
    }
}

//==================================
//描述：读取cookies
//程序员：江晓东
//日期：2008-11-23
//参数说明：name--cookies的名字，返回指定cookies的值
//==================================
function readCookie(name) {
    var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
    if (arr != null) return unescape(arr[2]);
    return null;
}
function readCookieSecond(name1, name2) {
    var cookieValue = "";
    var search = name2 + "=";
    var firstvalue = readCookie(name1);
    if (firstvalue.length > 0) {
        var offset = firstvalue.indexOf(search);
        if (offset != -1) {
            offset += search.length;
            end = firstvalue.indexOf("&", offset);
            if (end == -1) end = firstvalue.length;
            cookieValue = unescape(firstvalue.substring(offset, end));
        }
    }
    return cookieValue;
}
function setCookie(name, value) {
    document.cookie = name + '=' + value+';path=/';
}

//
function string2Date(datestr) {
    var converted = Date.parse(datestr);
    var myDate = new Date(converted);
    if (isNaN(myDate)) {
        var arys = datestr.split('-');
        myDate = new Date(arys[0], --arys[1], arys[2]);
    }
    return myDate;
}
function dateDiff(interval, date1, date2) {
    var objInterval = { 'D': 1000 * 60 * 60 * 24, 'H': 1000 * 60 * 60,
        'M': 1000 * 60, 'S': 1000, 'T': 1
    };
    interval = interval.toUpperCase();
    var dt1 = Date.parse(date1.replace(/-/g, '/'));
    var dt2 = Date.parse(date2.replace(/-/g, '/'));
    try {
        return Math.round((dt2 - dt1) / eval('(objInterval.' + interval + ')'));
    }
    catch (e) {
        return e.message;
    }
}
//==================================
//描述：重载setTimeout方法，使之可以执行带参数的函数
//程序员：江晓东
//日期：2008-11-23
//参数说明：name--cookies的名字，返回指定cookies的值
//==================================
_setTimeout = function (callback, timeout, param) {
    var args = Array.prototype.slice.call(arguments, 2);
    var _cb = function () {
        callback.apply(null, args);
    }
    setTimeout(_cb, timeout);
}
//==================================
//描述：只适用于宝特工作室的网站，用于后台转换图片路径
//程序员：江晓东
//==================================
function resetImagePath(sourcePath) {
    if (sourcePath == null) return "";
    if (sourcePath.substring(0, 4) == 'http')return sourcePath
    var regimg = /\.\.\/images\/upload/g;
    return sourcePath.replace(regimg, '../images/upload');
}
//==================================
//描述：只适用于宝特工作室的网站，用于后台转换图片路径
//程序员：江晓东
//==================================
function cleanImage(target_img, target_hidden) {
    target_img.attr("src", target_img.attr("defaultValue") || '');
    target_hidden.val(target_hidden.attr("defaultValue") || '');
}
//==================================
//描述：通用打开编辑窗口对话框
//程序员：江晓东
//==================================
function openDialogEdit(url) {
    var objs = new Array();
    var getv = window.showModalDialog(url, objs, 'dialogWidth=800px;dialogHeight=470px;status=no;scroll=auto;location=no');
    return getv;
}
//==================================
//描述：强制保留两位小数
//程序员：WEB
//==================================
function toDecimal2(x) {
    var f = parseFloat(x);
    if (isNaN(f)) {
        return false;
    }
    var f = Math.round(x * 100) / 100;
    var s = f.toString();
    var rs = s.indexOf('.');
    if (rs < 0) {
        rs = s.length;
        s += '.';
    }
    while (s.length <= rs + 2) {
        s += '0';
    }
    return s;
}

//==================================
//描述：sql日期转换成标准日期yyyy-MM-dd
//程序员：WEB
//==================================
function data_string(str) {
    var d = new Date(str);
    var ar_date = [d.getFullYear(), d.getMonth() + 1, d.getDate()];
    for (var i = 0; i < ar_date.length; i++) ar_date[i] = dFormat(ar_date[i]); return ar_date.join('-');
    function dFormat(i) { return i < 10 ? "0" + i.toString() : i; }
}

//==================================
//描述：sql日期转换成标准日期时间yyyy-MM-dd hh:mm:ss
//程序员：WEB
//==================================
function datetime_string(str) {
    var s = str.replace("/Date(", "").replace(")/", "");
    var d = new Date(parseInt(s));
    var ar_date = [d.getFullYear(), d.getMonth() + 1, d.getDate()];
    var ar_time = [d.getHours(), d.getMinutes(), d.getSeconds()];
    for (var i = 0; i < ar_date.length; i++) ar_date[i] = dFormat(ar_date[i]);
    for (var i = 0; i < ar_time.length; i++) ar_time[i] = dFormat(ar_time[i]);
    return ar_date.join(' / ') + " " + ar_time.join(':');
    function dFormat(i) { return i < 10 ? "0" + i.toString() : i; }
}

//==================================
//描述：判断是手机版还是PC版
//程序员：WEB
//==================================
function uaredirect() {
    try {
        if (document.getElementById("bdmark") != null) {
            return false;
        }
        var urlhash = window.location.hash;
        if (!urlhash.match("fromapp")) {
            if ((navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i))) {
                return true;
            }
        }
        return false;
    } catch (err) {
        return false;
    }
}

// ==================================
// js从数组中删除指定值（不是指定位置）的元素
// ==================================
Array.prototype.indexOf = function (val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) return i;
    }
    return -1;
};
Array.prototype.remove = function (val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};

// ==================================
// 根据图片ID获取图片路径
// ==================================
function getImageSrcById(id, tips) {
    var src = "";
    var data = {};
    data.sessionid = readCookie("SessionId");
    data.action = "FILL_SYS_IMAGES";
    data.id = id;
    $.ajax({
        type: "POST",
        url: GET_DATA,
        data: data,
        dataType: 'json',
        async: false,
        success: function (response, status, xhr) {
            if (response.status == "SUCCESS") {
                if (response.records.length > 0) {
                    record = response.records[0];
                    if (tips == "S") {
                        src = record.srcS;
                    } else {
                        src = record.srcL;
                    }
                }
            } else {
                alert(response.errorMessage);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest);
        }
    });
    return src;
}

// ==================================
// 触摸事件
// ==================================
var touchEvents = {
    touchstart: "touchstart",
    touchmove: "touchmove",
    touchend: "touchend",

    /**
     * @desc:判断是否pc设备，若是pc，需要更改touch事件为鼠标事件，否则默认触摸事件
     */
    initTouchEvents: function () {
        if (isPC()) {
            this.touchstart = "mousedown";
            this.touchmove = "mousemove";
            this.touchend = "mouseup";
        }
    }
};


// ==================================
// 检查是否数字
// ==================================
function isNumber(value) {
    var patrn = /^(-)?\d+(\.\d+)?$/;
    if (patrn.exec(value) == null || value == "") {
        return false
    } else {
        return true
    }
}

// ==================================
// 只允许输入整数
// ==================================
function onlyInt() {
    if (!(event.keyCode == 46) && !(event.keyCode == 8) && !(event.keyCode == 37) && !(event.keyCode == 39))
    if (!((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 96 && event.keyCode <= 105)))
    event.returnValue = false;
}

// ==================================
// 只允许输入数字
// ==================================
function onlyNumber() {
    if (!(event.keyCode == 46) && !(event.keyCode == 8) && !(event.keyCode == 37) && !(event.keyCode == 39) && !(event.keyCode == 190) && !(event.keyCode == 110))
    if (!((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 96 && event.keyCode <= 105)))
    event.returnValue = false;
}


//日期格式，当天显示时间15:15，不是当天显示日期17/01/23
function formatDate(c_date) {
    var d = "";

    var date1 = c_date.substring(0, 10);

    var date = new Date();
    var year = date.getFullYear();
    var mon = date.getMonth() + 1;         //getMonth()返回的是0-11，则需要加1
    if (mon <= 9) {                                     //如果小于9的话，则需要加上0
        mon = "0" + mon;
    }
    var day = date.getDate();                   //getdate()返回的是1-31，则不需要加1
    if (day <= 9) {                                     //如果小于9的话，则需要加上0
        day = "0" + day;
    }
    var date2 = year + '-' + mon + '-' + day;

    if (date1 == date2) {
        //当天，显示时间
        d = c_date.substring(11, 16);
    } else {
        //显示日期
        d = date1.substring(2, 10);
        d = d.replace("-", "/").replace("-", "/");
    }

    return d;
}


//==================================
//微信登录
//==================================
function wxLogin(){
	var code=request("code");
	var sessionId = SESSION_ID;
	var data={};
	data.state = "weixin_login";
	data.code=code;
	data.sessionId=sessionId;
    data.temp = Math.random();
    $.ajax({
        type: "GET",
        url: WX_API,
        data: data,
        dataType: 'json',
        async: false,
        success: function (response, status, xhr) {
            if (response.status == "SUCCESS") {
                SESSION_ID = response.sessionId;
                setCookie("sessionid", response.sessionId);
                if (response.isPosition == 1) {
                    positionBaidu();
                    setCookie("houseFilter", "");
                }

            } else {
                SESSION_ID = "";
                var strUrl = window.location.href;
                var arrUrl = strUrl.split("/");
                var strPage = arrUrl[arrUrl.length - 1];
                //登录失败回调地址
                //location = 'http://wx1.gdyouliao.com/get_code.html?appid=wx4b6f27b208524982&scope=snsapi_base&state=index&redirect_uri=http%3A%2f%2f192.168.0.66%3A8081%2fbhouse%2fmobileCn%2f' + strPage;
                location = 'http://wx1.gdyouliao.com/get_code.html?appid=wx4b6f27b208524982&scope=snsapi_base&state=index&redirect_uri=http%3A%2f%2fapp.onlytechglobal.com%2fbhouse%2fmobileCn%2f' + strPage;

            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest);
        }
    });
}
// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
// 例子： 
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}