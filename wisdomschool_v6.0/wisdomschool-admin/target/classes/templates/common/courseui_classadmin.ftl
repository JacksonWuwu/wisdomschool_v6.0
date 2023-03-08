<#macro courseLayout pageTitle title keywords description>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
        <meta charset="utf-8"/>
        <title>${pageTitle}</title>

        <meta name="description" content="${description}"/>
        <meta name="keywords" content="${keywords}">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>

        <#include "/common/style.ftl"/>

    </head>

    <#--
        -   sdsadmin的管理页面
        -
        -
        -->

<#--    <body class="no-skin gray-bg">-->
<#--    <div id="navbar" class="navbar navbar-default ace-save-state navbar-fixed-top">-->
<#--        <div class="navbar-container ace-save-state" id="navbar-container">-->
<#--            <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">-->
<#--                <span class="sr-only">Toggle sidebar</span>-->

<#--                <span class="icon-bar"></span>-->

<#--                <span class="icon-bar"></span>-->

<#--                <span class="icon-bar"></span>-->
<#--            </button>-->

<#--                <div class="navbar-header pull-left">-->
<#--                    <a class="navbar-brand">-->
<#--                        <a>-->
<#--                            <small>-->
<#--                                <i class="fa fa-leaf"></i>-->
<#--                            班级管理- ${name}-->
<#--                            </small>-->
<#--                        </a>-->
<#--                    </a>-->
<#--                </div>-->
    <body class="no-skin gray-bg">
    <div id="navbar" class="navbar navbar-default ace-save-state navbar-fixed-top">
        <div class="navbar-container ace-save-state" id="navbar-container">
            <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
                <span class="sr-only">Toggle sidebar</span>

                <span class="icon-bar"></span>

                <span class="icon-bar"></span>

                <span class="icon-bar"></span>
            </button>

                <div class="navbar-header pull-left">
                    <a href="/teacher/course/${cid}" class="navbar-brand">
                        <small>
                            <i class="fa fa-leaf"></i>
                            我的管理- ${name}
                        </small>
                    </a>
                </div>




            <div class="navbar-buttons navbar-header pull-right" role="navigation">
                <ul class="nav ace-nav">
                    <li class="purple dropdown-modal">
                        <a  class="dropdown-toggle" onclick="changeRole()">
                            <i class="ace-icon fa fa-exchange"></i>
                            <small> 切换角色</small>
                            <i class="ace-icon fa fa-caret-down"></i>
                        </a>
                    </li>
                    <li class="light-blue dropdown-modal">
                        <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                            <img class="nav-sysUser-photo" src="/img/avatars/user.jpg" alt="Jason's Photo"/>
                            <span class="sysUser-info">
									<small>欢迎您：</small>
									${sysUser.userName}
								</span>

                            <i class="ace-icon fa fa-caret-down"></i>

                        </a>
                        <ul class="sysUser-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">

                            <li class="divider"></li>

                            <li>
                                <a href="javascript:changePwd()">
                                    <i class="ace-icon fa fa-power-off"></i>
                                    修改密码
                                </a>
                            </li>
                            <li>
                                <a href="javascript:toEdit()">
                                    <i class="ace-icon fa fa-power-off"></i>
                                    个人信息
                                </a>
                            </li>
                            <li>
                                <a onclick="logout()">
                                    <i class="ace-icon fa fa-power-off"></i>
                                    登出
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div><!-- /.navbar-container -->
    </div>

    <div class="main-container ace-save-state" id="main-container">
        <script type="text/javascript">
            try {
                ace.settings.loadState('main-container')
            } catch (e) {
            }
        </script>
        <div id="sidebar" class="sidebar responsive ace-save-state sidebar-fixed sidebar-scroll">
            <script type="text/javascript">
                try {
                    ace.settings.loadState('sidebar')
                } catch (e) {
                }
            </script>
            <div class="sidebar-shortcuts" id="sidebar-shortcuts">
                <#--<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
                    <button class="btn-success" onclick=window.open('${ctx}/user/course/learn/${teacherCourse.id}')>
                        <i class="ace-icon fa fa-signal">课程界面</i>
                    </button>

                    <button class="btn-info" onclick=window.open('${ctx}/user/topicList')>
                        <i class="ace-icon fa fa-pencil">论坛区</i>
                    </button>
                </div>-->

                <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
                    <span class="btn btn-success"></span>

                    <span class="btn btn-info"></span>

                </div>
            </div><!-- /.sidebar-shortcuts -->

            <ul class="nav nav-list main-nav-list">
                <#list menus as row>
                    <li class="">
                        <a href="${row.url}" class="dropdown-toggle">
                            <i class="menu-icon ${row.icon}"></i>
                            ${row.menuName}
                            <#if row.children?? && (row.children?size > 0)>
                                <b class="arrow fa fa-angle-down"></b></#if>
                        </a>

                        <b class="arrow"></b>

                        <#if row.children??>
                            <ul class="submenu">
                                <#list row.children as c>
                                    <li class="">
                                        <a href="${c.url}">
                                            <i class="menu-icon fa fa-caret-right ${c.icon}"></i>
                                            ${c.menuName}
                                        </a>
                                        <#if c.children??>
                                            <ul class="submenu">
                                                <#list c.children as cc>
                                                    <li class="">
                                                        <a href="${cc.url}">
                                                            <i class="menu-icon fa fa-caret-right ${cc.icon}"></i>
                                                            ${cc.menuName}
                                                        </a>

                                                        <b class="arrow"></b>
                                                    </li>
                                                </#list>
                                            </ul>
                                        </#if>
                                        <b class="arrow"></b>
                                    </li>
                                </#list>
                            </ul>
                        </#if>
                    </li>
                </#list>
            </ul><!-- /.nav-list -->

            <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
                <i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state"
                   data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
            </div>
        </div>

        <div class="main-content">
            <div class="main-content-inner">
                <div class="breadcrumbs ace-save-state breadcrumbs-fixed" id="breadcrumbs">
                    <ul class="breadcrumb">
                        <li>
                            <i class="ace-icon fa fa-home home-icon"></i>
                            <a onclick="changeRole()">首页</a>
                        </li>
                        <li class="active "></li>
                    </ul><!-- /.breadcrumb -->


                </div>
                <#nested />
            </div>
        </div><!-- /.main-content -->

        <#include "/common/footer.ftl">
    </div><!-- /.main-container -->

    <!-- basic scripts -->
    <script>
        function toEdit() {
            var url = ctx + "/teacher/account/info";
            $.modal.open("修改信息", url, saveCallback);
        }
        function changePwd() {
            var url = ctx + "/teacher/account/changePwd";
            $.modal.open("修改信息", url, saveCallback);
        }
        function saveCallback(context) {
            $.modal.alertSuccess("操作成功！");
        }
        function SclickLoad(item) {
            let wp = window;
            wp.location.href='/admin/'+item;
        }
    </script>
    </body>
    </html>
</#macro>
