
<div style="height:200px;"></div>
<!-- 可删除 -->
<div class="Service-box">
    <div class="Service-content clearfix">
        <a href="javascript:;" class="Service-item">
            <div class="item-image">
                <img src="/img/images/icon-tag001.png" alt="">
            </div>
            <h3 class="item-title">画板</h3>
            <button STYLE="background: none; border: 0px" onclick="huaban()"><span class="item-link">我要使用</span></button>
        </a>

        <a href="javascript:;" class="Service-item">
            <div class="item-image">
                <img src="/img/images/icon-tag003.png" alt="">
            </div>
            <h3 class="item-title">思维导图</h3>
            <button STYLE="background: none; border: 0px" onclick="thinking()"><span class="item-link">我要使用</span></button>
        </a>

    </div>
</div>



<#include "/common/stretch.ftl"/>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
<script type="text/javascript">


    function huaban() {
        window.open("/teacher/study/huaban")
    }
    function thinking() {
        window.open("/teacher/study/thinking")
    }

    function lukydraw() {
        <#--var tcid="${tcid}"-->
        <#--&lt;#&ndash;window.location.href="/teacher/study/huaban"&ndash;&gt;-->
        <#--&lt;#&ndash;window.open("/teacher/study/luck?tcid="+tcid)&ndash;&gt;-->
        window.open("/teacher/study/luck")
        // $.modal.open('选择班级', ");
    }

    function vote() {
        window.location.href="/teacher/study/huaban"
    }
    function study() {
        window.location.href="/teacher/study/huaban"
    }
    function text() {
        window.location.href="/teacher/study/text"
    }
</script>
