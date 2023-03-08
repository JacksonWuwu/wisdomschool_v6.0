<#include "/common/style.ftl"/>
<link rel="stylesheet" href="/js/onlineExam/css/demo.css" type="text/css">
<link rel="stylesheet" href="/js/onlineExam/css/zTreeStyle/zTreeStyle.css" type="text/css">
<style type="text/css">
    #tj {
          min-height: 80px;
          overflow-y: auto;
          max-height: 850px;
      }
    #stu {
        min-height: 300px;
        overflow-y: auto;
        max-height: 588px;
    }
</style>
<div class="row" id="buileTest">
    <div class="col-xs-12">
        <div class="tabbable">
            <div class="tab-content no-border padding-24">

                <div class="page-content">

                    <div class="page-header">
                        <h1>试卷选题步骤：①选择难度》②选择学生》③设置题目》④选择-》
                            <button class="btn btn-info" type="button" id="autogeneration"
                                    onclick="autogeneration()">
                                <i class="ace-icon fa fa-check bigger-110"></i>
                                生成试卷
                            </button>
                        </h1>
                    </div><!-- /.page-header -->

                    <div class="row">
                        <div class="col-xs-12">
                            <div class="form-horizontal ">

                                <div class="form-group">
                                    <div id="tj" class="col-xs-12 col-sm-6 widget-container-col"
                                         style="width:50%;margin:0px;min-width:600px; ">
                                        <div class="form-group" style="width:50%; height:40px;display:static;">
                                            <div style="min-width:310px;position: absolute;left:0;">
                                                <label>
                                                    试卷总体难度:
                                                </label>
                                                <select style="width:220px;" id="testDifficulty">
                                                    <option value="">请选择难度</option>
                                                    <option value="1">容易</option>
                                                    <option value="2">较易</option>
                                                    <option value="3">中等</option>
                                                    <option value="4">较难</option>
                                                    <option value="5">困难</option>
                                                </select>
                                            </div>
                                            <div style="width:310px;min-width:310px;position: absolute;right:0;">
                                            </div>
                                        </div>
                                        <div class="form-group" style="width:50%; height:40px">
                                            <div style="min-width:322px;">
                                            </div>
                                            <div style="width:310px;min-width:310px;position: absolute;right:580;top:8px">
                                            </div>
                                        </div>
                                        <div id="tj" class="widget-box widget-color-blue">
                                            <div class="widget-header">
                                                <h5 class="widget-title bigger lighter" style="float:left;">
                                                    <i class="ace-icon fa fa-table"></i>
                                                    题型选择
                                                </h5>
                                                <div class="widget-toolbar widget-toolbar-light no-border">
                                                    <select id="simple-colorpicker-1" class="hide">
                                                        <option selected="" data-class="blue" value="#307ECC">
                                                            #307ECC
                                                        </option>
                                                        <option data-class="blue2" value="#5090C1">#5090C1
                                                        </option>
                                                        <option data-class="blue3" value="#6379AA">#6379AA
                                                        </option>
                                                        <option data-class="green" value="#82AF6F">#82AF6F
                                                        </option>
                                                        <option data-class="green2" value="#2E8965">#2E8965
                                                        </option>
                                                        <option data-class="green3" value="#5FBC47">#5FBC47
                                                        </option>
                                                        <option data-class="red" value="#E2755F">#E2755F
                                                        </option>
                                                        <option data-class="red2" value="#E04141">#E04141
                                                        </option>
                                                        <option data-class="red3" value="#D15B47">#D15B47
                                                        </option>
                                                        <option data-class="orange" value="#FFC657">#FFC657
                                                        </option>
                                                        <option data-class="purple" value="#7E6EB0">#7E6EB0
                                                        </option>
                                                        <option data-class="pink" value="#CE6F9E">#CE6F9E
                                                        </option>
                                                        <option data-class="dark" value="#404040">#404040
                                                        </option>
                                                        <option data-class="grey" value="#848484">#848484
                                                        </option>
                                                        <option data-class="default" value="#EEE">#EEE</option>
                                                    </select>
                                                </div>
                                                <div class="col-sm-10">
                                                    <div style="width:80%;height:40px;display: inline-block;padding:3px 10px;">
                                                        <div  name="form1"
                                                              style="width:200px;">
                                                            <select NAME="province" id="selectid"
                                                                    style="width:150px;"
                                                                    name="testQuestions.titleTypeId.id">
                                                            </select>
                                                        </div>
                                                        <button id="inbox-tabs" type="button"
                                                                class="btn btn-sm btn-danger"
                                                                onclick="removeOption()"
                                                                style="width:120px;height:35px;padding:0px;top:-32px;left:160px">
                                                            <i class="ace-icon fa fa-paperclip bigger-140"></i>
                                                            添加试卷题型
                                                        </button>

                                                    </div>
                                                </div>
                                            </div>
                                            <!-- 以下表格内容 -->
                                            <div class="widget-body">
                                                <div class="widget-main no-padding">
                                                    <table class="table table-striped table-bordered table-hover">
                                                        <thead class="thin-border-bottom">
                                                        <tr style="text-align:center; font-weight:bold;">
                                                            <td style="width:15%;">
                                                                <i class="ace-icon fa fa-user"></i>
                                                                题型
                                                            </td>
                                                            <td class="hidden-480" style="width:12%;">题型数量</td>
                                                            <td class="hidden-480" style="width:15%;">库存数量</td>
                                                            <td class="hidden-480" style="width:15%;">单题分值</td>
                                                            <td class="hidden-480" style="width:15%;">分值合计</td>
                                                            <td class="hidden-480" style="width:15%;">已选试题</td>
                                                            <td class="hidden-480" style="width:15%;">题库选择</td>
                                                            <td class="hidden-480" style="width:15%;">操作</td>
                                                        </tr>
                                                        </thead>
                                                    </table>
                                                    <div id="" class="hide  message-form ">
                                                        <div id="form-attachments">
                                                            <!-- js弹出内容 -->
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="stu">
                                            <#-- 学生树-->
                                            <div class="step-content pos-rel">
                                                <div class="step-pane active" data-step="1">
                                                    <div class="container" style="width: 700px">
                                                        <a style="margin-left:-19px"
                                                           class="btn btn-primary radius-4"
                                                           onclick="savebt()">
                                                            添加学生
                                                        </a>
                                                        <div class="row">
                                                            <ul id="treeDemo" class="ztree"
                                                                style="margin-top: 10px;margin-left: -7px;float:left;  background: #e7e7e7;"></ul>
                                                            <ul class="list">
                                                                <li style="list-style-type:none;"><p>
                                                                    <ul id="log" class="log"
                                                                        style="height:400px; overflow:auto;float:left; background: #e7e7e7; margin-top: 10px;">
                                                                        <li>当前被选择节点共 <span id="checkCount"
                                                                                            class="highlight_red"></span> 个
                                                                        </li>
                                                                        <li>当前未被选择的节点共 <span id="nocheckCount"
                                                                                             class="highlight_red"></span> 个
                                                                        </li>
                                                                        <li> 所选学生有：
                                                                            <div id="stuName"></div>
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
                                    </div>

                                    <div class="col-xs-12 col-sm-6 widget-container-col"
                                         style="width:40%;margin:0px; float: left;">
                                        <div class="widget-box">
                                            <div class="widget-header widget-header-large">
                                                <h4 class="widget-title">题目选择</h4>
                                                <div class="widget-toolbar">
                                                </div>
                                            </div>
                                            <!-- 以下是试题题目框内容部分 -->
                                            <div class="widget-body" style="height:502px;">
                                                <div class="widget-main  no-padding">
                                                    <div class="widget-box transparent no-margin">
                                                        <!-- 内容背景透明 -->
                                                        <div class="widget-main  no-padding-left no-padding-right no-padding">
                                                            <!-- 内容边框隐藏 -->
                                                            <div class="scrollable">
                                                                <div class="widget-main padding-6"
                                                                     style="height:500px;">
                                                                    <div class="container-div layout-container-div clearfix">
                                                                        <div class="row">
                                                                            <div class="col-sm-12 search-collapse">
                                                                                <form id="role-form">
                                                                                    <div class="select-list">
                                                                                        <ul>
                                                                                            <li>
                                                                                                题目：<input type="text" name="title"/>
                                                                                            </li>
                                                                                            <li>
                                                                                                &nbsp;章：<select style="width:89px;" name="chapterId" id="chapterId"
                                                                                                                onchange="ChapChange(this.value)">
                                                                                                    <option value="">所有</option>
                                                                                                </select>
                                                                                            </li>
                                                                                            <li>
                                                                                                &nbsp;节：<select style="width:89px;" name="jchapterId" id="jchapterId">
                                                                                                    <option value="">所有</option>
                                                                                                </select>
                                                                                            </li>

                                                                                            <li>
                                                                                                题&nbsp;型：<select style="width:60px;" name="titleTypeId" id="titleTypeName">
                                                                                                    <option value="">所有</option>
                                                                                                </select>
                                                                                            </li>


                                                                                            <li>
                                                                                                <a class="btn btn-primary btn-rounded btn-sm" onclick="$.table.search()"><i
                                                                                                            class="fa fa-search"></i>&nbsp;搜索</a>
                                                                                                <a class="btn btn-warning btn-rounded btn-sm" onclick="$.form.reset()"><i
                                                                                                            class="fa fa-refresh"></i> &nbsp;重置</a>
                                                                                            </li>
                                                                                        </ul>
                                                                                    </div>
                                                                                </form>
                                                                            </div>
                                                                            <div class="col-sm-12 select-table table-striped">
                                                                                <table id="bootstrap-table" class="bootstrap-table" data-mobile-responsive="true"></table>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <input type="hidden" id="count">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-xs-12 col-sm-6 widget-container-col"
                                         style="width:50%; margin:0px;">
                                        <!-- 以下表格 -->
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- PAGE CONTENT ENDS -->
                </div><!-- /.col -->
                <div class="message-list-container">
                </div>
            </div>
        </div>
        <!-- PAGE CONTENT ENDS -->
    </div><!-- /.col -->
</div><!-- /.row -->
<div class="main-container" id="paper" style="display: none;">
    <div class="main-content">
        <div class="main-content-inner">
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="row">
                            <div class="col-xs-12 col-sm-7 widget-container-col border-widget-container-col"
                                 name="testQuestion" id="testQuestion"
                                 style="margin:80px 30px; width:2000px;padding:42px 300px;">
                                <!-- js生成试卷内容 -->
                                <button class="btn btn-info ipt" type="button" name="b_print"
                                        onClick="printdiv('testQuestion');"
                                        value=" Print ">
                                    <i class="ace-icon fa fa-check bigger-110"></i>
                                    打印
                                </button>
                            </div>
                        </div><!-- /.row -->
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div>
    </div><!-- /.main-content -->
</div><!-- /.main-container -->
<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    var questionid = "";
    var testid = "${testPaper.id}";
    var coursrId = "${testPaper.coursrId}";
    var testName = "${testPaper.testName}";
    var qList = "";
    var chapterId = "";
    var title = "${testPaper.testName}";
    var cid="${testPaper.coursrId}";
    var qid= "";
    var qidArray  = new Array();
    var titleType = new Array();
    var titleTypeScore = "";
    var finalQid = "";
    var j = 1;
    var num = 1;
    var obj = "";
    var len = "";
    var t = "";
    var tt = "";
    var tid = "";
    var tqt = "";
    var optval;
    var optvalId;
    var subId = "${testPaper.coursrId}";
    var zname;
    var jname;
    var prefix = "/school/onlineExam/myQuestion";
    $chapterName1Select = $("#chapterId");//章
    $chapterNameSelect = $("#jchapterId");//节
    $subjectsNameSelect = $("#subjectsId");//科目
    $titleTypeNameSelect2 = $("#selectid");//题型
    $titleTypeNameSelect = $("#titleTypeName");//题型

    $(function () {
        var options = {
            url: prefix + "/list/${testPaper.coursrId}",
            parentCode: "parentId",
            createUrl: prefix + "/add/${testPaper.coursrId}",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            exportUrl: prefix + "/export",
            importUrl: prefix + "/importData",
            importTemplateUrl: prefix + "/importTemplate",
            sortName: "createTime",
            sortOrder: "desc",
            modalName: "我的题目",
            search: false,
            showExport: true,
            columns: [
                {
                    checkbox: true
                },
                {
                    field: 'id',
                    title: '编号',
                    sortable: true
                },
                {
                    field: 'title',
                    title: '试题题目',
                    sortable: true
                },
                {
                    field: 'titleTypeName',
                    title: '题型'
                },
                {
                    field: 'difficulty',
                    title: '难度',
                    sortable: true
                },
                {
                    field: 'qexposed',
                    title: '引用次数',
                    sortable: true
                },
                {
                    field: 'createTime',
                    title: '创建时间',
                    sortable: true
                }
            ]
        };
        $.table.init(options);
        $.common.initBind();

    });
    function addQuestion(titleId) {
        var arr=[];
        var rows = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
        if (rows.length <= 0) {
            $.modal.alertWarning("请至少选择一条记录");
            return;
        }
        $.modal.confirm("确认要添加选中的" + rows.length + "条数据吗?", function () {
            var url = $.table._option.removeUrl;
            var data = {
                "ids": rows.join()
            };
            qid+=rows.join()+","; //字符分割
            qidArray=qid.split(",");
            //将数组进行排序
            qidArray.sort();
            //定义结果数组
            for (var i = 1; i < qidArray.length; i++) {    //从数组第二项开始循环遍历数组
                //判断相邻两个元素是否相等，如果相等说明数据重复，否则将元素写入结果数组
                if (qidArray[i] !== arr[arr.length - 1]) {
                    arr.push(qidArray[i]);
                }
            }
            var tandq = "";
            for (var i = 0; i <arr.length; i++) {
                tandq+=arr[i]+",";
            }
            var  myinputFlag = "inputFlag"+titleId;
            document.getElementById(myinputFlag).value = titleId+';'+tandq;
            $('#titFlag'+titleId).empty();
            $('#titFlag'+titleId).append(arr.length+"条");

        });
    }


    //自动生成试卷
    function autogeneration() {
        var dif =document.getElementById("testDifficulty").value;
        /* alert(dif); */
        $("div[name=typelable]").each(function () {
            $(this).parent().parent().each(function () {
                tid += $(this).find("div[name=typelable]").attr("value") + ",";
            });

            $(this).parent().parent().find("input[name=typenum]").each(function () {
                t += $(this).val() + ",";
            });

            $(this).parent().parent().find("input[name=typenum2]").each(function () {
                tt += $(this).val() + ",";
            });

            $(this).parent().parent().find("input[name=typenum3]").each(function () {
                tqt += $(this).val() + "&";
            });

        });
        tval = "";
        for (var i = 0; i < t.length; i++) {

            if (t[i] == ",") {
                //alert("nnn"+tval);
                if (tval == 0) {
                    sul = 1;
                }
                tval = "";
            }//if_end
            else {
                tval += t[i];
            }
        }//for_end
        //智能组卷
        if (tid == "" || t == "" || tt == "" || dif == ""||stuId=="") {
            $.modal.alertWarning("请先填写完整信息");
            tqt="";
        } else {
            var sid  = new Array();
             sid = stuId.split(",");
            for (let i = 0; i < sid.length-1; i++) {
                var thisStudentID =sid[i];
                var thisStudentNum = i;
                console.log("Yes:::::"+thisStudentID);
                if(thisStudentID!=""||thisStudentID!=null){
                     $.ajax({
                         type: "POST",
                         async:"false",
                         url: "/school/onlineExam/course/CoursebuildPaper ",
                         dataType: "json",
                         data: {
                             'tqt':tqt,
                             'testId': testid,
                             'coursrId': coursrId,
                             'txid': tid,
                             'selectType': t,
                             'selectTypescore': tt,
                             'stuId':thisStudentID,
                             'diff': dif
                         },
                         error: function (data) {
                             $.modal.alertError("系统错误");
                         },
                         success: function (data) {
                             if (null != data && "" != data) {
                                 console.log("组卷成功");
                                 qList = data;
                                 console.log("Yes"+sid[i]);
                                 setTimeout(function(){ showPaper(sid[i],thisStudentNum);}, 9000);
                             } else {
                                 t = "";
                                 tid = "";
                                 tt = "";
                                 $.modal.alertWarning("组卷失败：找不到满足要求的题目");

                             }
                         }
                     });
                 }


            }
        }
    }

    function showPaper(thisStudentID,thisStudentNum) {
        $.ajax({
            type: "POST",
            async:"false",
            url: "/school/onlineExam/course/AGAbuildTestStepTwo",
            error: function (request) {
                $.modal.alertError("系统错误");
            },
            success: function (data) {
                var questionDataArray = JSON.parse(data);
                obj = questionDataArray;
                len = questionDataArray.length;
                for (var i = 0; i < len; i++) {
                    tid += questionDataArray[i].tid + ",";
                }
                //标题
                var file = $('<div class="widget-box border-widget-box itemdiv" style="min-height:40px;">\
					<div class="widget-header tools" style="z-index:5555;bottom:auto ;width:300px;min-height:35px;">\
					<h5 class="widget-title"> </h5>\
					<div class="widget-toolbar">\
							<a href="#" data-action="close">\
								<i class="ace-icon fa fa-times"></i>\
							</a>\
							</div>\
						</div>\
						<div class="widget-body" >\
							<div class="widget-main" id="test_title" style="text-align: center;">' + title + '</div>\
						</div>\
					</div>').appendTo('#testQuestion');

                file.addClass('width-15');
                //题型
                for (var i = 0; i < len; i++) {
                    if (i == 0) {
                        var file = $('<div class="widget-box border-widget-box itemdiv"  style="min-height:40px;">\
                        <div id="widget-header" class="widget-header tools" style="z-index:5555;bottom:auto ;width:300px;min-height:37px;">\
			<div class="widget-toolbar">\
    			<a href="#" data-action="fullscreen" class="orange2">\
    						<i class="ace-icon fa fa-expand"></i>\
    					</a>\
    					<a href="#" data-action="reload">\
    						<i class="ace-icon fa fa-refresh"></i>\
    					</a>\
    					<a href="#" data-action="collapse">\
    						<i class="ace-icon fa fa-chevron-up"></i>\
    					</a>\
    					<a href="#" data-action="close">\
    						<i class="ace-icon fa fa-times"></i>\
    					</a>\
					</div>\
				</div>\
				<div class="widget-body">\
					<div class="widget-main" style="padding:0;margin:0;font-size: 18px; font-weight: bold;">' + charname(j) + '、' + obj[i].titleType + '</div>\
				</div>\
			</div>').appendTo('#testQuestion');
                        j++;
                    }//if_end
                    else if (obj[i].number != obj[i - 1].number) {

                        var file = $('<div class="widget-box border-widget-box itemdiv"  style="min-height:40px;">\
                        <div id="widget-header" class="widget-header tools" style="z-index:5555;bottom:auto ;width:300px;min-height:37px;">\
						<div class="widget-toolbar">\
			    			<a href="#" data-action="fullscreen" class="orange2">\
			    						<i class="ace-icon fa fa-expand"></i>\
			    					</a>\
			    					<a href="#" data-action="reload">\
			    						<i class="ace-icon fa fa-refresh"></i>\
			    					</a>\
			    					<a href="#" data-action="collapse">\
			    						<i class="ace-icon fa fa-chevron-up"></i>\
			    					</a>\
			    					<a href="#" data-action="close">\
			    						<i class="ace-icon fa fa-times"></i>\
			    					</a>\
								</div>\
							</div>\
							<div class="widget-body">\
								<div class="widget-main" style="padding:0;margin:0;font-size: 18px; font-weight: bold;">' + charname(j) + '、' + obj[i].titleType + '</div>\
							</div>\
						</div>').appendTo('#testQuestion');
                        j++;
                        num = 1;
                    }
                    //题目
                    var file = $('<div class="widget-box border-widget-box itemdiv " style="min-height:40px;" name="questionid" value=' + obj[i].tid + ';' + obj[i].score + ';' + '>\
					<div id="widget-header" class="widget-header tools" style="z-index:5555;bottom:auto ;width:300px;min-height:35px;">\
					<h5 class="widget-title"></h5>\
					<div class="widget-toolbar">\
		    					<a href="#" data-action="close">\
		    						<i class="ace-icon fa fa-times"></i>\
		    					</a>\
							</div>\
						</div>\
						<div class="widget-body" >\
							<div class="widget-main" style="padding-top:0;padding-bottom: 0px;margin-top:0 ;"><div style="float:left;font-size:14px;margin-left:15px;">' + num + '\
							、</div><div  name=' + obj[i].tid + ' style="display: inline-block;width:90%">' + '(' + obj[i].score + '分)' + obj[i].title.replace("<p>", "").replace("</p>", "") + '\
							<div id=' + obj[i].tid + ' ></div>\
							</div>\
							</div>\
						</div>\
					</div>').appendTo('#testQuestion');
                    $("div[id=" + obj[i].tid + "]").bind("load", optionChange(i));//onclick="optionChange('+i+')"
                    num++;
                }//for_end
                savethisone(thisStudentID,thisStudentNum);
            }
        });
    }
    function savethisone(thisStudentID,thisStudentNum) {
        console.log("thisStudentID:"+thisStudentID+"thisStudentNum;"+thisStudentNum);
        $("div[name=questionid]").each(function () {
            questionid="";
            questionid += $(this).attr("value") + ",";
        });
        var fagi = thisStudentNum;
        var idlength = stuId.split(",");
        var len  = idlength.length-2;
        if(fagi==len){
            console.log("testQuestionsId:"+questionid);
                $.operate.saveModal("/school/onlineExam/course/CourseAddPaper", $.param({
                    'testPaperId': testid,
                    'testQuestionsId': questionid,
                    'studentId':thisStudentID

                }));
            questionid="";
        }
        else{
            $.ajax({
                type: "POST",
                url: "/school/onlineExam/course/CourseAddPaper",
                dataType: "json",
                data: {
                    'testPaperId': testid,
                    'testQuestionsId': questionid,
                    'studentId':thisStudentID
                },
                error: function (data) {
                    $.modal.alertError("系统错误");
                },
                success: function (data) {
                }
            });
                questionid="";
        }

    }


    function printdiv(printpage) {
        document.getElementById('widget-header').style.display = 'none';
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

    function charname(val) {
        var hanzi = new Array("一", "二", "三", "四", "五", "六", "七", "八", "九", "十");
        var alabo = new Array("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        for (var i = 0; i < 11; i++) {
            if (val == alabo[i])
                return hanzi[i];
        }
        return ' ';
    }
    function optionChange(i) {
        var objlength = obj[i].optlist.length;
        if (obj[i].titleType != "填空题" && (objlength > 1)) {
            var k = 1;
            var fvalone = 0;
            for (var t = 0; t < objlength; t++) {
                var abcd = String.fromCharCode(t + 65);
                if (k == 1) {
                    var file = $('<span>测试一行高度</span>').appendTo('#' + obj[i].titleType + '');
                } else if (k == 2) {
                    var file = $('<span style="display: inline-block;width:24%">' + abcd + '、' + obj[i].optlist[t] + '</span>').appendTo('#' + obj[i].tid + '');
                } else if (k == 3) {
                    var ab = String.fromCharCode(t + 65);
                    var cd = String.fromCharCode(t + 66);
                    var file = $('<div>\
                            <span style="display: inline-block;width:50%">' + ab + '、' + obj[i]["optlist"][t] + '</span>\
						<span style="display: inline-block;width:49%">' + cd + '、' + obj[i]["optlist"][t + 1] + '</span>\
						</div>').appendTo('#' + obj[i].tid + '');
                    t++;
                } else if (k == 4) {
                    var file = $('<div >' + abcd + '、' + obj[i].optlist[t] + '</div>').appendTo('#' + obj[i]["${questions.tid}"] + '');
                }
                if (t == 0) {
                    fval = $("div[id=" + obj[i]["${questions.tid}"] + "]").height();
                    if (k == 1) {
                        fvalone = fval;
                    }
                }
                if (t == objlength - 1) {
                    eval = $("div[id=" + obj[i]["${questions.tid}"] + "]").height();
                    if ((eval - fvalone) == 0 && k != 3) {
                        if (k == 1) {
                            document.getElementById(obj[i]["${questions.tid}"]).innerHTML = "";
                            t = -1;
                            k = 2;
                            continue;
                        }
                    } else if (k == 2) {
                        document.getElementById(obj[i]["${questions.tid}"]).innerHTML = "";
                        t = -1;
                        k = 3;
                        continue;
                    } else if (k == 3 && ((eval / 2) - fvalone) != 0) {
                        document.getElementById(obj[i]["${questions.tid}"]).innerHTML = "";
                        t = -1;
                        k = 4;
                        continue;
                    }
                }
            }//for_end
        }//if_end
    }
    //移除下拉列表值
    function removeOption() {
        var x;
        var xa = document.getElementById("selectid");
        optv = xa.length;
        x = document.getElementById("selectid");
        optval = x.options[x.selectedIndex].innerHTML;
        optvalId = x.options[x.selectedIndex].value;
        x.remove(x.selectedIndex);
        var difficulty = document.getElementById("testDifficulty").value;
        $.ajax({
            type: "POST",
            url: "/school/onlineExam/myQuestion/getQuestionSum ",
            dataType: "json",
            data: {
                "titleTypeId": optvalId,
                "xzsubjectsId": coursrId,
                "difficulty": difficulty
            },
            error: function (data) {
            },
            success: function (data) {
                document.getElementById(optval + "Flag").innerText = "共" + data.length + "条";
            }
        });

    }//removeOption()

    //插入下拉列表值
    function insertOption(opt) {
        optval = opt;
        console.log("opt:" + opt);
        var y = document.createElement('option');
        y.text = optval;
        var x = document.getElementById("selectid");
        try {
            x.add(y, null); // standards compliant
        } catch (ex) {
            x.add(y); // IE only
        }
    }//insertOption()

    jQuery(function ($) {
        $('#inbox-tabs')
            .on('click', function () {
                if (optv != 0) {
                    setTimeout(function () {
                        $('.message-form').removeClass('hide').insertAfter('.message-list');
                    }, 300 + parseInt(Math.random() * 100));
                    var file = $('<div  style="width:15%;margin:auto 0px;float:left;text-align:center;" ><div id=' + optval + '  name="typelable" value=' + optvalId + ' style="font-size:12px;" class="label label-danger arrowed-in"></div></div>').appendTo('#form-attachments');
                    var div1 = document.getElementById(optval);
                    div1.innerHTML = optval;//重新设置div1内的html代码。
                    var optvalFlag = optval + "Flag";
                    var titFlag ="titFlag"+optvalId;
                    var inputFlag = "inputFlag"+optvalId;
                    file.addClass('width-15 inline')
                        .wrap('<div class="form-group file-input-container"><div class="col-sm-7" style="width:100%;margin:0px;"></div></div>')
                        .parent().append('<div style="text-align:center;width:10%;float:left;"><input  class=' + optval + ' id="1" name="typenum" type="text" style="width: 60px;height:30px" oninput="value=value.replace(/[^\\d]/g,\'\')" ></div>\
                        <div><input type="hidden"   id='+inputFlag+' name="typenum3"></div>\
									<a id=' + optvalFlag + ' class="btn disabled" style="width:10%;height:30px;margin:0px;padding:0px;float:left;">共6条</a>\
									<div style="text-align:center;width:20%;float:left;"><input  class=' + optval + ' id="2" name="typenum2" type="text" style="width: 80px;height:30px" oninput="value=value.replace(/[^\\d]/g,\'\')"></div>\
									<a  class="btn disabled" style="width:15%;height:30px;margin:0px;padding:0px;float:left;">计算</a >\
                                                          <div style="text-align:center;width:12%;float:left;" id='+titFlag+'></div>\
                                                          <div style="text-align:center;width:12%;float:left;" >\
                                                         <button class="btn-info" type="button" id='+optvalId+'  name='+optvalId+'  onclick="addQuestion(this.name)">\
                                                      <i class="ace-icon fa "></i>选择\
                                                        </button>\
                                                        </div>\
                                                        <div class="action-buttons pull-left col-xs-1" style="width:5%;margin:0px;text-align:center;vertical-align:middle;">\
                                                        <a href="#" data-action="delete" class="middle" name=' + optval + ' onclick="insertOption(this.name)" style="margin:0px auto;">\
									<i class="ace-icon fa fa-trash-o red bigger-130 middle"></i>\
								</a>\
							</div>')

                        .find('a[data-action=delete]').on('click', function (e) {//删除功能
                        e.preventDefault();
                        $(this).closest('.file-input-container').hide(300, function () {
                            $(this).remove();
                        });
                    });
                    setTimeout(function () {
                        $('.' + optval + '').ace_spinner({
                            value: 0,
                            min: 0,
                            max: 100,
                            step: 1,
                            btn_up_class: 'btn-info',
                            btn_down_class: 'btn-info'
                        })
                            .closest('.ace-spinner')
                            .on('changed.fu.spinbox', function () {
                                num = $(this).children().find("input").val();
                                numname = $(this).children().find("input").attr("name");
                            });
                    }, 1);// parseInt(Math.random())
                }//if
            });
    });


    //显示第五级——节数，给第四级的下拉选项添加单击响应事件
    function ChapChange(chapParentID) {
        $chapterNameSelect.find("option:not(:first)").remove();
        var chaperId = chapParentID;
        console.log(">>>" + chaperId);
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/myQuestion/findchapterName2?chaperId=' + chaperId,
            dataType: 'json',
            success: function (data) {
                for (i = 0; i < data.length; i++) {
                    console.log(data);
                    var cid2 = data[i].id;
                    var chaName = data[i].title;
                    if (jname != null) {
                        var result = (chaName == jname) ? "selected" : "";
                    } else {
                        var result = null;
                    }
                    $chapterNameSelect.append('<option value="' + cid2 + '" ${"'+result+'"}>' + chaName + '</option>');
                }
            },
            error: function () {
                alert("获取数据失败!");
            }
        });
    };

    (function () {
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/myQuestion/findTitleType?cid=' + cid,
            dataType: 'json',
            success: function (data) {
                console.log("data,length:" + data.length);
                for (i = 0; i < data.length; i++) {
                    console.log("titleTypeName:" + data[i].titleTypeName);
                    var tid = data[i].id;
                    var titName = data[i].titleTypeName;//name="'+titName+'"
                    var titnum = data[i].templateNum;
                    $titleTypeNameSelect.append('<option value="' + tid + '" title="' + titnum + '" >' + titName + '</option>');
                }
            },
            error: function () {
                alert("获取题型数据失败!");
            }
        });
    })();
    //显示——章数，给第三级的下拉选项添加单击响应事件
    (function () {
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/myQuestion/findchapterName1?subId=' + cid,
            dataType: 'json',
            success: function (data) {
                console.log(data);
                for (i = 0; i < data.length; i++) {
                    var tid = data[i].id;
                    var chaName = data[i].title;
                    if (zname != null) {
                        var result = (chaName == zname) ? "selected" : "";
                    } else {
                        var result = null;
                    }
                    $chapterName1Select.append('<option value="' + tid + '" ${"'+result+'"}>' + chaName + '</option>');
                }
            },
            error: function () {
                alert("获取数据失败!");
            }
        });
    })();

    (function () {
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/myQuestion/findTitleType?cid=' + cid,
            dataType: 'json',
            success: function (data) {
                for (i = 0; i < data.length; i++) {
                    var tid = data[i].id;
                    var titName = data[i].titleTypeName;//name="'+titName+'"
                    var titnum = data[i].templateNum;
                    $titleTypeNameSelect2.append('<option value="' + tid + '" title="' + titnum + '" >' + titName + '</option>');
                }
            },
            error: function () {
                alert("获取数据失败!");
            }
        });
    })();
/*
    学生树部分
*/
    var tid = "${tid}";
    var cId = "${cid}";
    var stuId = "";
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
    var zNodes = [];

    var clearFlag = false;

    function onCheck(e, treeId, treeNode) {
        count();
        if (clearFlag) {
            clearCheckedOldNodes();
        }
    }

    function clearCheckedOldNodes() {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            nodes = zTree.getChangeCheckedNodes();
        for (var i = 0, l = nodes.length; i < l; i++) {
            nodes[i].checkedOld = nodes[i].checked;
        }
    }

    function count() {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            zTree
            checkCount = zTree.getCheckedNodes(true).length,
            nocheckCount = zTree.getCheckedNodes(false).length,
            changeCount = zTree.getChangeCheckedNodes().length;
        $("#checkCount").text(checkCount);
        $("#nocheckCount").text(nocheckCount);
        $("#changeCount").text(changeCount);

    }

    function createTree() {
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        count();
        clearFlag = $("#last").attr("checked");
    }

    $(document).ready(function () {
        createTree();
        $("#init").bind("change", createTree);
        $("#last").bind("change", createTree);

        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/userTest/getStudents?cId=' + cId + "&&tid=" + tid,
            dataType: 'json',
            success: function (data) {
                put(data);
            },
            error: function () {
                console.log("获取数据失败!");
            }
        });

    });
    //年级
    function put(data) {
        for (let i = 0; i < data.length; i++) {
            let datavalue = {};
            datavalue["id"] = data[i].tgId;
            datavalue["pId"] = 0;
            datavalue["name"] = data[i].tgName;
            datavalue["open"] = "true";
            zNodes.push(datavalue);
        }
        getTcoName();
    }

    function getTcoName() {
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/userTest/getTcoName?cId=' + cId + "&&tid=" + tid,
            dataType: 'json',
            success: function (data) {
                for (let i = 0; i < data.length; i++) {
                    let datavalue = {};
                    datavalue["id"] = data[i].tcId;
                    datavalue["pId"] = data[i].tgId;
                    datavalue["name"] = data[i].tcName;
                    datavalue["open"] = "true";
                    zNodes.push(datavalue);
                }
                getTcoStu();

            },
            error: function () {
                alert("获取数据失败!");
            }
        });
    }
    //获取学生
    function getTcoStu() {
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/userTest/getTcoStu?cId=' + cId + "&&tid=" + tid,
            dataType: 'json',
            success: function (data) {
                for (let i = 0; i < data.length; i++) {
                    let datavalue = {};
                    datavalue["id"] = data[i].tsId;
                    datavalue["pId"] = data[i].tcId;
                    datavalue["name"] = data[i].sysName;
                    console.log(data[i].sysName);
                    zNodes.push(datavalue);
                }
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);

            },
            error: function () {
                alert("获取数据失败!");
            }
        });
    }

    function savebt() {
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var sNodes = treeObj.getSelectedNodes();
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = treeObj.getCheckedNodes(true);
        document.getElementById("stuName").innerHTML = '';
        for (let i = nodes.length; i > 0; i--) {
            if(nodes[i - 1].isParent!=true){
                document.getElementById("stuName").innerHTML += nodes[i - 1].name + '<br>';
                stuId += nodes[i - 1].id + ",";
            }
        }
        if (stuId == "") {
            $.modal.alertError("请选好学生后点击添加学生");
        } else {
            $.modal.alertSuccess("添加学生成功");
        }
    }
</SCRIPT>
