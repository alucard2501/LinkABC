var H=0;
var VH=0.001;
var T=0;
var E=0;

var sat_lat="E0901017.27012";
var sat_lng="N000000.95409"
var dE=18880.9;
var dS=0;
var dV=200;
var dx=0;
var dy=0;
var dz=0;
var cango=false;

/**skip,go */
var play_case="";
/**小人起始位置 */
var PEOPLE_OFFSET_Y=0;
/**小人摆动幅度 */
var PEOPLE_TOP=10;
/**小人摆动计时 */
var PEOPLE_COUNT=0;
var SDCARD_COUNT=0;
var IS_FIRST_TIME=true;
var SERVER1="";
; (function () {

    /** 初始化页面*/
    var init = function () {
        window.history.forward(1); 
        var w1 = 1080; var w2 = $(window).width();
        var h1 = 138; var h2 = w2 * h1 / w1;
        $('.js-skip').css('height', h2 + 'px');
        h1 = 160; h2 = w2 * h1 / w1;
        $('.js-enter').css('height', h2 + 'px');
        h1 = 278; h2 = w2 * h1 / w1;
        $('.js-btn-info').css('height', h2 + 'px');
        $('.js-btn-poster').css('height', h2 + 'px');
        h1 = 84; h2 = w2 * h1 / w1;
        $('.js-btn-back').css('height', h2 + 'px');
        $('.js-btn-close').css('height', h2 + 'px');

        h1 = 1155; h2 = w2 * h1 / w1;
        $('.js-enter').css('top', h2 + 'px');
        h1 = 1450; h2 = w2 * h1 / w1;
        $('.js-btn-info').css('top', h2 + 'px');
        $('.js-btn-poster').css('top', h2 + 'px');
        h1 = 1790; h2 = w2 * h1 / w1;
        $('.js-btn-back').css('top', h2 + 'px');
        h1 = 1420; h2 = w2 * h1 / w1;
        $('.js-btn-close').css('top', h2 + 'px');

        h1 = 948; h2 = w2 * h1 / w1;
        $('.js-form').css('top', h2 + 'px');
        
        h1 = 440; h2 = w2 * h1 / w1;
        $('.divSatelliteInfo').css('top', h2 + 'px');

        h1 = 1490; h2 = w2 * h1 / w1;
        $('.js-arrow1').css('top', h2 + 'px');
        $('.js-arrow2').css('top', h2 + 'px');

        h1 = 1490; h2 = w2 * h1 / w1;
        $('.js-btn-card').css('top', h2 + 'px');
        $('.js-btn-card-bg').css('top', h2 + 'px');

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

        $("#Span_Time").html((new Date()).Format("yyyy年MM月dd日 hh:mm"));
        
        h1=550; h2 = w2 * h1 / w1;
        PEOPLE_OFFSET_Y=h2;
        skipClick();
    };

    /** 跳过效果*/
    var skipClick = function () {
        $(".js-bg").find("img").attr("src","images/bg_satellite.jpg");
        $(".js-loading").fadeOut("fast");
        $(".js-main").fadeIn("slow");
        startCounter();
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
        	//createJPG();
        });
        //点击助力应援
        $('.js-enter').on('click', function (event) {
            skipClick();
        	//createJPG();
        });

        //点击卫星
        $('.js-btn-info').on('click', function (event) {
            $(".js-main").fadeOut("fast");
            $(".js-info").fadeIn("slow");
        });
        //关闭卫星页面
        $('.js-btn-close').on('click', function (event) {
            $(".js-info").fadeOut("fast");
            $(".js-main").fadeIn("slow");
        });
        
        //生成海报
        $('.js-btn-poster').on('click', function (event) {
            document.getElementById("alarm").play();
            var server=$($("#Select_Server")[0].nextSibling).find("input").val();
            var server1=$($("#Select_Server1")[0].nextSibling).find("input").val();
            var local=$("#Select_Position").val();
            if(server!=null && server.trim().length==0){
                //alert("请选择所在服务器");
                //$("<div>请选择所在服务器</div>").dialog();
            	myAlert('系统提示','请选择所在服务器！',function(){   
                    //要回调的方法  
                    //window.location.href="http://www.baidu.com"
                });
                return;

            }
            if(server1!=null && server1.trim().length==0){
                //alert("请选择所在服务器");
                //$("<div>请选择所在服务器</div>").dialog();
            	myAlert('系统提示','请选择要应援服务器！',function(){   
                    //要回调的方法  
                    //window.location.href="http://www.baidu.com"
                });
                return;

            }
            if($("#Input_Name").val().trim().length==0){
                //$("<div>请填写玩家昵称</div>").dialog();
                //alert("请填写玩家昵称");
                myAlert('系统提示','请填写玩家昵称！',function(){   
                    //要回调的方法  
                    //window.location.href="http://www.baidu.com"
                });
                return;
            }
            if($("#Input_Name").val().trim().length>9){
                //$("<div>请填写玩家昵称</div>").dialog();
                //alert("请填写玩家昵称");
                myAlert('系统提示','玩家昵称不能超过9个字！',function(){   
                    //要回调的方法  
                    //window.location.href="http://www.baidu.com"
                });
                return;
            }
            if(!IS_FIRST_TIME){
                myAlertDelay('系统提示','您已应援过' + SERVER1 + '服务器，本次应援仅能生成海报，并未计算有效应援次数',function(){   
                    //要回调的方法  
                    
                    var data={};
                    data.action = "SAVE_USER_POSTER";
                    data.location=local;
                    data.server=server;
                    data.server1=SERVER1;
                    data.gameName=$("#Input_Name").val();
                    data.temp = Math.random();
                    data.userId=(window.parent.USER_ID==""?0:window.parent.USER_ID);
                    $.ajax({
                        type: "GET",
                        url: GET_DATA,
                        data: data,
                        dataType: 'json',
                        async: false,
                        success: function (response, status, xhr) {
                            if (response.status == "SUCCESS") {
                                window.parent.location='poster1.html?userId=' + (window.parent.USER_ID==""?0:window.parent.USER_ID) + "&distance=" + escape(dE)+"&sat_lat=" + escape(sat_lat)+"&sat_lng=" + escape(sat_lng)+"&dx=" + escape(dx)+"&dy=" + escape(dy)+"&dz=" + escape(dz)  ;
                                
                            }
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            alert(XMLHttpRequest);
                        }
                    });
                });
            }else{
                myConfirm("系统提示","选定服务器后不能修改，确定要应援\""+ server1 +"\"服务器？",function(result){
                    if(!result)return;
                    var data={};
                    data.action = "SAVE_USER_POSTER";
                    data.location=local;
                    data.server=server;
                    data.server1=server1;
                    data.gameName=$("#Input_Name").val();
                    data.temp = Math.random();
                    data.userId=(window.parent.USER_ID==""?0:window.parent.USER_ID);
                    $.ajax({
                        type: "GET",
                        url: GET_DATA,
                        data: data,
                        dataType: 'json',
                        async: false,
                        success: function (response, status, xhr) {
                            if (response.status == "SUCCESS") {
                                myAlertDelay('系统提示','同一微信用户只能应援一个天命人',function(){   
                                    //要回调的方法  
                                    window.parent.location='poster1.html?userId=' + (window.parent.USER_ID==""?0:window.parent.USER_ID) +"&distance=" + escape(dE)+"&sat_lat=" + escape(sat_lat)+"&sat_lng=" + escape(sat_lng)+"&dx=" + escape(dx)+"&dy=" + escape(dy)+"&dz=" + escape(dz)  ;
                                });
                                
                            }
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            alert(XMLHttpRequest);
                        }
                    });
                });
            }
            
            //console.log(local);
            
            

            
        });

        //返回首页
        $('.js-btn-back').on('click', function (event) {
            location='index.html';
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
    };

//    /** 加载我的位置*/
//    var loadPosition = function () {
//        $( "#Select_Position" ).combobox();
//        $( "#Select_Position #toggle" ).click(function() {
//            $( "#Select_Position" ).toggle();
//        });
//    };

    /** 加载服务器*/
    var loadServer = function () {
        //Select_Position
        //for debug

        $( "#Select_Server1" ).combobox();
        $( "#Select_Server1 #toggle" ).click(function() {
            $( "#Select_Server1" ).toggle();
        });
        $($("#Select_Server1")[0].nextSibling).find("input").on("blur",function(){
            var that=this;
            var val=$(this).val();
            var $options=$("#Select_Server1").find("option");
            for(var i=0;i<$options.length;i++){
                if($($options[i]).attr("value").indexOf(val)>-1){
                    $(this).val($($options[i]).attr("value"));
                    return;
                }
            }
            $(this).val("");
        });

    	var s = $("#Select_Server");
        s.empty();

        var option = $("<option/>");
        option.attr("value", "");
        option.html("请选择...");
        s.append(option);
    	var data={};
    	data.action = "LOAD_SERVER";
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
                		for(var i=0;i<response.records.length;i++){
                			var row=response.records[i];
                			var option = $("<option/>");
                            option.attr("value", row.name);
                            option.html( row.name);
                            s.append(option);
                		}
                        $( "#Select_Server" ).combobox();
                        $( "#Select_Server #toggle" ).click(function() {
                            $( "#Select_Server" ).toggle();
                        });
                        $($("#Select_Server")[0].nextSibling).find("input").on("blur",function(){
                            var that=this;
                            var val=$(this).val();
                            var $options=$("#Select_Server").find("option");
                            for(var i=0;i<$options.length;i++){
                                if($($options[i]).attr("value").indexOf(val)>-1){
                                    $(this).val($($options[i]).attr("value"));
                                    return;
                                }
                            }
                            $(this).val("");
                        });
                        getUserPoster();
                	}
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert(XMLHttpRequest);
                //setTimeout(getFaceListen,1000);
            }
        });
    };
    /** 加载服务器*/
    var loadPosition = function (lat,lng) {
        var word1="即将到达你的上方";
        var word2="刚刚掠过你的上空";
        var word3="正在奔向地球的彼端";
        var word4="在地球另一端与你遥望";
        var word5="正从地球彼端向你飞来";
        var o=43272;
    	//Select_Position
    	var s = $("#Select_Position");
        //s.empty();
        var lat_demo=23.1333333;
        var lng_demo=113.2833333;
//        var option = $("<option/>");
//        option.attr("value", "");
//        option.html("请选择...");
//        s.append(option);
    	var data={};
    	data.action = "GET_POI";
    	data.lat=(lat==0?lat_demo:lat);
    	data.lng=(lng==0?lng_demo:lng);
        data.temp = Math.random();
    	$.ajax({
            type: "GET",
            url: GET_DATA,
            data: data,
            dataType: 'json',
            async: false,
            success: function (response, status, xhr) {
                if (response.status == "SUCCESS") {
                	dE=parseFloat(response.distance);
                	dS=0;
                    dV=dE/50;
                    var word="";
                    if(dE<=o*0.1 && response.coming==0)word=word2;
                    if(dE>o*0.1 && dE<=o*0.42 && response.coming==0)word=word3;
                    if(dE>o*0.42 && dE<=o*0.5 && response.coming==0)word=word4;
                    if(dE>o*0.42 && dE<=o*0.5 && response.coming==1)word=word4;
                    if(dE>o*0.1 && dE<=o*0.42 && response.coming==1)word=word5;
                    if(dE<=o*0.1 && response.coming==1)word=word1;
                    $("#PWord2").html("天命之人已收到你的应援，他们" + word);
                    s.val(response.province+response.city+response.district);
                    $("#SpanLng").html(response.sat_lng);
                    $("#SpanLat").html(response.sat_lat);
                    sat_lat=response.sat_lat;
                    sat_lng=response.sat_lng;
                    dx=response.dx;
                    dy=response.dy;
                    dz=response.dz;
                    $("#Spandx").html(dx);
                    $("#Spandy").html(dy);
                    $("#Spandz").html(dz);
                	// if(response.pois!=null){
                	// 	for(var i=0;i<response.pois.length;i++){
                	// 		var poi=response.pois[i];
                	// 		var option = $("<option/>");
                    //         option.attr("value", poi);
                    //         option.html(poi);
                    //         s.append(option);
                	// 	}
                	// 	//$( "#Select_Position" ).combobox();
                    //     $( "#Select_Position #toggle" ).click(function() {
                    //         $( "#Select_Position" ).toggle();
                    //     });
                	// }
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert(XMLHttpRequest);
                //setTimeout(getFaceListen,1000);
            }
        });
        
    };
    /** 加载服务器排行*/
    var loadServerRank=function(){
        var s = $(".js-info-server");
        s.empty();
        var data={};
        data.action = "LOAD_SERVER_RANKING";
        data.userId=(parseInt(window.parent.USER_ID==null?0:window.parent.USER_ID))
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
                    if(response.server1!="NULL" && response.server1!=null && response.server1.trim().length>0){
                        IS_FIRST_TIME=false;
                        SERVER1=response.server1;
                    }
                    $("#SpanCount").html(response.rank);
                    $("#SpanServerName").html(response.server1);
                    $("#SpanRank").html(rank);
                    
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert(XMLHttpRequest);
                //setTimeout(getFaceListen,1000);
            }
        });
    }

    

    /** 执行函数*/
    $(function () {
        init();
        initButton();
        
        var lat=window.parent.USER_LAT==null?0:window.parent.USER_LAT;
        var lng=window.parent.USER_LNG==null?0:window.parent.USER_LNG;
        loadPosition(lat,lng);
        loadServer();
        loadServerRank();
        if(ImgMask.complete){
        	//startAnimate();
        }
    });
    
    $(document).click(function(){
    	//console.log("click");
    	if(cango){
    		skipClick();

    	}
    });
}());

ImgMask.onload=function(){
	console.log("ImgMask Loaded");
	//startAnimate();
}
var timer_animation=null;
function startAnimate(){
	$("#DivImgWord").css("display","");
	H=$(window).height();
    T=0.12;
    $("#DivImgWord").css("top",(T*H)+"px");
    timer_animation=setInterval(function(){
    	if(T>-0.11){
    		T-=VH;
    	}else{
    		T=-0.11;
    		if(timer_animation!=null){
    			clearInterval(timer_animation);
    		}
    		cango=true;
    	}
    	
    	$("#DivImgWord").css("top",(T*H)+"px");
    },50);


}

function shakeArrow(target){
    var tag=$("."+target);
    tag.fadeIn("fast",function(){
        tag.fadeOut("fast",function(){
            tag.fadeIn("fast",function(){
                tag.fadeOut("fast",function(){
                    tag.fadeIn("fast",function(){
                        tag.fadeOut("fast");
                    });
                });
            });
        });
    });
}
var timer_counter=null;
function startCounter(){
	timer_counter=setInterval(function(){
    	if(dS<dE){
    		dS+=dV;
    	}else{
    		dS=dE;
    		if(timer_counter!=null){
    			clearInterval(timer_counter);
    		}
    	}
    	
    	$("#Span_Distance").text(parseInt(dS));
    },100);
}
function startPeopleAnimate(){
    $(".js-people").fadeIn();
    setInterval(function(){
        var t=Math.sin(PEOPLE_COUNT/360*2*Math.PI)
        PEOPLE_COUNT+=2
        $(".js-people").css("top",(PEOPLE_OFFSET_Y+PEOPLE_TOP*t)+"px");

        t=Math.sin(SDCARD_COUNT/360*2*Math.PI)
        SDCARD_COUNT+=4
        $(".js-btn-card-bg").css("opacity",t*0.5+0.5);
        
    },30);

    setInterval(function(){
        shakeArrow("js-arrow1");
        shakeArrow("js-arrow2");
    },5000);
}

function getUserPoster(){
    var data={};
    data.action = "GET_USER_POSTER";
    data.temp = Math.random();
    data.userId=(parseInt(window.parent.USER_ID==null?0:window.parent.USER_ID))
    $.ajax({
        type: "GET",
        url: GET_DATA,
        data: data,
        dataType: 'json',
        async: false,
        success: function (response, status, xhr) {
            if (response.status == "SUCCESS") {
                if(response.server.trim().length>0){
                    $($("#Select_Server")[0].nextSibling).find("input").val(response.server);
                    $($("#Select_Server")[0].nextSibling).find("a").remove();
                    $($("#Select_Server")[0].nextSibling).find("input").attr("readonly","1");
                }
                
                if(response.local!=null && response.local!='null' && response.local!=""){
                    //$("#Select_Position").val(response.local);
                    //$($("#Select_Position")[0].nextSibling).find("input").val(response.local);
                }
                $("#Input_Name").val(response.gameName);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest);
        }
    });
}