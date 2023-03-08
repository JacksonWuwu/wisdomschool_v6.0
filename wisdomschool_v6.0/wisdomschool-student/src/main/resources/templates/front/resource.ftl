<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>资源下载专区</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="static/plugins/home/img/favicon.ico" type="image/x-icon"/>
    <!--地址栏和标签上显示图标-->
    <!--bootstrap引用-->
    <link rel="shortcut icon" href="/img/front/favicon.ico" type="image/x-icon">
    <link href="/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="/css/indexstyle.css" type="text/css" rel="stylesheet">
    <script src="/js/jquery-2.1.4.min.js" type="text/javascript"></script>
    <script src="/js/bootstrap.min.js" type="text/javascript"></script>

    <link rel="stylesheet" href="/css/style-home.css"/>
<#include '/front/inc/commonJs.ftl'/>
</head>
<body>
<!--页头-->
<div class="row">
        <#include '/front/inc/header.ftl'/>
</div>
<!--主体部分-->
<div class="row">

    <div class="res-mainbody">
        <!--搜索栏部分-->
        <nav class="res-sousuo">
            <div class="sousuodaohang">
                <div class="allres"><a href="/user/resource/0/${tcid}?pageSize=${list.total}">全部</a></div>
                <#list types as t>
                    <#if (t_index > 4)>
                        <#break>
                    </#if>
                    <li class="shipinku"><a href="/user/resource/${t.id}/${tcid}?pageSize=25">${t.name}</a></li>
                </#list>
                <div class="sousuobiaodan"><!--搜索表单-->
                    <form style="float:left;width:240px;padding:8px;">

                        <input type="text" style="float:left;width:160px;line-height:200%;margin-right:10px;padding:3px;"
                               placeholder="请输入关键字" name="ziyuansousuo" id="ziyuansousuo">

                        <button type="button" style="float:left;border:none;width:50px;height:36px;border-radius:5px;"
                                id="gotoSelect">搜索
                        </button>
                    </form>  <!--搜索表单结束-->
                </div>
            </div>
        </nav>


        <!--显示部分-->
        <div class="res-xianshimain">

            <!--侧边栏部分-->
            <#--        <div class="res-sibar">-->
            <#--            <div class="res-sibar-headr">-->
            <#--                最新资源-->
            <#--            </div>-->
            <#--            <div class="res-sibar-body">-->
            <#--                <ul id="zuixinziyuaMenu">-->
            <#--                </ul>-->

            <#--            </div>-->
            <#--        </div>-->
            <div class="res-center">
                <div class="res-header">
        		<span>当前位置：资源  >
        		<a id="all-ziyuan">
					${type.name!"全部"}
        		</a>
        		</span>
                    <div class="res-more">共搜索到 ${list.total} 个</div>
                </div>
                <div class="res-body" id="ziyuaMenu">
                    <div style=" overflow:scroll; height:600px;">
                        <!--每条资源-->
                        <#list list.rows as row>
                            <div class="res-body-title">
                            <div class="res-title-img">
                                <img src="/img/front/res.jpg"/>
                            </div>
                            <div class="res-title">
                            <a href="#" yuname="${row.name }" url="${row.id }"
                        onclick="readFile(this)"> ${row.name }</a><br/>
                                <!--href="/modal/ziyuan/documentView.jsp?filePath=${row.id }"-->
                                <!--small>上传者：${row.createBy} </small-->
                            <small>上传时间：${row.createTime?string('yyyy-MM-dd HH:mm:ss')}</small>
                            <small>格式：${row.ext }</small>
                            <small>下载次数：${row.count }</small>
                            </div>
                            <div class="res-title-download">
                            <#if row.ext == 'pdf'>
                                <a href="#" url="${fylist[4] }" name="${fylist[0] }" onclick="pdfView('${row.attrId}')"
                            yunid="${fylist[5] }">在线浏览</a>
                            </#if>
                        <a href="#" url="${fylist[4] }" name="${fylist[0] }" onclick="downLoad('${row.attrId}')"
                        yunid="${fylist[5] }">点击下载</a>

                            </div>
                            </div>
                        </#list>


                    </div>

                </div>
            </div>
        </div>
        <!--页脚-->
        <div class="footer">
            <center>
                <br/>
                智慧教育云平台版权所有 | 后台管理
            </center>

        </div>

    </div><!--主体部分结束-->
</div>

<!-- 视频在线播放模态框 -->
<div class="modal fade" id="shipinModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width:850px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <input class="modal-title" id="shipinLabel" value="" disabled="true"
                       style="font-size:18px;border:none;width:790px;">

                </input>
            </div>
            <div class="modal-body">
                <form>


                    <video src="" id="videoyulan" width="800" height="580" controls="controls">
                        你的视频不支持播放
                    </video>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>


<script type="text/javascript">

    $().ready(function () { //$().ready页面加载好就执行
        //  makeZiyuanList();
        makeZuiZiyuanList();//最新资源推荐

    });


    //侧边栏最新资源
    function makeZuiZiyuanList() {

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
                $("#zuixinziyuaMenu>div").remove();
                $("#zuixinziyuaMenu>h4").remove();
                if (data.code != 0) {
                    $("#zuixinziyuaMenu").append("<h4>缺少资源！</h4>");
                } else {
                    $("#zuixinziyuaMenu").html("");
                    let json = data.rows;
                    let menu = '';
                    for (let i = 0; i < json.length; i++) {
                        menu += "<li><div class=\"news-first\"><img src=\"/img/front/res.jpg\" />"
                            + "  <a><small>" + json[i].name + "</small></a></div></li>";
                    }
                    $("#zuixinziyuaMenu").append(menu);

                }
                ;
            }
        });
    };


    //读取总页码or列出全部资源
    function yematongji(dangqianyema) {
        window.location.href = "/courseUP/CourseUploadAction!fileyema.action?DangQianYeMa=" + dangqianyema;

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

    //读取开发工具的信息
    function kaifagongju() {

        window.location.href = "/tool/DeveToolMangeAction!showHuangjinQiantai.action";

    }

    //读取总页码or列出全部资源
    function newstongji(dangqianyema) {

        window.location.href = "/news/newsMangeAction!newsyema.action?DangQianYeMa=" + dangqianyema;

    }

    //课程学习
    function coursefg() {
        window.location.href = "/learn/courseLearnMangeAction!toCourseLearnList.action";

    }

    //读取总页码or列出全部问题
    function talktongji(dangqianyema) {

        window.location.href = "/talk/talkMangeAction!talkyema.action?DangQianYeMa=" + dangqianyema;


    }

    //读取学习指导的信息
    function xuexizhidao() {

        window.location.href = "/tool/DeveToolMangeAction!showDaoxueQiantai.action";

    }


    //点击搜索
    $("#gotoSelect").click(function () {
        var valuekey = $("#ziyuansousuo").val();

        if (document.all("ziyuansousuo").value == "") {
            alert("输入不能为空！");
            document.all("ziyuansousuo").focus();//使光标定位到这个元素
            return false;
        } else {
            window.location.href = "/user/resource/0/${tcid}?pageSize=8&search="+valuekey+"&token="+localStorage.getItem("token");
        }

    });

    //判断是否是视频
    function readFile(objc) {
        var name = $(objc).attr("yuname");

        var url = $(objc).attr("url");

        var FileListType = "doc,docx";
        var destStr = name.substring(name.lastIndexOf(".") + 1, name.length);
        var path = url.split("ROOT")[1];
        path = "http://" + window.location.host + path;

        if ("pdf".indexOf(destStr) != -1) {
            window.open("http://" + window.location.host + "/resources/pdfjs/viewer.html?file=" + path);
            return false;
        }

        if (FileListType.indexOf(destStr) == -1) {
            document.getElementById("videoyulan").src = path;
            document.getElementById("shipinLabel").value = name;
            $('#shipinModal').modal('show');
            return false;
        }


        window.open("http://www.xdocin.com/xdoc?_format=pdf&_xdoc=" + path);
    };


    //初始化搜索结果
    function makeZiyuanList() {

        $.ajax({
            type: "post",
            url: "/courseUP/CourseUploadAction!findSearchZiyuan.action?time=" + new Date(),
            data: {
                "ziyuansousuo": $("#ziyuansousuo").val(),
            },
            cache: false,
            async: false,  //同步处理
            success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success
                if (data == "error") {
                    $("#ziyuaMenu>div").remove();
                    $("#ziyuaMenu>h4").remove();
                    $("#ziyuaMenu").append("<h4>缺少资源！</h4>");
                } else {
                    var json = JSON.parse(data);

                    $("#ziyuaMenu>div").remove();
                    $("#ziyuaMenu>h4").remove();
                    for (var i = 0; i < json.length; i++) {
                        $("#ziyuaMenu").append(
                            "<div class=\"res-body-title\">"
                            + "<div class=\"res-title-img\">"
                            + "<img src=\"/static/plugins/home/img/res.jpg\" />"
                            + "</div>"
                            + "<div class=\"res-title\"><a href=\"#\">" + json[i].courseWareName + "</a><br/><small>上传者：" + json[i].createPerson + "</small>  <small>上传时间：" + json[i].uploadTime + "</small>    <small>大小：" + json[i].courseWareSize + "</small></div>"
                            + " <div class=\"res-title-download\">"
                            + " <a href=\"#\" url=" + json[i].fileRolad + "  name=" + json[i].courseWareName + " onclick=\"downLoad(this)\">点击下载</a></div>"
                            + " </div>"
                        );
                    }
                    ;
                }
                ;
            }
        });

    };


    //下载
    function oneDownload(obj) {

        var url = encodeURIComponent(encodeURIComponent($(obj).attr("url")));

        var name = $(obj).attr("name");
        var yunid = $(obj).attr("yunid");
        window.location.href = "/courseUP/CourseUploadAction!fileDownload.action?courseware.fileRolad="
            + url + "&courseware.courseWareName=" + name + "&courseware.courseWareId=" + yunid;
    }

    function pdfView(filePath) {
        window.open("/recourse/fileresource/review?file=${storage}/resource/handle/" + filePath);
    }

    function downLoad(fileId) {
        window.location.href = "/resource/download/" + fileId;
    }


</script>


</body>
</html>

