<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="refresh" content="10">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>考场监控</title>
    <link rel="shortcut icon"
          href="/img/front/favicon.ico"
          type="image/x-icon">
    <link href="/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="/css/indexstyle.css" type="text/css" rel="stylesheet">
    <link href="/css/pt_web_category_index_f4afc3211e6bd6468fa0bd4444fbe79d.css" type="text/css" rel="stylesheet">
    <link href="/css/pt_web_index.web_53cd3c4ea11548eed1e4d7ba855fe904.css" type="text/css" rel="stylesheet">
    <script src="/js/jquery-2.1.4.min.js" type="text/javascript"></script>
    <script src="/js/bootstrap.min.js" type="text/javascript"></script>


</head>
<style>
    .chapter a {
        color: #5a5c5f;
    }

    ul.chapter-sub.chapter-resource li {
        color: #0089D2;
        font-size: 16px;
    }
    table.imagetable {
        font-family: verdana,arial,sans-serif;
        font-size:12px;
        color:#333333;
        width: 100%;
        border-width: 1px;
        border-color: #999999;
        border-collapse: collapse;
    }
    table.imagetable th {
        background:#b5cfd2 url('cell-blue.jpg');
        border-width: 1px;
        padding: 8px;
        border-style: solid;
        border-color: #999999;
    }
    table.imagetable td {
        background:#dcddc0 url('cell-grey.jpg');
        border-width: 1px;
        padding: 8px;
        border-style: solid;
        border-color: #999999;
    }
</style>

<body>
<#--<script>-->
<#--    <@shiro.lacksRole name="student">-->
<#--    window.location.href = '/admin';-->
<#--    </@shiro.lacksRole>-->
<#--</script>-->
<!--页头-->
<#--<nav class="nav navbar navbar-default"style="height:100px;">-->
<#--    <div class="container-fluid">-->
<#--        <!-- 导航栏头部 &ndash;&gt;-->
<#--        <div class="navbar-header" >-->
<#--            <!-- 折叠导航栏的按钮 &ndash;&gt;-->
<#--            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#collapsenav"-->
<#--                    aria-expanded="false">-->
<#--                <span class="sr-only">Toggle navigation</span>-->
<#--                <span class="icon-bar"></span>-->
<#--                <span class="icon-bar"></span>-->
<#--                <span class="icon-bar"></span>-->
<#--            </button>-->
<#--            <!-- logo &ndash;&gt;-->
<#--            <div class="logo-contanier">-->
<#--                <!--a class="navbar-brand" href="/index">-->
<#--                    <img alt="Brand" src="/img/front/index_ico_03.png" style="width:200px;margin-top:1px;margin-left: 80px;">-->
<#--                </a&ndash;&gt;-->
<#--                <span class="slogan"><p class="navbar-text" style="width: 240px; font-size: 25px;margin-left: 25px;">监考中心</p></span>-->
<#--            </div>-->
<#--        </div>-->
<#--        <!-- 导航栏菜单 &ndash;&gt;-->
<#--        <div class="collapse navbar-collapse" id="collapsenav">-->
<#--            <ul class="nav navbar-nav navbar-left">-->
<#--                <li><a href="">在线考生</a></li>-->
<#--                <li><a href="javascript:void(0);" onclick="toMyCourse()">考场监控</a></li>-->
<#--            </ul>-->
<#--        </div>-->
<#--        </div>-->
<#--</nav>-->
<!-- 毛玻璃特效 -->
<#--<div class="bk" id="bkimg" style="background-image: url(/img/front/banner3.png);"></div>-->

<!-- 分割线 -->
  <#include '/front/inc/examControllunbotu.ftl'/>
<div id="g-body" style="top: -460px; position: relative">
    <div id="j-content" class="g-flow-wide f-cb">
        <div class="g-main-content f-fl">
            <div class="m-bread f-f0">
                <div class="m-recCourse-title">
                    <div style="text-align: center;font-size: 25px">《${testPaperOne.testName}》</div>
                    <nav class="nav navbar navbar-default"style="height:90px;">
                    <div class="collapse navbar-collapse" id="collapsenav">
                        <ul class="nav navbar-nav navbar-left">
                            <li><a href="/onlineStudent?cid=${cid}&tid=${tid}&paperId=${paperId}">在线考生</a></li>
                            <li><a href="/infoStudent?cid=${cid}&tid=${tid}&paperId=${paperId}">考场监控</a></li>
                        </ul>

                    </div>
                        </nav>
                </div>
            </div>
        </div>


        <div id="j-courseCardListBox" class="m-course-card-list f-cb">
            <div style="text-align: center;font-size: 15px">总人数(${PeopleSum}）&nbsp;&nbsp;登录（${LoginPeople}）&nbsp;&nbsp;掉线（${OffLinePeople}）&nbsp;&nbsp;缺考（${NoLoginPeople}）&nbsp;&nbsp;交卷（${submitPeople}）&nbsp;&nbsp;评分（${scorePeople}）&nbsp;&nbsp;</div>
            <div>
                <div class="m-course-list">
                    <div class="container-fluid" style=" width: 100%;background-color: #eef7ef;overflow: auto;">
                        <table class="imagetable" style="width: 45%;float: left;margin-top: 18px">
                            <tr>
                                <th colspan="6">缺考考生</th>
                            </tr>
                            <tr>
                                <th>试卷</th>
<#--                                <th>时间</th>-->
<#--                                <th>地点</th>-->
                                <#--                                <th>年级</th>-->
                                <#--                                <th>班级</th>-->
                                <th>学号</th>
                                <th>姓名</th>
<#--                                <th>评分</th>-->
                                <#--                                <th>登录状态</th>-->

                            </tr>
                            <#list listFour as c>
                                <tr>
                                    <th>${c.testName}</th>&nbsp;
<#--                                    <th>${c.loginTime?string('yyyy-MM-dd HH:mm:ss')}</th>-->
<#--                                    <th>${c.loginLocation} </th>-->
                                    <#--                                        <th>${c.tcName}</th>-->
                                    <#--                                        <th>${c.tgName} </th>&nbsp;-->
                                    <th>${c.stuNum} </th>
                                    <th>${c.sysName} </th>
<#--                                    <#if c.stuScore =='0'>-->
<#--                                        <th>未评分</th>-->
<#--                                    </#if>-->
<#--                                    <#if c.stuScore !='0'>-->
<#--                                        <th>已评分</th>-->
<#--                                    </#if>-->
                                    <#--                                        <th>${c.browser} </th>-->
                                    <#--                                        <#if c.status=='0'>-->
                                    <#--                                            <th>成功</th>-->
                                    <#--                                        </#if>-->
                                    <#--                                        <#if c.status!='0'>-->
                                    <#--                                            <th>失败</th>-->
                                    <#--                                        </#if>-->

                                </tr>
                            </#list>
                        </table>
                        <table class="imagetable" style="width: 50%;float: right">
                            <tr>
                                <th colspan="6">交卷考生</th>
                            </tr>
                            <tr>
                                <th>试卷</th>
                                <th>时间</th>
<#--                                <th>年级</th>-->
<#--                                <th>班级</th>-->
                                <th>学号</th>
                                <th>姓名</th>
                                <th>评分</th>
<#--                                <th>登录状态</th>-->

                            </tr>
                            <#list listThree as c>
                                    <tr>
                                        <th>${c.testName}</th>&nbsp;
                                        <th>${c.stuStartTime}</th>
<#--                                        <th>${c.tcName}</th>-->
<#--                                        <th>${c.tgName} </th>&nbsp;-->
                                        <th>${c.stuNum} </th>
                                        <th>${c.sysName} </th>
                                        <#if c.madeScore =='否'>
                                            <th>未评分</th>
                                        </#if>
                                        <#if c.madeScore =='是'>
                                            <th>已评分</th>
                                        </#if>
<#--                                        <th>${c.browser} </th>-->
<#--                                        <#if c.status=='0'>-->
<#--                                            <th>成功</th>-->
<#--                                        </#if>-->
<#--                                        <#if c.status!='0'>-->
<#--                                            <th>失败</th>-->
<#--                                        </#if>-->

                                    </tr>
                            </#list>
                        </table>
                        <table class="imagetable" style="width: 50%;float: right;margin-top: 20%;">
                            <tr>
                                <th colspan="6">掉线考生</th>
                            </tr>
                            <tr>
                                <th>试卷</th>
                                <#--                                <th>年级</th>-->
                                <#--                                <th>班级</th>-->
                                <th>学号</th>
                                <th>姓名</th>
                                <th>掉线时间</th>
                                <#--                                <th>登录状态</th>-->

                            </tr>
                            <#list listOffLine as c>
                                <tr>
                                    <th>${c.testName}</th>&nbsp;
                                    <#--                                        <th>${c.tcName}</th>-->
                                    <#--                                        <th>${c.tgName} </th>&nbsp;-->
                                    <th>${c.stuNum} </th>
                                    <th>${c.sysName} </th>
                                    <th>${c.updateTime?string('yyyy-MM-dd HH:mm:ss')}</th>
                                    <#--                                        <th>${c.browser} </th>-->
                                    <#--                                        <#if c.status=='0'>-->
                                    <#--                                            <th>成功</th>-->
                                    <#--                                        </#if>-->
                                    <#--                                        <#if c.status!='0'>-->
                                    <#--                                            <th>失败</th>-->
                                    <#--                                        </#if>-->

                                </tr>
                            </#list>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 页底 -->

<footer>
    <div class="container-fluid" style="margin-top: -200px;">
        <div class="row">
            <div class="footer_item col-xs-7">
                <div class="col-xs-12"><p>智慧教育云平台版权所有| 监考端</p></div>
            </div>
            <!--img src="/img/front/logo-black.png"-->
        </div>
    </div>
</footer>

<script type="text/javascript">

    var i = 1;
    $(document).ready(function () {
        myCourse();
        initPath();
    });


    let prefix = "/user/exam/control";

    function myCourse() {
        $.ajax({
            type: "post",

            url: prefix + "/list",
            data: {
                pageNum: 0,
                pageSize: 6
            },
            cache: true,
            dataType: 'json',
            async: true,  //同步处理
            success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success

                console.log(data.code);
                if (data.code === 0) {
                    fillCourse(data.rows);
                }
                /*if(data == "error"){

                    $("#ziyuanList").append("<h4>缺少资源！</h4>");
                }else{
                    var json = JSON.parse(data);
                    maxnumber = json.length - 1;
                    $("#courseselectMenu>div").remove();
                    $("#courseselectMenu>h4").remove();
                    for (var i = 0; i < 6; i++) {  //只显示前6条
                        $("#ziyuanList").append(
                            "<li><a title=\"下载该资源\" href=\"#\" url="+json[maxnumber-i].fileRolad+"  name="+json[maxnumber-i].courseWareName+" onclick=\"oneDownload(this)\">"+json[maxnumber-i].courseWareName+"</a></li>"

                        );
                    };
                };*/
            }
        });
    }




    function fillCourse(data) {
        let i = 1;
        let template = `<div class="class="u-clist f-bgw f-cb f-pr j-href ga-click" data-action="课程点击">
                <a href="/user/course/info/{0}">

                        <div class="g-sd1">
		<div class="u-img f-fl">
                        		<img  src="/img/course/course{3}.jpg"height="150px" width="265px">
             		</div>
	         </div>
               <div class="g-mn1">
	<div class="g-mn1c" style="margin_left: 290px;">
	<div class="cnt f-pr">
	<div class="t1 f-f0 f-cb first-row">
		<a href="/user/course/info/{0}" target="_blank">
		<span class=" u-course-name f-thide"></span>
		</a>
		<a target="_blank" data-action="课程学习" style="background-color: #CBA265">
		<span>课程学习</span>
	<div class="more-tag-info-hover">
	<div class="more-tag-info">
	<span>智慧教育云服务平台认定的课程</span>
	<div class="tipBg f-pa"></div>
	<div class="tipTp f-pa">
	</div></div></div></a></div>
	<div class="t2 f-fc3 f-nowrp f-f0">
	<a class="t21 f-fc9"  target="_blank"></a>
	<a class="f-fc9"  target="_blank"></a>
	</div>
	<a >
	<span class="p5 brief f-ib f-f0 f-cb">计算机是运算工具，更是创新平台，高效有趣地利用计算机需要更简洁实用的编程语言。它是当今世界受欢迎的编程语言，学好它终身受用。请跟随我们，学习并掌握JAVA语言。</span></a>
	<div class="t2 f-fc3 f-nowrp f-f0 margin-top0">
	<div class="over "><span class="f-icon clock u-icon-clock2"></span><span class="txt">开课进行中</span></div>
	</div></div></div></div>
    <div style="text-align: right;margin:25px;"><a href="/user/course/learnone/{0}" class="btn btn-warning btn-rounded">进入考试
                        </a></div>

                        <div class="infotitle" style="padding: 20px 5px 26px 0px;">
                            {1}<a href="javaScript:void(0)" style="float:right;"></a>
                        </div>
                </a>
                </div>`;
        let tmp = data.map(d => $.format(template, d.teacherCourse.id, d.course.name, d.teacherCourse.courseBriefIntroduction, i++)).join("");
        $("#mycourse").html(tmp);
    }
}




<#--    //列出资源-->
<#--    function getZiyuanList() {-->

<#--        $.ajax({-->
<#--            type: "post",-->
<#--            url: "${basePath}/courseUP/CourseUploadAction!findAllZiyuan.action?time=" + new Date(),-->
<#--            data: {},-->
<#--            cache: false,-->
<#--            async: false,  //同步处理-->
<#--            success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success-->
<#--                if (data == "error") {-->

<#--                    $("#ziyuanList").append("<h4>缺少资源！</h4>");-->
<#--                } else {-->
<#--                    var json = JSON.parse(data);-->
<#--                    maxnumber = json.length - 1;-->
<#--                    $("#courseselectMenu>div").remove();-->
<#--                    $("#courseselectMenu>h4").remove();-->
<#--                    for (var i = 0; i < 6; i++) {  //只显示前6条-->
<#--                        $("#ziyuanList").append(-->
<#--                            "<li><a title=\"下载该资源\" href=\"#\" url=" + json[maxnumber - i].fileRolad + "  name=" + json[maxnumber - i].courseWareName + " onclick=\"oneDownload(this)\">" + json[maxnumber - i].courseWareName + "</a></li>"-->
<#--                        );-->
<#--                    }-->
<#--                    ;-->
<#--                }-->
<#--                ;-->
<#--            }-->
<#--        });-->

<#--    };-->





<#--    function getGoodBrother() {-->

<#--        $.ajax({-->
<#--            type: "post",-->
<#--            url: "${basePath}/talk/talkMangeAction!findGoodBrother.action?course=1&sort=1&&forumid=" + "2c9f56fa632a527401633ec77c120039",-->
<#--            cache: false,-->
<#--            async: false,  //同步处理-->
<#--            success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success-->
<#--                if (data == "error") {-->
<#--                    $("#brothero").append("<h4>暂无提问！</h4>");-->
<#--                } else {-->
<#--                    var json = JSON.parse(data);-->
<#--                    $("#brothero>div").remove();-->
<#--                    $("#brothero>h4").remove();-->

<#--                    for (var i = 0; i < 5 && i < json.length; i++) {  //只显示前5条-->
<#--                        $("#brothero").append(-->
<#--                            "<li><a title=\"点击查看\" talkid=" + json[i].id + " onclick=\"answerRead(this)\">" + json[i].title + "</a></li>"-->
<#--                        );-->
<#--                    }-->
<#--                    ;-->
<#--                }-->
<#--                ;-->
<#--            }-->
<#--        });-->

<#--        $.ajax({-->
<#--            type: "post",-->
<#--            url: "${basePath}/talk/talkMangeAction!findGoodBrother.action?course=1&sort=1&&forumid=" + "2c9f56fa632a527401633ec6d4370038",-->
<#--            cache: false,-->
<#--            async: false,  //同步处理-->
<#--            success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success-->
<#--                if (data == "error") {-->
<#--                    $("#brothertw").append("<h4>暂无提问！</h4>");-->
<#--                } else {-->
<#--                    var json = JSON.parse(data);-->
<#--                    $("#brothertw>div").remove();-->
<#--                    $("#brothertw>h4").remove();-->

<#--                    for (var i = 0; i < 5 && i < json.length; i++) {  //只显示前5条-->
<#--                        $("#brothertw").append(-->
<#--                            "<li><a title=\"点击查看\" talkid=" + json[i].id + " onclick=\"answerRead(this)\">" + json[i].title + "</a></li>"-->
<#--                        );-->
<#--                    }-->
<#--                    ;-->
<#--                }-->
<#--                ;-->
<#--            }-->
<#--        });-->

<#--        $.ajax({-->
<#--            type: "post",-->
<#--            url: "${basePath}/talk/talkMangeAction!findGoodBrother.action?course=1&sort=1&&forumid=" + "2c9f56fa632a527401633ec7d2f3003a",-->
<#--            cache: false,-->
<#--            async: false,  //同步处理-->
<#--            success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success-->
<#--                if (data == "error") {-->
<#--                    $("#brothert").append("<h4>暂无提问！</h4>");-->
<#--                } else {-->
<#--                    var json = JSON.parse(data);-->
<#--                    $("#brothert>div").remove();-->
<#--                    $("#brothert>h4").remove();-->

<#--                    for (var i = 0; i < 5 && i < json.length; i++) {  //只显示前5条-->
<#--                        $("#brothert").append(-->
<#--                            "<li><a title=\"点击查看\" talkid=" + json[i].id + " onclick=\"answerRead(this)\">" + json[i].title + "</a></li>"-->
<#--                        );-->
<#--                    }-->
<#--                    ;-->
<#--                }-->
<#--                ;-->
<#--            }-->
<#--        });-->

<#--    };-->


<#--    //查看问题详情-->
<#--    function answerRead(obj) {-->
<#--        var talkId = $(obj).attr("talkid");-->
<#--        window.location.href = "/user/topic/detail/" + talkId;-->
<#--    }-->


<#--    function swipperclick() {-->
<#--        var buttons = document.getElementsByClassName("swiper-button")[0].getElementsByTagName("span");-->
<#--        for (var j = 0; j < buttons.length; j++) {-->
<#--            (function (j) {-->
<#--                buttons[j].onclick = function () {-->
<#--                    var clickIndex = parseInt(this.getAttribute("index"));-->
<#--                    var i = clickIndex;-->
<#--                    document.getElementById("swipperimg").src = "${basePath}/static/plugins/home/img/banner" + i + ".png";-->
<#--                    //document.getElementById("imgurl").href=urllist[i];-->
<#--                    document.getElementById("imgurl").href = "#";-->
<#--                    document.getElementById("bkimg").style = "background-image: url(${basePath}/static/plugins/home/img/banner" + i + ".png);";-->
<#--                    changeimg();-->
<#--                    window.clearInterval(time1);-->
<#--                    time1 = setInterval("changeimg()", 3000);-->
<#--                }-->
<#--            })(j)-->
<#--        }-->
<#--    }-->

<#--    function changeimg() {-->
<#--        var buttons = document.getElementsByClassName("swiper-button")[0].getElementsByTagName("span");-->
<#--        document.getElementById("swipperimg").src = "${basePath}/static/plugins/home/img/banner" + i + ".png";-->
<#--        //document.getElementById("imgurl").href=urllist[i];-->
<#--        document.getElementById("imgurl").href = "#";-->
<#--        document.getElementById("bkimg").style = "background-image: url(${basePath}/static/plugins/home/img/banner" + i + ".png);";-->
<#--        for (var k = 0; k < buttons.length; k++) {-->
<#--            if (buttons[k].style = "background: #d6d6d6;") {-->
<#--                buttons[k].style = "";-->
<#--            }-->
<#--        }-->
<#--        buttons[i - 1].style = "background: #d6d6d6;";-->
<#--        i++;-->
<#--        if (i > 4) {-->
<#--            i = 1;-->
<#--        }-->
<#--    }-->

<#--    $.extend({-->
<#--        /**-->
<#--         * 使用参数格式化字符串-->
<#--         * source：字符串模式 abcdef{0}-{1}，-->
<#--         * params：参数数组，参数序号对应模式中的下标-->
<#--         */-->
<#--        format: function (source, params) {-->
<#--            if (arguments.length == 1)-->
<#--                return function () {-->
<#--                    var args = $.makeArray(arguments);-->
<#--                    args.unshift(source);-->
<#--                    return $.format.apply(this, args);-->
<#--                };-->
<#--            if (arguments.length > 2 && params.constructor != Array) {-->
<#--                params = $.makeArray(arguments).slice(1);-->
<#--            }-->
<#--            if (params.constructor != Array) {-->
<#--                params = [params];-->
<#--            }-->
<#--            $.each(params, function (i, n) {-->
<#--                source = source.replace(new RegExp('\\{' + i + '\\}', 'g'), n);-->
<#--            });-->
<#--            return source;-->
<#--        }-->

<#--    });-->

<#--    function initPath() {-->
<#--        let url = new URL(window.location.href);-->
<#--        let params = new URLSearchParams(url.search);-->
<#--        if (params.get("cur") === '_') {-->
<#--            document.getElementById('mycourses').scrollIntoView();-->
<#--        }-->
<#--    }-->
<#--</script>-->
<#--<script>-->
<#--    let stuid="${stuid}";-->
<#--    console.log("stuid:"+stuid);-->
<#--    (function () {-->

<#--    })();-->


<#--    //数字转换-->
<#--    //阿拉伯数字转换为简写汉字-->
<#--    //转自：https://www.cnblogs.com/wangqiideal/p/5579807.html-->
<#--    function Arabia_To_SimplifiedChinese(Num) {-->
<#--        for (i = Num.length - 1; i >= 0; i--) {-->
<#--            Num = Num.replace(",", "")//替换Num中的“,”-->
<#--            Num = Num.replace(" ", "")//替换Num中的空格-->
<#--        }-->
<#--        if (isNaN(Num)) { //验证输入的字符是否为数字-->
<#--            return;-->
<#--        }-->
<#--        //字符处理完毕后开始转换，采用前后两部分分别转换-->
<#--        part = String(Num).split(".");-->
<#--        newchar = "";-->
<#--        //小数点前进行转化-->
<#--        for (i = part[0].length - 1; i >= 0; i--) {-->
<#--            if (part[0].length > 10) {-->
<#--                //alert("位数过大，无法计算");-->
<#--                return "";-->
<#--            }//若数量超过拾亿单位，提示-->
<#--            tmpnewchar = ""-->
<#--            perchar = part[0].charAt(i);-->
<#--            switch (perchar) {-->
<#--                case "0":-->
<#--                    tmpnewchar = "零" + tmpnewchar;-->
<#--                    break;-->
<#--                case "1":-->
<#--                    tmpnewchar = "一" + tmpnewchar;-->
<#--                    break;-->
<#--                case "2":-->
<#--                    tmpnewchar = "二" + tmpnewchar;-->
<#--                    break;-->
<#--                case "3":-->
<#--                    tmpnewchar = "三" + tmpnewchar;-->
<#--                    break;-->
<#--                case "4":-->
<#--                    tmpnewchar = "四" + tmpnewchar;-->
<#--                    break;-->
<#--                case "5":-->
<#--                    tmpnewchar = "五" + tmpnewchar;-->
<#--                    break;-->
<#--                case "6":-->
<#--                    tmpnewchar = "六" + tmpnewchar;-->
<#--                    break;-->
<#--                case "7":-->
<#--                    tmpnewchar = "七" + tmpnewchar;-->
<#--                    break;-->
<#--                case "8":-->
<#--                    tmpnewchar = "八" + tmpnewchar;-->
<#--                    break;-->
<#--                case "9":-->
<#--                    tmpnewchar = "九" + tmpnewchar;-->
<#--                    break;-->
<#--            }-->
<#--            switch (part[0].length - i - 1) {-->
<#--                case 0:-->
<#--                    tmpnewchar = tmpnewchar;-->
<#--                    break;-->
<#--                case 1:-->
<#--                    if (perchar != 0) tmpnewchar = tmpnewchar + "十";-->
<#--                    break;-->
<#--                case 2:-->
<#--                    if (perchar != 0) tmpnewchar = tmpnewchar + "百";-->
<#--                    break;-->
<#--                case 3:-->
<#--                    if (perchar != 0) tmpnewchar = tmpnewchar + "千";-->
<#--                    break;-->
<#--                case 4:-->
<#--                    tmpnewchar = tmpnewchar + "万";-->
<#--                    break;-->
<#--                case 5:-->
<#--                    if (perchar != 0) tmpnewchar = tmpnewchar + "十";-->
<#--                    break;-->
<#--                case 6:-->
<#--                    if (perchar != 0) tmpnewchar = tmpnewchar + "百";-->
<#--                    break;-->
<#--                case 7:-->
<#--                    if (perchar != 0) tmpnewchar = tmpnewchar + "千";-->
<#--                    break;-->
<#--                case 8:-->
<#--                    tmpnewchar = tmpnewchar + "亿";-->
<#--                    break;-->
<#--                case 9:-->
<#--                    tmpnewchar = tmpnewchar + "十";-->
<#--                    break;-->
<#--            }-->
<#--            newchar = tmpnewchar + newchar;-->
<#--        }-->
<#--        //替换所有无用汉字，直到没有此类无用的数字为止-->
<#--        while (newchar.search("零零") != -1 || newchar.search("零亿") != -1 || newchar.search("亿万") != -1 || newchar.search("零万") != -1) {-->
<#--            newchar = newchar.replace("零亿", "亿");-->
<#--            newchar = newchar.replace("亿万", "亿");-->
<#--            newchar = newchar.replace("零万", "万");-->
<#--            newchar = newchar.replace("零零", "零");-->
<#--        }-->
<#--        //替换以“一十”开头的，为“十”-->
<#--        if (newchar.indexOf("一十") == 0) {-->
<#--            newchar = newchar.substr(1);-->
<#--        }-->
<#--        //替换以“零”结尾的，为“”-->
<#--        if (newchar.lastIndexOf("零") == newchar.length - 1) {-->
<#--            newchar = newchar.substr(0, newchar.length - 1);-->
<#--        }-->
<#--        return newchar;-->
<#--    }-->
<#--</script>-->
<#--    <script>-->
<#--        let stuid="${stuid}";-->
<#--        console.log("stuid:"+stuid);-->
<#--        (function () {-->
<#--            $.ajax({-->
<#--                type: "post",-->
<#--                url: "/jiaowu/integral/getsort?studentId="+stuid,-->
<#--                dataType: "json",-->
<#--                error: function (request) {-->

<#--                },-->
<#--                success: function (data) {-->
<#--                    $('#menu1').empty();-->
<#--                    $('#menu1').append('<ul class="menu_ul"style="position: relative;">' +-->
<#--                            '<li class="item">' +-->
<#--                            '<div class="inner_item">' +-->
<#--                            ' <a>年级积分排名</a>' +-->
<#--                            '</div>' +-->
<#--                            ' </li>');-->
<#--                    for (let j = 0; j < 6; j++) {-->
<#--                        console.log(data[j].userName+":"+data[j].credit);-->
<#--                        if(data[j].credit=="0"){-->
<#--                            return;-->
<#--                        }-->
<#--                        let no = Arabia_To_SimplifiedChinese(j+1);-->
<#--                        $('#menu1').append('<li class="item">' +-->
<#--                                '<div class="inner_item"><a>第'+ no +'名：'+data[j].userName+":"+data[j].credit+"个积分"+-->
<#--                                '</a>' +-->
<#--                                '</div>' +-->
<#--                                '</li>' +-->
<#--                                '</ul>');-->
<#--                    }-->

<#--                }-->
<#--            });-->
<#--        })();-->


<#--        //数字转换-->
<#--        //阿拉伯数字转换为简写汉字-->
<#--        //转自：https://www.cnblogs.com/wangqiideal/p/5579807.html-->
<#--        function Arabia_To_SimplifiedChinese(Num) {-->
<#--            for (i = Num.length - 1; i >= 0; i--) {-->
<#--                Num = Num.replace(",", "")//替换Num中的“,”-->
<#--                Num = Num.replace(" ", "")//替换Num中的空格-->
<#--            }-->
<#--            if (isNaN(Num)) { //验证输入的字符是否为数字-->
<#--                return;-->
<#--            }-->
<#--            //字符处理完毕后开始转换，采用前后两部分分别转换-->
<#--            part = String(Num).split(".");-->
<#--            newchar = "";-->
<#--            //小数点前进行转化-->
<#--            for (i = part[0].length - 1; i >= 0; i--) {-->
<#--                if (part[0].length > 10) {-->
<#--                    //alert("位数过大，无法计算");-->
<#--                    return "";-->
<#--                }//若数量超过拾亿单位，提示-->
<#--                tmpnewchar = ""-->
<#--                perchar = part[0].charAt(i);-->
<#--                switch (perchar) {-->
<#--                    case "0":-->
<#--                        tmpnewchar = "零" + tmpnewchar;-->
<#--                        break;-->
<#--                    case "1":-->
<#--                        tmpnewchar = "一" + tmpnewchar;-->
<#--                        break;-->
<#--                    case "2":-->
<#--                        tmpnewchar = "二" + tmpnewchar;-->
<#--                        break;-->
<#--                    case "3":-->
<#--                        tmpnewchar = "三" + tmpnewchar;-->
<#--                        break;-->
<#--                    case "4":-->
<#--                        tmpnewchar = "四" + tmpnewchar;-->
<#--                        break;-->
<#--                    case "5":-->
<#--                        tmpnewchar = "五" + tmpnewchar;-->
<#--                        break;-->
<#--                    case "6":-->
<#--                        tmpnewchar = "六" + tmpnewchar;-->
<#--                        break;-->
<#--                    case "7":-->
<#--                        tmpnewchar = "七" + tmpnewchar;-->
<#--                        break;-->
<#--                    case "8":-->
<#--                        tmpnewchar = "八" + tmpnewchar;-->
<#--                        break;-->
<#--                    case "9":-->
<#--                        tmpnewchar = "九" + tmpnewchar;-->
<#--                        break;-->
<#--                }-->
<#--                switch (part[0].length - i - 1) {-->
<#--                    case 0:-->
<#--                        tmpnewchar = tmpnewchar;-->
<#--                        break;-->
<#--                    case 1:-->
<#--                        if (perchar != 0) tmpnewchar = tmpnewchar + "十";-->
<#--                        break;-->
<#--                    case 2:-->
<#--                        if (perchar != 0) tmpnewchar = tmpnewchar + "百";-->
<#--                        break;-->
<#--                    case 3:-->
<#--                        if (perchar != 0) tmpnewchar = tmpnewchar + "千";-->
<#--                        break;-->
<#--                    case 4:-->
<#--                        tmpnewchar = tmpnewchar + "万";-->
<#--                        break;-->
<#--                    case 5:-->
<#--                        if (perchar != 0) tmpnewchar = tmpnewchar + "十";-->
<#--                        break;-->
<#--                    case 6:-->
<#--                        if (perchar != 0) tmpnewchar = tmpnewchar + "百";-->
<#--                        break;-->
<#--                    case 7:-->
<#--                        if (perchar != 0) tmpnewchar = tmpnewchar + "千";-->
<#--                        break;-->
<#--                    case 8:-->
<#--                        tmpnewchar = tmpnewchar + "亿";-->
<#--                        break;-->
<#--                    case 9:-->
<#--                        tmpnewchar = tmpnewchar + "十";-->
<#--                        break;-->
<#--                }-->
<#--                newchar = tmpnewchar + newchar;-->
<#--            }-->
<#--            //替换所有无用汉字，直到没有此类无用的数字为止-->
<#--            while (newchar.search("零零") != -1 || newchar.search("零亿") != -1 || newchar.search("亿万") != -1 || newchar.search("零万") != -1) {-->
<#--                newchar = newchar.replace("零亿", "亿");-->
<#--                newchar = newchar.replace("亿万", "亿");-->
<#--                newchar = newchar.replace("零万", "万");-->
<#--                newchar = newchar.replace("零零", "零");-->
<#--            }-->
<#--            //替换以“一十”开头的，为“十”-->
<#--            if (newchar.indexOf("一十") == 0) {-->
<#--                newchar = newchar.substr(1);-->
<#--            }-->
<#--            //替换以“零”结尾的，为“”-->
<#--            if (newchar.lastIndexOf("零") == newchar.length - 1) {-->
<#--                newchar = newchar.substr(0, newchar.length - 1);-->
<#--            }-->
<#--            return newchar;-->
<#--        }-->
<#--    </script>-->
<#--    <script>-->
<#--        let stuid="${stuid}";-->
<#--        console.log("stuid:"+stuid);-->
<#--        (function () {-->
<#--            $.ajax({-->
<#--                type: "post",-->
<#--                url: "/jiaowu/integral/getsort?studentId="+stuid,-->
<#--                dataType: "json",-->
<#--                error: function (request) {-->

<#--                },-->
<#--                success: function (data) {-->
<#--                    $('#menu2').empty();-->
<#--                    $('#menu2').append('<ul class="menu_ul"style="position: relative;">' +-->
<#--                            '<li class="item">' +-->
<#--                            '<div class="inner_item">' +-->
<#--                            ' <a>学院积分排名</a>' +-->
<#--                            '</div>' +-->
<#--                            ' </li>');-->
<#--                    for (let j = 0; j < 6; j++) {-->
<#--                        console.log(data[j].userName+":"+data[j].credit);-->
<#--                        if(data[j].credit=="0"){-->
<#--                            return;-->
<#--                        }-->
<#--                        let no = Arabia_To_SimplifiedChinese(j+1);-->
<#--                        $('#menu2').append('<li class="item">' +-->
<#--                                '<div class="inner_item"><a>第'+ no +'名：'+data[j].userName+":"+data[j].credit+"个积分"+-->
<#--                                '</a>' +-->
<#--                                '</div>' +-->
<#--                                '</li>' +-->
<#--                                '</ul>');-->
<#--                    }-->

<#--                }-->
<#--            });-->
<#--        })();-->


<#--        //数字转换-->
<#--        //阿拉伯数字转换为简写汉字-->
<#--        //转自：https://www.cnblogs.com/wangqiideal/p/5579807.html-->
<#--        function Arabia_To_SimplifiedChinese(Num) {-->
<#--            for (i = Num.length - 1; i >= 0; i--) {-->
<#--                Num = Num.replace(",", "")//替换Num中的“,”-->
<#--                Num = Num.replace(" ", "")//替换Num中的空格-->
<#--            }-->
<#--            if (isNaN(Num)) { //验证输入的字符是否为数字-->
<#--                return;-->
<#--            }-->
<#--            //字符处理完毕后开始转换，采用前后两部分分别转换-->
<#--            part = String(Num).split(".");-->
<#--            newchar = "";-->
<#--            //小数点前进行转化-->
<#--            for (i = part[0].length - 1; i >= 0; i--) {-->
<#--                if (part[0].length > 10) {-->
<#--                    //alert("位数过大，无法计算");-->
<#--                    return "";-->
<#--                }//若数量超过拾亿单位，提示-->
<#--                tmpnewchar = ""-->
<#--                perchar = part[0].charAt(i);-->
<#--                switch (perchar) {-->
<#--                    case "0":-->
<#--                        tmpnewchar = "零" + tmpnewchar;-->
<#--                        break;-->
<#--                    case "1":-->
<#--                        tmpnewchar = "一" + tmpnewchar;-->
<#--                        break;-->
<#--                    case "2":-->
<#--                        tmpnewchar = "二" + tmpnewchar;-->
<#--                        break;-->
<#--                    case "3":-->
<#--                        tmpnewchar = "三" + tmpnewchar;-->
<#--                        break;-->
<#--                    case "4":-->
<#--                        tmpnewchar = "四" + tmpnewchar;-->
<#--                        break;-->
<#--                    case "5":-->
<#--                        tmpnewchar = "五" + tmpnewchar;-->
<#--                        break;-->
<#--                    case "6":-->
<#--                        tmpnewchar = "六" + tmpnewchar;-->
<#--                        break;-->
<#--                    case "7":-->
<#--                        tmpnewchar = "七" + tmpnewchar;-->
<#--                        break;-->
<#--                    case "8":-->
<#--                        tmpnewchar = "八" + tmpnewchar;-->
<#--                        break;-->
<#--                    case "9":-->
<#--                        tmpnewchar = "九" + tmpnewchar;-->
<#--                        break;-->
<#--                }-->
<#--                switch (part[0].length - i - 1) {-->
<#--                    case 0:-->
<#--                        tmpnewchar = tmpnewchar;-->
<#--                        break;-->
<#--                    case 1:-->
<#--                        if (perchar != 0) tmpnewchar = tmpnewchar + "十";-->
<#--                        break;-->
<#--                    case 2:-->
<#--                        if (perchar != 0) tmpnewchar = tmpnewchar + "百";-->
<#--                        break;-->
<#--                    case 3:-->
<#--                        if (perchar != 0) tmpnewchar = tmpnewchar + "千";-->
<#--                        break;-->
<#--                    case 4:-->
<#--                        tmpnewchar = tmpnewchar + "万";-->
<#--                        break;-->
<#--                    case 5:-->
<#--                        if (perchar != 0) tmpnewchar = tmpnewchar + "十";-->
<#--                        break;-->
<#--                    case 6:-->
<#--                        if (perchar != 0) tmpnewchar = tmpnewchar + "百";-->
<#--                        break;-->
<#--                    case 7:-->
<#--                        if (perchar != 0) tmpnewchar = tmpnewchar + "千";-->
<#--                        break;-->
<#--                    case 8:-->
<#--                        tmpnewchar = tmpnewchar + "亿";-->
<#--                        break;-->
<#--                    case 9:-->
<#--                        tmpnewchar = tmpnewchar + "十";-->
<#--                        break;-->
<#--                }-->
<#--                newchar = tmpnewchar + newchar;-->
<#--            }-->
<#--            //替换所有无用汉字，直到没有此类无用的数字为止-->
<#--            while (newchar.search("零零") != -1 || newchar.search("零亿") != -1 || newchar.search("亿万") != -1 || newchar.search("零万") != -1) {-->
<#--                newchar = newchar.replace("零亿", "亿");-->
<#--                newchar = newchar.replace("亿万", "亿");-->
<#--                newchar = newchar.replace("零万", "万");-->
<#--                newchar = newchar.replace("零零", "零");-->
<#--            }-->
<#--            //替换以“一十”开头的，为“十”-->
<#--            if (newchar.indexOf("一十") == 0) {-->
<#--                newchar = newchar.substr(1);-->
<#--            }-->
<#--            //替换以“零”结尾的，为“”-->
<#--            if (newchar.lastIndexOf("零") == newchar.length - 1) {-->
<#--                newchar = newchar.substr(0, newchar.length - 1);-->
<#--            }-->
<#--            return newchar;-->
<#--        }-->
<#--    </script>-->
</body>
</html>
