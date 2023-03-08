<#include "/common/style.ftl"/>

<style>
    .div1{
        position:fixed;
        right:0;
        top:5%;
        height:50px;

    }
    .test_content_nr_main{width:85%; margin:0 auto; padding:10px 0;height:auto;}
    .test_content_nr{width:100%; border-top:3px solid #efefef;}
    .test_content_nr_tt{width:85%; height:auto; line-height:32px; margin:0 auto; border-bottom:1px solid #e4e4e4;}
    .test_content{width:100%; height:auto; margin-top:15px;}
    .test_content_nr_tt i{width:25px; height:25px; line-height:25px; text-align:center; display:block; float:left; background:#5d9cec; border-radius:50%; margin-left:-50px; color:#fff; margin-top:8px; font-size:16px;}
    .test_content_title{width:100%; height:50px; line-height:50px; background:#f7f7f7; text-align:center;}
    .option{line-height:32px; display:block; background:#fff;color:#666;}
    .option input{width:20px; height:20px; display:block; float:left; margin:10px 10px 0 0;}
    .inputfile {
        position:absolute;clip:rect(0 0 0 0);
    }
</style>
<div id="divpx2">
    <div class="col-sm-12 select-table table-striped" style="width: 70%;left: 15%">
        <div class="form-horizontal" id="startexamform" >
            <center>
                <img src="/img/images/csxyexam.jpg" width="600px" height="100px"/>
            </center>
            <center>
                <h1>${testPaper.headline}
                </h1>
            </center>
            <center>
                <h4>试卷名:${testPaper.testName}&nbsp;总分:${testPaper.score}</h4>
            </center>
            <center>
                <h5>学号:${stuName} 姓名:${name}</h5>
            </center>
            <input type="hidden" value="${testPaper.time} " id="paperTime">
            <#--            <div id="stuScore">得分：</div>-->

            <div id="content">
            </div>
            <div class="btn-group btn-corner" style="width:100%; " id="sumb">
            </div>
        </div>
    </div>
    <div id="right-menu" class="modal aside"  data-body-scroll="false"
         data-offset="true" data-placement="right" data-fixed="true"
         data-backdrop="invisible" tabindex="-1">
        <div class="modal-dialog" style="top: 0px;">
            <div class="modal-content">
                <div class="modal-header no-padding">
                    <div class="table-header btn-info">
                        <button type="button" class="close" data-dismiss="modal"
                                aria-hidden="true">
                            <#--                            <span class="white">&times;</span>-->
                        </button>
                        作业目录
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
            <button class="aside-trigger btn btn-info btn-app btn-xs ace-settings-btn"
                    data-target="#right-menu" data-toggle="modal" type="button" style="float: left;margin-top: -25px">
                <i data-icon1="fa-plus" data-icon2="fa-minus"
                   class="ace-icon fa fa-search-plus bigger-110 icon-only"></i>
            </button>
        </div>
        <!-- /.modal-dialog -->
    </div>
    <div class="div1" id="div1">
    </div>


    <!--[if !IE]> -->
    <script src="/js/jquery.min.js"></script>
    <script src="/js/qrcode/qrcode.js"></script>
    <script src="/js/dropify/dropify.min.js"></script>
    <!-- <![endif]-->

    <!--[if IE]>
    <script src="/js/jquery-1.11.3.min.js"></script>
    <![endif]-->
    <script type="text/javascript">
        if ('ontouchstart' in document.documentElement) document.write("<script src='js/ace/js/jquery.mobile.custom.min.js'>" + "<" + "/script>");
    </script>
    <script src="/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/bootbox.min.js"></script>
    <!-- page specific plugin scripts -->

    <!--[if lte IE 8]>
    <script src="/js/excanvas.min.js"></script>
    <![endif]-->

    <!-- ace scripts -->
    <script type="text/javascript" src="/js/ace/js/ace-elements.min.js"></script>
    <script type="text/javascript" src="/js/ace/js/ace.min.js"></script>
    <script type="text/javascript" src="/js/plugins/bootstrap-treetable/bootstrap-treetable.js"></script>
    <!-- bootstrap-table 表格插件 -->
    <script src="/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
    <script src="/js/plugins/jquery-ztree/3.5/js/jquery.ztree.all-3.5.js"></script>
    <script src="/js/plugins/bootstrap-table/extensions/mobile/bootstrap-table-mobile.min.js"></script>
    <script src="/js/plugins/bootstrap-table/extensions/toolbar/bootstrap-table-toolbar.min.js"></script>

    <script type="text/javascript" src="/js/plugins/layer/layer.js"></script>
    <script type="text/javascript" src="/js/plugins/layui/layui.js"></script>
    <script type="text/javascript" src="/js/plugins/blockUI/jquery.blockUI.min.js"></script>
    <script type="text/javascript" src="/js/plugins/iCheck/icheck.min.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery-validation/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery-validation/messages_zh.min.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery-validation/jquery.validate.extend.js"></script>
    <script type="text/javascript" src="/js/plugins/select/select2.js"></script>
    <script type="text/javascript" src="/js/plugins/bootstrap-fileinput/js/fileinput.js"></script>
    <script type="text/javascript" src="/js/plugins/bootstrap-fileinput/js/locales/zh.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
    <script type="text/javascript" src="/js/admin/index.js"></script>
    <script type="text/javascript" src="/js/ace/js/ace.min.js"></script>
    <script src="/js/page.js"></script>

    <script type="text/javascript">
        var UserAttrId=${UserAttrId};
        function TinitPagerEvent() {
            //属性 用于加载分页数据
            let TcontentItem = [{
                key: 'course.id',
                replace: 'redirect:/teacher/course/{%}',
                attr: 'href'
            }, {
                key: 'thumbnailPath',
                replace: '${storage}/showCondensedPicture?fileId={%}',
                attr: 'src'
            }, {
                key: 'course.name'
            }];
            let TpagerOptions = {
                loadURL: 'http://lxweixin.free.idcfengye.com/wx/course/list?studentId='+UserAttrId,
                item: TcontentItem
            };
            let page = $.fn.startPage(TpagerOptions);
        }

        function TclickLoad(e) {
            let that = e.currentTarget;
            let url = $.common.trim(that.href);
            if (url !== '#' && url !== '' && url !== undefined) {
                $.common.loadPage('main-page-container', url);
            }
            e.preventDefault();
        }

        $(document).ready(function () {
            TinitPagerEvent();
        })


        function SclickLoad(item) {
            let wp = window;
            wp.location.href='/admin/'+item;
        }
    </script>

    <script src="/js/jquery.easy-pie-chart.js"></script>
    <!--时间js-->
    <script src="/js/jquery.countdown.js"></script>
    <script type="text/javascript" src="/dist/vendors/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="/js/onlineExam/js/reconnecting-websocket.min.js"></script>
    <script type="text/javascript" src="/js/onlineExam/js/offline.min.js"></script>
    <script>
        $(document).ready(function(){
            $('#right-menu').modal('show');
        })
        var ansList = "";//试题答案
        var stuAnsList = "";
        var studentId = "${studentId}";//学生ID
        var tutId = "${tutId}";//
        var tqg = 0;//是否有答案标记
        var paperId = "${paperId}";
        var cid = "${chapterId}";
        var courseId = "${testPaper.coursrId}";
        var paperRule="${testPaper.rule}";
        var ckeditor1 = "";
        var myid;
        var ws;
        var anwserstr;
        //答案相关数组
        var questiontypeArray = new Array(100);
        var questionnumArray = new Array(100);
        var answerstrArray = new Array(100);
        var questionidArray = new Array(100);
        var anwseridArray = new Array(100);
        let stuPaperScore = 0;//学生分数
        var  questionsId = "";
        var studentQusScore  ="";
        var contents = "content10";
        $(function () {

            initPaper();
            // CKEDITOR.replace(contents);
        });

        function initPaper() {

            //webso();
            $.ajax({
                type: "get",
                dataType: 'json',
                url: "http://lxweixin.free.idcfengye.com/wx/exam/chapterTest/startCourseWorkPaper?paperId=" + paperId + "&&studentId=" + studentId + "&&paperRule=" + paperRule,
                error: function (request) {
                    $.modal.alertError("初始化试卷失败!");
                },
                success: function (data) {

                    //目录部分
                    var str2 = "abc";
                    var mulu = "";
                    var numitem = 0;
                    var onesum_mulu = 0;
                    for (var i = 0; i < data.length; i++) {
                        numitem += 1;
                        if (data[i].titleTypeName != mulu) {
                            onesum_mulu += 1;
                            mulu = data[i].titleTypeName;
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
                    var type = "";
                    for (var i = 0; i < data.length; i++) {
                        num += 1;
                        var tQuestiontemplateNum = "'" + data[i].tQuestiontemplateNum + "'";
                        var dataId = "'" + data[i].id + "'";

                        var type = data[i].titleTypeNum;
                        if (data[i].titleTypeName != str) {
                            onesum += 1;
                            var simplifiedChinese = Arabia_To_SimplifiedChinese(onesum);
                            str = data[i].titleTypeName;
                            var qid = data[i].id;
                            $('#content').append('<div id="' + data[i].tQuestiontemplateNum + '"  class="test_content" >' +
                                ' <div class="test_content_title" >' +
                                '<strong class="content_lit" style="float: left">' + simplifiedChinese + '、' + data[i].titleTypeName +
                                '</strong></div></div>' +
                                '  <div id="' + qid + '"' +
                                'class="test_content_nr" >' +
                                '<h5><strong><div class="test_content_nr_tt" >' +
                                '<i>' + num + '</i>' +
                                '<p>(' + data[i].questionScore + '分)' + data[i].title +
                                '</p></div> </strong></h5><div><ul>');
                        } else {
                            var qid2 = data[i].id;
                            $('#content').append('<div id="' + qid2 + '"' +
                                'class="test_content_nr"  >' +
                                '<h5><strong><div class="test_content_nr_tt">' +
                                '<i>' + num + '</i>' +
                                '<p>('+data[i].questionScore+'分)' + data[i].title +
                                '</p></div></strong></h5><div><ul>');
                        }

                        if (data[i].tQuestiontemplateNum == "1"&&data[i].stuOptionExamanswerList == "") {

                            var flag = 64;
                            ansList = data[i].testpaperOptinanswerList;
                            stuAnsList = data[i].stuOptionExamanswerList;
                            let stuIstrue = false;
                            let stuScore = 0;

                            let parseStuAnsList = [];

                            for (let j = 0; j < ansList.length; j++) {
                                if (parseStuAnsList.length != 0 && parseStuAnsList.length >= ansList.length) {
                                    if ((parseStuAnsList[j] == ansList[j].stanswer) && (parseStuAnsList[j] != "")) {
                                        stuIstrue = true;
                                    }
                                }
                            }
                            let thisId1 = "";
                            for (var j = 0; j < ansList.length; j++) {
                                thisId1 = ansList[j].id;
                                flag += 1;
                                var paperAns = "";
                                var isCheck = "";
                                var stutrueAns = "green";
                                var trueAns = "";
                                // if (stuAnsList.length!= 0){
                                //         if (stuAnsList[j].stuAnswer!='0'){
                                //             trueAns = 'checked';
                                //         }
                                // }
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
                                $('#content').append('<li style="list-style: none">' +
                                    '<div class="test_content_nr_tt" id=" ' + data[i].id + '"' +
                                    '>' +
                                    '<div class="option">' +
                                    '<label>' +
                                    '<input name="form-field-radio' + num + '"' +
                                    ' id="answerId' + anlId + '"' +
                                    'type="radio" ' +
                                    'title="' + data[i].id + '"' +
                                    'value="' + fla + '"'  +
                                    'class="radioOrCheck"' +
                                    'onclick=' +
                                    '"anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlId + ', ' + type +') "' + trueAns + '>' +
                                    '<p class="ue" style="display: inline;">' +
                                    fla + '.' + ansList[j].stoption +
                                    '</p></label></div> </div></li> </ul></div></div>');
                            }
                        }
                        if (data[i].tQuestiontemplateNum == "1"&&data[i].stuOptionExamanswerList != "") {

                            var flag = 64;
                            ansList = data[i].testpaperOptinanswerList;
                            stuAnsList = data[i].stuOptionExamanswerList;
                            let stuIstrue = false;
                            let stuScore = 0;

                            let parseStuAnsList = [];
                            if (stuAnsList[0].stuAnswer != null || typeof (stuAnsList[0].stuAnswer) === "undefined") {
                                parseStuAnsList = stuAnsList[0].stuAnswer.split(";");
                            } else {
                                parseStuAnsList = [];
                            }

                            for (let j = 0; j < ansList.length; j++) {
                                if (parseStuAnsList.length != 0 && parseStuAnsList.length >= ansList.length) {
                                    if ((parseStuAnsList[j] == ansList[j].stanswer) && (parseStuAnsList[j] != "")) {
                                        stuIstrue = true;
                                    }
                                }
                            }
                            let thisId1 = "";
                            for (var j = 0; j < ansList.length; j++) {
                                thisId1 = ansList[j].id;
                                flag += 1;
                                var paperAns = "";
                                var isCheck = "";
                                var stutrueAns = "green";
                                var trueAns = "";
                                // if (stuAnsList.length!= 0){
                                //         if (stuAnsList[j].stuAnswer!='0'){
                                //             trueAns = 'checked';
                                //         }
                                // }
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
                                // $('#content').append('<li style="list-style: none">' +
                                //         '<div class="test_content_nr_tt" id=" ' + data[i].id + '"' +
                                //         '>' +
                                //         '<div class="option">' +
                                //         '<label>' +
                                //         '<input name="form-field-radio' + num + '"' +
                                //         ' id="answerId' + anlId + '"' +
                                //         'type="radio" ' +
                                //         'title="' + data[i].id + '"' +
                                //         'value="' + fla + '"'  +
                                //         'class="radioOrCheck"' +
                                //         'onclick=' +
                                //         '"anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlId + ', ' + type +') "' + trueAns + '>' +
                                //         '<p class="ue" style="display: inline;">' +
                                //          fla + '.' + ansList[j].stoption +
                                //         '</p></label></div> </div></li> </ul></div></div>');
                                $('#content').append('' +
                                    '<li  style="list-style: none" class="' + trueAns + "  " + stutrueAns +
                                    '">' +
                                    '<div class="test_content_nr_tt" id=" ' + data[i].id + '">' +
                                    '<div class="option" style="padding-bottom: 7px;">' +
                                    '</li></div></div>');
                                $('#content').append(
                                    '<li style="list-style: none">' +
                                    '<div class="test_content_nr_tt" id=" ' + data[i].id + '"' +
                                    '>' +
                                    '<div class="option">' +
                                    '<label>' +
                                    '<input style="color: #95f71c;" name="form-field-radio' + num + '"' +
                                    'type="radio"' +
                                    ' id="answerId' + anlId + '"' +
                                    'title="' + data[i].id + '"' +
                                    ' class="radioOrCheck"' +
                                    'onclick=' +
                                    '"anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlId + ', ' + type + ') "' + trueAns +
                                    // 'disabled readonly="true"' +
                                    'value="' + fla + '" ' + isCheck + '>' +
                                    ' <p class="ue" style="display: inline;">' + fla + '.' + ansList[j].stoption +
                                    '</p></label></div></div></li>');

                            }
                        }
                        if (data[i].tQuestiontemplateNum == "2" && data[i].stuOptionExamanswerList == "") {
                            var flag = 64;
                            ansList = data[i].testpaperOptinanswerList;
                            stuAnsList = data[i].stuOptionanswerList;

                            for (var j = 0; j < ansList.length; j++) {

                                var trueAns = "";
                                if (stuAnsList.length != 0) {
                                    if (stuAnsList[0].stuAnswer.indexOf(ansList[j].stanswer) === -1) {
                                        stuIstrue2 = false;
                                    }
                                }
                                if (stuAnsList.length != 0) {
                                    if (stuAnsList[j].stuAnswer != '0') {
                                        trueAns = 'checked';
                                    }
                                }
                            }
                            for (let j = 0; j < ansList.length; j++) {
                                flag += 1;
                                var fla = String.fromCharCode(flag);
                                var anlId = "'" + ansList[j].id + "'";
                                $('#content').append('<li style="list-style: none">' +
                                    '<div class="test_content_nr_main" id=" ' + data[i].id + '"' +
                                    '>' +
                                    '<div class="option">' +
                                    ' <label for="1_answer_13_option_1">' +
                                    ' <input name="form-field-checkbox' + num + '"' +
                                    ' id="answerId' + anlId + '"' +
                                    ' title="' + data[i].id + '"' +
                                    'value="' + fla + '"' +
                                    'onchange=' +
                                    '"anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlId + ', ' + type + ') "' + trueAns +
                                    ' type="checkbox"' +
                                    ' class="radioOrCheck"/>' +
                                    '<span class="ue" style="display: inline;"><span>' + fla + '.' + ansList[j].stoption +
                                    ' </span></span></label></div></li></ul></div></div>');

                            }
                        }
                        /**
                         * 双选
                         */
                        if (data[i].tQuestiontemplateNum == "2" && data[i].stuOptionExamanswerList != "") {
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
                                    '<div class="test_content_nr_main" id=" ' + data[i].id + '"' +
                                    '>' +
                                    '<div class="option">' +
                                    // '<label style="padding-left: 30">' +
                                    // ' <input name="form-field-checkbox' + num + '520"' +
                                    // ' id="answerId' + anlId + '"' +
                                    // ' title="' + data[i].id + '"' +
                                    // 'value="' + fla + '"' + pdtrueAns +
                                    // ' type="checkbox"' +
                                    // 'disabled readonly="true"' +
                                    // ' class="ace green"/>' +
                                    // '<span class="lbl"></span></label>' +
                                    ' <label> <input name="form-field-checkbox' + num +
                                    '" ' + trueAns + ' ' +
                                    ' type="checkbox" ' +
                                    ' id="answerId' + anlId + '"' +
                                    ' title="' + data[i].id + '"' +
                                    'value="' + fla + '"' +
                                    'onchange=' +
                                    '"anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlId + ', ' + type + ') "' +
                                    'class="radioOrCheck" /><span class="ue" style="display: inline;">' +
                                    '<span>' + fla + '.' + ansList[j].stoption +
                                    ' </span></span></label></div></div></li>');


                            }


                        }
                        if (data[i].tQuestiontemplateNum == "3" && data[i].stuOptionExamanswerList == "") {
                            let anlIdscore3 = "";
                            ansList = data[i].testpaperOptinanswerList;
                            stuAnsList = data[i].stuOptionExamanswerList;
                            var flag = 64;
                            var Optionnum = 0;
                            for (var j = 0; j < ansList.length; j++) {
                                Optionnum += 1;
                                flag += 1;
                                var trueAns = "";
                                if (stuAnsList.length != 0) {
                                    if (stuAnsList[j].stuAnswer != '0') {
                                        trueAns = stuAnsList[j].stuAnswer;
                                    }
                                }
                                var fla = String.fromCharCode(flag);
                                var anlId = "'" + ansList[j].id + "'";
                                var ewnum = Arabia_To_SimplifiedChinese(Optionnum);
                                $('#content').append('<li style="list-style: none">' +
                                    '<div class="test_content_nr_main" style="padding-left:40px" id=" ' + data[i].id + '"' +
                                    '>' +
                                    '  <div style="display:inline-block;vertical-align:top;padding-top: 15px;">' + '第' + ewnum + '空、' +
                                    '</div>' +
                                    '<div style="display:inline-block;vertical-align:top;max-width: 685px;padding: 10px 0;">' +
                                    '<textarea ' +
                                    'title="' + data[i].id + '"' +
                                    'class="autosize-transition form-control"' +
                                    'id="answerId' + anlId + '"' +
                                    'value="F"' +
                                    'onchange=' +
                                    '"anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlId + ', ' + type + ') "' +
                                    'style="width: 630px;max-width:630px; height: 35px;font-family:' +
                                    ' inherit;font-style: normal;font-weight: normal;text-align: left;">' + trueAns +
                                    '</textarea>' +
                                    '</div></div></li></ul></div></div>');
                            }
                        }

                        if (data[i].tQuestiontemplateNum == "3" && data[i].stuOptionExamanswerList != "") {
                            let anlIdscore3 = "";
                            ansList = data[i].testpaperOptinanswerList;
                            stuAnsList = data[i].stuOptionExamanswerList;

                            let flag = 64;
                            let Optionnum = 0;

                            var parseStuAnsList = [];
                            if (typeof (stuAnsList[0].stuAnswer) !== "undefined" || stuAnsList[0].stuAnswer != null) {
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

                                if (stuAnsList != null) {
                                    stuScore3 = stuAnsList[0].questionScore;
                                } else {
                                    stuScore3 = "0";
                                }
                                let fla = String.fromCharCode(flag);
                                let anlId = "'" + ansList[j].id + "'";
                                let ewnum = Arabia_To_SimplifiedChinese(Optionnum);
                                $('#content').append('<li style="list-style: none">' +
                                    '<div class="test_content_nr_main" style="padding-left:40px" id=" ' + data[i].id + '"' +
                                    '>' +
                                    '  <div style="display:inline-block;vertical-align:top;padding-top: 15px;">' + '第' + ewnum + '空.' +
                                    '</div>' +
                                    '<div style="display:inline-block;vertical-align:top;max-width: 685px;padding: 10px 0;">' +
                                    '<textarea ' +
                                    'title="' + data[i].id + '"' +
                                    'class="autosize-transition form-control"' +
                                    'id="answerId' + anlId + '"' +
                                    'onchange=' +
                                    '"anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlId + ', ' + type + ') "' +
                                    'value="F"' +
                                    'style="width: 630px;max-width:630px; height: 35px;font-family:' +
                                    ' inherit;font-style: normal;font-weight: normal;text-align: left;">' + trueAns +
                                    '</textarea>' +
                                    '</div>' +
                                    '</div></li>');
                            }

                        }
                        if (data[i].tQuestiontemplateNum == "4"&& data[i].stuOptionExamanswerList == "") {
                            var flag = 64;
                            var Optionnum = 0;
                            ansList = data[i].testpaperOptinanswerList;
                            stuAnsList = data[i].stuOptionExamanswerList;
                            for (var j = 0; j < ansList.length; j++) {
                                Optionnum += 1;
                                flag += 1;
                                var trueAns = "";
                                if (stuAnsList.length != 0) {
                                    if (stuAnsList[j].stuAnswer != '0') {
                                        trueAns = 'checked';
                                    }
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
                                    'value="F"' +
                                    'onchange=' + '"anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlId + ', ' + type +') "' + trueAns + ' ' +
                                    'type="checkbox"/> <span class="lbl"></span> </label></li></div></ul></div></div>');
                            }
                        }
                        if (data[i].tQuestiontemplateNum == "4"&& data[i].stuOptionExamanswerList != "") {
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

                                    'value="F"' + trueAns + ' ' +
                                    'onchange=' + '"anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlId + ', ' + type + ') "' +
                                    'type="checkbox"/> <span class="lbl"></span> </label></li>' +
                                    '</ul></div>');
                            }
                        }
                        if (data[i].tQuestiontemplateNum == "5" && data[i].titleTypeNum == 10 && data[i].stuOptionExamanswerList == "") {
                            var flag = 64;
                            var Optionnum = 0;
                            ansList = data[i].testpaperOptinanswerList;
                            stuAnsList = data[i].stuOptionExamanswerList;
                            for (var j = 0; j < ansList.length; j++) {

                                Optionnum += 1;
                                flag += 1;
                                sum += 1;
                                var trueAns = "";
                                if (stuAnsList.length != 0) {
                                    if (stuAnsList[j].stuAnswer != '0') {
                                        trueAns = stuAnsList[j].stuAnswer;
                                    }
                                }
                                var fla = String.fromCharCode(flag);
                                var anlId = "'" + ansList[j].id + "'";
                                // var ansType = stuAnsList[j].answerType.split(";");

                                $('#content').append(' <li style="list-style: none">' +
                                    ' <div class="test_content_nr_main" id=" ' + data[i].id + '"' +
                                    '>' +
                                    '<div class="content" id="content1" name="' + data[i].id + '"' +
                                    ' title="' + data[i].tQuestiontemplateNum + ';' + data[i].id + ';' + ansList[j].id + '">' +
                                    ' <div style="display:inline-block;vertical-align:top;padding-top: 15px; padding-left:35px">' + "答:" + '</div>' +
                                    '<textarea for="multipartFile" name="title" rows="30" cols="80" id="' + ansList[j].id + '"' +
                                    // '<textarea name="title" rows="30" cols="80" id="'+contents+'"' +
                                    'style="margin: 0px; width: 569px; height: 152px;"' +
                                    'onchange=' + '"anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlId + ', ' + type + ')">' + trueAns + ' ' +
                                    '</textarea>' +
                                    // '<input type="file" name="thumbnail" id="thumbnail" class="inputfile" accept="image/png, image/jpeg, image/gif, image/jpg"/>' +
                                    // '<label for="thumbnail" class="btn btn-success">请上传代码截图</label>'+
                                    // '<div  id="divUL">' +
                                    // '</div>'+
                                    '</br>'+
                                    '<p style="color: red;font-size:15px">（请先在文本框中填写代码再上传代码截图）</p>'+
                                    '<form action="${ctx}/school/onlineExam/testPaperOne/uploadImg?paperId=' + paperId + '&&studentId=' + studentId + '&&stoption=' + ansList[j].id + '" id="domeform' + ansList[j].id + '" target="frameName' + ansList[j].id + '" method="post" enctype="multipart/form-data">' +
                                    '    <input id="file' + ansList[j].id + '" multiple="multiple"  type="file" name="file" value="选择文件" accept="image/png, image/jpeg, image/gif, image/jpg">' +
                                    // '<label for="file" class="btn btn-success">请上传代码截图</label>' +
                                    '    <input type="submit" class="btn btn-success btn-xs" value="上传" onclick="window.location.reload()">' +
                                    '</form>' );
                                <#--if (stuAnsList[j].answerType!=""){-->
                                <#--    $('#content').append('<p style="font-size:15px">上传成功！</p></br>');-->
                                <#--}-->
                                <#--if (stuAnsList[j].answerType!=""){-->
                                <#--    for (var k=0;k<ansType.length;k++) {-->
                                <#--        if (ansType[k]==""){}-->
                                <#--        else{-->
                                <#--            $('#content').append('<img width="500px" src="${storage}/showCondensedPicture?fileId='+ansType[k]+'" /><button class="btn btn-danger btn-xs" onclick="deleteImage(\'' + ansType[k] + ','+ ansList[j].id+'\')">删除</button></br>');-->
                                <#--        }-->
                                <#--    }-->
                                <#--    }-->
                                $('#content').append('</br>'+
                                    '<iframe src="" frameborder="0" name="frameName' + ansList[j].id + '"></iframe>' +
                                    '</div></div></div></li></ul></div></div>');
                                // CKEDITOR.replace("content10")


                            }

                        }
                        if (data[i].tQuestiontemplateNum == "5" && data[i].titleTypeNum == 10 && data[i].stuOptionExamanswerList != "") {

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
                                let trueAnsOne = "";
                                let stuScore5 = "";
                                if (stuAnsList.length != 0) {
                                    if (stuAnsList[0].stuAnswer != null || typeof (stuAnsList[0].stuAnswer) === "undefined") {
                                        trueAns = stuAnsList[0].stuAnswer.replace(";", "");
                                        trueAnsOne = stuAnsList[0].answerType.replace(";", "");
                                    }
                                }
                                let fla = String.fromCharCode(flag);
                                let anlId = "'" + ansList[j].id + "'";
                                let trueAnsTwo = [];
                                trueAnsTwo = trueAnsOne.split(";");
                                var ansType = stuAnsList[j].answerType.split(";");


                                $('#content').append(' <li style="list-style: none">' +
                                    ' <div class="divhuiactive row" id=" ' + data[i].id + '"' +
                                    '>' +
                                    '<div class="content" id="content1" name="' + data[i].id + '"' +
                                    ' title="' + data[i].tQuestiontemplateNum + ';' + data[i].id + ';' + ansList[j].id + '">' +
                                    ' <div style="display:inline-block;vertical-align:top;padding-top: 15px; padding-left:35px">' + "答:" + '</div>' +
                                    // '<textarea name="title"  rows="5" id="importeditor"  style="width: 629px; height: 138px; margin: 0px;"  >' + trueAns +
                                    // ' </textarea>' +
                                    '<textarea for="multipartFile" name="title" rows="30" cols="80" id="' + ansList[j].id + '"' +
                                    'style="margin: 0px; width: 569px; height: 152px;"' +
                                    'onchange=' + '"anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlId + ', ' + type + ')">' + trueAns + ' ' +
                                    '</textarea>' +
                                    '</br>'+
                                    '<p style="color: red;font-size:15px">（请先在文本框中填写代码再上传代码截图）</p>'+
                                    '<form  action="${ctx}/school/onlineExam/testPaperOne/uploadImg?paperId=' + paperId + '&&studentId=' + studentId + '&&stoption=' + ansList[j].id + '" id="domeform' + ansList[j].id + '" target="frameName' + ansList[j].id + '" method="post" enctype="multipart/form-data">' +
                                    '    <input id="file' + ansList[j].id + '" multiple="multiple"    type="file" name="file" value="选择文件" accept="image/png, image/jpeg, image/gif, image/jpg">' +
                                    // '<label for="file' + ansList[j].id + '" class="btn btn-success">请选择代码截图</label>' +
                                    '<input  type="submit" class="btn btn-warning btn-xs" value="上传" onclick="window.location.reload()">' +
                                    '</form>' );
                                if (stuAnsList[j].answerType!=""){
                                    $('#content').append('<p style="font-size:15px">上传成功！</p></br>');
                                }
                                if (stuAnsList[j].answerType!=""){
                                    for (var k=0;k<ansType.length;k++){
                                        if (ansType[k]==""){}
                                        else{
                                            $('#content').append('<img width="500px" src="${storage}/showCondensedPicture?fileId='+ansType[k].trim()+'" /><button class="btn btn-danger btn-xs" onclick="deleteImage(\'' + ansType[k] + ','+ ansList[j].id+'\')">删除</button></br>');
                                        }
                                    }
                                }
                                $('#content').append('</br>'+
                                    '<iframe  src="" frameborder="0" name="frameName' + ansList[j].id + '"></iframe>' +
                                    '</div></div></li>' +
                                    '</div></div></div></li>' +
                                    '');
                            }
                        }
                        if (data[i].tQuestiontemplateNum == "5" && data[i].titleTypeNum == 7 && data[i].stuOptionExamanswerList == "") {
                            var flag = 64;
                            var Optionnum = 0;
                            ansList = data[i].testpaperOptinanswerList;
                            stuAnsList = data[i].stuOptionExamanswerList;
                            for (var j = 0; j < ansList.length; j++) {

                                Optionnum += 1;
                                flag += 1;
                                sum += 1;
                                var trueAns = "";
                                if (stuAnsList.length != 0) {
                                    if (stuAnsList[j].stuAnswer != '0') {
                                        trueAns = stuAnsList[j].stuAnswer;
                                    }
                                }
                                var fla = String.fromCharCode(flag);
                                var anlId = "'" + ansList[j].id + "'";


                                $('#content').append(' <li style="list-style: none">' +
                                    ' <div class="test_content_nr_main" id=" ' + data[i].id + '"' +
                                    '>' +
                                    '<div class="content" id="content1" name="' + data[i].id + '"' +
                                    ' title="' + data[i].tQuestiontemplateNum + ';' + data[i].id + ';' + ansList[j].id + '">' +
                                    ' <div style="display:inline-block;vertical-align:top;padding-top: 15px; padding-left:35px">' + "答:" + '</div>' +
                                    '<textarea name="title" rows="30" cols="80" id="' + ansList[j].id + '"' +
                                    // '<textarea name="title" rows="30" cols="80" id="'+contents+'"' +
                                    'style="margin: 0px; width: 516px; height: 145px;"' +
                                    'onchange=' + '"anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlId + ', ' + type + ')">' + trueAns + ' ' +
                                    '</textarea>' +
                                    '</div></div></li></ul></div></div>');
                                // CKEDITOR.replace("content10");
                            }
                        }

                        if (data[i].tQuestiontemplateNum == "5" && data[i].titleTypeNum == 7 && data[i].stuOptionExamanswerList != "") {
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
                                    if (stuAnsList[0].stuAnswer != null || typeof (stuAnsList[0].stuAnswer) === "undefined") {
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
                                    '<textarea name="title" rows="30" cols="80" id="' + ansList[j].id + '"' +
                                    // '<textarea name="title" rows="30" cols="80" id="'+contents+'"' +
                                    'style="margin: 0px; width: 516px; height: 145px;"' +
                                    'onchange=' + '"anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlId + ', ' + type + ')">' + trueAns + ' ' +
                                    '</textarea>' +
                                    '</div></div></li></ul></div></div>');

                            }
                        }
                    }
                    $("#sumb").append('<button class="btn btn-sm btn-danger" style="width:100%; " id="issubmitsave" onclick="issubmitsave()">' +
                        '<i class="ace-icon glyphicon glyphicon-check bigger-120"></i>' +
                        '&nbsp;提&nbsp;&nbsp;交' +
                        '</button>' +
                        '<br/>');
                }

            });
        };

        function deleteImage(i) {
            var num = i.split(",");
            var fileId = num[0];
            var stoption = num[1];
            $.ajax({
                url: "http://localhost:8081/storage/deleteExamFile?fileId="+fileId+"&&stoption="+stoption+"&&testPaperOneId="+paperId+"&&studentId="+studentId,
                type: "get",
                datatype: "json",
                success:function (req) {
                    alert("删除成功！");
                    $.ajax({
                        url: "http://localhost:8080/school/onlineExam/testPaperOne/deleteFileId?fileId="+fileId+"&&stoption="+stoption+"&&testPaperOneId="+paperId+"&&studentId="+studentId,
                        type: "post",
                        datatype: "json",
                        success:function (req) {

                        },
                        error:function () {

                        }
                    })
                    window.location = "chapterExam?testPaperOneId="+paperId+"&&studentId="+studentId+"&&tutId="+tutId
                },
                error:function () {
                    alert("删除失败！");
                }
            })

        }
        // function showImages(i) {
        //     var img = new Image();
        //     img = $("#divUL").find("img[id='img"+i+"']").attr("src");
        //     var imgHtml = "<img src='" + $("#divUL").find("img[id='img"+i+"']").attr("src")  + "' />";
        //     //捕获页
        //     layer.open({
        //         type: 1,
        //         shade: 0.3,
        //         shadeClose: true,
        //         title: false, //不显示标题
        //         area:['1000px','500px'],
        //         // area: [img.width + 'px', img.height+'px'],
        //         content: imgHtml, //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
        //         cancel: function () {
        //             //layer.msg('捕获就是从页面已经存在的元素上，包裹layer的结构', { time: 5000, icon: 6 });
        //         }
        //     });
        //
        // }

        //  提交事件
        function issubmitsave() {
            options1 = new Array();
            for (var i = 0; i < tqg; i++) {
                var option = {
                    'answerType': questiontypeArray[i],
                    'testPaperOneId': paperId,
                    'testPaperQuestionId': questionidArray[i],
                    'stuOptionAnswer': anwseridArray[i],
                    'stuAnswer': answerstrArray[i],
                    'paperId': paperId,
                    'studentId': studentId,
                    'tutId': tutId

                }
                options1.push(option);
            }
            var statu = confirm("注意：提交后不能继续作答，表示要离开考场，确定要提交吗？");//在js里面写confirm，在页面中弹出提示信息。
            if (!statu) {

                return false;
            } else {

                $("#content").empty();
                $("#sumb").empty();
                $.ajax({
                    type: "post",
                    url: "http://lxweixin.free.idcfengye.com/wx/exam/chapterTest/submitPaperWork/"+studentId,
                    dataType: "json",
                    contentType: "application/json;charset-UTF-8",
                    data: JSON.stringify(options1),
                    error: function (request) {
                        // window.close();
                        $.modal.alertError("提交失败!");
                        return false;
                    },
                    success: function (data) {
                        $.modal.alertSuccess("提交成功!");
                        setTimeout(function () {
                            // window.close();
                        },3000);
                        if ("" == data) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                <#--$.ajax({-->
                <#--    type: "POST",-->
                <#--    dataType: "json",-->
                <#--    contentType: "application/json;charset=UTF-8",-->
                <#--    url: "${ctx}/school/onlineExam/userExam/setState",-->
                <#--    data:-->
                <#--        JSON.stringify(options1),-->
                <#--    //     {-->
                <#--    //     'paperId': paperId,-->
                <#--    //     'studentId': studentId,-->
                <#--    //     'tutId': tutId-->
                <#--    // },-->
                <#--    error: function (data) {-->
                <#--        $.modal.alertSuccess("提交成功!");-->
                <#--        setTimeout(function () {-->
                <#--        window.close();-->
                <#--        },2000);-->
                <#--        document.write("提交失败，请刷新提交");-->
                <#--    },-->
                <#--    success: function (data) {-->

                <#--        document.write("提交成功");-->
                <#--    }-->
                <#--});-->


                closeWebSocket();

            }

        };

        function closePaper() {
            window.close();
        };

        function anwserchange(questionnum, questionid, anwserid, questiontype) {
            anwserstr = "";
            if (questionnum == 1) {
                var inputId = "answerId" + "'" + anwserid + "'";
                var stuanwser = "";
                stuanwser = document.getElementById(inputId).value;
                $("input[title='" + questionid + "']").each(function () {
                    if (this.checked) {
                        anwserstr += stuanwser + ";";
                    } else {
                        anwserstr += "0;";
                    }
                });
            } else if (questionnum == 2) {
                $("input[title='" + questionid + "']").each(function () {
                    if (this.checked) {
                        anwserstr += this.value + ";";
                    } else {
                        anwserstr += "0;";
                    }
                });
            } else if (questionnum == 3) {
                $("textarea[title='" + questionid + "']").each(function () {
                    if (this.value != null && "" != this.value) {
                        anwserstr += this.value + ";";
                    } else {
                        anwserstr += "0;";
                    }
                });
            } else if (questionnum == 4) {
                var result = $("input[title='" + questionid + "']").val();
                if (result == 'F') {
                    $("input[title='" + questionid + "']").val('T');
                    result = 'T';
                } else {
                    $("input[title='" + questionid + "']").val('F');
                    result = 'F';
                }
                anwserstr += result + ";";

            } else if (questionnum == 5) {
                var edStuanwser = document.getElementById(anwserid).value;
                // var edStuanwser = CKEDITOR.instances.content10.document.getBody().getText();

                anwserstr = edStuanwser + ";";
            }
            testAnswer(questionnum, questionid, anwserid, anwserstr, tqg, questiontype);
        }

        function testAnswer(questionnum, questionid, anwserid, anwserstr, index, questiontype) {
            //  去重
            if (!questionidArray.includes(questionid)) {
                questionnumArray[index] = questionnum;
                answerstrArray[index] = anwserstr;
                questionidArray[index] = questionid;
                anwseridArray[index] = anwserid;
                questiontypeArray[index] = questiontype;
                tqg++;
            } else {
                var index = questionidArray.indexOf(questionid);
                questionnumArray[index] = questionnum;
                answerstrArray[index] = anwserstr;
                anwseridArray[index] = anwserid;
                questiontypeArray[index] = questiontype;
            }
        }

        /**
         * 定义提交对象
         */
        var options;
        var options1;

        function saveOrUpdateAnswer() {
            options = new Array();
            for (var i = 0; i < tqg; i++) {
                var option = {
                    'answerType': questiontypeArray[i],
                    'testPaperOneId': paperId,
                    'testPaperQuestionId': questionidArray[i],
                    'stuOptionAnswer': anwseridArray[i],
                    'stuAnswer': answerstrArray[i]
                }
                options.push(option);
            }

            $.ajax({
                type: "post",
                url: "${ctx}/school/onlineExam/chapterTest/saveExamWorkAnswer",
                dataType: "json",
                contentType: "application/json;charset-UTF-8",
                data: JSON.stringify(options),
                error: function (request) {
                    $.modal.alertError("保存答案失败!");
                    return false;
                },
                success: function (data) {
                    if ("" == data) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        }

        // function webso() {
        //     //心跳机制
        //     var msg = "HeartBeat";
        //     var lockReconnect = false;//避免重复连接
        //     var host = window.location.host;
        //     var wsUrl = "ws://127.0.0.1:8089/wsk";
        //     var tt;
        //
        //     if ('WebSocket' in window) {
        //         createWebSocket();
        //     } else {
        //         $.modal.alertError('当前浏览器 不支持 WebSocket');
        //     }
        //
        //     function createWebSocket() {
        //         try {
        //             ws = new WebSocket(wsUrl);
        //             init();
        //         } catch (e) {
        //             reconnect(wsUrl);
        //         }
        //     };
        //
        //     function init() {
        //         ws.onclose = function () {
        //             reconnect(wsUrl);
        //             onLineCheck();
        //         };
        //         // ws.onerror = function () {
        //         //     $.modal.alertError('网络发生异常了');
        //         //     reconnect(wsUrl);
        //         // };
        //         ws.onopen = function () {
        //             //心跳检测重置
        //             ws.send(msg);
        //             heartCheck.start();
        //         };
        //         ws.onmessage = function (event) {
        //             //拿到任何消息都说明当前连接是正常的
        //             heartCheck.reset();
        //         }
        //     };
        //
        //     function reconnect(url) {
        //         if (lockReconnect) {
        //             return;
        //         }
        //         ;
        //         lockReconnect = true;
        //         //没连接上会一直重连，设置延迟避免请求过多
        //         tt && clearTimeout(tt);
        //         tt = setTimeout(function () {
        //             createWebSocket(url);
        //             lockReconnect = false;
        //         }, 4000);
        //     };
        //
        //     //心跳检测
        //     var heartCheck = {
        //         timeout: 6000,//一次心跳
        //         timeoutObj: null,
        //         serverTimeoutObj: null,
        //         reset: function () {
        //             clearTimeout(this.timeoutObj);
        //             clearTimeout(this.serverTimeoutObj);
        //             this.start();
        //         },
        //         start: function () {
        //             var self = this;
        //             this.timeoutObj = setTimeout(function () {
        //                 ws.send(msg);
        //                 // saveOrUpdateAnswer();
        //                 self.serverTimeoutObj = setTimeout(function () {
        //                     ws.close();
        //                     //如果onclose会执行reconnect，我们执行ws.close()就行了.如果直接执行reconnect 会触发onclose导致重连两次
        //                 }, self.timeout)
        //             }, this.timeout)
        //         },
        //     };
        //
        //     function reconnectSocket() {
        //         if ('ws' in window) {
        //             ws = new ReconnectingWebSocket(wsUrl);
        //         } else if ('MozWebSocket' in window) {
        //             ws = new MozWebSocket(wsUrl);
        //         } else {
        //             ws = new WebSocket(wsUrl);
        //
        //         }
        //     };
        //
        //     function onLineCheck() {
        //         Offline.check();
        //         if (!this.socketStatus) {
        //             if (Offline.state === 'up' && ws.reconnectAttempts > ws.maxReconnectInterval) {
        //                 window.location.reload();
        //             }
        //             reconnectSocket();
        //         } else {
        //             $.modal.alertSuccess('网络连接成功！');
        //             ws.send("HeartBeat");
        //         }
        //     };
        // }
        //
        // //关闭WebSocket连接
        // function closeWebSocket() {
        //     ws.close();
        // };
        (function () {
            //心跳机制
            var msg = "HeartBeat";
            var lockReconnect = false;//避免重复连接
            var host = window.location.host;
            var wsUrl = "ws://127.0.0.1:8080/wsk";
            var tt;

            if ('WebSocket' in window) {
                createWebSocket();
            } else {
                $.modal.alertError('当前浏览器 不支持 WebSocket');
            }

            function createWebSocket() {
                try {
                    ws = new WebSocket(wsUrl);
                    init();
                } catch (e) {
                    reconnect(wsUrl);
                }
            };

            function init() {
                ws.onclose = function () {
                    reconnect(wsUrl);
                    onLineCheck();
                };
                ws.onerror = function () {
                    // $.modal.alertError('网络发生异常了，请重新进入！');
                    reconnect(wsUrl);
                    // setTimeout(function () {
                    //     window.close();
                    // },3000);

                };
                ws.onopen = function () {
                    //心跳检测重置
                    ws.send(msg);
                    heartCheck.start();
                };
                ws.onmessage = function (event) {
                    //拿到任何消息都说明当前连接是正常的
                    heartCheck.reset();
                }
            };

            function reconnect(url) {
                if (lockReconnect) {
                    return;
                }
                ;
                lockReconnect = true;
                //没连接上会一直重连，设置延迟避免请求过多
                tt && clearTimeout(tt);
                tt = setTimeout(function () {
                    createWebSocket(url);
                    lockReconnect = false;
                }, 6000);
            };

            //心跳检测
            var heartCheck = {
                timeout: 6000,//一次心跳
                timeoutObj: null,
                serverTimeoutObj: null,
                reset: function () {
                    clearTimeout(this.timeoutObj);
                    clearTimeout(this.serverTimeoutObj);
                    this.start();
                },
                start: function () {
                    var self = this;
                    this.timeoutObj = setTimeout(function () {
                        ws.send(msg);
                        saveOrUpdateAnswer();
                        self.serverTimeoutObj = setTimeout(function () {
                            ws.close();
                            //如果onclose会执行reconnect，我们执行ws.close()就行了.如果直接执行reconnect 会触发onclose导致重连两次
                        }, self.timeout)
                    }, this.timeout)
                },
            };

            function reconnectSocket() {
                if ('ws' in window) {
                    ws = new ReconnectingWebSocket(wsUrl);
                } else if ('MozWebSocket' in window) {
                    ws = new MozWebSocket(wsUrl);
                } else {
                    ws = new WebSocket(wsUrl);

                }
            };

            function onLineCheck() {
                Offline.check();
                if (!this.socketStatus) {
                    // $.modal.alertError('网络连接已断开！');
                    if (Offline.state === 'up' && ws.reconnectAttempts > ws.maxReconnectInterval) {
                        window.location.reload();
                    }
                    reconnectSocket();
                } else {
                    $.modal.alertSuccess('网络连接成功！');
                    ws.send("HeartBeat");
                }
            };
        })();

        //关闭WebSocket连接
        function closeWebSocket() {
            ws.close();

        };
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
        window.onload=function(){
            var paperType = "${testPaper.type}";
            if(paperType=="2"){
                $('#div1').append('' +
                    '<strong>' +
                    ' <input style="text-align: right" type="text" name="mss" id="mss"/>' +
                    '  </strong>' +
                    '');
                only();
            }
        }
        var pTime = document.getElementById("paperTime").value;
        var counttime=(parseInt(pTime))*60;
        function only(){
            if(counttime>=0){
                var ms = counttime%60;
                var mis = Math.floor(counttime/60);
                if(mis>=60){
                    var hour=Math.floor(mis/60);

                    mis=Math.floor((counttime-hour*60*60)/60);

                    document.getElementById("mss").value=hour+"小时"+mis+"分钟"+ms+"秒";
                }else if(mis>=1){
                    document.getElementById("mss").value=mis+"分钟"+ms+"秒";
                }else{
                    document.getElementById("mss").value=ms+"秒";
                }
                counttime--;
                vartt =  window.setTimeout("only()",1000);
            }else{
                window.clearTimeout(vartt);
                window.confirm("时间到，请点击确定提交");
                issubmitsave()

            }


        }
    </script>
