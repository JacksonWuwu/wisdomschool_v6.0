<#include "/front/account/component/header.ftl"/>
<style>
    .btn-default:hover{
        background-color: #2c654587;
        border-color: #2c654587;
    }
</style>

   <div class="u-container">

       <div class="container">
           <div class="row feature">
               <div style="width: 890px; margin-bottom: 30px;">
                   <div style="margin-left: 30px; border-bottom: solid 1px #bebebe">
                       <h4 style="margin: 20px 30px;">
                           <span style="color: #1d9cba;" class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;基本信息
                       </h4>
                   </div>

                   <div>
                       <div class="jianjie">
                           学号：<span class="neirong">${sysUser.loginName}</span>
                       </div>
                       <div class="jianjie">
                           姓名：<span class="neirong">
										${sysUser.userName}</span>
                       </div>
                       <div class="jianjie">
                           性别：<span class="neirong">

                               <#if sysUser.sex=0>男<#else >女</#if>
                       </span>
                       </div>
<#--                       <div class="jianjie">-->
<#--                           绑定邮箱：<span class="neirong">-->
<#--                                    <@shiro.principal property="email"/>-->
<#--                       </span>-->
                       </div>
                       <!--div class="jianjie">
                           所在班级：<span class="neirong">

                       </span>
                       </div-->
                       <div class="jianjie">
                           我的学校：<span class="neirong">${school.name}</span>
                       </div>

                   </div>
                   <div class="center-block"
                        style="width: 713px; margin-top: 35px;">

                       <button class="btn btn-default" onclick="toxiugai()">
                           修改密码
                       </button>
<#--                       <button class="btn btn-default" onclick="toxiugaiemail()">-->
<#--                           修改邮箱-->
<#--                       </button>-->

                   </div>
               </div>
           </div>
       </div>
   </div>


<div id="modal-table-updateUser" class="modal" tabindex="-1">
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

<div id="modal-table-updateUser-email" class="modal" tabindex="-1">
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
                        <label>邮&nbsp;&nbsp;&nbsp;&nbsp;箱：</label> <input
                            id="newemail" type="text" class="form-control col-xs-4"
                            placeholder="若不想更改邮箱可留空"/>
                    </div>

                </div>
                <div class="col-xs-2"></div>


            </div>
            <div class="modal-footer">

                <button class="btn btn-sm btn-primary" type="button" id="saveUseremail">
                    <i class="icon-ok"></i>保存
                </button>
            </div>
        </div>
    </div>
</div>

<script src="/js/plugins/layui/layui.js"></script>
<script type="text/javascript">


    function selectattention() {

        var s = "<%=session.getAttribute("
        userId
        ")%>";
        if (s != "null") {
            window.location.href = "integral/allintegralAct!findattentionlist.action?userId=" + s;

        } else {
            return false;
        }
    }

    function updateAvatar() {
        $('#modal-table-xiugaitouxiang').modal('show');
    }
    function toxiugai() {

        $('#modal-table-updateUser').modal('show');

    }
    function toxiugaiemail() {

        $('#modal-table-updateUser-email').modal('show');

    }
    $("#quxiao").click(
            function () {
                $('#modal-table-xiugaitouxiang').modal('hide');
                window.location.href = "integral/allintegralAct!personalByuserid.action?userId=<%=session.getAttribute("
                userId
                ")%>";

            });
    $("#saveupdate").click(
            function () {

                $('#modal-table-xiugaitouxiang').modal('hide');

                var img64 = $("#upImg")[0].files[0];
                var fd = new FormData();
                fd.append('file', img64);

                $.ajax({
                    type: "post",
                    url: "/account/info/thumb",
                    data: fd,
                    cache: false,
                    contentType: false,
                    processData: false,
                    async: false, //同步处理
                    success: function (data) { //这里的status是ajax自己的参数，请求成功就success
                        if (0 == data.code) {
                        } else {
                        }
                    }
                });
            });

    function toindex() {

        window.location.href = "";

    }

    //读取总页码or列出全部资源
    function yematongji(dangqianyema) {

        window.location.href = "courseUP/CourseUploadAction!fileyema.action?DangQianYeMa=" + dangqianyema;

    }

    //读取总页码or列出全部课件
    function kejianku(dangqianyema) {

        window.location.href = "courseUP/CourseUploadAction!kejianku.action?DangQianYeMa=" + dangqianyema;

    }

    //读取总页码or列出全部上机
    function shangjitiku(dangqianyema) {

        window.location.href = "courseUP/CourseUploadAction!shangjitiku.action?DangQianYeMa=" + dangqianyema;

    }

    //读取总页码or列出全部试题
    function shitiku(dangqianyema) {

        window.location.href = "courseUP/CourseUploadAction!shitiku.action?DangQianYeMa=" + dangqianyema;

    }

    //读取总页码or列出全部视频
    function shipinku(dangqianyema) {

        window.location.href = "courseUP/CourseUploadAction!shipinku.action?DangQianYeMa=" + dangqianyema;

    }

    //读取总页码or列出全部案例
    function anliku(dangqianyema) {

        window.location.href = "courseUP/CourseUploadAction!anliku.action?DangQianYeMa=" + dangqianyema;

    }

    //读取开发工具的信息
    function kaifagongju() {

        window.location.href = "tool/DeveToolMangeAction!showHuangjinQiantai.action";

    }

    //读取总页码or列出全部资源
    function newstongji(dangqianyema) {

        window.location.href = "news/newsMangeAction!newsyema.action?DangQianYeMa=" + dangqianyema;

    }

    //课程学习
    function coursefg() {
        window.location.href = "learn/courseLearnMangeAction!toCourseLearnList.action";

    }

    //读取总页码or列出全部问题
    function talktongji(dangqianyema) {

        window.location.href = "talk/talkMangeAction!talkyema.action?DangQianYeMa=" + dangqianyema;


    }

    //读取学习指导的信息
    function xuexizhidao() {

        window.location.href = "tool/DeveToolMangeAction!showDaoxueQiantai.action";

    }

    $("[name='topic']").click(function () {
        var id = $(this).attr("id");
        window.location.href = "${basePath }/user/topic/detail/" + id;
    });

    $("[name='reply']").click(function () {
        var id = $(this).attr("id");
        window.location.href = "${basePath }/user/topic/detail/" + id;
    });

    function xuanzetimu(testid) {
        //alert(testid);
        window.location.href = "${basePath }/onlineExam/testManageAct!TestStart.action?testid=" + testid,
                "mywindow", "status=1,toolbar=0";

    }


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
    $("#saveUseremail")
            .click(
                    function () {
                        var email=$("#newemail").val();
                        var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
                        if($("#newemail").val()=="")
                        {
                            alert("邮箱不能为空");
                            return false;


                        }else if(!reg.test(email))
                        {
                            alert("邮箱格式不正确");
                            $("#email1").focus();
                            return false;
                        };
                        var stuName = $("#stuName").val();
                        var stuemail = $("#newemail").val();

                        $
                                .ajax({
                                    type: "post",
                                    url: "/user/email",
                                    data: {
                                        "stuName": stuName,
                                        "stuemail": stuemail,
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
                    }
            );
</script>

<#include "/front/account/component/footer.ftl"/>
