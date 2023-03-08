<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
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
    <link href="/css/indexstyle1.css" type="text/css" rel="stylesheet">
    <link href="/css/dati.css" type="text/css" rel="stylesheet">
    <link href="/css/common-less.css" type="text/css" rel="stylesheet">
    <script src="/js/jquery-2.1.4.min.js" type="text/javascript"></script>
    <script src="/js/bootstrap.min.js" type="text/javascript"></script>
    <script
            src="/js/plugins/uploadimage/js/jquery.imgareaselect.min.js"></script>
    <script src="/js/plugins/uploadimage/js/image.js"></script>
    <link href="/js/plugins/layui/css/layui.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="/js/plugins/pagination/style/pagination.css" media="screen">
    <link rel="stylesheet" type="text/css" href="/js/plugins/pagination/style/normalize.css" media="screen">
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

                      </li>





            </ul>
        </div>
    </div>
</nav>
<!--主体部分-->

<div class="wrap" style="width: 1200px; margin: 0px auto">

    <div class="slider" style="position: absolute; left: 24px; top: 0px;">
        <#--    头像  -->
        <div class="avator-wapper">
            <div class="avator-mode" style="text-align: center;">
                <button class="btn btn-default" onclick="updateAvatar()" style="background: bottom">
                <img class="avator-img" src="/img/front/user.png" data-portrait="5458501000018e5802200220"
                     width="92" height="92">
                <div class="update-avator" style="bottom: -30px;">
                    <input class="" type="file" style="display: none" id="upImg" onchange="changeImg(this)">
                </div>
            </div>
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
                                    <input type="file" id="upImg" accept="image/*" style="display: none;">
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

            <div style="color: #f2cf2e !important; text-aglin: center; text-align: center; margin-top: 10px; font-size: 15px;">
                    <span></span>${sysUser.userName}</span></div>
            <div class="center-block" style="width: 80px; margin-top: 10px;">
                <#--<button class="btn btn-success" onclick="toindex()">返回首页</button>-->
                <#--<a class="btn btn-success" href="/logout" style="margin-top: 10px;"> 退出登录</a>-->
            </div>
        </div>

        <ul>
            <li><a name="/account/index" href="/account/index" style="text-decoration: none">个人资料</a></li>
<#--            <li><a name="/account/exercise" href="/account/exercise" style="text-decoration: none">随机刷题</a></li>-->
<#--            <li><a name="/account/work" href="/account/work" style="text-decoration: none">我的作业</a></li>-->
<#--            <li><a name="/account/exam" href="/account/exam" style="text-decoration: none">我的考试</a></li>-->
<#--            <li><a name="/account/comment/index" href="/account/comment/index" style="text-decoration: none">我的回答</a></li>-->
<#--            <li><a name="/account/topic/index" href="/account/topic/index" style="text-decoration: none">我的帖子</a></li>-->
        </ul>

    </div>
</div>
    <script>
        $(document).ready(function () {
            var pathName = window.location.pathname;
            $("*[name='"+pathName+"']").addClass("active");
        })

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
    </script>
