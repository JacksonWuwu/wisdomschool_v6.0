<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>课程学习-教程</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!--页头-->
    <#include '/front/inc/commonJs.ftl'/>
</head>
<body>
<#include "/front/inc/ui.ftl"/>
<@layout  "wstom - 我的课程" "我的课程" "我的课程">
    <link rel="stylesheet" href="/dist/css/course.css">
    <style>
        .chapter-resource {
            flex-direction: column;
        }

        .chapter-resource li {
            margin: 8px 18px;
        }

        .course-info {
            margin-top: 0;
        }

        .comment-content-m {
            font-size: 16px;
        }

        @media (min-width: 992px) {
            .col-md-3 {
                width: 24%;
            }
        }

        .learn-btn {
            display: inline-block;
            border-radius: 4px;
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
        .course-video {
            width: 100%;
            height: 300px;
            border: 0px solid #93999F;
        }

    </style>
    <style type="text/css">
        #ExamPaper {
            min-height: 250px;
            overflow-y: auto;
            max-height: 588px;
        }
        #zy {
            min-height: 100px;
            overflow-y: auto;
            max-height: 300px;
        }
        #ks {
            min-height: 100px;
            overflow-y: auto;
            max-height: 300px;
        }

    </style>
    <div class="main-content forehead">
    <div class="main">
    <div class="">
        <!-- 基本信息 -->
        <div class="container">
            <div class="col-md-9 common-shadow pic-list"
                 style="padding-left: 20px;margin-top: 20px; margin-left: 120px;">
                <div class="course-info" id="ExamPaper">
<#--
                    <div class="course-title" style="font-size: 24px;margin-top: 15px;">我的作业</div>
-->
            <#--        <div class="course-video" id="zy">
                        <#list paperList as c>
                            <p class="test_font">
                            <h4>标题:${c.headLine} &nbsp;&nbsp;
                                名称:${c.testName} &nbsp;&nbsp;
                            </h4>
                            <h5>时间:${c.stuStartTime}----${c.stuEndTime}
                                成绩：${c.stuScore}&nbsp;
                                总分:${c.paperscore}&nbsp;&nbsp;
                                <button class="learn-btn"
                                        onclick="openwindow(${c.userId},${c.testPaperId},${c.id},'${c.stuStartTime}','${c.stuEndTime}')">
                                    开始
                                </button>
                            </h5>
                            </p>
                            <hr>
                        </#list>
                    </div>-->
                    <div class="course-title" style="font-size: 24px;margin-top: 15px;">我的考试</div>
                    <div class="course-video" id="ks">
                        <#list examTestList as c>
                            <p class="test_font">
                            <h4>标题:${c.headLine} &nbsp;&nbsp;
                                名称:${c.testName} &nbsp;&nbsp;
                            </h4>
                            <h5>时间:${c.time} 分钟&nbsp;
                                成绩：${c.stuScore}&nbsp;
                                总分:${c.paperscore}&nbsp;&nbsp;
                                <button class="learn-btn"
                                        onclick="openExamWindow(${c.userId},${c.testPaperId},${c.id},'${c.time}')">
                                    开始
                                </button>
                            </h5>
                            </p>
                            <hr>
                        </#list>
                    </div>
                </div>
            </div>
            <!-- 基本信息 end -->
        </div>
    </div>

    <script type="text/javascript">
        seajs.use('courseDetail');

        function openwindow(userId, testPaperId, tutId,startTime, endTime) {
            let xhr = null;
            let geshidate = "";
            let time;
            let Servicedate;
            let isTime = new Boolean();
            if (window.XMLHttpRequest) {
                xhr = new window.XMLHttpRequest();
            } else { // ie
                xhr = new ActiveObject("Microsoft");
            }

            xhr.open("GET", "/", true);
            xhr.send(null);
            xhr.onreadystatechange = function () {
                console.log("xhr.readyState:" + xhr.readyState);
                if (xhr.readyState == 2) {
                    /*这里的readyState有四种状态，方便做不同处理：
                                0: 请求未初始化
                                1: 服务器连接已建立
                                2: 请求已接收
                                3: 请求处理中
                                4: 请求已完成，且响应已就绪*/
                    time = xhr.getResponseHeader("Date");
                    Servicedate = new Date(time);
                    geshidate = Servicedate.getFullYear() + "-" + (Servicedate.getMonth() + 1) + "-" + Servicedate.getDate() + " " + Servicedate.getHours() + ":" + Servicedate.getMinutes() + ":" + Servicedate.getSeconds();
                    console.log("geshidate:" + geshidate);
                    isTime=getServiceTime(geshidate, startTime, endTime);
                }
            }

            console.log("isTime:"+isTime);
            if (isTime) {
                 if (confirm("您确定要开始吗？")) {
                     window.open('/user/stuToTest?testPaperId=' + testPaperId + '&&studentId=' + userId +"&&tutId="+tutId, '_blank');
                 }
            } else {

                alert("不在作业时间");
            }
        };

        function getServiceTime(date1, date2, date3) {
            let oDate1 = new Date(date1);
            let oDate2 = new Date(date2);
            let oDate3 = new Date(date3);
            console.log(oDate1.getTime());
            console.log(oDate2.getTime());
            console.log(oDate3.getTime());
            if ((oDate2.getTime() <= oDate1.getTime()) && (oDate1.getTime() <= oDate3.getTime())) {
                console.log("是时间");
                return true;
            } else {
                console.log("不是时间");
                return false;
            }
        }


        function openExamWindow(userId,testPaperId,tutId){
            // window.open('/user/chapterTest?testPaperId=' + testPaperId, '_blank');
            window.open('/user/stuToTest?testPaperId=' + testPaperId + '&&studentId=' + userId +"&&tutId="+tutId, '_blank');

        }
    </script>
</@layout>

</body>
</html>
