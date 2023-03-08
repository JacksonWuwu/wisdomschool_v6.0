<#include "/common/style.ftl"/>
<link href="/js/plugins/ueditor/themes/iframe.css" rel="stylesheet" />
<style>
    .div1{
        position:fixed;
        right:0;
        top:5%;
        height:50px;
    }
    #video{
        position:fixed;
        left:5%;
        top:5%;
        height:230px;
        width:250px;
        text-align: left;
        border: 1px seagreen solid;
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

<div class="col-sm-12 select-table table-striped" style="width: 50%;left: 25%">
    <div class="form-horizontal" id="startexamform" >
        <#--<center>-->
        <#--    <img src="/img/images/csxyexam.jpg" width="600px" height="100px"/>-->
        <#--</center>-->
        <#--<center>-->
        <#--    <h1>${testPaper.headline}-->
        <#--    </h1>-->
        <#--</center>-->
        <#--<center>-->
        <#--    <h4>试卷名:${testPaper.testName}&nbsp;总分:${testPaper.score}</h4>-->
        <#--</center>-->
        <#--<center>-->
        <#--    <h5>学号:${stuName} 姓名:${name}</h5>-->
        <#--</center>-->

        <div id="content">
        </div>
        <div class="btn-group btn-corner" style="width:100%; " id="sumb">
        </div>
    </div>
</div>

<div id="right-menu" class="modal aside"  data-body-scroll="false"
     data-offset="true" data-placement="right" data-fixed="true"
     data-backdrop="invisible" tabindex="-1" >
    <div class="modal-dialog" style="top: 0px;">
        <div class="modal-content">
            <div class="modal-header no-padding">
                <div class="table-header btn-info">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-hidden="true">
                        <#--                            <span class="white">&times;</span>-->
                    </button>
                    考试目录
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

<#include "/common/stretch.ftl"/>
<!-- 模态框移动 -->
<!-- 配置文件 -->
<script type="text/javascript" src="/js/plugins/ueditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="/js/plugins/ueditor/ueditor.all.js"></script>
<script type="text/javascript" src="/js/plugins/ueditor/ueditor.parse.js"></script>
<script type="text/javascript"  src="/js/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript">
    var ansList = "";//试题答案
    var tqg = 0;//是否有答案标记
    var anwserstr;
    //答案相关数组
    var questiontypeArray = new Array(100);
    //题型数组
    var questionnumArray = new Array(100);
    var answerstrArray = new Array(100);
    var questionidArray = new Array(100);
    var anwseridArray = new Array(100);
    var  questionsId = "";
    var studentQusScore  ="";
    $(function () {
        initPaper();
    });


    function initPaper() {
        $.ajax({
            type: "post",
            dataType: 'json',
            url: "/school/onlineExam/testPaperOneList/paperPreviewData?id=" + ${id},
            error: function (request) {
                $.modal.alertError("初始化试卷失败!");
            },
            success: function (data) {
                //目录部分
                var str2 = "abc";
                var mulu = "";
                var numitem = 0;
                var onesum_mulu = 0;
                //题目部分
                var onesum = 0;
                var num = 0;
                var sum = 1;
                var str = "";
                var type = "";
                for (var i = 0; i < data.length; i++) {
                    num += 1;
                    var tQuestiontemplateNum = "'" + data[i].tQuestiontemplateNum + "'"; //题型号
                    var dataId = "'" + data[i].id + "'";
                    var type = data[i].titleTypeNum;
                    onesum += 1;
                    var simplifiedChinese = Arabia_To_SimplifiedChinese(onesum);
                    str = data[i].titleTypeName;
                    var qid = data[i].id;
                    let subjectId = "subject" + data[i].id;//题目的id，用于ueditor初始化题目
                    $('#content').append('<div id="' + data[i].tQuestiontemplateNum + '"  class="test_content" >' +
                        ' <div class="test_content_title" >' +
                        '<strong class="content_lit" style="float: left">' + simplifiedChinese + '、' + data[i].titleTypeName +
                        '</strong></div></div>' +
                        '  <div id="' + qid + '"' +
                        'class="test_content_nr" >' +
                        '<h5><strong><div class="test_content_nr_tt" >' +
                        '<i>' + num + '</i>' +
                        '<p>(' + data[i].questionScore + '分)' +
                        '<textarea id="' + subjectId + '"></textarea>' +
                        '</p></div> </strong></h5><div><ul>');
                    initSubjectUeditor(subjectId, data[i].title);

                    //单选题
                    if (data[i].tQuestiontemplateNum == "1") {
                        var flag = 64;
                        ansList = data[i].testpaperOptinanswerList;
                        let thisId1 = "";
                        for (var j = 0; j < ansList.length; j++) {
                            var isCheck = "";
                            thisId1 = ansList[j].id;
                            flag += 1;
                            if (ansList[j].stanswer != "0") {
                                isCheck = "checked"
                            }
                            var fla = String.fromCharCode(flag);
                            var anlId = "'" + ansList[j].id + "'";
                            $('#content').append('' +
                                '<li  style="list-style: none">' +
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
                                ' type="radio"' +
                                ' id="answerId' + anlId + '"' +
                                ' title="' + data[i].id + '"' +
                                ' class="radioOrCheck"' +
                                ' value="' + fla + '" ' +
                                isCheck + '/>' +
                                ' <p class="ue" style="display: inline;">' + fla + '.' + ansList[j].stoption +
                                '</p></label></div></div></li>');

                        }
                    }
                            /**
                             * 多选
                             */

                            if (data[i].tQuestiontemplateNum == "2") {
                                var flag = 64;
                                ansList = data[i].testpaperOptinanswerList;
                                let thisId = "";
                                for (let j = 0; j < ansList.length; j++) {
                                    thisId = ansList[j].id;
                                    flag += 1;
                                    var isCheck = "";
                                    if (ansList[j].stanswer != "0") {
                                        isCheck = "checked"
                                    }
                                    let fla = String.fromCharCode(flag);
                                    $('#content').append('<li style="list-style: none">' +
                                        '<div class="test_content_nr_main" id=" ' + data[i].id + '"' +
                                        '>' +
                                        '<div class="option">' +
                                        ' <label> <input name="form-field-checkbox' + num +'"' +
                                        ' type="checkbox"' +
                                        ' title="' + data[i].id + '"' +
                                        ' value="' + fla + '"' +
                                        ' class="radioOrCheck"' +
                                        isCheck+
                                        '/><span class="ue" style="display: inline;">' +
                                        '<span>' + fla + '.' + ansList[j].stoption +
                                        ' </span></span></label></div></div></li>');
                                }
                            }

                            if (data[i].tQuestiontemplateNum == "3") {
                                ansList = data[i].testpaperOptinanswerList;
                                var Optionnum = 0;
                                for (var j = 0; j < ansList.length; j++) {
                                    Optionnum += 1;
                                    var trueAns = ansList[j].stoption;
                                    var ewnum = Arabia_To_SimplifiedChinese(Optionnum);
                                    $('#content').append('<li style="list-style: none">' +
                                        '<div class="test_content_nr_main" style="padding-left:40px" id=" ' + data[i].id + '"' +
                                        '>' +
                                        '  <div style="display:inline-block;vertical-align:top;padding-top: 15px;">' + '第' + ewnum + '空:' +
                                        '</div>' +
                                        '<div style="display:inline-block;vertical-align:top;max-width: 400px;padding: 10px 0;">' +
                                        '<input ' +
                                        ' class="autosize-transition form-control"' +
                                        ' style="width: 400px;max-width:400px; height: 35px;font-family:' +
                                        ' inherit;font-style: normal;font-weight: normal;text-align: left;"'+
                                        ' value="'+trueAns+'"'+
                                        '/>' +
                                        '</div></div></li>');
                                }
                            }


                        //     if (data[i].tQuestiontemplateNum == "4" && data[i].stuOptionExamanswerList.length == 0) {
                        //         var flag = 64;
                        //         var Optionnum = 0;
                        //         ansList = data[i].testpaperOptinanswerList;
                        //         stuAnsList = data[i].stuOptionExamanswerList;
                        //         for (var j = 0; j < ansList.length; j++) {
                        //             Optionnum += 1;
                        //             flag += 1;
                        //             var trueAns = "";
                        //             if (stuAnsList.length != 0) {
                        //                 if (stuAnsList[j].stuAnswer != '0') {
                        //                     trueAns = 'checked';
                        //                 }
                        //             }
                        //             var fla = String.fromCharCode(flag);
                        //             var anlId = "'" + ansList[j].id + "'";
                        //             $('#content').append('<div id=" ' + data[i].id + '"' +
                        //                 '>' +
                        //                 '<li style="list-style: none">' +
                        //                 '<label style="margin-left: 40px;">' +
                        //                 '<input name="switch-field-1" class="ace ace-switch ace-switch-6"' +
                        //                 ' title="' + data[i].id + '"' +
                        //                 ' id="answerId' + anlId + '"' +
                        //                 ' value="F"' +
                        //                 ' onchange=' + '"anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlId + ', ' + type + ') "' + trueAns + ' ' +
                        //                 ' type="checkbox"/> <span class="lbl"></span> </label></li></div></ul></div></div>');
                        //         }
                        //     }
                        //     if (data[i].tQuestiontemplateNum == "4" && data[i].stuOptionExamanswerList.length > 0) {
                        //         let anlIdscore4 = "";
                        //         let flag = 64;
                        //         let Optionnum = 0;
                        //         ansList = data[i].testpaperOptinanswerList;
                        //         stuAnsList = data[i].stuOptionExamanswerList;
                        //         let pdIstrue = false;
                        //         let stupdScore = 0;
                        //         for (let j = 0; j < ansList.length; j++) {
                        //             if (stuAnsList.length != 0) {
                        //                 if (stuAnsList[0].stuAnswer.indexOf(ansList[j].stanswer) != -1) {
                        //                     pdIstrue = true;
                        //                 }
                        //             }
                        //         }
                        //         for (let j = 0; j < ansList.length; j++) {
                        //             anlIdscore4 = "'" + ansList[j].id + "'";
                        //             Optionnum += 1;
                        //             flag += 1;
                        //             let trueAns = "";
                        //             if (stuAnsList.length != 0) {
                        //                 if (stuAnsList[j].stuAnswer == 'T;') {
                        //                     trueAns = 'checked';
                        //                 }
                        //
                        //             }
                        //             let isAns = "×";
                        //             if (ansList[j].stanswer == "T") {
                        //                 isAns = "√";
                        //             }
                        //             var stuGetScore = "";
                        //             if (stuAnsList != null) {
                        //                 stuGetScore = stuAnsList[0].questionScore;
                        //             }
                        //             if (pdIstrue == true && stuGetScore == "") {
                        //                 stupdScore = data[i].questionScore;
                        //             } else if (stuGetScore != "") {
                        //                 stupdScore = stuGetScore;
                        //             } else {
                        //                 stupdScore = "0";
                        //             }
                        //
                        //
                        //             let fla = String.fromCharCode(flag);
                        //             let anlId = "'" + ansList[j].id + "'";
                        //             $('#content').append('<div id=" ' + data[i].id + '"' +
                        //                 '>' +
                        //                 '<li style="list-style: none">' +
                        //                 '<label style="margin-left: 40px;">' +
                        //                 '<input name="switch-field-1" class="ace ace-switch ace-switch-6"' +
                        //                 ' title="' + data[i].id + '"' +
                        //                 ' id="answerId' + anlId + '"' +
                        //
                        //                 ' value="F"' + trueAns + ' ' +
                        //                 ' onchange=' + '"anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlId + ', ' + type + ') "' +
                        //                 'type="checkbox"/> <span class="lbl"></span> </label></li>' +
                        //                 '</ul></div>');
                        //         }
                        //     }
                        //
                            if (data[i].tQuestiontemplateNum == "5") {
                                ansList = data[i].testpaperOptinanswerList;
                                let answerAreaId = "answerArea" + data[i].id;
                                $('#content').append(
                                    ' <h3>答案</h3>' +
                                    '<div ><textarea id="' + answerAreaId + '" >' +
                                    '</textarea></div>'
                                );
                                initSubjectUeditor(answerAreaId, ansList[0].stoption);

                            }
                    $("input").attr("disabled",'disabled');
                    $("textarea").attr("disabled",'disabled');
                    }
                }
        });

    }
    UE.Editor.prototype._bkGetAtionUrl=UE.Editor.prototype.getActionUrl;
    var actions="uploadimage,uploadscrawl,catchimage,uploadfile".split(",");
    UE.Editor.prototype.getActionUrl=function(action){
        if(actions.indexOf(action) == -1){
            return this._bkGetAtionUrl.call(this,action);
        }else{
            // return "/upload?token="+localStorage.getItem("token");
            return "/upload"+"?token="+localStorage.getItem("token")
        }
    }
    //题目编辑器配置
    var SubjectUeditorConfig={
        toolbars: [],
        wordCount:false,          //是否开启字数统计
        maximumWords:50000,
        elementPathEnabled : false
    };//作答区编辑器配置
    var AnswerAreaUeditorConfig={
        initialFrameHeight : 400,
        toolbars: [[
            'fullscreen', '|', 'source', '|', 'undo', 'redo', 'bold', 'italic',
            'underline','fontborder', 'backcolor', 'fontsize', 'fontfamily',
            '|','justifyleft', 'justifyright','justifycenter', 'justifyjustify',
            '|','strikethrough','superscript', 'subscript', 'removeformat',
            'formatmatch','autotypeset', 'blockquote', 'pasteplain', '|',
            'forecolor', 'backcolor','insertorderedlist', 'insertunorderedlist',
            'selectall', 'cleardoc', 'link', 'unlink',
            '|','simpleupload',
            '|'
        ]],
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

    //作答区编辑器初始化
    function initAnswerAreaUeditor(AnswerAreaId,stuAnswer,tQuestiontemplateNum,dataId,anlId,type){
        var ue=UE.getEditor(AnswerAreaId,AnswerAreaUeditorConfig);
        setTimeout(function (){
            ue.execCommand('drafts');
        },500)
        ue.addListener("blur",function(){//当失去焦点时保存数据
            anwserstr=""
            anwserstr +=ue.getContent();
            testAnswer(tQuestiontemplateNum, dataId, anlId, anwserstr, tqg, type);
        });
    }




    //导出试卷
    // function exportExam(id,paperId){
    //     console.log(id)
    //     console.log(paperId)
    //     $.ajax({
    //         url: "/school/onlineExam/testPaperOne/exportExam?id="+id+"&&paperId="+paperId,
    //         type: "post",
    //         datatype: "json",
    //         success:function (req) {
    //             console.log("导出成功")
    //         },
    //         error:function () {
    //             console.log("导出失败")
    //         }
    //     })
    //
    // }




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

</script>
