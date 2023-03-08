<nav class="nav navbar navbar-default"style="height: 106px;">
    <div class="container-fluid"style="width: 1350px;">
        <!-- 导航栏头部 -->
        <div class="navbar-header">
            <!-- 折叠导航栏的按钮 -->
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#collapsenav"
                    aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <!-- logo -->
            <div class="logo-contanier">
                <a class="navbar-brand">
                    <img  style="width:80px;margin-top:1px;">
                </a>
                <span class="slogan"><p class="navbar-text" style="width: 220px; font-size: 20px;">智慧教育云平台</p></span>
            </div>
        </div>
        <!-- 导航栏菜单 -->
        <div class="collapse navbar-collapse" id="collapsenav">
            <ul class="nav navbar-nav navbar-left">
                <li><a href="/studentIndex">首页</a></li>
                <#if tcid??>
                    <li><a href="/user/course/paperWork/${tcid}">在线作业</a></li>
                    <#--<li><a href="/user/course/toExam/${tcid}">考试</a></li>-->
                    <li><a href="/user/course/learn/${tcid}">课程目录</a></li>
                    <li><a href="/user/course/preview/${tcid}">预习任务</a></li>
                    <li><a href="/user/resource/0/${tcid}?pageSize=8">资源库</a></li>
                    <#--li><a href="/user/resource/4?pageSize=8">在线测试</a></li-->
<#--                    <li><a href="/user/course/attendance/${tcid}">签到任务</a></li>-->
                    <li><a href="/user/course/adjunct/${tcid}">上机作业</a></li>
                   <li><a href="/user/topicList/${tcid}">学习讨论</a></li>
                </#if>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <img id="user-img"
                     src="/img/front/avatar3.png"
                     style="margin:10px 0px; float:left;width:30px;height: 30px;display:none;"/>


                    <#if tcid??>
                        <li style="width: 350px;"><a href="#" style="color: black">当前课程：<span id="courseName"></span></a></li>
                    </#if>
                     <li class="pull-left">
                      <li class="light-blue dropdown-modal">
                          <#--<a data-toggle="dropdown" href="#" class="dropdown-toggle">-->
                          <#--&lt;#&ndash;<img class="nav-sysUser-photo" src="/img/avatars/user.jpg" alt="Jason's Photo"/>&ndash;&gt;-->
                          <#--    <span class="sysUser-info">-->
							<#--		${sysUser.userName}-->
							<#--	</span>-->
                          <#--    <i class="ace-icon fa fa-caret-down"></i>-->
                          <#--</a>-->

                          <#--<ul class="sysUser-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close"style="top: auto; right: auto;">-->
                          <#--    <li>-->
                          <#--        <a href="/account/index" class="user">-->
                          <#--            <i class="ace-icon fa fa-sysUser"></i>-->
                          <#--            个人主页-->
                          <#--        </a>-->
                          <#--    </li>-->

                          <#--    <li>-->
                          <#--        <a onclick="logout()">-->
                          <#--            <i class="ace-icon fa fa-power-off"></i>-->
                          <#--            退出登录-->
                          <#--        </a>-->
                          <#--    </li>-->
                          <#--</ul>-->

                      </li>
            </ul>
        </div>
    </div>
</nav>
<script>
    $(document).ready(function () {
        //页面加载完自动执行
        CourseInfo()
    });

    function CourseInfo(){
        $.ajax({
            type: "get",
            url: "/user/courseInfo/${tcid}",
            success: function (res) {
                console.log(res)
                if (res.msg==0) {
                    course = res.data.course
                    document.getElementById('courseName').innerHTML = course.name;
                }
            }
        });
    }

    function toMyCourse() {
        let courses = document.getElementById('mycourses');
        console.log(courses !== null);
        if (courses !== undefined && courses !== null) {
            console.log("---=-=")
            document.getElementById('mycourses').scrollIntoView();
        } else {
            window.location.href = '?cur=_';
        }
    }
    function logout(){
        $.ajax({
            type: "get",
            url: "/admin/sysLogin/logout",
            success: function (res) {
                console.log(res)
                localStorage.clear();
                window.location.href="/loginIndex"
            },error: function(res){
                localStorage.clear();
                window.location.href="/loginIndex"
                console.log(res)
            }
        });
    }
</script>
