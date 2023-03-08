<#include "/common/style.ftl"/>
<link href="/js/plugins/ueditor/themes/iframe.css" rel="stylesheet" />
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
                                        <label >
                                            <i class="text-danger">*</i>
                                            添加题目需要选定章节及小节，请检查课程目录
                                        </label>
                                    </div>
                                    <div class="form-group" style="width:100%; ">
                                        <div style="min-width:322px;float:left;">
                                            <label class="col-sm-3 "
                                                   style="letter-spacing: 10px;line-height:30px;width:72px;float:left;">
                                                分类
                                            </label>
                                            <select style="width:125px;" id="chapterName1" name="chapterName1"
                                                    onchange="ChapChange(this.value)">
                                                <option value="0">请选择章数</option>
                                            </select>
                                            <select style="width:125px;" id="chapterName"
                                                    name="chapterId">
                                                <option> 请选择节数</option>
                                            </select>
                                        </div>
                                        <div style="min-width:322px;float:right;">
                                            <label class="col-sm-3 "
                                                   style="letter-spacing: 10px;line-height:30px;width:72px;;">
                                                公开
                                            </label>
                                            <input type="radio" name="ststatus" value="2"/>
                                            <label for="ststatus1">是</label>
                                            <input type="radio" name="ststatus" value="1" checked/>
                                            <label for="ststatus2">否</label>
                                        </div>
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
                                                    name="difficulty">
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
                                            <input type="text" id="year" name="year" value=" "/>
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
                                                <button id="inbox-tabs" type="button" class="btn btn-xs btn-danger hide"
                                                        style="margin-bottom:3px;">
                                                    <i class=" ace-icon fa fa-plus smaller-75"></i>
                                                    添加选项
                                                </button>
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
                                                        <textarea name="myoptionAnswerArr" rows="5"
                                                                  id="myEditor2"></textarea>
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
                                                </h4>
                                                <div class="widget-toolbar">
                                                    <a href="#" data-action="collapse">
                                                        <i class="ace-icon fa fa-chevron-up"></i>
                                                    </a>
                                                </div>
                                            </div>

                                            <div class="widget-body">
                                                <div class="widget-main ">
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
<script type="text/javascript" src="/js/plugins/ueditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="/js/plugins/ueditor/ueditor.all.js"></script>
<script type="text/javascript" src="/js/plugins/ueditor/ueditor.parse.js"></script>
<script type="text/javascript"  src="/js/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
    $chapterName1Select = $("#chapterName1");//章
    $chapterNameSelect = $("#chapterName");//节
    $titleTypeNameSelect = $("#titleTypeName");//题型
    let prefix = "/school/onlineExam/myQuestion";
    var cid = "${cid}";
    var myoptionAnswerArr = "";
    var zname;
    var jname;
    var typeId;
    var titleTypeId;
    var num = 65;
    var oleng;
    let ue1;
    let ue2;
    let ue3;
    var myQuestions;
    var myQAlist=new Array();
    var UeditorConfig={
        initialFrameHeight : 200,
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


    $(function () {
        initEditor();
        MyQuestionsInfo();
        var myDate = new Date();
        document.getElementById('year').value = myDate.getFullYear();
    })


    function initEditor() {
        UE.Editor.prototype._bkGetAtionUrl=UE.Editor.prototype.getActionUrl;
        var actions="uploadimage,uploadscrawl,catchimage,uploadfile".split(",");
        UE.Editor.prototype.getActionUrl=function(action){
            if(actions.indexOf(action) === -1){
                return this._bkGetAtionUrl.call(this,action);
            }else{
                return "/upload"+"?token="+localStorage.getItem("token")
            }
        }
        ue1=UE.getEditor('myEditor1',UeditorConfig);
        ue2=UE.getEditor('myEditor2',UeditorConfig);
        ue3=UE.getEditor('myEditor3',UeditorConfig);

        ue1.addListener("ready", function () {
            //赋值
            ue1.setContent(myQuestions.title);
        });
        ue2.addListener("ready", function () {
            //赋值
            if(myQuestions.templateNum==="5"){//当为编程题的时候
                if(myQAlist.length>0){
                    ue2.setContent(myQAlist[0].stoption);
                }
            }
        });
        ue3.addListener("ready", function () {
            //赋值
            ue3.setContent(myQuestions.parsing);
        });


    }



    function MyQuestionsInfo() {
        $.ajax({
            type: 'get',
            url: '/school/onlineExam/myQuestion/MyQuestionsInfo/${MyQuestionsId}',
            dataType: 'json',
            success: function (res) {
                console.log(res)
                if (res.code === 0) {
                    myQuestions = res.data.myQuestions
                    myQAlist = res.data.myQAlist
                    zname=myQuestions.chapterId
                    jname=myQuestions.jchapterId
                    titleTypeId=myQuestions.titleTypeId
                    $("#difficulty").val(myQuestions.difficulty);
                    getChapter();
                    ChapChange(myQuestions.chapterId)
                    getTitleType();
                }
            }, error: function () {
                alert("获取数据失败!");
            }
        });
    }
        /**
         *    保存
         */
        function submitHandler() {
            let ststatus = $("input[name='ststatus']:checked").val();
            let title = ue1.getContent();
            var titleTypeId = document.getElementById('titleTypeName').value;
            var difficulty = document.getElementById('difficulty').value;
            var chapterId = document.getElementById('chapterName').value;
            var year = document.getElementById('year').value;
            let parsing = ue3.getContent();
            if (typeId == 1) {//"单选题"
                //选项内容
                $("textarea[id=form-field-11]").each(function () {
                    var optionselect = $(this).parent().find("input[name='form-field-radio']:checked").val();
                    var optionval = $(this).parent().find("h4[name=szimu]").text();
                    if (optionselect == null) {
// 						alert("没有选中");
                        myoptionAnswerArr += $.param({'option': $(this).val()}) + ":" + "0:" + ";";//选项:答案;
                    } else {
// 						alert("选中");
                        myoptionAnswerArr += $.param({'option': $(this).val()}) + ":" + optionval + ":" + ";";//选项:答案;
                    }
                });
            } else if (typeId == 2) {//"双选题"||typeId=="不定项"
                //选项内容
                $("textarea[id=form-field-11]").each(function () {
                    /* alert($(this).parent().find("input[name='form-field-radio']:checked").val()); */
                    var optionselect = $(this).parent().find("input[name='form-field-checkbox']:checked").val();
                    var optionval = $(this).parent().find("h4[name=szimu]").text();
                    if (optionselect == null) {
                        myoptionAnswerArr += $.param({'option': $(this).val()}) + ":" + "0:" + ";";//选项:答案;
                    } else {
                        myoptionAnswerArr += $.param({'option': $(this).val()}) + ":" + optionval + ":" + ";";//选项:答案;
                    }

                });
            } else if (typeId == 3) {//"填空题"
                //选项内容
                $("textarea[id=form-field-11]").each(function () {
                    var optionval = $(this).parent().find("h4[name=chinesenum]").text();
                    if (optionval == null) {
                    } else {
                        myoptionAnswerArr += $.param({'option': $(this).val()}) + ":" + optionval + ":" + ";";//选项:答案;
                    }
                });
            } else if (typeId == 4) {//"判断题"
                //判断题
                $("input[name=form-field-radio]:checked").each(function () {
                    myoptionAnswerArr += "0:" + $(this).val() + ":" + ";";//选项:答案;
                });
            } else {
                myoptionAnswerArr += $.param({'option': ue2.getContent()}) + ":" + "0:" + ";";//选项:答案;
            }
            if (title != "\n" && chapterId != "请选择节数" && difficulty != "请选择难度" && titleTypeId != "请选择题型" && myoptionAnswerArr != "option=%0A:0:;" && myoptionAnswerArr != "option=%0A:0:;option=%0A:0:;" && myoptionAnswerArr != "option=:0:;option=:0:;option=:0:;option=:0:;" && myoptionAnswerArr != "option=:0:;option=:0:;option=:0:;option=:0:;option=%0A:0:;"
                && myoptionAnswerArr != "option=:第一空:;" && myoptionAnswerArr != "option=%0A:0:;option=%0A:0:;option=:0:;option=:0:;option=:0:;option=:0:;") {
                if ($.validate.form()) {
                    $.operate.saveModal(prefix + "/update", $.param({
                        'ststatus': ststatus,
                        'id': myQuestions.id,
                        'title': title,
                        'myoptionAnswerArr': myQuestions.myoptionAnswerArr,//选项的id
                        'myoptionAnswerArrContent': myoptionAnswerArr,//选项的具体内容
                        'parsing': parsing,
                        'xzsubjectsId': myQuestions.xzsubjectsId,
                        'titleTypeId': titleTypeId,
                        'jchapterId': chapterId,
                        'difficulty': difficulty,
                        'year': year
                    }));
                }
            } else {
                $.modal.alertWarning("请填写完整信息");
            }
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
                            <input name="form-field-radio" type="radio" class="ace">\
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
                            <input name="form-field-checkbox" type="checkbox" class="ace" >\
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

        /**
         *    方法函数
         */
        //选项题选项中字母序号变化函数
        function zimuChange() {
            num = 65;
            $("h4[name=szimu]").each(function () {
                //	t+=$(this).html() + ",";

                $(this).each(function () {
                    var str = String.fromCharCode(num++);
                    this.innerHTML = str;
                    //tid+=$(this).find("div[name=typelable]").attr("value")+ ",";
                });
            });
        }

        //填空题选项中文序号变化函数
        function chinesenumChange() {
            num = 1;
            $("h4[name=chinesenum]").each(function () {

                $(this).each(function () {
                    var str = charname(num++);
                    this.innerHTML = "第" + str + "空";
                });
            });
        }


        //显示第五级——节数，给第四级的下拉选项添加单击响应事件
        function ChapChange(chapParentID) {
            $chapterNameSelect.find("option:not(:first)").remove();
            $.ajax({
                type: 'POST',
                url: '/school/onlineExam/myQuestion/findchapterName2?chaperId=' + chapParentID,
                dataType: 'json',
                success: function (data) {
                    for (i = 0; i < data.length; i++) {
                        var cid2 = data[i].id;
                        var chaName = data[i].title;
                        var result
                        if (jname === cid2) {
                             result = "selected";
                        } else {
                             result = null;
                        }
                        $chapterNameSelect.append('<option value="' + cid2 + '" ${"'+result+'"}>' + chaName + '</option>');
                    }
                },
                error: function () {
                    alert("获取数据失败!");
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
            var optvals = "";
            document.getElementById("form-attachments").innerHTML = "<br/>";
            typeId = $("#titleTypeName").find("option:selected").prop("title");
            console.log("题型改变产生不同答案编辑框" + typeId);
            if (typeId == 1) {//单选题
                $('#edtext').addClass('hide');
                $('#form-attachments').removeClass('hide');
                num = 65;
                if (myQAlist.length != 0) {
                    oleng = 64 + myQAlist.length;
                } else {
                    oleng = 68;
                }

                for (; num <= oleng; num++) {
                    var select=""
                    optvals = myQAlist[num - 65].stoption;
                    if (myQAlist[num - 65].stanswer!=="0"){
                        select="checked"
                    }
                    var str = String.fromCharCode(num);
                    $('#inbox-tabs').removeClass('hide');
                    var file = $('<div class="radio" style="float:left;margin:0px;">\
                           <label>\
                               <input name="form-field-radio" type="radio" class="ace" '+select+' >\
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
                if (myQAlist.length !== 0) {
                    oleng = 64 + myQAlist.length;
                } else {
                    oleng = 68;
                }
                //$("#form-attachments").empty();
                document.getElementById("form-attachments").innerHTML = "<br/>";
                for (; num <= oleng; num++) {
                    optvals = myQAlist[num - 65].stoption;
                    var select=""
                    if (myQAlist[num - 65].stanswer!=="0"){
                        select="checked"
                    }
                    var str = String.fromCharCode(num);

                    $('#inbox-tabs').removeClass('hide');

                    var file = $('<div class="checkbox" style="float:left;margin:0px;">\
                           <label>\
                               <input name="form-field-checkbox" type="checkbox" class="ace" '+select+'>\
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
                if (myQAlist.length !== 0) {
                    oleng = myQAlist.length;
                } else {
                    oleng = 1;
                }
                for (; num <= oleng; num++) {
                    optvals = myQAlist[num - 1].stoption;
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
                for (let i = 0; i < myQAlist.length; i++) {
                  var select=""
                  if (myQAlist[i].stanswer!=="0"){
                     select="checked"
                   }
                }
                $('#edtext').addClass('hide');
                $('#form-attachments').removeClass('hide');
                document.getElementById("form-attachments").innerHTML = "<br/>";
                $('#inbox-tabs').addClass('hide');
                var file = $('<div class="form-group file-input-container"><div class="col-sm-7" style="width:100%;margin:0px;">\
						<div class="col-sm-9">\
						<label class="inline">\
						<input name="form-field-radio" type="radio" class="ace" value="T" '+select+' >\
						<span class="lbl middle" ><i class="ace-icon fa fa-check green" ></i>正确</span>\
					</label>\
					&nbsp; &nbsp; &nbsp;\
					<label class="inline">\
						<input name="form-field-radio" type="radio" class="ace" value="F" '+select+' >\
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
        };

    //显示——章数，给第三级的下拉选项添加单击响应事件
    function getChapter() {
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/myQuestion/findchapterName1?subId=' + cid,
            dataType: 'json',
            success: function (data) {
                for (i = 0; i < data.length; i++) {
                    var tid = data[i].id;
                    var chaName = data[i].title;
                    var result
                    if (zname === tid) {
                         result = "selected" ;
                    } else {
                         result = null;
                    }
                    $chapterName1Select.append('<option value="' + tid + '" ${"'+result+'"}>' + chaName + '</option>');
                }
            },
            error: function () {
                alert("获取数据失败!");
            }
        });
    }
    function getTitleType(){
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/myQuestion/findTitleType?cid='+cid,
            dataType: 'json',
            success: function (data) {
                for (i = 0; i < data.length; i++) {
                    var tid = data[i].id;
                    var titName = data[i].titleTypeName;//name="'+titName+'"
                    var titnum = data[i].templateNum;
                    if(titleTypeId===tid){
                      $titleTypeNameSelect.append('<option value="' + tid + '" title="' + titnum + '"  selected >' + titName + '</option>');
                    }else {
                        $titleTypeNameSelect.append('<option value="' + tid + '" title="' + titnum + '"  >' + titName + '</option>');
                    }
                }
                titleType();
            },
            error: function () {
                alert("获取题型数据失败!");
            }
        });
    }

</script>


