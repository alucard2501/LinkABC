var H=0;
var VH=0.001;
var T=0.18;
var E=-0.11;
var cango=false;
/**skip,go */
var play_case="";
/**小人起始位置 */
var PEOPLE_OFFSET_Y=0;
/**小人摆动幅度 */
var PEOPLE_TOP=10;
/**小人摆动计时 */
var PEOPLE_COUNT=0;
; (function () {

    /** 初始化页面*/
    var init = function () {
        var w1 = 1080; var w2 = $(window).width();
        var h1 = 165; 
        var h2 = w2 * h1 / w1;
        $('.js-btn-index1').css('height', h2 + 'px');
        $('.js-btn-index2').css('height', h2 + 'px');
        $('.js-btn-index3').css('height', h2 + 'px');

        h1 = 138; h2 = w2 * h1 / w1;
        $('.js-skip').css('height', h2 + 'px');

        h1 = 1040; h2 = w2 * h1 / w1;
        $('.js-btn-index1').css('top', h2 + 'px');
        h1 = 1380; h2 = w2 * h1 / w1;
        $('.js-btn-index2').css('top', h2 + 'px');
        $('.js-btn-index3').css('top', h2 + 'px');

        h1=600; h2 = w2 * h1 / w1;
        PEOPLE_OFFSET_Y=h2;
        // document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
        //     WeixinJSBridge.call('hideToolbar');
        //     WeixinJSBridge.call('hideOptionMenu');
        // });
        startPeopleAnimate();
    };

    var skipClick = function () {
    	$(".js-bg").find("img").attr("src","images/bg_index1.jpg");
        $(".js-loading").fadeOut("fast");
        $(".js-index").fadeIn("slow");
        cango=false;
        startPeopleAnimate();
        play_case="skip";
        if(timer_animation!=null){
            clearInterval(timer_animation);
        }
        document.getElementById("alarm").play();
    };
    /** 初始化按钮动作*/
    var initButton = function () {
        //跳过
        $('.js-skip').on('click', function (event) {
        	skipClick();
        });
        
        //千里应援天命人
        $('.js-btn-index1').on('click', function (event) {
           
            play_case="go";
            document.getElementById("alarm").play();
            //
        });
        //
        $('.js-btn-index2').on('click', function (event) {
           //http://xyq.163.com/2020/wstmxs/
           window.parent.location='http://xyq.163.com/2020/wstmxs/';
            //
        });
        $('.js-btn-index3').on('click', function (event) {
            //http://xyq.163.com/2020/wstmxs/
            //window.parent.location='http://xyq.163.com/2020/wstmxs/';
             //
         });


        document.getElementById("alarm").addEventListener('ended', function () { 
        
            //在这个方法里写相应的逻辑就可以了
            if(play_case=="go"){
                var lat=window.parent.USER_LAT==null?0:window.parent.USER_LAT;
                var lng=window.parent.USER_LNG==null?0:window.parent.USER_LNG;
                console.log("lat:"+lat + ",lng:" + lng);
                location='satellite.html?lat='+lat+"&lng=" + lng;
            }
            
        }, false);
    };

    /** 执行函数*/
    $(function () {
        init();
        initButton();
        // if(ImgMask.complete){
        // 	//startWordAnimate();
        // }
        
    });
    $(document).click(function(){
    	//console.log("click");
    	if(cango){
    		skipClick();
    	}
    });
}());

// ImgMask.onload=function(){
// 	console.log("ImgMask Loaded");
// 	//startWordAnimate();
// }
var timer_animation=null;
function startWordAnimate(){
	$(".js-words").css("display","");
	H=$(window).height();
    T=0.18;
    $(".js-words").css("top",(T*H)+"px");
    
    timer_animation=setInterval(function(){
    	if(T>-0.11){
    		T-=VH;
    	}else{
    		T=-0.11;
    		if(timer_animation!=null){
    			clearInterval(timer_animation);
    		}
            cango=true;
            $(".js-words").fadeIn();
    	}
    	
    	$(".js-words").css("top",(T*H)+"px");
    },50);
}



function startPeopleAnimate(){
    $(".js-people").fadeIn();
    setInterval(function(){
        var t=Math.sin(PEOPLE_COUNT/360*2*Math.PI)
        PEOPLE_COUNT+=2
    	
    	$(".js-people").css("top",(PEOPLE_OFFSET_Y+PEOPLE_TOP*t)+"px");
    },30);
}
//ImgMask.onreadystatechange = function() {
//    if(ImgMask.readyState=="complete"||ImgMask.readyState=="loaded"){
//    	
//    }
//}
