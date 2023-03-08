<#include "/common/style.ftl"/>
<div class="container-div container-file-div layout-container-div clearfix">
    <div class="row">
        <div class="col-sm-12 search-collapse">
            <form id="info-form">
                <div class="select-list">
                    <ul>
                        <li>
                            班级：<select style="width:89px;" name="nation" id="clumb">
                                <option value="">所有</option>
                            </select>
                        </li>
                        <li>
                            <label>学号：</label><input type="text" name="loginName" id="loginName"/>
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
        <div class="col-sm-12 select-table table-striped">
            <table id="bootstrap-table" data-mobile-responsive="true"></table>
        </div>
    </div>
</div><!--/.container-div-->

<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    let prefix = "/teacher/preview";

    $(function () {

        let options = {
            url: prefix + "/statis/${pid}",
            modalName: "学习统计",
            search: false,
            columns: [{
                checkbox: true
            },
                {
                    field: 'id',
                    title: '编号',
                    formatter(value, row, index) {
                        return index+1;
                    }
                },
                {
                    field: 'params.clbumName',
                    title: '班级'
                },
                {
                    field: 'loginName',
                    title: '学号'
                },
                {
                    field: 'userName',
                    title: '姓名'
                },
                {
                    field: 'params.watchVideoCount',
                    title: '视频观看数',
                },
                {
                    title: '详细观看',
                    formatter: function (value, row, index) {
                        let actions = [];
                        // actions.push('<a class="btn btn-success btn-xs " href="#" onclick="detailVideo(' + row.id + ', '+ tcId + ')"><i class="fa fa-edit"></i>详细</a> ');
                        return actions.join('');
                    }
                },
                {
                    field: 'params.pptCount',
                    title: 'ppt观看总数',
                },
                {
                    title: 'PPT详细',
                    formatter: function (value, row, index) {
                        let actions = [];
                        // actions.push('<a class="btn btn-success btn-xs " href="#" onclick="detailUser(' + row.id + ', '+ tcId + ')"><i class="fa fa-edit"></i>详细</a> ');
                        // actions.push('<a class="btn btn-success btn-xs " href="#" onclick="detailppt(' + row.id + ', '+ tcId + ')"><i class="fa fa-edit"></i>详细</a> ');
                        return actions.join('');
                    }
                }]
        };
        $.table.init(options);
        $.common.initBind();
    });
</script>

<script>
    <#--$clumbNameSelect = $("#clumb");//班级-->
    <#--&lt;#&ndash;var tid = ${tid};&ndash;&gt;-->
    <#--&lt;#&ndash;var cid = ${cid};&ndash;&gt;-->
    <#--&lt;#&ndash;var tcId = ${tcId};&ndash;&gt;-->
    <#--var clumbName;-->
    <#--//显示—班级，给下拉选项添加单击响应事件-->
    <#--(function () {-->
    <#--    $.ajax({-->
    <#--        type: 'GET',-->
    <#--        url: '/teacher/course/findClbumByTcid?tcId='+tcId,-->
    <#--        dataType: 'json',-->
    <#--        success: function (data) {-->
    <#--            console.log(data);-->
    <#--            for (i = 0; i < data.length; i++) {-->
    <#--                var clumbName = data[i].name;-->
    <#--                console.log("班级"+clumbName);-->
    <#--                $clumbNameSelect.append('<option value="' + data[i].id + '" >' + clumbName + '</option>');-->
    <#--            }-->
    <#--        },-->
    <#--        error: function () {-->
    <#--            alert("获取数据失败!");-->
    <#--        }-->
    <#--    });-->
    <#--})()-->


    function detailVideo(userId, tcId) {
        $.modal.openFull('用户观看视频记录', '/teacher/videoChapterUser/showDetail?id=' + userId + '&&tcId='+ tcId);
    }

    function detailppt(userId, tcId) {
        $.modal.openFull('用户观看PPT记录', '/teacher/ppt/Detail?id=' + userId + "&&tcId=" + tcId);
    }


</script>
