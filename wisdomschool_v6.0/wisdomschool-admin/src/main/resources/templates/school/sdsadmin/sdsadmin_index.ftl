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
    </head>

    <#--
        -   admin的管理页面
        -
        -
        -->

    <body class="no-skin gray-bg">

    <div id="navbar" class="navbar navbar-default ace-save-state navbar-fixed-top">
        <li class="navbar-container ace-save-state" id="navbar-container">
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
                                        <a href="javascript:SclickLoadrole('${item.roleKey}')">
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
<#--									<@shiro.principal property="userName"/>-->
                                     ${sysUser.userName}
								</span>

                            <i class="ace-icon fa fa-caret-down"></i>
                        </a>

                        <ul class="sysUser-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">

                            <li class="divider"></li>
                            <li>
                                <a href="/logout">
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
                            <a href="/admin">首页</a>
                        </li>
                        <li class="active "></li>

                    </ul><!-- /.breadcrumb -->

                    <#--<div class="nav-search" id="nav-search">-->
                    <#--<form class="form-search">-->
                    <#--<span class="input-icon">-->
                    <#--<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input"-->
                    <#--autocomplete="off"/>-->
                    <#--<i class="ace-icon fa fa-search nav-search-icon"></i>-->
                    <#--</span>-->
                    <#--</form>-->
                    <#--</div><!-- /.nav-search &ndash;&gt;-->
                </div>
                <#nested />
            </div>
        </div><!-- /.main-content -->

<#--        <#include "/common/footer.ftl">-->
    </div><!-- /.main-container -->

    <!-- basic scripts -->
    <script>
        function SclickLoadrole(item) {
            let wp = window;
            wp.location.href='/admin/'+item;
        }
    </script>
    </body>
    </html>
</#macro>
<#--<@shiro.hasRole name="sds_admin">-->
    <@adminLayout  "智慧教育云平台-以科技助力教育" "wstom-首页" "wstom-社区" "分享让世界更美好">
        <div class="page-content">
            <#--<div class="page-header">
                <h1>
                    首页
                    <small>
                        <i class="ace-icon fa fa-angle-double-right"></i>
                        我的课程
                    </small>
                </h1>
            </div>&ndash;&gt;<!-- /.page-header -->
            <div class="row">
                <div class="col-xs-12" id="main-page-container">
                    <div class="container-div layout-container-div clearfix">
                        <div class="row">
                            <div class="pagerContent pic-list">
                                <div class="pagerMainContent" style="display: none;">
                                    <a key="gdid"  class="pic-list-constant-item" onclick="SclickLoad(event)">
                                        <div class="">
                                            <div class="cover-info">
                                                <i class="fa fa-list-ul"></i> &nbsp;<h4 key="gradess.name"></h4>
                                                <div>
                                                    <h4 key="departments.name"></h4>
                                                </div>
                                            </div>
                                        </div>
                                    </a>
                                </div>
                                <div id="m-content">
                                </div>
                            </div>
                        </div>
                    </div><!--/.container-div-->
                </div>
            </div><!-- /.row -->
        </div><!-- /.page-content -->
    <#--下级管理员-->
        <div class="footer">
            <div class="footer-inner">
                <div class="footer-content">
			<span class="bigger-120">
				<span class="blue bolder">智慧教育云服务平台</span>
				版权所有 | 后台管理
			</span>

                    &nbsp; &nbsp;
                    <span class="action-buttons">
				<a href="#">
					<i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
				</a>

				<a href="#">
					<i class="ace-icon fa fa-facebook-square text-primary bigger-150"></i>
				</a>

				<a href="#">
					<i class="ace-icon fa fa-rss-square orange bigger-150"></i>
				</a>
			</span>
                </div>
            </div>
        </div>

        <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
            <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
        </a>
    </@adminLayout>

<#--</@shiro.hasRole>-->
<#--</@shiro.lacksRole>-->

<!--[if !IE]> -->
<script src="/js/jquery.min.js"></script>

<!-- <![endif]-->

<!--[if IE]>
<script src="/js/jquery-1.11.3.min.js"></script>
<![endif]-->
<script type="text/javascript">
    if ('ontouchstart' in document.documentElement) document.write("<script src='js/ace/js/jquery.mobile.custom.min.js'>" + "<" + "/script>");
</script>
<script src="/js/bootstrap.min.js"></script>

<!-- page specific plugin scripts -->

<!--[if lte IE 8]>
<script src="/js/excanvas.min.js"></script>
<![endif]-->

<!-- ace scripts -->
<script type="text/javascript" src="/js/ace/js/ace-elements.min.js"></script>
<script type="text/javascript" src="/js/ace/js/ace.min.js"></script>
<script type="text/javascript" src="/js/plugins/bootstrap-treetable/bootstrap-treetable.js"></script>
<!-- bootstrap-table 表格插件 -->
<script src="/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="/js/plugins/jquery-ztree/3.5/js/jquery.ztree.all-3.5.js"></script>
<script src="/js/plugins/bootstrap-table/extensions/mobile/bootstrap-table-mobile.min.js"></script>
<script src="/js/plugins/bootstrap-table/extensions/toolbar/bootstrap-table-toolbar.min.js"></script>

<script type="text/javascript" src="/js/plugins/layer/layer.js"></script>
<script type="text/javascript" src="/js/plugins/layui/layui.js"></script>
<script type="text/javascript" src="/js/plugins/blockUI/jquery.blockUI.min.js"></script>
<script type="text/javascript" src="/js/plugins/iCheck/icheck.min.js"></script>
<script type="text/javascript" src="/js/plugins/jquery-validation/jquery.validate.min.js"></script>
<script type="text/javascript" src="/js/plugins/jquery-validation/messages_zh.min.js"></script>
<script type="text/javascript" src="/js/plugins/jquery-validation/jquery.validate.extend.js"></script>
<script type="text/javascript" src="/js/plugins/select/select2.js"></script>
<script type="text/javascript" src="/js/plugins/bootstrap-fileinput/js/fileinput.js"></script>
<script type="text/javascript" src="/js/plugins/bootstrap-fileinput/js/locales/zh.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" src="/js/admin/index.js"></script>
<script src="/js/page.js"></script>
<script type="text/javascript">

    function initPagerEvent() {
        console.log("")
        //属性 用于加载分页数据
        let contentItem = [{
            key: 'gdid' ,
            replace: 'redirect:/sdsadmin/depart/{%}?token='+localStorage.getItem("token"),
            attr: 'href'
        }, {
            key: 'gradess.name'}
            ,{
                key: 'departments.name'
            }];

        let pagerOptions = {
            loadURL: '/sdsadmin/depart/list?token='+localStorage.getItem("token"),
            item: contentItem
        };
        let page = $.fn.startPage(pagerOptions);

    }

    function SclickLoad(e) {
        let that = e.currentTarget;
        let url = $.common.trim(that.href);
        if (url !== '#' && url !== '' && url !== undefined) {
            $.common.loadPage('main-page-container', url);
        }
        e.preventDefault();
    }

    $(document).ready(function () {
        initPagerEvent();
    })

</script>
