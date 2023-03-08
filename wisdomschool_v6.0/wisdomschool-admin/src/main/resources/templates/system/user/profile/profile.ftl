<!DOCTYPE html>
<html lang="zh" xmlns:th="http://www.thymeleaf.org" >
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!--360浏览器优先以webkit内核解析-->
    <title>个人信息</title>
    <link rel="shortcut icon" href="favicon.ico">
    <link th:href="@{/css/bootstrap.min.css}" rel="stylesheet"/>
    <link th:href="@{/css/font-awesome.min.css}" rel="stylesheet"/>
    <link th:href="@{/css/main/animate.min.css}" rel="stylesheet"/>
    <link th:href="@{/css/main/style.min862f.css}" rel="stylesheet"/>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content">
        <div class="row animated fadeInRight">
            <div class="col-sm-6">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>个人信息</h5>
                    <div class="ibox-tools">
                        
                        <a class="dropdown-toggle" data-toggle="dropdown" href="profile.html#">
                            <i class="fa fa-edit"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-sysUser">
                            <li><a href="javascript:edit()">修改信息</a></li>
                            <li><a href="javascript:resetPwd()">修改密码</a></li>
                            <li><a href="javascript:avatar()">修改头像</a></li>
                        </ul>
                       
                    </div>
                    </div>
                    <div class="contact-box">
	                        <div class="col-sm-4">
	                            <div class="text-center">
	                                <img alt="image" class="img-circle m-t-xs img-responsive" th:src="(${sysUser.avatar} == '') ? @{/img/profile.jpg} : @{/profile/avatar/} + ${sysUser.avatar}">
	                                <div class="m-t-xs font-bold">[[${sysUser.loginIp}]]</div>
	                            </div>
	                        </div>
	                        <div class="col-sm-8">
	                            <h3><strong>[[${sysUser.loginName}]]</strong></h3>
	                            <p><i class="fa fa-sysUser"></i> [[${sysUser.userName}]] / [[${#strings.defaultString(roleGroup,'无角色')}]]
	                            <p><i class="fa fa-phone"></i> [[${sysUser.phoneNumber}]]</p>
	                            <p><i class="fa fa-group"></i> [[${sysUser.dept?.deptName}]] / [[${#strings.defaultString(postGroup,'无岗位')}]]</p>
	                            <p><i class="fa fa-transgender"></i> 性别：[[${sysUser.sex}]]</p>
	                            <p><i class="fa fa-envelope-o"></i> [[${sysUser.email}]]</p>
	                            <p><i class="fa fa-calendar"></i> [[${#dates.format(sysUser.createTime, 'yyyy-MM-dd HH:mm:ss')}]]</p>
	                        </div>
	                        <div class="clearfix"></div>
	                    </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <div th:include="include::footer"></div>
    <script>
        var userId = [[${sysUser.userId}]];
	    /*用户信息-修改*/
	    function edit() {
	        var url = ctx + "system/sysUser/profile/edit/" + userId;
	        $.modal.open("修改用户", url);
	    }
	    /*用户管理-重置密码*/
	    function resetPwd() {
	        var url = ctx + 'system/sysUser/profile/resetPwd/' + userId;
	        $.modal.open("重置密码", url, '800', '500');
	    }
	    /*用户管理-头像*/
	    function avatar() {
	        var url = ctx + 'system/sysUser/profile/avatar/' + userId;
	        $.modal.open("修改头像", url);
	    }
	</script>
</body>
</html>
