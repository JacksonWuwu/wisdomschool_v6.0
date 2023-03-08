<#include "/common/style.ftl"/>
<link rel="stylesheet" href="/js/plugins/jquery-layout/jquery.layout-latest.css">
<body class="white-bg">
<div class="container-div layout-container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4>登录信息</h4>
                </div>
                <div class="modal-body overflow-visible col-xs-12">

                    <div class="col-xs-2"></div>
                    <div class="col-xs-8">
                        <div id="userCue" class="cue">请注意格式</div>
                        <div class="form-group">
                            <label>用&nbsp;户&nbsp;名：</label> <input id="stuName" type="text"
                                                                   readonly="true " class="form-control col-xs-4"
                                                                   value="${sysUser.userName}"/>
                        </div>
                        <div class="form-group">
                            <label>原&nbsp;&nbsp;密&nbsp&nbsp;码：</label> <input
                                id="oldPassword" type="password" class="form-control col-xs-4"
                                placeholder="原密码"/>
                        </div>
                        <div class="form-group">
                            <label>密&nbsp;&nbsp;&nbsp;&nbsp;码：</label> <input
                                id="stuPassword" type="password" class="form-control col-xs-4"
                                placeholder="若不想更改密码可留空"/>
                        </div>
                        <div class="form-group">
                            <label>确认密码：</label> <input id="stuPassword2" type="password"
                                                        class="form-control col-xs-4" placeholder="若不想更改密码可留空"/>
                        </div>
                    </div>
                    <div class="col-xs-2"></div>


                </div>
                <div class="modal-footer">

                    <button class="btn btn-sm btn-primary" type="button" id="saveUser">
                        <i class="icon-ok"></i>保存
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    $("#saveUser")
            .click(
                    function () {

                        if ($('#stuName').val().length < 2
                                || $('#stuName').val().length > 16) {

                            $('#user').focus().css({
                                border: "1px solid red",
                                boxShadow: "0 0 2px red"
                            });
                            $('#userCue')
                                    .html(
                                            "<font color='red'><b>×用户名位2-16字符</b></font>");
                            return false;
                        }
                        if ($('#oldPassword').val().length != 0) {
                            if ($('#oldPassword').val().length < 6) {
                                $('#oldPassword').focus();
                                $('#userCue').html(
                                        "<font color='red'><b>×密码小于" + 6
                                        + "位</b></font>");
                                return false;
                            }
                        }
                        if ($('#stuPassword').val().length != 0) {
                            if ($('#stuPassword').val().length < 6) {
                                $('#stuPassword').focus();
                                $('#userCue').html(
                                        "<font color='red'><b>×密码不能小于" + 6
                                        + "位</b></font>");
                                return false;
                            }
                            if ($('#stuPassword').val() != $(
                                    '#stuPassword2').val()) {
                                $('#stuPassword2').focus();
                                $('#userCue')
                                        .html(
                                                "<font color='red'><b>×两次密码不一致！</b></font>");
                                return false;
                            }
                            if ($('#oldPassword').val() == $('#stuPassword').val()) {
                                $('#stuPassword').focus();
                                $('#userCue')
                                        .html(
                                                "<font color='red'><b>×新密码与原密码一致！</b></font>");
                            }
                        }


                        var stuName = $("#stuName").val();
                        var stuPassword = $("#stuPassword").val();
                        var oldPasswrod = $("#oldPassword").val();
                        $
                                .ajax({
                                    type: "post",
                                    url: "/user/password",
                                    data: {
                                        "stuName": stuName,
                                        "oldPassword": oldPasswrod,
                                        "password": stuPassword,
                                    },
                                    async: false, //同步处理
                                    success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success
                                        data = JSON.parse(data)
                                        if (data.msg == "success") {
                                            alert("修改成功！");
                                            $('#modal-table-updateUser')
                                                    .modal('hide');
                                            location.reload()
                                        } else {
                                            alert("添加失败！");
                                            $('#modal-table-updateUser')
                                                    .modal('hide');
                                        }
                                    }
                                });

                    });
</script>
