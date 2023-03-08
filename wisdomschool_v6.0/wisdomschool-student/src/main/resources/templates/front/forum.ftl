<link rel="stylesheet" href="/css/course-main.css">
<link href="/js/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/js/plugins/jquery.poshytip/tip-yellowsimple/tip-yellowsimple.css"/>
<script type="text/javascript" src="/js/plugins/jquery.poshytip/jquery.poshytip.min.js"></script>
<link rel="stylesheet" href="/css/forum.css">
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
                                    <a target="_blank" href="/admin">我的课程</a>
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
                                        <li class="f-fl"><a class="j-chtab" hidefocus="true"
                                                            href="/user/course/learn/${tcid}">主页</a></li>
                                        <!--Regular if107-->
                                        <li class="f-fl"><a class="j-chtab selected" hidefocus="true">讨论区</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="b-15"></div>
                    <div class="m-forumIndex f-cb">
                        <div class="g-cimn2">
                            <div class="g-cimn2c f-bg f-cb" id="forum-box">
                                <div class="fhead " id="j-forumhead">
                                    <h1 class="f-cb">
<span class="ftit f-thide f-fl">
讨论区
</span>
                                        <a href="javascript:void(0);"
                                           target="_blank" class="f-fr rule" id="j-rulelink">讨论区使用规则</a>
                                    </h1>
                                    <p class="">
                                        欢迎进入课程讨论区，你可以与本课程的老师和同学在这里交流。如果你有课程相关的问题，请发到老师答疑区；经验、思考、创意、作品、转帖请发到综合讨论区。欢迎分享，鼓励原创，杜绝广告，请大家共同维护一个包容、积极、相互支持的交流氛围，谢谢。了解更多请点击“讨论区使用规则”↗
                                    </p>
                                </div>
                                <div id="j-forummode" class="sxww" style="display: none;">
                                    <div class="sxw f-pr">
                                        <div class="f-pa line"></div>
                                        <div class="con f-fr f-pr">
                                            <div class="f-fr" id="j-forumsort">
                                                <div class="f-fr" id="j-forumsortw"></div>
                                                <span class="f-fr lab">排序:</span>
                                            </div>
                                            <div class="f-fr" id="j-forumks">
                                                <div class="f-fr" id="j-forumksw"></div>
                                                <span class="f-fr lab">课时:</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="postwrap f-cb">
                                    <ul class="m-posts" id="j-forumposts">
                                        <#list pageModel.rows as topic>
                                            <li class="u-forumli j-forumli"
                                                data-link="/user/courseTopic/${tcid}/detail/${topic.id}"
                                                data-forumid="1002893100" id="auto-id-1555841743403">
                                                <div class="forumi">
                                                    <div class="f-cb">
                                                        <div class="head">
                                                            <a class="j-userLink" href="javascript:void(0);">
                                                                <img class="img j-avatar" id=""
                                                                     src="/img/front/small.jpg" width="30"
                                                                     height="30" alt="${topic.createName}">
                                                            </a>
                                                            <span class="j-userName" data-id="${topic.id}"
                                                                  id="auto-id-${topic.id}">
<a class="u-forumname j-userLink" target="_blank">${topic.createName}</a>
</span>
                                                            <span class="time s-fc5 j-postTime"
                                                                  data-time="1529838825972"
                                                                  id="auto-id-1555841743402">${topic.createTime?string('dd.MM.yyyy HH:mm:ss')}</span>
                                                            <span class="panel f-fr s-fc5">【${topic.forum.name}】</span>
                                                        </div>
                                                    </div>
                                                    <div class="f-cb cnt f-db f-thide">
                                                        <span class="j-manInfo u-manInfo">

                                                            <a href="/user/courseTopic/${tcid}/detail/${topic.id}"
                                                               id="${topic.id }" name="topic"
                                                               style="font-size: 14px; color: 0x333">${topic.title }</a>
                                                            <#if "${topic.essence}"==1>
                                                                <span class="u-forumtag u-forumtag2 j-tagAgreeNode">精华</span>
                                                            </#if>
                                                            <#if "${topic.type}"==2>
                                                                <span class="u-forumtag u-forumtag1 j-tagTopNode">置顶</span>
                                                            </#if>
                                                        </span>
                                                    </div>
                                                    <div class="f-cb">

                                                        <div class="f-cb f-fr">
                                                            <span class="s-fc2 num">（${topic.browse}）</span>
                                                            <span class="s-fc2 num num1">（${topic.replyCount}）</span>
                                                            <span class="s-fc2 num num2">（${topic.thumbsUp}）</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </li>
                                        </#list>
                                    </ul>
                                    <div class="ui-pager" id="j-pager"></div>
                                    <script>
                                        window.pagination = {
                                            "totlePageCount": "1",
                                            "currentPageNo": "1"
                                        };
                                    </script>
                                </div>
                            </div>
                        </div>
                        <div class="g-cisd2">
                            <@shiro.hasPermission name="teacher:course:view">
                                <a class="u-postbtn" id="j-postbtn" href="/user/courseTopicList/toPush/${tcid}"></a>
                            </@shiro.hasPermission>
                            <div class="b-15"></div>
                            <div class="f-bg panelw">
                                <div class="ps" id="j-forumslist">
                                    <#if ForumList?? && (ForumList?size > 0)>
                                        <#list ForumList as forum>
                                            <a href="javascript:void(0);" id="forumid_${forum.id}" class="panelitem"
                                               onclick="talktongji('1','${forum.id}','${sort}')">
                                                <div class="ic ic10 forumsprite f-fl"></div>
                                                <div class="in f-fl">
                                                    <h3 class="f-thide">${forum.name}</h3>
                                                    <p class="f-thide" title="${forum.description}">
                                                        ${forum.description}</p>
                                                </div>
                                            </a>
                                        </#list>
                                    <#else >
                                        <a href="javascript:void(0);" class="panelitem">
                                            <h3 class="f-thide">暂无板块</h3>
                                        </a>
                                    </#if>
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
