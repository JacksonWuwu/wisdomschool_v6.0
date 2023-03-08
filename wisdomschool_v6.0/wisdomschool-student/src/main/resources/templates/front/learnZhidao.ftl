<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>学习指导</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon"
          href="/img/front/favicon.ico"
          type="image/x-icon">
    <!--地址栏和标签上显示图标-->
    <!--bootstrap引用-->
    <link href="/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="/css/indexstyle.css" type="text/css" rel="stylesheet">
    <script src="/js/jquery-2.1.4.min.js" type="text/javascript"></script>
    <script src="/js/bootstrap.min.js" type="text/javascript"></script>

    <link rel="stylesheet" href="/css/style-home.css"/>
    <style>
        .cur {
            background: #36e04b;
            border-right: 4px solid white;
        }
    </style>
</head>
<body>
<!--页头-->
<#include '/front/inc/commonJs.ftl'/>
<#include '/front/inc/header.ftl'/>
<!--主体-->
<div class="serch-main-zd">

    <!--大图-->

    <img src="/img/front/banner_tea1.jpg" style="width: 100%"/>

    <!--中间模块-->
    <div class="serch-body">
        <div class="serch-sidbar"><!--侧边栏-->
            <div class="serch-sidbar-header">
                <ul>
                    <li <#if type=='lead'>class="cur"</#if>>
                        <center><a <#if type=='lead'>href="javascript:void(0);"<#else > href="/user/guide/lead"</#if>
                                   onclick="xuexizhidao(1);">开篇导学</a></center>
                    </li>

                    <li <#if type=='outline'>class="cur"</#if>>
                        <center>
                            <a <#if type=='outline'>href="javascript:void(0);"<#else > href="/user/guide/outline"</#if>
                               onclick="jiaoxuedagang(1);">教学大纲</a></center>
                    </li>

                    <li <#if type=='outpeizhi'>class="cur"</#if>>
                        <center>
                            <a <#if type=='outpeizhi'>href="javascript:void(0);"<#else > href="/user/guide/outpeizhi"</#if>
                               onclick="huanjingpeizhi(1);">环境配置</a></center>
                    </li>
                    <#--<li><center><a href="#" onclick="ruanjianxiazai(1);">学习环境配置</a></center></li>-->
                </ul>
            </div>
            <div class="serch-sidbar-more"></div>
        </div>
        <div class="serch-xianshi">
            <div class="serch-xianshi-head">
                <span>当前位置：学习指导><#if type=='lead'>开篇导学<#elseif type == 'outline'>教学大纲<#elseif type == 'outpeizhi'>环境配置</#if></span>
            </div>
            <div class="serch-zhengwen">
                <#--<c:forEach var="tooldaoxue" items="${tooldaList}" varStatus="status">-->
                <div>
                    ${guide.content}
                </div>


                <#--</c:forEach>-->
            </div>
        </div>

    </div>
    <div class="footer">
        <center>
            <br/>
            智慧教育云平台版权所有 | 后台管理
        </center>

    </div>
</div>
<script type="text/javascript">

    $().ready(function () { //$().ready页面加载好就执行
    });


    //读取开发工具的信息
    function kaifagongju() {

        window.location.href = "tool/DeveToolMangeAction!showHuangjinQiantai.action";

    }

    //读取数据库配置文章的信息
    function shujukupeizhi() {

        window.location.href = "tool/DeveToolMangeAction!showDateBaseQiantai.action";

    }

    //读取教学大纲的信息
    function jiaoxuedagang() {

        window.location.href = "tool/DeveToolMangeAction!showDagangQiantai.action";

    }

    function xuexizhidao() {

        window.location.href = "tool/DeveToolMangeAction!showDaoxueQiantai.action";

    }

    //读取教学大纲的信息
    function huanjingpeizhi() {

        window.location.href = "tool/DeveToolMangeAction!showDagangQiantai.action";

    }
    //读取开发软件下载文章的信息
    function ruanjianxiazai() {

        window.location.href = "tool/DeveToolMangeAction!showSoftwareQiantai.action";

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



</script>
</body>
</html>
