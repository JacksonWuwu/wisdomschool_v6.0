<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>智慧教育云平台-以科技助力教育</title>
    <link rel="shortcut icon"
          href="/img/front/favicon.ico"
          type="image/x-icon">
    <link href="/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="/css/indexstyle.css" type="text/css" rel="stylesheet">
    <script src="/js/jquery-2.1.4.min.js" type="text/javascript"></script>
    <script src="/js/bootstrap.min.js" type="text/javascript"></script>

</head>


<body>
<#--<script>-->
<#--    <@shiro.lacksRole name="student">-->
<#--    window.location.href = '/admin';-->
<#--    </@shiro.lacksRole>-->
<#--</script>-->
<!--页头-->
<#include '/front/inc/header.ftl'/>
<#include '/front/inc/commonJs.ftl'/>

<!-- 毛玻璃特效 -->
<div class="bk" id="bkimg" style="background-image: url(/img/front/banner3.png);"></div>

<!-- banner -->
<div id="banner">
    <!-- 轮播图 -->
    <div class="swiper">
        <a id="imgurl"><img id="swipperimg" src="/img/front/1555298349810_69262.png"></a>
    </div>
</div>

<!-- 分割线 -->
<div class="text-center" id="mycourses" style="top: -26px;">
    <h4>
        <lx>〄</lx>
        相 关 信 息
        <lx>〄</lx>
    </h4>
</div>

<!-- 主要内容 -->
<div class="container-fluid" style="width: 982px">
    <div class="row" id="mycourse">
        <!-- 版块1 -->
        <div class="col-md-4 col-xs-12">
            <div class="information center-block">
                <div class="infotitle">
                    最新资讯<a href="javaScript:void(0)" style="float:right">···</a>
                </div>
                <img class="img-responsive center-block" src="/img/front/newsimg.jpg">
                <ol id="shouyenewslist">

                </ol>
            </div>
        </div>
        <!-- 板块2 &ndash;&gt;-->
        <div class="col-md-4 col-xs-12">
            <div class="information center-block">
                <div class="infotitle">
                    资源下载<a href="/user/resource/0/${tcid}?pageSize=8" style="float:right">···</a>
                </div>
                <img class="img-responsive center-block" src="/img/front/newimg2.jpg">
                <ol id="ziyuanList">

                </ol>
            </div>
        </div>
        <!-- 板块3 -->
        <div class="col-md-4 col-xs-12">
            <div class="information center-block">
                <div class="infotitle">
                    最新提问<a href="javaScript:void(0)" style="float:right">···</a>
                </div>
                <img class="img-responsive center-block" src="/img/front/newsimg3.jpg">
                <ol id="tiwenList">

                </ol>
            </div>
        </div>

    </div>
</div>


<!-- 页底 -->
<footer>
    <div class="container-fluid">
        <div class="row">
            <div class="footer_item col-xs-7">
                <div class="col-xs-12"><p>智慧教育云平台版权所有| 后台管理</p></div>
            </div>
            <!--img src="/img/front/logo-black.png"-->
        </div>
    </div>
</footer>

<script type="text/javascript">
    /*        var urllist = new Array();
            urllist[1]="#";
            urllist[2]="#";
            urllist[3]="#";
            urllist[4]="#";*/
    var i = 1;

    $(document).ready(function () {

        getZiyuanList();

        initPath();
        getTalkList();
    });

    function getshangji(num) {
        if (confirm("是否要下载？") == true) {

            window.location.href = "/resources/shangjiti/shangji" + num + ".doc"+"&token="+localStorage.getItem("token");
        }
    }

    var chapter_josn;

    function fuzhi(data) {

        chapter_josn = eval("(" + data + ")");

    }

    function chapter(obj) {

        for (i = 0; i < chapter_josn.length; i++) {
            if (chapter_josn[i].name.split(" ")[0] == $(obj).attr("value")) {
                window.location.href = "/learn/courseLearnMangeAction!toCourseLearnList.action?id=" + chapter_josn[i].id+"&token="+localStorage.getItem("token");
            }
        }

    }

    //读取总页码or列出全部课件
    function kejianku(dangqianyema) {

        window.location.href = "/courseUP/CourseUploadAction!kejianku.action?DangQianYeMa=" + dangqianyema+"&token="+localStorage.getItem("token");

    }

    //读取总页码or列出全部上机
    function shangjitiku(dangqianyema) {

        window.location.href = "/courseUP/CourseUploadAction!shangjitiku.action?DangQianYeMa=" + dangqianyema+"&token="+localStorage.getItem("token");

    }

    //读取总页码or列出全部试题
    function shitiku(dangqianyema) {

        window.location.href = "/courseUP/CourseUploadAction!shitiku.action?DangQianYeMa=" + dangqianyema+"&token="+localStorage.getItem("token");

    }

    //读取总页码or列出全部视频
    function shipinku(dangqianyema) {

        window.location.href = "/courseUP/CourseUploadAction!shipinku.action?DangQianYeMa=" + dangqianyema+"&token="+localStorage.getItem("token");

    }

    //读取总页码or列出全部案例
    function anliku(dangqianyema) {

        window.location.href = "/courseUP/CourseUploadAction!anliku.action?DangQianYeMa=" + dangqianyema+"&token="+localStorage.getItem("token");

    }

    //下载
    function oneDownload(obj) {
        //判断是否已登录
        /*var s="<%=session.getAttribute("LOGINUSER")%>";

        if(s== "null"){
            if(confirm("请先登录!")){
                window.location.href  ="
        /login.jsp";
                }else{
                    return false;
                }
            }else{

                var url = encodeURIComponent(encodeURIComponent($(obj).attr("url")));

                var name = $(obj).attr("name");

                window.location.href = "
        /courseUP/CourseUploadAction!fileDownload.action?courseware.fileRolad="
                    + url + "&courseware.courseWareName=" + name;
            }*/
    }

    //读取学习指导的信息
    function xuexizhidao() {

        window.location.href = "/tool/DeveToolMangeAction!showDaoxueQiantai.action"+"&token="+localStorage.getItem("token");

    }


    //读取开发工具的信息
    function kaifagongju() {

        window.location.href = "/tool/DeveToolMangeAction!showHuangjinQiantai.action"+"&token="+localStorage.getItem("token");

    }

    //读取开发软件下载文章的信息
    function ruanjianxiazai() {

        window.location.href = "/tool/DeveToolMangeAction!showSoftwareQiantai.action"+"&token="+localStorage.getItem("token");

    }

    //初始化题库
    /*	function makeTikuList(){

            $.ajax({
                             type : "post",
                             url : "/csa/courseselectAction!findAllTiku.action?time="+new Date(),
				         data : {

				         },
				         cache:false,
				         async : false,  //同步处理
				         success : function(data, status) { //这里的status是ajax自己的参数，请求成功就success
				         	if(data == "error"){
				         		$("#ceshiList>div").remove();
	  			    			$("#ceshiList>h4").remove();
	  			    			$("#ceshiList").append("<h4>缺少资源！</h4>");
				         	}else{
				         	   	var json = JSON.parse(data);

		              			$("#ceshiList>div").remove();
		               			$("#ceshiList>h4").remove();
					   			for (var i = 0; i < 4; i++) {
						     		 $("#ceshiList").append(

						                  "<a href=\"#\" onclick=\"selectTiku(this)\"><li>"+json[i].courseName+"</li></a>"

									);
		  			    		};
				         	};
					    }
					 });

		};      */

    //选择题库
    function selectTiku(obj) {
        //判断是否已登录
        window.location.href = "/csa/courseselectAction!tikuexecute.action"+"&token="+localStorage.getItem("token");
    }

    //列出新闻列表
    function getNewsList() {

        $.ajax({
            type: "post",
            url: "/news/newsMangeAction!findAllNews.action?time=" + new Date(),
            data: {},
            cache: false,
            async: false,  //同步处理
            success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success
                if (data == "error") {
                    $("#shouyenewslist").append("<h4>暂无资讯！</h4>");
                } else {
                    var json = JSON.parse(data);

                    $("#courseselectMenu>div").remove();
                    $("#courseselectMenu>h4").remove();
                    for (var i = 0; i < 6 && i < json.length; i++) {  //只显示前6条
                        $("#shouyenewslist").append(
                            "<li><a title=\"点击查看\" newsid=" + json[i].id + " onclick=\"oneRead(this)\">" + json[i].newsTitle + "</a></li>"
                        );
                    }
                    ;
                }
                ;
            }
        });

    };

    //读取总页码
    function yematongji(dangqianyema) {
        window.location.href = "/courseUP/CourseUploadAction!fileyema.action?DangQianYeMa=" + dangqianyema;

    }


    //读取总页码or列出全部资源
    function newstongji(dangqianyema) {

        window.location.href = "/news/newsMangeAction!newsyema.action?DangQianYeMa=" + dangqianyema;

    }

    //课程学习
    function coursefg() {
        window.location.href = "/learn/courseLearnMangeAction!toCourseLearnList.action";

    }

    //读取教学大纲的信息
    function jiaoxuedagang() {

        window.location.href = "/tool/DeveToolMangeAction!showDagangQiantai.action";

    }


    //-----------------进入论坛----------------------------
    function talktongji(course, forumid, sort) {//参数：课程类别1=第一门，板块类别0=全部，排序方式1=第一种排序
        window.location.href = "/talk/talkMangeAction!talkyema.action?" +
            "course=" + course + "&" +
            "forumid=" + forumid + "&" +
            "sort=" + sort+
            "&token="+localStorage.getItem("token");
    }

    let prefix = "/user/course";


    function getZiyuanList() {

        $.ajax({
            type: "post",
            url: "/user/newResource/0/${tcid}",
            data: {
                pageNum: 0,
                pageSize: 6,
                orderByColumn: 'r.create_time',
                isAsc: 'asc'
            },
            dataType: 'json',
            cache: true,
            async: true,  //同步处理
            success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success
                $("#courseselectMenu>div").remove();
                $("#courseselectMenu>h4").remove();
                if (data.code != 0) {
                    $("#ziyuanList").append("<h4>缺少资源！</h4>");
                } else {
                    $("#ziyuanList").html("");
                    let json = data.rows;
                    let menu = '';
                    for (let i = 0; i < json.length; i++) {
                        menu += "<li><div class=\"news-first\">"
                            + "  <a href='javascript:void(0);' onclick='downLoad(\""+json[i].attrId+"\");'><small>" + json[i].name + "</small></a></div></li>";
                    }
                    $("#ziyuanList").append(menu);
                }
                ;
            }
        });
    };


    function downLoad(fileId) {
        window.location.href = "${storage}/downloadFile?fileId=" + fileId+"&token="+localStorage.getItem("token");;
    }


    //列出提问列表
    function getTalkList() {

        $.ajax({
            type: "post",
            url: "/user/shouye/talk",
            data: {
                pageSize: 10
            },
            dataType: 'json',
            success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success
                if (data.code != 0) {

                    $("#tiwenList").append("<h4>暂无提问！</h4>");
                } else {
                    var json = data.data;
                    $("#tiwenList>div").remove();
                    $("#tiwenList>h4").remove();

                    for (var i = 0; i < 6 && i < json.length; i++) {  //只显示前6条
                        $("#tiwenList").append(
                            "<li style='padding-bottom: 3px;" +
                            "padding-top: 3px;'><a title=\"点击查看\" talkid=" + json[i].id + " onclick=\"answerRead(this)\">" + json[i].title + "</a><span style=\"float:right;\">" + json[i].content + "</span></li>"
                        );
                    }
                    ;
                }
                ;
            }
        });

    };


    function getGoodBrother() {

        $.ajax({
            type: "post",
            url: "/talk/talkMangeAction!findGoodBrother.action?course=1&sort=1&&forumid=" + "2c9f56fa632a527401633ec77c120039",
            cache: false,
            async: false,  //同步处理
            success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success
                if (data == "error") {
                    $("#brothero").append("<h4>暂无提问！</h4>");
                } else {
                    var json = JSON.parse(data);
                    $("#brothero>div").remove();
                    $("#brothero>h4").remove();

                    for (var i = 0; i < 5 && i < json.length; i++) {  //只显示前11条
                        $("#brothero").append(
                            "<li><a title=\"点击查看\" talkid=" + json[i].id + " onclick=\"answerRead(this)\">" + json[i].title + "</a></li>"
                        );
                    }
                    ;
                }
                ;
            }
        });

        $.ajax({
            type: "post",
            url: "/talk/talkMangeAction!findGoodBrother.action?course=1&sort=1&&forumid=" + "2c9f56fa632a527401633ec6d4370038",
            cache: false,
            async: false,  //同步处理
            success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success
                if (data == "error") {
                    $("#brothertw").append("<h4>暂无提问！</h4>");
                } else {
                    var json = JSON.parse(data);
                    $("#brothertw>div").remove();
                    $("#brothertw>h4").remove();

                    for (var i = 0; i < 5 && i < json.length; i++) {  //只显示前11条
                        $("#brothertw").append(
                            "<li><a title=\"点击查看\" talkid=" + json[i].id + " onclick=\"answerRead(this)\">" + json[i].title + "</a></li>"
                        );
                    }
                    ;
                }
                ;
            }
        });

        $.ajax({
            type: "post",
            url: "/talk/talkMangeAction!findGoodBrother.action?course=1&sort=1&&forumid=" + "2c9f56fa632a527401633ec7d2f3003a",
            cache: false,
            async: false,  //同步处理
            success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success
                if (data == "error") {
                    $("#brothert").append("<h4>暂无提问！</h4>");
                } else {
                    var json = JSON.parse(data);
                    $("#brothert>div").remove();
                    $("#brothert>h4").remove();

                    for (var i = 0; i < 5 && i < json.length; i++) {  //只显示前11条
                        $("#brothert").append(
                            "<li><a title=\"点击查看\" talkid=" + json[i].id + " onclick=\"answerRead(this)\">" + json[i].title + "</a></li>"
                        );
                    }
                    ;
                }
                ;
            }
        });

    };


    //查看问题详情
    function answerRead(obj) {
        var talkId = $(obj).attr("talkid");
        var token=localStorage.getItem("token")
        window.location.href = "/user/topic/detail/" + talkId+"?token="+token;
    }


    function swipperclick() {
        var buttons = document.getElementsByClassName("swiper-button")[0].getElementsByTagName("span");
        for (var j = 0; j < buttons.length; j++) {
            (function (j) {
                buttons[j].onclick = function () {
                    var clickIndex = parseInt(this.getAttribute("index"));
                    var i = clickIndex;
                    document.getElementById("swipperimg").src = "/static/plugins/home/img/banner" + i + ".png";
                    //document.getElementById("imgurl").href=urllist[i];
                    document.getElementById("imgurl").href = "#";
                    document.getElementById("bkimg").style = "background-image: url(/static/plugins/home/img/banner" + i + ".png);";
                    changeimg();
                    window.clearInterval(time1);
                    time1 = setInterval("changeimg()", 3000);
                }
            })(j)
        }
    }

    function changeimg() {
        var buttons = document.getElementsByClassName("swiper-button")[0].getElementsByTagName("span");
        document.getElementById("swipperimg").src = "/static/plugins/home/img/banner" + i + ".png";
        //document.getElementById("imgurl").href=urllist[i];
        document.getElementById("imgurl").href = "#";
        document.getElementById("bkimg").style = "background-image: url(/static/plugins/home/img/banner" + i + ".png);";
        for (var k = 0; k < buttons.length; k++) {
            if (buttons[k].style = "background: #d6d6d6;") {
                buttons[k].style = "";
            }
        }
        buttons[i - 1].style = "background: #d6d6d6;";
        i++;
        if (i > 4) {
            i = 1;
        }
    }

    $.extend({
        /**
         * 使用参数格式化字符串
         * source：字符串模式 abcdef{0}-{1}，
         * params：参数数组，参数序号对应模式中的下标
         */
        format: function (source, params) {
            if (arguments.length == 1)
                return function () {
                    var args = $.makeArray(arguments);
                    args.unshift(source);
                    return $.format.apply(this, args);
                };
            if (arguments.length > 2 && params.constructor != Array) {
                params = $.makeArray(arguments).slice(1);
            }
            if (params.constructor != Array) {
                params = [params];
            }
            $.each(params, function (i, n) {
                source = source.replace(new RegExp('\\{' + i + '\\}', 'g'), n);
            });
            return source;
        }

    });

    function initPath() {
        let url = new URL(window.location.href);
        let params = new URLSearchParams(url.search);
        if (params.get("cur") === '_') {
            document.getElementById('mycourses').scrollIntoView();
        }
    }

    function toMyCourse() {
        let courses = document.getElementById('mycourses');
        console.log(courses !== null);
        if (courses !== undefined && courses !== null) {
            document.getElementById('mycourses').scrollIntoView();
        } else {
            window.location.href = '?cur=_';
        }
    }

    //列出提问列表
    function getTalkList() {

        $.ajax({
            type: "post",
            url: "/user/shouye/talk",
            data: {
                pageSize: 10
            },
            dataType: 'json',
            success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success
                if (data.code != 0) {

                    $("#tiwenList").append("<h4>暂无提问！</h4>");
                } else {
                    var json = data.data;
                    $("#tiwenList>div").remove();
                    $("#tiwenList>h4").remove();

                    for (var i = 0; i < 6 && i < json.length; i++) {  //只显示前6条
                        $("#tiwenList").append(
                            "<li style='padding-bottom: 3px;" +
                            "padding-top: 3px;'><a title=\"点击查看\" talkid=" + json[i].id + " onclick=\"answerRead(this)\">" + json[i].title + "</a><span style=\"float:right;\">" + json[i].content + "</span></li>"
                        );
                    }
                    ;
                }
                ;
            }
        });

    };
</script>
<script>
    let stuid="${sysUser.userAttrId}";
    (function () {
        $.ajax({
            type: "post",
            url: "/jiaowu/integral/getsort?studentId="+stuid,
            dataType: "json",
            error: function (request) {

            },
            success: function (data) {
                $('#menu').empty();
                $('#menu').append('<ul class="menu_ul"style="position: relative;">' +
                        '<li class="item">' +
                        '<div class="inner_item">' +
                        ' <a>班级积分排名</a>' +
                        '</div>' +
                        ' </li>');
                for (let j = 0; j < 6; j++) {
                    console.log(data[j].userName+":"+data[j].credit);
                    if(data[j].credit=="0"){
                        return;
                    }
                    let no = Arabia_To_SimplifiedChinese(j+1);
                    $('#menu').append('<li class="item">' +
                            '<div class="inner_item"><a>第'+ no +'名：'+data[j].userName+":"+data[j].credit+"个积分"+
                            '</a>' +
                            '</div>' +
                            '</li>' +
                            '</ul>');
                }

            }
        });
    })();
    var token = localStorage.getItem("token");
    $(document).click(function(e) { // 在页面任意位置点击而触发此事件
        if($(e.target).is('a')){
            var href= $(e.target).attr("href")
            if(href!=="#"||href!=="") {
                if (href.indexOf("?") === -1) {
                    href = href + "?token=" + token
                } else {
                    href = href + "&token=" + token
                }
            }
            $(e.target).attr("href",href)
        };       // e.target表示被点击的目标
    });
    //数字转换
    //阿拉伯数字转换为简写汉字
    //转自：https://www.cnblogs.com/wangqiideal/p/5579807.html
    function Arabia_To_SimplifiedChinese(Num) {
        for (i = Num.length - 1; i >= 0; i--) {
            Num = Num.replace(",", "")//替换Num中的“,”
            Num = Num.replace(" ", "")//替换Num中的空格
        }
        if (isNaN(Num)) { //验证输入的字符是否为数字
            return;
        }
        //字符处理完毕后开始转换，采用前后两部分分别转换
        part = String(Num).split(".");
        newchar = "";
        //小数点前进行转化
        for (i = part[0].length - 1; i >= 0; i--) {
            if (part[0].length > 10) {
                //alert("位数过大，无法计算");
                return "";
            }//若数量超过拾亿单位，提示
            tmpnewchar = ""
            perchar = part[0].charAt(i);
            switch (perchar) {
                case "0":
                    tmpnewchar = "零" + tmpnewchar;
                    break;
                case "1":
                    tmpnewchar = "一" + tmpnewchar;
                    break;
                case "2":
                    tmpnewchar = "二" + tmpnewchar;
                    break;
                case "3":
                    tmpnewchar = "三" + tmpnewchar;
                    break;
                case "4":
                    tmpnewchar = "四" + tmpnewchar;
                    break;
                case "5":
                    tmpnewchar = "五" + tmpnewchar;
                    break;
                case "6":
                    tmpnewchar = "六" + tmpnewchar;
                    break;
                case "7":
                    tmpnewchar = "七" + tmpnewchar;
                    break;
                case "8":
                    tmpnewchar = "八" + tmpnewchar;
                    break;
                case "9":
                    tmpnewchar = "九" + tmpnewchar;
                    break;
            }
            switch (part[0].length - i - 1) {
                case 0:
                    tmpnewchar = tmpnewchar;
                    break;
                case 1:
                    if (perchar != 0) tmpnewchar = tmpnewchar + "十";
                    break;
                case 2:
                    if (perchar != 0) tmpnewchar = tmpnewchar + "百";
                    break;
                case 3:
                    if (perchar != 0) tmpnewchar = tmpnewchar + "千";
                    break;
                case 4:
                    tmpnewchar = tmpnewchar + "万";
                    break;
                case 5:
                    if (perchar != 0) tmpnewchar = tmpnewchar + "十";
                    break;
                case 6:
                    if (perchar != 0) tmpnewchar = tmpnewchar + "百";
                    break;
                case 7:
                    if (perchar != 0) tmpnewchar = tmpnewchar + "千";
                    break;
                case 8:
                    tmpnewchar = tmpnewchar + "亿";
                    break;
                case 9:
                    tmpnewchar = tmpnewchar + "十";
                    break;
            }
            newchar = tmpnewchar + newchar;
        }
        //替换所有无用汉字，直到没有此类无用的数字为止
        while (newchar.search("零零") != -1 || newchar.search("零亿") != -1 || newchar.search("亿万") != -1 || newchar.search("零万") != -1) {
            newchar = newchar.replace("零亿", "亿");
            newchar = newchar.replace("亿万", "亿");
            newchar = newchar.replace("零万", "万");
            newchar = newchar.replace("零零", "零");
        }
        //替换以“一十”开头的，为“十”
        if (newchar.indexOf("一十") == 0) {
            newchar = newchar.substr(1);
        }
        //替换以“零”结尾的，为“”
        if (newchar.lastIndexOf("零") == newchar.length - 1) {
            newchar = newchar.substr(0, newchar.length - 1);
        }
        return newchar;
    }
    </script>
</body>
</html>
