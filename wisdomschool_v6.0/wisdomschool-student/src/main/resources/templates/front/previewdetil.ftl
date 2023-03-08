<link rel="stylesheet" href="/css/course-main.css">
<link href="/js/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/js/plugins/jquery.poshytip/tip-yellowsimple/tip-yellowsimple.css"/>
<script type="text/javascript" src="/js/plugins/jquery.poshytip/jquery.poshytip.min.js"></script>
<#include '/front/inc/commonJs.ftl'/>
<div id="auto-id-1555802290742" <#--style="padding-top: 60px;"--> class="auto-1555802290734-parent"
     cz-shortcut-listen="true">
    <div class="g-headwrap" id="j-fixed-head" data-log-id="topNav"
         data-log-data="{&quot;pageName&quot;:&quot;顶部导航&quot;}">
        <div class="m-yktnav " id="j-topnav">
            <div class="m-yktnav_wrap f-pr f-cb">
                <div class="m-breadcrumbBox f-cb" id="j-breadcrumbBox">
                    <div class="g-mn2">
                        <div class="g-flow f-cb">
                            <ul class="g-flow">
                                <li class="navcrumb-item">
                                    <a href="">我的课程</a>
                                </li>
                                <li class="navcrumb-item current" id="navcrumbId-2">
                                    <span class="arrow"></span>
                                    <a href="javascript: void(0);">${course.name }</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="g-flow">
                    <div class="b-20"></div>
                    <div>
                        <div class="f-bg headBox" id="courseHead">
                            <div class="u-courseHead" id="auto-id-1555802291070">
                                <#include '/front/courseinfo.ftl'/>
                                <div class="j-courseheadTab">
                                    <ul class="f-cb tabarea">
                                        <li class="f-fl"><a class="j-chtab selected" hidefocus="true">主页</a></li>
                                        <!--Regular if107-->
                                        <#--                                        <li class="f-fl"><a class="j-chtab" hidefocus="true"-->
                                        <#--                                                            href="/user/courseTopicList/${tcid}">讨论区</a></li>-->
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="f-cb">
                        <div class="g-cisd2" id="bdCom">
                            <div id="j-repo-box" class="f-dn f-bg repoBox f-cb">
                                <h2 class="u-ctit">相关题库</h2>
                                <div id="j-repoList" class="repoList f-bdr">
                                </div>
                            </div>
                            <div class="b-15"></div>
                            <div class="b-15"></div>
                            <div id="j-courseusers" class="usersBd">
                            </div>
                        </div>
                        <div class="g-cimn2" id="bdDir">
                            <div class="g-cimn2c">
                                <div id="j-livecourse-remind">
                                </div>
                                <div class="b-15"></div>
                                <div class="f-cb f-bg f-pr" id="j-chapter-list" style="z-index:10;">
                                    <div class="f-cb">
                                        <h2 class="u-ctit f-thide f-fl" id="j-chapter-title" style="width:320px;">
                                            目录</h2>
                                        <div class="f-fr u-muluchaptertime" id="muluchaptertime">
                                        </div>
                                    </div>
                                    <div class="m-chapterList f-pr" id="auto-id-1555802291084">
                                        <#list chapter as c>
                                            <div class="chapter f-pr">
                                                <div class="chapterhead"><span
                                                            class="f-fl chaptertitle">${c.title}</span><span
                                                            class="f-fl chaptericon">${c_index + 1}</span><span
                                                            class="f-fl f-thide chaptername">${c.name}</span>
                                                    <div class="f-fr j-chaptertime-0"></div>
                                                </div>
                                                <div class="sectionarea">
                                                    <#if c.children??>
                                                        <#list c.children as child>
                                                            <div class="section" data-lesson="0" data-chapter="0"
                                                                 id="auto-id-1555802291086">
                                                                <span class="f-fl f-thide ks"
                                                                      title="${child.title}">${child.title}</span>
                                                                <span class="f-fl ksicon ksicon-30" title="进行中"></span>
                                                                <span class="f-fl f-thide ksname"
                                                                      title="${child.name}">${child.name}</span>
                                                                <span class="f-fr ksinfo j-hoverhide"
                                                                      style="display: block;width: 200px;">
                                                            <@chapterResource courseId=child.cid chapterId=child.id>
                                                                <#list chapterresources as cs>
                                                                    <#if cs.resourceType == 1>
                                                                        <a href="javascript:void(0);"
                                                                           title="${cs.name}" class="info-tip"
                                                                           style="padding-left:6px;padding-right:6px;"
                                                                           onclick="toVideo('${cs.recourse.attrId}','${cs.id}')">
                                                                            <span class="fa fa-file-movie-o"
                                                                                  style="font-size: 18px;"></span>
                                                                        </a>
                                                                    <#elseif cs.resourceType == 3>
                                                                        <a href="javascript:void(0);"
                                                                        title="${cs.name}" class="info-tip"
                                                                                           style="padding-left:6px;padding-right:6px;"
                                                                    onclick="toTest('${cs.rid}')">
                                                                        <span class="fa fa-paper-plane"
                                                                              style="font-size: 18px;"></span>
                                                                        </a>
<#--
                                                                    <#elseif cs.resourceType == 3>
                                                                        <a href="javascript:void(0);"
                                                                           title="${cs.name}" class="info-tip"
                                                                           style="padding-left:6px;padding-right:6px;"
                                                                           onclick="toTest('${cs.rid}')">
                                                                            <span class="fa fa-paper-plane"
                                                                                  style="font-size: 18px;"></span>
                                                                        </a>
                                                                    <#elseif cs.resourceType==4>
                                                                        </a>-->
                                                                    <#else>
                                                                        <a href="javascript:void(0);"
                                                                           title="${cs.name}" class="info-tip"
                                                                           style="padding-left:6px;padding-right:6px;"
                                                                           onclick="downLoad('${cs.recourse.attrId}','${cs.rid}')">
                                                                            <span class="fa fa-file-archive-o"
                                                                                  style="font-size: 18px;"></span>
                                                                        </a>
                                                                    </#if>
                                                                </#list>
                                                            </@chapterResource>
                                                            <span class="f-fr flag flag-10" title=""></span></span>
                                                            </div>
                                                        </#list>
                                                    </#if>
                                                </div>
                                            </div>
                                        </#list>
                                    </div>
                                </div>
                                <div id="j-recommend" style="display:none;">
                                    <div class="b-15"></div>
                                    <div class="f-cb f-bg f-pr recommedCourse" id="j-recommendCourse">
                                        <h2 class="u-ctit f-thide recommend">学习过该课程的人还学习过：</h2>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 视频在线播放模态框 -->
<div class="modal fade" id="shipinModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" style="width:850px;">
        <div class="modal-content">
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <div class="titlebox">
                            <button type="button" class="btn btn-link rightbtn" data-dismiss="modal"
                                    aria-hidden="true">
                                关闭 <span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>
                            </button>
                        </div>
                        <hr/>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <p class="subtitle">
                            视频名称：<span id="vname">加载中...</span>
                        </p>
                        <p class="subtitle">
                            <span id="vcdate">加载中...</span>/<span
                                    id="vsize">加载中...</span> MB
                        </p>
                        <br/>
                        <!-- 播放窗口组件位置 -->
                        <div id="playerbox" class="col-md-12">
                            <p>加载中...</p>
                        </div>
                    </div>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<script type="text/javascript" src="/js/recourse/video.js"></script>
<script type="text/javascript">

    $().ready(function () { //$().ready页面加载好就执行


    });

    let tmpFileId;
    var rcid;
    let time;
    var myStu;
    var dataname;
    function pdfView(filePath) {
        window.open("${storage}/recourse/fileresource/review?file=${storage}/resource/handle/" + filePath);
    }

    function pdfView(filePath) {
        $.ajax({
            url: '/recourse/downloadFile/' + filePath,
            type: 'get',
            dataType: 'json',
            success: function (result) {
                if (result.code === 0) {
                    window.open("${storage}/recourse/fileresource/review?file=${storage}/resource/handle/" + result.fid);
                } else {
                    alert("错误：无法下载该文件。");
                }
            },
            error: function () {
                alert("错误：请求失败，请刷新重试。");
            }
        });
    }

    function downLoad(fileId, rid) {
        $.ajax({
            url: '/recourse/downloadFile/' + rid,
            type: 'get',
            dataType: 'json',
            success: function (result) {
                if (result.code === 0) {
                    window.location.href = "${storage}/downloadFile?fileId=" + fileId;
                } else {
                    alert("错误：无法下载该文件。");
                }
            },
            error: function () {
                alert("错误：请求失败，请刷新重试。");
            }
        });
    }

    function toTest(testPaperId) {
        window.open('/user/chapterTest?testPaperId=' + testPaperId, '_blank');

    }

    function toVideo(fileId,id) {
        tmpFileId = fileId;
        rcid=id
        $('#shipinModal').modal('show');
        return false;
    }

    $('#shipinModal').on('show.bs.modal', function (e) {
        loadVideo(tmpFileId,rcid);
    });
    $('#shipinModal').on('hidden.bs.modal', function (e) {
        pushvideoprogress(rcid,dataname);
        let player = videojs('kiftplayer');
        console.log(player);
        player.dispose();
        player = null;
        clearInterval(myStu);

    });

    function loadVideo(fileId,id) {
        $.ajax({
            url: '/recourse/playVideo/' + fileId,
            type: 'get',
            dataType: 'json',
            success: function (result) {
                if (result.code === 0) {
                    playVideo(result.data, result.pf,id)
                    console.log(id);
                } else {
                    alert("错误：无法定位该文件。");
                }
            },
            error: function () {
                alert("错误：请求失败，请刷新重试。");
            }
        });

    }


    //显示视屏信息并播放视频
    function playVideo(f, pf, id) {
        $.ajax({
            url: '/Student/chapter/video/info/' + id,
            type: 'get',
            dataType: 'json',
            success: function (data) {
                console.log(data)
                dataname=data.videoname
                time=data.lastTime;
                $("#vname").text(f.fileName);
                $("#vcdate").text(f.fileCreationDate);
                $("#vsize").text(f.fileSize);
                $("#playerbox")
                    .html(
                        "<video id='kiftplayer' class='video-js col-md-12' controls preload='auto' height='500'>"
                        + "<source src='" + pf + "/resource/getResource?fid=" + f.fileId + "' type='video/mp4'>"
                        + "<source src='" + pf + "/resource/getResource?fid=" + f.fileId + "' type='video/webm'>"
                        + "</video>");
                let player;
                if (data.state==0){
                     player = videojs('kiftplayer',{
                        controlBar: { // 设置控制条组件
                            /* 设置控制条里面组件的相关属性及显示与否
                            'currentTimeDisplay':true,
                            'timeDivider':true,
                            'durationDisplay':true,
                            'remainingTimeDisplay':false,
                            volumePanel: {
                              inline: false,
                            }
                            */
                            /* 使用children的形式可以控制每一个控件的位置，以及显示与否 */
                            children: [
                                {name: 'playToggle'}, // 播放按钮
                                {name: 'currentTimeDisplay'}, // 当前已播放时间
                                // {name: 'progressControl'}, // 播放进度条
                                {name: 'durationDisplay'}, // 总时间
                                { // 倍数播放
                                    name: 'playbackRateMenuButton',
                                    'playbackRates': [0.5, 1, 1.5, 2, 2.5]
                                },
                                {
                                    name: 'volumePanel', // 音量控制
                                    inline: false, // 不使用水平方式
                                },
                                {name: 'FullscreenToggle'} // 全屏
                            ]
                        }
                    });
                }else {
                     player = videojs('kiftplayer')
                }
                player.ready(function () {
                    this.play();
                    this.currentTime(time);
                    getvideoprogress(data.id,data.videoname);
                });

            },
            error: function () {
                alert("错误：无法读取进度2。");
            }
        });

    }
    function getvideoprogress(id,videoname) {
        myStu = setInterval(function(){pushvideoprogress(id,videoname) }, 60*1000);
    }
    function pushvideoprogress(id,videoname) {
        let player = videojs('kiftplayer');
        var currentTime=player.currentTime();
        $.ajax({
            url: '/Student/chapter/video/push',
            type: 'POST',
            data:{
                id:id,
                videoname:videoname,
                lastTime:currentTime
            },
            success: function (result) {
                console.log("记录成功");
            },
            error: function () {
                console.log("记录错误");
            }
        });
    };

    $('.info-tip').poshytip({
        className: 'tip-yellowsimple',
        showTimeout: 1,
        alignTo: 'target',
        alignX: 'center',
        offsetY: 5,
        allowTipHover: false
    });

</script>
