<#include "/common/style.ftl"/>
<link href="/js/plugins/jquery-ztree/3.5/css/metro/zTreeStyle.css" rel="stylesheet"/>
<div class="row" id="buileTest">
    <div class="col-xs-12">
        <div class="tabbable">
            <div class="tab-content no-border padding-24">
                <div class="page-content">
                    <div class="page-header">
                        <h1>试卷选题步骤：①选择难度》②选择章节》③选择题型》④选择-》
                            <button class="btn btn-info" type="button" id="autogeneration"
                                    onclick="autogeneration()">
                                <i class="ace-icon fa fa-check bigger-110"></i>
                                生成试卷
                            </button>
                        </h1>
                    </div><!-- /.page-header -->
                    <div class="row">
                        <div class="col-xs-12">
                            <form class="form-horizontal ">
                                <div class="form-group">
                                    <div class="col-xs-12 col-sm-6 widget-container-col"
                                         style="width:50%;margin:0px;min-width:600px; ">
                                        <div class="form-group" style="width:50%; height:40px;display:static;">
                                            <div style="min-width:310px;position: absolute;left:0;">
                                                <label>
                                                    难度:
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
                                        <div class="widget-box widget-color-blue">
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
                                                        <form method="" action="" name="form1"
                                                              style="width:200px;">
                                                            <select NAME="province" id="selectid"
                                                                    style="width:150px;"
                                                                    name="testQuestions.titleTypeId.id">
                                                            </select>
                                                        </form>

                                                        <button id="inbox-tabs" type="button"
                                                                class="btn btn-sm btn-danger"
                                                                onclick="removeOption()"
                                                                style="width:120px;height:30px;padding:0px;">
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
                                                            <td class="hidden-480" style="width:20%;">题型数量</td>
                                                            <td class="hidden-480" style="width:15%;">库存数量</td>
                                                            <td class="hidden-480" style="width:20%;">单题分值</td>
                                                            <td class="hidden-480" style="width:15%;">分值合计</td>
                                                            <td class="hidden-480" style="width:15%;">操作</td>
                                                        </tr>
                                                        </thead>
                                                    </table>
                                                    <form id="" class="hide  message-form ">
                                                        <div id="form-attachments">

                                                            <!-- js弹出内容 -->

                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xs-12 col-sm-6 widget-container-col"
                                         style="width:40%;margin:0px; float: left;">
                                        <div class="widget-box">
                                            <div class="widget-header widget-header-large">
                                                <h4 class="widget-title">章节选择</h4>
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
                                                                    <ul id="treeDemo2" class="ztree"></ul>
                                                                    <div class="hr"></div>
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
                            </form>
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
<script src="/js/plugins/jquery-ztree/3.5/js/jquery.ztree.all-3.5.js"></script>
<script>
    var cid = "${testPaper.coursrId}";
    var testPaperid = "${testPaper.id}";
    let questionid = "";//最后的试题ID
    let testid = "${testPaper.id}";
    let coursrId = "${testPaper.coursrId}";
    let testName = "${testPaper.testName}";
    let qList = "";
    let chapterId = "";
    let title = "${testPaper.testName}";
    let txid;
    let subId;
    let j = 1;
    let num = 1;
    let obj = "";
    let len = "";
    let t = "";
    let tt = "";
    let tid = "";
    let optval;
    let optvalId;
    $titleTypeNameSelect = $("#selectid");//题型
    $(function () {
        initTree();
        setHeight();
    });
    function initTree() {
        let url = "/teacher/chapter/chapterTreeData/" + cid;
        let options = {
            id: "treeDemo2",
            url: url,
            expandLevel: 0,
            check: {enable: true, nocheckInherit: true, chkboxType: {"Y": "ps", "N": "ps"}},
            onClick: zTreeOnClick,
            onExpand: nodeOnExpand,
            onCollapse: nodeOnCollapse
        };
        $.tree.init(options);
    }
    function zTreeOnClick(event, treeId, treeNode) {
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo2");
        var nodes = treeObj.getSelectedNodes();
        //判断选中的是否为子节点
        //若为父节点flag = true
        var flag = false;
        if (nodes.length > 0) {
            var flag = nodes[0].isParent;
        }
        if (flag) {
            ;
        } else {
            chapterId = treeNode.id + ",";
            chapterName = treeNode.name;
        }

    };
    function nodeOnExpand(event, treeId, treeNode) {
        setHeight();
    }

    function nodeOnCollapse(event, treeId, treeNode) {
        setHeight();
    }

    function setHeight() {
        let m = $('#inline-outline-layout-container');
        if (m.length === undefined || m.length === 0) {
            return;
        }
        let w = m.layout('panel', 'west');	// get the west panel

        let oldWestHeight = w.panel('panel').outerHeight();

        w.panel('resize', {height: 'auto'});

        let newWestHeight = w.panel('panel').outerHeight();
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
        let footstr = "</body>";
        let newstr = document.all.item(printpage).innerHTML;
        let oldstr = document.body.innerHTML;
        document.body.innerHTML = headstr + newstr + footstr;
        window.print();
        document.body.innerHTML = oldstr;
        return false;
    }

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
                alert("获取数据失败!");
            }
        });
    })()

    //移除下拉列表值
    function removeOption() {
        let x;
        let xa = document.getElementById("selectid");
        optv = xa.length;
        x = document.getElementById("selectid");
        optval = x.options[x.selectedIndex].innerHTML;
        optvalId = x.options[x.selectedIndex].value;
        x.remove(x.selectedIndex);
        let difficulty = document.getElementById("testDifficulty").value;
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
        let y = document.createElement('option');
        y.text = optval;
        let x = document.getElementById("selectid");
        try {
            x.add(y, null); // standards compliant
        } catch (ex) {
            x.add(y); // IE only
        }
    }//insertOption()

    function showChapter() {
        $.modal.open("章节管理", "/school/onlineExam/myQuestion/myChapter/chapterList?XZsubjectsID=" + XZsubjectsID);
    }

    jQuery(function ($) {
        $('#inbox-tabs')
            .on('click', function () {
                if (optv != 0) {
                    setTimeout(function () {
                        $('.message-form').removeClass('hide').insertAfter('.message-list');
                    }, 300 + parseInt(Math.random() * 100));
                    let file = $('<div  style="width:15%;margin:auto 0px;float:left;text-align:center;" ><div id=' + optval + '  name="typelable" value=' + optvalId + ' style="font-size:12px;" class="label label-danger arrowed-in"></div></div>').appendTo('#form-attachments');
                    let div1 = document.getElementById(optval);
                    div1.innerHTML = optval;//重新设置div1内的html代码。
                    let optvalFlag = optval + "Flag";
                    file.addClass('width-15 inline')
                        .wrap('<div class="form-group file-input-container"><div class="col-sm-7" style="width:100%;margin:0px;"></div></div>')
                        .parent().append('<div style="text-align:center;width:20%;float:left;"><input  class=' + optval + ' id="1" name="typenum" type="text" style="width: 120px;height:30px" oninput="value=value.replace(/[^\\d]/g,\'\')" ></div>\
									<a id=' + optvalFlag + ' class="btn disabled" style="width:15%;height:30px;margin:0px;padding:0px;float:left;">共6条</a>\
									<div style="text-align:center;width:20%;float:left;"><input  class=' + optval + ' id="2" name="typenum2" type="text" style="width: 120px;height:30px" oninput="value=value.replace(/[^\\d]/g,\'\')"></div>\
									<a  class="btn disabled" style="width:15%;height:30px;margin:0px;padding:0px;float:left;">计算</a >\
									<div class="action-buttons pull-left col-xs-1" style="width:15%;margin:0px;text-align:center;vertical-align:middle;">\
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

    //自动生成试卷
    function autogeneration() {
        let sNodes = "";
        let nodes = "";
        let chapterId = "";
        let treeObj = $.fn.zTree.getZTreeObj("treeDemo2");
        let dif = document.getElementById("testDifficulty").value;
        if (treeObj != null) {
            sNodes = treeObj.getSelectedNodes();
            nodes = treeObj.getCheckedNodes(true);
            for (let i = nodes.length; i > 1; i--) {
                chapterId += nodes[i - 1].id + ",";
            }
        } else {
            $.modal.alertWarning("请先填写完整信息");
        }
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

        });
        tval = "";
        for (let i = 0; i < t.length; i++) {
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
        if (tid == "" || t == "" || tt == "" || chapterId == "" || dif == "") {
            $.modal.alertWarning("请先填写完整信息");
        } else {
            $.ajax({
                    type: "POST",
                    url: "/school/onlineExam/testPaper/buileCoursePaper ",
                    dataType: "json",
                    data: {
                        'testId': testid,
                        'coursrId': coursrId,
                        'txid': tid,
                        'selectType': t,
                        'chapterId': chapterId,
                        'selectTypescore': tt,
                        'diff': dif
                    },
                    error: function (data) {
                        $.modal.alertError("系统错误");
                    },
                    success: function (data) {
                        if (null != data && "" != data) {
                            qList = data;
                            $.modal.alertSuccess("组卷成功");
                            document.getElementById("buileTest").style.display = "none";//隐藏
                            document.getElementById("paper").style.display = "";//显示
                            showPaper();
                        } else {
                            t = "";
                            tid = "";
                            tt = "";
                            $.modal.alertWarning("组卷失败：找不到满足要求的题目");

                        }
                    }
                }
            );
        }
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

    function showPaper() {
        // questionList = data;
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
    }
    function submitHandler() {
        $("div[name=questionid]").each(function () {
            questionid += $(this).attr("value") + ",";
        });
        if (questionid != "") {
            $.operate.saveModal("/school/onlineExam/testPaper/addCoursePaper", $.param({
                'testPaperId': testid,
                'testQuestionsId': questionid
            }));
        } else {
            $.modal.alertWarning("未选题！");
        }
    }
</script>




