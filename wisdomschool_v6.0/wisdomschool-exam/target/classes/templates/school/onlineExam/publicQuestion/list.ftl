<div class="container-div" id="listId">
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
                            &nbsp;节：<select style="width:89px;" name="jChapterId" id="jChapterId">
                                <option value="">所有</option>
                            </select>
                        </li>
                        <li>
                            题&nbsp;型：<select style="width:60px;" name="titleTypeId" id="titleTypeId">
                                <option value="">所有</option>
                            </select>
                        </li>
                        <li>
                            <a class="btn btn-primary btn-rounded btn-sm" onclick="$.table.search()"><i
                                        class="fa fa-search"></i>&nbsp;搜索</a>
                            <a class="btn btn-warning btn-rounded btn-sm" onclick="$.form.reset()"><i
                                        class="fa fa-refresh"></i>&nbsp;重置</a>
                        </li>
                    </ul>
                </div>
            </form>
        </div>
        <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
<#--            <@shiro.hasPermission name="teacher:course:view">-->
                <a class="btn btn-danger btn-del disabled" onclick="$.operate.removeAll()">
                    <i class="fa fa-remove"></i> 删除
                </a>
<#--            </@shiro.hasPermission>-->
        </div>
        <div class="col-sm-12 select-table table-striped">
            <table id="bootstrap-table" class="bootstrap-table" data-mobile-responsive="true"></table>
        </div>
    </div>
</div>

<script type="text/javascript">
    let prefix = "/school/onlineExam/publicQuestion";
    $(function () {
        let options = {
            url: prefix + "/list/${id}",
            parentCode: "parentId",
            createUrl: prefix + "/add/${id}",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            sortName: "id",
            search: false,
            showExport: false,
            // modalName: "查看题目",
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
                    field: 'createBy',
                    title: '分享人'
                },
                {
                    field: 'difficulty',
                    title: '难度',
                    sortable: true
                },
                {
                    field: 'createTime',
                    title: '创建时间',
                    sortable: true
                },
                {  title: '操作',
            align: 'center',
            formatter: function (value, row, index) {
            let actions = [];
<#--                <@shiro.hasPermission name="teacher:course:view">-->
            actions.push('<a class="btn btn-success btn-xs " href="#" onclick="$.operate.editFull(\'' + row.id + '\')"><i class="fa fa-edit"></i>查看</a> ');
<#--            </@shiro.hasPermission>-->
<#--                <@shiro.hasPermission name="teacher:course:view">-->
                actions.push('<a class="btn btn-success btn-xs " href="#" onclick="toPrivate(\'' + row.id + '\')"><i class="fa fa-edit"></i>移入我的题库</a> ');
<#--                </@shiro.hasPermission>-->
            return actions.join('');
        }
    }]
        };
        $.table.init(options);
    });

</script>
<script>

    function toPrivate(qid) {
        // 移动
        let rows = $.common.isEmpty($.table._option.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns($.table._option.uniqueId);
        $.modal.confirm("确认要移入我的题库吗?", function () {
            let data = {
                "ids": qid
            };

            $.operate.submit("/school/onlineExam/publicQuestion/toPrivate", "post", "json", data,true,function (result) {
                if(result.code===web_status.SUCCESS){
                    $.modal.alertSuccess("操作成功！");
                }else{
                    $.modal.alertWarning("操作未成功！");
                }
            });
        });
    }
</script>
<script type="text/javascript">
    $titleTypeNameSelectPublic = $("#titleTypeId");//题型
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




</script>

<script type="text/javascript">
    $chapterName1SelectPublic = $("#chapterId");//章
    $chapterNameSelectPublic = $("#jChapterId");//节
    $subjectsNameSelectPublic = $("#subjectsId");//科目
    $titleTypeNameSelectPublic = $("#titleTypeId");//题型
    var subId = "${id}";
    var zname;
    var jname;
    /**
     *    清空
     */
    $("#reset").click(function () {
        $chapterName1SelectPublic.find("option:not(:first)").remove();//章
        $chapterNameSelectPublic.find("option:not(:first)").remove();//节
        $titleTypeNameSelectPublic.find("option:not(:first)").remove();//题型
    });


    //显示第五级——节数，给第四级的下拉选项添加单击响应事件
    function ChapChange(chapParentID) {
        $chapterNameSelectPublic.find("option:not(:first)").remove();
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
                    $chapterNameSelectPublic.append('<option value="' + cid2 + '" ${"'+result+'"}>' + chaName + '</option>');
                }
            },
            error: function () {
                alert("获取数据失败!");
            }
        });
    }
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
                    $chapterName1SelectPublic.append('<option value="' + tid + '" ${"'+result+'"}>' + chaName + '</option>');
                }
            },
            error: function () {
                alert("获取数据失败!");
            }
        });
    })()

</script>

<script>
    $titleTypeNameSelectPublic = $("#titleTypeId");//题型
    (function () {
        $.ajax({
            type: 'POST',
            url: '/school/onlineExam/titleType/findTitleType',
            dataType: 'json',
            success: function (data) {
                for (i = 0; i < data.length; i++) {
                    var titleTypeId = data[i].id;
                    var titName = data[i].name;//name="'+titName+'"
                    var titnum = data[i].templateNum;
                    $titleTypeNameSelectPublic.append('<option value="' + titleTypeId + '" title="' + titnum + '" >' + titName + '</option>');
                }
            },
            error: function () {
                alert("获取题型数据失败!");
            }
        });
    })()
</script>

