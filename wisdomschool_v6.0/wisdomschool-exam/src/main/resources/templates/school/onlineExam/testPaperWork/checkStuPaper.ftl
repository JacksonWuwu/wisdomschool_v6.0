<#include "/common/style.ftl"/>
<link href="/js/plugins/ueditor/themes/iframe.css" rel="stylesheet" />
<div id="divpx2">
    <div class="col-sm-12 select-table table-striped" style="width: 50%;left:25%" id="piyue">

        <div class="form-horizontal" id="startexamform">
            <center>
                <h1>${testPaper.headline}
                </h1>
            </center>
            <center>
                <h3>${testPaper.testName}
                </h3>
            </center>
            <center>
                <h5>学号:${stuName} 姓名:${sysUser.userName}</h5>
            </center>
            <!-- js生成试卷内容 -->
            <button class="btn btn-info ipt" type="button" name="b_print"
                    onClick="printdiv('piyue');"
                    value=" Print ">
                <i class="ace-icon fa fa-check bigger-110"></i>
                打印全文
            </button>
            <#--            <div>总分：</div>-->
            <#--            <div id="stuScore"></div>-->
            <div id="content">
            </div>
            <div class="btn-group btn-corner" style="width:100%; " id="sumb">
            </div>

            <div class="btn-group btn-corner" style="width:100%; ">
                <button id="submit" class="btn btn-sm btn-danger" style="width:100%; "
                        type="submit" id="addRole" onclick="submitsave ()">
                    <i class="ace-icon glyphicon glyphicon-check bigger-120"></i>
                    &nbsp;提&nbsp;&nbsp;交&nbsp;&nbsp;批&nbsp;&nbsp;阅
                </button>
                <br/>

            </div>
        </div>
    </div>
    <div id="right-menu" class="modal aside" data-body-scroll="false"
         data-offset="true" data-placement="right" data-fixed="true"
         data-backdrop="invisible" tabindex="-1">
        <div class="modal-dialog" style="top: 0px;">
            <div class="modal-content">
                <div class="modal-header no-padding">
                    <div class="table-header btn-info">
                        <button type="button" class="close" data-dismiss="modal"
                                aria-hidden="true">
                            <span class="white">&times;</span>
                        </button>
                        页面目录
                    </div>
                </div>

                <div class="modal-body">
                    <div id="menu"
                         style="margin-left:auto;top:auto;left:auto; position:inherit;">
                        <div id="accordion"
                             class="accordion-style1 panel-group accordion-style2">
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.modal-content -->
            <button
                    class="aside-trigger btn btn-info btn-app btn-xs ace-settings-btn"
                    data-target="#right-menu" data-toggle="modal" type="button">
                <i data-icon1="fa-plus" data-icon2="fa-minus"
                   class="ace-icon fa fa-search-plus bigger-110 icon-only"></i>
            </button>
        </div>
        <!-- /.modal-dialog -->
    </div>
</div>
<#include "/common/stretch.ftl"/>
<!-- 配置文件 -->
<script type="text/javascript" src="/js/plugins/ueditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="/js/plugins/ueditor/ueditor.all.js"></script>
<script type="text/javascript" src="/js/plugins/ueditor/ueditor.parse.js"></script>
<script type="text/javascript"  src="/js/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
<script>
    var tqg = 0;//是否有答案标记
    var ansList = "";//试题答案
    var stuAnsList = "";//学生答案
    var scoreList;//分数
    var paperId = "${paperId}";
    var cid = "${chapterId}";
    var ckeditor1 = "";
    var myid;
    var ws;
    var anwserstr;
    var str = "";//题型标记
    //答案相关数组
    var questionnumArray = new Array(100);
    var answerstrArray = new Array(100);
    var questionidArray = new Array(100);
    var anwseridArray = new Array(100);
    var studentId = "${studentId}";
    var sumScore = new Array(100);
    $(function () {
        initPaper();
    });

    function initPaper() {
        $.ajax({
            type: "post",
            dataType: 'json',
            url: "/school/onlineExam/testPaperWork/startMakePaper?paperId=" + paperId + "&&studentId=" + studentId,
            error: function (request) {
                $.modal.alertError("初始化试卷失败!");
            },
            success: function (data) {
                console.log(data)
                //目录部分
                var numitem = 0;
                var onesum_mulu = 0;
                stuOptionExamanswerIdArray = new Array();
                for (var i = 0; i < data.length; i++) {
                    if (data[i].stuOptionExamanswerList.length>0) {
                        initStuOptionId(data[i].stuOptionExamanswerList[0].id);
                    }
                    numitem += 1;
                    if (data[i].titleTypeName != str) {
                        onesum_mulu += 1;
                        str = data[i].titleTypeName;
                        var shuzi = Arabia_To_SimplifiedChinese(onesum_mulu);
                        var tQuestiontemplateNum = data[i].tQuestiontemplateNum;
                        $('#accordion').append('<div class="panel panel-default">' +
                            '<div class="panel-heading">' +
                            ' <h4 class="panel-title">' +
                            '<a class="accordion-toggle" data-toggle="collapse"' +
                            'data-parent="#accordion"' +
                            ' href="#' + tQuestiontemplateNum + '">' + shuzi + "、" + data[i].titleTypeName +
                            ' <i class="ace-icon fa fa-chevron-down pull-right"   data-icon-hide="ace-icon fa fa-chevron-down" data-icon-show="ace-icon fa fa-chevron-left">' +
                            '</i>' +
                            '</a></h4>' +
                            '');
                        var qid3 = data[i].id;
                        $('#accordion').append('<div class="panel-collapse collapse in"' +
                            'id="' + data[i].titleTypeName + '"' +
                            '<ul style="margin: inherit;">' +
                            '');
                        numitem = 0;
                        for (let j = 0; j < data.length; j++) {
                            numitem += 1;
                            var qid4 = data[j].id;
                            if (data[i].titleTypeName == data[j].titleTypeName) {

                                $('#accordion').append('<li style="list-style: none" ><a href="#' + qid4 + '">' +
                                    '<div style="font-weight:normal;height: 20px; max-width:210px; wioverflow:hidden; text-overflow:ellipsis;white-space:nowrap;overflow:hidden">' +
                                    '<span>' + numitem + "、" + data[j].title +
                                    '</span> </div></a></li>' +
                                    '</ul>  </div>  </div>' +
                                    '');
                            }
                        }

                    }
                }
                //题目部分
                var onesum = 0;
                var num = 0;
                var sum = 1;
                var str = "";
                for (let i = 0; i < data.length; i++) {
                    num += 1;

                    var tQuestiontemplateNum = "'" + data[i].tQuestiontemplateNum + "'";
                    var dataId = "input"+data[i].id;
                    if (data[i].title!== "") {
                        onesum += 1;
                        var simplifiedChinese = Arabia_To_SimplifiedChinese(onesum);
                        str = data[i].titleTypeName;
                        var qid = data[i].id;
                        let subjectId="subject"+data[i].id;//题目的id，用于ueditor初始化题目
                        $('#content').append('<div id="' + data[i].tQuestiontemplateNum + '"  class="item dd2-content btn-info no-hover">' +
                            '<strong>' + simplifiedChinese + '、' + data[i].titleTypeName +
                            '</strong></div>' +
                            '  <div id="' + qid + '"' +
                            'class="item page-content">' +
                            '<h5><strong><div>' +
                            '<textarea id="'+subjectId+'"></textarea>'+
                            '</div>' +
                            '</div> </strong></h5><div><ul>');
                        initSubjectUeditor(subjectId,data[i].title);
                    }
                    //单选
                    if (data[i].tQuestiontemplateNum == "1") {
                        var flag = 64;
                        ansList = data[i].testpaperOptinanswerList;
                        stuAnsList = data[i].stuOptionExamanswerList;
                        let stuIstrue = false;
                        let stuScore = 0;
                        let StuAnswer = 0;
                        let parseStuAnsList = [];
                        let parseStuAns = [];
                        let stuGetScore = "";
                        if (stuAnsList.length>0) {
                            parseStuAnsList = stuAnsList[0].stuAnswer.split(";");
                            parseStuAns = stuAnsList[0].stuAnswer.split(";");
                            stuGetScore = stuAnsList[0].questionScore;
                        }

                        for (var k = 0; k < parseStuAns.length; k++) {
                            if (parseStuAns[k] == '0') {

                            } else if (parseStuAns[k] == '') {

                            } else {
                                StuAnswer = parseStuAns[k];
                            }
                        }
                        for (let j = 0; j < ansList.length; j++) {
                            if (parseStuAns.length != 0) {
                                if ((StuAnswer == ansList[j].stanswer) && (parseStuAns[j] != "")) {
                                    stuIstrue = true;
                                }
                            }
                        }
                        let thisId1 = "";
                        for (let j = 0; j < ansList.length; j++) {
                            thisId1 = ansList[j].id;
                            flag += 1;
                            var paperAns = "";
                            var trueAns = "";
                            var isCheck = "";
                            var stutrueAns = "green";
                            if (parseStuAnsList.length != 0 && parseStuAnsList.length >= ansList.length) {
                                if (parseStuAnsList[j] != '0') {
                                    paperAns = 'checked';
                                }
                            }
                            if (parseStuAnsList.length != 0 && parseStuAnsList.length >= ansList.length) {
                                if ((parseStuAnsList[j] == '0') && (ansList[j].stanswer != '0')) {
                                    trueAns = 'red';
                                }
                                if ((parseStuAnsList[j] != '0') && (ansList[j].stanswer == '0')) {
                                    stutrueAns = '';
                                }
                                if (parseStuAnsList[j] != '0') {
                                    isCheck = 'checked';
                                }
                            }
                            var fla = String.fromCharCode(flag);
                            var anlId = "'" + ansList[j].id + "'";
                            var anlIdscore = "'" + ansList[j].id + "'";
                            //
                            $('#content').append('' +
                                '<li  style="list-style: none" class="' + trueAns + "  " + stutrueAns +
                                '">' +
                                '<div class="divhuiactive row" id=" ' + data[i].id + '">' +
                                '<div class="radio" style="padding-bottom: 7px;">' +
                                '</li></div></div>');
                            if ((ansList[j].stanswer != "") && (ansList[j].stanswer != "0")) {
                                $('#content').append(
                                    '<label>' +
                                    '<input style="color: #0ab5f7;" name="form-field-radio' + num + '520"' +
                                    ' id="answerId' + anlId + '"' +
                                    'type="radio" ' +
                                    'title="' + data[i].id + '"' + ansList[j].stanswer +
                                    'value="' + fla + '"' +
                                    'class="ace "' +
                                    'disabled readonly="true"' +
                                    '' + 'checked="checked">' +
                                    '<span class="lbl"></span> </label></div></div></li>' +
                                    '');
                            } else {
                                $('#content').append(
                                    '<label>' +
                                    '<input style="color: #0ab5f7;" name="form-field-radio' + num + '520"' +
                                    ' id="answerId' + anlId + '"' +
                                    'type="radio" ' +
                                    'title="' + data[i].id + '"' + ansList[j].stanswer +
                                    'value="' + fla + '"' +
                                    'class="ace"' +
                                    'disabled readonly="true">' +
                                    '<span class="lbl"></span><span>' +
                                    '</span> </label></div></div></li>' +
                                    '')
                            }
                            $('#content').append(
                                '<input style="color: #95f71c;" name="form-field-radio' + num + '"' +
                                'type="radio"' +
                                'title="' + data[i].id + '"' +
                                ' class="ace"' +
                                'disabled readonly="true"' +
                                'value="' + fla + '" ' + isCheck + '>' +
                                ' <span class="lbl">' + fla + '.' + ansList[j].stoption +
                                '</span></div></div></li>');

                        }



                        if (stuIstrue == true) {
                            stuScore = data[i].questionScore;
                        }
                        else {
                            stuScore = "0";
                        }

                        let stuoptionid=""
                        if (stuAnsList.length>0) {
                            stuoptionid=stuAnsList[0].id
                        }
                        $('#content').append(
                            '<li style="list-style: none"><div id="hint" class="divhuiactive row" style="max-width: 685px;">' +
                            '<div class="alert alert-info no-margin">' +
                            '<div>以上选项左边为正确答案，右边为考生答案</div> ' +
                            '<div style="margin-top: 10px;">该题分值：' + data[i].questionScore + "分" +
                            '&nbsp;&nbsp;&nbsp;&nbsp;得分：' +
                            ' <input name="' + stuoptionid + '" oninput="value=value.replace(/[^\\d]/g,\'\')"  value="' + stuScore +
                            '"onchange="anwserchange(' + tQuestiontemplateNum + ",'"+dataId +"'," + data[i].questionScore + ",'"+ stuoptionid +"'"+')" id="' + dataId + '"' +
                            '/>' +
                            '&nbsp;&nbsp;分</div> </div></div> </li></div></div></li>' +
                            '');
                        sumScore[i] += stuScore;
                    }
                    /**
                     * 双选
                     */
                    if (data[i].tQuestiontemplateNum == "2") {
                        var flag = 64;
                        ansList = data[i].testpaperOptinanswerList;
                        stuAnsList = data[i].stuOptionExamanswerList;
                        let stuIstrue2 = true;
                        let stuScore2 = "0";
                        let standardAnswer=""
                        for (let j = 0; j < ansList.length; j++) {
                            if (stuAnsList.length != 0) {
                                if (stuAnsList[0].stuAnswer.indexOf(ansList[j].stanswer) === -1) {
                                    stuIstrue2 = false;
                                }
                            }
                        }
                        let thisId = "";
                        for (let j = 0; j < ansList.length; j++) {
                            thisId = ansList[j].id;
                            standardAnswer+=ansList[j].stanswer+';'
                            flag += 1;
                            let trueAns = "";
                            let pdtrueAns = "";
                            if (stuAnsList.length != 0) {
                                if (stuAnsList[0].stuAnswer.indexOf(ansList[j].stanswer) !== -1 && ansList[j].stanswer !== '0') {
                                    trueAns = 'checked';
                                }
                            }
                            if (ansList[j].stanswer != '0') {
                                pdtrueAns = 'checked';
                            }
                            let fla = String.fromCharCode(flag);
                            let anlId = "'" + ansList[j].id + "'";
                            $('#content').append('<li style="list-style: none">' +
                                '<div class="divhuiactive row id=" ' + data[i].id + '"' +
                                '>' +
                                '<div class="checkbox">' +
                                '<label style="padding-left: 30">' +
                                ' <input name="form-field-checkbox' + num + '520"' +
                                ' id="answerId' + anlId + '"' +
                                ' title="' + data[i].id + '"' +
                                'value="' + fla + '"' + pdtrueAns +
                                ' type="checkbox"' +
                                'disabled readonly="true"' +
                                ' class="ace green"/>' +
                                '<span class="lbl"></span></label>' +
                                ' <label> <input name="form-field-checkbox' + num +
                                '" ' + trueAns + ' ' +
                                ' type="checkbox" ' +
                                'disabled readonly="true"' +
                                'class="ace red" /><span class="lbl">' +
                                '<span>' + fla + '.' + ansList[j].stoption +
                                ' </span></span></label></div></div></li>');
                        }

                        if (stuAnsList.length>0) {
                            if (standardAnswer === stuAnsList[0].stuAnswer) {
                                stuScore2 = data[i].questionScore;
                            }
                        }
                        let stuoptionid=""
                        if (stuAnsList.length>0) {
                            stuoptionid=stuAnsList[0].id
                        }
                        $('#content').append(
                            '<li style="list-style: none"><div id="hint" class="divhuiactive row" style="max-width: 685px;">' +
                            '<div class="alert alert-info no-margin">' +
                            '<div>以上选项左边为正确答案，右边为考生答案</div> ' +
                            '<div style="margin-top: 10px;">该题分值：' + data[i].questionScore + "分" +
                            '&nbsp;&nbsp;&nbsp;&nbsp;得分：' +
                            '  <input name="' + stuoptionid + '" oninput="value=value.replace(/[^\\d]/g,\'\')"  value="' + stuScore2 +
                            '"onchange="anwserchange(' + tQuestiontemplateNum + ",'"+dataId +"'," + data[i].questionScore + ",'"+ stuoptionid +"'"+')" id="' + dataId + '"' +
                            '/>' +
                            '&nbsp;&nbsp;分</div> </div></div> </li></div></div></li>' +
                            '');
                        sumScore[i] = stuScore2;
                    }
                    /**
                     *  填空
                     */
                    if (data[i].tQuestiontemplateNum == "3") {
                        let ansStr = "";
                        ansList = data[i].testpaperOptinanswerList;
                        stuAnsList = data[i].stuOptionExamanswerList;
                        let flag = 64;
                        let Optionnum = 0;
                        let stuScore3 = "0";
                        var parseStuAnsList = [];
                        if(stuAnsList.length>0){
                            if (typeof (stuAnsList[0].stuAnswer) !== "undefined" || stuAnsList[0].stuAnswer != null) {
                                parseStuAnsList = stuAnsList[0].stuAnswer.split(";");
                            }
                        }

                        for (let j = 0; j < ansList.length; j++) {
                            ansStr +=ansList[j].stoption+";";
                            Optionnum += 1;
                            flag += 1;
                            let trueAns = "";


                            if (parseStuAnsList.length != 0 && parseStuAnsList.length >= ansList.length) {
                                trueAns = parseStuAnsList[j];
                            }

                            let fla = String.fromCharCode(flag);
                            let anlId = "'" + ansList[j].id + "'";
                            let ewnum = Arabia_To_SimplifiedChinese(Optionnum);
                            $('#content').append('<li style="list-style: none">' +
                                '<div class="divhuiactive row" style="padding-left:40px" id=" ' + data[i].id + '"' +
                                '>' +
                                '  <div style="display:inline-block;vertical-align:top;padding-top: 15px;">' + '第' + ewnum + '空.' +
                                '</div>' +
                                '<div style="display:inline-block;vertical-align:top;max-width: 685px;padding: 10px 0;">' +
                                '<input ' +
                                'title="' + data[i].id + '"' +
                                'class="autosize-transition form-control col-md-12" readonly="readonly"' +
                                'id="answerId' + anlId + '"' +
                                'readonly="readonly"' +
                                'value="'+trueAns+'"' +
                                'font-family:' +
                                ' inherit;font-style: normal;font-weight: normal;text-align: left;">'+
                                '</input>' +
                                '</div>' +
                                ' <div class="green bigger-110">正确答案是：' + ansList[j].stoption + '</div>' +
                                '</div></li>');
                        }
                        if (stuAnsList.length>0) {
                            if(stuAnsList[0].stuAnswer===ansStr)
                                stuScore3 = stuAnsList[0].questionScore;
                        }
                        let stuoptionid=""
                        if (stuAnsList.length>0) {
                            stuoptionid=stuAnsList[0].id
                        }
                        $('#content').append('<li style="list-style: none">' +
                            '<div id="hint" class="divhuiactive row" style="max-width: 685px;">' +
                            ' <div class="alert alert-info no-margin">' +
                            '<div style="margin-top: 10px;">该题分值：' + data[i].questionScore +
                            '分&nbsp;&nbsp;&nbsp;&nbsp;得分：' +
                            '  <input name="' + stuoptionid + '" oninput="value=value.replace(/[^\\d]/g,\'\')" value="' + stuScore3 +
                            '"onchange="anwserchange(' + tQuestiontemplateNum + ",'"+dataId +"'," + data[i].questionScore + ",'"+ stuoptionid +"'"+')" id="' + dataId + '"' +
                            '/> &nbsp;&nbsp;分</div></div></div> </li>');
                        // sumScore[i] = document.getElementById(ansList[j].id).value;
                    }


                    /**
                     * 程序设计题
                     */
                    if (data[i].tQuestiontemplateNum == "5" ) {
                        let anlIdscore5 = "";
                        let flag = 64;
                        let Optionnum = 0;
                        ansList = data[i].testpaperOptinanswerList;
                        stuAnsList = data[i].stuOptionExamanswerList;
                        console.log(data[i])
                        for (let j = 0; j < ansList.length; j++) {
                            anlIdscore5 = "'" + ansList[j].id + "'";
                            Optionnum += 1;
                            flag += 1;
                            sum += 1;
                            let trueAns = "";
                            let trueAnsOne = "";
                            let stuScore5 = "0";
                            var ansType;
                            if (stuAnsList.length>0) {
                                if (stuAnsList[0].stuAnswer != null || typeof (stuAnsList[0].stuAnswer) === "undefined") {
                                    trueAns = stuAnsList[0].stuAnswer.replace(";", "");
                                    trueAnsOne = stuAnsList[0].answerType.replace(";", "");
                                    ansType = stuAnsList[j].answerType.split(";");
                                }
                            }
                            let trueAnsTwo = [];
                            trueAnsTwo = trueAnsOne.split(";");
                            let stuoptionid=""
                            if (stuAnsList.length>0) {
                                stuScore5 = stuAnsList[0].questionScore;
                                stuoptionid=stuAnsList[0].id
                            }
                            let AnsId="AnsId"+data[i].id;//题目的id，用于ueditor初始化题目
                            let trueAnsId="trueAns"+data[i].id;//题目的id，用于ueditor初始化题目

                            $('#content').append(' <li style="list-style: none">' +
                                ' <div class="divhuiactive row" id=" ' + data[i].id + '"' +
                                '>' +
                                '<div class="content" id="content1" name="' + data[i].id + '"' +
                                ' title="' + data[i].tQuestiontemplateNum + ';' + data[i].id + ';' + ansList[j].id + '">' +
                                ' <div style="display:inline-block;vertical-align:top;padding-top: 15px; padding-left:35px">' + "学生答案:" + '</div>' +
                                '<textarea id="'+AnsId+'"></textarea>');
                            initSubjectUeditor(AnsId,trueAns);
                            $('#content').append('<div id="divUL"></div></div></div></li>' +
                                '<div class="green bigger-110">正确答案是:</div>' +
                                '<textarea id="'+trueAnsId+'"></textarea>'+
                                '<li style="list-style: none"><div id="hint" class="divhuiactive row" style="max-width: 685px;">' +
                                '  <div class="alert alert-info no-margin">' +
                                ' <div style="margin-top: 10px;">该题分值：' + data[i].questionScore +
                                ' 分&nbsp;&nbsp;&nbsp;&nbsp;得分：' +
                                '<input name="' + stuoptionid + '" oninput="value=value.replace(/[^\\d]/g,\'\')" value="' + stuScore5 +
                                '"onchange="anwserchange(' + tQuestiontemplateNum + ",'"+dataId +"'," + data[i].questionScore + ",'"+ stuoptionid +"'"+')" id="' + dataId + '"' +
                                '/>&nbsp;&nbsp;分</div></div></div></li>');
                            initSubjectUeditor(trueAnsId,ansList[j].stoption);
                        }
                    }
                }

            }
        });
    }

    //题目编辑器配置
    var SubjectUeditorConfig={
        toolbars: [],
        wordCount:false,          //是否开启字数统计
        maximumWords:50000,
        elementPathEnabled : false
    };

    //题目编辑器初始化
    function initSubjectUeditor(subjectId,subject){
        var ue=UE.getEditor(subjectId,SubjectUeditorConfig);
        ue.addListener("ready", function () {
            //赋值
            ue.setContent(subject);
            ue.setDisabled();
        });
    }
    var error=0
    function  anwserchange(questionnum, questionid, qscore, stuoptionid) {
        var score=document.getElementById(questionid).value;
        if ((score > qscore) || (score < 0)) {
            $.modal.alertWarning("该题分数不合理");
            error=1;
            return false;
        }else {
            error=0;
            return true;
        }

    }

    console.log("error"+error)
    /**
     * 提交分数
     */
    var scoreArray = [];
    function submitsave() {
        if(error===1){
            $.modal.alertWarning("该题分数不合理");
            return false;
        }
        scoreArray = new Array();
        if (stuOptionExamanswerIdArray.length != 0) {
            for (var i=0; i<stuOptionExamanswerIdArray.length; i++){
                var object = {
                    'stuOptionExamanswerId': stuOptionExamanswerIdArray[i],
                    'score': $("input[name='" + stuOptionExamanswerIdArray[i] +"']").val()
                }
                scoreArray.push(object);
            }
            console.log(scoreArray);
        }

        if (confirm("您确定要提交并关闭吗？")) {
            $.ajax({
                type: "post",
                dataType: 'json',
                url: "/school/onlineExam/testPaperWork/saveScore?paperId=" + paperId + "&userId=" + "${studentId}",
                contentType: "application/json;charset-UTF-8",
                data: JSON.stringify(scoreArray),
                error: function (data) {
                    $.modal.alertError("提交失败！");
                },
                success: function (data) {
                    $.modal.alertSuccess("提交成功！");
                }
            });
        }
    }


    function testAnswer(questionnum, questionid, anwserid, anwserstr, stuoptionid, index) {
        //  去重
        if (!questionidArray.includes(questionid)) {
            questionnumArray[index] = questionnum;
            answerstrArray[index] = anwserstr;
            questionidArray[index] = questionid;
            anwseridArray[index] = anwserid;
            questiontypeArray[index] = questiontype;
            stuOptionExamanswerIdArray[index] = stuoptionid;
            tqg++;
        } else {
            var index = questionidArray.indexOf(questionid);
            questionnumArray[index] = questionnum;
            answerstrArray[index] = anwserstr;
            anwseridArray[index] = anwserid;
            questiontypeArray[index] = questiontype;
            stuOptionExamanswerIdArray[index] = stuoptionid;
        }
    }

    function initStuOptionId(optionId) {
        stuOptionExamanswerIdArray.push(optionId);
    }


    //数字转换
    //阿拉伯数字转换为简写汉字
    //转自：https://www.cnblogs.com/wangqiideal/p/5579807.html
    function Arabia_To_SimplifiedChinese(Num) {
        for (i = Num.length - 1; i >= 0; i--) {
            Num = Num.replace(",", "")//替换Num中的“,”
            Num = Num.replace(" ", "")//替换Num中的空格
        }
        if (isNaN(Num)) { //验证输入的字符是否为数字
            return;
        }
        //字符处理完毕后开始转换，采用前后两部分分别转换
        part = String(Num).split(".");
        newchar = "";
        //小数点前进行转化
        for (i = part[0].length - 1; i >= 0; i--) {
            if (part[0].length > 10) {
                //alert("位数过大，无法计算");
                return "";
            }//若数量超过拾亿单位，提示
            tmpnewchar = ""
            perchar = part[0].charAt(i);
            switch (perchar) {
                case "0":
                    tmpnewchar = "零" + tmpnewchar;
                    break;
                case "1":
                    tmpnewchar = "一" + tmpnewchar;
                    break;
                case "2":
                    tmpnewchar = "二" + tmpnewchar;
                    break;
                case "3":
                    tmpnewchar = "三" + tmpnewchar;
                    break;
                case "4":
                    tmpnewchar = "四" + tmpnewchar;
                    break;
                case "5":
                    tmpnewchar = "五" + tmpnewchar;
                    break;
                case "6":
                    tmpnewchar = "六" + tmpnewchar;
                    break;
                case "7":
                    tmpnewchar = "七" + tmpnewchar;
                    break;
                case "8":
                    tmpnewchar = "八" + tmpnewchar;
                    break;
                case "9":
                    tmpnewchar = "九" + tmpnewchar;
                    break;
            }
            switch (part[0].length - i - 1) {
                case 0:
                    tmpnewchar = tmpnewchar;
                    break;
                case 1:
                    if (perchar != 0) tmpnewchar = tmpnewchar + "十";
                    break;
                case 2:
                    if (perchar != 0) tmpnewchar = tmpnewchar + "百";
                    break;
                case 3:
                    if (perchar != 0) tmpnewchar = tmpnewchar + "千";
                    break;
                case 4:
                    tmpnewchar = tmpnewchar + "万";
                    break;
                case 5:
                    if (perchar != 0) tmpnewchar = tmpnewchar + "十";
                    break;
                case 6:
                    if (perchar != 0) tmpnewchar = tmpnewchar + "百";
                    break;
                case 7:
                    if (perchar != 0) tmpnewchar = tmpnewchar + "千";
                    break;
                case 8:
                    tmpnewchar = tmpnewchar + "亿";
                    break;
                case 9:
                    tmpnewchar = tmpnewchar + "十";
                    break;
            }
            newchar = tmpnewchar + newchar;
        }
        //替换所有无用汉字，直到没有此类无用的数字为止
        while (newchar.search("零零") != -1 || newchar.search("零亿") != -1 || newchar.search("亿万") != -1 || newchar.search("零万") != -1) {
            newchar = newchar.replace("零亿", "亿");
            newchar = newchar.replace("亿万", "亿");
            newchar = newchar.replace("零万", "万");
            newchar = newchar.replace("零零", "零");
        }
        //替换以“一十”开头的，为“十”
        if (newchar.indexOf("一十") == 0) {
            newchar = newchar.substr(1);
        }
        //替换以“零”结尾的，为“”
        if (newchar.lastIndexOf("零") == newchar.length - 1) {
            newchar = newchar.substr(0, newchar.length - 1);
        }
        return newchar;
    }


    $("input[type='checkbox']").click(
        function () {
            this.checked = !this.checked;
        }
    );
</script>

<script>
    function printdiv(printpage) {
        document.getElementById('divpx2').style.display = 'none';
        var headstr = "<html><head><title></title>" +
            "<style type='text/css'>" +
            "div.widget-header #widget-header{display:none;}" +
            "div:visited.border-widget-box{border: 1px solid #FFFFFF;}" +
            "div:hover.border-widget-box{border:1px solid #FFFFFF}" +
            "div:active.border-widget-box{border:1px solid #FFFFFF}" +
            "div.border-widget-box{border: 1px solid #FFFFFF;}" +
            "div.border-widget-container-col{border:1px solid #CCC}" +
            "</style>" +
            "</head><body>";
        let footstr = "</body>";
        let newstr = document.all.item(printpage).innerHTML;
        let oldstr = document.body.innerHTML;
        /* document.getElementById('widget-header').setAttribute(class, hidden); */
        document.body.innerHTML = headstr + newstr + footstr;
        window.print();
        document.body.innerHTML = oldstr;
        return false;
    }
</script>
