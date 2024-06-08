; (function () {
    'use strict';

    /** 初始化页面*/
    var init = function () {
        var w1 = 1080; var w2 = $(window).width();
        var h1 = 1920; 
        var h2 = w2 * h1 / w1;
        $('#page').css('height', h2 + 'px');
    };

    /** 加载页面loading*/
    var loaderPage = function () {
        $(".loading").fadeOut("slow");
    };

    /** 向上箭头*/
    var goToTop = function () {
        $('.js-gotop').on('click', function (event) {
            event.preventDefault();
            $('html, body').animate({
                scrollTop: $('html').offset().top
            }, 500, 'easeInOutExpo');
            return false;
        });

        $(window).scroll(function () {
            var $win = $(window);
            if ($win.scrollTop() > 200) {
                $('.js-top').addClass('active');
            } else {
                $('.js-top').removeClass('active');
            }
        });
    };

    /** 执行函数*/
    $(function () {
        init();
        goToTop();                     
        loaderPage();                   
    });
}());
