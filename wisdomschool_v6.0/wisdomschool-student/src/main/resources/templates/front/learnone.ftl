<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>考试任务-考试</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--地址栏和标签上显示图标-->
    <!--bootstrap引用-->
    <link rel="shortcut icon"
          href="/img/front/favicon.ico"
          type="image/x-icon">
    <link href="/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="/css/indexstyle.css" type="text/css" rel="stylesheet">
    <script src="/js/jquery-2.1.4.min.js" type="text/javascript"></script>
    <script src="/js/bootstrap.min.js" type="text/javascript"></script>

    <link rel="stylesheet" href="/css/style-home.css"/>
    <link rel="stylesheet" href="/css/course.css"/>

    <style>
        .chapter a {
            color: #5a5c5f;
        }

        ul.chapter-sub.chapter-resource li {
            color: #0089D2;
            font-size: 16px;
        }
        table.imagetable {
            font-family: verdana,arial,sans-serif;
            font-size:18px;
            color:#333333;
            width: 100%;
            border-width: 1px;
            border-color: #999999;
            border-collapse: collapse;
        }
        table.imagetable th {
            background:#b5cfd2 url('cell-blue.jpg');
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #999999;
        }
        table.imagetable td {
            background:#dcddc0 url('cell-grey.jpg');
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #999999;
        }
    </style>

</head>
<body>
<!--页头-->
<#include '/front/inc/commonJs.ftl'/>
<#include '/front/inc/header.ftl'/>
<!--课程面板-->
<#--<div class="learn-mianban">
        <div class="learn-mianban-daohang">当前位置：课程学习</div>
        <div class="learn-title"><strong>${course.name }</strong></div>
        <div class="learn-info">
            <div class="learn-people">点击数：2468人次</div>
            &lt;#&ndash;<div class="learn-nandu">难度：初级</div>&ndash;&gt;
        </div>
</div>-->
<!--课程主要内容面板-->
<div class="learn-main">
    <#if selected == "forum">
        <#include '/front/forum.ftl'/>
    <#elseif selected == "editor">
        <#include '/front/editor.ftl'/>
    <#elseif selected == "detail">
        <#include '/front/detail.ftl'/>
    <#else >
        <#include '/front/courseOne.ftl'/>
    </#if>

    <#--<div class="learn-main-zhuti">
        <!--课程简介&ndash;&gt;
        <div class="learn-main-info">
            <p>简介：${teacherCourse.courseBriefIntroduction }</p>
        </div>
        <!--课程章节&ndash;&gt;
        <div class="learn-ztreelist">
            <!-- 课程章节 - start &ndash;&gt;
            <div>
                <#list chapter as c>
                    <div class="chapter">
                        <a href="javascript:void(0);" class="js-open">
                            <h3>
                                <strong>
                                    <div class="icon-chapter">=</div> ${c.title} ${c.name}</strong>
                            </h3>
                        </a>
                        <ul class="chapter-sub">
                            <#if c.children??>
                                <#list c.children as child>
                                    <a href="javascript:void(0);" class="js-open">
                                        <li class="chapter-sub-li">
                                            <span class="drop-down">▼</span> ${child.title} ${child.name}
                                        </li>
                                    </a>

                                    <ul class="chapter-sub chapter-resource">
                                        <@chapterResource courseId=child.cid chapterId=child.id>
                                            <#list chapterresources as cs>
                                                <#if cs.resourceType == 1>
                                                    <a href="javascript:void(0);"
                                                       onclick="toVideo('${cs.recourse.attrId}')">
                                                        <li class="">
                                                            <i class="fa fa-film"></i> ${cs.name}
                                                        </li>
                                                    </a>
                                                <#elseif cs.ext == 'pdf'>
                                                    <a href="javascript:void(0);"
                                                       onclick="pdfView('${cs.recourse.attrId}')">
                                                        <li class="">
                                                            <i class="fa fa-file-pdf-o"></i> ${cs.name}
                                                        </li>
                                                    </a>
                                                <#elseif cs.ext == 'txt'>
                                                    <a href="javascript:void(0);"
                                                       onclick="toTest('${cs.rid}')">
                                                        <li class="">
                                                            <i class="fa fa-paper-plane"></i> ${cs.name}
                                                        </li>
                                                    </a>
                                                <#else>
                                                    <a href="javascript:void(0);"
                                                       onclick="downLoad('${cs.recourse.attrId}')">
                                                        <li class="">
                                                            <i class="fa fa-file-archive-o"></i> ${cs.name}
                                                        </li>
                                                    </a>
                                                </#if>
                                            </#list>
                                        </@chapterResource>
                                    </ul>
                                </#list>
                            </#if>
                        </ul>
                    </div>
                </#list>

            </div>
            <!-- 课程章节 - end &ndash;&gt;
        </div>

    </div>-->

    <!--页脚-->
    <div class="footer">
        <center>
            <br/>
            智慧教育云平台版权所有 | 后台管理
        </center>

    </div>
</div>

<!-- 视频在线播放模态框 -->
<#--<div class="modal fade" id="shipinModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"-->
<#--     aria-hidden="true">-->
<#--    <div class="modal-dialog" style="width:850px;">-->
<#--        <div class="modal-content">-->
<#--            <div class="modal-body">-->
<#--                <div class="row">-->
<#--                    <div class="col-md-12">-->
<#--                        <div class="titlebox">-->
<#--                            <button type="button" class="btn btn-link rightbtn" data-dismiss="modal"-->
<#--                                    aria-hidden="true">-->
<#--                                关闭 <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>-->
<#--                            </button>-->
<#--                        </div>-->
<#--                        <hr/>-->
<#--                    </div>-->
<#--                </div>-->

<#--                <div class="row">-->
<#--                    <div class="col-md-12">-->
<#--                        <p class="subtitle">-->
<#--                            视频名称：<span id="vname">加载中...</span>-->
<#--                        </p>-->
<#--                        <p class="subtitle">-->
<#--                            <span id="vcdate">加载中...</span>/<span-->
<#--                                    id="vsize">加载中...</span> MB-->
<#--                        </p>-->
<#--                        <br/>-->
<#--                        <!-- 播放窗口组件位置 &ndash;&gt;-->
<#--                        <div id="playerbox" class="col-md-12">-->
<#--                            <p>加载中...</p>-->
<#--                        </div>-->
<#--                    </div>-->
<#--                </div>-->
<#--            </div>-->
<#--        </div><!-- /.modal-content &ndash;&gt;-->
<#--    </div><!-- /.modal &ndash;&gt;-->
<#--</div>-->
<script type="text/javascript">

    $().ready(function () { //$().ready页面加载好就执行


    });
    let tmpFileId;

    <#--function pdfView(filePath) {-->
    <#--    window.open("${storage}/recourse/fileresource/review?file=${storage}/resource/handle/" + filePath);-->
    <#--}-->

    <#--function pdfView(filePath) {-->
    <#--    $.ajax({-->
    <#--        url: '/recourse/downloadFile/' + filePath,-->
    <#--        type: 'get',-->
    <#--        dataType: 'json',-->
    <#--        success: function (result) {-->
    <#--            if (result.code === 0) {-->
    <#--                window.open("${storage}/recourse/fileresource/review?file=${storage}/resource/handle/" + result.fid);-->
    <#--            } else {-->
    <#--                alert("错误：无法下载该文件。");-->
    <#--            }-->
    <#--        },-->
    <#--        error: function () {-->
    <#--            alert("错误：请求失败，请刷新重试。");-->
    <#--        }-->
    <#--    });-->
    <#--}-->

    <#--function downLoad(fileId, rid) {-->
    <#--    $.ajax({-->
    <#--        url: '/recourse/downloadFile/' + rid,-->
    <#--        type: 'get',-->
    <#--        dataType: 'json',-->
    <#--        success: function (result) {-->
    <#--            if (result.code === 0) {-->
    <#--                window.location.href = "${storage}/downloadFile?fileId=" + fileId;-->
    <#--            } else {-->
    <#--                alert("错误：无法下载该文件。");-->
    <#--            }-->
    <#--        },-->
    <#--        error: function () {-->
    <#--            alert("错误：请求失败，请刷新重试。");-->
    <#--        }-->
    <#--    });-->
    <#--}-->

    function toExam(testPaperId) {
        var token=localStorage.getItem("token")
        window.open('/user/chapterExam?testPaperOneId=' + testPaperId+"&&token="+token, '_blank');

    }

    // function toVideo(fileId) {
    //     tmpFileId = fileId;
    //     $('#shipinModal').modal('show');
    //
    //     return false;
    // }

    // $('#shipinModal').on('show.bs.modal', function (e) {
    //     loadVideo(tmpFileId);
    // });
    // $('#shipinModal').on('hidden.bs.modal', function (e) {
    //     let player = videojs('kiftplayer');
    //     console.log(player);
    //     player.dispose();
    //     player = null;
    // });

    <#--function loadVideo(fileId) {-->
    <#--    $.ajax({-->
    <#--        url: '/recourse/playVideo/' + fileId,-->
    <#--        type: 'get',-->
    <#--        dataType: 'json',-->
    <#--        success: function (result) {-->
    <#--            if (result.code === 0) {-->
    <#--                playVideo(result.data, result.pf);-->

    <#--            } else {-->
    <#--                alert("错误：无法定位该文件。");-->
    <#--            }-->
    <#--        },-->
    <#--        error: function () {-->
    <#--            alert("错误：请求失败，请刷新重试。");-->
    <#--        }-->
    <#--    });-->

    <#--}-->


    // //显示视屏信息并播放视频
    // function playVideo(f, pf) {
    //     $("#vname").text(f.fileName);
    //     $("#vcdate").text(f.fileCreationDate);
    //     $("#vsize").text(f.fileSize);
    //     $("#playerbox")
    //         .html(
    //             "<video id='kiftplayer' class='video-js col-md-12' controls preload='auto' height='500'>"
    //             + "<source src='" + pf + "/resource/getResource?fid=" + f.fileId + "' type='video/mp4'>"
    //             + "<source src='" + pf + "/resource/getResource?fid=" + f.fileId + "' type='video/webm'>"
    //             + "</video>");
    //     let player = videojs('kiftplayer');
    //     player.ready(function () {
    //         this.play();
    //     });
    // }
</script>
</body>
</html>
