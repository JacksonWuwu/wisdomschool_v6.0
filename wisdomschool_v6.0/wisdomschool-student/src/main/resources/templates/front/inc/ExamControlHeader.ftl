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
                <li><a href="/examControl">首页</a></li>
<#--                <li><a href="/user/course/learnone/${tcid}">在线考试</a></li>-->
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <img id="user-img"
                     src="/img/front/avatar3.png"
                     style="margin:10px 0px; float:left;width:30px;height: 30px;display:none;"/>


                    <#if tcid??>
                        <li style="width: 350px;"><a href="javascript:void(0);">当前课程：<span>${course.name}</span></a></li>
                    </#if>
                     <li class="pull-left">
                      <li class="light-blue dropdown-modal">
                          <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                          <#--<img class="nav-sysUser-photo" src="/img/avatars/user.jpg" alt="Jason's Photo"/>-->
                              <span class="sysUser-info">
									<#--<small>欢迎,</small>-->
									<@shiro.principal property="userName"/>
								</span>

                              <i class="ace-icon fa fa-caret-down"></i>
                          </a>

                          <ul class="sysUser-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close"style="top: auto; right: auto;">
                              <#--li>
                                  <a href="#">
                                      <i class="ace-icon fa fa-cog"></i>
                                      设置
                                  </a>
                              </li>
                              <li class="divider"></li-->

                              <li>
                                  <a href="/account/index" class="user">
                                      <i class="ace-icon fa fa-sysUser"></i>
                                      个人主页
                                  </a>
                              </li>



                              <li>
                                  <a href="/logout">
                                      <i class="ace-icon fa fa-power-off"></i>
                                      退出登录
                                  </a>
                              </li>
                          </ul>





                      <#--<@notifiesBadge uid=profile.id>-->
                      <#--<a href="/user/personal/<@shiro.principal property="id"/>" class="user">-->
                      <#--<span><@shiro.principal property="userName"/></span><span-->
                      <#--class="discussion-item-icon">-->
                      <#--&lt;#&ndash;<#if (result.notifies > 0)>-->
                      <#--<svg class="octicon octicon-primitive-dot" viewBox="0 0 8 16" version="1.1" width="8" height="16" aria-hidden="true">-->
                      <#--<path fill="red" fill-rule="evenodd" d="M0 8c0-2.2 1.8-4 4-4s4 1.8 4 4-1.8 4-4 4-4-1.8-4-4z"></path>-->
                      <#--</svg>-->
                      <#--</#if>&ndash;&gt;-->
                      <#--</span>-->
                      <#--</a>-->
                      </li>

                    <li id="login-ad"><a type="button"
                                         style="color:#fff;font-size:15px;background-color:#8fbc8f;border-radius:3px;padding:10px 20px;margin-top:5px;"
                                         href="${base}/login">登录</a></li>


                <!--  <li id="login-houtai"><a href="${basePath}/login-admin.jsp">后台管理</a></li> -->
            </ul>
        </div>
    </div>
</nav>
<script>
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
</script>
