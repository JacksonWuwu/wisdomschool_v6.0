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
                                        <li class="f-fl"><a class="j-chtab selected" hidefocus="true">考试</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="f-cb">
                        <#--                        <div class="g-cisd2" id="bdCom">-->
                        <#--                            <div id="j-repo-box" class="f-dn f-bg repoBox f-cb">-->
                        <#--                                <h2 class="u-ctit">相关题库</h2>-->
                        <#--                                <div id="j-repoList" class="repoList f-bdr">-->
                        <#--                                </div>-->
                        <#--                            </div>-->
                        <#--                        </div>-->
                        <div class="g-cimn2" id="bdDir">
                            <div class="g-cimn2c">
                                <div id="j-livecourse-remind">
                                </div>
                                <div class="b-15"></div>
                                <div class="f-cb f-bg f-pr" id="j-chapter-list" style="z-index:10;">
                                    <div class="f-cb">
                                        <h2 class="u-ctit f-thide f-fl" id="j-chapter-title" style="width:320px;">
                                            上机作业</h2>
                                    </div>
                                    <div class="f-cb">
                                        <table class="imagetable">
                                            <tr>
                                                <th>名称</th>
                                                <th>链接</th>
                                            </tr>
                                            <#list chapterResourceList as cs>
                                                <#if cs??>
                                                    <tr>
                                                        <th>${cs.previewname}</th>
                                                        <th><a href="javascript:void(0);"
                                                               title="${cs.previewname}" class="info-tip"
                                                               style="padding-left:6px;padding-right:6px;"
                                                               onclick="topreview('${cs.id}')">
                                                                <span class="fa fa-paper-plane" style="font-size: 18px;"></span>进入预习
                                                            </a></th>
                                                        </tr>
                                                </#if>
                                            <#else>
                                                <div class="f-cb">-->
                                                    <h2 class="u-ctit f-thide f-fl" id="j-chapter-title" style="width:320px;">
                                                        当前无任务</h2>
                                                </div>
                                            </#list>
                                        </table>
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
