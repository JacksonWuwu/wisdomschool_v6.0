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

    let prefix = "/teacher/vote";
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
    let title = "${myQuestions.title}";
    var xzsubjectsId = "${myQuestions.xzsubjectsId}";
    var parsing = "${myQuestions.parsing}";
    var myoptionAnswerArr = "${myQuestions.myoptionAnswerArr}";
    var getoptionAnswerArr = "${myQuestions.myoptionAnswerArr}";

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
        let id = "${myQuestions.id}";
        let ststatus = $("input[name='ststatus']:checked").val();
        console.log("ststatus!" + ststatus);

        var optionAnswerArr = "";//试题答案
        let title = CKEDITOR.instances.myEditor1.document.getBody().getText();
        console.log("title!" + title);
        //alert(optl);
            //选项内容
        $("textarea[id=form-field-11]").each(function () {
            var optionselect = $(this).parent().find("input[name='form-field-radio']:checked").val();
            var optionval = $(this).parent().find("h4[name=szimu]").text();
            if (optionselect == null) {
                optionAnswerArr += $.param({'option': $(this).val()}) + ":" + "0:" + ";";//选项:答案;
            } else {
                optionAnswerArr += $.param({'option': $(this).val()}) + ":" + optionval + ":" + ";";//选项:答案;
            }
        });

        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/update", $.param({
                'id': id,
                'title': title,
                'myoptionAnswerArr': myoptionAnswerArr,
                'myoptionAnswerArrContent': optionAnswerArr
            }));
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
        });

    setTimeout(function () {
        setTimeout(function () {
            CKEDITOR.instances.myEditor1.document.getBody().setText(title);
            setTimeout(function () {
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
            url: '/teacher/vote/getQuestionsAnsList?getoptionAnswerArr=' + myGetoptionAnswerArr,
            dataType: 'json',
            success: function (data) {
                for (let i = 0; i < data.length; i++) {
                    oarr[i] = data[i]
                }
                titleType();
            },
            error: function () {
                alert("获取答案数据失3232败!");
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
                url: '/teacher/vote/getQuestionsAns?getoptionAnswerArr=' + optionAnswerArrId,
                dataType: 'json',
                success: function (data) {
                    var stanswer = data.stanswer;
                    stoption = data.stoption;
                    setRadio(stanswer);
                },
                error: function () {
                    alert("12答案信息数据失败!");
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
                url: '/teacher/vote/getQuestionsAns?getoptionAnswerArr=' + optionAnswerArrId,
                dataType: 'json',
                success: function (data) {
                    var stanswer = data.stanswer;
                    var stoption = data.stoption;
                    setRadio(stanswer);
                },
                error: function () {
                    alert("获取数据失败123!");
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


</script>


