<#include "/front/account/component/header.ftl"/>
<div class="u-container">
    <div class="container">
        <div class="row feature">
            <div style="width: 890px; margin-bottom: 30px;">
                <div style="margin-left: 30px; border-bottom: solid 1px #bebebe">
                    <h4 style="margin: 20px 30px;">
                        <span style="color: #1d9cba;" class="glyphicon glyphicon-pencil"></span>&nbsp;&nbsp;练习
                    </h4>
                </div>
                <div>
                <#--     内容  -->
                    <#list courses as course>
                        <div class="col-md-4 col-xs-12">
                            <div class="infotitle">
                                <div style="font-size: 15px;color:#6aaf75;">${course.course.name}</div>
                            </div>
                            <img class="img-responsive center-block" src="${paper.teacherCourse.thumbnailPath}">
                            <div style="font-size: 15px;padding: 12px;line-height: 25px;height: 30px;overflow: hidden;"></div>
                            <div style="text-align: right;">
                                <a href="/user/personal/shuati/${paper.teacherCourse.cid}" class="btn btn-warning btn-rounded">去刷题</a>
                            </div>
                        </div>
                    </#list>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="/js/plugins/layui/layui.js"></script>

<#include "/front/account/component/footer.ftl"/>
