<!DOCTYPE html>
<html lang="en">
<head>
    <#--<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />-->
    <#--<meta name="apple-mobile-web-app-capable" content="yes" />-->
    <#--<meta name="format-detection" content="telephone=no" />-->
    <title>课程内容</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/js/plugins/pagination/style/pagination.css" media="screen">
    <link rel="stylesheet" type="text/css" href="/js/plugins/pagination/style/normalize.css" media="screen">
    <link rel="stylesheet" href="/css/course-main.css">
    <link rel="stylesheet" type="text/css" href="/js/plugins/jquery.poshytip/tip-yellowsimple/tip-yellowsimple.css"/>
    <script type="text/javascript" src="/js/plugins/jquery.poshytip/jquery.poshytip.min.js"></script>
    <link rel="stylesheet" href="/css/recourse/video-js.css">
    <link href="/js/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link href="/css/wxbootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="/css/wxindexstyle.css" type="text/css" rel="stylesheet">
    <script src="/js/jquery-2.1.4.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <script src="/js/bootstrap.min.js" type="text/javascript"></script>

</head>
<body>
<nav class="nav navbar navbar-default"style="height:100px;width: 100%">
    <div class="container-fluid">
        <!-- 导航栏头部 -->
        <div class="navbar-header" >
            <h1 class="heading" style="color: #DAE6ED;width: 200px; margin-left: 100px;margin-top: 25px;">课程列表</h1>
            <!-- logo -->

        </div>
        <!-- 导航栏菜单 -->
        <div class="collapse navbar-collapse" id="collapsenav">
            <#--<ul class="nav navbar-nav navbar-left">-->
            <#--<li><a href="${ctx}">首页</a></li>-->
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
                        <img class="nav-sysUser-photo" src="/img/avatars/user.jpg" alt="Jason's Photo"/>
                        <#--<span class="sysUser-info">-->
									<#--&lt;#&ndash;<small>欢迎,</small>&ndash;&gt;-->
                            <#--<p>${openId}</p>-->
								<#--</span>-->
                        <p>${openId}</p>

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
                            <a href="/logout">
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
                </li>
                <#--</@notifiesBadge>-->
                    </li>
                <#--</@shiro.user>-->
                <#--<@shiro.guest>-->
                    <#--<li id="login-ad"><a type="button"-->
                    <#--style="color:#fff;font-size:15px;background-color:#8fbc8f;border-radius:3px;padding:10px 20px;margin-top:5px;"-->
                    <#--href="${base}/login">登录</a></li>-->
                <#--</@shiro.guest>-->

                <!--  <li id="login-houtai"><a href="${basePath}/login-admin.jsp">后台管理</a></li> -->
            </ul>
        </div>
    </div>
</nav>
<#--<!-- 毛玻璃特效 &ndash;&gt;-->
<#--<div class="bk" id="bkimg" style="background-image: url(/img/front/banner3.png);"></div>-->
<!-- 分割线 -->
<#--<img width="550px" height="250" src="../../../static/img/bg/background.jpg">-->

<div class="ov f-pr j-ch f-cb">
    <#--<div class="courseMark djMark f-pa j-djMark"></div>-->
    <#--<div class="courseMark sfMark f-pa j-sfMark"></div>-->
    <div class="g-sd1 left j-chimg"><img width="1000px" height="300"
                                         src="/img/bg/background.jpg">
    </div>
</div>
<div class="all-content">
    <div class="form-group"  style="height: 500px;width: 900px ">
        <#list courseVos as vo>
            <div class="col-md-4 col-xs-12">
            <div class="information center-block" >

        <div class="g-sd1 left j-chimg"><img width="100px" height="300"
                                             src="/img/bg/ketan.jpg">
        </div>
            <h3 style="font-size:18x;font-weight: 700;">${vo.course.name}</h3>
            <#--<div class="infotitle">-->
            <#--</div>-->
        <#--<img class="img-responsive center-block" src="/img/course/course1.jpg">
            <div style="text-align: left;font-size:18px;font-weight: 700;"">
                <label></label><p>${vo.course.period}</p>
                <label></label><p>${vo.course.credit}</p>
                <label></label><p>${vo.course.courseInfo}</p>
                <label></label><p>${vo.course.courseUserNum}</p>
            </div>-->
            <div style="text-align: right;margin:10px;">
        <a href="/wx/course/detail/${vo.tcid}?openId=${openId}" class="btn btn-warning btn-rounded">去学习</a>
        <#--                            <a href="/wx/attendance/toattendance/${vo.tcid}?openId=${openId}" class="btn btn-warning btn-rounded">去签到</a>-->
            <#if vo.aBoolean=false><a href="/wx/attendance/whethersuziattendance/${vo.tcid}?openId=${openId}" class="btn btn-warning btn-rounded">去签到</a></#if>
        <#--<a href="/wx/course/scan" class="btn btn-warning btn-rounded">去学习</a>-->
                        </div>
                    </div>
                </div>
                </#list>
    </div>
</div>


</body>
</html>