<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" name="viewport" content="width=device-width,user-scalable=no">
    <title>课程内容</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/js/plugins/pagination/style/pagination.css" media="screen">
    <link rel="stylesheet" type="text/css" href="/js/plugins/pagination/style/normalize.css" media="screen">
    <link rel="stylesheet" href="/css/wxcourse-main.css">
    <link rel="stylesheet" type="text/css" href="/js/plugins/jquery.poshytip/tip-yellowsimple/tip-yellowsimple.css"/>
    <script type="text/javascript" src="/js/plugins/jquery.poshytip/jquery.poshytip.min.js"></script>
    <link rel="stylesheet" href="/css/recourse/video-js.css">
    <link href="/js/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet">

</head>
<body>
<div class="ov f-pr j-ch f-cb">
    <div class="courseMark djMark f-pa j-djMark"></div>
    <div class="courseMark sfMark f-pa j-sfMark"></div>
    <div class="g-sd1 left j-chimg"><img width="525px" height="250"
                                         src="/img/front/couser.jpg">
    </div>
</div>

<div class="g-mn1">
    <div class="left">
        <div class="f-cb">
            <div class="ctarea f-fl j-cht">
                <div class="u-coursetitle f-fl" id="auto-id-1555802291123" style="margin-left: 15px;">
                    <h2 class="f-thide"><span class="u-coursetitle_title" title="${course.name }">${course.name }</span></h2>
                </div>
            </div>

        </div>

        <div class="j-coursehead-left-info">
            <div class="learn-main-info" style="border-bottom: none;width: 530px;margin-left: 15px;">
                <label>课程简介：</label><p>${teachCourse.courseBriefIntroduction}</p>
                <label style="margin-top: 10px;">学分：</label>${course.credit}
                <label>学时：</label>${course.period}
            </div>
        </div>
    </div>
</div>


<div class="content-box" style="width:550px;">
    <div class="g-cimn2" id="bdDir">
        <div class="f-cb f-bg f-pr" id="j-chapter-list" style="z-index:10;">
            <div class="f-cb">
                <#--<h2 class="u-ctit f-thide f-fl" id="j-chapter-title">课程章节</h2>-->
                <a href="/wx/course/detail/${tcid}?openId=${openId}" class="u-ctit f-thide f-fl" id="j-chapter-title">课程章节</a>
                <a  href="/wx/course/paperWork/${tcid}?openId=${openId}" class="u-ctit f-thide f-fl" id="j-chapter-title">在线作业</a>
                <div class="f-fr u-muluchaptertime" id="muluchaptertime">
                </div>
            </div>
            <div class="m-chapterList f-pr">
                <#--  chapters -->
                <#list chapters as chapter>
                    <#if chapter.pId == 0>
                        <div class="chapter f-pr">
                        <div class="chapterhead">
                        <span class="f-fl f-thide chaptername">${chapter.name}</span>
                    <div class="f-fr j-chaptertime-0"></div>
                        </div>
                        <div class="sectionarea" style="width: 550px;">
                        <#list chapters as children>
                            <#if children.pId = chapter.id>
                                <div class="section" data-lesson="0" data-chapter="0" id="auto-id-1555802291086" style="background-position:-300px -241px">
                                <span class="f-fl f-thide ks" title="${children.title}"></span>
                            <span class="f-fl ksicon ksicon-30" title="进行中"></span>
                            <span class="f-fl f-thide ksname" title="${children.name}">${children.name}</span>
                                <span class="f-fr ksinfo j-hoverhide" style="display: block;width: 250px;">

                                <#list children.resource as cs>
                                    <#if cs.resourceType == 1>
                                        <a href="javascript:void(0);" title="${cs.name}" class="info-tip" style="padding-left:6px;padding-right:6px;"
                                    onclick="toVideo('${cs.attrId}','${cs.name}')">
                                        <span class="fa fa-file-movie-o" style="font-size: 18px;"></span>
                                        </a>
                                    <#elseif cs.ext == 'pdf'>
                                        <a href="javascript:void(0);"
                                        title="${cs.name}" class="info-tip"
                                                           style="padding-left:6px;padding-right:6px;"
                                    onclick="pdfView('${cs.recourse.attrId}')">
                                        <span class="fa fa-file-pdf-o"
                                              style="font-size: 18px;"></span>
                                        </a>
                                    <#elseif cs.ext == 'txt'>
                                        <a href="javascript:void(0);"
                                        title="${cs.name}" class="info-tip"
                                                           style="padding-left:6px;padding-right:6px;"
                                    onclick="toTest('${cs.rid}','${openId}')">
                                        <span class="fa fa-paper-plane" style="font-size: 18px;"></span>
                                        </a>
                                    <#else>
                                        <a href="javascript:void(0);"
                                        title="${cs.name}" class="info-tip" style="padding-left:6px;padding-right:6px;"
                                    onclick="downLoad('${cs.attrId}','${cs.rid}')">
                                        <span class="fa fa-file-archive-o" style="font-size: 18px;"></span>
                                        </a>
                                    </#if>
                                </#list>
                                </div>
                            </#if>
                        </#list>
                        </div>
                        </div>
                    </#if>
                </#list>
            </div>
        </div>
    </div>
</div>

<!--div class="content-box">
                <h4>课程资源</h4>
                <#-- 资源tab -->
                <div class="col-md-8 col-md-offset-2">
                    <ul class="nav nav-tabs">
                        <#--  type  -->
                        <#list types as type>
                            <li class="active" role="presentation" id="resources_${type.id}">
                                <a href="javascript:void(0);" data-toggle="tab" onclick="resourceTypeContent(${type.id})">${type.name}</a>
                            </li>
                        </#list>
                    </ul>
                    <div id="resourceTabContent" class="tab-content"></div>
                </div>
            </div-->



<!-- 视频在线播放模态框 -->
<div class="modal fade" id="shipinModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <#--<div class="modal-dialog">-->
    <#--<div id="playerbox" class="col-md-12" >-->
    <#--</div>-->
    <#--</div>-->
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <input class="modal-title" id="shipinLabel" value="" disabled="true" style="font-size:18px;border:none;">
                </input>
            </div>
            <div class="modal-body">
                <#--<div class="row">-->
                <form>
                    <#--<p class="subtitle">-->
                    <#--视频名称：<span id="vname">加载中...</span>-->
                    <#--</p>-->
                    <#--<p class="subtitle">-->
                    <#--<span id="vcdate">加载中...</span>/<span-->
                    <#--id="vsize">加载中...</span> MB-->
                    <#--</p>-->
                    <#--<br/>-->

                        <div id="playerbox" class="col-md-12" >
                            <p>加载中...</p>
                        </div>


                    <#--<div id="playerbox" class="col-md-12">-->
                    <#--<p>加载中...</p>-->
                    <#--</div>-->
                </form>

                <#--<video src="" id="videoyulan" controls="controls">-->
                <#--你的视频不支持播放-->
                <#--</video>-->
                <#--</div>-->
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>


<script>

    $('.info-tip').poshytip({
        className: 'tip-yellowsimple',
        showTimeout: 1,
        alignTo: 'target',
        alignX: 'center',
        offsetY: 5,
        allowTipHover: false
    });
</script>


</body>
<script type="text/javascript" src="/js/recourse/video.js"></script>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
<script>

    $().ready(function () { //$().ready页面加载好就执行


    });

    //  资源类型内容页
    function resourceTypeContent(typeid) {
        var content = $("#resourceTabContent");
        $.ajax({
            type: 'get',
            url: "/wx/course/resource/${teachCourse.id}?type=" + typeid,
            dataType: 'json',
            async: true,
            success: function(result) {
                if(1 === result.code) {
                    content.empty();
                    for (var i=0; i <result.data.length; i++) {
                        content.append('<li><div class="news-first" style="margin: 10px;"><img src="/img/front/res-num1.jpg" />'
                            + '<a style="text-align: center;">' + result.data[i].name + '</a></div></li>');
                    }
                } else {
                    alert("获取资源失败");
                }
            }
        })
    }

    var tmpFileId;
    //  视频播放
    function toVideo(fileId) {
        tmpFileId = fileId;
        $('#shipinModal').modal('show');

        return false;
    }

    $('#shipinModal').on('show.bs.modal', function (e) {
        loadVideo(tmpFileId);
    });
    $('#shipinModal').on('hidden.bs.modal', function (e) {
        var player = videojs('kiftplayer');
        console.log(player);
        player.dispose();
        player = null;
    });

    function loadVideo(fileId) {
        $.ajax({

            url:  'http://lxweixin.free.idcfengye.com/wx/course/playVideo/' + fileId,
            type: 'get',
            dataType: 'json',
            success: function (result) {
                if (result.code === 0) {
                    playVideo(result.data, result.pf);
                }else {
                    alert("错误：无法定位该文件。");
                }
            },
            error: function (msg) {
                alert(JSON.stringify(msg));
            }

        });
    }



    //显示视屏信息并播放视频
    function playVideo(f, pf) {
        $("#vname").text(f.fileName);
        $("#vcdate").text(f.fileCreationDate);
        $("#vsize").text(f.fileSize);
        $("#playerbox")
            .html(
                "<video id='kiftplayer' class='video-js col-md-12' controls preload='auto' height='200'>"
                + "<source src='" + pf + "/resource/getResource?fid=" + f.fileId + "' type='video/mp4'>"
                + "<source src='" + pf + "/resource/getResource?fid=" + f.fileId + "' type='video/webm'>"
                + "</video>");
        var player = videojs('kiftplayer');
        player.ready(function () {
            this.play();
            this.currentTime(53.1225);
            getvideoprogress();
        });
    }
    function getvideoprogress() {
        setTimeout(function () {
            var player = videojs('kiftplayer');
            var currentTime=player.currentTime();
            console.log(currentTime);
            getvideoprogress();
        }, 1000);
    }

    function downLoad(fileId, rid) {
        $.ajax({
            url: 'http://lxweixin.free.idcfengye.com/wx/course/downloadFile/' + rid,
            type: 'get',
            dataType: 'json',
            success: function (result) {
                if (result.code === 0) {
                    window.location.href =result.pf+"/downloadFile?fileId=" + fileId;
                } else {
                    alert("错误：无法下载该文件。");
                }
            },
            error: function () {
                alert("错误：请求失败，请刷新重试。");
            }
        });
    }


    function toTest(testPaperId,openId) {
        window.open('/wx/course/wxchapterTest/'+testPaperId+'?openId='+openId, '_blank');

    }




</script>


</html>