<#include "/common/components/select.ftl"/>
<div class="container-div layout-container-div clearfix" >
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
        <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
<#--            <@shiro.hasPermission name="teacher:course:view">-->
                <a class="btn btn-success" onclick="$.operate.addFull()">
                    <i class="fa fa-plus"></i> 新增
                </a>
<#--            </@shiro.hasPermission>-->
<#--            <@shiro.hasPermission name="teacher:course:view">-->
                <a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">
                    <i class="fa fa-remove"></i> 删除
                </a>
<#--            </@shiro.hasPermission>-->
<#--            <@shiro.hasPermission name="teacher:course:view">-->
                <a class="btn btn-info" onclick="$.table.importExcel()">
                    <i class="fa fa-upload"></i> 导入
                </a>
<#--            </@shiro.hasPermission>-->
        </div>
        <div class="col-sm-12 select-table table-striped">
            <table id="bootstrap-table" class="bootstrap-table" data-mobile-responsive="true"></table>
        </div>
    </div>
</div>
<form id="importForm" enctype="multipart/form-data" class="mt-20 mb-10" style="display: none;">
    <div class="col-xs-offset-1">
        <input type="file" id="file" name="file"/>
        <div class="mt-10 pt-5">
            &nbsp; <a onclick="downloadTemplate()" class="btn btn-default btn-xs">
                <i class="fa fa-file-excel-o"></i> 下载模板</a>
        </div>
        <span class="text-danger pull-left mt-10">
            提示：仅允许导入“xls”或“xlsx”格式文件！模板中题目类型请填写对应的编号，
        </span>
        <a id="tishi"></a>

    </div>
</form>
<script type="text/javascript">
    let prefix = "/school/onlineExam/myQuestion";
    $(function () {
        let tid1 = ${tid};
        let cid1= ${id};
        let options = {
            url: prefix + "/list/${id}",
            parentCode: "parentId",
            createUrl: prefix + "/add/${id}",
            updateUrl: prefix + "/edit/{id}/"+cid1,
            removeUrl: prefix + "/remove",
            exportUrl: prefix + "/export",
            importUrl: prefix + '/importData?cid=' + cid1+"&tid="+tid1,
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
                }, {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
<#--                        <@shiro.hasPermission name="teacher:course:view">-->
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="$.operate.editFull(\'' + row.id + '\')"><i class="fa fa-edit"></i>编辑</a> ');
<#--                        </@shiro.hasPermission>-->
                        <#--<@shiro.hasPermission name="teacher:course:view">-->
                        //actions.push('<a class="btn btn-success btn-xs " href="#" onclick="toPublic(\'' + row.id + '\')"><i class="fa fa-edit"></i>共享</a> ');
                        <#--</@shiro.hasPermission>-->
                        return actions.join('');
                    }
                }
            ]
        };
        $.table.init(options);
        $.common.initBind();

    });

    function toPublic(qid) {
        // 移动
        $.modal.confirm("确认要分享?", function () {
            let url = $.table._option.removeUrl;
            let data = {
                "ids": qid
            };
            console.log("qid:"+qid);
            $.operate.submit("/school/onlineExam/myQuestion/toPublic", "post", "json", data, true, function (result) {
                if (result.code === web_status.SUCCESS) {
                    $.modal.alertSuccess("操作成功！");
                } else {
                    $.modal.alertError("操作失败:");
                }

            });
        });
    }

    function downloadTemplate() {
        window.open('/download/template');
    }

</script>
<script type="text/javascript">
    $chapterName1Select = $("#chapterId");//章
    $chapterNameSelect = $("#jchapterId");//节
    $subjectsNameSelect = $("#subjectsId");//科目
    $titleTypeNameSelect = $("#titleTypeName");//题型
    var subId = "${id}";
    var zname;
    var jname;

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
    }
</script>
<script>
    $titleTypeNameSelect = $("#titleTypeName");//题型
    var cid = "${id}";
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
<script>
    //显示——章数，给第三级的下拉选项添加单击响应事件
    (function () {
        var cid = "${id}";
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
    })()

</script>
<script type="text/javascript">
    (function (){
        let tid1 = ${tid};
        let cid1= ${id};
        $.ajax({
            type: 'GET',
            url: '/jiaowu/teacherCourse/gettixingid?cid=' + cid1+"&tid="+tid1,
            dataType: 'json',
            success: function (data) {
                $("#tishi").append('<i>'+data[1]+'的编号是'+data[0]+'。'+data[3]+'的编号是'+data[2]+'。'+data[5]+'的编号是'+data[4]+'。</i>');
            },
            error: function () {
                alert("获取数据失败!");
            }
        });
    })()
</script>
