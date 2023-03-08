<#include "/common/components/select.ftl"/>
<div class="container-div">
    <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
        <#--        <@shiro.hasPermission name="teacher:course:view">-->
        <#--            <a class="btn btn-success" onclick="$.operate.add()">-->
        <#--                <i class="fa fa-plus"></i> 新增-->
        <#--            </a>-->
        <#--        </@shiro.hasPermission>-->

        <#--        <@shiro.hasPermission name="teacher:course:view">-->
        <#--            <a class="btn btn-primary btn-edit disabled" onclick="$.operate.edit()">-->
        <#--                <i class="fa fa-edit"></i> 修改-->
        <#--            </a>-->
        <#--        </@shiro.hasPermission>-->
        <#--        <@shiro.hasPermission name="teacher:course:view">-->
        <#--            <a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">-->
        <#--                <i class="fa fa-remove"></i> 删除-->
        <#--            </a>-->
        <#--        </@shiro.hasPermission>-->
<#--        <@shiro.hasPermission name="teacher:course:view">-->
            <a class="btn btn-success" onclick="$.operate.add()">
                <i class="fa fa-plus"></i> 新增
            </a>
<#--        </@shiro.hasPermission>-->

<#--        <@shiro.hasPermission name="teacher:course:view">-->
            <a class="btn btn-primary btn-edit disabled" onclick="$.operate.edit()">
                <i class="fa fa-edit"></i> 修改
            </a>
<#--        </@shiro.hasPermission>-->


<#--        <@shiro.hasPermission name="teacher:course:view">-->
            <a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">
                <i class="fa fa-remove"></i> 删除
            </a>
<#--        </@shiro.hasPermission>-->
    </div>

    <div class="col-sm-12 select-table table-striped">
        <table id="bootstrap-table" data-mobile-responsive="true"></table>
    </div>
</div>
<script type="text/javascript">
    let prefix = "/school/onlineExam/testPaperOneList";

    $(function () {
        let options = {
            url: prefix + "/list/${id}",
            createUrl: prefix + "/add/${id}",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            sortName: "id",
            sortOrder: "desc",
            modalName: "试卷",
            columns: [{
                checkbox: true
            },
                {
                    field: 'id',
                    title: '编号',
                    formatter: function (value, row, index) {
                        return index+1;
                    }
                },
                // {
                //     field: 'headline',
                //     title: '标题'
                // },
                {
                    field: 'testName',
                    title: '试卷名'
                },
                {
                    field: 'rule',
                    title: '组卷规则'
                },
                {
                    field: 'createTime',
                    title: '创建时间',
                    sortable: true
                },
                {
                    field: 'state',
                    title: '是否已组卷'
                },
                {
                    field: 'score',
                    title: '试卷总分'
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];

                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="showPaper(\'' + row.id + '\')"><i class="fa fa-edit"></i>预览</a> ');

                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="Humanbuild(\'' + row.id +  ',' + row.rule + ','+ row.state +'\')"><i class="fa fa-edit"></i>组卷</a> ');

                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="fabu(\'' + row.id + '\')"><i class="fa fa-edit"></i>发布</a> ');

                        return actions.join('');
                    }
                }]
        };
        $.table.init(options);
        $.common.initBind();
    });


    function Humanbuild(tid) {
        let cid = "${id}";
        var paper = tid.split(",");
        var pid = paper[0];
        var prule = paper[1];
        var state = paper[2];
        if (prule == "随机") {
            $.modal.openFull('随机组卷', "/school/onlineExam/testPaperOneList/Coursebuild?id=" + pid,saveCallback,"1200px","650px");
        }else{
            let result = $.modal.openFull('固定组卷', "/school/paper/buildOne?id=" + pid+"&&cid="+cid, function () {
                $.modal.alertSuccess("固定组卷成功，如需设置题目乱序请到试卷库设置");
            });
            if (result) {
                $.modal.alertSuccess("已更新试题列表");
            }
        }
    }
    function detail(id) {
        $.modal.openFull( '详情', '/school/onlineExam/testPaperOne/showDetail?id=' + id, saveCallback);
    }
    function examControl(id) {
        $.modal.open( '监考链接', '/school/onlineExam/testPaperOne/examControl?id=' + id,saveCallback,'800px','300px');
    }
    function addExam(id) {
        $.modal.confirm("确定发布该考试吗？", function () {
            // type： 资源类型，1、视频，2、课件，3、习题，4、考试
            if (!$.common.isEmpty(id)) {
                $.operate.postJson('/teacher/chapter/saveChapterResource', {
                    chapterId:1, courseId: '${id}', rid: id, type: 4, length: 0
                }, true, function (result) {
                    if (result === undefined) {
                        return;
                    }
                    if (result.code === web_status.SUCCESS) {
                        $.modal.msgSuccess('发布成功');

                        //关闭iframe页面
                        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                        parent.layer.close(index);

                    } else {
                        $.modal.msgError(result.msg);
                    }
                })
            }
        });
        // $.modal.openFull( '详情', '/teacher/chapter/saveChapterResource?chapterId=1&courseId=162&rid='+id+'&type=4&length=0');
    }
    var token=localStorage.getItem("token")
    function saveCallback(context) {
        console.log("context:" + 123);
    }

    function openwindow(id)
    {
        window.open('/school/onlineExam/testPaperOne/stuToTest?id='+id,'_blank');

    }

    function showPaper(id) {
        window.open('/school/onlineExam/testPaperOneList/paperPreview?id='+id+"&token="+token,'_blank');
    }

    function analyze(id) {
        var cid = "${id}";
        $.modal.open('作业分析', "/school/onlineExam/testPaperOne/analyzePaper?id=" + id+"&&cid="+cid, saveCallback);
    }

    /**
     * lzj复用
     */
    function Coursebuild(tid) {
        let cid = "${id}";
        var paper = tid.split(",");
        var pid = paper[0];
        var prule = paper[1];
        if (prule == "随机") {
            $.modal.openFull('随机组卷', "/school/onlineExam/testPaperOne/Coursebuild?id=" + pid, saveCallback);
        }else{
            $.modal.openFull('固定组卷', "/school/onlineExam/testPaperOne/Humanbuild?id=" + pid+"&&cid="+cid, saveCallback);
        }
    }
    function setExamY(id) {
        // 移动
        $.modal.confirm("确认要开始?", function () {
            let data = {
                "ids": id
            };
            $.operate.submit("/school/onlineExam/testPaperOne/setExamY", "post", "json", data, true, function (result) {
                if (result.code === web_status.SUCCESS) {
                    $.modal.alertSuccess("操作成功！");
                } else {
                    $.modal.alertError("操作失败:"+result.code);
                }

            });

        });
    }

    function setExamN(id) {
        // 移动
        $.modal.confirm("确认要取消?", function () {
            let data = {
                "ids": id
            };
            $.operate.submit("/school/onlineExam/testPaperOne/setExamN", "post", "json", data, true, function (result) {
                if (result.code === web_status.SUCCESS) {
                    $.modal.alertSuccess("操作成功！");
                } else {
                    $.modal.alertError("操作失败:");
                }

            });

        });
    }
    function fabu(id) {
        var cid = "${id}";
        // var id = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
        $.modal.open('发布', "/school/onlineExam/userExam/fabu?id=" + id+"&&cid="+cid, saveCallback);

    }
</script>
<script>

</script>
