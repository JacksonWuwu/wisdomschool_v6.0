<#include "/common/style.ftl"/>
<div class="page-content" style="min-width:1000px;">
    <div class="row">
        <div class="col-xs-12">
            <div class="tabbable">
                <div id="xjst" class="tab-pane fade in active" class="modal">
                    <div class="page-content">
                        <div class="row">
                            <div class="space-10"></div>
                            <div class="col-sm-offset-1 col-sm-10" style="min-width:850px;">
                                <form class="form-horizontal" id="form1">
                                    <input type="hidden" value="${id}" name="xzsubjectsId">

                                    <div class="form-group" style="width:100%; ">
                                        <div style="min-width:322px;float:left;">
                                            <label class="col-sm-3 "
                                                   style="letter-spacing: 10px;line-height:30px;width:72px;">
                                                分类
                                            </label>
                                            <select style="width:125px;" id="chapterName1"
                                                    onchange="ChapChange(this.value)">
                                                <option>请选择章数</option>
                                            </select>
                                            <select style="width:125px;" id="chapterName"
                                                    name="chapterId">
                                                <option> 请选择节数</option>
                                            </select>
                                        </div>
                                        <#--<div style="min-width:322px;float:right;">-->
                                            <#--<label class="col-sm-3 "-->
                                                   <#--style="letter-spacing: 10px;line-height:30px;width:72px;;">-->
                                                <#--公开-->
                                            <#--</label>-->
                                            <#--<input type="radio" name="ststatus" id="radio0" value="0"/>-->
                                            <#--<label for="ststatus1">是</label>-->
                                            <#--<input type="radio" name="ststatus" id="radio1" value="1" checked/>-->
                                            <#--<label for="ststatus2">否</label>-->
                                        <#--</div>-->
                                    </div>

                                    <div class="form-group" style="width:100%; ">
                                        <div style="width:330px;float:left;">
                                            <label class="col-sm-3 "
                                                   style="letter-spacing: 10px;line-height:30px;width:72px;float:left;">
                                                题型
                                            </label>
                                            <select style="width:90px;float:left;" id="titleTypeName"
                                                    name="titleTypeId" onchange="titleType()">
                                                <option>请选择题型</option>
                                            </select>
                                            <label class="col-sm-3 "
                                                   style="letter-spacing: 10px;line-height:30px;width:72px;float:left;">
                                                难度
                                            </label>
                                            <select style="width:90px;float:left;" id="difficulty"
                                                    name="myQuestions.difficulty">
                                                <option>请选择难度</option>
                                                <option value="1">容易</option>
                                                <option value="2">较易</option>
                                                <option value="3">中等</option>
                                                <option value="4">较难</option>
                                                <option value="5">困难</option>
                                            </select>
                                        </div>

                                        <div style="min-width:322px;float:right">
                                            <label class="col-sm-3 "
                                                   style="letter-spacing: 10px;line-height:30px;width:72px;;">
                                                年份
                                            </label>
                                            <input type="text" id="year" name="year" value=" " />
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="widget-box transparent">
                                            <div class="widget-header widget-header-flat">
                                                <h4 class="widget-title lighter">
                                                    <i class="ace-icon fa fa-star orange"></i>
                                                    试题题目
                                                </h4>

                                                <div class="widget-toolbar">
                                                    <a href="#" data-action="collapse">
                                                        <i class="ace-icon fa fa-chevron-up"></i>
                                                    </a>
                                                </div>
                                            </div>

                                            <div class="widget-body">
                                                <div class="widget-main ">
                                                    <!-- 加载编辑器的容器 -->
                                                    <textarea name="title" rows="5" id="myEditor1"></textarea>

                                                    <#--    <script id="myEditor1" name="content" type="text/plain">
                                                            在这里输入内容......
                                                        </script>-->
                                                    <#--<div class="content" id="myEditor1" style="width:100%;"></div>-->
                                                </div><!-- /.widget-main -->
                                            </div><!-- /.widget-body -->
                                        </div><!-- /.widget-box -->
                                    </div><!-- /.col -->

                                    <div class="form-group">
                                        <div class="widget-box transparent">
                                            <div class="widget-header widget-header-flat">
                                                <h4 class="widget-title lighter">
                                                    <i class="ace-icon fa fa-star orange"></i>
                                                    试题答案
                                                </h4>
                                                <#--<button id="inbox-tabs" type="button" class="btn btn-xs btn-danger hide"-->
                                                        <#--style="margin-bottom:3px;">-->
                                                    <#--<i class=" ace-icon fa fa-plus smaller-75"></i>-->
                                                    <#--添加选项-->
                                                <#--</button>-->
                                                <div class="widget-toolbar">
                                                    <a href="#" data-action="collapse">
                                                        <i class="ace-icon fa fa-chevron-up"></i>
                                                    </a>
                                                </div>
                                            </div>

                                            <div class="widget-body">
                                                <div class="widget-main ">
                                                    <div id="form-attachments" class="hide">
                                                        <br/>
                                                        <!-- js弹出内容 -->
                                                    </div>
                                                    <div id="edtext" class="">
                                                        <!-- <textarea id="myEditor2" name="myEditor2"></textarea> -->
                                                        <textarea name="myoptionAnswerArr" rows="5"
                                                                  id="myEditor2"></textarea>
                                                        <#-- <script type="text/plain" id="myEditor2"
                                                                 name="myEditor2"></script>-->
                                                    </div>
                                                </div><!-- /.widget-main -->
                                            </div><!-- /.widget-body -->
                                        </div><!-- /.widget-box -->
                                    </div><!-- /.col -->

                                    <div class="form-group">
                                        <div class="widget-box transparent">
                                            <div class="widget-header widget-header-flat">
                                                <h4 class="widget-title lighter">
                                                    <i class="ace-icon fa fa-star orange"></i>
                                                    试题解析
                                                    <div class="widget-toolbar">
                                                        <a href="#" data-action="collapse">
                                                            <i class="ace-icon fa fa-chevron-up"></i>
                                                        </a>
                                                    </div>
                                            </div>

                                            <div class="widget-body">
                                                <div class="widget-main ">
                                                    <#--<script type="text/plain" id="myEditor3" name="myEditor3"></script>-->
                                                    <textarea name="parsing" rows="5" id="myEditor3"></textarea>

                                                </div><!-- /.widget-main -->
                                            </div><!-- /.widget-body -->
                                        </div><!-- /.widget-box -->
                                    </div><!-- /.col -->
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="message-list-container"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<#include "/common/stretch.ftl"/>
<script type="text/javascript" src="/dist/vendors/ckeditor/ckeditor.js"></script>

<script type="text/javascript">
    $chapterName1Select = $("#chapterName1");//章
    $chapterNameSelect = $("#chapterName");//节
    $titleTypeNameSelect = $("#titleTypeName");//题型
    let prefix = "/school/onlineExam/myQuestion";
    var myoptionAnswerArr = "";
    var depName;
    var subId;
    var zyname;
    var kmname;
    var jname;
    var stid;
    var optlk;
    var typeId;
    var num = 65;
    var oleng;
    let oarr = new Array();
    var ansarr = new Array();
    let ckeditor1;
    let ckeditor2;
    let ckeditor3;
    let title = "${publicQuestion.title}";
    var xzsubjectsId = "${publicQuestion.xzsubjectsId}";
    var difID = "${publicQuestion.difficulty}";
    var thisyear = "${publicQuestion.year}";
    var parsing = "${publicQuestion.parsing}";
    var chapParentID = "${publicQuestion.chapterId}";
    var chapID = "${publicQuestion.jChapterId}";
    var titleTypeID = "${publicQuestion.titleTypeId}";
    <#--var titleTypeName = "${publicQuestion.}";-->
    <#--var myoptionAnswerArr = "${publicQuestion.publicoptionAnswerArr}";-->
    var getoptionAnswerArr = "${publicQuestion.publicoptionAnswerArr}";
    var ststatus = "${publicQuestion.ststatus}";

    $(function () {
        initEditor();
    })

    function initEditor() {
        ckeditor1 = CKEDITOR.replace('myEditor1');
        ckeditor2 = CKEDITOR.replace('myEditor2');
        ckeditor3 = CKEDITOR.replace('myEditor3');
    }

    /**
     *    保存
     */
    function submitHandler() {
        console.log("xxx");

    }

    /**
     *    增加试题选项
     */
    $('#inbox-tabs')
        .on('click', function () {
            if (typeId == 1 && num <= 90) {//"单选题"
                var str = String.fromCharCode(num);
                num = num + 1;

                var file = $('<div class="radio" style="float:left;margin:0px;">\
                        <label>\
                            <input name="form-field-radio" type="radio" class="ace"/>\
                            <span class="lbl"></span>\
                        </label>\
                    </div>').appendTo('#form-attachments');

                file.addClass('width-15 inline')
                    .wrap('<div class="form-group file-input-container"><div class="col-sm-7" style="width:100%;margin:0px;"></div></div>')
                    .parent().append('<div  style="width:4%;margin:auto 0px;float:left;text-align:center;" >\
						<h4 name="szimu">' + str + '</h4>\
						</div>\
						<textarea id="form-field-11" class="autosize-transition form-control" style="width:85%;float:left;"></textarea>\
						<div class="action-buttons pull-left col-xs-1" style="width:5%;margin:0px;text-align:center;vertical-align:middle;">\
					<a href="#" data-action="delete" class="middle" name="1" onclick="insertOption(this.name)" style="margin:12px auto;">\
						<i class="ace-icon fa fa-trash-o red bigger-130 middle"></i>\
					</a>\
				</div>')

                    .find('a[data-action=delete]').on('click', function (e) {//删除功能
                    e.preventDefault();
                    $(this).closest('.file-input-container').hide(300, function () {
                        $(this).remove();
                    });
                    setTimeout(function () {
                        zimuChange();
                    }, 400 + parseInt(Math.random() * 100));
                });
            }//if_end

            else if ((typeId == 2) && num <= 90) {//"双选题"||typeId=="不定项"||typeId=="多选题"
                var str = String.fromCharCode(num);
                num = num + 1;
                var file = $('<div class="checkbox" style="float:left;margin:0px;">\
                        <label>\
                            <input name="form-field-checkbox" type="checkbox" class="ace" />\
                            <span class="lbl"> </span>\
                        </label>\
                    </div>').appendTo('#form-attachments');

                file.addClass('width-15 inline')
                    .wrap('<div class="form-group file-input-container"><div class="col-sm-7" style="width:100%;margin:0px;"></div></div>')
                    .parent().append('<div  style="width:4%;margin:auto 0px;float:left;text-align:center;" >\
						<h4 name="szimu">' + str + '</h4>\
						</div>\
						<textarea   id="form-field-11" class="autosize-transition form-control" style="width:85%;float:left;"></textarea>\
						<div class="action-buttons pull-left col-xs-1" style="width:5%;margin:0px;text-align:center;vertical-align:middle;">\
					<a href="#" data-action="delete" class="middle" name="1" onclick="insertOption(this.name)" style="margin:12px auto;">\
						<i class="ace-icon fa fa-trash-o red bigger-130 middle"></i>\
					</a>\
				</div>')

                    .find('a[data-action=delete]').on('click', function (e) {//删除功能
                    e.preventDefault();
                    $(this).closest('.file-input-container').hide(300, function () {
                        $(this).remove();
                    });
                    setTimeout(function () {
                        zimuChange();
                    }, 400 + parseInt(Math.random() * 100));
                });
            }//if_end

            if (typeId == 3 && num <= 10) {//"填空题"
                var str = charname(num);
                num = num + 1;
                var file = $('<div class="radio" style="float:left;margin:0px;">\
                    </div>').appendTo('#form-attachments');

                file.addClass('width-15 inline')
                    .wrap('<div class="form-group file-input-container"><div class="col-sm-7" style="width:100%;margin:0px;"></div></div>')
                    .parent().append('<div  style="width:10%;margin:auto 0px;float:left;text-align:center;" >\
						<h4 name="chinesenum">第' + str + '空</h4>\
						</div>\
						<textarea   id="form-field-11" class="autosize-transition form-control" style="width:85%;float:left;"></textarea>\
						<div class="action-buttons pull-left col-xs-1" style="width:5%;margin:0px;text-align:center;vertical-align:middle;">\
					<a href="#" data-action="delete" class="middle" name="1" onclick="insertOption(this.name)" style="margin:12px auto;">\
						<i class="ace-icon fa fa-trash-o red bigger-130 middle"></i>\
					</a>\
				</div>')

                    .find('a[data-action=delete]').on('click', function (e) {//删除功能
                    e.preventDefault();
                    $(this).closest('.file-input-container').hide(300, function () {
                        $(this).remove();
                    });
                    setTimeout(function () {
                        chinesenumChange();
                    }, 300 + parseInt(Math.random() * 100));
                });
            }//if_end
        });

    $('#difficulty').val(difID);//显示难度
    document.getElementById('year').value = thisyear;//显示年份
    setTimeout(function () {
        $('#titleTypeName').val(titleTypeID);		//显示题型
        document.getElementById("chapterName1").value = chapParentID;	//显示章
        ChapChange(chapParentID);
        setTimeout(function () {
            $('#chapterName').val(chapID);			//显示节
            CKEDITOR.instances.myEditor1.document.getBody().setText(title);
            setTimeout(function () {
                CKEDITOR.instances.myEditor3.document.getBody().setText(parsing);
                findAnswerList(getoptionAnswerArr);
                setTimeout(function () {
                    setAnswerMessage(getoptionAnswerArr);
                }, 700);
            }, 600);
        }, 500);
    }, 400);


    function findAnswerList(getoptionAnswerArr) {
        var myGetoptionAnswerArr = getoptionAnswerArr;
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/publicQuestion/getQuestionsAnsList?getoptionAnswerArr=' + myGetoptionAnswerArr,
            dataType: 'json',
            success: function (data) {
                for (let i = 0; i < data.length; i++) {
                    oarr[i] = data[i]
                }
                titleType();
            },
            error: function () {
                alert("获取答案数据失败!");
            }
        });

    }

    function setAnswerMessage(getoptionAnswerArr) {
        var optionAnswerArr = getoptionAnswerArr;
        var splitedArrs = new Array();
        var stoption;
        splitedArrs = optionAnswerArr.split(";");
        for (i = 0; i < splitedArrs.length - 1; i++) {
            var optionAnswerArrId = splitedArrs[i];
            $.ajax({
                type: 'POST',
                url: '/school/onlineExam/publicQuestion/getQuestionsAns?getoptionAnswerArr=' + optionAnswerArrId,
                dataType: 'json',
                success: function (data) {
                    var stanswer = data.stanswer;
                    stoption = data.stoption;
                    setRadio(stanswer);
                    setCheckbox(stanswer);
                    setStoption(stoption);
                },
                error: function () {
                    alert("答案信息数据失败!");
                }
            });
        }
    }

    function setRadio(stanswer) {
        var questionStanswer = stanswer;
        //单选题，判断题:checked
        $("input[name=form-field-radio]").each(function () {
            if ($(this).parent().parent().parent().find("h4[name=szimu]").text() == questionStanswer || this.value == questionStanswer) {
                $(this).attr("checked", 'checked');
            }
        });
    }

    function setCheckbox(stanswer) {
        var questionStanswer = stanswer;
        //多选题:checked
        $("input[name=form-field-checkbox]").each(function () {
            if ($(this).parent().parent().parent().find("h4[name=szimu]").text() == questionStanswer) {
                $(this).attr("checked", 'checked');
            }
        });
    }

    function setStoption(stoption) {
        var questionStoption = stoption;
        if (questionStoption != "") {
            CKEDITOR.instances.myEditor2.setData(questionStoption);
        }
    }

    /**
     *    方法函数
     */
    //选项题选项中字母序号变化函数
    function zimuChange() {
        num = 65;
        $("h4[name=szimu]").each(function () {

            $(this).each(function () {
                var str = String.fromCharCode(num++);
                this.innerHTML = str;
            });
        });
    }

    //填空题选项中文序号变化函数
    function chinesenumChange() {
        num = 1;
        $("h4[name=chinesenum]").each(function () {
            //	t+=$(this).html() + ",";

            $(this).each(function () {
                var str = charname(num++);
                this.innerHTML = "第" + str + "空";
            });
        });
    }

    //显示第五级——节数，给第四级的下拉选项添加单击响应事件
    function ChapChange(chapParentID) {
        $chapterNameSelect.find("option:not(:first)").remove();
        var chaperId = chapParentID;
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/myQuestion/findchapterName2?chaperId=' + chaperId,
            dataType: 'json',
            success: function (data) {
                for (i = 0; i < data.length; i++) {
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
                alert("获取节数据失败!");
            }
        });
    }

    //产生中文序号
    function charname(val) {
        var hanzi = new Array("一", "二", "三", "四", "五", "六", "七", "八", "九", "十");
        var alabo = new Array("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        for (var i = 0; i < 11; i++) {
            if (val == alabo[i])
                return hanzi[i];
        }
        return ' ';
    }

    //题型改变产生不同答案编辑框
    function titleType() {
        console.log("题型改变"+oarr);
        console.log("length:"+oarr.length);
        var optvals = "";
        document.getElementById("form-attachments").innerHTML = "<br/>";
        typeId = $("#titleTypeName").find("option:selected").prop("title");

        if (typeId == 1) {//单选题
            $('#edtext').addClass('hide');
            $('#form-attachments').removeClass('hide');
            num = 65;
            if (oarr.length != 0) {
                oleng = 64 + oarr.length;
            } else {
                oleng = 68;
            }

            for (; num <= oleng; num++) {
                if (oarr.length != 0) {
                    optvals = oarr[num - 65];
                } else {
                    optvals = "";
                }
                var str = String.fromCharCode(num);
                $('#inbox-tabs').removeClass('hide');
                var file = $('<div class="radio" style="float:left;margin:0px;">\
                           <label>\
                               <input name="form-field-radio" type="radio" class="ace" >\
                               <span class="lbl"></span>\
                           </label>\
                       </div>').appendTo('#form-attachments');

                file.addClass('width-15 inline')
                    .wrap('<div class="form-group file-input-container"><div class="col-sm-7" style="width:100%;margin:0px;"></div></div>')
                    .parent().append('<div  style="width:4%;margin:auto 0px;float:left;text-align:center;" >\
						<h4 name="szimu">' + str + '</h4>\
						</div>\
						<textarea   id="form-field-11" class="autosize-transition form-control" style="width:85%;float:left;">' + optvals + '</textarea>\
						<div class="action-buttons pull-left col-xs-1" style="width:5%;margin:0px;text-align:center;vertical-align:middle;">\
					<a href="#" data-action="delete" class="middle" name="1" onclick="insertOption(this.name)" style="margin:12px auto;">\
						<i class="ace-icon fa fa-trash-o red bigger-130 middle"></i>\
					</a>\
				</div>')

                    .find('a[data-action=delete]').on('click', function (e) {//删除功能
                    e.preventDefault();
                    $(this).closest('.file-input-container').hide(300, function () {
                        $(this).remove();
                    });
                    setTimeout(function () {
                        zimuChange();
                    }, 300 + parseInt(Math.random() * 100));
                });
            }//for_end
        }//if_end
        else if (typeId == 2) {//"双选题"||typeId=="不定项"||typeId=="多选题"
            $('#edtext').addClass('hide');
            $('#form-attachments').removeClass('hide');
            num = 65;
            if (oarr.length != 0) {
                oleng = 64 + oarr.length;
            } else {
                oleng = 68;
            }
            document.getElementById("form-attachments").innerHTML = "<br/>";
            for (; num <= oleng; num++) {
                if (oarr.length != 0) {
                    optvals = oarr[num - 65];
                } else {
                    optvals = "";
                }
                var str = String.fromCharCode(num);

                $('#inbox-tabs').removeClass('hide');

                var file = $('<div class="checkbox" style="float:left;margin:0px;">\
                           <label>\
                               <input name="form-field-checkbox" type="checkbox" class="ace" >\
                               <span class="lbl"> </span>\
                           </label>\
                       </div>').appendTo('#form-attachments');

                file.addClass('width-15 inline')
                    .wrap('<div class="form-group file-input-container"><div class="col-sm-7" style="width:100%;margin:0px;"></div></div>')
                    .parent().append('<div  style="width:4%;margin:auto 0px;float:left;text-align:center;" >\
						<h4 name="szimu">' + str + '</h4>\
						</div>\
						<textarea   id="form-field-11" class="autosize-transition form-control" style="width:85%;float:left;">' + optvals + '</textarea>\
						<div class="action-buttons pull-left col-xs-1" style="width:5%;margin:0px;text-align:center;vertical-align:middle;">\
					<a href="#" data-action="delete" class="middle" name="1" onclick="insertOption(this.name)" style="margin:12px auto;">\
						<i class="ace-icon fa fa-trash-o red bigger-130 middle"></i>\
					</a>\
				</div>')

                    .find('a[data-action=delete]').on('click', function (e) {//删除功能
                    e.preventDefault();
                    $(this).closest('.file-input-container').hide(300, function () {
                        $(this).remove();
                    });
                    setTimeout(function () {
                        zimuChange();
                    }, 300 + parseInt(Math.random() * 100));
                });
            }//for_end
        }//if_end
        else if (typeId == 3) {//"填空题"
            $('#edtext').addClass('hide');
            $('#form-attachments').removeClass('hide');
            $('#inbox-tabs').removeClass('hide');
            document.getElementById("form-attachments").innerHTML = "<br/>";
            num = 1;
            if (oarr.length != 0) {
                oleng = oarr.length;
            } else {
                oleng = 1;
            }
            for (; num <= oleng; num++) {
                if (oarr.length != 0) {
                    optvals = oarr[num - 1];
                } else {
                    optvals = "";
                }
                var str = charname(num);
                var file = $('<div class="radio" style="float:left;margin:0px;">\
                        </div>').appendTo('#form-attachments');

                file.addClass('width-15 inline')
                    .wrap('<div class="form-group file-input-container"><div class="col-sm-7" style="width:100%;margin:0px;"></div></div>')
                    .parent().append('<div  style="width:10%;margin:auto 0px;float:left;text-align:center;" >\
							<h4 name="chinesenum">第' + str + '空</h4>\
							</div>\
							<textarea   id="form-field-11" class="autosize-transition form-control" style="width:85%;float:left;">' + optvals + '</textarea>\
							<div class="action-buttons pull-left col-xs-1" style="width:5%;margin:0px;text-align:center;vertical-align:middle;">\
						<a href="#" data-action="delete" class="middle" name="1" onclick="insertOption(this.name)" style="margin:12px auto;">\
							<i class="ace-icon fa fa-trash-o red bigger-130 middle"></i>\
						</a>\
					</div>')

                    .find('a[data-action=delete]').on('click', function (e) {//删除功能
                    e.preventDefault();
                    $(this).closest('.file-input-container').hide(300, function () {
                        $(this).remove();
                    });
                    setTimeout(function () {
                        chinesenumChange();
                    }, 300 + parseInt(Math.random() * 100));
                });

            }//for_end
        } else if (typeId == 4) {//"判断题"
            $('#edtext').addClass('hide');
            $('#form-attachments').removeClass('hide');
            document.getElementById("form-attachments").innerHTML = "<br/>";
            $('#inbox-tabs').addClass('hide');

            var file = $('<div class="form-group file-input-container"><div class="col-sm-7" style="width:100%;margin:0px;">\
						<div class="col-sm-9">\
						<label class="inline">\
						<input name="form-field-radio" type="radio" class="ace" value="T" >\
						<span class="lbl middle" ><i class="ace-icon fa fa-check green"></i>正确</span>\
					</label>\
					&nbsp; &nbsp; &nbsp;\
					<label class="inline">\
						<input name="form-field-radio" type="radio" class="ace" value="F" >\
						<span class="lbl middle" ><i class="ace-icon fa fa-times red"></i>错误</span>\
					</label>\
				</div>\
				</div></div>').appendTo('#form-attachments');
            file.addClass('width-15');
        } else {
            $('#inbox-tabs').addClass('hide');
            $('#form-attachments').addClass('hide');
            $('#edtext').removeClass('hide');
        }

    }

    setTimeout(function () {
        console.log(getoptionAnswerArr);
        var optionAnswerArr = getoptionAnswerArr;
        var splitedArrs = new Array();
        splitedArrs = optionAnswerArr.split(";");
        for (i = 0; i < splitedArrs.length - 1; i++) {
            var optionAnswerArrId = splitedArrs[i];
            $.ajax({
                type: 'POST',
                url: '/school/onlineExam/publicQuestion/getQuestionsAns?getoptionAnswerArr=' + optionAnswerArrId,
                dataType: 'json',
                success: function (data) {
                    var stanswer = data.stanswer;
                    var stoption = data.stoption;
                    setRadio(stanswer);
                    setCheckbox(stanswer);
                    setStoption(stoption);
                },
                error: function () {
                    alert("获取数据失败!");
                }
            });
        }
    }, 1000);

    function setRadio(stanswer) {
        var questionStanswer = stanswer;
        //单选题，判断题:checked
        $("input[name=form-field-radio]").each(function () {
            if ($(this).parent().parent().parent().find("h4[name=szimu]").text() == questionStanswer || this.value == questionStanswer) {
                $(this).attr("checked", 'checked');
            }
        });
    }

    function setCheckbox(stanswer) {
        var questionStanswer = stanswer;
        //多选题:checked
        $("input[name=form-field-checkbox]").each(function () {
            if ($(this).parent().parent().parent().find("h4[name=szimu]").text() == questionStanswer) {
                $(this).attr("checked", 'checked');
            }
        });
    }
</script>

<script>
    $chapterName1Select = $("#chapterName1");//章
    var zname;
    (function () {
        var xzsubjectsId = "${publicQuestion.xzsubjectsId}";
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/myQuestion/findchapterName1?subId=' + xzsubjectsId,
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
    })()
</script>
<script>
    $titleTypeNameSelect = $("#titleTypeName");//题型
    var cid = "${myQuestions.xzsubjectsId}";
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
    })()
</script>

