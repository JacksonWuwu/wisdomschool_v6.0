    <#include "/common/style.ftl"/>
<link href="${basePath}/static/plugins/layui-v2.2.45/pl.css" type="text/css"/>
<link href="/css/step.css" type="text/css"/>
<link rel="stylesheet" media="all" href="/css/bundles/815814a3e796d2e86a0a332847a484bca5cf4667.css">
<div class="container-div layout-container-div clearfix">

<#--    已选  -->
    <div class="container">
        <div class="col-sm-12" style="background: #fff;border-radius: 6px;margin-top: 10px;padding-top: 3px;padding-bottom: 10px;">
            添加题目时，请注意更新选择列表
        </div>
        <div class="col-sm-12" style="background: #fff;border-radius: 6px;margin-top: 10px;padding-top: 3px;padding-bottom: 10px;">
            <table id="already-table" class="table-condensed" data-mobile-responsive="true"></table>
        </div>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
        <#--    题目  -->
            <div class="col-sm-12" style="background: #fff;border-radius: 6px;margin-top: 10px;padding-top: 3px;padding-bottom: 10px;">
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
            <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
            </div>
            <div class="col-sm-12" style="background: #fff;">
                <table id="bootstrap-table" class="table-condensed" data-mobile-responsive="true"></table>
            </div>
        </div>
    </div>

</div>
<#include "/common/stretch.ftl"/>
<script src="/js/plugins/layui/layui.js"></script>

<script type="text/javascript">

    /**
     * 选题界面
     */
    let questionid = "";//最后的试题ID
    let testid = "${testPaper.id}";
    let coursrId = "${testPaper.coursrId}";
    let testName = "${testPaper.testName}";
    let qList = "";
    let chapterId = "";
    let title = "${testPaper.testName}";
    let cid = "${cid}";
    let qid = "";
    let isSelectedQuestionIds = new Array(); //  记录选择题目
    let titleType = new Array();
    let titleTypeScore = "";    //记录题型分值
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
        loadQuestionTable();

        loadAlreadyTable();
    })
    function loadQuestionTable() {
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
                    checkbox: false
                },
                {
                    field: 'id',
                    title: '编号',
                    sortable: true,
                    formatter(value, row, index) {
                        return index+1;
                    }
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
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter : function (value, row, index) {
                        let actions = [];
                        actions.push('<a class="btn btn-success btn-xs" href="#" onclick="saveSelectedQuestions(\''+ testid + '\', \''+ row.id + '\')"><i class="fa fa-remove"></i>添加</a> ');
                        return actions.join('');
                    }
                }
            ]
        };
        $.table.init(options);
        $.common.initBind();
    }

    /**
     * 加载已选择题目
     */
    function loadAlreadyTable() {
        $('#already-table').bootstrapTable('destroy');
        $('#already-table').bootstrapTable({
            url: '/school/onlineExam/myQuestion/paperQuestion/list?paperId=' + ${testPaper.id},
            method: 'post',
            dataType:'json',
            sortOrder: "desc",
            cache: false,                        //是否使用缓存
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            showToggle:true,                    //是否显示详细视图和列表视图
            pagination: true,                    //启用分页
            pageNumber: 1,                        //初始化加载第一页，默认第一页
            pageSize: 10,                        //每页的记录行数
            pageList: [10,20,50],                //可供选择的每页行数
            sidePagination: "server",
            search: false,
            showExport: true,
            // queryParams: arr,
            contentType: "application/x-www-form-urlencoded",
            modalName: '已选择题目',
            height: $(window).height() - 100,
            columns: [
                {
                    checkbox: false
                },
                {
                    title: '已选题目',
                    field: 'params.question'
                },
                {
                    title: '题型',
                    field: 'params.titleType'
                },
                {
                    title: '分值',
                    field: 'params.score'
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
                        actions.push('<a class="btn btn-danger btn-xs  " href="#" onclick="removeAlready(\''+ row.params.paperQuestionId + '\')"><i class="fa fa-remove"></i>删除</a> ');
                        return actions.join('');
                    }
                }
            ]
        });
    }
    $(window).resize(function () {
        table.bootstrapTable('already-table', {
            height: $(window).height() - 100
        });
    })

    /**
     *  添加题目，变量保存
     */
    function saveSelectedQuestions(paperId, id) {
        //  通知俩个table更新
        $.modal.open('添加试题', "/school/paper/question/add?paperId="+ paperId + "&&myQuestionId=" + id, function () {
            $('#already-table').bootstrapTable('refresh');
            setTimeout('loadAlreadyTable()',2000)
            $.modal.alertSuccess("操作成功！");
        });
    }

    /**
     * 删除题目
     */
    function removeAlready(id) {
        $.modal.confirm("确认要删除?", function () {
            let data = {
                "id": id
            };
            $.operate.submit("/school/onlineExam/myQuestion/paperQuestion/remove", "post", "json", data, true, function (result) {
                if (result.code === web_status.SUCCESS) {
                    $.modal.alertSuccess("操作成功！");
                    loadAlreadyTable();
                } else {
                    $.modal.alertError("操作失败:");
                }
            });

        });
    }

    /**
     * 确认提交
     */
    function submitHandler() {
    }

    //  传递试卷分值组合数据以展示组卷
    function showPaper() {
        $.ajax({
            type: "POST",
            url: "/school/onlineExam/testPaper/buildTestToShow",
            dataType: 'json',
            contentType: "application/json;charset-UTF-8",
            data: JSON.stringify(qList),//POST请求中的body是Json对象

            error: function (request) {
                $.modal.alertError("系统错误");
            },
            success: function (data) {
                let questionDataArray = data;
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
    }

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
    }
    function charname(val) {
        let hanzi = new Array("一", "二", "三", "四", "五", "六", "七", "八", "九", "十");
        let alabo = new Array("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        for (let i = 0; i < 11; i++) {
            if (val == alabo[i])
                return hanzi[i];
        }
        return ' ';
    }
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
    }

    /**
     * 获取当前课程定义的题型，加载下拉表
     */
    (function () {
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/myQuestion/findTitleType?cid=' + cid,
            dataType: 'json',
            success: function (data) {
                //  查询当前课程定义的题型
                for (i = 0; i < data.length; i++) {
                    console.log("titleTypeName:" + data[i].titleTypeName);
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
    })()
    //显示——章数，给第三级的下拉选项添加单击响应事件
    (function () {
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/myQuestion/findchapterName1?subId=' + cid,
            dataType: 'json',
            success: function (data) {
                console.log("3333333" + data);
                for (i = 0; i < data.length; i++) {
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
