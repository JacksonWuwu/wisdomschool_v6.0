<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>个人中心</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon"
          href="static/plugins/home/img/favicon.ico"
          type="image/x-icon"/>
    <!--地址栏和标签上显示图标-->
    <!--bootstrap引用-->
    <link rel="stylesheet"
          href="/js/plugins/uploadimage/css/imgareaselect-default.css">
    <link href="/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="/css/indexstyle1.css"
          type="text/css" rel="stylesheet">
    <link href="/css/dati.css"
          type="text/css" rel="stylesheet">
    <script src="/js/jquery-2.1.4.min.js" type="text/javascript"></script>
    <script src="/js/bootstrap.min.js" type="text/javascript"></script>
    <script
            src="/js/plugins/uploadimage/js/jquery.imgareaselect.min.js"></script>
    <script src="/js/plugins/uploadimage/js/image.js"></script>


<#--<link href="/css/indexstyle.css" type="text/css" rel="stylesheet">

<link rel="stylesheet" href="/css/style-home.css" />
<link rel="stylesheet" href="/js/plugins/layer/theme/default/layer.css" />

<link rel="stylesheet" type="text/css"
      href="/js/plugins/pagination/style/pagination.css"
      media="screen">
<link rel="stylesheet" type="text/css"
      href="/js/plugins/pagination/style/normalize.css"
      media="screen">-->
    <#include '/front/inc/commonJs.ftl'/>
</head>
<body>
<!--页头-->
<nav class="nav navbar navbar-default">
    <div class="container-fluid">
        <!-- 导航栏头部 -->
        <div class="navbar-header">
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
                <a class="navbar-brand" href="/index">
                    <img alt="Brand" src="/img/front/relogo.png" style="width:80px;margin-top:1px;">
                </a>
                <span class="slogan"><p class="navbar-text" style="width: 220px; font-size: 20px;">在线课程服务平台</p></span>
            </div>
        </div>
        <!-- 导航栏菜单 -->
        <div class="collapse navbar-collapse" id="collapsenav">

            <ul class="nav navbar-nav navbar-right"style="margin-right: 10%;">
                <img id="user-img"
                     src="/img/front/avatar3.png"
                     style="margin:10px 0px; float:left;width:30px;height: 30px;display:none;"/>



                     <li class="pull-left">
                      <li class="light-blue dropdown-modal">
                          <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                          <#--<img class="nav-sysUser-photo" src="/img/avatars/user.jpg" alt="Jason's Photo"/>-->
                              <span class="sysUser-info">
									<#--<small>欢迎,</small>-->
									${sysUser.userName}
								</span>

                              <i class="ace-icon fa fa-caret-down"></i>
                          </a>

                          <ul class="sysUser-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close"style="top: auto; right: auto;">
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






                      </li>

                    <li id="login-ad"><a type="button"
                                         style="color:#fff;font-size:15px;background-color:#8fbc8f;border-radius:3px;padding:10px 20px;margin-top:5px;"
                                         href="${base}/login">登录</a></li>


                <!--  <li id="login-houtai"><a href="${basePath}/login-admin.jsp">后台管理</a></li> -->
            </ul>
        </div>
    </div>
</nav>
<script>
    function toMyCourse() {
        let courses = document.getElementById('mycourses');
        console.log(courses !== null);
        if (courses !== undefined && courses !== null) {
            console.log("---=-=")
            document.getElementById('mycourses').scrollIntoView();
        } else {
            window.location.href = '?cur=_';
        }
    }
</script>
<!--主体部分-->

<section>
    <div style="width: 1200px; margin: 0px auto">
        <div class="left">
            <div>
                <div style="margin: 0px auto; width: 100px; margin-top: 20px;">
                    <img src="/img/front/user.png" class="img-circle" width="100px " height="100px">
                    <input type="file" style="display: none" id="upImg"
                           onchange="changeImg(this)"> <label for="upImg"
                                                              id="preview1">


                </label>

                </div>
                <div
                        style="color: #f2cf2e !important; text-aglin: center; text-align: center; margin-top: 10px; font-size: 15px;">
                    <@shiro.principal property="userName"/></div>
                <div class="center-block" style="width: 80px; margin-top: 10px;">
                    <button class="btn btn-success" onclick="toindex()">返回首页</button>
                    <a class="btn btn-success"
                       href="/logout"
                       style="margin-top: 10px;"> 退出登录</a>
                </div>
                <div
                        style="height: 20px; margin: 10px 0px; background-color: #efefef;">
                </div>
                <#--<div style="text-align: center; font-size: 13px; color: #656565">-->
                    <#--<div class="attention">-->
                        <#--关注了&nbsp;&nbsp;<strong><a href="#"-->
                                                  <#--onclick="selectattention();">5</a></strong>-->
                        <#--&nbsp;&nbsp;|&nbsp;&nbsp; 关注者&nbsp;&nbsp;<strong><a-->
                            <#--href="#" onclick="selectattention();">2</a></strong>-->
                    <#--</div>-->
                <#--</div>-->
                <div style="margin: 10px 20px; color: #656565">
                    <label style="padding: 5px; font-size: 13px;"><span
                            class="glyphicon glyphicon-user"
                            style="color: #1d9cba; font-size: 18px;"></span>&nbsp;&nbsp;&nbsp;二班</label><br>
                    <label style="padding: 5px; font-size: 13px;"><span
                            class="glyphicon glyphicon-education"
                            style="color: #1d9cba; font-size: 18px;"></span>&nbsp;&nbsp;&nbsp;东莞理工学院城市学院</label>
                </div>
                <div
                        style="height: 20px; margin: 10px 0px; background-color: #efefef;">
                </div>
                <div
                        style="border-bottom: solid 1px #d1d1d1; padding: 10px 20px; font-size: 15px;">
                    成就
                </div>
                <div style="border-bottom: solid 1px #d1d1d1">
                    <div style="margin: 10px 20px; color: #656565;">
							<span class="glyphicon glyphicon-leaf"
                                  style="color: #1d9cba; font-size: 18px;"></span>&nbsp;&nbsp;&nbsp;积分值
                        <label style="float: right; font-size: 13px; color: #1d9cba">${credit}</label>
                    </div>
                </div>
                <#--<div style="height: 50px;">-->
                    <#--<div style="margin: 10px 20px; color: #656565;">-->
                        <#--<div-->
                                <#--style="float: left; width: 65px; text-align: center; font-size: 10px;">-->
                            <#--97<br> <label>次采纳</label>-->
                        <#--</div>-->

                        <#--<div-->
                                <#--style="float: left; width: 65px; text-align: center; font-size: 10px;">-->
                            <#--98<br> <label>题正确</label>-->
                        <#--</div>-->
                        <#--<div-->
                                <#--style="float: left; width: 65px; text-align: center; font-size: 10px;">-->
                            <#--99<br> <label>次点赞</label>-->
                        <#--</div>-->
                    <#--</div>-->
                <#--</div>-->
            </div>
        </div>

        <script type="text/javascript">

            function setClass1(obj) {
                clearClass();
                obj.style.color = "#fff";
            }

            function clearClass() {
                var aList = document.getElementsByName("ca");
                for (var i = 0, len = aList.length; i < len; i++) {
                    aList[i].removeAttribute("style");
                }
            }

            function setClass2(obj) {
                clearClass2();
                obj.style.backgroundColor = "#5cb85c";
            }

            function clearClass2() {
                var aList = document.getElementsByName("item");
                for (var i = 0, len = aList.length; i < len; i++) {
                    aList[i].removeAttribute("style");
                }
            }

        </script>
        <div class="left-2">
            <div style="height: 45px;">
                <ul id="ul1" role="tablist">
                    <li class="li1 active" onclick="setClass2(this);" name="item">
                        <a href="#tongzhi" name="ca" onclick="setClass1(this);"
                           role="tab" data-toggle="tab">通知</a>
                    </li>
                    <li class="li1" onclick="setClass2(this);" name="item"><a
                                href="#keqian" name="ca" onclick="setClass1(this);" role="tab"
                                data-toggle="tab">课前作业</a></li>
                    <li class="li1" onclick="setClass2(this);" name="item"><a
                            href="#shuati" name="ca" onclick="setClass1(this);" role="tab"
                            data-toggle="tab">刷题</a></li>
                    <li class="li1" onclick="setClass2(this);" name="item"><a
                            href="#zuoye" name="ca" onclick="setClass1(this);" role="tab"
                            data-toggle="tab">作业</a></li>
                    <li class="li1" onclick="setClass2(this);" name="item"><a
                            href="#kaoshi" name="ca" onclick="setClass1(this);" role="tab"
                            data-toggle="tab">考试</a></li>
                    <li class="li1" onclick="setClass2(this);" name="item"><a
                            href="#huida" name="ca" onclick="setClass1(this);" role="tab"
                            data-toggle="tab">回答</a></li>
                    <li class="li1" onclick="setClass2(this);" name="item"><a
                            href="#fatie" name="ca" onclick="setClass1(this);" role="tab"
                            data-toggle="tab">发帖</a></li>
                    <li class="li1" onclick="setClass2(this);" name="item"><a
                            href="#ziliao" name="ca" onclick="setClass1(this);" role="tab"
                            data-toggle="tab">个人资料</a></li>
                </ul>
            </div>
            <div style="background-color: #5cb85c; height: 5px"></div>
            <div class="tab-content">
                <div class="tab-pane active" id="tongzhi">
                    <div class="row" id="auto_page_content" style="padding: 5px">

                        <div class="col-xs-12">

                            <h3 class="header smaller lighter blue">查看公告</h3>

                            <!-- <div class="modal-footer no-margin-top">
                                <table>

                                    <tr>
                                        <td>&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);"
                                            onclick="goToUncheck();" class="btn btn-success"radius-4">
                                                未读公告 </a>&nbsp;&nbsp;&nbsp;
                                        </td>
                                        <td>&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);"
                                            onclick="goToCheck();" class="btn btn-success"radius-4">
                                                全部公告 </a>&nbsp;&nbsp;&nbsp;
                                        </td>

                                    </tr>

                                </table>
                            </div> -->

                            <input type="hidden" id="show_rangeAnnounce"/>
                            <table id="sample-table-2"
                                   class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>公告名称</th>
                                    <th>发布时间</th>
                                    <th>截至时间</th>
                                    <th>状态</th>
                                    <th>操作</th>

                                </tr>
                                </thead>
                                <tbody id="getInfo">
                                <#--<c:forEach var="proclamation" items="${proclamation}">
                                    <tr>
                                        <td>2019年大学生创新创业大赛</td>
                                        <td>2019-04-01</td>
                                        <td>2019-04-12</td>

                                        <td id="sta-${proclamation.stuproclamation.id }">
                                            <c:if test="${proclamation.state != 0 }">已读</c:if></td>
                                        <td><a onclick="getInfo('${proclamation.id }','${proclamation.stuproclamation.id }')">查看详情</a></td>
                                    </tr>
                                </c:forEach>-->
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <!-- Modal -->
                    <div class="modal fade" id="addContactsModal" tabindex="-1"
                         role="dialog" aria-labelledby="myModalLabel">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <!--模态框的头部 -->
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                    <h4 class="modal-title">
                                        <span></span>公告详情
                                    </h4>
                                </div>

                                <!-- 模态框的身体部分 -->
                                <div class="modal-body">
                                    <table class="table table-bordered">
                                        <!-- <form id="addContactsForm" class="form-horizontal"> -->
                                        <tr>
                                            <th>公告标题:</th>
                                            <td name="title" id="title"></td>
                                        </tr>
                                        <tr>
                                            <th>公告内容:</th>
                                            <td name="content" id="content"></td>
                                        </tr>
                                        <tr>
                                            <th>发布者:</th>
                                            <td name="createName" id="createName"></td>
                                        </tr>
                                        <tr>
                                            <th>开始时间:</th>
                                            <td name="publishDate" id="publishDate"></td>
                                        </tr>
                                        <tr>
                                            <th>结束时间:</th>
                                            <td name="publichedDate" id="publichedDate"></td>
                                        </tr>

                                        <tr>
                                            <th>附件:</th>
                                            <td id="fuj">
                                                <div id="accessory"
                                                     style="text-align: bottom:0;">
                                                </div>
                                            </td>
                                        </tr>


                                        <%-- ${proclamation1.title } --%>
                                        <!-- </form> -->
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="tab-pane" id="shuati">
                    <div style="width: 890px">
                        <h4 style="margin: 20px 30px;">
                            <span style="color: #1d9cba;" class="glyphicon glyphicon-pencil"></span>&nbsp;&nbsp;练习的课程
                        </h4>
                    </div>
                </div>


                <div class="tab-pane" id="huida">
                    <div style="width: 890px">
                        <div>
                            <h4 style="margin: 20px 30px;">
									<span style="color: #1d9cba;"
                                          class="glyphicon glyphicon-thumbs-up"></span>&nbsp;&nbsp;回答的问题
                            </h4>
                        </div>
                         <#list replylist as reply>
                            <div class="huida">
                                <div style="margin-bottom: 5px;">
                                    <a href="javaScript:void(0)" id="${ reply.topic.id }"
                                       name="reply">${reply.topic.title}</a>
                                </div>
                                <div style="color: #a2a2a2; margin-bottom: 8px;">
                                    ${reply.content}
                                </div>
                                <div style="color: #5cb85c;">发表于 [${reply.topic.title}]</div>
                            </div>
                         </#list>
                    </div>


                </div>

                <div class="tab-pane" id="fatie">
                    <div class="row feature">
                        <div style="width: 890px">
                            <#list topiclist as topic>
                                <div class="huida">
                                    <div style="margin-bottom: 5px;">
                                        <a href="javaScript:void(0)" id="${topic.id }"
                                           name="topic">${topic.title }</a>
                                    </div>
                                    <div style="color: #a2a2a2; margin-bottom: 8px;">
                                        ${topic.content }
                                    </div>
                                    <div style="color: #5cb85c;">发表于 [${topic.title}] }</div>
                                </div>
                            </#list>
                        <#-- <c:forEach items="${topiclist}" var="topiclist">
                             <div class="huida">
                                 <div style="margin-bottom: 5px;">
                                     <a href="javaScript:void(0)" id="${topiclist[3] }"
                                        name="topic">C语言</a>
                                 </div>
                                 <div style="color: #a2a2a2; margin-bottom: 8px;">
                                     C语言的指针详解 }
                                 </div>
                                 <div style="color: #5cb85c;">发表于 [C语言] }</div>
                             </div>
                         </c:forEach>-->


                        </div>

                    </div>
                </div>

                <div class="tab-pane" id="ziliao">
                    <div class="row feature">
                        <div style="width: 890px; margin-bottom: 30px;">
                            <div style="margin-left: 30px; border-bottom: solid 1px #bebebe">
                                <h4 style="margin: 20px 30px;">
                                    <span style="color: #1d9cba;" class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;基本信息
                                </h4>
                            </div>
                            <div>
                                <div class="jianjie">
                                    我的学号：<span class="neirong"><@shiro.principal property="loginName"/></span>
                                </div>
                                <div class="jianjie">
                                    我的名称：<span class="neirong">
											${sysUser.userName}
                                </div>
                                <div class="jianjie">
                                    我的性别：<span class="neirong">
                                        <@dictionary dictType="sys_user_sex">
                                            <#assign sex>
                                                ${sysUser.sex}
                                            </#assign>
                                            <#list dictData as row>
                                                <#if (row.dictValue == sex)>${row.dictLabel}</#if>
                                            </#list>
                                        </@dictionary>
                                </span>
                                </div>
                                <div class="jianjie">
                                    我的邮箱：<span class="neirong">
                                  	${sysUser.email}
                                </span>
                                </div>
                                <div class="jianjie">
                                    我的班级：<span class="neirong">二班</span>
                                </div>
                                <div class="jianjie">
                                    我的学校：<span class="neirong">东莞理工学院城市学院</span>
                                </div>
                                <div class="center-block"
                                     style="width: 80px; margin-top: 10px;">
                                    <button class="btn btn-default" onclick="toxiugai()">
                                        修改密码
                                    </button>
                                    <button class="btn btn-default" onclick="toxiugaiemail()">
                                        修改邮箱
                                    </button>

                                </div>
                            </div>
                        </div>

                    </div>
                </div>

                <div class="tab-pane" id="zuoye">
                    <div style="width: 890px">
                        <div>
                            <h4 style="margin: 20px 30px;">
									<span style="color: #1d9cba;"
                                          class="glyphicon glyphicon-thumbs-up"></span>&nbsp;&nbsp;我的作业
                            </h4>
                        </div>
                    </div>

                </div>


               <div class="tab-pane" id="kaoshi">
                    <div style="width: 890px">
                        <div>
                            <h4 style="margin: 20px 30px;">
                                <img src="/img/front/kaoshi.png" style="width:30px;">&nbsp;&nbsp;我的考试
                            </h4>
                        </div>
                    </div>

                </div>
                <div class="tab-pane" id="keqian">
                    <div style="width: 890px">
                        <div>
                            <h4 style="margin: 20px 30px;">
                                <img src="/img/front/kaoshi.png" style="width:30px;">&nbsp;&nbsp;我的课前作业
                            </h4>
                        </div>
                    </div>

                </div>
            </div>


        </div>

    </div>

</section>


<div id="modal-table-xiugaitouxiang" class="modal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4>修改头像</h4>
            </div>
            <div class="modal-body overflow-visible col-xs-12">

                <div class="col-xs-1"></div>
                <div class="col-xs-10">

                    <div class="form-group" style="margin: 0 auto;">
                        <label for="upImg" id="preview"> <img id="imghead"
                                                              class="img-circle"
                                                              src="/img/front/user.png"
                                                              width="250px " height="300px alt=" 头像">

                        </label>
                    </div>
                    <div id="imgmsg"></div>
                </div>
                <div class="col-xs-1"></div>


            </div>
            <input type="hidden" name="x1" value="0"> <input
                type="hidden" name="y1" value="0"> <input type="hidden"
                                                          name="x2" value="100"> <input type="hidden" name="y2"
                                                                                        value="100">
            <div class="modal-footer">
                <button class="btn btn-sm btn-default" type="button" id="quxiao">
                    <i class="icon-ok"></i>取消
                </button>
                <button class="btn btn-sm btn-primary" type="button"
                        id="saveupdate">
                    <i class="icon-ok"></i>保存
                </button>
            </div>
        </div>
    </div>
</div>

<div id="modal-table-updateUser" class="modal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4>登录信息</h4>
            </div>
            <div class="modal-body overflow-visible col-xs-12">

                <div class="col-xs-2"></div>
                <div class="col-xs-8">
                    <div id="userCue" class="cue">请注意格式</div>
                    <div class="form-group">
                        <label>用&nbsp;户&nbsp;名：</label> <input id="stuName" type="text"
                                                               readonly="true " class="form-control col-xs-4"
                                                               value="${sysUser.userName}"/>
                    </div>
                    <div class="form-group">
                        <label>原&nbsp;&nbsp;密&nbsp&nbsp;码：</label> <input
                            id="oldPassword" type="password" class="form-control col-xs-4"
                            placeholder="原密码"/>
                    </div>
                    <div class="form-group">
                        <label>密&nbsp;&nbsp;&nbsp;&nbsp;码：</label> <input
                            id="stuPassword" type="password" class="form-control col-xs-4"
                            placeholder="若不想更改密码可留空"/>
                    </div>
                    <div class="form-group">
                        <label>确认密码：</label> <input id="stuPassword2" type="password"
                                                    class="form-control col-xs-4" placeholder="若不想更改密码可留空"/>
                    </div>
                </div>
                <div class="col-xs-2"></div>


            </div>
            <div class="modal-footer">

                <button class="btn btn-sm btn-primary" type="button" id="saveUser">
                    <i class="icon-ok"></i>保存
                </button>
            </div>
        </div>
    </div>
</div>

<div id="modal-table-updateUser-email" class="modal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4>登录信息</h4>
            </div>
            <div class="modal-body overflow-visible col-xs-12">

                <div class="col-xs-2"></div>
                <div class="col-xs-8">
                    <div id="userCue" class="cue">请注意格式</div>
                    <div class="form-group">
                        <label>用&nbsp;户&nbsp;名：</label> <input id="stuName" type="text"
                                                               readonly="true " class="form-control col-xs-4"
                                                               value="${sysUser.userName}"/>
                    </div>

                    <div class="form-group">
                        <label>邮&nbsp;&nbsp;&nbsp;&nbsp;箱：</label> <input
                            id="newemail" type="text" class="form-control col-xs-4"
                            placeholder="若不想更改邮箱可留空"/>
                    </div>

                </div>
                <div class="col-xs-2"></div>


            </div>
            <div class="modal-footer">

                <button class="btn btn-sm btn-primary" type="button" id="saveUseremail">
                    <i class="icon-ok"></i>保存
                </button>
            </div>
        </div>
    </div>
</div>


<script>
    (function () {
        var studentId="${student.id}";
        $.ajax({
            type: "post",
            dataType: 'json',
            url: "/user/course/work/"+studentId,
            error: function (request) {
                alert("初始化失败!");
            },
            success: function (data) {
                for (var i = 0; i < data.length; i++) {
                    var userId="'"+data[i].userId+"'";
                    var testPaperId="'"+data[i].testPaperId+"'";
                    var id="'"+data[i].id+"'";
                    var stuStartTime="'"+data[i].stuStartTime+"'";
                    var stuEndTime="'"+data[i].stuEndTime+"'";
                    $('#zuoye').append('<div class="zuoye">' +
                            '<div style="margin-bottom: 5px;">' +
                            '<a href="" id="" name="reply">' +data[i].headLine+
                            '</a>' +
                            ' </div>' +
                            ' <div style="color: #a2a2a2; margin-bottom: 8px;">' +data[i].testName+
                            '</div>' +
                            ' <div style="color: #5cb85c;">' +
                            '<button class="learn-btn" onclick="openwindow(' +userId+','+testPaperId+','+id+','+stuStartTime+','+stuEndTime+
                            ')"> 开始' +
                            '</button> ' +
                            '</div></div>');

                }

            }
        });
    })();

    (function () {
        var studentId="${student.id}";
        $.ajax({
            type: "post",
            dataType: 'json',
            url: "/user/course/exam/"+studentId,
            error: function (request) {
                alert("初始化失败!");
            },
            success: function (data) {
                console.log("data:"+data);
                for (var i = 0; i < data.length; i++) {
                    var userId="'"+data[i].userId+"'";
                    var testPaperId="'"+data[i].testPaperId+"'";
                    var id="'"+data[i].id+"'";
                    var time="'"+data[i].stuStartTime+"'";
                    $('#kaoshi').append('<div class="kaoshi" style="margin-left:10px;margin-bottom:15px;border-radius:30px;border:2px solid #5CB85C;padding: 10px 10px 10px 10px;width:300px">' +
                            ' <p class="test_font">' +
                            ' <h4>卷名:' +data[i].testName+
                            '&nbsp;&nbsp; </h4>' +
                            '<h5>时间:' +data[i].time+
                            '分钟&nbsp;' +
                            '成绩：' +data[i].stuScore+
                            '&nbsp;' +
                            ' 总分:' +data[i].paperscore+
                            '&nbsp;&nbsp;  </h5>'+
                            ' <div>' +
                            '<button class="learn-btn" style="border:2px solid;border-radius:25px;background-color: #5cb85c;color: white" onclick="openExamWindow(' +userId+','+testPaperId+','+id+','+time+
                            ')"> 开始' +
                            '</button> ' +
                            '</div></div>');

                }
            }
        });
    })();

    (function () {
        var studentId="${student.id}";
        $.ajax({
            type: "post",
            dataType: 'json',
            url: "/user/course/shuati/"+studentId,
            error: function (request) {
                alert("初始化失败!");
            },
            success: function (data) {
                console.log("data:！！！！！！！！！！！！！！！！！！！"+data);
                for (var i = 0; i < data.length; i++) {
                    $('#shuati').append('<div class="col-md-4 col-xs-12">'+
                        '<div class="information center-block">'+
                        '<div class="infotitle">'+
                        '<div style="font-size: 15px;color:#6aaf75;">'+data[i].course.name+'</div >'+
                        '</div>'+
                        '<img class="img-responsive center-block" src="'+data[i].teacherCourse.thumbnailPath+'">'+
                        '<div style="font-size: 15px;padding: 12px;line-height: 25px;height: 30px;overflow: hidden;">'+
                        '</div>'+
                        '<div style="text-align: right;">'+
                        '<a href="/user/personal/shuati/'+data[i].teacherCourse.cid+'" class="btn btn-warning btn-rounded">去刷题'+
                        '</a></div>'+
                        '</div></a></div>');
                }
            }
        });
    })();

    (function () {
        var studentId="${student.id}";
        $.ajax({
            type: "post",
            dataType: 'json',
            url: "/user/course/keqian/"+studentId,
            error: function (request) {
                alert("初始化失败!");
            },
            success: function (data) {
                console.log("data:"+data);
                for (var i = 0; i < data.length; i++) {
                    var userId="'"+data[i].userId+"'";
                    var testPaperId="'"+data[i].testPaperId+"'";
                    var id="'"+data[i].id+"'";
                    var time="'"+data[i].stuStartTime+"'";
                    $('#keqian').append('<div class="kaoshi" style="margin-left:10px;margin-bottom:15px;border-radius:30px;border:2px solid #5CB85C;padding: 10px 10px 10px 10px;width:300px">' +
                            ' <p class="test_font">' +
                            ' <h4>卷名:' +data[i].testName+
                            '&nbsp;&nbsp; </h4>' +
                            // '<h5>时间:' +data[i].time+
                            // '分钟&nbsp;' +
                            '成绩：' +data[i].stuScore+
                            '&nbsp;' +
                            ' 总分:' +data[i].paperscore+
                            '&nbsp;&nbsp;  </h5>'+
                            ' <div>' +
                            '<button class="learn-btn" style="border:2px solid;border-radius:25px;background-color: #5cb85c;color: white" onclick="openExamWindow(' +userId+','+testPaperId+','+id+','+time+
                            ')"> 开始' +
                            '</button> ' +
                            '</div></div>');

                }
            }
        });
    })();


    function openExamWindow(userId,testPaperId,tutId){
        // window.open('/user/chapterTest?testPaperId=' + testPaperId, '_blank');
        window.open('/user/stuToTest?testPaperId=' + testPaperId + '&&studentId=' + userId +"&&tutId="+tutId, '_blank');

    }

    function openwindow(userId, testPaperId, tutId,startTime, endTime) {
        let xhr = null;
        let geshidate = "";
        let time;
        let Servicedate;
        let isTime = new Boolean();
        if (window.XMLHttpRequest) {
            xhr = new window.XMLHttpRequest();
        } else { // ie
            xhr = new ActiveObject("Microsoft");
        }

        xhr.open("GET", "/", true);
        xhr.send(null);
        xhr.onreadystatechange = function () {
            console.log("xhr.readyState:" + xhr.readyState);
            if (xhr.readyState == 2) {
                /*这里的readyState有四种状态，方便做不同处理：
                            0: 请求未初始化
                            1: 服务器连接已建立
                            2: 请求已接收
                            3: 请求处理中
                            4: 请求已完成，且响应已就绪*/
                time = xhr.getResponseHeader("Date");
                Servicedate = new Date(time);
                geshidate = Servicedate.getFullYear() + "-" + (Servicedate.getMonth() + 1) + "-" + Servicedate.getDate() + " " + Servicedate.getHours() + ":" + Servicedate.getMinutes() + ":" + Servicedate.getSeconds();
                console.log("geshidate:" + geshidate);
                isTime=getServiceTime(geshidate, startTime, endTime);
            }
        }

        console.log("isTime:"+isTime);
        if (isTime) {
            if (confirm("您确定要开始吗？")) {
                window.open('/user/stuToTest?testPaperId=' + testPaperId + '&&studentId=' + userId +"&&tutId="+tutId, '_blank');
            }
        } else {

            alert("不在作业时间");
        }
    };

    function getServiceTime(date1, date2, date3) {
        let oDate1 = new Date(date1);
        let oDate2 = new Date(date2);
        let oDate3 = new Date(date3);
        console.log(oDate1.getTime());
        console.log(oDate2.getTime());
        console.log(oDate3.getTime());
        if ((oDate2.getTime() <= oDate1.getTime()) && (oDate1.getTime() <= oDate3.getTime())) {
            console.log("是时间");
            return true;
        } else {
            console.log("不是时间");
            return false;
        }
    }

</script>




<script type="text/javascript">

    $().ready(function () { //$().ready页面加载好就执行


    });

    function getInfo(id, proId) {
        $.post("stupublish/stupublishAction!findStuDetail.action", {id: id, proId: proId}, function (data, status) {
            var json = $.parseJSON(data);
            if (status == "success") {
                console.log(json);
                $("#title").empty();
                $("#createName").empty();
                $("#publishDate").empty();
                $("#publichedDate").empty();
                $("#content").empty();
                $("#accessory").empty();
                $("#title").append(json.proclamation.title);
                $("#createName").append(json.proclamation.createName);
                $("#publishDate").append(json.proclamation.publishDate);
                $("#publichedDate").append(json.proclamation.publichedDate);
                $("#content").append(json.proclamation.content);
                if (json.accessory != undefined) {
                    $("#accessory").append(
                            '<a href="stuproclamation/stuproclamationAction!fileDownload.action?accessory.id=' + json.accessory.id + '">' + json.accessory.name + '</a>'
                    );
                }
                $('#addContactsModal').modal('show');
            } else {
                alert("连接失败");
            }
        });
        $('#addContactsModal').on('hidden.bs.modal', function (e) {
            $('#sta-' + proId).html("已读");
        });
    }


    function selectattention() {

        var s = "<%=session.getAttribute("
        userId
        ")%>";
        if (s != "null") {
            window.location.href = "integral/allintegralAct!findattentionlist.action?userId=" + s;

        } else {
            return false;
        }
    }

    function toxiugai() {

        $('#modal-table-updateUser').modal('show');

    }
    function toxiugaiemail() {

        $('#modal-table-updateUser-email').modal('show');

    }
    $("#quxiao").click(
            function () {
                $('#modal-table-xiugaitouxiang').modal('hide');
                window.location.href = "integral/allintegralAct!personalByuserid.action?userId=<%=session.getAttribute("
                userId
                ")%>";

            });
    $("#saveupdate").click(
            function () {

                $('#modal-table-xiugaitouxiang').modal('hide');


                var x1 = $("input[name='x1']").val();
                var y1 = $("input[name='y1']").val();
                var x2 = $("input[name='x2']").val();
                var y2 = $("input[name='y2']").val();
                var img64 = $("#imghead").attr("src");
                //alert(x1 + ":" + y1 + ":" + x2 + ":" + y2);

                $.ajax({
                    type: "post",
                    url: "integral/allintegralAct!uploadImage.action",
                    data: {
                        'x1': x1,
                        'y1': y1,
                        'x2': x2,
                        'y2': y2,
                        'image': img64
                    },
                    async: false, //同步处理
                    success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success
                        if (data == "success") {
                            window.location.href = "integral/allintegralAct!personalByuserid.action?userId=<%=session.getAttribute("
                            userId
                            ")%>";
                            //alert("修改成功!");
                        } else {
                            window.location.href = "integral/allintegralAct!personalByuserid.action?userId=<%=session.getAttribute("
                            userId
                            ")%>";
                            alert("添加失败！");
                        }
                    }
                });

            });

    function toindex() {

        window.location.href = "";

    }

    //读取总页码or列出全部资源
    function yematongji(dangqianyema) {

        window.location.href = "courseUP/CourseUploadAction!fileyema.action?DangQianYeMa=" + dangqianyema;

    }

    //读取总页码or列出全部课件
    function kejianku(dangqianyema) {

        window.location.href = "courseUP/CourseUploadAction!kejianku.action?DangQianYeMa=" + dangqianyema;

    }

    //读取总页码or列出全部上机
    function shangjitiku(dangqianyema) {

        window.location.href = "courseUP/CourseUploadAction!shangjitiku.action?DangQianYeMa=" + dangqianyema;

    }

    //读取总页码or列出全部试题
    function shitiku(dangqianyema) {

        window.location.href = "courseUP/CourseUploadAction!shitiku.action?DangQianYeMa=" + dangqianyema;

    }

    //读取总页码or列出全部视频
    function shipinku(dangqianyema) {

        window.location.href = "courseUP/CourseUploadAction!shipinku.action?DangQianYeMa=" + dangqianyema;

    }

    //读取总页码or列出全部案例
    function anliku(dangqianyema) {

        window.location.href = "courseUP/CourseUploadAction!anliku.action?DangQianYeMa=" + dangqianyema;

    }

    //读取开发工具的信息
    function kaifagongju() {

        window.location.href = "tool/DeveToolMangeAction!showHuangjinQiantai.action";

    }

    //读取总页码or列出全部资源
    function newstongji(dangqianyema) {

        window.location.href = "news/newsMangeAction!newsyema.action?DangQianYeMa=" + dangqianyema;

    }

    //课程学习
    function coursefg() {
        window.location.href = "learn/courseLearnMangeAction!toCourseLearnList.action";

    }

    //读取总页码or列出全部问题
    function talktongji(dangqianyema) {

        window.location.href = "talk/talkMangeAction!talkyema.action?DangQianYeMa=" + dangqianyema;


    }

    //读取学习指导的信息
    function xuexizhidao() {

        window.location.href = "tool/DeveToolMangeAction!showDaoxueQiantai.action";

    }

    $("[name='topic']").click(function () {
        var id = $(this).attr("id");
        window.location.href = "${basePath }/user/topic/detail/" + id;
    });

    $("[name='reply']").click(function () {
        var id = $(this).attr("id");
        window.location.href = "${basePath }/user/topic/detail/" + id;
    });

    function xuanzetimu(testid) {
        //alert(testid);
        window.location.href = "${basePath }/onlineExam/testManageAct!TestStart.action?testid=" + testid,
                "mywindow", "status=1,toolbar=0";

    }


    $("#saveUser")
            .click(
                    function () {

                        if ($('#stuName').val().length < 2
                                || $('#stuName').val().length > 16) {

                            $('#user').focus().css({
                                border: "1px solid red",
                                boxShadow: "0 0 2px red"
                            });
                            $('#userCue')
                                    .html(
                                            "<font color='red'><b>×用户名位2-16字符</b></font>");
                            return false;
                        }
                        if ($('#oldPassword').val().length != 0) {
                            if ($('#oldPassword').val().length < 6) {
                                $('#oldPassword').focus();
                                $('#userCue').html(
                                        "<font color='red'><b>×密码小于" + 6
                                        + "位</b></font>");
                                return false;
                            }
                        }
                        if ($('#stuPassword').val().length != 0) {
                            if ($('#stuPassword').val().length < 6) {
                                $('#stuPassword').focus();
                                $('#userCue').html(
                                        "<font color='red'><b>×密码不能小于" + 6
                                        + "位</b></font>");
                                return false;
                            }
                            if ($('#stuPassword').val() != $(
                                    '#stuPassword2').val()) {
                                $('#stuPassword2').focus();
                                $('#userCue')
                                        .html(
                                                "<font color='red'><b>×两次密码不一致！</b></font>");
                                return false;
                            }
                            if ($('#oldPassword').val() == $('#stuPassword').val()) {
                                $('#stuPassword').focus();
                                $('#userCue')
                                        .html(
                                                "<font color='red'><b>×新密码与原密码一致！</b></font>");
                            }
                        }


                        var stuName = $("#stuName").val();
                        var stuPassword = $("#stuPassword").val();
                        var oldPasswrod = $("#oldPassword").val();
                        $
                                .ajax({
                                    type: "post",
                                    url: "/user/password",
                                    data: {
                                        "stuName": stuName,
                                        "oldPassword": oldPasswrod,
                                        "password": stuPassword,
                                    },
                                    async: false, //同步处理
                                    success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success
                                        data = JSON.parse(data)
                                        if (data.msg == "success") {
                                            alert("修改成功！");
                                            $('#modal-table-updateUser')
                                                    .modal('hide');
                                            location.reload()
                                        } else {
                                            alert("添加失败！");
                                            $('#modal-table-updateUser')
                                                    .modal('hide');
                                        }
                                    }
                                });

                    });
    $("#saveUseremail")
            .click(
                function () {
                    var email=$("#newemail").val();
                    var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
                    if($("#newemail").val()=="")
                    {
                        alert("邮箱不能为空");
                        return false;


                }else if(!reg.test(email))
                    {
                       alert("邮箱格式不正确");
                        $("#email1").focus();
                        return false;
                    };
                    var stuName = $("#stuName").val();
                    var stuemail = $("#newemail").val();

                    $
                            .ajax({
                                type: "post",
                                url: "/user/email",
                                data: {
                                    "stuName": stuName,
                                    "stuemail": stuemail,
                                },
                                async: false, //同步处理
                                success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success
                                    data = JSON.parse(data)
                                    if (data.msg == "success") {
                                        alert("修改成功！");
                                        $('#modal-table-updateUser')
                                                .modal('hide');
                                        location.reload()
                                    } else {
                                        alert("添加失败！");
                                        $('#modal-table-updateUser')
                                                .modal('hide');
                                    }
                                }
                            });
                }
            );
</script>


</body>
</html>

