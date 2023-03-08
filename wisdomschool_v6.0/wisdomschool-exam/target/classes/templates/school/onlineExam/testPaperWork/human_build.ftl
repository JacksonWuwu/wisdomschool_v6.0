<#include "/common/style.ftl"/>
<div class="container-div layout-container-div clearfix" id="selectQuestions">
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

                        <li>难&nbsp;度
                            <select style="width:60px;"
                                    id="difficulty"
                                    name="difficulty">
                                <option value="">
                                    所有
                                </option>
                                <option value="1">
                                    容易
                                </option>
                                <option value="2">
                                    较易
                                </option>
                                <option value="3">
                                    中等
                                </option>
                                <option value="4">
                                    较难
                                </option>
                                <option value="5">
                                    困难
                                </option>
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
        <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
<#--            <@shiro.hasPermission name="school:onlineExam:course:view">-->
                <a class="btn btn-success" onclick="addQuestion()">
                    <i class="fa fa-plus"></i> 添加
                </a>
<#--            </@shiro.hasPermission>-->
<#--            <@shiro.hasPermission name="school:onlineExam:course:view">-->
                <a class="btn btn-success" onclick="nextStep()">
                    <i class="fa fa-plus"></i> 下一步
                </a>
<#--            </@shiro.hasPermission>-->
        </div>
        <div class="col-sm-12 select-table table-striped">
            <table id="bootstrap-table" class="bootstrap-table" data-mobile-responsive="true"></table>
        </div>
    </div>
</div>
<div class="container-div layout-container-div clearfix" id="setScore" style="display: none">
    <div id="score">
        <h4>设置分数</h4>
    </div>
    <a class="btn btn-success" onclick="getQuestionList()">
        <i class="fa fa-plus"></i> 生成试卷
    </a>
</div>
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
    let questionid = "";//最后的试题ID
    let testid = "${testPaper.id}";
    let coursrId = "${testPaper.coursrId}";
    let testName = "${testPaper.testName}";
    let qList = "";
    let chapterId = "";
    let title = "${testPaper.testName}";
    let cid = "${testPaper.coursrId}";
    let qid = "";
    let qidArray = new Array();
    let titleType = new Array();
    let titleTypeScore = "";
    let arr = [];
    let finalQid = "";
    var j = 1;
    var num = 1;
    var obj = "";
    var len = "";
    var t = "";
    var tt = "";
    var tid = "";
    var subId = "${id}";
    var zname;
    let jname;
    let prefix = "/school/onlineExam/myQuestion";
    $chapterName1Select = $("#chapterId");//章
    $chapterNameSelect = $("#jchapterId");//节
    $subjectsNameSelect = $("#subjectsId");//科目
    $titleTypeNameSelect = $("#titleTypeName");//题型
    $(function () {
        let options = {
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


    function addQuestion() {
        let rows = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
        if (rows.length <= 0) {
            $.modal.alertWarning("请至少选择一条记录");
            return;
        }
        $.modal.confirm("确认要添加选中的" + rows.length + "条数据吗?", function () {
            let data = {
                "ids": rows.join()
            };
            qid += rows.join() + ","; //字符分割
            qidArray = qid.split(",");
            $.modal.alertSuccess("添加成功");
        });
    };

    function nextStep() {
        if (qidArray.length == 0) {
            $.modal.alertError("请选择题目");
        } else {
            //将数组进行排序
            qidArray.sort();
            //定义结果数组
            for (let i = 1; i < qidArray.length; i++) {
                //从数组第二项开始循环遍历数组
                //判断相邻两个元素是否相等，如果相等说明数据重复，否则将元素写入结果数组
                if (qidArray[i] !== arr[arr.length - 1]) {
                    arr.push(qidArray[i]);
                }
            }
            document.getElementById('selectQuestions').style.display = 'none';
            document.getElementById('setScore').style.display = '';
            $.ajax({
                type: 'POST',
                url: '/school/onlineExam/myQuestion/findTitleType?cid=' + cid,
                dataType: 'json',
                success: function (data) {
                    for (i = 0; i < data.length; i++) {
                        titleType.push(data[i].id);
                        let tid = data[i].id;
                        let titName = data[i].titleTypeName;//name="'+titName+'"
                        let titnum = data[i].templateNum;
                        $('#score').append('<div><label>' + data[i].titleTypeName + ':' +
                            '</label> <input width="10px" oninput="value=value.replace(/[^\\d]/g,\'\')" type="text" id="' + data[i].id + '"' +
                            '></div>' +
                            '');
                    }
                },
                error: function () {
                    $.modal.alertError("获取题型数据失败!");
                }
            });

        }
    };

    function getQuestionList() {
        for (let i = 0; i < titleType.length; i++) {
            titleTypeScore += titleType[i] + ":";
            if (document.getElementById(titleType[i]).value == "" || document.getElementById(titleType[i]).value == null) {
                $.modal.alertWarning("分数不能为空");
                return;
            }
            titleTypeScore += document.getElementById(titleType[i]).value + ";";
        }
        for (let i = 0; i < arr.length; i++) {
            finalQid += arr[i] + ",";
        }

        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/testPaper/humanBuild?finalQid=' + finalQid + "&&score=" + titleTypeScore,
            dataType: 'json',
            success: function (data) {
                if (null != data && "" != data) {
                    qList = data;
                    $.modal.alertSuccess("组卷成功");
                    document.getElementById("selectQuestions").style.display = "none";//隐藏
                    document.getElementById("setScore").style.display = "none";//隐藏
                    document.getElementById("paper").style.display = "";//显示
                    showPaper();
                } else {
                    t = "";
                    tid = "";
                    tt = "";
                    $.modal.alertWarning("组卷失败：找不到满足要求的题目");

                }
            },
            error: function () {
                $.modal.alertError("获取题型数据失败!");
            }
        });
    };

    function showPaper() {
        $.ajax({
            type: "POST",
            url: "/school/onlineExam/testPaper/buildTestStepTwo",
            error: function (request) {
                $.modal.alertError("系统错误");
            },
            success: function (data) {
                let questionDataArray = JSON.parse(data);
                obj = questionDataArray;
                len = questionDataArray.length;
                for (let i = 0; i < len; i++) {
                    tid += questionDataArray[i].tid + ",";
                }
                //标题
                let file = $('<div class="widget-box border-widget-box itemdiv" style="min-height:40px;">\
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
                for (let i = 0; i < len; i++) {
                    if (i == 0) {
                        let file = $('<div class="widget-box border-widget-box itemdiv"  style="min-height:40px;">\
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
                        let file = $('<div class="widget-box border-widget-box itemdiv"  style="min-height:40px;">\
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
                    let file = $('<div class="widget-box border-widget-box itemdiv " style="min-height:40px;" name="questionid" value=' + obj[i].tid + ';' + obj[i].score + ';' + '>\
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
            }
        });
    };

    function submitHandler() {
        $("div[name=questionid]").each(function () {
            questionid += $(this).attr("value") + ",";
        });
        if (questionid != "") {
            $.operate.saveModal("/school/onlineExam/testPaper/humanAdd", $.param({
                'testPaperId': testid,
                'testQuestionsId': questionid
            }));
        } else {
            $.modal.alertWarning("未选题！");
        }
    };

    function printdiv(printpage) {
        document.getElementById('widget-header').style.display = 'none';
        let headstr = "<html><head><title></title>" +
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
    };

    function charname(val) {
        let hanzi = new Array("一", "二", "三", "四", "五", "六", "七", "八", "九", "十");
        let alabo = new Array("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        for (let i = 0; i < 11; i++) {
            if (val == alabo[i])
                return hanzi[i];
        }
        return ' ';
    };

    function optionChange(i) {
        let objlength = obj[i].optlist.length;
        if (obj[i].titleType != "填空题" && (objlength > 1)) {
            let k = 1;
            let fvalone = 0;
            for (let t = 0; t < objlength; t++) {
                let abcd = String.fromCharCode(t + 65);
                if (k == 1) {
                    let file = $('<span>测试一行高度</span>').appendTo('#' + obj[i].titleType + '');
                } else if (k == 2) {
                    let file = $('<span style="display: inline-block;width:24%">' + abcd + '、' + obj[i].optlist[t] + '</span>').appendTo('#' + obj[i].tid + '');
                } else if (k == 3) {
                    let ab = String.fromCharCode(t + 65);
                    let cd = String.fromCharCode(t + 66);
                    let file = $('<div>\
                            <span style="display: inline-block;width:50%">' + ab + '、' + obj[i]["optlist"][t] + '</span>\
						<span style="display: inline-block;width:49%">' + cd + '、' + obj[i]["optlist"][t + 1] + '</span>\
						</div>').appendTo('#' + obj[i].tid + '');
                    t++;
                } else if (k == 4) {
                    let file = $('<div >' + abcd + '、' + obj[i].optlist[t] + '</div>').appendTo('#' + obj[i]["${questions.tid}"] + '');
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
    };

    function ChapChange(chapParentID) {
        $chapterNameSelect.find("option:not(:first)").remove();
        let chaperId = chapParentID;
        if(chapParentID==""){
            return;
        }
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/myQuestion/findchapterName2?chaperId=' + chaperId,
            dataType: 'json',
            success: function (data) {
                for (i = 0; i < data.length; i++) {
                    let cid2 = data[i].id;
                    let chaName = data[i].title;
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
                for (i = 0; i < data.length; i++) {
                    let tid = data[i].id;
                    let titName = data[i].titleTypeName;//name="'+titName+'"
                    let titnum = data[i].templateNum;
                    $titleTypeNameSelect.append('<option value="' + tid + '" title="' + titnum + '" >' + titName + '</option>');
                }
            },
            error: function () {
                alert("获取题型数据失败!");
            }
        });
    })();

    (function () {
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/myQuestion/findchapterName1?subId=' + cid,
            dataType: 'json',
            success: function (data) {
                for (let i = 0; i < data.length; i++) {
                    let tid = data[i].id;
                    let chaName = data[i].title;
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
    })()
</script>
