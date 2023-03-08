<#include "/common/style.ftl"/>
<div class="container-div">
    <div class="row">
    <div class="col-sm-12 search-collapse">
        <form id="sysUser-form">
            <input type="hidden" id="currentId" name="currentId">
            <input type="hidden" id="currentType" name="currentType">
            <input type="hidden" id="parentId" name="parentId">
            <div class="select-list">
                <ul>
                    <li>
                        <div class="r">
                            学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：<input type="text" name="loginName"/>
                        </div>
                    </li>
                    <li>
                        <div class="r">
                            姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：<input type="text" name="userName"/>
                        </div>
                    </li>

                    <li>
                        班&nbsp;级：<select style="width:60px;" name="cid" id="cid">
                            <option value="">所有</option>
                        </select>
                    </li>
                    <li>
                        <a class="btn btn-primary btn-rounded btn-sm" onclick="$.table.search()"><i
                                    class="fa fa-search"></i>&nbsp;搜索</a>
                    </li>
                </ul>
            </div>
        </form>
    </div>
        <div class="btn-group-sm hidden-xs" id="toolbar" role="group">
                <a class="btn btn-success" onclick="downloadAll('${id}')" >
                    <i class="fa fa-plus"></i> 下载全部文件
                </a>
               <a class="btn btn-warning" onclick="$.table.exportExcel()">
                  <i class="fa fa-download"></i> 导出
               </a>
                <a class="btn btn-danger" onclick="clearCache()">
                    <i class="fa fa-remove"></i> 清除预览缓存
                </a>
        </div>
        <div class="col-sm-12 select-table table-striped">
            <table id="bootstrap-table" data-mobile-responsive="true"></table>
        </div>
    </div>
</div>

<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    let prefix = "/teacher/adjunctdetail/${id}";
    let prefix2 = "/teacher/adjunctdetail"
    $(function () {
        let options = {
            url: prefix + "/list",
            updateUrl: prefix + "/edit/{id}",
            removeUrl: prefix + "/remove",
            exportUrl: prefix + "/exportAdjunct",
            modalName: "签到名单",
            showExport: true,
            columns: [{
                checkbox: false
            },
                {
                    field: 'id',
                    title: '编号',
                    sortable: true,
                    formatter: function (value, row, index) {
                        return index + 1;
                    }
                },
                {
                    field: 'loginName',
                    title: '学号',
                    sortable: true,
                },
                {
                    field: 'clbum.name',
                    title: '班级',
                },
                {
                    field: 'userName',
                    title: '姓名'
                },
                {
                    field: 'results',
                    title: '成绩',
                    sortable: true
                },

                {
                    field: 'States',
                    title: '状态',
                    sortable: true,
                    formatter: function(value, item, index) {
                        if (item.states == '0') {
                            return '<span class="label label-success">未交</span>';
                        }
                        else if (item.states == '1') {
                            return '<a class="btn btn-warning btn-xs" href="#" onclick="preview('+item.id+')"><i class="glyphicon glyphicon-eye-open"></i>预览</a> ';
                        }
                    }
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
                        actions.push('<a class="btn btn-danger btn-xs" style="display:block;height: 30px;width: 65px" id="btn'+row.id+'" href="#" onclick="evaluates(this,'+row.id+')">评定成绩</a>' +
                            '<input id="input'+row.id+'" onblur="return update(this,'+row.id+')" style="display:none;height: 20px;width: 65px" />');
                        return actions.join('');
                    }
                }]
        };
        $.table.init(options);
        $.common.initBind();
    });
    function preview(id){
        window.open("/teacher/adjunctdetail/viewAdjunct/"+id+"?token="+localStorage.getItem("token"))
    }

    function clearCache(){
        $.modal.loading("正在处理中，请稍后...");
        $.ajax({
            type: 'POST',
            url: "/teacher/adjunctdetail/clear",
            dataType: 'json',
            success: function (data) {
                $.modal.msgSuccess("清除成功")
                $.modal.closeLoading();
            },
            error: function () {
                $.modal.msgError("清除失败");
            }
        });
    }

    function downloadAll(adjid) {
        $.modal.loading("正在处理中，请稍后...");
        $.ajax({
            type: 'POST',
            url: "/teacher/adjunctdetail/downloadAll/" + adjid,
            dataType: 'json',
            success: function (data) {
                window.location.href = "${storage}/downloadFileTwo?Filename="+data;
                $.modal.closeLoading();
            },
            error: function () {
                $.modal.msgError("下载失败！请检查网络状态或通知网络管理员");
            }
        });
    }


    function evaluates(obj,id) {
        obj.style.display="none";
        document.getElementById("input"+id).style.display="block"
    }
    //更新分数
    function update(obj,id) {
        document.getElementById("btn"+id).style.display="block"
        obj.style.display="none"
        var value=obj.value
        var Regx = /^[A-Za-z0-9]*$/
         if (!value) {
             return false;
        } else if (!(Regx.test(value))){
             alert('只能输入字母和数组');
             return false;
        }else{
             $.operate.submitPost(prefix2 + "/adjunctdetailedit", {
                 "id":id,
                 "results":value
             });
             $('[name$=refresh]').trigger('click');
             return true;
        }

    }



</script>
<script>
    $cidSelect = $("#cid");
    var adjid = "${id}";
    $(function () {
        $.ajax({
            type: 'POST',
            url: '/teacher/adjunctdetail/findClbum?adjid=' + adjid,
            dataType: 'json',
            success: function (data) {
                console.log("data,length:" + data.length);
                for (i = 0; i < data.length; i++) {
                    console.log("clbumname:" + data[i].name);
                    var cid = data[i].id;
                    var clbumname = data[i].name;//name="'+titName+'"
                    $cidSelect.append('<option value="' + cid + '">' + clbumname + '</option>');
                }
            },
            error: function () {
                alert("获取题型数据失败!");
            }

        });
    })
</script>
