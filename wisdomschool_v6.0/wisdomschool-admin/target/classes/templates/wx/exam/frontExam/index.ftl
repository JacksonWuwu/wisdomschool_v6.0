<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" name="viewport" content="width=device-width,user-scalable=no">
    <title>课前测试</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <script type="text/javascript" src="/js/jquery.min.js"></script>
</head>
<body>
<div class="container">
    <h2 class="heading">课前测试</h2>

    <div class="all-content">
        <div class="form-group">
            <p hidden id="openId">${openId}</p>
                <#list frontexamlist as f>
                    <p hidden id="studentId">${f.userId}</p>
            <p hidden id="testPaperId">${f.testPaperId}</p>
            <p hidden id="tutId">${f.id}</p>
                    <#--<p>${vo.testName}</p>-->
                    <div class="kaoshi" style="margin-left:10px;margin-bottom:15px;border-radius:30px;border:2px solid #5CB85C;padding: 10px 10px 10px 10px;width:300px">
                        <p class="test_font">
                        <h4>卷名: ${f.testName}&nbsp;&nbsp; </h4>
                        <h5>成绩：${f.stuScore}&nbsp;
                            总分: ${f.paperscore}
                            &nbsp;&nbsp;  </h5>
                        <div>
                            <button class="btn btn-danger btn" style="border:2px solid;border-radius:25px;background-color: #5cb85c;color: white" id="startexam" onclick="starttoexam()">开始</button>
                            <#--<button class="learn-btn" style="border:2px solid;border-radius:25px;background-color: #5cb85c;color: white" onclick="openExamWindow(${f.userId},${f.testPaperId},${f.id})"> 开始-->
                            <#--</button>-->
                        </div>
                        </div>

                </#list>
        </div>
    </div>
</div>
</body>
<script>
    // var openId = $("#openId").text();
    // function openExamWindow(userId,testPaperId,tutId){
    //     // window.open('/user/chapterTest?testPaperId=' + testPaperId, '_blank');
    //     window.open('stuToTest?testPaperId=' + testPaperId + '&&studentId=' + userId +"&&tutId="+tutId +"&&openId="+openId, '_blank');
    //
    // }


                //$("#openId").text();
        // var studentId = $("#studentId").text();
        // var testPaperId = $("#testPaperId").text();
        // var tutId = $("#tutId").text();
        // var Url = '/wx/exam/stuToTest?testPaperId=' + testPaperId + '&&studentId=' + userId +"&&tutId="+tutId +"&&openId="+openId';


        function starttoexam() {
            var openId = $("#openId").text();
            var studentId = $("#studentId").text();
            var testPaperId = $("#testPaperId").text();
            var tutId = $("#tutId").text();
            console.log("!!!!!!!!!!!!!"+studentId+"testPaperId"+testPaperId+"tutId"+tutId);
            window.open('/wx/exam/stuToTest?testPaperId=' + testPaperId + '&&studentId=' + studentId +"&&tutId="+tutId+"&&openId="+openId, '_blank');
            //window.open('/wx/exam/stuToTest');
           // window.open('/wx/exam/stuToTest?testPaperId='+testPaperId + '&&studentId='+studentId+"&&tutId="+tutId +"&&openId="+openId');
            // $.ajax({
            //     type: "post",
            //     url: Url,
            //     dataType: "json",
            //     data:'openId='+openId+'&testPaperId='+testPaperId+'&tutId='+tutId+'&studentId='+studentId,
            //     //         {
            //     //     "openId": openId,
            //     //     "testPaperId": testPaperId,
            //     //     "tutId": tutId,
            //     //     "studentId": studentId
            //     // },
            //
            // })
        }
</script>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
</html>

