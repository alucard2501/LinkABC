
var WX_IS_READY = false;
var debug_case=2;
var loca=escape(window.location).replace(/%25/g,"%");
$.ajax({
    type: "GET",
    url: 'wxapi?action=INIT_WX_SDK&myurl=' + loca,
    dataType: 'json',
    async: false,
    success: function (response, status, xhr) {
        //alert(JSON.stringify(response));
        wx.config({
        	beta:true,
        	debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId: response.appId, // 必填，公众号的唯一标识
            timestamp: response.timeStamp, // 必填，生成签名的时间戳
            nonceStr: response.nonceStr, // 必填，生成签名的随机串
            signature: response.signature,// 必填，签名，见附录1
            jsApiList: ['chooseImage'
                        , 'previewImage'
                        , 'uploadImage'
                        , 'downloadImage'
                        , 'scanQRCode'
						, 'getLocation'
						, 'updateAppMessageShareData'
                        , 'updateTimelineShareData'
                        , 'onMenuShareAppMessage'
                        , 'onMenuShareTimeline'
                        ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });
        
    },
    error: function (XMLHttpRequest, textStatus, errorThrown) {
    }
});
wx.ready(function () {
	WX_IS_READY = true;
	wx.updateAppMessageShareData({ 
        title: shared_info.title, // 分享标题
        desc: shared_info.desc, // 分享描述
        link: shared_info.link, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
        imgUrl: shared_info.imgUrl, // 分享图标
        success: function () {
          // 设置成功
        }
    })
    wx.updateTimelineShareData({ 
        title: shared_info.title, // 分享标题
        link: shared_info.link, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
        imgUrl: shared_info.imgUrl, // 分享图标
        success: function () {
          // 设置成功
        }
    })
	wx.getLocation({
		  type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
		  success: function (res) {
		    var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
		    var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
		    var speed = res.speed; // 速度，以米/每秒计
		    var accuracy = res.accuracy; // 位置精度
		    USER_LAT=latitude;
			USER_LNG=longitude;
			GET_LOCATION=true;
		    console.log("lat:"+latitude +",lng:" + longitude);
		    //checkDistance(latitude,longitude);
		  }
		});
});
wx.error(function (res) {

    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
    alert(JSON.stringify(res.errMsg));
});
function checkDistance(lat,lng){
	var data={};
	data.action = "get_distance";
	data.lat=lat;
	data.lng=lng;
	data.sessionid=request("sessionId");
	data.debug_case=debug_case;
    data.temp = Math.random();
    $.ajax({
        type: "GET",
        url: GET_DATA,
        data: data,
        dataType: 'json',
        async: false,
        success: function (response, status, xhr) {
            if (response.status == "SUCCESS") {
            	if(parseInt(response.distance)>3000){
            		$(".js-p01").css("display","none");
                	$(".js-p02").css("display","none");
                	$(".js-p03").css("display","none");
                	$(".js-p15").css("display","none");
                	$(".js-p16").css("display","none");
                	$(".js-p17").css("display","none");
                	$(".js-p18").css("display","none");
                	$(".js-p19").css("display","none");
                	$(".js-p20").css("display","none");
                	$(".js-p07").fadeIn();
            	}
            }else{
            	$(".js-p01").css("display","none");
            	$(".js-p02").css("display","none");
            	$(".js-p03").css("display","none");
            	$(".js-p15").css("display","none");
            	$(".js-p16").css("display","none");
            	$(".js-p17").css("display","none");
            	$(".js-p18").css("display","none");
            	$(".js-p19").css("display","none");
            	$(".js-p20").css("display","none");
            	$(".js-p07").fadeIn();
            	
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest);
            //setTimeout(getFaceListen,1000);
        }
    });
}

