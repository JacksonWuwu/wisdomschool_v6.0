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
<script>
<#--    <@shiro.lacksRole name="sds_admin">-->
<#--         window.location.href = '/admin/teacher';-->
<#--    </@shiro.lacksRole>-->
</script>
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
        try{ace.settings.loadState('main-container')}catch(e){}
    </script>
    <div class="main-content">
        <div class="main-content-inner">
            <div class="breadcrumbs ace-save-state breadcrumbs-fixed" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="/admin/index">首页</a>
                    </li>
                    <li class="active "></li>

                </ul><!-- /.breadcrumb -->

                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="space-6"></div>
                        <div class="row">
                            <div class="col-sm-10 col-sm-offset-1">
                                <div class="widget-box transparent">
                                    <div class="widget-header widget-header-large">
                                        <h3 class="widget-title grey lighter">
                                            <i class="ace-icon fa fa-leaf green"></i>
                                            选择角色
                                        </h3>
                                    </div>

                                    <div class="widget-body">
                                        <div class="widget-main padding-24 align-center " style="height: 320px">
                                            <div class="row">
                                                <div class="col-sm-3">
                                                    <#list roles as item>
                                                        <div class="label label-info arrowed-in arrowed-right">
                                                            <button class="btn btn-lg btn-success " style="height: 180px;width:120px;font-size: 40px;border-radius: 8px" onclick="SclickLoad('${item.roleKey}')">${item.roleName}</button>
                                                        </div>
                                                    </#list>
                                                </div><!-- /.col -->
                                            </div><!-- /.row -->
                                        </div>
        </div>
    </div><!-- /.main-content -->

    <#include "/common/footer.ftl">
</div><!-- /.main-container -->

<!-- basic scripts -->


        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script >
    function SclickLoad(item) {
        let wp = window;
        let token=localStorage.getItem("token");
        wp.location.href='/admin/'+item+"?token="+token;
    }
</script>
</body>

</html>
