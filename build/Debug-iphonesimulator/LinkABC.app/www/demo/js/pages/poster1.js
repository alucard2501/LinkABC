var SERVER="";
var SERVER1="";
var sat_lat="";
var sat_lng="";
var dx=0;
var dy=0;
var dz=0;
shared_info={
    title: '千里应援天命人，助力武神坛全明星争霸赛开战！', // 分享标题
    desc: '我正在为服务器的天命人应援助威，快来和我一起！', // 分享描述
    link: 'http://h5.thinkcloudmedia.com/get_code.html?state=index'+ request("userId"), // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
    imgUrl: 'http://h5.thinkcloudmedia.com/xyq163Wst/images/logo.png', // 分享图标
}

function bodyScroll(event){
    event.preventDefault();
}

function scrControl(t){
    if(t == 0){ //禁止滚动
        document.body.addEventListener('touchmove', this.bodyScroll, { passive: false });
    }else if( t == 1){ //开启滚动
        document.body.removeEventListener('touchmove',this.bodyScroll, {passive: false});
    }
}
; (function () {

    /** 初始化页面*/
    var init = function () {
        $("body").css("overflow-y","hidden");
        scrControl(0);
        //window.history.forward(1); 
        var w1 = 1080; var w2 = $(window).width();
        var h1 = 1080; 
        var h2 = w2 * h1 / w1;
        $('.js-info').css('top', h2 + 'px');
        h1 = 1760; h2 = w2 * h1 / w1;
        $('.js-qrword').css('top', h2 + 'px');
        h1 = 1800; h2 = w2 * h1 / w1;
        $('.js-btn-create').css('top', h2 + 'px');
        h1 = 1700; h2 = w2 * h1 / w1;
        $('.js-btn-back').css('top', h2 + 'px');
        h1 = 1600; h2 = w2 * h1 / w1;
        $('.js-btn-share').css('top', h2 + 'px');
        h1 = 1350; h2 = w2 * h1 / w1;
        $('.js-btn-link').css('top', h2 + 'px');
        h1 = 1574; h2 = w2 * h1 / w1;
        $('.js-btn-card').css('top', h2 + 'px');
        $('.js-btn-card-bg').css('top', h2 + 'px');
        h1 = 1784; h2 = w2 * h1 / w1;
        $('.js-btn-card-tips').css('top', h2 + 'px');

        h1 = 440; h2 = w2 * h1 / w1;
        $('.divSatelliteInfo').css('top', h2 + 'px');
        h1 = 1300; h2 = w2 * h1 / w1;
        $('.js-btn-server').css('top', h2 + 'px');
        $('.js-btn-satellite').css('top', h2 + 'px');
        h1 = 220; h2 = w2 * h1 / w1;
        $('.js-title1').css('top', h2 + 'px');
        $('.js-title2').css('top', h2 + 'px');
        h1 = 1100; h2 = w2 * h1 / w1;
        $('.js-info-rank').css('top', h2 + 'px');
        h1 = 440; h2 = w2 * h1 / w1;
        $('.js-info-server').css('top', h2 + 'px');
        h1 = 500; h2 = w2 * h1 / w1;
        $('.js-info-server').css('height', h2 + 'px');
        h1 = 1580; h2 = w2 * h1 / w1;
        $('.js-qrcode').css('top', h2 + 'px');
        $("#ImgQRCode").width(w2*0.16).height(w2*0.16);
        h1 = 1420; h2 = w2 * h1 / w1;
        $('.js-btn-close').css('top', h2 + 'px');

        //var new_code_url=escape("http://h5.thinkcloudmedia.com/get_code.html?state=index");
        //var userid=(window.parent.USER_ID==""?0:window.parent.USER_ID);
        var userid=request("userId");
        var newcode="images/upload/qrcode" +(parseInt(userid/10000)) +"/qr"+userid;
        //http://h5.thinkcloudmedia.com/txt2qrcode?txt=111222;
        $("#ImgQRCode").attr("src",newcode);

        
        // var qrcode = new QRCode("DivQRCode");
        // qrcode.makeCode("http://h5.thinkcloudmedia.com/get_code.html?state=index");

        sat_lat=request("sat_lat");
        sat_lng=request("sat_lng");
        $("#SpanLng").html(sat_lng);
        $("#SpanLat").html(sat_lat);

        sat_lat=request("sat_lat");
        sat_lng=request("sat_lng");
        $("#SpanLng").html(sat_lng);
        $("#SpanLat").html(sat_lat);

        dx=request("dx");
        dy=request("dy");
        dz=request("dz");
        $("#Spandx").html(dx);
        $("#Spandy").html(dy);
        $("#Spandz").html(dz);
        // var nickname=unescape(request("nickname"));
        // var local=unescape(request("local"));
        var distance=unescape(request("distance"));
        // if(server.length>4)server.substr(0,4);
        // if(nickname.length>6)nickname.substr(0,6);
        // if(local.length>12)local.substr(0,12);
        
        $("#Span_Time").html((new Date()).Format("yyyy年MM月dd日 hh:mm"));
        $("#Span_Distance").html(distance);
        getUserPoster();

        // if(WX_IS_READY){
            // wx.updateAppMessageShareData({ 
            //     title: shared_info.title, // 分享标题
            //     desc: shared_info.desc, // 分享描述
            //     link: shared_info.link, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
            //     imgUrl: shared_info.imgUrl, // 分享图标
            //     success: function () {
            //     // 设置成功
            //     }
            // })
            // wx.updateTimelineShareData({ 
            //     title: shared_info.title, // 分享标题
            //     link: shared_info.link, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
            //     imgUrl: shared_info.imgUrl, // 分享图标
            //     success: function () {
            //     // 设置成功
            //     }
            // })
        // };
        
    };

    var createJPG=function(){
    	var getPixelRatio = function (context) { // 获取设备的PixelRatio
            var backingStore = context.backingStorePixelRatio ||
                context.webkitBackingStorePixelRatio ||
                context.mozBackingStorePixelRatio ||
                context.msBackingStorePixelRatio ||
                context.oBackingStorePixelRatio ||
                context.backingStorePixelRatio || 0.5;
            return (window.devicePixelRatio || 0.5) / backingStore;
        };
        //生成的图片名称
        var imgName = "cs.jpg";
        var shareContent = document.getElementById("page");
        var width = shareContent.offsetWidth;
        var height = shareContent.offsetHeight;
        var canvas = document.createElement("canvas");
        var context = canvas.getContext('2d');
        var scale = getPixelRatio(context); //将canvas的容器扩大PixelRatio倍，再将画布缩放，将图像放大PixelRatio倍。
        canvas.width = width * scale;
        canvas.height = height * scale;
        canvas.style.width = width + 'px';
        canvas.style.height = height + 'px';
        context.scale(scale, scale);

        var opts = {
            scale: scale,
            canvas: canvas,
            width: width,
            height: height,
            //dpi: window.devicePixelRatio,
            useCORS : true
        };
        html2canvas(shareContent, opts).then(function (canvas) {
            context.imageSmoothingEnabled = false;
            context.webkitImageSmoothingEnabled = false;
            context.msImageSmoothingEnabled = false;
            context.imageSmoothingEnabled = false;
            var dataUrl = canvas.toDataURL("jpeg");
            console.log(dataUrl)
            $("#ImgPage").attr("src",dataUrl).css("display","");
            $("#page").css("display","none");
            //dataURIToBlob(imgName, dataUrl, callback);
        });
    }
    var dataURIToBlob =  function (imgName, dataURI, callback) {
        var binStr = atob(dataURI.split(',')[1]),
            len = binStr.length,
            arr = new Uint8Array(len);

        for (var i = 0; i < len; i++) {
            arr[i] = binStr.charCodeAt(i);
        }
        
        callback(imgName, new Blob([arr]));
    }

    var callback = function (imgName, blob) {
        var triggerDownload = $("<a>").attr("href", URL.createObjectURL(blob)).attr("download", imgName).appendTo("body").on("click", function () {
            if (navigator.msSaveBlob) {
                return navigator.msSaveBlob(blob, imgName);
            }
        });
        triggerDownload[0].click();
        triggerDownload.remove();
    };
    ImgBG.onload=function(){
    	console.log("ImgMask Loaded");
    	//createJPG();
    	
    	//convert2canvas();
    }
    /** 执行函数*/
    $(function () {
        init();
        loadServerRank();
        setTimeout(checkIsLoaded,3000);

    });

}());

function checkIsLoaded(){
  if(ImgBG.complete){
    $("body").css("overflow-y","");
    scrControl(1);
	convert2canvas();
  }else{
	  setTimeout(checkIsLoaded,1000);
  }
}
function convert2canvas() {
//    var el = document.getElementById("page");//要截图的div
//    var saveImg = document.getElementById("ImgPage");
//    var canvas = document.createElement("canvas");
//    var scale = window.devicePixelRatio;//获取设备的显示参数
//    var ctx = canvas.getContext("2d")
//    var rect = el.getBoundingClientRect(); //获取元素相对于视察的偏移量
//    var w = el.offsetWidth;
//    var h = el.offsetHeight;
//    
//    console.log(w)
//    canvas.width = w * scale;
//    canvas.height = h * scale;
//    canvas.style.width = w;
//    canvas.style.height = h;
//    ctx.scale(scale, scale);
//    ctx.translate(-rect.left, -rect.top); //设置context位置，值为相对于视窗的偏移量负值，让图片复位
//    html2canvas(el, {
//        scale : scale,
//        canvas : canvas,
//        width : w,
//        height : h,
//        logging : false
//    }).then(function(canvas) {
//        var dataUrl = canvas.toDataURL("jpeg");
//        saveImg.src = dataUrl;
//    });
//    
//    $("#page").hide();
//    $("#ImgPage").show();
	new html2canvas(document.getElementById('page')).then(canvas => {
        // canvas为转换后的Canvas对象
        let oImg = document.getElementById("ImgPage")
        oImg.src = canvas.toDataURL();  // 导出图片
        document.body.appendChild(oImg);  // 将生成的图片添加到body
        $("#page").hide();
        $("#ImgPage").show();
        startSDCardAnimate();

        //返回首页
        // $('.js-btn-back').on('click', function (event) {
        //     location='http://h5.thinkcloudmedia.com/get_code.html?state=index0';
        // });
        $('.js-btn-share').on('click', function (event) {
            $("#ImgShareTips").css("display","");
        });
        // $('.js-btn-create').on("touchstart",function(event){
        //     event.preventDefault();
        //     T_START=true;
        //     setTimeout(function(){
        //         if(T_START){
        //             console.log("download!");
        //             dataURIToBlob("海报.jpg", $("#ImgPage").attr("src"), function (imgName, blob) {
        //                 var triggerDownload = $("<a>").attr("href", URL.createObjectURL(blob)).attr("download", imgName).appendTo("body").on("click", function () {
        //                     if (navigator.msSaveBlob) {
        //                         return navigator.msSaveBlob(blob, imgName);
        //                     }
        //                 });
        //                 triggerDownload[0].click();
        //                 triggerDownload.remove();
        //             });
        //         }
        //     },3000);
        // });
        // $('.js-btn-create').on("touchend",function(event){
        //     console.log("up");
        //     T_START=false;
        // });
        $('.js-btn-card').on('click', function (event) {
            $(".js-satellite-info").css("display","");
        });
        $("#ImgShareTips").on('click',function(event){
            $("#ImgShareTips").css("display","none");
        });
        $(".js-btn-link").on('click',function(event){
            location='http://xyq.163.com/2020/wstmxs/';
        });

        //返回首页
        $('.js-btn-back').on('click', function (event) {
            location='http://h5.thinkcloudmedia.com/get_code.html?state=index0';
        });

        //应援总榜
        $('.js-btn-server').on('click', function (event) {
            $('.js-title2').css('display',"");
            $('.js-title1').css('display',"none");
            $('.js-btn-server').css("display","none");
            $('.js-btn-satellite').css('display',"");
            $(".divSatelliteInfo").css("display","none");
            $('.js-info-server').css('display',"");
        });

        //卫星位置
        $('.js-btn-satellite').on('click', function (event) {
            $('.js-title2').css('display',"none");
            $('.js-title1').css('display',"");
            $('.js-btn-server').css("display","");
            $('.js-btn-satellite').css('display',"none");
            $(".divSatelliteInfo").css("display","");
            $('.js-info-server').css('display',"none");
        });
        //关闭卫星页面
        $('.js-btn-close').on('click', function (event) {
            $(".js-satellite-info").fadeOut("fast");
        });
      });
}

var T_START=false;
/**小人摆动计时 */
var SDCARD_COUNT=0;
function startSDCardAnimate(){
    setInterval(function(){
        var t=Math.sin(SDCARD_COUNT/360*2*Math.PI)
        SDCARD_COUNT+=4
    	
    	$(".js-btn-card-bg").css("opacity",t*0.5+0.5);
    },30);

}

/** 加载服务器排行*/
var loadServerRank=function(){
    var s = $(".js-info-server");
    s.empty();
    var data={};
    data.action = "LOAD_SERVER_RANKING";
    data.userId=request("userId");
    data.temp = Math.random();
    $.ajax({
        type: "GET",
        url: GET_DATA,
        data: data,
        dataType: 'json',
        async: false,
        success: function (response, status, xhr) {
            if (response.status == "SUCCESS") {
                if(response.records!=null){
                    var rank=0;
                    for(var i=0;i<response.records.length;i++){
                        var row=response.records[i];
                        var p = $("<p/>");
                        p.html('<span class="spanT1">' + row.server1+'</span> <span class="spanT2">'+row.count+"次应援</span>");
                        s.append(p);
                        if(row.server1==response.server1){
                            rank=i+1;
                        }
                    }
                    
                }
                $("#SpanCount").html(response.rank);
                $("#SpanServerName").html(response.server1);
                $("#SpanRank").html(rank);
                $("#Span_Server1").html(response.server1);
                shared_info.desc='我正在为' + response.server1 +'服务器的天命人应援助威，快来和我一起！';
                if(WX_IS_READY){
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
                }
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            //alert(XMLHttpRequest);
            //setTimeout(getFaceListen,1000);
        }
    });
}

function getUserPoster(){
    var data={};
    data.action = "GET_USER_POSTER";
    data.temp = Math.random();
    data.userId=request("userId");
    $.ajax({
        type: "GET",
        url: GET_DATA,
        data: data,
        dataType: 'json',
        async: false,
        success: function (response, status, xhr) {
            if (response.status == "SUCCESS") {
                $("#Span_Server").html(response.server);
                
                $("#Span_Nickname").html(response.gameName);
                $("#Span_Local").html(response.local);
                
                
                
                // if(response.server.trim().length>0){
                //     $($("#Select_Server")[0].nextSibling).find("input").val(response.server);
                //     $($("#Select_Server")[0].nextSibling).find("a").remove();
                //     $($("#Select_Server")[0].nextSibling).find("input").attr("readonly","1");
                // }
                
                // if(response.local!=null && response.local!='null' && response.local!=""){
                //     //$("#Select_Position").val(response.local);
                //     //$($("#Select_Position")[0].nextSibling).find("input").val(response.local);
                // }
                // $("#Input_Name").val(response.gameName);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest);
        }
    });
}