<link rel="stylesheet" href="/css/course-main.css">
<link href="/js/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/js/plugins/jquery.poshytip/tip-yellowsimple/tip-yellowsimple.css"/>
<script type="text/javascript" src="/js/plugins/jquery.poshytip/jquery.poshytip.min.js"></script>
<#include '/front/inc/commonJs.ftl'/>
<style>
    .learn-btn {
        display: inline-block;
        border-radius: 10px;
        text-align: center;
        background-color: #0089D2;
        color: #FFF;
        width: 71px;
        height: 32px;
        line-height: 37px;
        font-size: 16px;
        position: relative;
        top: 3px;
        border: 0;
    }
    .table_box{
        padding: 26px 44px;
        background-color: #fff;
        border-radius: 6px;
        font-size:14px;
        border: 1px solid #f0f0f0;
    }

    .has_gutter{
        color:#909399;
        font-weight: 500;
        display: table-header-group;
        vertical-align: middle;
        border-color: inherit;
    }


</style>
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
                                    <a href="">课程作业</a>
                                </li>
                                <li class="navcrumb-item current" id="navcrumbId-2">
                                    <span class="arrow"></span>
                                    <a href="javascript: void(0);">${course.name}</a>
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
                                        <li class="f-fl"><a class="j-chtab selected" hidefocus="true" href="/user/course/learnone/${tcid}">考试</a></li>
                                        <li class="f-fl"><a class="j-chtab selected" hidefocus="true" href="/user/course/learnjilu/${tcid}">记录</a></li>
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
                                    <div class="table-box">
                                        <table class="imagetable">
                                            <tr>
                                                <th style="text-align: center">作业名称</th>
                                                <th style="text-align: center">作业时间</th>
                                                <th style="text-align: center">作业状态</th>
                                                <th style="text-align: center">作业分值</th>
<#--                                                <th>考试成绩</th>-->
                                                <th style="text-align: center">开始作业</th>
                                            </tr>
                                        <#list examTestList as c>
                                            <tr>
                                            <td style="text-align: center">${c.testName} </td>&nbsp;
                                            <td style="text-align: center">${c.startTime}-${c.endTime}</td>&nbsp;
                                            <td style="text-align: center">
                                            <#if c.setExam=="0">
                                                未开始
                                            <#elseif c.setExam=="2">
                                                已结束
                                            <#else>
                                                <#if c.user_state==0>
                                                    未参与
                                                <#else>
                                                    已参与
                                                </#if>
                                            </#if>
                                            </td>
                                        <#--                                            <th>${c.stuScore}</th>&nbsp;-->
                                            <td style="text-align: center">${c.paperscore} </td>
                                            <td style="text-align: center">
                                            <#if c.setExam=="0">
                                                <button class="learn-btn center disable">
                                                    未开始
                                                </button>
                                            <#elseif c.setExam=="2">
                                                <button class="learn-btn center disable">
                                                    已结束
                                                </button>
                                            <#else>
                                                <#if c.sumbitState==0>
                                                    <button class="learn-btn center"
                                                    onclick="toExam(${c.userId},${c.testPaperWorkId},${c.id})">
                                                    开始
                                                    </button>
                                                <#else>
                                                    <button class="learn-btn center"
                                                    onclick="toExam(${c.userId},${c.testPaperWorkId},${c.id})">
                                                    查看
                                                    </button>
                                                </#if>
                                            </#if>
                                            </td>&nbsp;
                                            </tr>
                                        </#list>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
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
