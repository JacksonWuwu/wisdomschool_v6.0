<link rel="stylesheet" href="/css/course-main.css">
<link href="/js/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/js/plugins/jquery.poshytip/tip-yellowsimple/tip-yellowsimple.css"/>
<script type="text/javascript" src="/js/plugins/jquery.poshytip/jquery.poshytip.min.js"></script>
<link rel="stylesheet" href="/css/forum.css">

<link rel="stylesheet" type="text/css"
      href="/js/plugins/pagination/style/pagination.css"
      media="screen">
<link rel="stylesheet" type="text/css"
      href="/js/plugins/pagination/style/normalize.css"
      media="screen">
<style type="text/css">

    blockquote {
        border-left: none;
    }

    .module-content-select {
        cursor: pointer;
        line-height: normal;
        outline: 0;
        white-space: nowrap;
        float: right;
        background: #FFF;
        border: 1px solid #d4d4d4;
        border-radius: 3px;
        color: #333;
        padding: 10px 25px 10px 10px;
        text-align: left;
        position: relative;
        font-size: 14px;
        min-width: 160px;
        vertical-align: top;
        width: 160px;
        outline: 0;
        height: 39px;
    }

</style>
<link rel="stylesheet" href="/css/editor.css">
<div id="auto-id-1555802290742" <#--style="padding-top: 60px;"--> class="auto-1555802290734-parent"
     cz-shortcut-listen="true">
    <div class="g-headwrap" id="j-fixed-head" data-log-id="topNav"
         data-log-data="{&quot;pageName&quot;:&quot;顶部导航&quot;}">
        <div class="m-yktnav " id="j-topnav">
            <div class="m-yktnav_wrap f-pr f-cb">
                <div class="m-breadcrumbBox f-cb" id="j-breadcrumbBox">
                    <div class="g-mn2">
                        <div class="g-flow f-cb">
                            <ul class="g-flow">
                                <li class="navcrumb-item">
                                    <a target="_blank" href="/admin">我的课程</a>
                                </li>
                                <li class="navcrumb-item current" id="navcrumbId-2">
                                    <span class="arrow"></span>
                                    <a href="javascript: void(0);">${course.name }</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="g-flow">
                    <div class="b-20"></div>
                    <div>
                        <div class="f-bg headBox" id="courseHead">
                            <div class="u-courseHead" id="auto-id-1555802291070">
                                <#include '/front/courseinfo.ftl'/>
                                <div class="j-courseheadTab">
                                    <ul class="f-cb tabarea">
                                        <li class="f-fl"><a class="j-chtab" hidefocus="true"
                                                            href="/user/course/learn/${tcid}">主页</a></li>
                                        <!--Regular if107-->
                                        <li class="f-fl"><a class="j-chtab selected" hidefocus="true">讨论区</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="b-15"></div>
                    <div class="m-forumIndex f-cb">
                        <div class="g-cimn2" id="auto-id-1555855417164">
                            <div class="g-cimn2c f-bg f-cb" id="j-forum-box">
                                <div class="u-forumbread">
                                    <a href="/user/courseTopicList/${tcid}">讨论区</a>
                                    <span class="sp"></span>
                                    <#--<a href="https://study.163.com/forum/index.htm?cid=1004075068&amp;fid=1002893099&amp;p=1">老师答疑区</a>
                                    <span class="sp"></span>-->
                                    <span>话题发布</span>
                                </div>
                                <div class="m-postTopic" id="auto-id-1555855417163">
                                    <div style="background-color: #fff" id="div1">
                                        <div style="margin-top: 10px">
                                            <h4 style="font-size: 16px;font-weight: 700">
                                                <span class="glyphicon glyphicon-pencil" style="color:#23aaa0"></span>
                                                &nbsp;&nbsp;话题发布
                                            </h4>
                                        </div>
                                        <div style="padding:20px 0px;">
                                            <div class="module-content" style="float: left;">
                                                <select id="selectforum" class="module-content-select">
                                                    <#list forumList as forum>
                                                        <option value="${forum.id}">${forum.name}</option>
                                                    </#list>
                                                </select>
                                            </div>

                                            <div style="float:left;margin-left:20px;width:500px">
                                                <input type="text" id="talktitle" style="height: 39px"
                                                       class="form-control"
                                                       placeholder="标题：一句话说明你想分享的经验或问题">
                                            </div>
                                            <div
                                                    style="margin-top:45px;width:400px;background-color:rgba(255, 203, 203, 0.6);padding:5px;text-align:center;color:#ff7d0d;">
                                                请选择相关话题后发布，如发布至无关话题下，帖子将被删除
                                            </div>
                                            <div style="margin-top:30px"></div>
                                            <!--发布的框 -->
                                            <textarea class="layui-textarea" id="LAY_demo1" style="display: none">

            </textarea>
                                        </div>

                                        <div style="float: right;margin-bottom: 10px">

                                            <a id="cancel" class="btn btn-default" style="width: 110px;padding: 9px 0"
                                               href="/user/courseTopicList/${tcid}">取消</a>
                                            <a id="saveQuestion" class="btn btn-primary"
                                               style="background-color: #00BC9B;width: 110px;padding: 9px 0"
                                               href="javascript:void(0)">发布</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="g-cisd2 f-bg">
                            <div class="m-forumRule">
                                <h3 class="tit">发起讨论规则</h3>
                                <blockquote>很高兴你愿意和老师同学一起交流，分享你的问题、经验、思考、创意和吐槽。如果你在其它地方看到好的内容，也转发过来一起享用吧。</blockquote>
                                <blockquote>满足基本原则的帖子更容易获得老师/同学参与。</blockquote>
                                <blockquote>
                                    <b>【基本原则】</b>
                                </blockquote>
                                <blockquote>
                                    <li><b>尊重为本，友善沟通。</b>在此互动的是与你一样爱学习的人，请给予你希望获得的同样的尊重。</li>
                                </blockquote>
                                <blockquote>
                                    <li><b>鼓励为主，关注进步。</b>学习就是为了变得更好，与其笑别人不懂，不如帮助他更快提升。</li>
                                </blockquote>
                                <blockquote>
                                    <li><b>明确表达，有效互动。</b>凝炼准确的表达是思路清晰的表现，如果帖子过长且模糊，你可能需要先想清楚。</li>
                                </blockquote>
                                <blockquote>
                                    <b>【发帖方法】</b>
                                </blockquote>
                                <blockquote>
                                    以下是发帖的基本操作，准确发帖可以让大家更方便的和你交流。
                                </blockquote>
                                <blockquote>
                                    <li>主题区请填写帖子标题，建议用一句话言简意赅的概括问题/内容。</li>
                                </blockquote>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>

    $('.info-tip').poshytip({
        className: 'tip-yellowsimple',
        showTimeout: 1,
        alignTo: 'target',
        alignX: 'center',
        offsetY: 5,
        allowTipHover: false
    });
</script>
<script type="text/javascript" src="/js/plugins/ckeditor/ckeditor.js"></script>
<script>
    $(document).ready(function () {
        //页面加载完自动执行
    });
    ckeditor1 = CKEDITOR.replace('LAY_demo1', {
        height: 300,
        toolbar: [
            {name: 'document', items: ['Save', 'NewPage', 'DocProps', 'Preview', 'Print', '-', 'Templates']},
            {name: 'clipboard', items: ['Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo']},
            {name: 'editing', items: ['Find', 'Replace', '-', 'SelectAll', '-', 'SpellChecker', 'Scayt']},
            {
                name: 'forms',
                items: ['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton',
                    'HiddenField']
            },
            {
                name: 'basicstyles',
                items: ['Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', '-', 'RemoveFormat']
            },
            {
                name: 'paragraph',
                items: ['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote', 'CreateDiv',
                    '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', '-', 'BidiLtr', 'BidiRtl']
            },
            {name: 'links', items: ['Link', 'Unlink', 'Anchor']},
            {
                name: 'insert',
                items: ['Image', 'Flash', 'Table', 'HorizontalRule', 'Smiley', 'SpecialChar', 'PageBreak', 'Iframe']
            },
            {name: 'styles', items: ['Styles', 'Format', 'Font', 'FontSize']},
            {name: 'colors', items: ['TextColor', 'BGColor']},
            {name: 'tools', items: ['Maximize', 'ShowBlocks']}]
    });

    //-----------------进入论坛----------------------------
    function talktongji(course, forumid, sort) {//参数：课程类别1=第一门，板块类别0=全部，排序方式1=第一种排序
        window.location.href = "/user/topicList?" +
            "course=" + course + "&" +
            "forumid=" + forumid + "&" +
            "sort=" + sort;
    }

    $("#saveQuestion").click(
        function () {
            let talktitle = $("#talktitle").val();
            let talkcontent = CKEDITOR.instances.LAY_demo1.document.getBody().getText();
            let forumid = $("#selectforum option:selected").val();
            if (talktitle == "" && talkcontent == "") {
                alert("标题和内容不能为空");
            } else {
                console.log(talkcontent);

                $.ajax({
                    type: "post",
                    url: "${basePath }/user/topic/add",
                    data: {
                        "talktitle": talktitle,
                        "talkcontent": CKEDITOR.instances.LAY_demo1.getData(),
                        "forumid": forumid,
                        "tcid": ${tcid}
                    },
                    dataType: 'json',
                    success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success
                        window.location.href = "/user/courseTopicList/${tcid}";
                    }
                });
            }
        });
</script>