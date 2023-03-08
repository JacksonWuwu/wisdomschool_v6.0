<#include "/common/style.ftl"/>
<div id="divpx2">
    <div class="col-sm-12 select-table table-striped" style="width: 50%;left:25%">
        <div class="form-horizontal" id="startexamform">
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
            <div id="stuScore">得分：</div>
            <div id="content">
            </div>
            <div class="btn-group btn-corner" style="width:100%; " id="sumb">
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
    var studentId = "${studentId}";//学生ID
    var tutId = "${tutId}";//
    var tqg = 0;//是否有答案标记
    var paperId = "${paperId}";
    var cid = "${chapterId}";
    var courseId = "${testPaper.coursrId}";
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
    $(function () {
        initPaper();
    });

    function initPaper() {
        webso();
        $.ajax({
            type: "post",
            dataType: 'json',
            url: "/school/onlineExam/chapterTest/startCoursePaper?paperId=" + paperId + "&&studentId=" + studentId,
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
                        $('#content').append('<div id="' + data[i].tQuestiontemplateNum + '"  class="item dd2-content btn-info no-hover">' +
                                '<strong>' + simplifiedChinese + '、' + data[i].titleTypeName +
                                '</strong></div>' +
                                '  <div id="' + qid + '"' +
                                'class="item page-content">' +
                                '<h5><strong><div>' +
                                '<div style="display:inline-block;vertical-align:top;">' + num + '.' +
                                ' </div>' +
                                '<div style="display:inline-block;vertical-align:top;max-width: 680px; word-wrap: break-word">' + data[i].title +
                                '</div></div> </strong></h5><div><ul>');
                    } else {
                        var qid2 = data[i].id;
                        $('#content').append('<div id="' + qid2 + '"' +
                                'class="item page-content">' +
                                '<h5><strong><div>' +
                                '<div style="display:inline-block;vertical-align:top;">' + num + '.' +
                                '</div>' +
                                '<div style="display:inline-block;vertical-align:top;max-width: 680px; word-wrap: break-word">' + data[i].title +
                                '</div></div></strong></h5><div><ul>');
                    }

                    if (data[i].tQuestiontemplateNum == "1") {
                        var flag = 64;
                        var ansList = data[i].testpaperOptinanswerList;
                        var stuAnsList = data[i].stuOptionanswerList;
                        for (var j = 0; j < ansList.length; j++) {
                            flag += 1;
                            var trueAns = "";
                            if (stuAnsList.length!= 0){
                                if (stuAnsList[j].stuAnswer!='0'){
                                    trueAns = 'checked';
                                }
                            }
                            var fla = String.fromCharCode(flag);
                            var anlId = "'" + ansList[j].id + "'";

                            $('#content').append('<li style="list-style: none">' +
                                    '<div class="divhuiactive row" id=" ' + data[i].id + '"' +
                                    '>' +
                                    '<div class="radio">' +
                                    '<label>' +
                                    '<input name="form-field-radio' + num + '"' +
                                    ' id="answerId' + anlId + '"' +
                                    'type="radio" ' +
                                    'title="' + data[i].id + '"' +
                                    'value="' + fla + '"' +
                                    'class="ace"' +
                                    'onclick=' +
                                    '"anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlId + ', ' + type +') "' + trueAns + '>' +
                                    '<span class="lbl"  style="margin-left: 20px">' +
                                    '<span>' + fla + '.' + ansList[j].stoption +
                                    '</span></span></label></div> </div></li> </ul></div></div>');
                        }
                    }
                    if (data[i].tQuestiontemplateNum == "2") {
                        var flag = 64;
                        var ansList = data[i].testpaperOptinanswerList;
                        var stuAnsList = data[i].stuOptionanswerList;
                        for (var j = 0; j < ansList.length; j++) {
                            flag += 1;
                            var trueAns = "";

                            if (stuAnsList.length != 0) {
                                if (stuAnsList[j].stuAnswer != '0') {
                                    trueAns = 'checked';
                                }
                            }
                            var fla = String.fromCharCode(flag);
                            var anlId = "'" + ansList[j].id + "'";
                            $('#content').append('<li style="list-style: none">' +
                                    '<div class="divhuiactive row id=" ' + data[i].id + '"' +
                                    '>' +
                                    '<div class="checkbox">' +
                                    '<label style="padding-left: 30">' +
                                    ' <input name="form-field-checkbox' + num + '"' +
                                    ' id="answerId' + anlId + '"' +
                                    ' title="' + data[i].id + '"' +
                                    'value="' + fla + '"' +
                                    'onchange=' +
                                    '"anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlId + ', ' + type +') "' + trueAns +
                                    ' type="checkbox"' +
                                    ' class="ace"/>' +
                                    '<span class="lbl"><span>' + fla + '.' + ansList[j].stoption +
                                    ' </span></span></label></div></li></ul></div></div>');

                        }
                    }
                    if (data[i].tQuestiontemplateNum == "3") {
                        ansList = data[i].testpaperOptinanswerList;
                        stuAnsList = data[i].stuOptionanswerList;
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
                                    '<div class="divhuiactive row" style="padding-left:40px" id=" ' + data[i].id + '"' +
                                    '>' +
                                    '  <div style="display:inline-block;vertical-align:top;padding-top: 15px;">' + '第' + ewnum + '空.' +
                                    '</div>' +
                                    '<div style="display:inline-block;vertical-align:top;max-width: 685px;padding: 10px 0;">' +
                                    '<textarea ' +
                                    'title="' + data[i].id + '"' +
                                    'class="autosize-transition form-control"' +
                                    'id="answerId' + anlId + '"' +
                                    'value="F"' +
                                    'onchange=' +
                                    '"anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlId + ', ' + type +') "' +
                                    'style="width: 630px;max-width:630px; height: 35px;font-family:' +
                                    ' inherit;font-style: normal;font-weight: normal;text-align: left;">' + trueAns +
                                    '</textarea>' +
                                    '</div></div></li></ul></div></div>');
                        }
                    }

                    if (data[i].tQuestiontemplateNum == "4") {
                        var flag = 64;
                        var Optionnum = 0;
                        ansList = data[i].testpaperOptinanswerList;
                        stuAnsList = data[i].stuOptionanswerList;
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
                    if (data[i].tQuestiontemplateNum == "5") {
                        var flag = 64;
                        var Optionnum = 0;
                        ansList = data[i].testpaperOptinanswerList;
                        stuAnsList = data[i].stuOptionanswerList;
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
                                    ' <div class="divhuiactive row" id=" ' + data[i].id + '"' +
                                    '>' +
                                    '<div class="content" id="content1" name="' + data[i].id + '"' +
                                    ' title="' + data[i].tQuestiontemplateNum + ';' + data[i].id + ';' + ansList[j].id + '">' +
                                    ' <div style="display:inline-block;vertical-align:top;padding-top: 15px; padding-left:35px">' + "答:" + '</div>' +
                                    '<textarea name="title" rows="5" id="' + ansList[j].id + '"' +
                                    'style="margin: 0px; width: 569px; height: 152px;"' +
                                    'onchange=' + '"anwserchange(' + tQuestiontemplateNum + ',' + dataId + ',' + anlId + ', ' + type +')">' + trueAns + ' ' +
                                    ' </textarea>' +
                                    '</div></div></li></ul></div></div>');
                        }
                    }
                }

                $("#sumb").append('<button class="btn btn-sm btn-danger" style="width:100%; " id="issubmitsave" onclick="issubmitsave()">' +
                        '<i class="ace-icon glyphicon glyphicon-check bigger-120"></i>' +
                        '&nbsp;提&nbsp;&nbsp;交&nbsp;&nbsp;作&nbsp;&nbsp;页' +
                        '</button>' +
                        '<br/>');
            }
        });
    };

    //  提交事件
    function issubmitsave() {
        var statu = confirm("确认要提交吗!!");//在js里面写confirm，在页面中弹出提示信息。
        if (!statu) {

            return false;
        } else {
            ws.close();
            $("#content").empty();
            $("#sumb").empty();
            saveOrUpdateAnswer();// 提交
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

    function saveOrUpdateAnswer() {
        options = new Array();
        for (var i = 0; i < tqg; i++) {
            var option = {
                'answerType': questiontypeArray[i],
                'testPaperId': paperId,
                'testPaperQuestionId': questionidArray[i],
                'stuOptionAnswer': anwseridArray[i],
                'stuAnswer': answerstrArray[i]
            }
            options.push(option);
        }
        $.ajax({
            type: "post",
            url: "/school/onlineExam/chapterTest/saveChapterWorkAnswer",
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

    function webso() {
        //心跳机制
        var msg = "HeartBeat";
        var lockReconnect = false;//避免重复连接
        var host = window.location.host;
        var wsUrl = "ws://127.0.0.1:8089/wsk";
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
            // ws.onerror = function () {
            //     $.modal.alertError('网络发生异常了');
            //     reconnect(wsUrl);
            // };
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
            }, 4000);
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
                    // saveOrUpdateAnswer();
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
                if (Offline.state === 'up' && ws.reconnectAttempts > ws.maxReconnectInterval) {
                    window.location.reload();
                }
                reconnectSocket();
            } else {
                $.modal.alertSuccess('网络连接成功！');
                ws.send("HeartBeat");
            }
        };
    }

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

</script>
