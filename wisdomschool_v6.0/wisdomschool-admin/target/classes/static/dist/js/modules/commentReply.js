define(function (require, exports, module) {
    require("flexText");

    /*$(function () {
        $('.comment-editor-text').flexText();
    });*/

    /*/!*<!--点击评论创建评论条-->*!/
    $('.commentAll').on('click', '.plBtn', function () {
        let myDate = new Date();
        //获取当前年
        let year = myDate.getFullYear();
        //获取当前月
        let month = myDate.getMonth() + 1;
        //获取当前日
        let date = myDate.getDate();
        let h = myDate.getHours();       //获取当前小时数(0-23)
        let m = myDate.getMinutes();     //获取当前分钟数(0-59)
        if (m < 10) m = '0' + m;
        let s = myDate.getSeconds();
        if (s < 10) s = '0' + s;
        let now = year + '-' + month + "-" + date + " " + h + ':' + m + ":" + s;
        //获取输入内容
        let oSize = $('#comment-editor-text').val();
        console.log(oSize);
        //动态创建评论模块
        oHtml = '<div class="comment-show-con clearfix"><div class="comment-show-con-img pull-left"><img src="images/header-img-comment_03.png" alt=""></div> <div class="comment-show-con-list pull-left clearfix"><div class="pl-text clearfix"> <a href="#" class="comment-size-name">David Beckham : </a> <span class="my-pl-con">&nbsp;' + oSize + '</span> </div> <div class="date-dz"> <span class="date-dz-left pull-left comment-time">' + now + '</span> <div class="date-dz-right pull-right comment-pl-block"><a href="javascript:;" class="removeBlock">删除</a> <a href="javascript:;" class="date-dz-pl pl-hf hf-con-block pull-left">回复</a> <span class="pull-left date-dz-line">|</span> <a href="javascript:;" class="date-dz-z pull-left"><i class="date-dz-z-click-red"></i>赞 (<i class="z-num">666</i>)</a> </div> </div><div class="hf-list-con"></div></div> </div>';
        if (oSize.replace(/(^\s*)|(\s*$)/g, "") != '') {
            $('.comment-show').prepend(oHtml);
            $('#comment-editor-text').val('');
        }
    });

    /!*<!--评论回复块创建-->*!/

    $('.comment-show').on('click', '.hf-pl', function () {
        let oThis = $(this);
        //获取输入内容
        let oHfVal = $(this).siblings('.hf-input').val();
        console.log(oHfVal)
        let oHfName = $(this).parents('.hf-con').parents('.date-dz').siblings('.pl-text').find('.comment-size-name').html();
        let oAllVal = '回复@' + oHfName;
        if (oHfVal.replace(/^ +| +$/g, '') == '' || oHfVal == oAllVal) {

        } else {
            $.getJSON("json/pl.json", function (data) {
                let oAt = '';
                let oHf = '';
                $.each(data, function (n, v) {
                    delete v.hfContent;
                    delete v.atName;
                    let arr;
                    let ohfNameArr;
                    if (oHfVal.indexOf("@") == -1) {
                        data['atName'] = '';
                        data['hfContent'] = oHfVal;
                    } else {
                        arr = oHfVal.split(':');
                        ohfNameArr = arr[0].split('@');
                        data['hfContent'] = arr[1];
                        data['atName'] = ohfNameArr[1];
                    }

                    if (data.atName == '') {
                        oAt = data.hfContent;
                    } else {
                        oAt = '回复<a href="#" class="atName">@' + data.atName + '</a> : ' + data.hfContent;
                    }
                    oHf = data.hfName;
                });

                let oHtml = '<div class="all-pl-con"><div class="pl-text hfpl-text clearfix">' +
                    '<a href="#" class="comment-size-name">我的名字 : </a><span class="my-pl-con">'
                    + oAt + '</span></div><div class="date-dz"> <span class="date-dz-left pull-left comment-time">'
                    + now + '</span> <div class="date-dz-right pull-right comment-pl-block"> ' +
                    '<a href="javascript:;" class="removeBlock">删除</a> ' +
                    '<a href="javascript:;" class="date-dz-pl pl-hf hf-con-block pull-left">回复</a> ' +
                    '<span class="pull-left date-dz-line">|</span> <a href="javascript:;" class="date-dz-z pull-left">' +
                    '<i class="date-dz-z-click-red"></i>赞 (<i class="z-num">0</i>)</a> </div> </div></div>';
                oThis.parents('.hf-con').parents('.comment-show-con-list').find('.hf-list-con').css('display', 'block')
                    .prepend(oHtml) && oThis.parents('.hf-con').siblings('.date-dz-right').find('.pl-hf')
                    .addClass('hf-con-block') && oThis.parents('.hf-con').remove();
            });
        }
    });

    /!*<!--删除评论块-->*!/

    $('.commentAll').on('click', '.removeBlock', function () {
        let oT = $(this).parents('.date-dz-right').parents('.date-dz').parents('.all-pl-con');
        if (oT.siblings('.all-pl-con').length >= 1) {
            oT.remove();
        } else {
            $(this).parents('.date-dz-right').parents('.date-dz').parents('.all-pl-con').parents('.hf-list-con').css('display', 'none')
            oT.remove();
        }
        $(this).parents('.date-dz-right').parents('.date-dz').parents('.comment-show-con-list').parents('.comment-show-con').remove();

    })

    /!*<!--点赞-->*!/

    $('.comment-show').on('click', '.date-dz-z', function () {
        let zNum = $(this).find('.z-num').html();
        if ($(this).is('.date-dz-z-click')) {
            zNum--;
            $(this).removeClass('date-dz-z-click red');
            $(this).find('.z-num').html(zNum);
            $(this).find('.date-dz-z-click-red').removeClass('red');
        } else {
            zNum++;
            $(this).addClass('date-dz-z-click');
            $(this).find('.z-num').html(zNum);
            $(this).find('.date-dz-z-click-red').addClass('red');
        }
    })*/
});
