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
                                                        <!-- <textarea id="myEditor2" name="myEditor2"></textarea> -->
                                                        <textarea name="myoptionAnswerArr" rows="5"
                                                                  id="myEditor2"></textarea>
                                                    </div>
                                                </div><!-- /.widget-main -->
                                            </div><!-- /.widget-body -->
                                        </div><!-- /.widget-box -->
                                    </div><!-- /.col -->
                                 <!-- /.widget-box -->
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
    let prefix = "/teacher/vote";
    var cid = "${cid}";
    var myoptionAnswerArr = "";
    var depName;
    var subId;
    var zyname;
    var kmname;
    var zname;
    var jname;
    var stid;
    var optlk;
    var typeId;
    var num = 65;
    var oleng;
    var oarr = new Array();
    var ansarr = new Array();
    let ckeditor1;
    let ckeditor2;

    $(function () {
        initEditor();
    })

    function initEditor() {
        ckeditor1 = CKEDITOR.replace('myEditor1');
        ckeditor2 = CKEDITOR.replace('myEditor2');

    }

    /**
     *    保存
     */
    function submitHandler() {
        let title = CKEDITOR.instances.myEditor1.document.getBody().getText();
        let xzsubjectsId = "${id}";
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
        if(title!="\n"&&myoptionAnswerArr!="option=%0A:0:;"&&myoptionAnswerArr!="option=%0A:0:;option=%0A:0:;"&&myoptionAnswerArr!="option=:0:;option=:0:;option=:0:;option=:0:;"&&myoptionAnswerArr!="option=:0:;option=:0:;option=:0:;option=:0:;option=%0A:0:;"
            &&myoptionAnswerArr!="option=:第一空:;"&&myoptionAnswerArr!="option=%0A:0:;option=%0A:0:;option=:0:;option=:0:;option=:0:;option=:0:;") {
            if ($.validate.form()) {
                $.operate.saveModal(prefix + "/add", $.param({
                    'title': title,
                    'myoptionAnswerArr': myoptionAnswerArr,
                    'xzsubjectsId': xzsubjectsId,
                }));
            }
            console.log("aaaaaaaaaaa" + myoptionAnswerArr);
        }
        else{
            $.modal.alertWarning("请填写完整信息");
        }
    }


    /**
     *    增加试题选项
     */
    $('#inbox-tabs')
        .on('click', function () {
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
            //if_end
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
            }
    }
</script>
<script>
    (function () {
        titleType();
    })()
</script>
