<#macro adminLayout pageTitle title keywords description>
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
        <style type="text/css">
            .type1 {
                position: fixed;
                left: 50%;
                top: 50%;
                width: 300px;
                height: 100px;
                margin: -100px 0px 0px -150px;
                border: 5px solid #888;
                background-color: rgba(255, 140, 0, 0.5);
                text-align: center;
            }

            .type2 {
                background-color: rgba(255, 140, 0, 0.1);
                border-style: groove;
                border-color: rgb(184, 134, 11)
            }
        </style>

    </head>



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
                <a href="/admin" class="navbar-brand">
                    <small>
                        <i class="fa fa-leaf"></i>
                        智慧教育云服务平台后台管理
                    </small>
                </a>
            </div>

            <div class="navbar-buttons navbar-header pull-right" role="navigation">
                <ul class="nav ace-nav">

                          <li class="purple dropdown-modal">
                              <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                                  <i class="ace-icon fa fa-exchange"></i>
                                  <small>切换角色</small>
                                  <i class="ace-icon fa fa-caret-down"></i>
                                  <ul class="sysUser-menu dropdown-menu-right dropdown-menu dropdown-caret dropdown-close">

                                      <li class="divider"></li>
                                      <li>
                                          <a>
                                              <i class="ace-icon glyphicon glyphicon-check"></i>
                                              ${nowrole}
                                          </a>
                                      </li>
                                      <#list roles as item>
                                          <li>
                                              <a href="javascript:SclickLoad('${item.roleKey}')">
                                                  <i class="ace-icon glyphicon glyphicon-unchecked"></i>
                                                  ${item.roleName}
                                              </a>
                                          </li>
                                      </#list>
                                  </ul>
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
                                <a href="#">
                                    <button onclick="logout()">
                                    <i class="ace-icon fa fa-power-off"></i>
                                    登出
                                    </button>
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
            try{ace.settings.loadState('main-container')}catch(e){}
        </script>



            <#--<div id="sidebar" class="sidebar responsive ace-save-state sidebar-fixed sidebar-scroll">-->
            <#--    <script type="text/javascript">-->
            <#--        try{ace.settings.loadState('sidebar')}catch(e){}-->
            <#--    </script>-->
            <#--    <div class="sidebar-shortcuts" id="sidebar-shortcuts">-->
            <#--        <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">-->
            <#--            <button class="btn btn-success">-->
            <#--                <i class="ace-icon fa fa-signal"></i>-->
            <#--            </button>-->

            <#--            <button class="btn btn-info">-->
            <#--                <i class="ace-icon fa fa-pencil"></i>-->
            <#--            </button>-->

            <#--            <button class="btn btn-warning">-->
            <#--                <i class="ace-icon fa fa-users"></i>-->
            <#--            </button>-->

            <#--            <button class="btn btn-danger">-->
            <#--                <i class="ace-icon fa fa-cogs"></i>-->
            <#--            </button>-->
            <#--        </div>-->

            <#--        <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">-->
            <#--            <span class="btn btn-success"></span>-->

            <#--            <span class="btn btn-info"></span>-->

            <#--            <span class="btn btn-warning"></span>-->

            <#--            <span class="btn btn-danger"></span>-->
            <#--        </div>-->
            <#--    </div><!-- /.sidebar-shortcuts &ndash;&gt;-->

            <#--    <ul class="nav nav-list main-nav-list">-->

            <#--        <#list menus as row>-->
            <#--            <li class="">-->
            <#--                <a href="${row.url}" class="dropdown-toggle">-->
            <#--                    <i class="menu-icon ${row.icon}"></i>-->

            <#--                    ${row.menuName}-->
            <#--                    <b class="arrow fa fa-angle-down"></b>-->
            <#--                </a>-->

            <#--                <b class="arrow"></b>-->

            <#--                <#if row.children??>-->
            <#--                    <ul class="submenu">-->
            <#--                        <#list row.children as c>-->
            <#--                            <li class="">-->
            <#--                                <a href="${c.url}">-->
            <#--                                    <i class="menu-icon fa fa-caret-right ${c.icon}"></i>-->
            <#--                                    ${c.menuName}-->
            <#--                                </a>-->
            <#--                                <#if c.children??>-->
            <#--                                    <ul class="submenu">-->
            <#--                                        <#list c.children as cc>-->
            <#--                                            <li class="">-->
            <#--                                                <a href="${cc.url}">-->
            <#--                                                    <i class="menu-icon fa fa-caret-right ${cc.icon}"></i>-->
            <#--                                                    ${cc.menuName}-->
            <#--                                                </a>-->

            <#--                                                <b class="arrow"></b>-->
            <#--                                            </li>-->
            <#--                                        </#list>-->
            <#--                                    </ul>-->
            <#--                                </#if>-->
            <#--                                <b class="arrow"></b>-->
            <#--                            </li>-->
            <#--                        </#list>-->
            <#--                    </ul>-->
            <#--                </#if>-->
            <#--            </li>-->
            <#--        </#list>-->

            <#--    </ul><!-- /.nav-list &ndash;&gt;-->

            <#--    <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">-->
            <#--        <i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state"-->
            <#--           data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>-->
            <#--    </div>-->
            <#--</div>-->


        <div class="main-content">
            <div class="main-content-inner">
                <div class="breadcrumbs ace-save-state breadcrumbs-fixed" id="breadcrumbs">
                    <ul class="breadcrumb">
                        <li>
                            <i class="ace-icon fa fa-home home-icon"></i>
                            <a href="">首页</a>
                        </li>
                        <li class="active"></li>

                    </ul><!-- /.breadcrumb -->
                </div>
                <#nested />
            </div>
        </div><!-- /.main-content -->

        <#include "/common/footer.ftl">
    </div><!-- /.main-container -->


    </body>
 <script>
     var token = localStorage.getItem("token");
     $(document).click(function(e) { // 在页面任意位置点击而触发此事件
         if($(e.target).is('a')){

             var href= $(e.target).attr("href")
             if(href!=="#"&&href!==""&&href!==undefined) {
                 if (href.indexOf("?") === -1) {
                     href = href + "?token=" + token
                 } else {
                     href = href + "&token=" + token
                 }
             }
             $(e.target).attr("href",href)
         };       // e.target表示被点击的目标
     });


 </script>

    </html>
</#macro>

