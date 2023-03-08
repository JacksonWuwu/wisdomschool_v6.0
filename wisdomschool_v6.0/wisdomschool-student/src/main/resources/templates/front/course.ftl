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
                            <#--<div class="f-cb f-bg">-->
                                <#--<h2 class="u-ctit">讨论</h2>-->
                                <#--<div id="j-cmt-box" class="f-bdr">-->
                                    <#--<div class="">-->
                                        <#--<div class="m-cmttxt"></div>-->
                                        <#--<div class="u-asknull j-al-null" style="display:none">暂无评价</div>-->
                                        <#--<div class="m-cmtcnt j-5cnt">-->
                                        <#--</div>-->
                                        <#--<a class="m-ckgd j-op f-db" id="auto-id-1555802291160">查看更多</a></div>-->
                                    <#--<script>-->
                                        <#--$.ajax({-->
                                            <#--type: "post",-->
                                            <#--url: "/user/shouye/talk",-->
                                            <#--data: {-->
                                                <#--pageSize: 10-->
                                            <#--},-->
                                            <#--dataType: 'json',-->
                                            <#--success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success-->
                                                <#--if (data.code != 0) {-->

                                                    <#--$(".m-cmtcnt").append("<h4>暂无提问！</h4>");-->
                                                <#--} else {-->
                                                    <#--var json = data.data;-->
                                                    <#--$(".m-cmtcnt").html("");-->

                                                    <#--for (var i = 0; i < 11 && i < json.length; i++) {  //只显示前11条-->
                                                        <#--$(".m-cmtcnt").append(-->
                                                            <#--'<div class="u-cmt" id="auto-id-1555802291208">'-->
                                                            <#--+ '<div class="cnt f-fc3 f-fs1 j-info" style="margin-bottom: 4px;">'-->
                                                            <#--+ '<a title="点击查看" href="/user/topic/detail/' + json[i].id + '">' + json[i].title + '</a>'-->
                                                            <#--+ '</div>'-->
                                                            <#--+ '<span class="time s-fc2 f-fs0 j-info">' + json[i].content + '</span>'-->
                                                            <#--+ '<a class="f-pa reply s-fc3 j-replyBtn" id="auto-id-1555802291230"'-->
                                                            <#--+ 'style="display: none;">回复</a>'-->
                                                            <#--+ '<div class="j-reply"></div>'-->
                                                            <#--+ '</div>'-->
                                                        <#--);-->
                                                    <#--}-->
                                                    <#--;-->
                                                <#--}-->
                                                <#--;-->
                                            <#--}-->
                                        <#--});-->
                                    <#--</script>-->
                                <#--</div>-->
                            <#--</div>-->
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
<#--                                                            <@chapterResource courseId=child.cid chapterId=child.id>-->
                                                                <#list chapterresources as cs>
                                                                    <#if cs.resourceType == 1>
<#--                                                                        <a href="javascript:void(0);"-->
<#--                                                                           title="${cs.name}" class="info-tip"-->
<#--                                                                           style="padding-left:6px;padding-right:6px;"-->
<#--                                                                           onclick="toVideo('${cs.recourse.attrId}','${cs.name}')">-->
<#--                                                                            <span class="fa fa-file-movie-o"-->
<#--                                                                                  style="font-size: 18px;"></span>-->
<#--                                                                        </a>-->
                                                                    <#elseif cs.ext == 'pdf'>
                                                                        <a href="javascript:void(0);"
                                                                           title="${cs.name}" class="info-tip"
                                                                           style="padding-left:6px;padding-right:6px;"
                                                                           onclick="pdfView('${cs.recourse.attrId}')">
                                                                            <span class="fa fa-file-pdf-o"
                                                                                  style="font-size: 18px;"></span>
                                                                        </a>
                                                                    <#elseif cs.resourceType == 3>
<#--                                                                        <a href="javascript:void(0);"-->
<#--                                                                           title="${cs.name}" class="info-tip"-->
<#--                                                                           style="padding-left:6px;padding-right:6px;"-->
<#--                                                                           onclick="toTest('${cs.rid}')">-->
<#--                                                                            <span class="fa fa-paper-plane"-->
<#--                                                                                  style="font-size: 18px;"></span>-->
<#--                                                                        </a>-->
                                                                    <#elseif cs.resourceType==4>
<#--                                                                        <a href="javascript:void(0);"-->
<#--                                                                           title="${cs.name}" class="info-tip"-->
<#--                                                                           style="padding-left:6px;padding-right:6px;"-->
<#--                                                                           onclick="downLoad('${cs.recourse.attrId}','${cs.rid}')">-->
<#--                                                                            <span class="fa fa-file-archive-o"-->
<#--                                                                                  style="font-size: 18px;"></span>-->
<#--                                                                        </a>-->
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
<#--                                                            </@chapterResource>-->
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
