<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" name="viewport" content="width=device-width,user-scalable=no">
    <title>章节练习</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <style>
        input[type="radio"] {
            position: absolute;
            clip: rect(0, 0, 0, 0);
        }

        input[type="radio"] + label::before {
            content: "";
            background: #FFFFFF;
            border: 1px solid #C2C7CB;
            width: 15px;
            height: 15px;
            border-radius: 50%;
            float: left;
            margin-right: 6px;
            margin-top: 2px;
        }

        input[type="radio"]:checked + label::before {
            background-color: #76E025;
            background-clip: content-box;
            padding: 3px;
        }
        input[type="checkbox"] {
            position: absolute;
            clip: rect(0, 0, 0, 0);
        }

        input[type="checkbox"] + label::before {
            content: "";
            background: #FFFFFF;
            border: 1px solid #C2C7CB;
            width: 15px;
            height: 15px;
            border-radius: 50%;
            float: left;
            margin-right: 6px;
            margin-top: 2px;
        }

        input[type="checkbox"]:checked + label::before {
            background-color: #76E025;
            background-clip: content-box;
            padding: 3px;
        }
        .showanswer {
            width: 100px;
            height: 30px;
            background: green;
            border: none;
            border-radius: 6px;
            color: white;
            margin: 6px 10px;
            display: none;
        }

        .consummation {
            width: 100px;
            height: 30px;
            background: #f0ad4e;
            border: none;
            border-radius: 6px;
            color: white;
            margin: 6px 10px;
            display: block;
            margin: 0 auto;
            margin-top: 30px;
            margin-bottom: 40px;
        }

        .answer {
            color: red;
            margin-top: 5px;
        }
        .questionType{
            font-size: 20px;
            font-weight: bolder;
            padding-bottom: 10px;
        }
    </style>
</head>
<body>
<div id="shuatishow"
     style="color: #000000;margin-left: 15px;margin-right:10px;margin-top: 8px;font-size: 15px;text-align: left;height: auto;line-height: normal">
    <#list shuatis as s>
        <#if s.myQuestions.titleTypeNum=="09">
            <p class="questionType">单选题</p>
            <#break>
        </#if>
    </#list>
    <#list shuatis as s>
        <#if s.myQuestions.titleTypeNum=="09">
            <div style="font-size: 18px;margin-bottom: 10px;word-wrap:break-word;">
                ${s.myQuestions.title}
            </div>
            <div style="margin-bottom: 20px">
                <#list s.myOptionAnswerList as m>
                    <li style="list-style: none;margin-left: 15px;margin-bottom: 5px">
                    <span>
                        <input type="radio" class="single" name="oneselect${s.myQuestions.id}"
                         <#if m_index ==0>
                                    id="${s.myQuestions.id}A" value="${m.stoption}">
                        <label for="${s.myQuestions.id}A" style="font-weight: normal;"
                        <#if m.stanswer!=0>
                               class="correct"
                        </#if>
                            >&nbsp;A.&nbsp;&nbsp;${m.stoption}
                        </label>
                         </#if>
                        <#if m_index ==1>
                            id="${s.myQuestions.id}B" value="${m.stoption}">
                            <label for="${s.myQuestions.id}B" style="font-weight: normal;"
                            <#if m.stanswer!=0>
                                   class="correct"
                            </#if>
                                >&nbsp;B.&nbsp;&nbsp;${m.stoption}
                            </label>
                        </#if>
                        <#if m_index ==2>
                            id="${s.myQuestions.id}C" value="${m.stoption}">
                            <label for="${s.myQuestions.id}C" style="font-weight: normal;"
                            <#if m.stanswer!=0>
                                   class="correct"
                            </#if>
                                >&nbsp;C.&nbsp;&nbsp;${m.stoption}
                            </label>
                        </#if>
                        <#if m_index ==3>
                            id="${s.myQuestions.id}D" value="${m.stoption}">
                            <label for="${s.myQuestions.id}D" style="font-weight: normal;"
                            <#if m.stanswer!=0>
                                   class="correct"
                            </#if>
                                >&nbsp;D.&nbsp;&nbsp;${m.stoption}
                            </label>
                        </#if>
                        <#if m_index ==4>
                            id="${s.myQuestions.id}E" value="${m.stoption}">
                            <label for="${s.myQuestions.id}E" style="font-weight: normal;"
                            <#if m.stanswer!=0>
                                class="correct"
                            </#if>
                                >&nbsp;E.&nbsp;&nbsp;${m.stoption}
                            </label>
                        </#if>
                        <#if m_index ==5>
                            id="${s.myQuestions.id}F" value="${m.stoption}">
                            <label for="${s.myQuestions.id}F" style="font-weight: normal;"
                            <#if m.stanswer!=0>
                                class="correct"
                            </#if>
                                >&nbsp;F.&nbsp;&nbsp;${m.stoption}
                            </label>
                        </#if>
                    </span>
                    </li>
                </#list>
                <button onclick="showanswer(${s.myQuestions.id})" class="showanswer">答案解析</button>
                <div id="${s.myQuestions.id}answer" class="answer"
                     style="display: none;width:90%;word-wrap:break-word;">答案：${s.myQuestions.parsing}</div>
            </div>
        </#if>
    </#list>
    <#list shuatis as s>
        <#if s.myQuestions.titleTypeNum=="03">
            <p class="questionType">多选题</p>
            <#break>
        </#if>
    </#list>
    <#list shuatis as s>
        <#if s.myQuestions.titleTypeNum=="03">
            <div style=" font-size: 18px;margin-bottom: 10px;width:90%;word-wrap:break-word;">
                ${s.myQuestions.title}
            </div>
            <div style="margin-bottom: 20px">
                <#list s.myOptionAnswerList as m>
                    <li style="list-style: none;margin-bottom: 5px">
                    <span>
                        <input class="checkbox" type="checkbox"  name="manyselect${s.myQuestions.id}"
                         <#if m_index ==0>
                                    id="${s.myQuestions.id}A" value="${m.stoption}">
                        <label for="${s.myQuestions.id}A" style="font-weight: normal;"
                        <#if m.stanswer!=0>
                            class="correct"
                        </#if>
                            >&nbsp;A.&nbsp;&nbsp;${m.stoption}
                        </label>
                         </#if>
                        <#if m_index ==1>
                            id="${s.myQuestions.id}B" value="${m.stoption}">
                            <label for="${s.myQuestions.id}B" style="font-weight: normal;"
                            <#if m.stanswer!=0>
                                class="correct"
                            </#if>
                                >&nbsp;B.&nbsp;&nbsp;${m.stoption}
                            </label>
                        </#if>
                        <#if m_index ==2>
                            id="${s.myQuestions.id}C" value="${m.stoption}">
                            <label for="${s.myQuestions.id}C" style="font-weight: normal;"
                            <#if m.stanswer!=0>
                                class="correct"
                            </#if>
                                >&nbsp;C.&nbsp;&nbsp;${m.stoption}
                            </label>
                        </#if>
                        <#if m_index ==3>
                            id="${s.myQuestions.id}D" value="${m.stoption}">
                            <label for="${s.myQuestions.id}D" style="font-weight: normal;"
                            <#if m.stanswer!=0>
                                class="correct"
                            </#if>
                                >&nbsp;D.&nbsp;&nbsp;${m.stoption}
                            </label>
                        </#if>
                        <#if m_index ==4>
                            id="${s.myQuestions.id}E" value="${m.stoption}">
                            <label for="${s.myQuestions.id}E" style="font-weight: normal;"
                            <#if m.stanswer!=0>
                                class="correct"
                            </#if>
                                >&nbsp;E.&nbsp;&nbsp;${m.stoption}
                            </label>
                        </#if>
                        <#if m_index ==5>
                            id="${s.myQuestions.id}F" value="${m.stoption}">
                            <label for="${s.myQuestions.id}F" style="font-weight: normal;"
                            <#if m.stanswer!=0>
                                class="correct"
                            </#if>
                                >&nbsp;F.&nbsp;&nbsp;${m.stoption}
                            </label>
                        </#if>
                    </span>
                    </li>
                </#list>
                <button onclick="showanswer(${s.myQuestions.id})" class="showanswer">答案解析</button>
                <div id="${s.myQuestions.id}answer" class="answer"
                     style="display: none;width:90%;word-wrap:break-word;">答案：${s.myQuestions.parsing}</div>
            </div>
        </#if>
    </#list>
    <#list shuatis as s>
        <#if s.myQuestions.titleTypeNum=="05">
            <p class="questionType">判断题</p>
            <#break>
        </#if>
    </#list>
    <#list shuatis as s>
        <#if s.myQuestions.titleTypeNum=="05">
            <div style=" font-size: 18px;margin-bottom: 10px;word-wrap:break-word;">${s.myQuestions.title}
                <li style="padding-left:10px;list-style: none;display: inline;float: right">
                       <span>
                           <input type="radio" class="judge"  name="judge${s.myQuestions.id}" id="${s.myQuestions.id}true" value="对">
                           <label for="${s.myQuestions.id}true" style="font-weight: normal;"><span style="color: #0E9A00" class="glyphicon glyphicon-ok"></span></label>
                       </span>
                </li>
                <li style="padding-left:10px;list-style: none;display: inline;float: right">
                       <span>
                           <input type="radio" class="judge" name="judge${s.myQuestions.id}" id="${s.myQuestions.id}false" value="错">
                           <label for="${s.myQuestions.id}false" style="font-weight: normal;"><span style="color: red" class="glyphicon glyphicon-remove"></span></label>
                       </span>
                </li>
            </div>
            <button onclick="showanswer(${s.myQuestions.id})" class="showanswer">答案解析</button>
            <div id="${s.myQuestions.id}answer" class="answer"
                 style="display: none;width:90%;word-wrap:break-word;">答案：${s.myQuestions.parsing}</div>
        </#if>
    </#list>
    <#list shuatis as s>
        <#if s.myQuestions.titleTypeNum=="06">
            <p class="questionType">简答题</p>
            <#break>
        </#if>
    </#list>
    <#list shuatis as s>
        <#if s.myQuestions.titleTypeNum=="06">
            <div style="font-size: 18px;margin-bottom: 10px;width:90%;word-wrap:break-word;">
                ${s.myQuestions.title}
            </div>
            <div style="margin-bottom: 20px">
                <div style="display:inline-block;vertical-align:top;">答：</div>
                <textarea id="${s.myQuestions.id}" class="shortAnswer" name="shortAnswerQquestion" rows="5"
                          style="width: 90%;"></textarea>
                <button onclick="showanswer(${s.myQuestions.id})" class="showanswer">答案解析</button>
                <div id="${s.myQuestions.id}answer" class="answer"
                     style="display: none;width:90%x;word-wrap:break-word;">答案：${s.myQuestions.parsing}</div>
            </div>
        </#if>
    </#list>
    <#list shuatis as s>
        <#if s.myQuestions.titleTypeNum=="10">
            <p class="questionType">程序设计题</p>
            <#break>
        </#if>
    </#list>
    <#list shuatis as s>
        <#if s.myQuestions.titleTypeNum=="10">
            <div style="font-size: 18px;margin-bottom: 10px;width:90%;word-wrap:break-word;">
                ${s.myQuestions.title}
            </div>
            <div style="margin-bottom: 20px">
                <div style="display:inline-block;vertical-align:top;">答：</div>
                <textarea id="${s.myQuestions.id}" class="programminguestion" name="shortAnswerQquestion" rows="5"
                          style="width:90%;"></textarea>
                <button onclick="showanswer(${s.myQuestions.id})" class="showanswer">答案解析</button>
                <div id="${s.myQuestions.id}answer" class="answer"
                     style="display: none;width:90%;word-wrap:break-word;">答案：${s.myQuestions.parsing}</div>
            </div>
        </#if>
    </#list>
    <span id="chapterAlert" style="font-size:18px;width: 90%;color: red;display:none;text-align:center">&nbsp;&nbsp;题目没有刷完，请继续刷题</span>
    <button class="consummation" id="consummation" onclick="chapterConsummation(${listsize},'${myQuestionsIds}')">完成刷题</button>
</div>


</body>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
<script>
    var count=0;

    function chapterConsummation(listsize,myQuestionsIds) {
        var MCQCorrectTotal=0;
        console.log(myQuestionsIds);
        var  answers=[];
        var str = myQuestionsIds.split(',');
        for(var i=0;i<=str.length;i++) {
            var obj = document.getElementsByName("oneselect" + str[i]);
            if (obj.length != 0) {
                for (var j = 0; j < obj.length; j++) {
                    if (obj[j].getAttribute("class") == "single") {
                        if (obj[j].checked) {
                            console.log("选择题答案" + obj[j].value);
                            var row1 = {};
                            row1.titleId = str[i];
                            row1.stuanswer = obj[j].value;
                            answers.push(row1);
                        }
                    }
                }
            }
        }
        for(var i=0;i<=str.length;i++){
            var obj = document.getElementsByName("manyselect"+str[i]);
            var row1 = {};
            var MCQanswer="";
            if(obj.length!=0){
                for(var j=0; j<obj.length; j ++){
                    if(obj[j].getAttribute("class")=="checkbox"){
                        if(obj[j].checked){
                            MCQanswer=MCQanswer+obj[j].value+",";
                        }
                    }
                }
                console.log("多选题选中答案"+MCQanswer);
                row1.titleId=str[i];
                row1.stuanswer=MCQanswer;
                answers.push(row1);
            }
        }
        for(var i=0;i<=str.length;i++) {
            var obj = document.getElementsByName("judge" + str[i]);
            if (obj.length != 0) {
                for (var j = 0; j < obj.length; j++) {
                    if (obj[j].getAttribute("class") == "judge") {
                        if (obj[j].checked) {
                            console.log("判断题答案" + obj[j].value);
                            var row1 = {};
                            row1.titleId = str[i];
                            row1.stuanswer = obj[j].value;
                            answers.push(row1);
                        }
                    }
                }
            }
        }
        var shortAnswerQquestion=document.getElementsByName("shortAnswerQquestion");
        for(var n=0;n<shortAnswerQquestion.length;n++){
            if(shortAnswerQquestion[n].value!=""){
                console.log("简答题答案"+shortAnswerQquestion[n].value);
                var row2 = {};
                row2.titleId=shortAnswerQquestion[n].id;
                row2.stuanswer=shortAnswerQquestion[n].value;
                answers.push(row2);
            }
            //answers.set(shortAnswerQquestion[n].id,shortAnswerQquestion[n].value);
        }
        console.log(answers);
        console.log(JSON.stringify(answers));
        if(answers.length<listsize){
            document.getElementById("chapterAlert").style.display="block";
        }else {
            $.ajax({
                type : 'POST',
                contentType : 'application/json;charset=utf-8',
                url : '${request.contextPath}/wx/exam/submitShuati',
                processData : false,
                dataType : 'json',
                data : JSON.stringify(answers),
                success : function(data) {
                    if(data=="1"){
                        //根据后台返回值确定是否操作成功
                        var answers = document.getElementsByClassName("showanswer");
                        for(var i=0;i<answers.length;i++){
                            answers[i].style.display="block";
                        }
                        var corrects = document.getElementsByClassName("correct");
                        for(var i=0;i<corrects.length;i++){
                            corrects[i].style.color="#FF0000";
                            var MCQTrueOption=corrects[i].getAttribute("for");
                            if(document.getElementById(MCQTrueOption).checked){
                                if(document.getElementById(MCQTrueOption).getAttribute("class")!="checkbox"){
                                    MCQCorrectTotal=MCQCorrectTotal+1;
                                }
                            }
                        }
                        console.log("选择题做对数"+MCQCorrectTotal);
                        document.getElementById("consummation").style.display="none";
                        document.getElementById("chapterAlert").style.display="block";
                        document.getElementById("chapterAlert").style.marginBottom="20px";
                        document.getElementById("chapterAlert").innerHTML="恭喜！本章节题目已刷完！\n 单选题一共做对了"+MCQCorrectTotal+"道";
                        // document.getElementById("zhangjieshijuanshow").innerHTML ="";
                        // document.getElementById("zhangjieshijuanshow").style.height=500+"px"
                        // document.getElementById("zhangjieshijuanshow").innerHTML="恭喜！本章节题目已刷完！";
                    }
                }
            });
        }

        // if(count!=listsize){
        //     console.log("继续刷题");
        //     document.getElementById("chapterAlert").style.display="block";
        // }else {
        //     console.log("刷题完成");
        //     count=0;
        //     document.getElementById("zhangjieshijuanshow").innerHTML ="";
        //     document.getElementById("zhangjieshijuanshow").style.height=500+"px"
        //     document.getElementById("zhangjieshijuanshow").innerHTML="恭喜！本章节题目已刷完！";
        // }
    }
    function showanswer(questionId) {
        count=count+1;
        console.log("!!!!!!!!!!!!!!!!!!!!!!"+count);
        document.getElementById(questionId+"answer").style.display="block";
    }
</script>
</html>