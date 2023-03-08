<#include "/common/style.ftl"/>
<div id="divpx2">
    <div class="col-sm-12 select-table table-striped" style="width: 50%;left:25%" id="piyue">
        <div class="form-horizontal" id="startexamform">
            <div id="content">
                <center>
                    <h1>${testPaper.headline}
                    </h1>
                </center>
                <center>
                    <h5>${testPaper.testName}</h5>
                    <h6>学生姓名：${sysUser.userName} 学号：${sysUser.loginName}</h6>
                </center>
                <!-- js生成试卷内容 -->
                <button class="btn btn-info ipt" type="button" name="b_print"
                        onClick="printdiv('piyue');"
                        value=" Print ">
                    <i class="ace-icon fa fa-check bigger-110"></i>
                    打印全文
                </button>
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
<script type="text/javascript" src="/dist/vendors/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="/js/onlineExam/js/reconnecting-websocket.min.js"></script>
<script type="text/javascript" src="/js/onlineExam/js/offline.min.js"></script>
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

    $(function () {
        initPaper();
    });
    function initPaper() {
        $.ajax({
                type: "post",
                dataType: 'json',
                url: "/school/onlineExam/examList/startMakePaper?paperId=" + paperId + "&&studentId=" + studentId,
                error: function (request) {
                    $.modal.alertError("初始化试卷失败!");
                },
                success: function (data) {
                    //目录部分
                    var str2 = "abc";
                    var numitem = 0;
                    var onesum_mulu = 0;
                    for (var i = 0; i < data.length; i++) {
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
                            for (var j = 0; j < data.length; j++) {
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
                    for (var i = 0; i < data.length; i++) {
                        console.log("data[i].tQuestiontemplateNu:"+data[i].tQuestiontemplateNu);

                        num += 1;
                        var tQuestiontemplateNum = "'" + data[i].tQuestiontemplateNum + "'";
                        var dataId = "'" + data[i].id + "'";
                        if (data[i].titleTypeName != str) {
                            onesum += 1;
                            var simplifiedChinese = Arabia_To_SimplifiedChinese(onesum);
                            str = data[i].titleTypeName;
                            var qid = data[i].id;
                            $('#content').append('<div id="' + data[i].tQuestiontemplateNum + '"  class="item dd2-content btn-info no-hover">' +
                                '<strong>' + simplifiedChinese + '、' + data[i].titleTypeName +
                                '</strong></div>' +
                                '  <div id="' + qid + '"' +
                                'class="item page-content">' +
                                '<h5><strong><div>' +
                                '<div style="display:inline-block;vertical-align:top;">' + num + '.' +
                                ' </div>' +
                                '<div style="display:inline-block;vertical-align:top;max-width: 680px;">' + data[i].title +
                                '</div></div> </strong></h5><div><ul>');
                        } else {
                            var qid2 = data[i].id;
                            $('#content').append('<div id="' + qid2 + '"' +
                                'class="item page-content">' +
                                '<h5><strong><div>' +
                                '<div style="display:inline-block;vertical-align:top;">' + num + '.' +
                                '</div>' +
                                '<div style="display:inline-block;vertical-align:top;max-width: 680px;">' + data[i].title +
                                '</div></div></strong></h5><div><ul>');
                        }

                        if (data[i].tQuestiontemplateNum == "1") {
                            var flag = 64;
                            ansList = data[i].testpaperOptinanswerList;
                            stuAnsList = data[i].coursetestStuoptionanswerList;
                            scoreList = data[i].stuQuestionScore;
                            var stuIstrue = false;
                            var stuScore = 0;
                            for (var j = 0; j < ansList.length; j++) {
                                if (stuAnsList.length != 0) {
                                    if ((stuAnsList[j].stuanswer == ansList[j].stanswer) && (stuAnsList[j].stuanswer != "")) {
                                        stuIstrue = true;
                                    }
                                }
                            }
                            var thisId1 = "";
                            for (var j = 0; j < ansList.length; j++) {
                                thisId1 = ansList[j].id;
                                flag += 1;
                                var paperAns = "";
                                var trueAns = "";
                                var isCheck = "";
                                var stutrueAns = "green";
                                if (stuAnsList.length != 0) {
                                    if (stuAnsList[j].stuanswer != '0') {
                                        paperAns = 'checked';
                                    }
                                }
                                if (stuAnsList.length != 0) {
                                    if ((stuAnsList[j].stuanswer == '0') && (ansList[j].stanswer != '0')) {
                                        trueAns = 'red';
                                    }
                                    if ((stuAnsList[j].stuanswer != '0') && (ansList[j].stanswer == '0')) {
                                        stutrueAns = '';
                                    }
                                    if (stuAnsList[j].stuanswer != '0') {
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
                                if ((ansList[j].stanswer != "")&&(ansList[j].stanswer != "0")) {
                                    $('#content').append(
                                        '<label>' +
                                        '<input style="color: #0ab5f7;" name="form-field-radio' + num + '520"' +
                                        ' id="answerId' + anlId + '"' +
                                        'type="radio" ' +
                                        'title="' + data[i].id + '"' + ansList[j].stanswer +
                                        'value="' + fla + '"' +
                                        'class="ace"' +
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
                                    'value="' + fla + '" ' + paperAns + '>' +
                                    ' <span class="lbl">' + fla + '.' + ansList[j].stoption +
                                    '</span></div></div></li>');
                            }

                            var stuGetScore = "";
                            if (scoreList != null) {
                                stuGetScore = scoreList.questionScore;
                            }
                            if (stuIstrue == true && stuGetScore == "") {
                                stuScore = data[i].questionScore;
                            } else if (stuGetScore != "") {
                                stuScore = stuGetScore;
                            } else {
                                stuScore = "0";
                            }

                            $('#content').append(
                                '<li style="list-style: none"><div id="hint" class="divhuiactive row" style="max-width: 685px;">' +
                                '<div class="alert alert-info no-margin">' +
                                '<div>以上选项左边为正确答案，右边为考生答案</div> ' +
                                '<div style="margin-top: 10px;">该题分值：' + data[i].questionScore + "分" +
                                '&nbsp;&nbsp;&nbsp;&nbsp;得分：' +
                                '  <input oninput="value=value.replace(/[^\\d]/g,\'\')"  value="' + stuScore +
                                '"onchange="anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlIdscore + ',' + data[i].questionScore + ')  " id="' + thisId1 + '"' +
                                '">' +
                                '&nbsp;&nbsp;分</div> </div></div> </li></div></div></li>' +
                                '');

                        }
                        if (data[i].tQuestiontemplateNum == "2") {
                            var flag = 64;
                            ansList = data[i].testpaperOptinanswerList;
                            stuAnsList = data[i].coursetestStuoptionanswerList;
                            scoreList = data[i].stuQuestionScore;
                            var stuIstrue2 = true;
                            var stuScore2 = 0;
                            var anlIdscore2 = "";
                            for (var j = 0; j < ansList.length; j++) {
                                if (stuAnsList.length != 0) {
                                    if (stuAnsList[j].stuanswer != ansList[j].stanswer) {
                                        stuIstrue2 = false;
                                    }
                                }
                            }
                            var thisId = "";
                            for (var j = 0; j < ansList.length; j++) {
                                thisId = ansList[j].id;
                                anlIdscore2 = "'" + ansList[j].id + "'";
                                flag += 1;
                                var trueAns = "";
                                var pdtrueAns = "";
                                if (stuAnsList.length != 0) {
                                    if (stuAnsList[j].stuanswer != '0') {
                                        trueAns = 'checked';
                                    }

                                }
                                if (ansList[j].stanswer != '0') {
                                    pdtrueAns = 'checked';
                                }
                                var fla = String.fromCharCode(flag);
                                var anlId = "'" + ansList[j].id + "'";
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


                            var stuGetScore = "";
                            if (scoreList != null) {
                                stuGetScore = scoreList.questionScore;
                            }
                            if (stuIstrue2 == true && stuGetScore == "") {
                                stuScore2 = data[i].questionScore;
                            } else if (stuGetScore != "") {
                                stuScore2 = stuGetScore;
                            } else {
                                stuScore2 = "0";
                            }

                            $('#content').append(
                                '<li style="list-style: none"><div id="hint" class="divhuiactive row" style="max-width: 685px;">' +
                                '<div class="alert alert-info no-margin">' +
                                '<div>以上选项左边为正确答案，右边为考生答案</div> ' +
                                '<div style="margin-top: 10px;">该题分值：' + data[i].questionScore + "分" +
                                '&nbsp;&nbsp;&nbsp;&nbsp;得分：' +
                                '  <input oninput="value=value.replace(/[^\\d]/g,\'\')"  value="' + stuScore2 +
                                '" onchange="anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlIdscore2 + ',' + data[i].questionScore + ')  " id="' + thisId + '"' +
                                '">' +
                                '&nbsp;&nbsp;分</div> </div></div> </li></div></div></li>' +
                                '');
                        }
                        if (data[i].tQuestiontemplateNum == "3") {
                            var anlIdscore3 = "";
                            ansList = data[i].testpaperOptinanswerList;
                            stuAnsList = data[i].coursetestStuoptionanswerList;
                            scoreList = data[i].stuQuestionScore;

                            var flag = 64;
                            var Optionnum = 0;
                            for (var j = 0; j < ansList.length; j++) {
                                anlIdscore3 = "'" + ansList[j].id + "'";
                                Optionnum += 1;
                                flag += 1;
                                var trueAns = "";
                                var stuScore3 = "0";
                                if (stuAnsList.length != 0) {
                                    if (stuAnsList[j].stuanswer != '0') {
                                        trueAns = stuAnsList[j].stuanswer;
                                    }
                                }
                                ;
                                if (scoreList != null) {
                                    stuScore3 = scoreList.questionScore;
                                } else {
                                    stuScore3 = "0";
                                }
                                var fla = String.fromCharCode(flag);
                                var anlId = "'" + ansList[j].id + "'";
                                var ewnum = Arabia_To_SimplifiedChinese(Optionnum);
                                $('#content').append('<li style="list-style: none">' +
                                    '<div class="divhuiactive row" style="padding-left:40px" id=" ' + data[i].id + '"' +
                                    '>' +
                                    '  <div style="display:inline-block;vertical-align:top;padding-top: 15px;">' + '第' + ewnum + '空.' +
                                    '</div>' +
                                    '<div style="display:inline-block;vertical-align:top;max-width: 685px;padding: 10px 0;">' +
                                    '<textarea ' +
                                    'title="' + data[i].id + '"' +
                                    'class="autosize-transition form-control" readonly="readonly"' +
                                    'id="answerId' + anlId + '"' +
                                    'readonly="readonly"' +
                                    'value="F"' +
                                    'style="width: 629px; height: 138px; margin: 0px;font-family:' +
                                    ' inherit;font-style: normal;font-weight: normal;text-align: left;">' + trueAns +
                                    '</textarea>' +
                                    '</div>' +
                                    ' <div class="green bigger-110">正确答案是：' + ansList[j].stoption + '</div>' +
                                    '</div></li>' +
                                    '<li style="list-style: none">' +
                                    '<div id="hint" class="divhuiactive row" style="max-width: 685px;">' +
                                    ' <div class="alert alert-info no-margin">' +
                                    '<div style="margin-top: 10px;">该题分值：' + data[i].questionScore +
                                    '分&nbsp;&nbsp;&nbsp;&nbsp;得分：' +
                                    '  <input oninput="value=value.replace(/[^\\d]/g,\'\')" value="' + stuScore3 +
                                    '"' +
                                    'onchange="anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlIdscore3 + ',' + data[i].questionScore + ')  " id="' + ansList[j].id + '"' +
                                    '> &nbsp;&nbsp;分</div></div></div> </li>');
                            }
                        }
                        if (data[i].tQuestiontemplateNum == "4") {
                            var anlIdscore4 = "";
                            var flag = 64;
                            var Optionnum = 0;
                            ansList = data[i].testpaperOptinanswerList;
                            stuAnsList = data[i].coursetestStuoptionanswerList;
                            scoreList = data[i].stuQuestionScore;

                            var pdIstrue = false;
                            var stupdScore = 0;
                            for (var j = 0; j < ansList.length; j++) {
                                if (stuAnsList.length != 0) {
                                    if (stuAnsList[j].stuanswer == ansList[j].stanswer) {
                                        pdIstrue = true;
                                    }
                                }
                            }
                            for (var j = 0; j < ansList.length; j++) {
                                anlIdscore4 = "'" + ansList[j].id + "'";
                                Optionnum += 1;
                                flag += 1;
                                var trueAns = "";
                                if (stuAnsList.length != 0) {
                                    if (stuAnsList[j].stuanswer == 'T') {
                                        trueAns = 'checked';
                                    }

                                }
                                var isAns = "×";
                                if (ansList[j].stanswer == "T") {
                                    isAns = "√";
                                }
                                var stuGetScore = "";
                                if (scoreList != null) {
                                    stuGetScore = scoreList.questionScore;
                                }
                                if (pdIstrue == true && stuGetScore == "") {
                                    stupdScore = data[i].questionScore;
                                } else if (stuGetScore != "") {
                                    stupdScore = stuGetScore;
                                } else {
                                    stupdScore = "0";
                                }


                                var fla = String.fromCharCode(flag);
                                var anlId = "'" + ansList[j].id + "'";
                                $('#content').append('<div id=" ' + data[i].id + '"' +
                                    '>' +
                                    '<li style="list-style: none">' +
                                    '<label style="margin-left: 40px;">' +
                                    '<input name="switch-field-1" class="ace ace-switch ace-switch-6"' +
                                    'title="' + data[i].id + '"' +
                                    'id="answerId' + anlId + '"' +
                                    'readonly="readonly"' +
                                    'value="F"' + trueAns + ' ' +
                                    'type="checkbox"/> <span class="lbl"></span> </label></li>' +
                                    '<li  style="list-style: none"><label style="margin-left: 10px;">' +
                                    '<div class="green bigger-110">正确答案是：<strong class="bigger-130">' + isAns +
                                    ' </strong></div></label> </li>' +
                                    ' <li  style="list-style: none">' +
                                    '<div id="hint" class="divhuiactive row" style="max-width: 685px;">' +
                                    '<div class="alert alert-info no-margin">' +
                                    '<div style="margin-top: 10px;">该题分值：' + data[i].questionScore +
                                    '分&nbsp;&nbsp;&nbsp;&nbsp;得分：' +
                                    '<input  oninput="value=value.replace(/[^\\d]/g,\'\')" value="' + stupdScore +
                                    '" onchange="anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlIdscore4 + ',' + data[i].questionScore + ')  " id="' + ansList[j].id + '"' +

                                    '">&nbsp;&nbsp;分</div>' +
                                    '</div></ul></div></div>');
                            }

                        }
                        if (data[i].tQuestiontemplateNum == "5") {
                            var anlIdscore5 = "";
                            var flag = 64;
                            var Optionnum = 0;
                            ansList = data[i].testpaperOptinanswerList;
                            stuAnsList = data[i].coursetestStuoptionanswerList;
                            scoreList = data[i].stuQuestionScore;

                            for (var j = 0; j < ansList.length; j++) {
                                anlIdscore5 = "'" + ansList[j].id + "'";
                                Optionnum += 1;
                                flag += 1;
                                sum += 1;
                                var trueAns = "";
                                var stuScore5 = "";
                                if (stuAnsList.length != 0) {
                                    if (stuAnsList[j].stuanswer != '0') {
                                        trueAns = stuAnsList[j].stuanswer;
                                    }
                                }
                                var fla = String.fromCharCode(flag);
                                var anlId = "'" + ansList[j].id + "'";

                                if (scoreList != null) {
                                    stuScore5 = scoreList.questionScore;
                                } else {
                                    stuScore5 = "0";
                                }
                                $('#content').append(' <li style="list-style: none">' +
                                    ' <div class="divhuiactive row" id=" ' + data[i].id + '"' +
                                    '>' +
                                    '<div class="content" id="content1" name="' + data[i].id + '"' +
                                    ' title="' + data[i].tQuestiontemplateNum + ';' + data[i].id + ';' + ansList[j].id + '">' +
                                    ' <div style="display:inline-block;vertical-align:top;padding-top: 15px; padding-left:35px">' + "答:" + '</div>' +
                                    '<textarea name="title" readonly="readonly" rows="5" id="importeditor"  style="width: 629px; height: 138px; margin: 0px;"  >' + trueAns +
                                    ' </textarea>' +
                                    '</div></div></li>' +
                                    ' <div class="green bigger-110">正确答案是：' + ansList[j].stoption + '</div>' +
                                    '<li style="list-style: none">' +
                                    '<div id="hint" class="divhuiactive row" style="max-width: 685px;">' +
                                    '  <div class="alert alert-info no-margin">' +
                                    ' <div style="margin-top: 10px;">该题分值：' + data[i].questionScore +
                                    ' 分&nbsp;&nbsp;&nbsp;&nbsp;得分：' +
                                    '<input  oninput="value=value.replace(/[^\\d]/g,\'\')" value="' + stuScore5 +
                                    '"' +
                                    'onchange="anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlIdscore5 + ',' + data[i].questionScore + ') " id="' + ansList[j].id + '"' +
                                    '>&nbsp;&nbsp;分</div></div></div></li>' +
                                    '');

                            }
                        }
                        if (data[i].tQuestiontemplateNum == "6") {
                            var anlIdscore6 = "";
                            var flag = 64;
                            var Optionnum = 0;
                            ansList = data[i].testpaperOptinanswerList;
                            stuAnsList = data[i].coursetestStuoptionanswerList;
                            scoreList = data[i].stuQuestionScore;

                            for (var j = 0; j < ansList.length; j++) {
                                anlIdscore6 = "'" + ansList[j].id + "'";
                                Optionnum += 1;
                                flag += 1;
                                sum += 1;
                                var trueAns = "";
                                var stuScore6 = "";
                                if (stuAnsList.length != 0) {
                                    if (stuAnsList[j].stuanswer != '0') {
                                        trueAns = stuAnsList[j].stuanswer;
                                    }
                                }
                                var fla = String.fromCharCode(flag);
                                var anlId = "'" + ansList[j].id + "'";

                                if (scoreList != null) {
                                    stuScore6 = scoreList.questionScore;
                                } else {
                                    stuScore6 = "0";
                                }
                                $('#content').append(' <li style="list-style: none">' +
                                    ' <div class="divhuiactive row" id=" ' + data[i].id + '"' +
                                    '>' +
                                    '<div class="content" id="content1" name="' + data[i].id + '"' +
                                    ' title="' + data[i].tQuestiontemplateNum + ';' + data[i].id + ';' + ansList[j].id + '">' +
                                    ' <div style="display:inline-block;vertical-align:top;padding-top: 15px; padding-left:35px">' + "答:" + '</div>' +
                                    '<textarea name="title" readonly="readonly" rows="5" id="' + ansList[j].id+
                                    '"  style="width: 629px; height: 138px; margin: 0px;"  >' + trueAns +
                                    ' </textarea>' +
                                    '</div></div></li>' +
                                    ' <div class="green bigger-110">正确答案是：' + ansList[j].stoption + '</div>' +
                                    '<li style="list-style: none">' +
                                    '<div id="hint" class="divhuiactive row" style="max-width: 685px;">' +
                                    '  <div class="alert alert-info no-margin">' +
                                    ' <div style="margin-top: 10px;">该题分值：' + data[i].questionScore +
                                    ' 分&nbsp;&nbsp;&nbsp;&nbsp;得分：' +
                                    '<input  oninput="value=value.replace(/[^\\d]/g,\'\')" value="' + stuScore6 +
                                    '"' +
                                    'onchange="anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlIdscore6 + ',' + data[i].questionScore + ') " id="' + ansList[j].id + '"' +
                                    '>&nbsp;&nbsp;分</div></div></div></li>' +
                                    '');

                                CKEDITOR.replace(ansList[j].id);
                                if(trueAns!=""){
                                    console.log("******"+trueAns);
                                    CKEDITOR.instances[ansList[j].id].setData(trueAns);

                                }

                            }
                        }

                    }
                }
            });
    }

    function submitsave() {
        if (confirm("您确定要提交并关闭吗？")) {
            $.ajax({
                type: "post",
                dataType: 'json',
                url: "/school/onlineExam/examList/saveScore?paperId=" + paperId + "&studentId=" + studentId,
                error: function (data) {
                    document.write("提交成功！");
                },
                success: function (data) {
                    document.write("提交成功！");
                }
            });

        }
    }

    function anwserchange(questionnum, questionid, questionscore, qscore) {
        var score = document.getElementById(questionscore).value;
        if ((score > qscore) || (score < 0)) {
            $.modal.alertWarning("该题分数不合理");
            return false;
        }
        $.ajax({
            type: "post",
            url: "/school/onlineExam/examList/saveStuScore?studentId=" + studentId + "&&questionid=" + questionid + "&&questionscore=" + score+"&&paperId"+paperId,
            error: function (data) {
                $.modal.alertError("保存失败!");
                return false;
            },
            success: function (data) {
                if (!data) {
                    $.modal.alertError("保存失败!");
                    return true;
                } else {
                    $.modal.alertSuccess("保存成功!");
                    return false;
                }
            }
        });
    }

    function testAnswer(questionnum, questionid, anwserid, anwserstr, tqg) {
        questionnumArray[tqg] = questionnum;
        answerstrArray[tqg] = anwserstr;
        questionidArray[tqg] = questionid;
        anwseridArray[tqg] = anwserid;
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
        var footstr = "</body>";
        var newstr = document.all.item(printpage).innerHTML;
        var oldstr = document.body.innerHTML;
        /* document.getElementById('widget-header').setAttribute(class, hidden); */
        document.body.innerHTML = headstr + newstr + footstr;
        window.print();
        document.body.innerHTML = oldstr;
        return false;
    }
</script>
