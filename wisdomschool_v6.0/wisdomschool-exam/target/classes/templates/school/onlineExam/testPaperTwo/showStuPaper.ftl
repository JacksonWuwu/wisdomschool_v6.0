<#include "/common/style.ftl"/>
<div id="divpx2">
    <div class="col-sm-12 select-table table-striped" style="width: 50%;left:25%" id="piyue">

        <div class="form-horizontal" id="startexamform">
            <center>
                <h3>${testPaper.testName}
                </h3>
            </center>
            <center>
                <h5>学号:${stuName} 姓名:${name}</h5>
            </center>
            <div id="stuScore"></div>
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
            url: "/school/onlineExam/testPaperOne/startMakePaper?paperId=" + paperId + "&&studentId=" + studentId,
            error: function (request) {
                $.modal.alertError("初始化试卷失败!");
            },
            success: function (data) {
                //目录部分
                var str2 = "abc";
                var numitem = 0;
                var onesum_mulu = 0;
                stuOptionExamanswerIdArray = new Array();
                for (let i = 0; i < data.length; i++) {
                    initStuOptionId(data[i].stuOptionExamanswerList[0].id);

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
                    /**
                     *   —— ———— ——
                     *   —— 试题加载 ——
                     *   —— ———— ——
                     * /
                     /**
                     * 单选
                     */
                    if (data[i].tQuestiontemplateNum == "1") {
                        var flag = 64;
                        ansList = data[i].testpaperOptinanswerList;
                        stuAnsList = data[i].stuOptionExamanswerList;
                        let stuIstrue = false;
                        let stuScore = 0;

                        let parseStuAnsList = [];
                        if (stuAnsList[0].stuAnswer != null || typeof(stuAnsList[0].stuAnswer) === "undefined") {
                            parseStuAnsList = stuAnsList[0].stuAnswer.split(";");
                        }

                        for (let j = 0; j < ansList.length; j++) {
                            if (parseStuAnsList.length != 0 && parseStuAnsList.length >= ansList.length) {
                                if ((parseStuAnsList[j] == ansList[j].stanswer) && (parseStuAnsList[j] != "")) {
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
                                    'value="' + fla + '" ' + isCheck + '>' +
                                    ' <span class="lbl">' + fla + '.' + ansList[j].stoption +
                                    '</span></div></div></li>');
                        }

                        var stuGetScore = "";
                        stuGetScore = stuAnsList[0].questionScore;

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
                                '  <input name="'+ stuAnsList[0].id + '" oninput="value=value.replace(/[^\\d]/g,\'\')"  value="' + data[i].questionScore +
                                '"onchange="anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlIdscore + ',' + data[i].questionScore + ', '+ stuAnsList[0].id +')  " id="' + thisId1 + '"' +
                                '">' +
                                '&nbsp;&nbsp;分</div> </div></div> </li></div></div></li>' +
                                '');

                    }
                    /**
                     * 双选
                     */
                    if (data[i].tQuestiontemplateNum == "2") {
                        var flag = 64;
                        ansList = data[i].testpaperOptinanswerList;
                        stuAnsList = data[i].stuOptionExamanswerList;
                        let stuIstrue2 = true;
                        let stuScore2 = 0;
                        let anlIdscore2 = "";
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
                            anlIdscore2 = "'" + ansList[j].id + "'";
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


                        var stuGetScore = "";
                        stuGetScore = stuAnsList[0].questionScore.questionScore;

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
                                '  <input name="'+ stuAnsList[0].id + '" oninput="value=value.replace(/[^\\d]/g,\'\')"  value="' + data[i].questionScore +
                                '" onchange="anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlIdscore2 + ',' + data[i].questionScore + ', '+ stuAnsList[0].id +')  " id="' + thisId + '"' +
                                '">' +
                                '&nbsp;&nbsp;分</div> </div></div> </li></div></div></li>' +
                                '');
                    }
                    /**
                     *  填空
                     */
                    if (data[i].tQuestiontemplateNum == "3") {
                        let anlIdscore3 = "";
                        ansList = data[i].testpaperOptinanswerList;
                        stuAnsList = data[i].stuOptionExamanswerList;

                        let flag = 64;
                        let Optionnum = 0;

                        var parseStuAnsList = [];
                        if (typeof(stuAnsList[0].stuAnswer) !== "undefined" || stuAnsList[0].stuAnswer != null) {
                            parseStuAnsList = stuAnsList[0].stuAnswer.split(";");
                        }

                        for (let j = 0; j < ansList.length; j++) {
                            anlIdscore3 = "'" + ansList[j].id + "'";
                            Optionnum += 1;
                            flag += 1;
                            let trueAns = "";
                            let stuScore3 = "0";

                            if (parseStuAnsList.length != 0 && parseStuAnsList.length >= ansList.length) {
                                trueAns = parseStuAnsList[j];
                            }

                            if (stuAnsList != null ) {
                                stuScore3 = stuAnsList[0].questionScore;
                            } else {
                                stuScore3 = "0";
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
                                    '</div></li>');
                        }
                        $('#content').append('<li style="list-style: none">' +
                                '<div id="hint" class="divhuiactive row" style="max-width: 685px;">' +
                                ' <div class="alert alert-info no-margin">' +
                                '<div style="margin-top: 10px;">该题分值：' + data[i].questionScore +
                                '分&nbsp;&nbsp;&nbsp;&nbsp;得分：' +
                                '  <input name="'+ stuAnsList[0].id + '" oninput="value=value.replace(/[^\\d]/g,\'\')" value="' + data[i].questionScore +
                                '"onchange="anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlIdscore3 + ',' + data[i].questionScore + ', '+ stuAnsList[0].id +')  " id=""' +
                                '> &nbsp;&nbsp;分</div></div></div> </li>');
                    }
                    /**
                     * 填空
                     */
                    if (data[i].tQuestiontemplateNum == "4") {
                        let anlIdscore4 = "";
                        let flag = 64;
                        let Optionnum = 0;
                        ansList = data[i].testpaperOptinanswerList;
                        stuAnsList = data[i].stuOptionExamanswerList;

                        let pdIstrue = false;
                        let stupdScore = 0;
                        for (let j = 0; j < ansList.length; j++) {
                            if (stuAnsList.length != 0) {
                                if (stuAnsList[0].stuAnswer.indexOf(ansList[j].stanswer) != -1) {
                                    pdIstrue = true;
                                }
                            }
                        }
                        for (let j = 0; j < ansList.length; j++) {
                            anlIdscore4 = "'" + ansList[j].id + "'";
                            Optionnum += 1;
                            flag += 1;
                            let trueAns = "";
                            if (stuAnsList.length != 0) {
                                if (stuAnsList[j].stuAnswer == 'T;') {
                                    trueAns = 'checked';
                                }

                            }
                            let isAns = "×";
                            if (ansList[j].stanswer == "T") {
                                isAns = "√";
                            }
                            var stuGetScore = "";
                            if (stuAnsList != null) {
                                stuGetScore = stuAnsList[0].questionScore;
                            }
                            if (pdIstrue == true && stuGetScore == "") {
                                stupdScore = data[i].questionScore;
                            } else if (stuGetScore != "") {
                                stupdScore = stuGetScore;
                            } else {
                                stupdScore = "0";
                            }


                            let fla = String.fromCharCode(flag);
                            let anlId = "'" + ansList[j].id + "'";
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
                                    '<input name="'+ stuAnsList[0].id + '" oninput="value=value.replace(/[^\\d]/g,\'\')" value="' + data[i].questionScore +
                                    '" onchange="anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlIdscore4 + ',' + data[i].questionScore + ', '+ stuAnsList[0].id +')  " id="' + ansList[j].id + '"' +

                                    '">&nbsp;&nbsp;分</div>' +
                                    '</div></ul></div></div>');
                        }
                    }
                    /**
                     * 简答题
                     */
                    if (data[i].tQuestiontemplateNum == "5") {
                        let anlIdscore5 = "";
                        let flag = 64;
                        let Optionnum = 0;
                        ansList = data[i].testpaperOptinanswerList;
                        stuAnsList = data[i].stuOptionExamanswerList;

                        for (let j = 0; j < ansList.length; j++) {
                            anlIdscore5 = "'" + ansList[j].id + "'";
                            Optionnum += 1;
                            flag += 1;
                            sum += 1;
                            let trueAns = "";
                            let stuScore5 = "";
                            if (stuAnsList.length != 0) {
                                if (stuAnsList[0].stuAnswer != null || typeof(stuAnsList[0].stuAnswer) === "undefined") {
                                    trueAns = stuAnsList[0].stuAnswer.replace(";", "");
                                }
                            }
                            let fla = String.fromCharCode(flag);
                            let anlId = "'" + ansList[j].id + "'";

                            if (stuAnsList != null) {
                                stuScore5 = stuAnsList[0].questionScore;
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
                                    '<input name="'+ stuAnsList[0].id + '" oninput="value=value.replace(/[^\\d]/g,\'\')" value="' + data[i].questionScore +
                                    '"' +
                                    'onchange="anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlIdscore5 + ',' + data[i].questionScore + ', '+ stuAnsList[0].id +') " id="' + ansList[j].id + '"' +
                                    '>&nbsp;&nbsp;分</div></div></div></li>' +
                                    '');

                        }
                    }
                    /**
                     *
                     */
                    if (data[i].tQuestiontemplateNum == "6") {
                        var anlIdscore6 = "";
                        var flag = 64;
                        var Optionnum = 0;
                        ansList = data[i].testpaperOptinanswerList;
                        stuAnsList = data[i].stuOptionExamanswerList;

                        let parseStuAnsList = [];
                        if (stuAnsList[0].stuAnswer != null || typeof(stuAnsList[0].stuAnswer) === "undefined") {
                            parseStuAnsList = stuAnsList[0].stuAnswer.split(";");
                        }

                        for (var j = 0; j < ansList.length; j++) {
                            anlIdscore6 = "'" + ansList[j].id + "'";
                            Optionnum += 1;
                            flag += 1;
                            sum += 1;
                            var trueAns = "";
                            var stuScore6 = "";
                            if (parseStuAnsList.length != 0 && parseStuAnsList.length >= ansList.length) {
                                if (parseStuAnsList[j] != '0') {
                                    trueAns = parseStuAnsList[j];
                                }
                            }
                            var fla = String.fromCharCode(flag);
                            var anlId = "'" + ansList[j].id + "'";

                            if (stuAnsList != null) {
                                stuScore6 = stuAnsList[0].questionScore;
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
                                    '<input name="'+ stuAnsList[0].id + '" oninput="value=value.replace(/[^\\d]/g,\'\')" value="' + data[i].questionScore +
                                    '"' +
                                    'onchange="anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlIdscore6 + ',' + data[i].questionScore + ', '+ stuAnsList[0].id +') " id="' + ansList[j].id + '"' +
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

    function submitHandler() {
    }

    /**
     * 提交分数
     */
    var scoreArray = [];
    function submitsave() {
        scoreArray = new Array();
        if (stuOptionExamanswerIdArray.length != 0) {
            for (var i=0; i<stuOptionExamanswerIdArray.length; i++){
                var object = {
                    'stuOptionExamanswerId': stuOptionExamanswerIdArray[i],
                    'score': $("input[name='" + stuOptionExamanswerIdArray[i] +"']").val()
                }
                scoreArray.push(object);
            }
        }

        if (confirm("您确定要提交并关闭吗？")) {
            $.ajax({
                type: "post",
                dataType: 'json',
                url: "/school/onlineExam/testPaperOne/saveScore?paperId=" + paperId + "&userId=" + "${stuUserId}",
                contentType: "application/json;charset-UTF-8",
                data: JSON.stringify(scoreArray),
                error: function (data) {
                    document.write("提交成功！");
                },
                success: function (data) {
                    document.write("提交成功！");
                }
            });
        }
    }
    function anwserchange(questionnum, questionid, questionscore, qscore, stuoptionid) {
        let score = document.getElementById(questionscore).value;
        if ((score > qscore) || (score < 0)) {
            $.modal.alertWarning("该题分数不合理");
            return false;
        }

        console.log(stuoptioni);
        testAnswer(questionnum, questionid, "", "", stuoptionid, tqg);
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
