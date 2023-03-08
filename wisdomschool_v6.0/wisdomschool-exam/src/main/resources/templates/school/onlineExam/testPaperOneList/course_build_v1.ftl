<#include "/common/style.ftl"/>
<link rel="stylesheet" href="/js/onlineExam/css/demo.css" type="text/css">
<link href="/js/plugins/jquery-ztree/3.5/css/metro/zTreeStyle.css" rel="stylesheet"/>
<style>
    #menu {
        float: left;
    }

</style>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="form-horizontal m" >
        <div id="menu">
            <#-- 目录树-->
            <div class="step-content pos-rel">
                <div class="step-pane active" data-step="1">
                    <div class="container" style="width: auto">
                        <a style="border-radius:10px;margin-left:-15px;"
                           class="btn btn-primary radius-4"
                           onclick="savemenu()">
                            选择知识点
                        </a>
                        <div class="row">
                            <ul id="treeDemoOne" class="ztree"
                                style="margin-top: 10px;margin-left: -0px;float:left;  background: #e7e7e7;"></ul>
                            <ul class="list">
                                <li style="list-style-type:none;"><p>
                                    <ul id="log" class="log"
                                        style="display:none;height:400px; overflow:auto;float:left; background: #e7e7e7; margin-top: 10px;">
                                        <li> 所选知识点有：
                                            <div id="chapterName"></div>
                                        </li>
                                    </ul>
                                    </p>
                                </li>
                            </ul>
                            </li>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <#-- 试卷总分-->
        <div class="col-sm-2 pull-right" id="paperScore" >
            试卷总分:<div id="sumScore" style="color: #C42B1C"></div>
            总题数:<div id="sumSubject" style="color: #C42B1C"></div>
        </div>
        <form class="form-horizontal m" id="form-userExam-add" style="float: left;width: 70%;padding-top: 45px;">
            <div class="form-group">
                <label class="col-sm-3 control-label"style="width: auto">请选择生成的单选题（题库共<span id="choice"></span>道
                    容易题<span id="soEasyChoice"></span>
                    较易题<span id="easyChoice"></span>
                    中等题<span id="mediumChoice"></span>
                    较难题<span id="difficultChoice"></span>
                    困难题<span id="soDifficultChoice"></span>
                    ）
                </label>
            <div class="col-sm-10">
                容易题：
                <input id="soEasyChoiceNum" name="soEasyChoiceNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                较易题：
                <input id="easyChoiceNum" name="easyChoiceNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                中等题：
                <input id="mediumChoiceNum" name="mediumChoiceNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                较难题：
                <input id="difficultChoiceNum" name="difficultChoiceNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                困难题：
                <input id="soDifficultChoiceNum" name="soDifficultChoiceNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                每题分数：
                <input id="choiceScore" name="choiceScore" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text">
                总分：<label id="choiceSum"></label>
<#--                <ul id="choiceK"></ul>-->
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label" style="width: auto">请选择生成的多选题（题库共<span id="checkbox"></span>道
                容易题<span id="soEasyCheckbox"></span>
                较易题<span id="easyCheckbox"></span>
                中等题<span id="mediumCheckbox"></span>
                较难题<span id="difficultCheckbox"></span>
                困难题<span id="soDifficultCheckbox"></span>
                ）</label>
            <div class="col-sm-10">
                容易题：
                <input id="soEasyCheckboxNum" name="soEasyCheckboxNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                较易题：
                <input id="easyCheckboxNum" name="easyCheckboxNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                中等题：
                <input id="mediumCheckboxNum" name="mediumCheckboxNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                较难题：
                <input id="difficultCheckboxNum" name="difficultCheckboxNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                困难题：
                <input id="soDifficultCheckboxNum" name="soDifficultCheckboxNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                每题分数：
                <input id="checkboxScore" name="checkboxScore" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text">
                总分：<label id="checkboxSum"></label>
<#--                <ul id="checkboxK"></ul>-->
            </div>
        </div>
            <div class="form-group">
                <label class="col-sm-3 control-label" style="width: auto">请选择生成的判断题（题库共<span id="judge"></span>道
                    容易题<span id="soEasyJudge"></span>
                    较易题<span id="easyJudge"></span>
                    中等题<span id="mediumJudge"></span>
                    较难题<span id="difficultJudge"></span>
                    困难题<span id="soDifficultJudge"></span>
                    ）</label>
                <div class="col-sm-10">
                    容易题：
                    <input id="soEasyJudgeNum" name="soEasyJudgeNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                    较易题：
                    <input id="easyJudgeNum" name="easyJudgeNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                    中等题：
                    <input id="mediumJudgeNum" name="mediumJudgeNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                    较难题：
                    <input id="difficultJudgeNum" name="difficultJudgeNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                    困难题：
                    <input id="soDifficultJudgeNum" name="soDifficultJudgeNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                    每题分数：
                    <input id="judgeScore" name="judgeScore" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text">
                    总分：<label id="judgeSum"></label>
                    <#--                <ul id="judgeK"></ul>-->
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label"  style="width: auto">请选择生成的填空题（题库共<span id="blank"></span>道
                    容易题<span id="soEasyBlank"></span>
                    较易题<span id="easyBlank"></span>
                    中等题<span id="mediumBlank"></span>
                    较难题<span id="difficultBlank"></span>
                    困难题<span id="soDifficultBlank"></span>
                    ）</label>
                <div class="col-sm-10">
                    容易题：
                    <input id="soEasyBlankNum" name="soEasyBlankNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                    较易题：
                    <input id="easyBlankNum" name="easyBlankNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                    中等题：
                    <input id="mediumBlankNum" name="mediumBlankNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                    较难题：
                    <input id="difficultBlankNum" name="difficultBlankNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                    困难题：
                    <input id="soDifficultBlankNum" name="soDifficultBlankNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                    每题分数：
                    <input id="blankScore" name="blankScore" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text">
                    总分：<label id="blankSum"></label>
<#--                    <ul id="blankK"></ul>-->
                </div>
            </div>
        <div class="form-group">
            <label class="col-sm-3 control-label" style="width: auto">请选择生成的简答题（题库共<span id="jianda"></span>道
                容易题<span id="soEasyJianda"></span>
                较易题<span id="easyJianda"></span>
                中等题<span id="mediumJianda"></span>
                较难题<span id="difficultJianda"></span>
                困难题<span id="soDifficultJianda"></span>
                ）</label>
            <div class="col-sm-10">
                容易题：
                <input id="soEasyJiandaNum" name="soEasyJiandaNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                较易题：
                <input id="easyJiandaNum" name="easyJiandaNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                中等题：
                <input id="mediumJiandaNum" name="mediumJiandaNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                较难题：
                <input id="difficultJiandaNum" name="difficultJiandaNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                困难题：
                <input id="soDifficultJiandaNum" name="soDifficultJiandaNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                每题分数：
                <input id="jiandaScore" name="jiandaScore" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text">
                总分：<label id="jiandaSum"></label>
<#--                <ul id="jiandaK"></ul>-->
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label" style="width: auto">请选择生成的程序题（题库共<span id="program"></span>道
                容易题<span id="soEasyProgram"></span>
                较易题<span id="easyProgram"></span>
                中等题<span id="mediumProgram"></span>
                较难题<span id="difficultProgram"></span>
                困难题<span id="soDifficultProgram"></span>
                ）</label>
            <div class="col-sm-10">
                容易题：
                <input id="soEasyProgramNum" name="soEasyProgramNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                较易题：
                <input id="easyProgramNum" name="easyProgramNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                中等题：
                <input id="mediumProgramNum" name="mediumProgramNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                较难题：
                <input id="difficultProgramNum" name="difficultProgramNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                困难题：
                <input id="soDifficultProgramNum" name="soDifficultProgramNum" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text" />
                每题分数：
                <input id="programScore" name="programScore" value="0" onkeyup="sum(this);" class="time-input" style="width: 50px;height: 30px" type="text">
                总分：<label id="programSum"></label>
<#--                <ul id="programK"></ul>-->
            </div>
        </div>
        </form>


    </div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    $(function () {

            $("#easyChoiceNum").attr('disabled','disabled');
            $("#soEasyChoiceNum").attr('disabled','disabled');
            $("#mediumChoiceNum").attr('disabled','disabled');
            $("#difficultChoiceNum").attr('disabled','disabled');
            $("#soDifficultChoiceNum").attr('disabled','disabled');

            $("#easyCheckboxNum").attr('disabled','disabled');
            $("#soEasyCheckboxNum").attr('disabled','disabled');
            $("#mediumCheckboxNum").attr('disabled','disabled');
            $("#difficultCheckboxNum").attr('disabled','disabled');
            $("#soDifficultCheckboxNum").attr('disabled','disabled');

            $("#easyJudgeNum").attr('disabled','disabled');
            $("#soEasyJudgeNum").attr('disabled','disabled');
            $("#mediumJudgeNum").attr('disabled','disabled');
            $("#difficultJudgeNum").attr('disabled','disabled');
            $("#soDifficultJudgeNum").attr('disabled','disabled');

            $("#easyJiandaNum").attr('disabled','disabled');
            $("#soEasyJiandaNum").attr('disabled','disabled');
            $("#mediumJiandaNum").attr('disabled','disabled');
            $("#difficultJiandaNum").attr('disabled','disabled');
            $("#soDifficultJiandaNum").attr('disabled','disabled');

            $("#easyBlankNum").attr('disabled','disabled');
            $("#soEasyBlankNum").attr('disabled','disabled');
            $("#mediumBlankNum").attr('disabled','disabled');
            $("#difficultBlankNum").attr('disabled','disabled');
            $("#soDifficultBlankNum").attr('disabled','disabled');


            $("#easyProgramNum").attr('disabled','disabled');
            $("#soEasyProgramNum").attr('disabled','disabled');
            $("#mediumProgramNum").attr('disabled','disabled');
            $("#difficultProgramNum").attr('disabled','disabled');
            $("#soDifficultProgramNum").attr('disabled','disabled');

        $.common.initBind();
    });
    var cId = "${testPaper.coursrId}";
    let prefix = "/school/onlineExam/testPaperOneList";
    let coursrId="${id}";
    var chapterId = "";
    let choiceSum = 0;
    let checkboxSum = 0;
    let judgeSum= 0;
    let jiandaSum= 0;
    let programSum= 0;
    let blankSum= 0;
    let choiceNum1 = ${choiceNum};
    let checkboxNum1 = ${checkboxNum};
    let judgeNum1 = ${judgeNum};
    let jiandaNum1 = ${jiandaNum};
    let programNum1 = ${programNum};
    let blankNum1 = ${blankNum};
    $("#choiceSum").text(choiceSum+'分');
    $("#checkboxSum").text(checkboxSum+'分');
    $("#judgeSum").text(judgeSum+'分');
    $("#jiandaSum").text(jiandaSum+'分');
    $("#programSum").text(programSum+'分');
    $("#blankSum").text(blankSum+'分');
   var error=0;
   function sum(obj) {
       let easyChoiceNum = document.getElementById("easyChoiceNum").value;
       let soEasyChoiceNum = document.getElementById("soEasyChoiceNum").value;
       let mediumChoiceNum = document.getElementById("mediumChoiceNum").value;
       let difficultChoiceNum = document.getElementById("difficultChoiceNum").value;
       let soDifficultChoiceNum = document.getElementById("soDifficultChoiceNum").value;
       let easyCheckboxNum = document.getElementById("easyCheckboxNum").value;
       let soEasyCheckboxNum = document.getElementById("soEasyCheckboxNum").value;
       let mediumCheckboxNum = document.getElementById("mediumCheckboxNum").value;
       let difficultCheckboxNum = document.getElementById("difficultCheckboxNum").value;
       let soDifficultCheckboxNum = document.getElementById("soDifficultCheckboxNum").value;
       let easyJudgeNum = document.getElementById("easyJudgeNum").value;
       let soEasyJudgeNum = document.getElementById("soEasyJudgeNum").value;
       let mediumJudgeNum = document.getElementById("mediumJudgeNum").value;
       let difficultJudgeNum = document.getElementById("difficultJudgeNum").value;
       let soDifficultJudgeNum = document.getElementById("soDifficultJudgeNum").value;
       let easyJiandaNum = document.getElementById("easyJiandaNum").value;
       let soEasyJiandaNum = document.getElementById("soEasyJiandaNum").value;
       let mediumJiandaNum = document.getElementById("mediumJiandaNum").value;
       let difficultJiandaNum = document.getElementById("difficultJiandaNum").value;
       let soDifficultJiandaNum = document.getElementById("soDifficultJiandaNum").value;
       let easyProgramNum = document.getElementById("easyProgramNum").value;
       let difficultProgramNum = document.getElementById("difficultProgramNum").value;
       let soEasyProgramNum = document.getElementById("soEasyProgramNum").value;
       let mediumProgramNum = document.getElementById("mediumProgramNum").value;
       let soDifficultProgramNum = document.getElementById("soDifficultProgramNum").value;
       let easyBlankNum = document.getElementById("easyBlankNum").value;
       let soEasyBlankNum = document.getElementById("soEasyBlankNum").value;
       let mediumBlankNum = document.getElementById("mediumBlankNum").value;
       let difficultBlankNum = document.getElementById("difficultBlankNum").value;
       let soDifficultBlankNum = document.getElementById("soDifficultBlankNum").value;

       let easyChoice = document.getElementById("easyChoice").innerHTML;
       let soEasyChoice = document.getElementById("soEasyChoice").innerHTML;
       let mediumChoice = document.getElementById("mediumChoice").innerHTML;
       let difficultChoice = document.getElementById("difficultChoice").innerHTML;
       let soDifficultChoice = document.getElementById("soDifficultChoice").innerHTML;

       let easyCheckbox = document.getElementById("easyCheckbox").innerHTML;
       let soEasyCheckbox= document.getElementById("soEasyCheckbox").innerHTML;
       let mediumCheckbox = document.getElementById("mediumCheckbox").innerHTML;
       let difficultCheckbox = document.getElementById("difficultCheckbox").innerHTML;
       let soDifficultCheckbox = document.getElementById("soDifficultCheckbox").innerHTML;

       let easyJudge = document.getElementById("easyJudge").innerHTML;
       let soEasyJudge = document.getElementById("soEasyJudge").innerHTML;
       let mediumJudge = document.getElementById("mediumJudge").innerHTML;
       let difficultJudge = document.getElementById("difficultJudge").innerHTML;
       let soDifficultJudge = document.getElementById("soDifficultJudge").innerHTML;

       let easyJianda = document.getElementById("easyJianda").innerHTML;
       let soEasyJianda = document.getElementById("soEasyJianda").innerHTML;
       let mediumJianda = document.getElementById("mediumJianda").innerHTML;
       let difficultJianda = document.getElementById("difficultJianda").innerHTML;
       let soDifficultJianda = document.getElementById("soDifficultJianda").innerHTML;

       let easyProgram = document.getElementById("easyProgram").innerHTML;
       let difficultProgram = document.getElementById("difficultProgram").innerHTML;
       let soEasyProgram = document.getElementById("soEasyProgram").innerHTML;
       let mediumProgram = document.getElementById("mediumProgram").innerHTML;
       let soDifficultProgram = document.getElementById("soDifficultProgram").innerHTML;

       let easyBlank = document.getElementById("easyBlank").innerHTML;
       let soEasyBlank = document.getElementById("soEasyBlank").innerHTML;
       let mediumBlank = document.getElementById("mediumBlank").innerHTML;
       let difficultBlank = document.getElementById("difficultBlank").innerHTML;
       let soDifficultBlank = document.getElementById("soDifficultBlank").innerHTML;
       error=0;
       if(soEasyChoiceNum>parseInt(soEasyChoice)){

           $.modal.alertError("不能超过题库数量");
           error=1;
       }
       if(easyChoiceNum>parseInt(easyChoice)){

           $.modal.alertError("不能超过题库数量");
           error=1;
       }

       if(mediumChoiceNum>parseInt(mediumChoice)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }
       if(difficultChoiceNum>parseInt(difficultChoice)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }
       if(soDifficultChoiceNum>parseInt(soDifficultChoice)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }
       //----------
       if(easyCheckboxNum>parseInt(easyCheckbox)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }
       if(soEasyCheckboxNum>parseInt(soEasyCheckbox)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }
       if(mediumCheckboxNum>parseInt(mediumCheckbox)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }
       if(difficultCheckboxNum>parseInt(difficultCheckbox)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }
       if(soDifficultCheckboxNum>parseInt(soDifficultCheckbox)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }
       if(easyJudgeNum>parseInt(easyJudge)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }
       if(soEasyJudgeNum>parseInt(soEasyJudge)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }
       if(mediumJudgeNum>parseInt(mediumJudge)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }
       if(difficultJudgeNum>parseInt(difficultJudge)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }
       if(soDifficultJudgeNum>parseInt(soDifficultJudge)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }

       if(easyJiandaNum>parseInt(easyJianda)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }
       if(soEasyJiandaNum>parseInt(soEasyJianda)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }
       if(mediumJiandaNum>parseInt(mediumJianda)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }
       if(difficultJiandaNum>parseInt(difficultJianda)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }
       if(soDifficultJiandaNum>parseInt(soDifficultJianda)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }

       if(easyProgramNum>parseInt(easyProgram)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }
       if(soEasyProgramNum>parseInt(soEasyProgram)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }
       if(mediumProgramNum>parseInt(mediumProgram)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }
       if(difficultProgramNum>parseInt(difficultProgram)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }
       if(soDifficultProgramNum>parseInt(soDifficultProgram)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }

       if(easyBlankNum>parseInt(easyBlank)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }
       if(soEasyBlankNum>parseInt(soEasyBlank)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }
       if(mediumBlankNum>parseInt(mediumBlank)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }
       if(difficultBlankNum>parseInt(difficultBlank)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }
       if(soDifficultBlankNum>parseInt(soDifficultBlank)){
           $.modal.alertError("不能超过题库数量");
           error=1;
       }

       let choiceScore = document.getElementById("choiceScore").value;
       let checkboxScore = document.getElementById("checkboxScore").value;
       let judgeScore = document.getElementById("judgeScore").value;
       let jiandaScore = document.getElementById("jiandaScore").value;
       let programScore = document.getElementById("programScore").value;
       let blankScore = document.getElementById("blankScore").value;


       $("#choiceSum").text((parseInt(easyChoiceNum,10)+parseInt(soEasyChoiceNum,10)+parseInt(mediumChoiceNum,10)+parseInt(difficultChoiceNum,10)+parseInt(soDifficultChoiceNum,10))*choiceScore+'分');
        $("#checkboxSum").text((parseInt(easyCheckboxNum,10)+parseInt(soEasyCheckboxNum,10)+parseInt(mediumCheckboxNum,10)+parseInt(difficultCheckboxNum,10)+parseInt(soDifficultCheckboxNum,10))*checkboxScore+'分');
        $("#judgeSum").text((parseInt(easyJudgeNum,10)+parseInt(soEasyJudgeNum,10)+parseInt(mediumJudgeNum,10)+parseInt(difficultJudgeNum,10)+parseInt(soDifficultJudgeNum,10))*judgeScore+'分');
        $("#jiandaSum").text((parseInt(easyJiandaNum,10)+parseInt(soEasyJiandaNum,10)+parseInt(mediumJiandaNum,10)+parseInt(difficultJiandaNum,10)+parseInt(soDifficultJiandaNum,10))*jiandaScore+'分');
        $("#programSum").text((parseInt(easyProgramNum,10)+parseInt(soEasyProgramNum,10)+parseInt(mediumProgramNum,10)+parseInt(difficultProgramNum,10)+parseInt(soDifficultProgramNum,10))*programScore+'分');
        $("#blankSum").text((parseInt(easyBlankNum,10)+parseInt(soEasyBlankNum,10)+parseInt(mediumBlankNum,10)+parseInt(difficultBlankNum,10)+parseInt(soDifficultBlankNum,10))*blankScore+'分');
        choiceSum =(parseInt(easyChoiceNum,10)+parseInt(soEasyChoiceNum,10)+parseInt(mediumChoiceNum,10)+parseInt(difficultChoiceNum,10)+parseInt(soDifficultChoiceNum,10))*choiceScore;
       checkboxSum = (parseInt(easyCheckboxNum,10)+parseInt(soEasyCheckboxNum,10)+parseInt(mediumCheckboxNum,10)+parseInt(difficultCheckboxNum,10)+parseInt(soDifficultCheckboxNum,10))*checkboxScore;
       judgeSum = (parseInt(easyJudgeNum,10)+parseInt(soEasyJudgeNum,10)+parseInt(mediumJudgeNum,10)+parseInt(difficultJudgeNum,10)+parseInt(soDifficultJudgeNum,10))*judgeScore;
       jiandaSum = (parseInt(easyJiandaNum,10)+parseInt(soEasyJiandaNum,10)+parseInt(mediumJiandaNum,10)+parseInt(difficultJiandaNum,10)+parseInt(soDifficultJiandaNum,10))*jiandaScore;
       programSum = (parseInt(easyProgramNum,10)+parseInt(soEasyProgramNum,10)+parseInt(mediumProgramNum,10)+parseInt(difficultProgramNum,10)+parseInt(soDifficultProgramNum,10))*programScore;
        blankSum = (parseInt(easyBlankNum,10)+parseInt(soEasyBlankNum,10)+parseInt(mediumBlankNum,10)+parseInt(difficultBlankNum,10)+parseInt(soDifficultBlankNum,10))*blankScore;
       let sumScore;
       let sumSubject
       sumScore=choiceSum+checkboxSum+judgeSum+jiandaSum+programSum+blankSum;
       sumSubject=
           parseInt(easyChoiceNum,10)+parseInt(soEasyChoiceNum,10)+parseInt(mediumChoiceNum,10)+parseInt(difficultChoiceNum,10)+parseInt(soDifficultChoiceNum,10)+
           parseInt(easyCheckboxNum,10)+parseInt(soEasyCheckboxNum,10)+parseInt(mediumCheckboxNum,10)+parseInt(difficultCheckboxNum,10)+parseInt(soDifficultCheckboxNum,10)+
           parseInt(easyJudgeNum,10)+parseInt(soEasyJudgeNum,10)+parseInt(mediumJudgeNum,10)+parseInt(difficultJudgeNum,10)+parseInt(soDifficultJudgeNum,10)+
           parseInt(easyJiandaNum,10)+parseInt(soEasyJiandaNum,10)+parseInt(mediumJiandaNum,10)+parseInt(difficultJiandaNum,10)+parseInt(soDifficultJiandaNum,10)+
           parseInt(easyProgramNum,10)+parseInt(soEasyProgramNum,10)+parseInt(mediumProgramNum,10)+parseInt(difficultProgramNum,10)+parseInt(soDifficultProgramNum,10)+
           parseInt(easyBlankNum,10)+parseInt(soEasyBlankNum,10)+parseInt(mediumBlankNum,10)+parseInt(difficultBlankNum,10)+parseInt(soDifficultBlankNum,10)
        $("#sumScore").text(parseInt(sumScore));
        $("#sumSubject").text(parseInt(sumSubject));
    }
    var thistid = "${tid}";
    var setting = {
        view: {
            selectedMulti: false
        },
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onCheck: onCheck
        }
    };
    var zNodesOne = [];

    var clearFlag = false;
    function onCheck(e, treeId, treeNode) {
        count();
        if (clearFlag) {
            clearCheckedOldNodesOne();
        }
    }
    function clearCheckedOldNodesOne() {
        var zTree = $.fn.zTree.getZTreeObj("treeDemoOne"),
            nodes = zTree.getChangeCheckedNodes();
        for (var i = 0, l = nodes.length; i < l; i++) {
            nodes[i].checkedOld = nodes[i].checked;
        }
    }
    function count() {
        var zTree = $.fn.zTree.getZTreeObj("treeDemoOne"),
            zTree
        checkCount = zTree.getCheckedNodes(true).length,
            nocheckCount = zTree.getCheckedNodes(false).length,
            changeCount = zTree.getChangeCheckedNodes().length;
        $("#checkCountOne").text(checkCount);
        $("#nocheckCountOne").text(nocheckCount);
        $("#changeCount").text(changeCount);

    }
    function createTreeOne() {
        $.fn.zTree.init($("#treeDemoOne"), setting, zNodesOne);
        count();
        clearFlag = $("#last").attr("checked");
    }
    $(document).ready(function () {
        createTreeOne();
        $("#init").bind("change", createTreeOne);
        $("#last").bind("change", createTreeOne);

        $.ajax({
            type: 'GET',
            url: '/teacher/chapter/chapterTreeData/' + cId,
            dataType: 'json',
            success: function (data) {
                for (let i = 0; i < data.length; i++) {
                    let datavalue = {};
                    datavalue["id"] = data[i].id;
                    datavalue["pId"] = data[i].pId;
                    datavalue["name"] = data[i].name;
                    datavalue["open"] = "true";
                    zNodesOne.push(datavalue);
                }
                $.fn.zTree.init($("#treeDemoOne"), setting, zNodesOne);
            }

        });

    });
    function savemenu() {
        var treeObj = $.fn.zTree.getZTreeObj("treeDemoOne");
        var sNodes = treeObj.getSelectedNodes();
        var treeObj = $.fn.zTree.getZTreeObj("treeDemoOne");
        var nodes = treeObj.getCheckedNodes(true);
        document.getElementById("chapterName").innerHTML = '';
        for (let i = nodes.length; i > 0; i--) {
            if (nodes[i - 1].isParent != true) {
                document.getElementById("chapterName").innerHTML += nodes[i - 1].name + '<br>';
                chapterId += nodes[i - 1].id + ",";
            }
        }
        if (chapterId == "") {
            $.modal.alertError("请选好知识点后点击添加知识点");
        } else {
            $.ajax({
                type: 'POST',
                url: '/school/onlineExam/testPaperOneList/questionNum?chapterIds=' + chapterId + "&&cid="+cId+"&&tid=" + thistid,
                dataType: 'json',
                success: function (data) {

                    let choicenum = 0;
                    let easyChoiceNum = 0;
                    let soEasyChoiceNum = 0;
                    let mediumChoiceNum = 0;
                    let difficultChoiceNum = 0;
                    let soDifficultChoiceNum = 0;
                    let checkboxnum = 0;
                    let easyCheckboxNum = 0;
                    let soEasyCheckboxNum = 0;
                    let mediumCheckboxNum = 0;
                    let difficultCheckboxNum = 0;
                    let soDifficultCheckboxNum = 0;
                    let judgenum = 0;
                    let easyJudgeNum = 0;
                    let soEasyJudgeNum = 0;
                    let mediumJudgeNum = 0;
                    let difficultJudgeNum = 0;
                    let soDifficultJudgeNum= 0;
                    let jiandanum = 0;
                    let easyJiandaNum= 0;
                    let soEasyJiandaNum= 0;
                    let mediumJiandaNum = 0;
                    let difficultJiandaNum = 0;
                    let soDifficultJiandaNum = 0;
                    let programnum = 0;
                    let easyProgramNum = 0;
                    let soEasyProgramNum = 0;
                    let mediumProgramNum= 0;
                    let difficultProgramNum = 0;
                    let soDifficultProgramNum = 0;
                    let blanknum = 0;
                    let easyBlankNum = 0;
                    let soEasyBlankNum = 0;
                    let mediumBlankNum = 0;
                    let difficultBlankNum= 0;
                    let soDifficultBlankNum = 0;
                for (let i = 0; i <data.length; i++) {
                     for (let k = 0; k <data[i].length ; k++) {
                         if ("01,05".includes(data[i][k].titleTypeNum)){ //单选题
                                choicenum++;
                            if (data[i][k].difficulty=='1'){
                                soEasyChoiceNum++;
                            }
                            if (data[i][k].difficulty=='2'){
                                easyChoiceNum++;
                            }
                            if (data[i][k].difficulty=='3'){
                                mediumChoiceNum++;
                            }
                            if (data[i][k].difficulty=='4'){
                                difficultChoiceNum++;
                            }
                            if (data[i][k].difficulty=='5'){
                                soDifficultChoiceNum++;
                            }
                         }
                         if ("02,03".includes(data[i][k].titleTypeNum)){ //多选题
                                checkboxnum++;
                            if (data[i][k].difficulty=='1'){
                                soEasyCheckboxNum++;
                            }
                            if (data[i][k].difficulty=='2'){
                                easyCheckboxNum++;
                            }
                            if (data[i][k].difficulty=='3'){
                                mediumCheckboxNum++;
                            }
                            if (data[i][k].difficulty=='4'){
                                difficultCheckboxNum++;
                            }
                            if (data[i][k].difficulty=='5'){
                                soDifficultCheckboxNum++;
                            }
                         }
                         if ("04".includes(data[i][k].titleTypeNum)){//填空题
                             blanknum++;
                             if (data[i][k].difficulty=='1'){
                                 soEasyBlankNum++;
                             }
                             if (data[i][k].difficulty=='2'){
                                 easyBlankNum++;
                             }
                             if (data[i][k].difficulty=='3'){
                                 mediumBlankNum++;
                             }
                             if (data[i][k].difficulty=='4'){
                                 difficultBlankNum++;
                             }
                             if (data[i][k].difficulty=='5'){
                                 soDifficultBlankNum++;
                             }
                         }
                         if ("06".includes(data[i][k].titleTypeNum)){ //判断题
                                judgenum++;
                            if (data[i][k].difficulty=='1'){
                                soEasyJudgeNum++;
                            }
                            if (data[i][k].difficulty=='2'){
                                easyJudgeNum++;
                            }
                            if (data[i][k].difficulty=='3'){
                                mediumJudgeNum++;
                            }
                            if (data[i][k].difficulty=='4'){
                                difficultJudgeNum++;
                            }
                            if (data[i][k].difficulty=='5'){
                                soDifficultJudgeNum++;
                            }
                         }
                         if ("07".includes(data[i][k].titleTypeNum)){//简答题
                             jiandanum++;
                             if (data[i][k].difficulty=='1'){
                                 soEasyJiandaNum++;
                             }
                             if (data[i][k].difficulty=='2'){
                                 easyJiandaNum++;
                             }
                             if (data[i][k].difficulty=='3'){
                                 mediumJiandaNum++;
                             }
                             if (data[i][k].difficulty=='4'){
                                 difficultJiandaNum++;
                             }
                             if (data[i][k].difficulty=='5'){
                                 soDifficultJiandaNum++;
                             }
                         }
                         if ("08,09,10,11,12".includes(data[i][k].titleTypeNum)){//主观题,编程题
                                programnum++;
                            if (data[i][k].difficulty=='1'){
                                soEasyProgramNum++;
                            }
                            if (data[i][k].difficulty=='2'){
                                easyProgramNum++;
                            }
                            if (data[i][k].difficulty=='3'){
                                mediumProgramNum++;
                            }
                            if (data[i][k].difficulty=='4'){
                                difficultProgramNum++;
                            }
                            if (data[i][k].difficulty=='5'){
                                soDifficultProgramNum++;
                            }
                         }
                        }
                    }
                    $("#choice").text(choicenum);
                    $("#soEasyChoice").text(soEasyChoiceNum);
                    $("#easyChoice").text(easyChoiceNum);
                    $("#mediumChoice").text(mediumChoiceNum);
                    $("#difficultChoice").text(difficultChoiceNum);
                    $("#soDifficultChoice").text(soDifficultChoiceNum);
                    if (easyChoiceNum!=0){
                        $("#easyChoiceNum").attr('disabled',false);
                    }
                    if (soEasyChoiceNum!=0){
                        $("#soEasyChoiceNum").attr('disabled',false);
                    }
                    if (mediumChoiceNum!=0){
                        $("#mediumChoiceNum").attr('disabled',false);
                    }
                    if (difficultChoiceNum!=0){
                        $("#difficultChoiceNum").attr('disabled',false);
                    }
                    if (soDifficultChoiceNum!=0){
                        $("#soDifficultChoiceNum").attr('disabled',false);
                    }
                    $("#checkbox").text(checkboxnum);
                    $("#easyCheckbox").text(easyCheckboxNum);
                    $("#soEasyCheckbox").text(soEasyCheckboxNum);
                    $("#mediumCheckbox").text(mediumCheckboxNum);
                    $("#difficultCheckbox").text(difficultCheckboxNum);
                    $("#soDifficultCheckbox").text(soDifficultCheckboxNum);
                    if (easyCheckboxNum!=0){
                        $("#easyCheckboxNum").attr('disabled',false);
                    }
                    if (soEasyCheckboxNum!=0){
                        $("#soEasyCheckboxNum").attr('disabled',false);
                    }
                    if (mediumCheckboxNum!=0){
                        $("#mediumCheckboxNum").attr('disabled',false);
                    }
                    if (difficultCheckboxNum!=0){
                        $("#difficultCheckboxNum").attr('disabled',false);
                    }
                    if (soDifficultCheckboxNum!=0){
                        $("#soDifficultCheckboxNum").attr('disabled',false);
                    }
                    $("#judge").text(judgenum);
                    $("#easyJudge").text(easyJudgeNum);
                    $("#soEasyJudge").text(soEasyJudgeNum);
                    $("#mediumJudge").text(mediumJudgeNum);
                    $("#difficultJudge").text(difficultJudgeNum);
                    $("#soDifficultJudge").text(soDifficultJudgeNum);
                    if (easyJudgeNum!=0){
                        $("#easyJudgeNum").attr('disabled',false);
                    }
                    if (soEasyJudgeNum!=0){
                        $("#soEasyJudgeNum").attr('disabled',false);
                    }
                    if (mediumJudgeNum!=0){
                        $("#mediumJudgeNum").attr('disabled',false);
                    }
                    if (difficultJudgeNum!=0){
                        $("#difficultJudgeNum").attr('disabled',false);
                    }
                    if (soDifficultJudgeNum!=0){
                        $("#soDifficultJudgeNum").attr('disabled',false);
                    }
                    $("#jianda").text(jiandanum);
                    $("#easyJianda").text(easyJiandaNum);
                    $("#soEasyJianda").text(soEasyJiandaNum);
                    $("#mediumJianda").text(mediumJiandaNum);
                    $("#difficultJianda").text(difficultJiandaNum);
                    $("#soDifficultJianda").text(soDifficultJiandaNum);
                    if (easyJiandaNum!=0){
                        $("#easyJiandaNum").attr('disabled',false);
                    }
                    if (soEasyJiandaNum!=0){
                        $("#soEasyJiandaNum").attr('disabled',false);
                    }
                    if (mediumJiandaNum!=0){
                        $("#mediumJiandaNum").attr('disabled',false);
                    }
                    if (difficultJiandaNum!=0){
                        $("#difficultJiandaNum").attr('disabled',false);
                    }
                    if (soDifficultJiandaNum!=0){
                        $("#soDifficultJiandaNum").attr('disabled',false);
                    }
                    $("#program").text(programnum);
                    $("#easyProgram").text(easyProgramNum);
                    $("#soEasyProgram").text(soEasyProgramNum);
                    $("#mediumProgram").text(mediumProgramNum);
                    $("#difficultProgram").text(difficultProgramNum);
                    $("#soDifficultProgram").text(soDifficultProgramNum);
                    if (easyProgramNum!=0){
                        $("#easyProgramNum").attr('disabled',false);
                    }
                    if (soEasyProgramNum!=0){
                        $("#soEasyProgramNum").attr('disabled',false);
                    }
                    if (mediumProgramNum!=0){
                        $("#mediumProgramNum").attr('disabled',false);
                    }
                    if (difficultProgramNum!=0){
                        $("#difficultProgramNum").attr('disabled',false);
                    }
                    if (soDifficultProgramNum!=0){
                        $("#soDifficultProgramNum").attr('disabled',false);
                    }
                    $("#blank").text(blanknum);
                    $("#easyBlank").text(easyBlankNum);
                    $("#soEasyBlank").text(soEasyBlankNum);
                    $("#mediumBlank").text(mediumBlankNum);
                    $("#difficultBlank").text(difficultBlankNum);
                    $("#soDifficultBlank").text(soDifficultBlankNum);
                    if (easyBlankNum!=0){
                        $("#easyBlankNum").attr('disabled',false);
                    }
                    if (soEasyBlankNum!=0){
                        $("#soEasyBlankNum").attr('disabled',false);
                    }
                    if (mediumBlankNum!=0){
                        $("#mediumBlankNum").attr('disabled',false);
                    }
                    if (difficultBlankNum!=0){
                        $("#difficultBlankNum").attr('disabled',false);
                    }
                    if (soDifficultBlankNum!=0){
                        $("#soDifficultBlankNum").attr('disabled',false);
                    }
                },
                error: function () {
                    alert("获取数据失败!");
                }
            });

        }
    }
    function submitHandler() {
        if(error==1){
            $.modal.alertError("选题数量有误，请重新分配！");
        }
        if ((choiceSum+checkboxSum+judgeSum+jiandaSum+programSum+blankSum)<0){
            $.modal.alertError("总分数需大于0，请重新分配！");
        }else if ($.validate.form()) {
            $.operate.saveModal(prefix + "/question/add/${id}/${tid}/${cid}?chapterIds="+chapterId, $('#form-userExam-add').serialize());

        }
    }
</script>
</body>
