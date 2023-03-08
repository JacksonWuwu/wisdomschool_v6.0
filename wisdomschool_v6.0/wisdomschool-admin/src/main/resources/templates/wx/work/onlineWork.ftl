<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" name="viewport" content="width=device-width,user-scalable=no">
    <title>在线作业</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/js/plugins/pagination/style/pagination.css" media="screen">
    <link rel="stylesheet" type="text/css" href="/js/plugins/pagination/style/normalize.css" media="screen">
    <link rel="stylesheet" href="/css/wxcourse-main.css">
    <link rel="stylesheet" type="text/css" href="/js/plugins/jquery.poshytip/tip-yellowsimple/tip-yellowsimple.css"/>
    <script type="text/javascript" src="/js/plugins/jquery.poshytip/jquery.poshytip.min.js"></script>
    <link href="/js/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <script type="text/javascript" src="/js/bootstrap.min.js"></script>
    <style>
        table.imagetable {
            font-family: verdana,arial,sans-serif;
            font-size:18px;
            color:#333333;
            width: 208%;
            border-width: 1px;
            border-color: #999999;
            border-collapse: collapse;
        }
        table.imagetable th {
            background:#b5cfd2;
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #999999;
        }
    </style>
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
            <div class="f-cb" style="background-color: darkgreen;">
                <#--<h2 class="u-ctit f-thide f-fl" id="j-chapter-title">课程章节</h2>-->
                <a href="/wx/course/detail/${tcid}?openId=${openId}" class="u-ctit f-thide f-fl" id="j-chapter-title">课程章节</a>
                <a  href="/wx/course/paperWork/${tcid}?openId=${openId}" class="u-ctit f-thide f-fl" id="j-chapter-title">在线作业</a>
                <div class="f-fr u-muluchaptertime" id="muluchaptertime">
                </div>
            </div>
            <div class="m-chapterList f-pr">
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
                                    <table class="imagetable">
                                        <tr>
                                            <th>作业名称</th>
                                            <th>作业总分</th>
                                            <#--                                                <th>考试成绩</th>-->
                                            <th>开始作业</th>
                                        </tr>
                                        <#list examTestList as c>
                                            <#if c.sumbitState=='0'>
                                            <tr>
                                                <th>${c.testName} </th>&nbsp;
                                                <th>${c.paperscore}</th>&nbsp;
<#--                                                <th>${c.stuScore}</th>&nbsp;-->
                                                <th><button class="learn-btn"
                                                        onclick="toExam(${c.userId},${c.testPaperWorkId},${c.id})">
                                                    开始
                                                </button></th>&nbsp;
                                            </tr>
                                            <#else>
                                            <tr>
                                                <th>${c.testName} </th>&nbsp;
                                                <th>${c.paperscore}</th>&nbsp;
                                                <th><button disabled style="background-color: #5a5c5f" class="learn-btn"
                                                            onclick="toExam(${c.userId},${c.testPaperWorkId},${c.id}">
                                                        开始</button></th>&nbsp;
<#--                                                <th>已提交</th>&nbsp;-->
                                            </tr>
                                            </#if>
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
<script>

    $().ready(function () { //$().ready页面加载好就执行


    });

    function toExam(userId,testPaperOneId,tutId) {
        window.open('/wx/course/chapterPaperWork?testPaperOneId=' + testPaperOneId+'&&studentId=' + userId +"&&tutId="+tutId, '_blank');

    }


</script>





</body>
</html>