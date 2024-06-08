; (function () {

    /** 初始化页面*/
    var init = function () {
        window.history.forward(1); 
        var w1 = 1080; var w2 = $(window).width();
        var h1 = 1080; 
        var h2 = w2 * h1 / w1;
        $('.js-info').css('top', h2 + 'px');
        h1 = 1740; h2 = w2 * h1 / w1;
        //$('.js-btn-back').css('top', h2 + 'px');

        h1 = 1580; h2 = w2 * h1 / w1;
        $('.js-qrcode').css('top', h2 + 'px');
        $("#ImgQRCode").width(w2*0.16).height(w2*0.16);
        //var new_code_url=escape("http://h5.thinkcloudmedia.com/get_code.html?state=index");
        var userid=(window.parent.USER_ID==""?0:window.parent.USER_ID);
        var newcode="images/upload/qrcode" +(parseInt(userid/10000)) +"/qr"+userid;
        //http://h5.thinkcloudmedia.com/txt2qrcode?txt=111222;
        $("#ImgQRCode").attr("src",newcode);
        // var qrcode = new QRCode("DivQRCode");
        // qrcode.makeCode("http://h5.thinkcloudmedia.com/get_code.html?state=index");

        var server=unescape(request("server"));
        var nickname=unescape(request("nickname"));
        var local=unescape(request("local"));
        var distance=unescape(request("distance"));
        if(server.length>4)server.substr(0,4);
        if(nickname.length>6)nickname.substr(0,6);
        if(local.length>12)local.substr(0,12);
        $("#Span_Server").html(server);
        $("#Span_Nickname").html(nickname);
        $("#Span_Local").html(local);
        $("#Span_Time").html((new Date()).Format("yyyy年MM月dd日 hh:mm"));
        $("#Span_Distance").html(distance);

       //返回首页
    //    $('.js-btn-back').on('click', function (event) {
    //        location='index.html';
    //    });
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
        
        //callback(imgName, new Blob([arr]));
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
        setTimeout(checkIsLoaded,1000);

    });

}());

function checkIsLoaded(){
  if(ImgBG.complete){
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
      });
}
