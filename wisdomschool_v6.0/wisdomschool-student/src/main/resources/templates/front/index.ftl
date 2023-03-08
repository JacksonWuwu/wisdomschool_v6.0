<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>智慧教育云平台-以科技助力教育</title>
    <link rel="shortcut icon" href="/img/front/favicon.ico" type="image/x-icon">
    <link href="/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="/css/indexstyle.css" type="text/css" rel="stylesheet">
    <link href="/css/pt_web_category_index_f4afc3211e6bd6468fa0bd4444fbe79d.css" type="text/css" rel="stylesheet">
    <link href="/css/pt_web_index.web_53cd3c4ea11548eed1e4d7ba855fe904.css" type="text/css" rel="stylesheet">
    <script src="/js/jquery-2.1.4.min.js" type="text/javascript"></script>
    <script src="/js/bootstrap.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="/js/common.js"></script>
</head>

<body>

<!--页头-->
<nav class="nav navbar navbar-default"style="height:100px;">
    <div class="container-fluid">
        <!-- 导航栏头部 -->
        <div class="navbar-header" >
            <!-- 折叠导航栏的按钮 -->
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#collapsenav"
                    aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <!-- logo -->
            <div class="logo-contanier">
                <!--a class="navbar-brand" href="/index">
                    <img alt="Brand" src="/img/front/index_ico_03.png" style="width:200px;margin-top:1px;margin-left: 80px;">
                </a-->
                <span class="slogan"><p class="navbar-text" style="width: 240px; font-size: 25px;margin-left: 25px;">智慧教育云平台</p></span>
            </div>
        </div>
        <!-- 导航栏菜单 -->
        <div class="collapse navbar-collapse" id="collapsenav">
            <#--<ul class="nav navbar-nav navbar-left">-->
                <#--<li><a href="">首页</a></li>-->
                <#--<li><a href="javascript:void(0);" onclick="toMyCourse()">我的课程</a></li>-->
                <#--<li><a href="/user/topicList">学习讨论</a></li>-->
            <#--</ul>-->
            <ul class="nav navbar-nav navbar-right"style="margin-right: 10%;">
                <img id="user-img"
                     src="/img/front/avatar3.png"
                     style="margin:10px 0px; float:left;width:30px;height: 30px;display:none;"/>

                <#--<@shiro.user>-->
                    <#--<#if tcid??>-->
                        <#--<li><a href="javascript:void(0);">当前课程：<span>${course.name}</span></a></li>-->
                    <#--</#if>-->
                    <li class="pull-left">
                      <li class="light-blue dropdown-modal">
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                        <#--<img class="nav-sysUser-photo" src="/img/avatars/user.jpg" alt="Jason's Photo"/>-->
                        <span class="sysUser-info">
                                    <#--显示用户身份信息-->
									${sysUser.userName}
								</span>

                        <i class="ace-icon fa fa-caret-down"></i>
                    </a>

                    <ul class="sysUser-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close"style="top: 41px; left: 0px;width: 90px;">
                        <#--li>
                            <a href="#">
                                <i class="ace-icon fa fa-cog"></i>
                                设置
                            </a>
                        </li>
                        <li class="divider"></li-->

                        <li>
                            <a href="/account/index" class="user">
                                <i class="ace-icon fa fa-sysUser"></i>
                                个人主页
                            </a>
                        </li>



                        <li>
                            <a onclick="logout()">
                                <i class="ace-icon fa fa-power-off"></i>
                                退出登录
                            </a>
                        </li>
                    </ul>





                        <#--<@notifiesBadge uid=profile.id>-->
                        <#--<a href="/user/personal/<@shiro.principal property="id"/>" class="user">-->
                            <#--<span><@shiro.principal property="userName"/></span><span-->
                                    <#--class="discussion-item-icon">-->
                                <#--&lt;#&ndash;<#if (result.notifies > 0)>-->
                                    <#--<svg class="octicon octicon-primitive-dot" viewBox="0 0 8 16" version="1.1" width="8" height="16" aria-hidden="true">-->
                                      <#--<path fill="red" fill-rule="evenodd" d="M0 8c0-2.2 1.8-4 4-4s4 1.8 4 4-1.8 4-4 4-4-1.8-4-4z"></path>-->
                                  <#--</svg>-->
                                <#--</#if>&ndash;&gt;-->
                                <#--</span>-->
                        <#--</a>-->
                        <#--</@notifiesBadge>-->
                <#--</@shiro.user>-->
                    <#--base:ip地址+端口号-->
<#--                <@shiro.guest>-->
<#--                    <li id="login-ad"><a type="button"-->
<#--                                         style="color:#fff;font-size:15px;background-color:#8fbc8f;border-radius:3px;padding:10px 20px;margin-top:5px;"-->
<#--                                         href="${base}/login">登录</a></li>-->
<#--                </@shiro.guest>-->
            </ul>
        </div>
    </div>
</nav>

<!-- 毛玻璃特效 -->
<div class="bk" id="bkimg" style="background-image: url(/img/front/banner3.png);"></div>


<!-- 分割线 -->
  <#include '/front/inc/lunbotu.ftl'/>
<div id="g-body" style="top: -160px; position: relative">
    <div id="j-content" class="g-flow-wide f-cb">
        <div class="g-main-content f-fl">
            <div class="m-bread f-f0">
                <div class="m-recCourse-title">
                    <h2>
                        我 的 课 程
                    </h2>
                </div>
            </div>
        </div>



        <div id="j-courseCardListBox" class="m-course-card-list f-cb">
            <div>
                <div class="m-course-list">
                    <div class="container-fluid" style="float: left; width: 930px;margin-top: 60px;background-color: #f7f7f7;overflow: auto;">
                        <div class="row" id="mycourse" style="border-radius: 10px;height: 714px;overflow: auto"></div>
                    </div>

                </div>
            </div>



            <div id="j-side-bar" class="g-side-bar f-fr">

                <div class="u-hot-course-title f-f0"style="margin-bottom: 10px;">排行榜</div>
                <div class="container-fluid">

                    <div id="menu" style="height: 400px; float:left;border-radius:10px;overflow: auto;margin-bottom: 15px; background-color: #a1afc9;">
                    </div>

                    <!--div id="menu1" style="height: 350px;float:left;border-radius:10px;overflow: auto;margin-bottom: 15px; background-color: #a1afc9;">
                    </div-->

                    <!--div id="menu2" style="border-radius:10px; height: 350px;overflow: auto;margin-bottom: 15px; background-color: #a1afc9;">
                    </div-->

                </div>
            </div>
        </div>

        <div class="p-index-discussFunc"style="width: 1260px;height:310px; float: left;top: 20px;background-color: #617fa5;">
            <div class="p-index-discussFunc-i">
                <div class="m-discuss-container">
                    <div class="m-discuss-img"><img style="margin-left: 100px;" src="/img/front/discuss.png" alt="精彩讨论"></div>
                    <div class="m-discuss-title" style="margin-left: 100px;"><span>一起学习 一起成长</span></div>

                    <div class="m-discuss-introFunc">
                        <div class="m-discuss-introFunc-t">
                            <div class="u-discussIntro-container" style="margin-left: 100px;"><span>与老师和同学发言讨论</span></div>
                        </div>
                    </div>
                    <div class="m-discuss-joinFunc">
                        <div class="m-discuss-joinFunc-t">
                            <div class="navLoginBtn u-discuss-join-container" style="margin-left: 100px;" ><span>加入智慧教育云平台</span></div>
                        </div>
                    </div>
                    <div class="m-discuss-content" id="j-index-discuss-box">
                        <div class="ux-slider-slider-wrapper f-pr" style="width: 581px; height: 241px;margin-left: 100px; border: 2px solid white;background-color: white;border-radius: 10px;">
                            <!--Regular if6-->
                            <div class="ux-scroll-container" style="width: 581px; height: 238px;overflow: auto;">
                                <ol id="tiwenList">
                                </ol>
                            </div>
                        </div>
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
        //页面加载完自动执行

        //judgeLogin();
        //登录后移除登录按钮
        //getZiyuanList();
        //getNewsList();
        //makeTikuList();
        //getTalkList();
        //$.post("/learn/learnTreeviewAction!BuildTree.action",function(data){
        //console.log(data);
        // fuzhi(data);
        //});
        //time1 = setInterval("changeimg()", 3000);
        //swipperclick();
        //getGoodBrother();
        myCourse();
        initPath();
        getTalkList();
    });

    function getshangji(num) {
        if (confirm("是否要下载？") == true) {
            window.location.href = "/resources/shangjiti/shangji" + num + ".doc";
        }
    }

    var chapter_josn;

    function fuzhi(data) {

        chapter_josn = eval("(" + data + ")");

    }

    function chapter(obj) {

        for (i = 0; i < chapter_josn.length; i++) {
            if (chapter_josn[i].name.split(" ")[0] == $(obj).attr("value")) {
                window.location.href = "/learn/courseLearnMangeAction!toCourseLearnList.action?id=" + chapter_josn[i].id;
            }
        }

    }

    //读取总页码or列出全部课件
    function kejianku(dangqianyema) {

        window.location.href = "/courseUP/CourseUploadAction!kejianku.action?DangQianYeMa=" + dangqianyema;

    }

    //读取总页码or列出全部上机
    function shangjitiku(dangqianyema) {

        window.location.href = "/courseUP/CourseUploadAction!shangjitiku.action?DangQianYeMa=" + dangqianyema;

    }

    //读取总页码or列出全部试题
    function shitiku(dangqianyema) {

        window.location.href = "/courseUP/CourseUploadAction!shitiku.action?DangQianYeMa=" + dangqianyema;

    }

    //读取总页码or列出全部视频
    function shipinku(dangqianyema) {

        window.location.href = "/courseUP/CourseUploadAction!shipinku.action?DangQianYeMa=" + dangqianyema;

    }

    //读取总页码or列出全部案例
    function anliku(dangqianyema) {

        window.location.href = "/courseUP/CourseUploadAction!anliku.action?DangQianYeMa=" + dangqianyema;

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

        window.location.href = "/tool/DeveToolMangeAction!showDaoxueQiantai.action";

    }


    //读取开发工具的信息
    function kaifagongju() {

        window.location.href = "/tool/DeveToolMangeAction!showHuangjinQiantai.action";

    }

    //读取开发软件下载文章的信息
    function ruanjianxiazai() {
        window.location.href = "/tool/DeveToolMangeAction!showSoftwareQiantai.action";

    }

    function logout(){
        $.ajax({
            type: "get",
            url: "/admin/sysLogin/logout",
            success: function (res) {
                console.log(res)
                localStorage.clear();
                window.location.href="/loginIndex"
            },error: function(res){
                localStorage.clear();
                window.location.href="/loginIndex"
                console.log(res)
            }
        });
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
        window.location.href = "/csa/courseselectAction!tikuexecute.action";
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
            "sort=" + sort;
    }

    let prefix = "/user/course";

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
    <div style="text-align: right;margin:25px;"><a href="/user/course/info/{0}" class="btn btn-warning btn-rounded">进入课程
                        </a></div>

                        <div class="infotitle" style="padding: 20px 5px 26px 0px;">
                            {1}<a href="javaScript:void(0)" style="float:right;"></a>
                        </div>
                </a>
                </div>`;
        let tmp = data.map(d => $.format(template, d.teacherCourse.id, d.course.name, d.teacherCourse.courseBriefIntroduction, i++)).join("");
        $("#mycourse").html(tmp);
    }




    //列出资源
    function getZiyuanList() {

        $.ajax({
            type: "post",
            url: "/courseUP/CourseUploadAction!findAllZiyuan.action?time=" + new Date(),
            data: {},
            cache: false,
            async: false,  //同步处理
            success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success
                if (data == "error") {
                    $("#ziyuanList").append("<h4>缺少资源！</h4>");
                } else {
                    var json = JSON.parse(data);
                    maxnumber = json.length - 1;
                    $("#courseselectMenu>div").remove();
                    $("#courseselectMenu>h4").remove();
                    for (var i = 0; i < 6; i++) {  //只显示前6条
                        $("#ziyuanList").append(
                            "<li><a title=\"下载该资源\" href=\"#\" url=" + json[maxnumber - i].fileRolad + "  name=" + json[maxnumber - i].courseWareName + " onclick=\"oneDownload(this)\">" + json[maxnumber - i].courseWareName + "</a></li>"
                        );
                    }
                    ;
                }
                ;
            }
        });

    };


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

                    for (var i = 0; i < 1000 && i < json.length; i++) {  //只显示前1000条
                        $("#tiwenList").append(
                            "<li style='padding-bottom: 3px;" +
                            "padding-top: 3px;"+"padding-left: 5px;"+"overflow: hidden;'><a title=\"点击查看\" talkid=" + json[i].id + " onclick=\"answerRead(this)\">" + json[i].title + "</a><span style=\"float:right;\">提出时间：" + json[i].content + "</span></li>"
                        );
                    };
                };
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

                    for (var i = 0; i < 5 && i < json.length; i++) {  //只显示前5条
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

                    for (var i = 0; i < 5 && i < json.length; i++) {  //只显示前5条
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

                    for (var i = 0; i < 5 && i < json.length; i++) {  //只显示前5条
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
        let token = localStorage.getItem("token");
        var talkId = $(obj).attr("talkid");
        window.location.href = "/user/topic/detail/" + talkId+"&token="+token;
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
</script>
<script>
    let stuid="${sysUser.userAttrId}";
    window.onload=function () {
        getsort()
    }
    function getsort() {
        $.ajax({
            type: "post",
            url: "/jiaowu/integral/getsort?studentId="+stuid,
            dataType: "json",
            success: function (data) {
                $('#menu').empty();
                $('#menu').append('<ul class="menu_ul"style="position: relative;">' +
                    '<li class="item">' +
                    '<div class="inner_item">' +
                    ' <a>班级积分排名</a>' +
                    '</div>' +
                    ' </li>');
                $('#menu1').empty();
                $('#menu1').append('<ul class="menu_ul"style="position: relative;">' +
                    '<li class="item">' +
                    '<div class="inner_item">' +
                    ' <a>年级积分排名</a>' +
                    '</div>' +
                    ' </li>');
                $('#menu2').empty();
                $('#menu2').append('<ul class="menu_ul"style="position: relative;">' +
                    '<li class="item">' +
                    '<div class="inner_item">' +
                    ' <a>学院积分排名</a>' +
                    '</div>' +
                    ' </li>');
                for (let j = 0; j < 100; j++) {
                    // console.log(data[j].userName+":"+data[j].credit);
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
                for (let j = 0; j < 6; j++) {
                    // console.log(data[j].userName+":"+data[j].credit);
                    if(data[j].credit=="0"){
                        return;
                    }
                    let no = Arabia_To_SimplifiedChinese(j+1);
                    $('#menu1').append('<li class="item">' +
                        '<div class="inner_item"><a>第'+ no +'名：'+data[j].userName+":"+data[j].credit+"个积分"+
                        '</a>' +
                        '</div>' +
                        '</li>' +
                        '</ul>');
                    $('#menu2').append('<li class="item">' +
                        '<div class="inner_item"><a>第'+ no +'名：'+data[j].userName+":"+data[j].credit+"个积分"+
                        '</a>' +
                        '</div>' +
                        '</li>' +
                        '</ul>');
                }


            }
        });
    };

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
